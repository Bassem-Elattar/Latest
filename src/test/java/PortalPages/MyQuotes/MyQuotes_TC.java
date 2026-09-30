package PortalPages.MyQuotes;

import AdminPages.BookingMidOffice.Booking.SearchBookingBranch;
import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.DataUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Portal - Save Quote from flight results, My Quotes search, Select Flight popup, reopening the
 */
public class MyQuotes_TC {

    private SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private MyQuotes_Page myQuotesPage;

    private String portalUrl;
    private LocalDate travelDate;

    /** Quote saved once in @BeforeClass and only read by the search / popup tests. */
    private SavedQuote sharedQuote;

    /** What was on the ticked flight card when the quote was saved, plus the new quotation id. */
    private static class SavedQuote {
        String quotationId;
        String airline;
        String flightNumber;
        String departureTime;
        String arrivalTime;
        String price;
    }

    @BeforeClass
    public void setup() {
        testData = new SHAFT.TestData.JSON("PortalMyQuotes.json");
        portalUrl = config("Portal_Url");
        travelDate = LocalDate.now().plusDays(Integer.parseInt(testData.getTestData("search.daysFromToday")));

        CommonMethod.setupDriver(config("browser"));
        driver = CommonMethod.getDriver();

        new Login_Page(driver)
                .navigateToLoginPage(portalUrl)
                .enterAgencyCode(config("Portal_AgencyCode"))
                .enterEmail(config("Portal_Email"))
                .enterPassword(config("Portal_Password"))
                .clickLoginButton()
                .waitUntilLoggedIn();

        myQuotesPage = new MyQuotes_Page(driver);
        sharedQuote = searchAndSaveQuoteAsNewUser();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        CommonMethod.quitDriver();
    }

    // ==================== A. Save Quote from the flight results ====================

    @Test
    public void TC01_verifyThatSaveQuoteWithNoFlightTickedShowsSelectFlightMessage() {
        searchFlights();
        myQuotesPage.clickSaveQuote();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                myQuotesPage.getSelectFlightValidationText(),
                testData.getTestData("expected.selectFlightValidation"),
                "Save Quote with no flight ticked should show the select-a-flight message");
        softAssert.assertFalse(
                myQuotesPage.isSaveQuoteDialogDisplayed(),
                "Save Quote with no flight ticked should not open the Save Quote dialog");
        softAssert.assertAll();
    }

    @Test
    public void TC02_verifyThatEmptyNewUserSaveQuoteShowsRequiredErrorsAndKeepsDialogOpen() {
        searchFlights();
        myQuotesPage.tickFlightCard(cardToQuote())
                    .clickSaveQuote()
                    .selectNewUser()
                    .clickConfirmSaveQuote();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                myQuotesPage.getSaveQuoteFieldError(testData.getTestData("saveQuoteField.firstName")),
                testData.getTestData("expected.firstNameRequired"),
                "Empty First Name should show its required message");
        softAssert.assertEquals(
                myQuotesPage.getSaveQuoteFieldError(testData.getTestData("saveQuoteField.lastName")),
                testData.getTestData("expected.lastNameRequired"),
                "Empty Last Name should show its required message");
        softAssert.assertEquals(
                myQuotesPage.getSaveQuoteFieldError(testData.getTestData("saveQuoteField.email")),
                testData.getTestData("expected.emailRequired"),
                "Empty Email should show its required message");
        softAssert.assertEquals(
                myQuotesPage.getSaveQuoteFieldError(testData.getTestData("saveQuoteField.phone")),
                testData.getTestData("expected.phoneRequired"),
                "Empty Phone should show its required message");
        softAssert.assertTrue(
                myQuotesPage.isSaveQuoteDialogDisplayed(),
                "The Save Quote dialog should stay open when required fields are empty");
        softAssert.assertFalse(
                myQuotesPage.isQuoteSavedDialogDisplayed(),
                "No quote should be saved when required fields are empty");
        softAssert.assertAll();
    }

    @Test
    public void TC03_verifyThatSavingQuoteAsNewUserShowsQuotationIdInExpectedFormat() {
        String quotationId = searchAndSaveQuoteAsNewUser().quotationId;

        String expectedPattern = testData.getTestData("quote.idCountryPrefix")
                + config("Portal_AgencyCode")
                + testData.getTestData("quote.branchCode")
                + format(LocalDate.now(), "dateFormat.quotationId")
                + testData.getTestData("quote.idRandomPartPattern");
        Assert.assertTrue(
                quotationId.matches(expectedPattern),
                "Quotation ID '" + quotationId + "' should match " + expectedPattern);
    }

    // ==================== B. My Quotes search ====================

    @Test
    public void TC04_verifyThatMyQuotesSearchWithoutStatusShowsRequiredAndNoResults() {
        myQuotesPage.navigateToMyQuotes(portalUrl)
                    .enterQuotesNo(sharedQuote.quotationId)
                    .clickSearch();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
                myQuotesPage.getQuoteStatusErrorText(),
                testData.getTestData("expected.quoteStatusRequired"),
                "Search without Quote Status should show Required under Quote Status");
        softAssert.assertEquals(
                myQuotesPage.getQuoteRowsCount(sharedQuote.quotationId), 0,
                "Search without Quote Status should not list the quote");
        softAssert.assertAll();
    }

    @Test
    public void TC05_verifyThatSearchByQuotationIdReturnsOneRowMatchingTheSavedQuote() {
        myQuotesPage.navigateToMyQuotes(portalUrl)
                    .enterQuotesNo(sharedQuote.quotationId)
                    .selectQuoteStatus(testData.getTestData("status.open"))
                    .clickSearch()
                    .waitForQuoteRow(sharedQuote.quotationId);

        String id = sharedQuote.quotationId;
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(myQuotesPage.getQuoteRowsCount(id), 1,
                "Searching by quotation id should return exactly one row");
        softAssert.assertEquals(gridCell(id, "quotesNo"),
                id + " (" + testData.getTestData("search.tripType") + ")",
                "Quotes No should show the id and the trip type");
        softAssert.assertEquals(gridCell(id, "clientName"), testData.getTestData("expected.clientName"),
                "Client / Passenger Name should be the New user's first + last name");
        softAssert.assertEquals(gridCell(id, "mobileNo"), testData.getTestData("expected.mobileNo"),
                "Mobile No should be the New user's phone with the +20 country code");
        softAssert.assertEquals(gridCell(id, "emailId"), testData.getTestData("quote.newUser.email"),
                "Email ID should be the New user's email");
        softAssert.assertEquals(gridCell(id, "productType"), testData.getTestData("expected.productType"),
                "Product Type should be Flight");
        softAssert.assertEquals(gridCell(id, "creationDate"), format(LocalDate.now(), "dateFormat.grid"),
                "Creation Date should be today");
        softAssert.assertEquals(gridCell(id, "agentName"), testData.getTestData("expected.agentName"),
                "Agent Name should be the logged-in agency");
        softAssert.assertEquals(gridCell(id, "travelDate"), format(travelDate, "dateFormat.grid"),
                "Travel Date should be the searched departure date");
        softAssert.assertEquals(gridCell(id, "status"), testData.getTestData("status.open"),
                "A new quote should be Open");
        softAssert.assertAll();
    }

    @Test
    public void TC06_verifyThatSearchByClientNameReturnsTheSavedQuote() {
        myQuotesPage.navigateToMyQuotes(portalUrl)
                    .enterClientName(testData.getTestData("expected.clientName"))
                    .selectQuoteStatus(testData.getTestData("status.open"))
                    .clickSearch()
                    .waitForQuoteRow(sharedQuote.quotationId);

        Assert.assertEquals(myQuotesPage.getQuoteRowsCount(sharedQuote.quotationId), 1,
                "Searching by the client name should list the saved quote");
    }

    @Test
    public void TC07_verifyThatSearchByEmailReturnsTheSavedQuote() {
        myQuotesPage.navigateToMyQuotes(portalUrl)
                    .enterEmailId(testData.getTestData("quote.newUser.email"))
                    .selectQuoteStatus(testData.getTestData("status.open"))
                    .clickSearch()
                    .waitForQuoteRow(sharedQuote.quotationId);

        Assert.assertEquals(myQuotesPage.getQuoteRowsCount(sharedQuote.quotationId), 1,
                "Searching by the client email should list the saved quote");
    }

    @Test
    public void TC08_verifyThatSearchByTravelDateRangeReturnsTheSavedQuote() {
        myQuotesPage.navigateToMyQuotes(portalUrl)
                    .selectQuoteStatus(testData.getTestData("status.open"))
                    .setTravelDateRange(
                            format(travelDate.minusDays(1), "dateFormat.searchForm"),
                            format(travelDate.plusDays(1), "dateFormat.searchForm"))
                    .clickSearch()
                    .waitForQuoteRow(sharedQuote.quotationId);

        Assert.assertEquals(gridCell(sharedQuote.quotationId, "travelDate"), format(travelDate, "dateFormat.grid"),
                "A travel-date range around the trip date should list the saved quote with that travel date");
    }

    // ==================== C. Select Flight popup ====================

    @Test
    public void TC09_verifyThatQuotesNoLinkOpensSelectFlightWithTheTickedFlightDetails() {
        openSelectFlightPopup(sharedQuote.quotationId);

        SoftAssert softAssert = new SoftAssert();
        assertSelectFlightRowMatches(sharedQuote, softAssert);
        softAssert.assertAll();
    }

    @Test
    public void TC10_verifyThatClosingSelectFlightPopupKeepsAgentOnMyQuotes() {
        openSelectFlightPopup(sharedQuote.quotationId);
        myQuotesPage.closeSelectFlightDialog();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(myQuotesPage.isSelectFlightDialogDisplayed(),
                "Close should close the Select Flight popup");
        softAssert.assertEquals(myQuotesPage.getQuoteRowsCount(sharedQuote.quotationId), 1,
                "After Close the My Quotes grid should still show the quote");
        softAssert.assertAll();
    }

    // ==================== D. Open the trip from the quote (main scenario) ====================

    @Test
    public void TC11_verifyThatSelectingTheQuotedFlightOpensPassengerDetailsForTheSameTrip() {
        SavedQuote quote = searchAndSaveQuoteAsNewUser();
        openSelectFlightPopup(quote.quotationId);

        SoftAssert softAssert = new SoftAssert();
        assertSelectFlightRowMatches(quote, softAssert);

        myQuotesPage.clickSelectFlightRow(1)
                    .expandSegmentsDetails();

        String tripDate = format(travelDate, "dateFormat.passengerDetails");
        List<String> chips = myQuotesPage.getTripChips();
        softAssert.assertTrue(
                myQuotesPage.getCurrentUrl().contains(testData.getTestData("expected.quotationIdUrlParam") + quote.quotationId),
                "Passenger Details URL should carry the quotation id. Actual: " + myQuotesPage.getCurrentUrl());
        softAssert.assertTrue(chips.contains(testData.getTestData("search.tripType")),
                "Trip chips should show the trip type. Actual: " + chips);
        softAssert.assertTrue(chips.contains(testData.getTestData("expected.passengersChip")),
                "Trip chips should show the searched passenger count. Actual: " + chips);
        softAssert.assertTrue(chips.contains(testData.getTestData("expected.tripDepartureChipPrefix") + tripDate),
                "Trip chips should show the searched departure date. Actual: " + chips);
        softAssert.assertEquals(myQuotesPage.getDepartureAirport(), testData.getTestData("search.origin"),
                "Departure airport should be the searched origin");
        softAssert.assertEquals(myQuotesPage.getArrivalAirport(), testData.getTestData("search.destination"),
                "Arrival airport should be the searched destination");
        softAssert.assertEquals(myQuotesPage.getDepartureDateTime(), quote.departureTime + " " + tripDate,
                "Departure time and date should match the ticked flight card");
        softAssert.assertTrue(myQuotesPage.getArrivalDateTime().startsWith(quote.arrivalTime + " "),
                "Arrival time should match the ticked flight card. Actual: " + myQuotesPage.getArrivalDateTime());
        // The page stores "economy" and capitalises it with CSS, so the text read back is lower case.
        softAssert.assertTrue(myQuotesPage.getCabinClass().equalsIgnoreCase(testData.getTestData("search.cabin")),
                "Cabin should be the searched cabin. Actual: " + myQuotesPage.getCabinClass());
        softAssert.assertEquals(amount(myQuotesPage.getTotalPrice()).compareTo(amount(quote.price)), 0,
                "Total fare " + myQuotesPage.getTotalPrice() + " should equal the ticked card price " + quote.price);
        softAssert.assertAll();
    }

    // ==================== E. Reject a quote ====================

    @Test
    public void TC12_verifyThatRejectWithoutRemarkShowsRequiredAndKeepsQuoteOpen() {
        searchQuoteById(sharedQuote.quotationId, testData.getTestData("status.open"));
        myQuotesPage.clickRejectIcon(sharedQuote.quotationId)
                    .clickReject();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(myQuotesPage.getRejectRemarksErrorText(),
                testData.getTestData("expected.rejectRemarkRequired"),
                "Reject without a remark should show the required message under Remarks");
        softAssert.assertTrue(myQuotesPage.isRejectDialogDisplayed(),
                "Reject without a remark should keep the Reject dialog open");

        myQuotesPage.closeDialog();
        softAssert.assertEquals(gridCell(sharedQuote.quotationId, "status"), testData.getTestData("status.open"),
                "The quote should still be Open after a Reject attempt with no remark");
        softAssert.assertAll();
    }

    @Test
    public void TC13_verifyThatRejectingOpenQuoteWithRemarkShowsSuccessAndChangesStatusToReject() {
        String quotationId = searchAndSaveQuoteAsNewUser().quotationId;

        searchQuoteById(quotationId, testData.getTestData("status.open"));
        myQuotesPage.clickRejectIcon(quotationId)
                    .selectRejectRemark(testData.getTestData("quote.rejectRemark"))
                    .clickReject();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(myQuotesPage.getToastText(), testData.getTestData("expected.rejectSuccessToast"),
                "Rejecting with a remark should show the status-updated toast");

        searchQuoteById(quotationId, testData.getTestData("status.reject"));
        softAssert.assertEquals(gridCell(quotationId, "status"), testData.getTestData("status.reject"),
                "The rejected quote should have status Reject");
        softAssert.assertAll();
    }

    @Test
    public void TC14_verifyThatRejectedQuoteHasNoRejectIconAndNoQuotesNoLink() {
        String quotationId = searchAndSaveQuoteAsNewUser().quotationId;

        searchQuoteById(quotationId, testData.getTestData("status.open"));
        myQuotesPage.clickRejectIcon(quotationId)
                    .selectRejectRemark(testData.getTestData("quote.rejectRemark"))
                    .clickReject();
        searchQuoteById(quotationId, testData.getTestData("status.reject"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(myQuotesPage.isRejectIconDisplayed(quotationId),
                "A rejected quote should not offer the Reject action again");
        softAssert.assertFalse(myQuotesPage.isQuotesNoALink(quotationId),
                "A rejected quote's Quotes No should not be a link");
        softAssert.assertAll();
    }

    // ==================== Flow steps shared by several tests ====================

    /** Search form steps come from the Admin SearchBookingBranch, which works unchanged on the Portal. */
    private void searchFlights() {
        myQuotesPage.navigateToFlightSearch(portalUrl);
        new SearchBookingBranch(driver)
                .AddStartingFrom(testData.getTestData("search.origin"))
                .AddGoingTo(testData.getTestData("search.destination"))
                .passengersDropDown()
                .SelectNumberOfAdult(Integer.parseInt(testData.getTestData("search.adults")))
                .SelectNumberOfChildren(Integer.parseInt(testData.getTestData("search.children")))
                .SelectNumberOfInfant(Integer.parseInt(testData.getTestData("search.infants")));
        myQuotesPage.enterDepartureDate(format(travelDate, "dateFormat.searchForm"));
        new SearchBookingBranch(driver).clickOnSearchButton();
        myQuotesPage.waitForFlightResults();
    }

    /** Searches, ticks the configured card, saves it for a New user, and returns what was quoted. */
    private SavedQuote searchAndSaveQuoteAsNewUser() {
        searchFlights();
        int card = cardToQuote();

        SavedQuote quote = new SavedQuote();
        quote.airline = myQuotesPage.getCardAirline(card);
        quote.flightNumber = myQuotesPage.getCardFlightNumber(card);
        quote.departureTime = myQuotesPage.getCardDepartureTime(card);
        quote.arrivalTime = myQuotesPage.getCardArrivalTime(card);
        quote.price = myQuotesPage.getCardPrice(card);

        myQuotesPage.tickFlightCard(card)
                    .clickSaveQuote()
                    .selectNewUser()
                    .enterFirstName(testData.getTestData("quote.newUser.firstName"))
                    .enterLastName(testData.getTestData("quote.newUser.lastName"))
                    .enterEmail(testData.getTestData("quote.newUser.email"))
                    .enterPhone(testData.getTestData("quote.newUser.phone"))
                    .clickConfirmSaveQuote();
        quote.quotationId = myQuotesPage.getQuotationId();
        myQuotesPage.clickQuoteSavedOk();
        return quote;
    }

    private void searchQuoteById(String quotationId, String status) {
        myQuotesPage.navigateToMyQuotes(portalUrl)
                    .enterQuotesNo(quotationId)
                    .selectQuoteStatus(status)
                    .clickSearch()
                    .waitForQuoteRow(quotationId);
    }

    private void openSelectFlightPopup(String quotationId) {
        searchQuoteById(quotationId, testData.getTestData("status.open"));
        myQuotesPage.clickQuotesNoLink(quotationId);
    }

    /** Select Flight row 1 against the ticked card (regression for bug 52032 - details missing after the link). */
    private void assertSelectFlightRowMatches(SavedQuote quote, SoftAssert softAssert) {
        String flightCell = myQuotesPage.getSelectFlightCell(1, "flight");   // e.g. "RX 9331 | RiyadhAirKSA"
        String[] flightParts = flightCell.split("\\|");

        softAssert.assertEquals(myQuotesPage.getSelectFlightRowsCount(), 1,
                "A quote for one ticked flight should list exactly one flight");
        softAssert.assertEquals(myQuotesPage.getSelectFlightCell(1, "airline"), quote.airline,
                "Airline should match the ticked card");
        softAssert.assertEquals(flightCode(flightParts[0]), flightCode(quote.flightNumber),
                "Flight number " + flightCell + " should match the ticked card " + quote.flightNumber);
        softAssert.assertEquals(flightParts.length > 1 ? flightParts[1].trim() : "",
                testData.getTestData("expected.supplierName"),
                "Flight column should name the supplier. Actual: " + flightCell);
        softAssert.assertEquals(myQuotesPage.getSelectFlightCell(1, "tripType"), testData.getTestData("search.tripType"),
                "Trip Type should be the searched trip type");
        softAssert.assertEquals(myQuotesPage.getSelectFlightCell(1, "origin"), testData.getTestData("search.origin"),
                "Origin should be the searched origin");
        softAssert.assertEquals(myQuotesPage.getSelectFlightCell(1, "destination"), testData.getTestData("search.destination"),
                "Destination should be the searched destination");
        softAssert.assertEquals(myQuotesPage.getSelectFlightCell(1, "date"), format(travelDate, "dateFormat.selectFlightPopup"),
                "Date should be the searched departure date");
        softAssert.assertTrue(myQuotesPage.getSelectFlightCell(1, "departureTime").startsWith(quote.departureTime),
                "Departure Time should match the ticked card " + quote.departureTime);
        softAssert.assertEquals(amount(myQuotesPage.getSelectFlightCell(1, "amount")).compareTo(amount(quote.price)), 0,
                "Amount " + myQuotesPage.getSelectFlightCell(1, "amount") + " should equal the ticked card price " + quote.price);
    }

    // ==================== Small value helpers ====================

    private String gridCell(String quotationId, String columnKey) {
        return myQuotesPage.getGridCellText(quotationId, testData.getTestData("gridColumn." + columnKey));
    }

    private int cardToQuote() {
        return Integer.parseInt(testData.getTestData("search.cardToQuote"));
    }

    private String format(LocalDate date, String formatKey) {
        return date.format(DateTimeFormatter.ofPattern(testData.getTestData(formatKey), Locale.ENGLISH));
    }

    /** "RX-9331 (RBD - )" and "RX 9331 " both become "RX9331". */
    private static String flightCode(String displayed) {
        return displayed.split("\\(")[0].replaceAll("[^A-Za-z0-9]", "");
    }

    /** "8,489", "8,489 EGP" and "8,489.00EGP" all become 8489. */
    private static BigDecimal amount(String displayed) {
        return new BigDecimal(displayed.replaceAll("[^0-9.]", ""));
    }

    /** An environment variable wins over Environment.properties. */
    private static String config(String key) {
        String fromEnvironment = System.getenv(key);
        return fromEnvironment != null && !fromEnvironment.isEmpty() ? fromEnvironment : DataUtils.get(key);
    }
}
