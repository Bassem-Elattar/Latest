package PortalPages.BookingMidOffice.Booking;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import com.github.javafaker.Faker;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import utilities.FakerSingleton;

import java.time.Duration;
import java.util.List;

// Portal clone of AdminPages.BookingMidOffice.Booking.PaxDetailsPage. The passenger form is not
// branch-dependent, so this is kept as-is (locators are best-effort copies of Admin's, same
// underlying component library, TODO: confirm against real Portal DOM).
public class PaxDetailsPage {

    private SHAFT.GUI.WebDriver driver;
    private final SHAFT.TestData.JSON testData;
    SoftAssert softAssert = new SoftAssert();

    public PaxDetailsPage(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
        this.testData = new SHAFT.TestData.JSON("searchBookingBrData.json");
    }

    // Dynamic Locators
    // Confirmed live: Portal's Title/Nationality controls are <p-dropdown formcontrolname="...">
    // wrapping a readonly input whose id carries the passenger index (title_0, nationality_0, ...),
    // not the Admin-style "<label for=...>/following-sibling::p-dropdown" pattern.
    private By titleDropdown(int index) {
        return By.xpath("//p-dropdown[.//input[@id='title_" + index + "']]");
    }

    private By firstName(int index) {
        return By.id("firstName_" + index);
    }

    private By lastName(int index) {
        return By.id("lastName_" + index);
    }

    private By dateOfBirth(int index) {
        return By.id("dob_" + index);
    }

    private By email(int index) {
        return By.id("email_" + index);
    }

    private By phone(int index) {
        return By.id("phone_" + index);
    }

    private By documentNumber(int index) {
        return By.id("document_" + index);
    }

    private By documentExpiry(int index) {
        return By.id("docExpiry_" + index);
    }

    private By nationalityDropdown(int index) {
        return By.xpath("//label[@for='nationality_" + index + "']/following::p-dropdown[1]//div[@role='button']");
    }

    private By paxType(int index) {
        return By.xpath("(//span[@class='pax-logo ng-star-inserted'])[" + index + "]");
    }

    private By seeMore(int index) {
        return By.xpath("//a[@id='p-accordiontab-" + index + "']");
    }

    private final By saveQuoteBtn = By.xpath("//button[.//span[normalize-space()='Save Quote']]");
    private final By bookBtn = By.xpath("//p-button[@label='Book']/button");
    private final By confirmBookBtn = By.xpath("//button[contains(@class,'p-button-raised')]");
    private final By holdBtn = By.xpath("//p-button[@label='Hold']/button");
    private final By quoteSavedMsg = By.xpath("//span[normalize-space()='Quote Saved']");
    private final By infantAssignedTo = By.xpath("//input[contains(@id,'assigned-to_3')]");
    private final By termsSelect = By.xpath("(//div[contains(@class,'p-checkbox-box')])[last()]");
    private final By GDSPNR_Confirmation = By.xpath("//th[text()='GDS PNR Number']");
    private final By brandedFares = By.xpath("//p-carousel");
    private final By Btn_Proceed = By.xpath("(//button[@class='book-btn'])[1]");
    private final By Btn_ExpandAll = By.xpath("(//span[normalize-space()='Expand All'])[1]");
    By Btn_Next = By.xpath("(//button[@class='p-ripple p-element step-btn step-btn--primary p-button p-component'])[1]");
    private final By FirstMeal = By.xpath("(//div[@class='meal-option ng-star-inserted'])[1]");
    private final By FirstBaggage = By.xpath("(//p-dropdownitem[@class='p-element ng-star-inserted'])[1]");
    private final By ExpandAll = By.xpath("(//button[@type='button'])[4]");

    By assignedToDropdowns =
            By.xpath("//p-dropdown[@formcontrolname='assignedTo']");

    private By assignedToDropdown(int index) {
        return By.xpath("(//p-dropdown[@formcontrolname='assignedTo'])[" + index + "]");
    }

    private By dropdownOptionByIndex(int index) {
        return By.xpath("(//li[@role='option'])[" + index + "]");
    }

    private By dropdownOption(String value) {
        return By.xpath("//li[@aria-label='" + value + "']");
    }

    public void ElementClick(By by) {
        driver.element().click(by);
    }

    public void ElementType(By by, String value) {
        driver.element().type(by, value);
    }

    public PaxDetailsPage fillOnePassengerDetails(
            String title,
            String dob,
            String chDob,
            String infDob,
            String emailValue,
            String phoneValue,
            String documentExpiryValue,
            String nationality) {

        int total = parseCount("NumberOfAdults")
                + parseCount("NumberOfChildren")
                + parseCount("NumberOfInfants");
        driver.element().click(Btn_ExpandAll);
        for (int i = 0; i < total; i++) {

            // paxType XPath starts from 1
            String paxText = driver.element().getText(paxType(i + 1));

            ElementClick(titleDropdown(i));
            ElementClick(dropdownOption(title));

            ElementType(firstName(i),
                    FakerSingleton.PassengerFactory.firstName());

            ElementType(lastName(i),
                    FakerSingleton.PassengerFactory.lastName());

            ElementType(documentNumber(i),
                    FakerSingleton.PassengerFactory.documentNumber());

            ElementType(email(i), emailValue);
            ElementType(phone(i), phoneValue);

            ElementType(documentExpiry(i), documentExpiryValue);

            ElementClick(nationalityDropdown(i));
            ElementClick(dropdownOption(nationality));

            if (paxText.contains("Adult")) {

                ElementType(dateOfBirth(i), dob);
                System.out.println("Adult Passenger : " + (i + 1));

            } else if (paxText.contains("Child")) {

                ElementType(dateOfBirth(i), chDob);
                System.out.println("Child Passenger : " + (i + 1));

            } else if (paxText.contains("Infant")) {

                ElementType(dateOfBirth(i), infDob);
                System.out.println("Infant Passenger : " + (i + 1));
            }
        }
        int assignedToCount = driver.getDriver().findElements(assignedToDropdowns).size();

        for (int i = 0; i < assignedToCount; i++) {

            // open dropdown (re-located each time via SHAFT's click, which scrolls into view
            // and waits for clickability -- a cached WebElement.click() intercepted here since
            // the element isn't scrolled into view first)
            ElementClick(assignedToDropdown(i + 1));

            // select option i+1 (Infant1→Option1)
            driver.element().click(dropdownOptionByIndex(i + 1));
        }

        return this;
    }

    public PaxDetailsPage saveQuote() {
        driver.element().click(saveQuoteBtn);
        return this;
    }

    public PaxDetailsPage payAndBook() {
        driver.element().click(bookBtn);
        driver.element().click(confirmBookBtn);
        return this;
    }

    public PaxDetailsPage clickOnHold() {
        driver.element().click(holdBtn);
        return this;
    }

    public PaxDetailsPage clickNextIfDisplayed() throws InterruptedException {
        List<WebElement> elements = driver.getDriver().findElements(Btn_Next);
        if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
            driver.element().click(Btn_Next);
        }
        Thread.sleep(8000);
        return new PaxDetailsPage(driver);
    }

    private final By anyMealOrBaggageDropdown = By.xpath(
            "//span[@class='p-dropdown-label p-inputtext p-placeholder ng-star-inserted']" +
                    "[normalize-space()='Choose a meal' or normalize-space()='Choose a baggage']");

    public PaxDetailsPage handlePassengerAncillaries(String adults, String children) {
        // ExpandAll is a fragile page-global "4th button[@type='button']" locator with nothing
        // scoping it to an ancillaries section -- it always matches *some* 4th button on the page,
        // just not necessarily the right one, so an emptiness check on it alone doesn't help. When
        // this fare has no meal/baggage ancillaries to expand (as with a plain one-way economy
        // fare), it instead lands on an unrelated button elsewhere on the page (confirmed live: it
        // matched the sidebar's "Top Up Wallet" button and navigated away from the booking flow
        // entirely). Only attempt it when an actual meal/baggage dropdown is present on the page.
        if (!driver.getDriver().findElements(anyMealOrBaggageDropdown).isEmpty()) {
            driver.element().click(ExpandAll);
        }
        int totalPassengers = Integer.parseInt(adults) + Integer.parseInt(children);

        for (int i = 1; i <= totalPassengers; i++) {

            By mealDropdown = By.xpath(
                    "(//span[@class='p-dropdown-label p-inputtext p-placeholder ng-star-inserted'][normalize-space()='Choose a meal'])[" + i + "]");

            By baggageDropdown = By.xpath(
                    "(//span[@class='p-dropdown-label p-inputtext p-placeholder ng-star-inserted'][normalize-space()='Choose a baggage'])[" + i + "]");

            // Meal
            if (!driver.getDriver().findElements(mealDropdown).isEmpty()) {
                driver.element().click(mealDropdown);
                driver.element().click(FirstMeal);
            }

            // Baggage
            if (!driver.getDriver().findElements(baggageDropdown).isEmpty()) {
                driver.element().click(baggageDropdown);
                driver.element().click(FirstBaggage);
            }
        }
        return new PaxDetailsPage(driver);
    }

    private int parseCount(String key) {
        String value = testData.getTestData(key);
        return value == null || value.isBlank() ? 0 : Integer.parseInt(value);
    }

    public PaxDetailsPage SelectTermsAndConditions() {
        driver.element().click(termsSelect);
        return this;
    }

    public PaxDetailsPage AssertThatTicketIsHoldSuccessfully() {
        // Explicit wait for the confirmation page's GDS PNR header -- the live app occasionally
        // takes well over SHAFT's default action timeout to render this (supplier hang / BE
        // deployment quirk), so this polls with a longer, dedicated timeout instead of relying on
        // the default wait baked into driver.element().getText().
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(120))
                .until(ExpectedConditions.presenceOfElementLocated(GDSPNR_Confirmation));
        String s = driver.element().getText(GDSPNR_Confirmation);
        softAssert.assertEquals(s, "GDS PNR Number");
        return this;
    }

    public void assertThatQuoteSaved() {
        driver.verifyThat()
                .element(quoteSavedMsg)
                .isVisible();
    }
}
