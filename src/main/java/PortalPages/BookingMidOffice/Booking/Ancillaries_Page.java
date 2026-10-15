package PortalPages.BookingMidOffice.Booking;

import PortalPages.BookingMidOffice.Booking_Common;
import PortalPages.BookingMidOffice.SearchBooking.SearchBooking_Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;
import utilities.DataUtils;
import utilities.FakerSingleton;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Portal clone of AdminPages.BookingMidOffice.Booking.Ancillaries_Page: ancillaries step of the
// Portal (agency) booking flow (bundle per route, meal per pax per segment, baggage per pax per
// journey) plus the price checks on every page that shows them afterwards: passenger details fare
// breakdown, booking confirmation, e-ticket and itinerary (booking details).
//
// Differences from Admin:
//  - Portal login, "Book Flight" sidebar link, no branch selection anywhere (an agency only has its own).
//  - "My Bookings" instead of Admin's Search Booking; pay after hold is done straight from the results row.
//  - No AR Ledger step: the charge is checked against the wallet balance in the Portal sidebar, read
//    before the booking and again after it (from a freshly loaded page -- the sidebar is stale after payment).
//  - Passenger page title dropdown / Next / terms locators accept both the Admin and the Portal markup.
//  - Itinerary costing lines that an agency may not see (Costing & Payment tab, sales / net / profit,
//    sidebar costing card) are checked when shown and logged when not.
//
// Every selection is recorded with the price shown in the dropdown at the moment it is picked, so
// the expected totals are computed from what the UI offered, never from hard-coded prices.
// Ancillary / document locators are the Admin ones (same components); Portal-only locators are
// marked "Portal" and still need confirming against the live Portal DOM on the first run.
public class Ancillaries_Page {

    private final SHAFT.GUI.WebDriver driver;

    public Ancillaries_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    // ---------------------------------------------------------------- selection model

    public enum Type { BUNDLE, MEAL, BAGGAGE }

    // How to choose an option from a dropdown without knowing the supplier's names:
    //  - by position in the list: FIRST, SECOND, ... TENTH, LAST (test data may also use "1", "2", ...)
    //  - by price (meals/baggage only -- bundle options carry no price): CHEAPEST_PAID, MOST_EXPENSIVE, FREE
    public enum Pick {
        FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHTH, NINTH, TENTH,
        LAST, CHEAPEST_PAID, MOST_EXPENSIVE, FREE;

        // 1-based position for FIRST..TENTH, 0 for the others.
        public int position() {
            return ordinal() <= TENTH.ordinal() ? ordinal() + 1 : 0;
        }
    }

    public static class Selection {
        public final Type type;
        public final int pax;          // 1-based passenger tab on the ancillaries step (0 for bundle)
        public final int index;        // 1-based route (bundle), segment card (meal) or journey card (baggage)
        public final String route;     // e.g. "CAI-RUH"
        public final String direction; // e.g. "Onward · Leg 1" (empty for bundle)
        public final String name;      // option label: bundle name, meal code, baggage description
        public final double price;     // total price this selection adds to the booking
        public final String paxName;   // "First Last" as shown on the ancillaries step (empty for bundle)
        public final String description;    // meal description, e.g. "BB Meal" (empty otherwise)
        public final List<String> services; // bundle services, e.g. "30KG Check-in Baggage (PDBG)"

        Selection(Type type, int pax, int index, String route, String direction, String name, double price,
                  String paxName, String description, List<String> services) {
            this.type = type;
            this.pax = pax;
            this.index = index;
            this.route = route;
            this.direction = direction;
            this.name = name;
            this.price = price;
            this.paxName = paxName;
            this.description = description;
            this.services = services;
        }

        @Override
        public String toString() {
            return type + " pax=" + pax + (paxName.isEmpty() ? "" : " (" + paxName + ")") + " #" + index + " " + route
                    + " [" + direction + "] " + name + (description.isEmpty() ? "" : " - " + description) + " = " + price
                    + (services.isEmpty() ? "" : " " + services);
        }
    }

    private final List<String> selectionIssues = new ArrayList<>();

    private final Map<String, Selection> selections = new LinkedHashMap<>();
    private double baseTotal = -1;      // base fare + taxes as the system rounds them (Defaults.Rounding)
    private double exactBaseTotal = -1; // the same, unrounded
    private int adults, children, infants;
    private int journeyCount;
    private Map<String, Double> baseFareRows = new LinkedHashMap<>();
    private Money walletTopUpBefore;
    private int bundleRouteCount;
    private boolean ancillariesOffered;
    private final List<String> notOffered = new ArrayList<>();
    private List<String> ancillaryPaxNames = new ArrayList<>();
    // ADULT / CHILD per ancillaries tab, in passenger-form order (infants never get a tab).
    private final List<String> paxTypes = new ArrayList<>();
    private List<String> bundleRoutes = new ArrayList<>();
    private List<String> searchRoutes = new ArrayList<>();

    public List<Selection> getSelections() {
        return new ArrayList<>(selections.values());
    }

    public double expectedTotal(Type type) {
        return selections.values().stream().filter(s -> s.type == type).mapToDouble(s -> s.price).sum();
    }

    public double expectedAncillariesTotal() {
        return expectedTotal(Type.BUNDLE) + expectedTotal(Type.MEAL) + expectedTotal(Type.BAGGAGE);
    }

    // Passenger page "Total Amount" only: base fare and taxes rounded per Defaults.Rounding, plus ancillaries.
    public double expectedGrandTotal() {
        return baseTotal + expectedAncillariesTotal();
    }

    // What is actually charged: exact base fare + taxes + ancillaries. Used for Net Payable and for
    // everything after booking -- confirmation, e-ticket, itinerary, wallet (confirmed
    // live: base 13,775.5 + meal 600.2 -> confirmation Total 14,375.7, not the rounded 14,376.2).
    public double expectedNetPayable() {
        return exactBaseTotal + expectedAncillariesTotal();
    }

    public double getBaseTotal() {
        return baseTotal;
    }

    private void record(Selection s) {
        String key = s.type + "|" + s.pax + "|" + s.index;
        selections.put(key, s);
        System.out.println("Ancillary selected: " + s);
    }

    private void forget(Type type, int pax, int index) {
        selections.remove(type + "|" + pax + "|" + index);
    }

    // ---------------------------------------------------------------- locators: search

    private final By calendarButtons = By.xpath("//span[@class='p-button-icon pi pi-calendar']");
    private final By journeyDateInput = By.xpath("//input[@placeholder='DD/MM/YYYY *']");
    private final By datePickerMonth = By.xpath("//button[contains(@class,'p-datepicker-month')]");
    private final By datePickerYear = By.xpath("//button[contains(@class,'p-datepicker-year')]");
    private final By datePickerNext = By.xpath("//button[contains(@class,'p-datepicker-next')]");

    private By datePickerDay(String day) {
        // The calendar also renders the adjacent months' days (dimmed) -- only the enabled day of the shown month.
        return By.xpath("(//td[not(contains(@class,'p-datepicker-other-month'))]"
                + "/span[not(contains(@class,'p-disabled')) and normalize-space()='" + day + "'])[1]");
    }

    // ---------------------------------------------------------------- locators: passenger step

    // Portal: <p-dropdown> wrapping a readonly input with id title_<n> (see Portal PaxDetailsPage);
    // Admin's label/sibling shape is kept as a fallback.
    private By titleDropdown(int index) {
        return By.xpath("(//p-dropdown[.//input[@id='title_" + index + "']]"
                + " | //label[@for='title_" + index + "']/following-sibling::p-dropdown)[1]");
    }

    private By paxType(int index) {
        return By.xpath("(//span[@class='pax-logo ng-star-inserted'])[" + index + "]");
    }

    private By dropdownOption(String value) {
        return By.xpath("//li[@aria-label='" + value + "']");
    }

    private By dropdownOptionByIndex(int index) {
        return By.xpath("(//li[@role='option'])[" + index + "]");
    }

    private final By paxFormExpandAll = By.xpath("//ndc-pax-details-form//button[.//span[normalize-space()='Expand All']]");
    private final By assignedToDropdowns = By.xpath("//p-dropdown[@formcontrolname='assignedTo']");
    private final By termsCheckbox = By.xpath("//p-checkbox[@inputid='termsAndConditions']//div[contains(@class,'p-checkbox-box')]");
    // Portal PaxDetailsPage clicks the last checkbox on the page for Terms & Conditions.
    private final By portalTermsCheckbox = By.xpath("(//div[contains(@class,'p-checkbox-box')])[last()]");
    // Portal: the step button is a plain <button class="... step-btn step-btn--primary ...">.
    private final By nextButtons = By.xpath("//p-button[@label='Next']/button"
            + " | //button[contains(@class,'step-btn--primary')][not(contains(normalize-space(.),'Book'))][not(contains(normalize-space(.),'Hold'))]");
    private final By holdButton = By.xpath("//p-button[@label='Hold']/button");
    private final By holdNotAvailableMsg = By.xpath("//ndc-inline-notification//span[contains(normalize-space(.),'Hold is not available')]");
    private final By ancillariesStepActive = By.xpath("//ndc-ancillaries//div[contains(@class,'ancillaries-preference-section')]");

    // ---------------------------------------------------------------- locators: bundles

    private final By bundleTabs = By.xpath("//ndc-bundles//p-accordiontab");
    private final By bundleRequiredError = By.xpath("//ndc-bundles//small[contains(@class,'p-error')][contains(normalize-space(.),'Bundle selection is required')]");

    private String bundleTab(int route) {
        return "(//ndc-bundles//p-accordiontab)[" + route + "]";
    }

    // ---------------------------------------------------------------- locators: meals / baggage

    private final By ancillariesExpandAll = By.xpath("//ndc-ancillaries//button[.//span[normalize-space()='Expand All']]");
    private final By ancillaryPaxTabs = By.xpath("//ndc-ancillaries//p-accordiontab");
    private static final String SSR_CARD = "div[contains(concat(' ',normalize-space(@class),' '),' ssr-card ')]";

    private String paxTab(int pax) {
        return "(//ndc-ancillaries//p-accordiontab)[" + pax + "]";
    }

    private String mealCard(int pax, int segment) {
        return "(" + paxTab(pax) + "//ndc-meal-selector//" + SSR_CARD + ")[" + segment + "]";
    }

    private String baggageCard(int pax, int journey) {
        return "(" + paxTab(pax) + "//ndc-baggage-selector//" + SSR_CARD + ")[" + journey + "]";
    }

    private final By openOptions = By.xpath("//li[@role='option']");

    // ---------------------------------------------------------------- locators: fare breakdown (pax page)

    private final By fareBreakdownTotal = By.xpath("//ndc-fare-breakdown//div[contains(@class,'detail-row')][.//span[normalize-space()='Total Amount:']]//span[contains(@class,'value')]");
    private final By fareBreakdownSsrTotal = By.xpath("//ndc-fare-breakdown//div[contains(@class,'ssr-main-header')]//span[contains(@class,'value')]");
    private final By netPayable = By.xpath("//span[contains(normalize-space(.),'Net Payable Amount:')]");
    private final By fareBreakdownFareRows = By.xpath("//ndc-fare-breakdown//div[contains(@class,'detail-row')][not(contains(@class,'ssr-main-header'))]");
    // Portal sidebar wallet: <h1 class="balance-amount"> 974,274.400 <span class="currency">EGP</span></h1>,
    // rendered twice (a hidden duplicate) -- the displayed one is picked in readWalletBalance().
    private final By walletBalance = By.xpath("//*[contains(concat(' ',normalize-space(@class),' '),' balance-amount ')]");

    private By fareBreakdownSsrSub(String label) {
        return By.xpath("//ndc-fare-breakdown//span[normalize-space(.)='" + label + "']/following::span[contains(@class,'ssr-sub-value')][1]");
    }

    // ---------------------------------------------------------------- locators: confirmation / e-ticket / itinerary

    private final By confirmationTotal = By.xpath("//ndc-fare-details//div[contains(@class,'total-value')]");
    private final By eTicketButton = By.xpath("//*[self::button or self::a][contains(normalize-space(.),'E-Ticket')]");
    private final By eTicketTotal = By.xpath("//span[contains(@class,'total-amount')]");
    // Portal My Bookings: Order Id link in the 1st column (see Portal RefundPage); Admin's href shape first.
    private final By itineraryLink = By.xpath("(//a[contains(@href,'booking-details')] | (//td[@class='ng-star-inserted'])[1]//a)[1]");
    // Portal itinerary tabs are "Itinerary Details" and "Costing" (Admin: "Costing & Payment").
    private final By costingTab = By.xpath("//a[@role='tab'][normalize-space(.)='Costing' or contains(normalize-space(.),'Costing & Payment')]");
    private final By itineraryDetailsTab = By.xpath("//a[contains(normalize-space(.),'Itinerary Details')]");
    private final By itineraryAncillaryPax = By.xpath("//ndc-itinerary-ancillaries-view//p-accordiontab");

    private By confirmationFareItem(String label) {
        return By.xpath("//ndc-fare-details//div[contains(@class,'fare-item')][.//span[normalize-space(.)='" + label + "']]//div[contains(@class,'fare-value')]");
    }

    private By eTicketFareLine(String label) {
        return By.xpath("//div[contains(@class,'fare-line')][span[1][normalize-space(.)='" + label + "']]//span[contains(@class,'fare-amount')]");
    }

    // Right-hand sidebar of the itinerary: <ndc-costing-card> ... <span>Total Booking Amount:</span><span class="amount">..</span>
    private By sidebarCosting(String label) {
        return By.xpath("(//ndc-costing-card//*[normalize-space(text())='" + label + "']/following-sibling::*[1])[1]");
    }

    private By costingValue(String label) {
        return By.xpath("//p[normalize-space(.)='" + label + "']/following-sibling::p[1]");
    }

    // ================================================================= scenario runner (Portal_Ancillaries.json)
    //
    // Every scenario in Portal_Ancillaries.json is one test run (see "_HowToUse" in the JSON). The test
    // class only feeds scenarios to runScenario(); everything else lives here.
    //
    // Command-line options (all optional):
    //   -Dscenario="OW*,RT 1A+1C*"   names, * wildcard, comma-separated
    //   -Dtags=smoke,dryrun          any of these tags
    //   -Dsupplier="Fly Nas NDC"     overrides the supplier of every scenario
    //   -Dflow=NO_BOOKING            dry run: passenger-page checks only, nothing is booked

    public static final String DATA_FILE = "src/test/resources/testDataFiles/Portal_Ancillaries.json";

    public enum Trip { ONE_WAY, ROUND_TRIP, MULTI_CITY }

    public enum Flow { BOOK, HOLD_THEN_PAY, NO_BOOKING }

    private JsonNode defaults;

    // Enabled scenarios matching -Dscenario / -Dtags, as {name, scenario} rows for a TestNG DataProvider.
    public static Object[][] loadScenarios() throws IOException {
        JsonNode root = new ObjectMapper().readTree(new File(DATA_FILE));
        List<String> namePatterns = csv(System.getProperty("scenario"));
        List<String> tags = csv(System.getProperty("tags"));
        List<Object[]> rows = new ArrayList<>();
        for (JsonNode s : root.path("Scenarios")) {
            String name = s.path("Name").asText("(unnamed)");
            if (!s.path("Enabled").asBoolean(true)) continue;
            if (!namePatterns.isEmpty() && namePatterns.stream().noneMatch(p -> wildcard(p, name))) continue;
            if (!tags.isEmpty()) {
                List<String> own = new ArrayList<>();
                s.path("Tags").forEach(t -> own.add(t.asText().toLowerCase(Locale.ROOT)));
                if (tags.stream().noneMatch(t -> own.contains(t.toLowerCase(Locale.ROOT)))) continue;
            }
            rows.add(new Object[]{name, s});
        }
        if (rows.isEmpty()) {
            System.out.println("No scenario matches -Dscenario=" + namePatterns + " -Dtags=" + tags);
        }
        return rows.toArray(new Object[0][]);
    }

    // One scenario by its exact "Name", for the per-scenario @Test methods in Ancillaries_TC.
    // Disabled scenarios and ones filtered out by -Dscenario / -Dtags are skipped; a name that isn't
    // in the file fails, because the test method and the JSON are out of sync.
    public static JsonNode findScenario(String name) throws IOException {
        for (Object[] row : loadScenarios()) {
            if (name.equals(row[0])) return (JsonNode) row[1];
        }
        for (JsonNode s : new ObjectMapper().readTree(new File(DATA_FILE)).path("Scenarios")) {
            if (name.equals(s.path("Name").asText())) {
                throw new SkipException("Scenario \"" + name + "\" is disabled or filtered out by -Dscenario / -Dtags");
            }
        }
        throw new IllegalArgumentException("No scenario named \"" + name + "\" in " + DATA_FILE);
    }

    private static List<String> csv(String value) {
        List<String> out = new ArrayList<>();
        if (value == null || value.isBlank()) return out;
        for (String v : value.split(",")) if (!v.isBlank()) out.add(v.trim());
        return out;
    }

    private static boolean wildcard(String pattern, String text) {
        String regex = "\\Q" + pattern.toLowerCase(Locale.ROOT).replace("*", "\\E.*\\Q") + "\\E";
        return text.toLowerCase(Locale.ROOT).matches(regex);
    }

    // Runs one scenario end to end. Checks are collected (soft) and reported together at the end, with
    // hard checkpoints where continuing would cost money or make no sense:
    //   - money gate on the passenger page before Book / Hold (nothing is booked at a wrong price)
    //   - booking / hold actually created, pay-after-hold confirmed
    //   - confirmation Total and itinerary Amount Received (what was really charged)
    public void runScenario(String name, JsonNode scenario) throws Exception {
        System.out.println("===== Scenario: " + name + " =====");
        defaults = new ObjectMapper().readTree(new File(DATA_FILE)).path("Defaults");
        Checks soft = new Checks();
        // -Dflow=NO_BOOKING turns any scenario into a dry run (passenger-page checks only, no wallet cost).
        Flow flow = Flow.valueOf(System.getProperty("flow", scenario.path("Flow").asText("BOOK")).trim().toUpperCase());
        List<String> expect = new ArrayList<>();
        scenario.path("Expect").forEach(e -> expect.add(e.asText().trim().toUpperCase()));

        // Wallet balance before anything is booked or held (also covers hold -> pay: the balance after
        // payment must be this minus the full total, whatever the hold itself did).
        if (flow != Flow.NO_BOOKING) captureWalletBeforePayment();
        startBooking(scenario);

        if (expect.contains("BUNDLE_REQUIRED")) {
            verifyBundleRequired(soft);
            soft.assertAll();
            return;
        }

        List<Rule> bundleRules = rules(scenario.path("Bundle"), Type.BUNDLE);
        List<Rule> ssrRules = new ArrayList<>(rules(scenario.path("Meals"), Type.MEAL));
        ssrRules.addAll(rules(scenario.path("Baggage"), Type.BAGGAGE));

        applyBundleRules(bundleRules).goToAncillariesStep();

        if (expect.contains("INFANT_HAS_NO_TAB")) {
            if (!hasAncillaries()) throw new SkipException("This flight/supplier has no meals/baggage");
            soft.hard(getAncillaryPaxCount() == adults + children,
                    "Infants must not get an ancillaries tab: tabs " + getAncillaryPaxCount() + ", adults+children " + (adults + children));
            assertAncillaryStructure(soft);
        }

        applySsrRules(ssrRules);
        applySsrRules(typedRules(scenario.path("ChangeBeforeBooking")));

        List<Rule> clears = typedRules(scenario.path("Clear"));
        if (expect.contains("CLEAR_RESTORES_PRICE")) {
            boolean anySsr = getSelections().stream().anyMatch(x -> x.type != Type.BUNDLE);
            if (!anySsr) throw new SkipException("Nothing to clear: this flight/supplier offered none of the requested meals/baggage");
            verifyPassengerPage(soft);
            if (clears.isEmpty()) clearAllSsr(); else applyClearRules(clears);
            moneyGate("after clearing, the fare breakdown must drop the cleared prices");
        } else if (!clears.isEmpty()) {
            applyClearRules(clears);
        }

        if (!getNotOffered().isEmpty()) {
            System.out.println("Requested but not offered on this flight: " + getNotOffered());
        }

        switch (flow) {
            case NO_BOOKING:
                verifyPassengerPage(soft);
                break;
            case BOOK:
                skipIfNothingRequestedIsOffered(name, bundleRules, ssrRules);
                bookAndVerifyAllPages(soft);
                break;
            case HOLD_THEN_PAY:
                skipIfNothingRequestedIsOffered(name, bundleRules, ssrRules);
                holdPayAndVerifyAllPages(soft);
                break;
        }
        soft.assertAll();
    }

    // "Meals": "FIRST"  or  "Meals": [ {"Passenger": .., "Journey": .., "Segment": .., "Pick": ..}, ... ]
    private static List<Rule> rules(JsonNode node, Type type) {
        List<Rule> out = new ArrayList<>();
        if (node.isMissingNode() || node.isNull()) return out;
        if (node.isValueNode()) {
            out.add(new Rule(type, "ALL", "ALL", "ALL", node.asText()));
            return out;
        }
        for (JsonNode r : node) {
            out.add(new Rule(type, jsonText(r, "Passenger"), jsonText(r, "Journey"), jsonText(r, "Segment"), jsonText(r, "Pick")));
        }
        return out;
    }

    // ChangeBeforeBooking / Clear: each rule names its Type (MEAL | BAGGAGE).
    private static List<Rule> typedRules(JsonNode node) {
        List<Rule> out = new ArrayList<>();
        for (JsonNode r : node) {
            String type = jsonText(r, "Type");
            if (type == null) throw new IllegalArgumentException("Rule needs \"Type\": \"MEAL\" or \"BAGGAGE\": " + r);
            out.add(new Rule(Type.valueOf(type.trim().toUpperCase()), jsonText(r, "Passenger"), jsonText(r, "Journey"), jsonText(r, "Segment"), jsonText(r, "Pick")));
        }
        return out;
    }

    private static String jsonText(JsonNode node, String field) {
        JsonNode v = node.get(field);
        return v == null || v.isNull() ? null : v.asText();
    }

    // Routes and dates live in one place: Defaults.Routes. A scenario picks one with "Trip"
    // (ONE_WAY / ROUND_TRIP / MULTI_CITY -> the route of that name) or "Route" (any other name in
    // Defaults.Routes, e.g. "MC_3_LEGS"). Missing both = ONE_WAY.
    private JsonNode routeFor(JsonNode scenario) {
        String name = scenario.has("Route") ? scenario.path("Route").asText()
                : scenario.path("Trip").asText("ONE_WAY");
        JsonNode route = defaults.path("Routes").path(name.trim());
        if (route.isMissingNode()) {
            List<String> known = new ArrayList<>();
            defaults.path("Routes").fieldNames().forEachRemaining(known::add);
            throw new IllegalArgumentException("Route '" + name + "' is not in Defaults.Routes " + known);
        }
        return route;
    }

    // Search -> supplier's flight -> passenger details filled -> base fare captured.
    private void startBooking(JsonNode scenario) throws InterruptedException {
        JsonNode search = routeFor(scenario);
        JsonNode pax = scenario.has("Passengers") ? scenario.path("Passengers") : defaults.path("Passengers");
        JsonNode details = defaults.path("Passenger");
        // Trip: the route's own "Trip" if it has one, else the scenario's "Trip", else the route's name.
        String tripName = search.has("Trip") ? search.path("Trip").asText()
                : scenario.has("Trip") ? scenario.path("Trip").asText()
                : scenario.path("Route").asText("ONE_WAY");
        Trip trip = Trip.valueOf(tripName.trim().toUpperCase());
        int adultCount = pax.path("Adults").asInt(1), childCount = pax.path("Children").asInt(0), infantCount = pax.path("Infants").asInt(0);

        List<String[]> legs = new ArrayList<>(); // {From, To, Date}
        List<String> routes = new ArrayList<>();
        for (JsonNode j : search.path("Journeys")) {
            legs.add(new String[]{j.path("From").asText(), j.path("To").asText(), j.path("Date").asText()});
            routes.add(j.path("From").asText() + "-" + j.path("To").asText());
        }
        int needed = trip == Trip.ONE_WAY ? 1 : 2;
        if (legs.size() < needed) {
            throw new IllegalArgumentException(trip + " needs at least " + needed + " Journeys but the scenario has " + legs.size());
        }

        SearchBookingBranch searchPage = new SearchBookingBranch(driver);
        new Booking_Common(driver).clickBookFlight(); // Portal: no branch to select
        switch (trip) {
            case ONE_WAY:
                searchPage.AddStartingFrom(legs.get(0)[0]).AddGoingTo(legs.get(0)[1]);
                selectTravelDate(1, legs.get(0)[2]);
                break;
            case ROUND_TRIP:
                searchPage.SelectRoundTrip().AddStartingFromRoundTrip(legs.get(0)[0]).AddGoingToRoundTrip(legs.get(0)[1]);
                selectTravelDate(1, legs.get(0)[2]);
                selectTravelDate(2, legs.get(1)[2]);
                break;
            case MULTI_CITY:
                searchPage.SelectMultiCity();
                fillMultiCityLegs(legs);
                break;
        }
        searchPage.passengersDropDown()
                .SelectNumberOfAdult(adultCount)
                .SelectNumberOfChildren(childCount)
                .SelectNumberOfInfant(infantCount)
                .clickOnSearchButton();
        if (!waitForSearchResults()) {
            throw new SkipException("No flights found for " + trip + " " + routes + " -- update Defaults.Routes in Portal_Ancillaries.json");
        }
        String supplier = System.getProperty("supplier",
                // route's own Supplier when filled in, else Defaults.Supplier (empty = first flight)
                !search.path("Supplier").asText("").isBlank() ? search.path("Supplier").asText() : defaults.path("Supplier").asText(""));
        if (!bookFlightOfSupplier(supplier)) {
            String firstCard = textOf(By.xpath("(" + BOOK_FLIGHT_BUTTON + ")[1]/ancestor::div[contains(@class,'card')][1]"));
            throw new SkipException("No flight from supplier '" + supplier + "' in the results (first result card shows: ["
                    + firstCard.substring(0, Math.min(400, firstCard.length())) + "])");
        }
        setJourneyCount(trip == Trip.ONE_WAY ? 1 : legs.size()).setSearchRoutes(routes);
        reachPassengerForm();
        fillPassengers(adultCount, childCount, infantCount,
                details.path("Title").asText(),
                details.path("AdultDateOfBirth").asText(),
                details.path("ChildDateOfBirth").asText(),
                details.path("InfantDateOfBirth").asText(),
                details.path("Email").asText(),
                details.path("Phone").asText(),
                details.path("ExpiryDate").asText(),
                details.path("Nationality").asText())
                .acceptTerms()
                .captureBaseFare();
    }

    private void verifyBundleRequired(Checks soft) throws InterruptedException {
        if (!hasBundles()) throw new SkipException("This flight/supplier has no bundles");
        clickNext();
        if (getBundleErrorText().isEmpty() && isOnAncillariesStep()) {
            throw new SkipException("Bundle is optional for this flight/supplier (Next went to the ancillaries step)");
        }
        soft.hard(!isOnAncillariesStep(), "Must not reach the ancillaries step without a bundle");
        soft.equal(Severity.MAJOR, getBundleErrorText(), defaults.path("Messages").path("BundleRequired").asText(),
                "Bundle validation message");
    }

    private void verifyPassengerPage(Checks soft) {
        assertAncillaryStructure(soft).assertPassengerPageSelections(soft).assertPassengerPageFareBreakdown(soft);
    }

    // HARD gate: the passenger page must show exactly the expected money before anything is booked,
    // held or paid -- otherwise the run stops here instead of spending wallet money on a booking
    // that is already known to be wrong.
    private void moneyGate(String when) {
        Checks gate = new Checks();
        assertPassengerPageFareBreakdown(gate);
        if (gate.criticalFailures() > 0) {
            System.out.println("MONEY GATE FAILED (" + when + ") -- nothing will be booked");
            gate.assertAll();
        }
    }

    // Don't spend a booking when the flight offers none of what the scenario asked for.
    private void skipIfNothingRequestedIsOffered(String name, List<Rule> bundleRules, List<Rule> ssrRules) {
        boolean requested = bundleRules.stream().anyMatch(r -> !isNone(r.pick))
                || ssrRules.stream().anyMatch(r -> !isNone(r.pick));
        if (requested && getSelections().isEmpty()) {
            throw new SkipException(name + ": this flight/supplier offers none of the requested ancillaries " + getNotOffered());
        }
    }

    // Portal "My Bookings": no Flight/Booking module tabs and no branch (see Portal RefundPage).
    private void openSearchBooking(String bookingReference) throws InterruptedException {
        new Booking_Common(driver).clickMyBookings();
        new SearchBooking_Page(driver)
                .SelectCurrentStartDate()
                .SelectCurrentEndDate()
                .EnterBookingReference(bookingReference)
                .ClickSearch();
        Thread.sleep(2000);
    }

    // Book (wallet) -> confirmation -> e-ticket -> itinerary -> wallet balance.
    private void bookAndVerifyAllPages(Checks soft) throws Exception {
        verifyPassengerPage(soft);
        moneyGate("before Book");
        new PaxDetailsPage(driver).payAndBook().AssertThatTicketIsHoldSuccessfully();
        String bookingReference = new SearchBookingBranch(driver).GetBookingReference();
        System.out.println("Booking reference: " + bookingReference + " | trip id: " + getTripIdFromUrl());

        assertBookingCreated(soft, "Confirmed");
        assertConfirmationPage(soft).assertETicket(soft);

        openSearchBooking(bookingReference);
        openItineraryFromSearchResults().assertItinerary(soft).assertItineraryAmountReceived(soft);
        assertWalletDeducted(soft, bookingReference);
    }

    // Hold -> confirmation + itinerary (on hold) -> pay after hold (from the My Bookings row) ->
    // confirmation -> e-ticket -> itinerary -> wallet balance. The held total must be exactly what
    // is paid and received.
    private void holdPayAndVerifyAllPages(Checks soft) throws Exception {
        verifyPassengerPage(soft);
        skipIfHoldNotAvailable();
        moneyGate("before Hold");
        new PaxDetailsPage(driver).clickOnHold().AssertThatTicketIsHoldSuccessfully();
        SearchBookingBranch searchBookingBranch = new SearchBookingBranch(driver);
        String bookingReference = searchBookingBranch.GetBookingReference();
        System.out.println("Held booking reference: " + bookingReference);

        // Held booking keeps every ancillary price.
        assertBookingCreated(soft, null);
        assertConfirmationPage(soft);
        Money heldTotal = sumMoney(confirmationTotal);
        openSearchBooking(bookingReference);
        openItineraryFromSearchResults().assertItinerary(soft);

        // Pay after hold continues with the same ancillaries -- the amount paid must include them.
        // Portal pays straight from the My Bookings results row (no Lock / Take Control / Confirm To Pay).
        openSearchBooking(bookingReference);
        searchBookingBranch.PayAfterHoldFlow();
        assertPayAfterHoldConfirmed(soft);

        if (isETicketAvailable()) {
            assertConfirmationPage(soft).assertETicket(soft);
            assertMoney(soft, sumMoney(confirmationTotal), heldTotal.value, "Hold -> paid: confirmation Total after payment = total on hold");
        }
        openSearchBooking(bookingReference);
        openItineraryFromSearchResults().assertItinerary(soft).assertItineraryAmountReceived(soft);
        assertWalletDeducted(soft, bookingReference);
    }

    // ================================================================= search helpers

    // Opens calendar #index (1-based; one-way has a plain input instead) and picks day/month/year by
    // paging forward with the "next" arrow -- avoids the year/month picker, which renders past months
    // as disabled and intercepts the click.
    public Ancillaries_Page selectTravelDate(int index, String day, String month, String year) throws InterruptedException {
        List<WebElement> icons = driver.getDriver().findElements(calendarButtons);
        if (icons.size() >= index) {
            driver.element().click(By.xpath("(//span[@class='p-button-icon pi pi-calendar'])[" + index + "]"));
        } else {
            driver.element().click(journeyDateInput);
        }
        for (int i = 0; i < 24 && !calendarShows(month, year); i++) {
            driver.element().click(datePickerNext);
        }
        driver.element().click(datePickerDay(day));
        Thread.sleep(1000);
        return this;
    }

    private static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    // Same, from a "DD/MM/YYYY" date (test data format).
    public Ancillaries_Page selectTravelDate(int index, String date) throws InterruptedException {
        String[] p = date.trim().split("/");
        if (p.length != 3) throw new IllegalArgumentException("Date must be DD/MM/YYYY but was '" + date + "'");
        String day = String.valueOf(Integer.parseInt(p[0]));          // the calendar shows "5", not "05"
        String month = MONTHS[Integer.parseInt(p[1]) - 1];
        return selectTravelDate(index, day, month, p[2]);
    }

    // Multi-city: one route-row per leg (From / To / date); "Add flight" adds rows beyond the first two.
    // A leg's From is pre-filled with the previous leg's To -- it is only changed when it differs.
    public Ancillaries_Page fillMultiCityLegs(List<String[]> legs) throws InterruptedException {
        By rows = By.xpath("//div[contains(@class,'route-row') and contains(@class,'multi-city')]");
        for (int i = 1; i <= legs.size(); i++) {
            while (driver.getDriver().findElements(rows).size() < i) {
                driver.element().click(By.xpath("//button[contains(@class,'add-route-btn')]"));
                Thread.sleep(800);
            }
            String row = "(//div[contains(@class,'route-row') and contains(@class,'multi-city')])[" + i + "]";
            String[] leg = legs.get(i - 1); // {from, to, date}
            chooseAirport(row + "//p-dropdown[@formcontrolname='origins']", leg[0]);
            chooseAirport(row + "//p-dropdown[@formcontrolname='destination']", leg[1]);
            driver.element().click(By.xpath(row + "//button[contains(@class,'p-datepicker-trigger')]"));
            pickOpenCalendarDate(leg[2]);
        }
        return this;
    }

    private void chooseAirport(String dropdown, String iata) throws InterruptedException {
        String shown = textOf(By.xpath(dropdown + "//span[contains(@class,'p-dropdown-label') and not(contains(@class,'p-placeholder'))]"));
        if (shown.contains(iata)) return; // already pre-filled with this airport
        driver.element().click(By.xpath(dropdown + "//div[@role='button']"));
        driver.element().type(By.xpath("//input[contains(@class,'p-dropdown-filter')]"), iata);
        Thread.sleep(800);
        driver.element().click(By.xpath("//li[@aria-label=" + xq(iata) + "]"));
        Thread.sleep(500);
    }

    private void pickOpenCalendarDate(String date) throws InterruptedException {
        String[] p = date.trim().split("/");
        String month = MONTHS[Integer.parseInt(p[1]) - 1];
        for (int i = 0; i < 24 && !calendarShows(month, p[2]); i++) {
            driver.element().click(datePickerNext);
        }
        driver.element().click(datePickerDay(String.valueOf(Integer.parseInt(p[0]))));
        Thread.sleep(1000);
    }

    private boolean calendarShows(String month, String year) {
        List<WebElement> m = driver.getDriver().findElements(datePickerMonth);
        List<WebElement> y = driver.getDriver().findElements(datePickerYear);
        if (m.isEmpty() || y.isEmpty()) return true; // no header to page through; trust the shown month
        String shownMonth = m.get(0).getText().trim().toLowerCase();
        return shownMonth.startsWith(month.trim().toLowerCase().substring(0, 3))
                && y.get(0).getText().trim().equals(year.trim());
    }

    // ================================================================= passenger step

    public Ancillaries_Page fillPassengers(int adults, int children, int infants,
                                           String title, String adultDob, String childDob, String infantDob,
                                           String email, String phone, String documentExpiry, String nationality) {
        this.adults = adults;
        this.children = children;
        this.infants = infants;
        if (!driver.getDriver().findElements(paxFormExpandAll).isEmpty()) {
            driver.element().click(paxFormExpandAll);
        }
        int total = adults + children + infants;
        for (int i = 0; i < total; i++) {
            String paxText = driver.element().getText(paxType(i + 1));

            driver.element().click(titleDropdown(i));
            driver.element().click(dropdownOption(title));
            driver.element().type(By.id("firstName_" + i), FakerSingleton.PassengerFactory.firstName());
            driver.element().type(By.id("lastName_" + i), FakerSingleton.PassengerFactory.lastName());
            driver.element().type(By.id("document_" + i), FakerSingleton.PassengerFactory.documentNumber());
            // Some branches pre-fill and lock Email / Phone from the branch profile.
            typeIfEditable(By.id("email_" + i), email);
            typeIfEditable(By.id("phone_" + i), phone);
            driver.element().type(By.id("docExpiry_" + i), documentExpiry);
            driver.element().click(By.xpath("//label[@for='nationality_" + i + "']/following::p-dropdown[1]//div[@role='button']"));
            driver.element().click(dropdownOption(nationality));

            if (!paxText.contains("Infant")) paxTypes.add(paxText.contains("Child") ? "CHILD" : "ADULT");
            if (paxText.contains("Child")) {
                driver.element().type(By.id("dob_" + i), childDob);
            } else if (paxText.contains("Infant")) {
                driver.element().type(By.id("dob_" + i), infantDob);
            } else {
                driver.element().type(By.id("dob_" + i), adultDob);
            }
        }
        // Each infant must be assigned to an adult (Infant1 -> option 1, ...).
        int assignedTo = driver.getDriver().findElements(assignedToDropdowns).size();
        for (int i = 1; i <= assignedTo; i++) {
            driver.element().click(By.xpath("(//p-dropdown[@formcontrolname='assignedTo'])[" + i + "]"));
            driver.element().click(dropdownOptionByIndex(i));
        }
        return this;
    }

    // Types only when the field accepts input; a disabled / read-only (pre-filled) field is left as is.
    private void typeIfEditable(By field, String value) {
        List<WebElement> found = driver.getDriver().findElements(field);
        if (found.isEmpty()) return;
        WebElement e = found.get(0);
        boolean locked = !e.isEnabled() || e.getAttribute("readonly") != null || e.getAttribute("disabled") != null;
        if (locked) {
            System.out.println("Pre-filled / locked, left as is: " + field + " = '" + e.getAttribute("value") + "'");
            return;
        }
        try {
            driver.element().type(field, value);
        } catch (AssertionError notInteractable) {
            System.out.println("Not editable, left as is: " + field + " = '" + e.getAttribute("value") + "'");
        }
    }

    public Ancillaries_Page acceptTerms() {
        By terms = driver.getDriver().findElements(termsCheckbox).isEmpty() ? portalTermsCheckbox : termsCheckbox;
        List<WebElement> box = driver.getDriver().findElements(terms);
        if (!box.isEmpty() && !box.get(0).getAttribute("class").contains("p-highlight")) {
            driver.element().click(terms);
        }
        return this;
    }

    // Total Amount and every Base fare / Tax & Fees row of the side fare breakdown before any
    // ancillary is chosen -- ancillaries must only add their own lines, never change these.
    public Ancillaries_Page captureBaseFare() {
        baseFareRows = readFareRows();
        // The system rounds the Base fare total and the Taxes total (each summed over all passenger
        // rows) according to Defaults.Rounding -- UP by default: 12,781.8 -> 12,782. Ancillaries are
        // added unrounded. The exact (unrounded) base is kept to recognise a Net Payable shown unrounded.
        if (baseFareRows.isEmpty()) {
            baseTotal = readMoney(fareBreakdownTotal).value;
            exactBaseTotal = baseTotal;
        } else {
            exactBaseTotal = rawFareSum("Base fare") + rawFareSum("Tax");
            baseTotal = baseFareSum("Base fare") + baseFareSum("Tax");
        }
        System.out.println("Base total (no ancillaries): " + baseTotal + " (exact " + exactBaseTotal + ", rounding base "
                + roundingFor("BaseFare") + " / taxes " + roundingFor("Taxes") + ") | fare rows: " + baseFareRows);
        return this;
    }

    // "Base fare: (1 Adult)" -> 1005.0, "Tax & Fees: (1 Child)" -> 4089.0, ...
    private Map<String, Double> readFareRows() {
        Map<String, Double> rows = new LinkedHashMap<>();
        for (WebElement row : driver.getDriver().findElements(fareBreakdownFareRows)) {
            String label = clean(row.findElements(By.xpath(".//span[contains(@class,'label')]")).isEmpty() ? ""
                    : row.findElement(By.xpath(".//span[contains(@class,'label')]")).getAttribute("textContent"));
            if (label.startsWith("Base fare") || label.startsWith("Tax")) {
                rows.put(label, parseAmount(row.findElement(By.xpath(".//span[contains(@class,'value')]")).getAttribute("textContent")));
            }
        }
        return rows;
    }

    private double rawFareSum(String prefix) {
        return baseFareRows.entrySet().stream().filter(e -> e.getKey().startsWith(prefix)).mapToDouble(Map.Entry::getValue).sum();
    }

    // Base fare / Taxes total as the system shows and charges it (rounded per Defaults.Rounding).
    private double baseFareSum(String prefix) {
        return round(rawFareSum(prefix), roundingFor(prefix.startsWith("Tax") ? "Taxes" : "BaseFare"));
    }

    // Defaults.Rounding.BaseFare / .Taxes = UP (default) | DOWN | NEAREST | NONE.
    private String roundingFor(String component) {
        JsonNode r = defaults == null ? null : defaults.path("Rounding").path(component);
        return r == null || r.isMissingNode() || r.asText().isBlank() ? "UP" : r.asText().trim().toUpperCase();
    }

    private static double round(double amount, String mode) {
        switch (mode) {
            case "NONE": return amount;
            case "DOWN": return Math.floor(amount + 1e-9);
            case "NEAREST": return Math.floor(amount + 0.5 + 1e-9);
            default: return Math.ceil(amount - 1e-9); // UP
        }
    }

    // Portal sidebar wallet balance before the booking; after it, the balance must be exactly this
    // minus the amount charged (assertWalletDeducted). Read from a freshly loaded page: the sidebar
    // is not refreshed after a payment inside the single-page app.
    public Ancillaries_Page captureWalletBeforePayment() throws InterruptedException {
        walletTopUpBefore = readWalletBalance();
        if (walletTopUpBefore != null) {
            System.out.println("Wallet balance before payment: " + walletTopUpBefore);
        } else {
            System.out.println("Wallet balance not shown in the Portal sidebar -- wallet deduction check will be skipped");
        }
        return this;
    }

    private String portalHome() {
        return DataUtils.get("Portal_Url").replaceFirst("/auth/login.*$", "");
    }

    // Reloads the Portal home page and reads the displayed sidebar wallet balance (null when not shown).
    private Money readWalletBalance() throws InterruptedException {
        driver.getDriver().get(portalHome());
        try {
            new WebDriverWait(driver.getDriver(), Duration.ofSeconds(30))
                    .until(d -> d.findElements(walletBalance).stream().anyMatch(WebElement::isDisplayed));
        } catch (org.openqa.selenium.TimeoutException none) {
            return null;
        }
        Thread.sleep(1500); // the amount is filled in after the widget renders
        for (WebElement e : driver.getDriver().findElements(walletBalance)) {
            if (e.isDisplayed()) return money(e.getAttribute("textContent"));
        }
        return null;
    }

    // Clicks the visible "Next" (step 1 -> step 2: ancillaries).
    public Ancillaries_Page clickNext() throws InterruptedException {
        for (WebElement next : driver.getDriver().findElements(nextButtons)) {
            if (next.isDisplayed()) {
                scrollTo(next);
                next.click();
                break;
            }
        }
        Thread.sleep(3000);
        return this;
    }

    // Suppliers differ: some have no bundles, some no meals/baggage (no ancillaries step at all).
    // This never fails on a missing feature -- it records what the flight offers instead.
    public Ancillaries_Page goToAncillariesStep() throws InterruptedException {
        bundleRouteCount = getBundleRouteCount(); // bundle routes are only rendered on step 1
        bundleRoutes = new ArrayList<>();
        for (int r = 1; r <= bundleRouteCount; r++) bundleRoutes.add(getBundleRoute(r));
        if (journeyCount == 0) journeyCount = bundleRouteCount;
        boolean hasNext = driver.getDriver().findElements(nextButtons).stream().anyMatch(WebElement::isDisplayed);
        if (hasNext) clickNext();
        if (isBundleRequiredErrorDisplayed()) {
            throw new AssertionError("Bundle is required on this flight but none was selected -- set Bundle/Route<n>Bundle in the scenario");
        }
        try {
            new WebDriverWait(driver.getDriver(), Duration.ofSeconds(20))
                    .until(ExpectedConditions.presenceOfElementLocated(ancillariesStepActive));
            ancillariesOffered = true;
        } catch (org.openqa.selenium.TimeoutException none) {
            ancillariesOffered = false;
        }
        if (ancillariesOffered && !driver.getDriver().findElements(ancillariesExpandAll).isEmpty()) {
            driver.element().click(ancillariesExpandAll);
        }
        ancillaryPaxNames = ancillariesOffered ? getAncillaryPaxNames() : new ArrayList<>();
        System.out.println("Flight offers: bundles on " + bundleRouteCount + " route(s), ancillaries step: " + ancillariesOffered
                + (ancillariesOffered ? ", meals: " + offersMeals() + ", baggage: " + offersBaggage() : ""));
        return this;
    }

    // Number of journeys searched (OW 1, RT 2, MC = legs); used to check baggage is per journey.
    public Ancillaries_Page setJourneyCount(int journeys) {
        this.journeyCount = journeys;
        return this;
    }

    public boolean hasBundles() {
        return bundleRouteCount > 0 || getBundleRouteCount() > 0;
    }

    public boolean hasAncillaries() {
        return ancillariesOffered;
    }

    public boolean offersMeals() {
        return ancillariesOffered && !driver.getDriver().findElements(By.xpath("//ndc-ancillaries//ndc-meal-selector//" + SSR_CARD)).isEmpty();
    }

    public boolean offersBaggage() {
        return ancillariesOffered && !driver.getDriver().findElements(By.xpath("//ndc-ancillaries//ndc-baggage-selector//" + SSR_CARD)).isEmpty();
    }

    // Selections the scenario asked for but this flight doesn't have (e.g. "pax 2 baggage journey 2").
    public List<String> getNotOffered() {
        return new ArrayList<>(notOffered);
    }

    // Portal: the sidebar nav link is also labelled "Book Flight" (a <span> inside an <a>), so result
    // card buttons are matched as <button> only (same fix as Portal SearchBookingBranch.BookFlight_BTN).
    private static final String BOOK_FLIGHT_BUTTON = "//button[.//span[normalize-space()='Book Flight']]";

    // Books the first result whose card mentions the supplier (e.g. "Fly Nas NDC"); empty = first flight.
    public boolean bookFlightOfSupplier(String supplier) throws InterruptedException {
        By button = (supplier == null || supplier.isBlank())
                ? By.xpath("(" + BOOK_FLIGHT_BUTTON + ")[1]")
                : By.xpath("(//*[contains(normalize-space(text())," + xq(supplier.trim()) + ")]"
                    + "/ancestor::div[." + BOOK_FLIGHT_BUTTON + "][1]" + BOOK_FLIGHT_BUTTON + ")[1]");
        List<WebElement> found = driver.getDriver().findElements(button);
        if (found.isEmpty()) return false;
        driver.element().click(button);
        return true; // what comes next (price dialog / branded fares / form) is awaited in reachPassengerForm()
    }

    // After "Book Flight": a "Price Update" dialog and / or the branded-fare "Proceed" step may come
    // before the passenger form, in either order. Each is handled the moment it shows, and this stops
    // as soon as the passenger form is there (60s cap) -- no fixed waits for things that usually don't appear.
    private void reachPassengerForm() throws InterruptedException {
        By priceUpdate = By.xpath("//div[contains(@class,'p-dialog')][.//*[normalize-space()='Price Update']]");
        By proceed = By.xpath("(//button[@class='book-btn'])[1]");
        By passengerForm = By.id("firstName_0");
        long end = System.currentTimeMillis() + 60_000;
        while (System.currentTimeMillis() < end) {
            if (!driver.getDriver().findElements(priceUpdate).isEmpty()) {
                acceptPriceUpdateIfShown(1);
            } else if (driver.getDriver().findElements(proceed).stream().anyMatch(e -> e.isDisplayed() && e.isEnabled())) {
                driver.element().click(proceed);
                Thread.sleep(1000);
            } else if (driver.getDriver().findElements(passengerForm).stream().anyMatch(WebElement::isDisplayed)) {
                acceptPriceUpdateIfShown(2); // a price dialog can still pop up right after the form renders
                return;
            } else {
                Thread.sleep(500);
            }
        }
        System.out.println("Passenger form not shown within 60s after Book Flight -- continuing");
    }

    // After "Book Flight" the airline may reprice: a "Price Update - The fare has changed" dialog with
    // a confirm countdown blocks the passenger page until it is accepted.
    public Ancillaries_Page acceptPriceUpdateIfShown(int waitSeconds) {
        By dialog = By.xpath("//div[contains(@class,'p-dialog')][.//*[normalize-space()='Price Update']]");
        try {
            new WebDriverWait(driver.getDriver(), Duration.ofSeconds(waitSeconds))
                    .until(ExpectedConditions.presenceOfElementLocated(dialog));
        } catch (org.openqa.selenium.TimeoutException noDialog) {
            return this;
        }
        List<WebElement> buttons = driver.getDriver().findElements(By.xpath(
                "//div[contains(@class,'p-dialog')][.//*[normalize-space()='Price Update']]//button[not(contains(@class,'p-dialog-header-close'))]"));
        for (WebElement b : buttons) System.out.println("Price Update dialog button: [" + b.getText().trim() + "]");
        WebElement accept = null;
        for (WebElement b : buttons) {
            String t = b.getText().trim().toLowerCase();
            if (t.matches(".*(confirm|continue|proceed|accept|ok|book).*")) accept = b;
        }
        if (accept == null && !buttons.isEmpty()) accept = buttons.get(buttons.size() - 1);
        if (accept == null) throw new AssertionError("Price Update dialog has no confirm button");
        scrollTo(accept);
        accept.click();
        try { Thread.sleep(3000); } catch (InterruptedException ignored) { }
        return this;
    }

    // Waits for the flight search to finish; false when "No Flights Found". Seen live on the Portal
    // (RT / MC, Al Jazeera): the same search returns flights in one run and "No Flights Found" a
    // minute later -- so "No Flights Found" only counts once it has stayed for 15s, and the search is
    // repeated once before giving up.
    public boolean waitForSearchResults() throws InterruptedException {
        By bookFlight = By.xpath("(" + BOOK_FLIGHT_BUTTON + ")[1]");
        By noFlights = By.xpath("//*[normalize-space()='No Flights Found']");
        By searchButton = By.xpath("//button[contains(@class,'search-btn')]");
        for (int attempt = 1; attempt <= 2; attempt++) {
            long end = System.currentTimeMillis() + 90_000;
            long noFlightsSince = -1;
            while (System.currentTimeMillis() < end) {
                if (!driver.getDriver().findElements(bookFlight).isEmpty()) return true;
                if (driver.getDriver().findElements(noFlights).isEmpty()) {
                    noFlightsSince = -1;
                } else if (noFlightsSince < 0) {
                    noFlightsSince = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - noFlightsSince > 15_000) {
                    break;
                }
                Thread.sleep(1000);
            }
            if (attempt == 1 && !driver.getDriver().findElements(searchButton).isEmpty()) {
                System.out.println("No flights on the first search -- searching again once");
                driver.element().click(searchButton);
                Thread.sleep(2000);
            }
        }
        return false;
    }

    public boolean isOnAncillariesStep() {
        return !driver.getDriver().findElements(ancillariesStepActive).isEmpty();
    }

    public String getBundleErrorText() {
        return textOf(By.xpath("//ndc-bundles//small[contains(@class,'p-error')]"));
    }

    public boolean isBundleRequiredErrorDisplayed() {
        return driver.getDriver().findElements(bundleRequiredError).stream().anyMatch(WebElement::isDisplayed);
    }

    // Hold depends on the airline configuration -- a hold scenario can't run on a flight without it.
    public Ancillaries_Page skipIfHoldNotAvailable() {
        boolean banner = !driver.getDriver().findElements(holdNotAvailableMsg).isEmpty();
        boolean button = !driver.getDriver().findElements(holdButton).isEmpty();
        if (banner || !button) {
            throw new SkipException("Hold is not available for this flight (airline configuration) -- pick a route/airline with hold enabled (Defaults.Routes / Supplier) in Portal_Ancillaries.json");
        }
        return this;
    }

    // ================================================================= bundles (one per route)

    public int getBundleRouteCount() {
        return driver.getDriver().findElements(bundleTabs).size();
    }

    public String getBundleRoute(int route) {
        List<WebElement> airports = driver.getDriver().findElements(By.xpath(bundleTab(route) + "//div[contains(@class,'custom-accordion-header')]/span"));
        List<String> codes = new ArrayList<>();
        for (WebElement a : airports) codes.add(a.getText().trim());
        return String.join("-", codes);
    }

    public Ancillaries_Page selectBundleForAllRoutes(Pick pick) throws InterruptedException {
        for (int r = 1; r <= getBundleRouteCount(); r++) selectBundle(r, pick);
        return this;
    }

    public Ancillaries_Page selectBundle(int route, Pick pick) throws InterruptedException {
        openBundleDropdown(route);
        List<String> names = new ArrayList<>();
        for (WebElement o : driver.getDriver().findElements(openOptions)) names.add(o.getText().trim());
        // Bundle options carry no price -- pick by position; prices are read from the details card after.
        int idx = pick.position() > 0 ? pick.position() : (pick == Pick.LAST || pick == Pick.MOST_EXPENSIVE ? names.size() : 1);
        if (names.isEmpty() || idx > names.size()) {
            String what = "BUNDLE for route " + route + " (" + pick + ", only " + names.size() + " offered)";
            notOffered.add(what);
            System.out.println("Not offered on this flight, skipped: " + what);
            driver.getDriver().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
            Thread.sleep(500);
            return this;
        }
        return chooseBundle(route, names.get(idx - 1));
    }

    // option: a Pick name ("FIRST", "CHEAPEST_PAID", ...) or an exact bundle name ("Value Flex").
    public Ancillaries_Page selectBundle(int route, String option) throws InterruptedException {
        if (asPick(option) != null) return selectBundle(route, asPick(option));
        openBundleDropdown(route);
        return chooseBundle(route, option);
    }

    public Ancillaries_Page selectBundleForAllRoutes(String option) throws InterruptedException {
        for (int r = 1; r <= getBundleRouteCount(); r++) selectBundle(r, option);
        return this;
    }

    // Test data values: a Pick name, an exact option name, or "NONE"/empty for "don't select".
    public static Pick asPick(String option) {
        if (option == null) return null;
        if (option.trim().matches("\\d{1,2}")) {
            int n = Integer.parseInt(option.trim());
            return n >= 1 && n <= Pick.TENTH.position() ? Pick.values()[n - 1] : null;
        }
        try {
            return Pick.valueOf(option.trim().toUpperCase());
        } catch (IllegalArgumentException notAPick) {
            return null;
        }
    }

    public static boolean isNone(String option) {
        return option == null || option.isBlank() || option.trim().equalsIgnoreCase("NONE");
    }

    private void openBundleDropdown(int route) throws InterruptedException {
        expandIfCollapsed(bundleTab(route));
        driver.element().click(By.xpath(bundleTab(route) + "//p-dropdown[@formcontrolname='bundle']//div[@role='button']"));
        Thread.sleep(800);
    }

    private Ancillaries_Page chooseBundle(int route, String bundleName) throws InterruptedException {
        List<WebElement> option = driver.getDriver().findElements(By.xpath("//li[@role='option'][@aria-label=" + xq(bundleName) + " or normalize-space(.)=" + xq(bundleName) + "]"));
        if (option.isEmpty()) {
            List<String> offered = textsOf(driver.getDriver().findElements(openOptions));
            throw new AssertionError("Route " + route + ": bundle '" + bundleName + "' is not offered on this flight; offered: " + offered);
        }
        double breakdownBefore = readOptionalMoney(fareBreakdownSsrSub("Bundle Fare")).value;
        Selection previous = selections.get(Type.BUNDLE + "|0|" + route);
        driver.element().click(By.xpath("//li[@role='option'][@aria-label=" + xq(bundleName) + " or normalize-space(.)=" + xq(bundleName) + "]"));
        Thread.sleep(1500);
        // "Price for Adult: 2,146 EGP" / "Price for Child: ..." -- unit price per passenger of that
        // type; the bundle costs unit x count for each type in the booking.
        double price = 0;
        boolean adultPriced = false, childPriced = false;
        List<WebElement> rows = driver.getDriver().findElements(By.xpath(bundleTab(route) + "//div[contains(@class,'pricing-section')]/div"));
        for (WebElement row : rows) {
            String text = row.getText();
            double unit = parseAmount(text);
            if (text.contains("Adult")) { price += unit * adults; adultPriced = true; }
            else if (text.contains("Child")) { price += unit * children; childPriced = true; }
            else if (text.contains("Infant")) price += unit * infants;
        }
        if (rows.isEmpty()) {
            // Supplier shows no per-passenger prices on the card: take what the fare breakdown added.
            double breakdownAfter = readOptionalMoney(fareBreakdownSsrSub("Bundle Fare")).value;
            price = breakdownAfter - breakdownBefore + (previous == null ? 0 : previous.price);
        } else {
            // Every passenger type in the booking must have its own bundle price, otherwise that type
            // is either not charged or charged an unknown amount.
            if (adults > 0 && !adultPriced) selectionIssues.add("[CRITICAL] Route " + route + ": bundle '" + bundleName + "' shows no 'Price for Adult' although the booking has " + adults + " adult(s)");
            if (children > 0 && !childPriced) selectionIssues.add("[CRITICAL] Route " + route + ": bundle '" + bundleName + "' shows no 'Price for Child' although the booking has " + children + " child(ren)");
        }
        List<String> services = textsOf(By.xpath(bundleTab(route) + "//div[contains(@class,'bundle-details-card')]//ul/li"));
        String shown = textOf(By.xpath(bundleTab(route) + "//p-dropdown[@formcontrolname='bundle']//span[contains(@class,'p-dropdown-label')]"));
        if (!shown.equals(bundleName)) {
            selectionIssues.add("Route " + route + ": bundle dropdown shows '" + shown + "' after choosing '" + bundleName + "'");
        }
        if (services.isEmpty()) {
            selectionIssues.add("Route " + route + ": bundle '" + bundleName + "' shows no services");
        }
        record(new Selection(Type.BUNDLE, 0, route, getBundleRoute(route), "", bundleName, price, "", "", services));
        return this;
    }

    // ================================================================= meals / baggage

    public int getAncillaryPaxCount() {
        return driver.getDriver().findElements(ancillaryPaxTabs).size();
    }

    public List<String> getAncillaryPaxNames() {
        List<String> names = new ArrayList<>();
        for (WebElement n : driver.getDriver().findElements(By.xpath("//ndc-ancillaries//span[@class='pax-header__name']")))
            names.add(clean(n.getAttribute("textContent")));
        return names;
    }

    public int getMealCardCount(int pax) {
        expandIfCollapsed(paxTab(pax));
        return driver.getDriver().findElements(By.xpath(paxTab(pax) + "//ndc-meal-selector//" + SSR_CARD)).size();
    }

    public int getBaggageCardCount(int pax) {
        expandIfCollapsed(paxTab(pax));
        return driver.getDriver().findElements(By.xpath(paxTab(pax) + "//ndc-baggage-selector//" + SSR_CARD)).size();
    }

    public String getCardDirection(String cardXpath) {
        List<WebElement> d = driver.getDriver().findElements(By.xpath(cardXpath + "//span[@class='ssr-card__direction']"));
        return d.isEmpty() ? "" : clean(d.get(0).getAttribute("textContent"));
    }

    public String getMealCardDirection(int pax, int segment) {
        return getCardDirection(mealCard(pax, segment));
    }

    public String getBaggageCardDirection(int pax, int journey) {
        return getCardDirection(baggageCard(pax, journey));
    }

    private String cardRoute(String cardXpath) {
        List<String> codes = new ArrayList<>();
        for (WebElement a : driver.getDriver().findElements(By.xpath(cardXpath + "//span[@class='ssr-card__airport']")))
            codes.add(clean(a.getAttribute("textContent")));
        return String.join("-", codes);
    }

    // Single-card selection by card index (1-based, in page order). Scenarios use the rules
    // (applySsrRules), which resolve passenger / journey / segment to these indexes.
    public Ancillaries_Page selectMeal(int pax, int segment, Pick pick) throws InterruptedException {
        return selectSsr(Type.MEAL, pax, segment, mealCard(pax, segment), pick, null);
    }

    // option: a Pick name or an exact meal code ("SFML").
    public Ancillaries_Page selectMeal(int pax, int segment, String option) throws InterruptedException {
        return selectSsr(Type.MEAL, pax, segment, mealCard(pax, segment), asPick(option), asPick(option) == null ? option : null);
    }

    public Ancillaries_Page selectBaggage(int pax, int journey, Pick pick) throws InterruptedException {
        return selectSsr(Type.BAGGAGE, pax, journey, baggageCard(pax, journey), pick, null);
    }

    // option: a Pick name or an exact baggage description ("20Kg Baggage").
    public Ancillaries_Page selectBaggage(int pax, int journey, String option) throws InterruptedException {
        return selectSsr(Type.BAGGAGE, pax, journey, baggageCard(pax, journey), asPick(option), asPick(option) == null ? option : null);
    }

    public Ancillaries_Page clearMeal(int pax, int segment) throws InterruptedException {
        return clearSsr(Type.MEAL, pax, segment, mealCard(pax, segment));
    }

    public Ancillaries_Page clearBaggage(int pax, int journey) throws InterruptedException {
        return clearSsr(Type.BAGGAGE, pax, journey, baggageCard(pax, journey));
    }

    // Lists "label | price" for every option of one meal/baggage dropdown (closes it again).
    public List<String> getOptions(Type type, int pax, int index) throws InterruptedException {
        String card = type == Type.MEAL ? mealCard(pax, index) : baggageCard(pax, index);
        if (!openSsrDropdown(card)) return new ArrayList<>();
        List<String> out = new ArrayList<>();
        for (WebElement o : driver.getDriver().findElements(openOptions))
            out.add(o.getAttribute("aria-label") + " | " + optionPrice(type, o));
        driver.getDriver().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
        Thread.sleep(500);
        return out;
    }

    private Ancillaries_Page selectSsr(Type type, int pax, int index, String card, Pick pick, String label) throws InterruptedException {
        if (!ancillariesOffered || driver.getDriver().findElements(By.xpath(card)).isEmpty()) {
            String what = type + " for pax " + pax + " #" + index + " (" + (label != null ? label : pick) + ")";
            notOffered.add(what);
            System.out.println("Not offered on this flight, skipped: " + what);
            return this;
        }
        expandIfCollapsed(paxTab(pax));
        List<WebElement> options = openSsrDropdown(card) ? driver.getDriver().findElements(openOptions) : new ArrayList<>();
        if (options.isEmpty()) {
            notOffered(type + " for pax " + pax + " #" + index + " (" + (label != null ? label : pick) + "): the dropdown has no options");
            return this;
        }

        WebElement chosen = null;
        if (label != null) {
            for (WebElement o : options)
                if (label.equals(o.getAttribute("aria-label"))) chosen = o;
            if (chosen == null) throw new AssertionError(type + " option '" + label + "' not offered for pax " + pax + " card " + index);
        } else {
            chosen = pickOption(type, options, pick);
            if (chosen == null) {
                String what = type + " for pax " + pax + " #" + index + " (no " + pick + " option)";
                notOffered.add(what);
                System.out.println("Not offered on this flight, skipped: " + what);
                driver.getDriver().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
                Thread.sleep(500);
                return this;
            }
        }
        String name = chosen.getAttribute("aria-label");
        double price = optionPrice(type, chosen);
        String description = "";
        if (type == Type.MEAL) {
            List<WebElement> d = chosen.findElements(By.xpath(".//span[contains(@class,'meal-option__description')]"));
            description = d.isEmpty() ? "" : clean(d.get(0).getAttribute("textContent"));
        }
        String paxName = textOf(By.xpath(paxTab(pax) + "//span[@class='pax-header__name']"));
        scrollTo(chosen);
        chosen.click();
        Thread.sleep(1500);
        record(new Selection(type, pax, index, cardRoute(card), getCardDirection(card), name, price,
                paxName, description, new ArrayList<>()));
        return this;
    }

    private WebElement pickOption(Type type, List<WebElement> options, Pick pick) {
        if (pick.position() > 0) return pick.position() <= options.size() ? options.get(pick.position() - 1) : null;
        if (pick == Pick.LAST) return options.get(options.size() - 1);
        WebElement best = null;
        double bestPrice = 0;
        for (WebElement o : options) {
            double p = optionPrice(type, o);
            if (pick == Pick.FREE && p == 0) return o;
            if (pick == Pick.CHEAPEST_PAID && p > 0 && (best == null || p < bestPrice)) { best = o; bestPrice = p; }
            if (pick == Pick.MOST_EXPENSIVE && (best == null || p > bestPrice)) { best = o; bestPrice = p; }
        }
        return best; // null: no option matches (e.g. FREE on a supplier without free meals)
    }

    private double optionPrice(Type type, WebElement option) {
        if (type == Type.MEAL) {
            List<WebElement> p = option.findElements(By.xpath(".//span[@class='meal-option__price']"));
            return p.isEmpty() ? 0 : parseAmount(p.get(0).getAttribute("textContent"));
        }
        // "20Kg Baggage - ( 4,101 EGP )"
        return parseAmount(option.getAttribute("textContent"));
    }

    // Opens a meal/baggage dropdown; false (and closed again) when it has no options -- some suppliers
    // show a baggage card with an empty list.
    private boolean openSsrDropdown(String card) throws InterruptedException {
        WebElement trigger = driver.getDriver().findElement(By.xpath(card + "//p-dropdown//div[@role='button']"));
        scrollTo(trigger);
        trigger.click();
        try {
            new WebDriverWait(driver.getDriver(), Duration.ofSeconds(8))
                    .until(ExpectedConditions.presenceOfElementLocated(openOptions));
        } catch (org.openqa.selenium.TimeoutException empty) {
            driver.getDriver().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
            Thread.sleep(500);
            return false;
        }
        Thread.sleep(500);
        return true;
    }

    private Ancillaries_Page clearSsr(Type type, int pax, int index, String card) throws InterruptedException {
        expandIfCollapsed(paxTab(pax));
        List<WebElement> icons = driver.getDriver().findElements(By.xpath(card + "//*[contains(@class,'p-dropdown-clear-icon')]"));
        if (icons.isEmpty()) return this; // nothing selected on this card
        WebElement clear = icons.get(0);
        scrollTo(clear);
        clear.click();
        Thread.sleep(1500);
        forget(type, pax, index);
        return this;
    }

    public String getSelectedMeal(int pax, int segment) {
        List<WebElement> n = driver.getDriver().findElements(By.xpath(mealCard(pax, segment) + "//span[@class='meal-selected__name']"));
        return n.isEmpty() ? "" : clean(n.get(0).getAttribute("textContent"));
    }

    public String getSelectedBaggage(int pax, int journey) {
        // The placeholder ("Choose a baggage") is rendered in the same label span, with p-placeholder.
        return textOf(By.xpath(baggageCard(pax, journey) + "//span[contains(@class,'p-dropdown-label') and not(contains(@class,'p-placeholder'))]"));
    }

    // ================================================================= rules (passenger / journey / segment / pick)

    // One line of the test data, e.g. {"Passenger": "ALL", "Journey": 1, "Segment": "LAST", "Pick": "SECOND"}.
    // Passenger: ALL | ADULTS | CHILDREN | n.  Journey: ALL | n.  Segment: ALL | LAST | n.
    // Pick: FIRST..TENTH | LAST | CHEAPEST_PAID | MOST_EXPENSIVE | FREE | exact option name | NONE.
    public static class Rule {
        public final Type type;
        public final String passenger, journey, segment, pick;

        public Rule(Type type, String passenger, String journey, String segment, String pick) {
            this.type = type;
            this.passenger = blankTo(passenger, "ALL");
            this.journey = blankTo(journey, "ALL");
            this.segment = blankTo(segment, "ALL");
            this.pick = blankTo(pick, "NONE");
        }

        private static String blankTo(String v, String def) {
            return v == null || v.isBlank() ? def : v.trim();
        }

        @Override
        public String toString() {
            return type + "{Passenger=" + passenger + ", Journey=" + journey
                    + (type == Type.MEAL ? ", Segment=" + segment : "") + ", Pick=" + pick + "}";
        }
    }

    // A meal/baggage card of one passenger, located by journey and segment.
    public static class CardRef {
        public final int index, journey, segment;
        public final String route;

        CardRef(int index, int journey, int segment, String route) {
            this.index = index;
            this.journey = journey;
            this.segment = segment;
            this.route = route;
        }

        @Override
        public String toString() {
            return "J" + journey + "S" + segment + " " + route;
        }
    }

    // Routes as searched ("CAI-DXB", ...), the last fallback for journey boundaries.
    public Ancillaries_Page setSearchRoutes(List<String> routes) {
        this.searchRoutes = new ArrayList<>(routes);
        if (journeyCount == 0) journeyCount = routes.size();
        return this;
    }

    // Journey routes in order: from pax 1's baggage cards (one per journey), else the bundle routes,
    // else the searched routes. Only the endpoints are used to cut segments into journeys.
    private List<String> journeyRoutes() {
        if (ancillariesOffered && getAncillaryPaxCount() > 0) {
            int bags = getBaggageCardCount(1);
            if (bags > 0) {
                List<String> r = new ArrayList<>();
                for (int j = 1; j <= bags; j++) r.add(cardRoute(baggageCard(1, j)));
                // Ignore baggage routes when a card shows no route (seen on Al Jazeera: "→" only).
                boolean complete = r.stream().allMatch(x -> x.contains("-") && !x.startsWith("-") && !x.endsWith("-"));
                if (complete && r.size() >= Math.max(1, journeyCount)) return r;
            }
        }
        if (!bundleRoutes.isEmpty()) return bundleRoutes;
        return searchRoutes;
    }

    private static String destinationOf(String route) {
        return route.contains("-") ? route.substring(route.lastIndexOf('-') + 1).trim() : route.trim();
    }

    // Meal cards of one passenger with their journey/segment. Segments are cut into journeys by the
    // journey destinations (CAI-RUH, RUH-DXB -> journey 1 ends at DXB); if that doesn't add up, the
    // card direction labels are used ("Onward · Leg 1" / "Return · Leg 1").
    public List<CardRef> getMealCards(int pax) {
        int n = getMealCardCount(pax);
        List<String> routes = journeyRoutes();
        List<CardRef> byRoute = new ArrayList<>();
        int journey = 1, segment = 0;
        for (int i = 1; i <= n; i++) {
            String route = cardRoute(mealCard(pax, i));
            byRoute.add(new CardRef(i, journey, ++segment, route));
            if (journey < routes.size() && destinationOf(route).equals(destinationOf(routes.get(journey - 1)))) {
                journey++;
                segment = 0;
            }
        }
        int journeysFound = byRoute.isEmpty() ? 0 : byRoute.get(byRoute.size() - 1).journey;
        if (routes.size() <= 1 || journeysFound == routes.size()) return byRoute;

        List<CardRef> byLabel = new ArrayList<>();
        String group = null;
        journey = 0;
        for (int i = 1; i <= n; i++) {
            String label = getMealCardDirection(pax, i);
            String g = label.contains("·") ? label.substring(0, label.indexOf('·')).trim() : label.replaceAll("(?i)leg\\s*\\d+", "").trim();
            if (!g.equals(group)) {
                group = g;
                journey++;
                segment = 0;
            }
            byLabel.add(new CardRef(i, journey, ++segment, cardRoute(mealCard(pax, i))));
        }
        System.out.println("Meal cards mapped by direction labels (route endpoints didn't match): " + byLabel);
        return byLabel;
    }

    // Baggage cards of one passenger: one per journey, matched to the journey by route when possible.
    public List<CardRef> getBaggageCards(int pax) {
        List<String> routes = journeyRoutes();
        List<CardRef> out = new ArrayList<>();
        for (int i = 1; i <= getBaggageCardCount(pax); i++) {
            String route = cardRoute(baggageCard(pax, i));
            int j = routes.indexOf(route) + 1;
            out.add(new CardRef(i, j > 0 ? j : i, 1, route));
        }
        return out;
    }

    private boolean paxMatches(String spec, int pax) {
        String s = spec.toUpperCase();
        if (s.equals("ALL")) return true;
        String type = pax <= paxTypes.size() ? paxTypes.get(pax - 1) : (pax <= adults ? "ADULT" : "CHILD");
        if (s.equals("ADULTS") || s.equals("ADULT")) return type.equals("ADULT");
        if (s.equals("CHILDREN") || s.equals("CHILD")) return type.equals("CHILD");
        return s.matches("\\d+") && Integer.parseInt(s) == pax;
    }

    private static boolean numMatches(String spec, int value, int last) {
        String s = spec.toUpperCase();
        if (s.equals("ALL")) return true;
        if (s.equals("LAST")) return value == last;
        return s.matches("\\d+") && Integer.parseInt(s) == value;
    }

    // Bundle rules: Journey = route tab (ALL | n). Later rules override earlier ones.
    public Ancillaries_Page applyBundleRules(List<Rule> rules) throws InterruptedException {
        Map<Integer, String> plan = new LinkedHashMap<>();
        int routes = getBundleRouteCount();
        for (Rule rule : rules) {
            boolean hit = false;
            for (int r = 1; r <= routes; r++) {
                if (numMatches(rule.journey, r, routes)) {
                    plan.put(r, rule.pick);
                    hit = true;
                }
            }
            if (!hit) notOffered("rule " + rule + " matched no bundle on this flight (" + routes + " bundle route(s))");
        }
        for (Map.Entry<Integer, String> e : plan.entrySet()) {
            if (!isNone(e.getValue())) selectBundle(e.getKey(), e.getValue());
        }
        return this;
    }

    // Meal / baggage rules (call on the ancillaries step). Resolved into one final choice per card
    // first, so a later rule ("pax 2 -> NONE") cleanly overrides an earlier one ("ALL -> FIRST").
    public Ancillaries_Page applySsrRules(List<Rule> rules) throws InterruptedException {
        Map<String, String> plan = new LinkedHashMap<>(); // "TYPE|pax|card" -> pick
        Map<String, List<CardRef>> cards = new LinkedHashMap<>();
        for (Rule rule : rules) {
            boolean hit = false;
            for (int p = 1; p <= getAncillaryPaxCount(); p++) {
                if (!paxMatches(rule.passenger, p)) continue;
                final int pax = p;
                List<CardRef> paxCards = cards.computeIfAbsent(rule.type + "|" + p, k -> cardsFor(rule.type, pax));
                for (CardRef c : paxCards) {
                    if (matches(rule, c, paxCards)) {
                        plan.put(rule.type + "|" + p + "|" + c.index, rule.pick);
                        hit = true;
                    }
                }
            }
            if (!hit) notOffered("rule " + rule + " matched no card on this flight");
        }
        for (Map.Entry<String, String> e : plan.entrySet()) {
            String[] k = e.getKey().split("\\|");
            Type type = Type.valueOf(k[0]);
            int pax = Integer.parseInt(k[1]), card = Integer.parseInt(k[2]);
            if (isNone(e.getValue())) {
                if (type == Type.MEAL) clearMeal(pax, card); else clearBaggage(pax, card);
            } else if (type == Type.MEAL) {
                selectMeal(pax, card, e.getValue());
            } else {
                selectBaggage(pax, card, e.getValue());
            }
        }
        return this;
    }

    // "Clear" rules: remove what is selected on the matching cards (Pick is ignored).
    public Ancillaries_Page applyClearRules(List<Rule> rules) throws InterruptedException {
        for (Rule rule : rules) {
            for (int p = 1; p <= getAncillaryPaxCount(); p++) {
                if (!paxMatches(rule.passenger, p)) continue;
                List<CardRef> paxCards = cardsFor(rule.type, p);
                for (CardRef c : paxCards) {
                    if (!matches(rule, c, paxCards)) continue;
                    if (rule.type == Type.MEAL) clearMeal(p, c.index); else clearBaggage(p, c.index);
                }
            }
        }
        return this;
    }

    public Ancillaries_Page clearAllSsr() throws InterruptedException {
        for (int p = 1; p <= getAncillaryPaxCount(); p++) {
            for (int m = 1; m <= getMealCardCount(p); m++) clearMeal(p, m);
            for (int b = 1; b <= getBaggageCardCount(p); b++) clearBaggage(p, b);
        }
        return this;
    }

    private List<CardRef> cardsFor(Type type, int pax) {
        return type == Type.MEAL ? getMealCards(pax) : getBaggageCards(pax);
    }

    private static boolean matches(Rule rule, CardRef card, List<CardRef> all) {
        int lastJourney = all.stream().mapToInt(c -> c.journey).max().orElse(0);
        if (!numMatches(rule.journey, card.journey, lastJourney)) return false;
        if (rule.type != Type.MEAL) return true;
        int lastSegment = all.stream().filter(c -> c.journey == card.journey).mapToInt(c -> c.segment).max().orElse(0);
        return numMatches(rule.segment, card.segment, lastSegment);
    }

    private void notOffered(String what) {
        notOffered.add(what);
        System.out.println("Not offered on this flight, skipped: " + what);
    }

    // ================================================================= checks: severity, money, hard checkpoints
    //
    // Every check is tagged so money problems stand out in the report:
    //   [CRITICAL] money (amounts, totals, charged / received, ledger, wallet)
    //   [MAJOR]    wrong data (names, codes, routes, counts that are not money)
    //   [MINOR]    display only
    // Checks are collected and reported together (soft); hard() stops immediately -- after reporting
    // everything collected so far -- where continuing would cost money or make no sense.
    // Only CRITICAL failures fail the test. MAJOR / MINOR failures are printed as warnings when they
    // happen and attached to the Allure report ("Non-critical findings") at the end, without failing it.

    public enum Severity { CRITICAL, MAJOR, MINOR }

    public static final class Checks {
        private final SoftAssert soft = new SoftAssert();
        private final List<String> warnings = new ArrayList<>();
        private int failures;
        private int criticalFailures;

        public void check(Severity severity, boolean ok, String message) {
            if (!ok) {
                failures++;
                if (severity == Severity.CRITICAL) criticalFailures++;
            }
            if (severity == Severity.CRITICAL) {
                soft.assertTrue(ok, "[" + severity + "] " + message);
            } else if (!ok) {
                warnings.add("[" + severity + "] " + message);
                System.out.println("WARNING [" + severity + "] " + message);
            }
        }

        public int criticalFailures() {
            return criticalFailures;
        }

        public void equal(Severity severity, Object actual, Object expected, String message) {
            boolean ok = actual == null ? expected == null : actual.equals(expected);
            check(severity, ok, message + " -- expected [" + expected + "] but found [" + actual + "]");
        }

        public void fail(Severity severity, String message) {
            check(severity, false, message);
        }

        // Hard checkpoint: on failure, stop now and report this plus everything collected so far.
        public void hard(boolean ok, String message) {
            check(Severity.CRITICAL, ok, "HARD CHECKPOINT: " + message);
            if (!ok) assertAll();
        }

        public int failures() {
            return failures;
        }

        public List<String> warnings() {
            return new ArrayList<>(warnings);
        }

        public void assertAll() {
            if (!warnings.isEmpty()) {
                String report = warnings.size() + " non-critical finding(s) (not failing the test):\n  " + String.join("\n  ", warnings);
                System.out.println(report);
                io.qameta.allure.Allure.addAttachment("Non-critical findings", report);
                warnings.clear(); // published once
            }
            soft.assertAll();
        }
    }

    // An amount as shown on a page, with how precisely that page shows it: exact to the cent where
    // the page shows decimals ("273.00", "1914.0"), +/-0.50 where it rounds to whole units ("16,667").
    public static final class Money {
        public final double value;
        public final double tolerance;
        public final String shown;

        Money(double value, double tolerance, String shown) {
            this.value = value;
            this.tolerance = tolerance;
            this.shown = shown;
        }

        @Override
        public String toString() {
            return shown + " (" + value + ")";
        }
    }

    private static final Money ABSENT = new Money(0, 0, "(no line)");
    private static final Pattern DECIMALS = Pattern.compile("\\d\\.\\d");

    public static Money money(String text) {
        String t = text == null ? "" : clean(text);
        double tolerance = DECIMALS.matcher(t).find() ? 0.005 : 0.5;
        return new Money(parseAmount(t), tolerance, t);
    }

    private void assertMoney(Checks c, Money actual, double expected, String where) {
        c.check(Severity.CRITICAL, moneyMatches(actual, expected), moneyMessage(actual, expected, where));
    }

    private void hardMoney(Checks c, Money actual, double expected, String where) {
        c.hard(moneyMatches(actual, expected), moneyMessage(actual, expected, where));
    }

    private static boolean moneyMatches(Money actual, double expected) {
        return Math.abs(actual.value - expected) <= actual.tolerance + 1e-6;
    }

    private static String moneyMessage(Money actual, double expected, String where) {
        return where + ": shown " + actual + ", expected " + String.format(Locale.ROOT, "%.2f", expected)
                + " (allowed +/-" + (actual.tolerance < 0.01 ? "0.00" : String.format(Locale.ROOT, "%.2f", actual.tolerance)) + ")";
    }

    // ================================================================= assertions: passenger page

    // Ancillaries step structure: one tab per adult/child (infants get none), baggage once per journey,
    // meals once per segment, and every card labelled with its route.
    public Ancillaries_Page assertAncillaryStructure(Checks soft) {
        int journeys = journeyCount;
        if (bundleRouteCount > 0 && journeys > 0) {
            soft.equal(Severity.MAJOR, bundleRouteCount, journeys, "One bundle dropdown per journey/route");
        }
        if (!ancillariesOffered) {
            soft.equal(Severity.MAJOR, getAncillaryPaxCount(), 0, "No ancillaries step expected for this flight");
            return this;
        }
        soft.equal(Severity.MAJOR, getAncillaryPaxCount(), adults + children,
                "Ancillary passenger tabs must be adults + children only (infants are not eligible)");
        for (int p = 1; p <= getAncillaryPaxCount(); p++) {
            // Every card must say which route it is for (seen on Al Jazeera: baggage card shows only "->").
            for (int j = 1; j <= getBaggageCardCount(p); j++) {
                String route = cardRoute(baggageCard(p, j));
                soft.check(Severity.MINOR, route.matches("[^-]+-[^-]+.*"), "Pax " + p + ": extra baggage card " + j + " shows no route ('" + route + "')");
            }
            for (int s = 1; s <= getMealCardCount(p); s++) {
                String route = cardRoute(mealCard(p, s));
                soft.check(Severity.MINOR, route.matches("[^-]+-[^-]+.*"), "Pax " + p + ": meal card " + s + " shows no route ('" + route + "')");
            }
            int bags = getBaggageCardCount(p);
            if (bags > 0 && journeys > 0) {
                soft.equal(Severity.MAJOR, bags, journeys, "Pax " + p + ": baggage must be offered once per journey");
            }
            int meals = getMealCardCount(p);
            soft.check(Severity.MAJOR, meals == 0 || journeys == 0 || meals >= journeys,
                    "Pax " + p + ": meal cards (" + meals + ") must cover every segment of " + journeys + " journey(s)");
            if (p > 1) {
                soft.equal(Severity.MAJOR, meals, getMealCardCount(1), "Every passenger must get the same meal (segment) cards");
            }
        }
        return this;
    }

    // Ancillaries step: every meal/baggage card shows exactly what was chosen for that passenger and
    // segment/journey (code, description, price) and untouched cards stay empty; bundle dropdown,
    // services and per-passenger-type prices were checked when the bundle was chosen on step 1.
    public Ancillaries_Page assertPassengerPageSelections(Checks soft) {
        for (String issue : selectionIssues) {
            Severity s = issue.startsWith("[CRITICAL]") ? Severity.CRITICAL : Severity.MAJOR;
            soft.fail(s, "Pax page - " + issue.replaceFirst("^\\[(CRITICAL|MAJOR)] ", ""));
        }
        for (int p = 1; p <= getAncillaryPaxCount(); p++) {
            for (int s = 1; s <= getMealCardCount(p); s++) {
                Selection m = selections.get(Type.MEAL + "|" + p + "|" + s);
                String card = mealCard(p, s);
                String where = "Pax page - pax " + p + " meal " + cardRoute(card) + " [" + getCardDirection(card) + "]";
                String shownCode = getSelectedMeal(p, s);
                if (m == null) {
                    // Something selected that nobody asked for would be charged.
                    soft.check(Severity.CRITICAL, shownCode.isEmpty(), where + " should be empty (nothing chosen) but shows '" + shownCode + "'");
                    continue;
                }
                soft.equal(Severity.MAJOR, shownCode, m.name, where + " code");
                soft.equal(Severity.MAJOR, textOf(By.xpath(card + "//span[contains(@class,'meal-selected__description')]")), m.description, where + " description");
                assertMoney(soft, money(textOf(By.xpath(card + "//span[@class='meal-selected__price']"))), m.price, where + " price");
            }
            for (int j = 1; j <= getBaggageCardCount(p); j++) {
                Selection b = selections.get(Type.BAGGAGE + "|" + p + "|" + j);
                String where = "Pax page - pax " + p + " baggage " + cardRoute(baggageCard(p, j)) + " [" + getBaggageCardDirection(p, j) + "]";
                String shown = getSelectedBaggage(p, j);
                if (b == null) {
                    soft.check(Severity.CRITICAL, shown.isEmpty(), where + " should be empty (nothing chosen) but shows '" + shown + "'");
                } else {
                    soft.check(Severity.MAJOR, shown.startsWith(b.name), where + " should show '" + b.name + "' but shows '" + shown + "'");
                    assertMoney(soft, money(shown.substring(Math.min(shown.length(), b.name.length()))), b.price, where + " price");
                }
            }
        }
        return this;
    }

    // The money gate (hard before Book / Hold) uses exactly these checks.
    public Ancillaries_Page assertPassengerPageFareBreakdown(Checks soft) {
        soft.check(Severity.CRITICAL, baseTotal >= 0, "captureBaseFare() must run before any ancillary is selected");
        assertMoney(soft, readOptionalMoney(fareBreakdownSsrSub("Bundle Fare")), expectedTotal(Type.BUNDLE), "Pax page - Bundle Fare");
        assertMoney(soft, readOptionalMoney(fareBreakdownSsrSub("Meal Preference")), expectedTotal(Type.MEAL), "Pax page - Meal Preference");
        assertMoney(soft, readOptionalMoney(fareBreakdownSsrSub("Extra Baggage")), expectedTotal(Type.BAGGAGE), "Pax page - Extra Baggage");
        assertMoney(soft, readOptionalMoney(fareBreakdownSsrTotal), expectedAncillariesTotal(), "Pax page - (+) Ancillaries & SSR");
        assertMoney(soft, readMoney(fareBreakdownTotal), expectedGrandTotal(), "Pax page - Total Amount");
        // By design Net Payable is NOT rounded: exact base fare + taxes + ancillaries (18,855.4 while
        // Total Amount shows the rounded 18,856).
        assertMoney(soft, readMoney(netPayable), expectedNetPayable(), "Pax page - Net Payable Amount (unrounded by design)");
        // Ancillaries only add their own lines: every Base fare / Tax & Fees row stays exactly as before.
        Map<String, Double> now = readFareRows();
        for (Map.Entry<String, Double> before : baseFareRows.entrySet()) {
            Double after = now.get(before.getKey());
            soft.check(Severity.CRITICAL, after != null && Math.abs(after - before.getValue()) < 0.005,
                    "Pax page - '" + before.getKey() + "' changed after adding ancillaries: before " + before.getValue() + ", now " + after);
        }
        return this;
    }

    // ================================================================= assertions: booking created / paid

    public String getTripIdFromUrl() {
        String url = driver.getDriver().getCurrentUrl();
        return url.substring(url.lastIndexOf('/') + 1).split("\\?")[0];
    }

    // Order details on the confirmation page: "GDS PNR Number | Airline PNR Number | Booking Date | Status".
    private String orderDetail(String header) {
        List<WebElement> ths = driver.getDriver().findElements(By.xpath("//th[normalize-space()=" + xq(header) + "]"));
        if (ths.isEmpty()) return "";
        WebElement th = ths.get(0);
        int index = th.findElements(By.xpath("./preceding-sibling::th")).size() + 1;
        List<WebElement> cell = th.findElements(By.xpath("./ancestor::table[1]//tbody/tr[1]/td[" + index + "] | ./ancestor::thead[1]/following-sibling::tbody[1]/tr[1]/td[" + index + "]"));
        if (cell.isEmpty()) cell = th.findElements(By.xpath("./ancestor::tr[1]/following-sibling::tr[1]/td[" + index + "]"));
        return text(cell);
    }

    // HARD: the booking / hold really exists (PNR issued); a booked ticket must be "Confirmed".
    private void assertBookingCreated(Checks soft, String expectedStatus) {
        String pnr = orderDetail("GDS PNR Number");
        String status = orderDetail("Status");
        System.out.println("Order details: GDS PNR '" + pnr + "', Status '" + status + "'");
        // "-" is a placeholder, not a PNR: seen live on a Portal Al Jazeera hold stuck at "In Progress"
        // (no PNR, no Pay action in My Bookings even 10+ minutes later).
        boolean pnrIssued = !pnr.isEmpty() && !pnr.equals("-");
        soft.hard(pnrIssued, "Booking created: GDS PNR must be shown on the confirmation page (GDS PNR '" + pnr + "', Status '" + status + "')");
        if (expectedStatus != null) {
            soft.check(Severity.CRITICAL, status.equalsIgnoreCase(expectedStatus), "Booking status should be '" + expectedStatus + "' but is '" + status + "'");
        }
    }

    // HARD: pay after hold must end on "Your ticket has been successfully Confirmed".
    private void assertPayAfterHoldConfirmed(Checks soft) {
        By success = By.xpath("//*[contains(normalize-space(.),'successfully Confirmed')][not(*[contains(normalize-space(.),'successfully Confirmed')])]");
        boolean confirmed;
        try {
            new WebDriverWait(driver.getDriver(), Duration.ofSeconds(120)).until(ExpectedConditions.presenceOfElementLocated(success));
            confirmed = true;
        } catch (org.openqa.selenium.TimeoutException none) {
            confirmed = false;
        }
        soft.hard(confirmed, "Pay after hold: 'Your ticket has been successfully Confirmed' must be shown");
    }

    // ================================================================= assertions: confirmation page

    public Ancillaries_Page assertConfirmationPage(Checks soft) {
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(120))
                .until(ExpectedConditions.presenceOfElementLocated(confirmationTotal));
        assertMoney(soft, sumMoney(confirmationFareItem("Base fare")), rawFareSum("Base fare"), "Confirmation - Base fare unchanged by ancillaries");
        assertMoney(soft, sumMoney(confirmationFareItem("Taxes (+)")), rawFareSum("Tax"), "Confirmation - Taxes unchanged by ancillaries");
        assertMoney(soft, sumMoney(confirmationFareItem("Bundle (+)")), expectedTotal(Type.BUNDLE), "Confirmation - Bundle (+)");
        assertMoney(soft, sumMoney(confirmationFareItem("Meal (+)")), expectedTotal(Type.MEAL), "Confirmation - Meal (+)");
        assertMoney(soft, sumMoney(confirmationFareItem("Baggage (+)")), expectedTotal(Type.BAGGAGE), "Confirmation - Baggage (+)");
        assertDocumentAncillaryDetails(soft, "Confirmation", "//ndc-passenger-ticket-details");
        hardMoney(soft, sumMoney(confirmationTotal), expectedNetPayable(), "Confirmation - Total Amount");
        return this;
    }

    // ================================================================= assertions: e-ticket

    // Opens the E-Ticket (new browser tab), asserts it, and returns to the original tab.
    public Ancillaries_Page assertETicket(Checks soft) throws InterruptedException {
        String main = driver.getDriver().getWindowHandle();
        int before = driver.getDriver().getWindowHandles().size();
        WebElement btn = driver.getDriver().findElement(eTicketButton);
        scrollTo(btn);
        btn.click();
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(30))
                .until(d -> d.getWindowHandles().size() > before);
        for (String h : driver.getDriver().getWindowHandles()) {
            if (!h.equals(main)) driver.getDriver().switchTo().window(h);
        }
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(60))
                .until(ExpectedConditions.presenceOfElementLocated(eTicketTotal));
        Thread.sleep(1500);

        assertMoney(soft, sumMoney(eTicketFareLine("Base Fare")), rawFareSum("Base fare"), "E-Ticket - Base Fare unchanged by ancillaries");
        assertMoney(soft, sumMoney(eTicketFareLine("Taxes (+)")), rawFareSum("Tax"), "E-Ticket - Taxes unchanged by ancillaries");
        assertMoney(soft, sumMoney(eTicketFareLine("Bundle (+)")), expectedTotal(Type.BUNDLE), "E-Ticket - Bundle (+)");
        assertMoney(soft, sumMoney(eTicketFareLine("Meal (+)")), expectedTotal(Type.MEAL), "E-Ticket - Meal (+)");
        assertMoney(soft, sumMoney(eTicketFareLine("Baggage (+)")), expectedTotal(Type.BAGGAGE), "E-Ticket - Baggage (+)");
        assertMoney(soft, sumMoney(eTicketTotal), expectedNetPayable(), "E-Ticket - Total");
        assertDocumentAncillaryDetails(soft, "E-Ticket", "//ndc-e-ticket-page");

        driver.getDriver().close();
        driver.getDriver().switchTo().window(main);
        return this;
    }

    // Shared by confirmation and e-ticket (same components):
    //  - Passenger Details table: each passenger's row lists exactly their meals ("XY 274 - BB Meal (BBML)"),
    //    "-" when none, and the Bundle column shows the bundle name.
    //  - Extra Baggage: one card per passenger (h4 = name) with "route + description + price" per journey.
    //  - Bundle Details: one card per route (h4 = route) listing the bundle's services.
    private void assertDocumentAncillaryDetails(Checks soft, String page, String scope) {
        // Meal / Seat / Bundle columns are only rendered when the flight has them.
        String table = scope + "//table[.//th[normalize-space()='Passenger Name']]";
        int mealCol = columnIndex(table, "Meal");
        int bundleCol = columnIndex(table, "Bundle");
        List<WebElement> rows = driver.getDriver().findElements(By.xpath(table + "//tbody/tr"));
        soft.check(Severity.MAJOR, !rows.isEmpty(), page + " - passenger table not found");
        if (!selectionsOf(Type.MEAL).isEmpty()) {
            soft.check(Severity.MAJOR, mealCol > 0, page + " - meals were bought but the passenger table has no Meal column");
        }

        for (String paxName : paxNames()) {
            WebElement row = rowFor(rows, paxName);
            if (row == null) {
                soft.fail(Severity.MAJOR, page + " - no passenger row for " + paxName);
                continue;
            }
            List<Selection> meals = selectionsFor(Type.MEAL, paxName);
            if (mealCol > 0) {
                String mealCell = cellText(row, mealCol);
                int mealEntries = row.findElements(By.xpath("./td[" + mealCol + "]//div[contains(@class,'ssr-item')]")).size();
                soft.equal(Severity.MAJOR, mealEntries, meals.size(), page + " - " + paxName + " meal entries [" + mealCell + "] vs selected " + meals);
                for (Selection m : meals) {
                    soft.check(Severity.MAJOR, mealCell.contains(mealLabel(m)), page + " - " + paxName + " should have meal '" + mealLabel(m) + "' (" + m.route + ") but cell is [" + mealCell + "]");
                }
            }
            checkBundleColumn(soft, page, paxName, row, bundleCol);
        }

        // Extra Baggage cards
        String bagCards = "//ndc-extra-baggage[.//span[normalize-space()='Extra Baggage']]//div[contains(concat(' ',normalize-space(@class),' '),' card ')]";
        List<Selection> bags = selectionsOf(Type.BAGGAGE);
        soft.equal(Severity.CRITICAL, driver.getDriver().findElements(By.xpath(bagCards + "//p[contains(@class,'baggage-info')]")).size(), bags.size(),
                page + " - number of extra baggage lines (each one is charged) vs bought " + bags);
        for (Selection b : bags) {
            // <div class="trip"><div class="trip-route"><span class="route">CAI-DXB</span></div><p class="baggage-info">...</p></div>
            List<WebElement> trips = driver.getDriver().findElements(By.xpath(bagCards + "[.//h4[contains(normalize-space(.)," + xq(b.paxName) + ")]]//div[contains(concat(' ',normalize-space(@class),' '),' trip ')]"));
            WebElement trip = null;
            for (WebElement t : trips) {
                if (text(t.findElements(By.xpath(".//span[contains(@class,'route')]"))).equals(b.route)) trip = t;
            }
            if (trip == null) {
                soft.fail(Severity.MAJOR, page + " - extra baggage card for " + b.paxName + " on " + b.route + " not found");
                continue;
            }
            String info = text(trip.findElements(By.xpath(".//p[contains(@class,'baggage-info')]")));
            soft.check(Severity.MAJOR, info.startsWith(b.name), page + " - " + b.paxName + " " + b.route + " baggage should be '" + b.name + "' but was [" + info + "]");
            assertMoney(soft, money(info.substring(Math.min(info.length(), b.name.length()))), b.price, page + " - " + b.paxName + " " + b.route + " baggage price");
        }

        // Bundle Details cards
        String bundleCards = "//ndc-extra-baggage[.//span[normalize-space()='Bundle Details']]//div[contains(concat(' ',normalize-space(@class),' '),' card ')]";
        for (Selection b : selectionsOf(Type.BUNDLE)) {
            List<WebElement> card = driver.getDriver().findElements(By.xpath(bundleCards + "[.//h4[normalize-space(.)=" + xq(b.route) + "]]"));
            if (card.isEmpty()) {
                soft.fail(Severity.MAJOR, page + " - Bundle Details card for route " + b.route + " not found");
                continue;
            }
            List<String> shown = textsOf(card.get(0).findElements(By.xpath(".//ul/li")));
            assertServices(soft, page + " - Bundle Details for '" + b.name + "' on " + b.route, shown, b.services);
        }
    }

    // Known gap seen live (flynas, Al Jazeera): this column shows "-" even when a bundle was bought.
    private void checkBundleColumn(Checks soft, String page, String paxName, WebElement row, int bundleCol) {
        List<Selection> bundles = selectionsOf(Type.BUNDLE);
        if (bundles.isEmpty()) return;
        if (bundleCol == 0) {
            soft.fail(Severity.MINOR, page + " - a bundle was bought but the passenger table has no Bundle column");
            return;
        }
        String bundleCell = cellText(row, bundleCol);
        for (Selection b : bundles) {
            soft.check(Severity.MINOR, bundleCell.contains(b.name), page + " - " + paxName + " Bundle column should show '" + b.name + "' but was [" + bundleCell + "]");
        }
    }

    private static String mealLabel(Selection m) {
        return m.description.isEmpty() ? "(" + m.name + ")" : m.description + " (" + m.name + ")";
    }

    // ================================================================= assertions: itinerary (booking details)

    // From the My Bookings results grid: open the booking's itinerary (order id link).
    public Ancillaries_Page openItineraryFromSearchResults() throws InterruptedException {
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(60))
                .until(ExpectedConditions.elementToBeClickable(itineraryLink));
        driver.element().click(itineraryLink);
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(60)).until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(costingTab),
                ExpectedConditions.presenceOfElementLocated(itineraryDetailsTab),
                ExpectedConditions.presenceOfElementLocated(itineraryAncillaryPax)));
        Thread.sleep(2000);
        return this;
    }

    // The agency itinerary may not have the Costing & Payment tab Admin has.
    private boolean openCostingTab() throws InterruptedException {
        if (driver.getDriver().findElements(costingTab).isEmpty()) {
            System.out.println("Itinerary - no 'Costing & Payment' tab on the Portal itinerary; costing checks skipped");
            return false;
        }
        driver.element().click(costingTab);
        Thread.sleep(2500);
        return true;
    }

    private void backToItineraryDetails() {
        if (!driver.getDriver().findElements(itineraryDetailsTab).isEmpty()) driver.element().click(itineraryDetailsTab);
    }

    public Ancillaries_Page assertItinerary(Checks soft) throws InterruptedException {
        // Checked once, right after booking: the itinerary must already show what was booked.
        assertItineraryAncillaryData(soft);

        if (!openCostingTab()) return this;
        // Portal "Costing" tab (seen live, Al Jazeera, no meals/baggage): per-pax Net / Gross / Tax
        // table + Total Booking / Sales / Net Amount only -- none of Admin's SSR lines. When the lines
        // are there they are checked; when ancillaries were bought and none is shown, that is reported.
        Money bundleLine = readOptionalMoney(costingValue("• Bundle Amount:"));
        Money mealLine = readOptionalMoney(costingValue("• Meal Amount:"));
        Money bagLine = readOptionalMoney(costingValue("• Baggage Amount:"));
        Money ssrLine = readOptionalMoney(costingValue("Total SSR Amount:"));
        if (bundleLine == ABSENT && mealLine == ABSENT && bagLine == ABSENT && ssrLine == ABSENT) {
            if (expectedAncillariesTotal() > 0) {
                soft.fail(Severity.MAJOR, "Itinerary - Costing tab shows no ancillary lines (Bundle / Meal / Baggage / Total SSR) although "
                        + String.format(Locale.ROOT, "%.2f", expectedAncillariesTotal()) + " of ancillaries was bought");
            }
        } else {
            assertMoney(soft, bundleLine, expectedTotal(Type.BUNDLE), "Itinerary - Bundle Amount");
            assertMoney(soft, mealLine, expectedTotal(Type.MEAL), "Itinerary - Meal Amount");
            assertMoney(soft, bagLine, expectedTotal(Type.BAGGAGE), "Itinerary - Baggage Amount");
            assertMoney(soft, ssrLine, expectedAncillariesTotal(), "Itinerary - Total SSR Amount");
        }
        // Portal "Total Booking Amount" is the exact amount charged (seen live: 29,437.40 EGP = wallet
        // deduction). Admin's itinerary rounds the whole total instead (14,375.7 -> 14,376.00).
        Money totalBooking = readOptionalMoney(costingValue("Total Booking Amount:"));
        if (totalBooking != ABSENT) {
            assertMoney(soft, totalBooking, expectedNetPayable(), "Itinerary - Total Booking Amount (exact, as charged)");
        } else {
            soft.fail(Severity.MAJOR, "Itinerary - Costing & Payment 'Total Booking Amount' not found");
        }
        Money payable = readOptionalMoney(costingValue("Total Cost Payable :"));
        if (payable != ABSENT) {
            assertMoney(soft, payable, expectedNetPayable(), "Itinerary - Total Cost Payable");
        } else {
            System.out.println("Itinerary - 'Total Cost Payable' not shown on the Portal Costing tab; skipped");
        }

        // Sales - Net = Profit (no fixed expected value: markup depends on configuration). An agency
        // may not be shown all three -- checked only when they are.
        Money sales = readOptionalMoney(costingValue("Total Sales Amount:"));
        Money net = readOptionalMoney(costingValue("Total Net Amount:"));
        Money profit = readOptionalMoney(costingValue("Total Profit:"));
        if (sales != ABSENT && net != ABSENT && profit != ABSENT) {
            assertProfitConsistent(soft, "Itinerary - Costing & Payment", sales, net, profit);
        } else {
            System.out.println("Itinerary - Total Sales / Net / Profit not all shown on the Portal (sales " + sales + ", net " + net + ", profit " + profit + "); profit check skipped");
        }

        // Right-hand sidebar Costing card must agree with the Costing & Payment tab (when the Portal shows it).
        Money sidebarBooking = readOptionalMoney(sidebarCosting("Total Booking Amount:"));
        if (sidebarBooking != ABSENT && totalBooking != ABSENT) {
            soft.check(Severity.CRITICAL, Math.abs(sidebarBooking.value - totalBooking.value) <= sidebarBooking.tolerance + totalBooking.tolerance + 1e-6,
                    "Itinerary - sidebar Costing card Total Booking Amount " + sidebarBooking + " must equal the Costing & Payment tab " + totalBooking);
        } else {
            System.out.println("Itinerary - sidebar Costing card Total Booking Amount not shown on the Portal; sidebar check skipped");
        }
        Money sidebarSales = readOptionalMoney(sidebarCosting("Total Sales Amount:"));
        Money sidebarNet = readOptionalMoney(sidebarCosting("Total Net Amount:"));
        Money tabSales = readOptionalMoney(costingValue("Total Sales Amount:"));
        Money tabNet = readOptionalMoney(costingValue("Total Net Amount:"));
        if (sidebarSales != ABSENT && tabSales != ABSENT) {
            soft.check(Severity.CRITICAL, Math.abs(sidebarSales.value - tabSales.value) <= sidebarSales.tolerance + tabSales.tolerance + 1e-6,
                    "Itinerary - sidebar Total Sales Amount " + sidebarSales + " must equal the Costing & Payment tab " + tabSales);
        }
        if (sidebarNet != ABSENT && tabNet != ABSENT) {
            soft.check(Severity.CRITICAL, Math.abs(sidebarNet.value - tabNet.value) <= sidebarNet.tolerance + tabNet.tolerance + 1e-6,
                    "Itinerary - sidebar Total Net Amount " + sidebarNet + " must equal the Costing & Payment tab " + tabNet);
        }
        backToItineraryDetails();
        return this;
    }

    // Total Sales - Total Net must equal Total Profit (all three as shown).
    private void assertProfitConsistent(Checks soft, String where, Money sales, Money net, Money profit) {
        if (sales == ABSENT || net == ABSENT || profit == ABSENT) {
            soft.fail(Severity.MAJOR, where + " - Total Sales / Total Net / Total Profit not all shown (sales " + sales + ", net " + net + ", profit " + profit + ")");
            return;
        }
        double tolerance = sales.tolerance + net.tolerance + profit.tolerance + 1e-6;
        soft.check(Severity.CRITICAL, Math.abs((sales.value - net.value) - profit.value) <= tolerance,
                where + " - Total Sales " + sales + " - Total Net " + net + " must equal Total Profit " + profit
                        + " (difference " + String.format(Locale.ROOT, "%.3f", sales.value - net.value - profit.value) + ")");
    }


    // Itinerary Details tab:
    //  - Segment Ancillaries (Meals, Baggage, Seats): a tab per passenger -- repeated once per
    //    journey on round trip / multi-city, so all of a passenger's tabs are read together;
    //    baggage chips ("10Kg Baggage EGP 1914.0"), meal chips grouped under their segment route
    //    (code, price, description); a passenger without anything carries the "no ancillaries" badge.
    //  - Journey Details: "Bundle Services" lists the chosen bundle's services.
    private void assertItineraryAncillaryData(Checks soft) {
        List<WebElement> paxTabs = driver.getDriver().findElements(itineraryAncillaryPax);
        // Portal (seen live, booking without meals/baggage): no Segment Ancillaries section at all, so
        // there is no "no ancillaries" badge to check. With meals/baggage bought it must show them.
        boolean ssrBought = !selectionsOf(Type.MEAL).isEmpty() || !selectionsOf(Type.BAGGAGE).isEmpty();
        if (paxTabs.isEmpty()) {
            if (ssrBought) {
                soft.fail(Severity.MAJOR, "Itinerary - no Segment Ancillaries section, but meals/baggage were bought: "
                        + selectionsOf(Type.MEAL) + " " + selectionsOf(Type.BAGGAGE));
            } else {
                System.out.println("Itinerary - no Segment Ancillaries section on the Portal (no meals/baggage bought)");
            }
        }
        for (String paxName : paxTabs.isEmpty() ? new ArrayList<String>() : paxNames()) {
            List<WebElement> tabs = new ArrayList<>();
            for (WebElement t : paxTabs) {
                if (text(t.findElements(By.xpath(".//span[@class='pax-name']"))).equals(paxName)) tabs.add(t);
            }
            if (tabs.isEmpty()) {
                soft.fail(Severity.MAJOR, "Itinerary - Segment Ancillaries has no tab for " + paxName);
                continue;
            }
            List<Selection> meals = selectionsFor(Type.MEAL, paxName);
            List<Selection> bags = selectionsFor(Type.BAGGAGE, paxName);
            if (meals.isEmpty() && bags.isEmpty()) {
                boolean allBadged = tabs.stream().allMatch(t -> !t.findElements(By.xpath(".//div[contains(@class,'no-ancillaries-badge')]")).isEmpty());
                soft.check(Severity.MINOR, allBadged, "Itinerary - " + paxName + " has no ancillaries and should show the no-ancillaries badge");
                continue;
            }

            List<String> bagChips = new ArrayList<>();
            List<WebElement> mealChipElements = new ArrayList<>();
            List<WebElement> routeSections = new ArrayList<>();
            for (WebElement t : tabs) {
                bagChips.addAll(textsOf(t.findElements(By.xpath(".//div[contains(@class,'baggage-chip')]"))));
                mealChipElements.addAll(t.findElements(By.xpath(".//div[contains(@class,'meal-chip')]")));
                routeSections.addAll(t.findElements(By.xpath(".//div[contains(@class,'route-section')]")));
            }
            soft.equal(Severity.CRITICAL, bagChips.size(), bags.size(), "Itinerary - " + paxName + " baggage items (each one is charged) " + bagChips + " vs bought " + bags);
            for (Selection b : bags) {
                boolean found = bagChips.stream().anyMatch(c -> c.startsWith(b.name)
                        && moneyMatches(money(c.substring(b.name.length())), b.price));
                soft.check(Severity.CRITICAL, found, "Itinerary - " + paxName + " should have baggage '" + b.name + "' at " + b.price + " but chips are " + bagChips);
            }

            soft.equal(Severity.CRITICAL, mealChipElements.size(), meals.size(), "Itinerary - " + paxName + " meal items (each one is charged) vs bought " + meals);
            for (Selection m : meals) {
                WebElement section = null;
                for (WebElement rs : routeSections) {
                    List<String> airports = textsOf(rs.findElements(By.xpath(".//span[@class='ssr-card__airport']")));
                    if (String.join("-", airports).equals(m.route)) section = rs;
                }
                if (section == null) {
                    soft.fail(Severity.MAJOR, "Itinerary - " + paxName + " has no meal section for segment " + m.route);
                    continue;
                }
                WebElement chip = null;
                List<String> shownMeals = new ArrayList<>();
                for (WebElement c : section.findElements(By.xpath(".//div[contains(@class,'meal-chip')]"))) {
                    // flynas: <span class="item-name"><span>BBML</span>...; Al Jazeera: <span class="item-name"><i/>SRML</span> -- first word of the whole text.
                    String code = text(c.findElements(By.xpath(".//span[contains(@class,'item-name')]"))).split(" ")[0];
                    shownMeals.add(code + " " + text(c.findElements(By.xpath(".//span[contains(@class,'item-price')]"))));
                    if (code.equals(m.name)) chip = c;
                }
                if (chip == null) {
                    soft.fail(Severity.MAJOR, "Itinerary - " + paxName + " segment " + m.route + " should show the booked meal "
                            + m.name + " (" + m.description + ", " + m.price + ") but shows " + shownMeals);
                    continue;
                }
                assertMoney(soft, money(text(chip.findElements(By.xpath(".//span[contains(@class,'item-price')]")))), m.price,
                        "Itinerary - " + paxName + " " + m.route + " meal " + m.name + " price");
                soft.equal(Severity.MAJOR, text(chip.findElements(By.xpath(".//div[contains(@class,'item-description')]"))), m.description,
                        "Itinerary - " + paxName + " " + m.route + " meal " + m.name + " description");
            }
        }

        List<List<String>> bundleLists = new ArrayList<>();
        for (WebElement ul : driver.getDriver().findElements(By.xpath("//p[contains(@class,'label')][normalize-space()='Bundle Services']/following-sibling::ul[1]")))
            bundleLists.add(textsOf(ul.findElements(By.xpath("./li"))));
        // One "Bundle Services" list per journey, in journey order.
        for (Selection b : selectionsOf(Type.BUNDLE)) {
            String where = "Itinerary - Journey Details 'Bundle Services' for '" + b.name + "' on " + b.route;
            if (bundleLists.size() < b.index) {
                soft.fail(Severity.MAJOR, where + " not found (lists on page: " + bundleLists + ")");
                continue;
            }
            assertServices(soft, where, bundleLists.get(b.index - 1), b.services);
        }
    }

    // Bundle services must match what the bundle card showed when it was chosen. Common failures:
    // the same text split differently -- "40KG Check-in Baggage (BULK, FRAG)" shown as two bullets
    // because the page splits on commas -- or the raw list printed ("[7KG/1piece carry-on baggage]").
    private void assertServices(Checks soft, String where, List<String> shown, List<String> expected) {
        if (shown.equals(expected)) return;
        boolean sameText = String.join(", ", shown).equals(String.join(", ", expected));
        boolean bracketed = String.join(", ", shown).replaceAll("^\\[|]$", "").equals(String.join(", ", expected));
        soft.fail(Severity.MAJOR, where + (sameText
                ? ": same services but split differently (a service containing a comma is shown as separate items)"
                : bracketed ? ": same services but shown as a raw list with brackets"
                : ": services differ")
                + "\n    chosen: " + expected + "\n    shown:  " + shown);
    }

    // HARD: what the booking actually received must be the full total including ancillaries. Without
    // the costing tab on the Portal, the wallet check (assertWalletDeducted) is what proves the charge.
    public Ancillaries_Page assertItineraryAmountReceived(Checks soft) throws InterruptedException {
        if (!openCostingTab()) return this;
        Money received = readOptionalMoney(costingValue("Amount Received :"));
        if (received != ABSENT) {
            hardMoney(soft, received, expectedNetPayable(), "Itinerary - Amount Received");
        } else {
            System.out.println("Itinerary - 'Amount Received' not shown on the Portal; checked through the wallet balance instead");
        }
        backToItineraryDetails();
        return this;
    }

    public boolean isETicketAvailable() {
        return !driver.getDriver().findElements(eTicketButton).isEmpty();
    }

    // ================================================================= assertions: wallet (Portal)

    // Replaces Admin's AR Ledger step: the Portal sidebar wallet balance after the booking must be the
    // balance read before it minus exactly the amount charged (Net Payable, ancillaries included).
    // Assumes nothing else charges or tops up this agency's wallet while the scenario runs.
    private void assertWalletDeducted(Checks soft, String bookingReference) throws InterruptedException {
        if (walletTopUpBefore == null) {
            soft.fail(Severity.MAJOR, "Wallet - balance before payment was not shown in the Portal sidebar; deduction for "
                    + bookingReference + " not checked");
            return;
        }
        double expectedBalance = walletTopUpBefore.value - expectedNetPayable();
        Money after = null;
        for (int attempt = 1; attempt <= 3; attempt++) {
            after = readWalletBalance();
            if (after != null && Math.abs(after.value - walletTopUpBefore.value) > 1e-6) break;
            if (attempt < 3) Thread.sleep(10000); // the wallet can update a little after the booking
        }
        if (after == null) {
            soft.fail(Severity.MAJOR, "Wallet - balance after booking " + bookingReference + " not shown in the Portal sidebar");
            return;
        }
        System.out.println("Wallet - " + bookingReference + ": before " + walletTopUpBefore + ", after " + after
                + ", charged " + String.format(Locale.ROOT, "%.3f", walletTopUpBefore.value - after.value));
        // Both readings carry display precision, so the allowed difference is the sum of the two.
        Money balance = new Money(after.value, after.tolerance + walletTopUpBefore.tolerance, after.shown);
        assertMoney(soft, balance, expectedBalance, "Wallet - balance before payment " + walletTopUpBefore
                + " minus Net Payable " + String.format(Locale.ROOT, "%.2f", expectedNetPayable())
                + " must equal the balance after booking " + bookingReference);
    }

    // ================================================================= helpers

    private Money readMoney(By by) {
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(30))
                .until(ExpectedConditions.presenceOfElementLocated(by));
        return money(driver.getDriver().findElement(by).getAttribute("textContent"));
    }

    // Rows for an ancillary type only exist once something of that type is bought.
    private Money readOptionalMoney(By by) {
        List<WebElement> e = driver.getDriver().findElements(by);
        return e.isEmpty() ? ABSENT : money(e.get(0).getAttribute("textContent"));
    }

    // Round trip / multi-city documents repeat the fare block per journey -- add them all up.
    private Money sumMoney(By by) {
        List<Money> parts = new ArrayList<>();
        for (WebElement e : driver.getDriver().findElements(by)) parts.add(money(e.getAttribute("textContent")));
        return parts.isEmpty() ? ABSENT : sum(parts);
    }

    private static Money sum(List<Money> parts) {
        double value = 0, tolerance = 0;
        List<String> shown = new ArrayList<>();
        for (Money m : parts) {
            value += m.value;
            tolerance += m.tolerance;
            shown.add(m.shown);
        }
        return new Money(value, tolerance, parts.size() == 1 ? shown.get(0) : String.join(" + ", shown));
    }

    private static Money subtract(Money a, Money b) {
        return new Money(a.value - b.value, a.tolerance + b.tolerance, a.shown + (b.value == 0 ? "" : " - " + b.shown));
    }

    // Adults/children in ancillaries-step order (infants never get a tab).
    private List<String> paxNames() {
        return ancillaryPaxNames;
    }

    private List<Selection> selectionsOf(Type type) {
        List<Selection> out = new ArrayList<>();
        for (Selection s : selections.values()) if (s.type == type) out.add(s);
        return out;
    }

    private List<Selection> selectionsFor(Type type, String paxName) {
        List<Selection> out = new ArrayList<>();
        for (Selection s : selectionsOf(type)) if (s.paxName.equals(paxName)) out.add(s);
        return out;
    }

    // 1-based column of a header in a table (0 when missing).
    private int columnIndex(String tableXpath, String header) {
        List<WebElement> ths = driver.getDriver().findElements(By.xpath(tableXpath + "//thead//th"));
        for (int i = 0; i < ths.size(); i++) {
            if (clean(ths.get(i).getAttribute("textContent")).equals(header)) return i + 1;
        }
        return 0;
    }

    private WebElement rowFor(List<WebElement> rows, String paxName) {
        for (WebElement r : rows) {
            if (clean(r.getAttribute("textContent")).contains(paxName)) return r;
        }
        return null;
    }

    private String cellText(WebElement row, int column) {
        if (column <= 0) return "";
        return text(row.findElements(By.xpath("./td[" + column + "]")));
    }

    // Normalized textContent of the first element (works for collapsed/hidden content too).
    private static String text(List<WebElement> elements) {
        return elements.isEmpty() ? "" : clean(elements.get(0).getAttribute("textContent"));
    }

    private String textOf(By by) {
        return text(driver.getDriver().findElements(by));
    }

    private static List<String> textsOf(List<WebElement> elements) {
        List<String> out = new ArrayList<>();
        for (WebElement e : elements) out.add(clean(e.getAttribute("textContent")));
        return out;
    }

    // textContent with non-breaking spaces (U+00A0, used by some pages) and runs of whitespace collapsed.
    private static final char NBSP = (char) 0x00A0;

    private static String clean(String s) {
        return s == null ? "" : s.replace(NBSP, ' ').replaceAll("\\s+", " ").trim();
    }

    private List<String> textsOf(By by) {
        return textsOf(driver.getDriver().findElements(by));
    }


    private static final Pattern AMOUNT_BEFORE_CURRENCY = Pattern.compile("([\\d,]+(?:\\.\\d+)?)\\s*[A-Z]{3}\\b");
    private static final Pattern AMOUNT_AFTER_CURRENCY = Pattern.compile("\\b[A-Z]{3}\\s*([\\d,]+(?:\\.\\d+)?)");
    private static final Pattern ANY_AMOUNT = Pattern.compile("([\\d,]+(?:\\.\\d+)?)");

    // "+ 4,292 EGP", "273.00 EGP", "20Kg Baggage - ( 4,101 EGP )", "16,667EGP", "EGP 1914.0", " 2,010 EGP".
    public static double parseAmount(String text) {
        if (text == null) return 0;
        for (Pattern p : new Pattern[]{AMOUNT_BEFORE_CURRENCY, AMOUNT_AFTER_CURRENCY, ANY_AMOUNT}) {
            Matcher m = p.matcher(text);
            if (m.find()) return Double.parseDouble(m.group(1).replace(",", ""));
        }
        return 0;
    }

    private void expandIfCollapsed(String accordionTab) {
        List<WebElement> header = driver.getDriver().findElements(By.xpath(accordionTab + "//a[@role='tab'][1]"));
        if (!header.isEmpty() && "false".equals(header.get(0).getAttribute("aria-expanded"))) {
            scrollTo(header.get(0));
            header.get(0).click();
            try { Thread.sleep(800); } catch (InterruptedException ignored) { }
        }
    }

    private void scrollTo(WebElement e) {
        ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", e);
        try { Thread.sleep(300); } catch (InterruptedException ignored) { }
    }

    private static String xq(String value) {
        if (!value.contains("'")) return "'" + value + "'";
        return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }
}
