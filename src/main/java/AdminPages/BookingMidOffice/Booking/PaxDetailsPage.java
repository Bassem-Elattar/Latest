package AdminPages.BookingMidOffice.Booking;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import com.github.javafaker.Faker;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;
import utilities.FakerSingleton;

import java.util.List;

public class PaxDetailsPage {

    private SHAFT.GUI.WebDriver driver;
    private final SHAFT.TestData.JSON testData;
    SoftAssert softAssert = new SoftAssert();

    public PaxDetailsPage(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
        this.testData = new SHAFT.TestData.JSON("searchBookingBrData.json");
    }

    // Dynamic Locators
    private By titleDropdown(int index) {
        return By.xpath("//label[@for='title_" + index + "']/following-sibling::p-dropdown");
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
    private final By termsSelect = By.xpath("(//div[@class='p-checkbox-box'])[3]");
    private final By GDSPNR_Confirmation = By.xpath("//th[text()='GDS PNR Number']");
    private final By brandedFares = By.xpath("//p-carousel");
    private final By Btn_Proceed= By.xpath("(//button[@class='book-btn'])[1]");
    private final By Btn_ExpandAll= By.xpath("(//span[normalize-space()='Expand All'])[1]");
    By Btn_Next = By.xpath("(//button[@class='p-ripple p-element step-btn step-btn--primary p-button p-component'])[1]");
    private final By FirstMeal= By.xpath("(//div[@class='meal-option ng-star-inserted'])[1]");
    private final By FirstBaggage= By.xpath("(//p-dropdownitem[@class='p-element ng-star-inserted'])[1]");
    private final By ExpandAll= By.xpath("(//button[@type='button'])[4]");

    By assignedToDropdowns =
            By.xpath("//p-dropdown[@formcontrolname='assignedTo']");
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

//    public PaxDetailsPage fillOnePassengerDetails(String Title,
//                                                 String FirstName,
//                                                  String LastName,
//                                                  String DOB,
//                                                  String Email,
//                                                  String Phone,
//                                                  String DocumentNumber,
//                                                  String DocumentExpiry,
//                                                  String Nationality)
//    {
//        ElementClick(titleDropdown); ElementClick(dropdownOption(Title));
//        ElementType(firstName, FirstName);
//        ElementType(lastName, LastName);
//        ElementType(dateOfBirth, DOB);
//        ElementType(email, Email);
//        ElementType(phone, Phone);
//        ElementType(documentNumber, DocumentNumber);
//        ElementType(documentExpiry, DocumentExpiry);
//        ElementClick(nationalityDropdown);
//        ElementClick(dropdownOption(Nationality));
//        return new PaxDetailsPage(driver); }
//    public void AssertThatQuoteSaved(){
//        driver.verifyThat() .element(QuoteSavedMsg) .isVisible();
//    }

    public PaxDetailsPage fillOnePassengerDetails(
            String title,
            String dob,
            String chDob,
            String infDob,
            String emailValue,
            String phoneValue,
            String documentExpiryValue,
            String nationality) {

        int total =
                Integer.parseInt(testData.getTestData("NumberOfAdults"))
                        + Integer.parseInt(testData.getTestData("NumberOfChildren"))
                        + Integer.parseInt(testData.getTestData("NumberOfInfants"));
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

           // ElementType(documentNumber(i), documentNumberValue);
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

//            if (i<=total-2)
//            driver.element().click(seeMore(i + 7));
        }
        List<WebElement> assignedToElements =
                driver.getDriver().findElements(assignedToDropdowns);// just for size

        for (int i = 0; i < assignedToElements.size(); i++) {

            // open dropdown
            assignedToElements.get(i).click();

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

    public PaxDetailsPage handlePassengerAncillaries(String adults, String children) {
        driver.element().click(ExpandAll);
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
                // Select meal option
            }

            // Baggage
            if (!driver.getDriver().findElements(baggageDropdown).isEmpty()) {
                driver.element().click(baggageDropdown);
                driver.element().click(FirstBaggage);
                // Select baggage option
            }
        }
        return new PaxDetailsPage(driver);
    }

    public PaxDetailsPage SelectTermsAndConditions() {
        driver.element().click(termsSelect);
        return this;
    }
    public PaxDetailsPage AssertThatTicketIsHoldSuccessfully() {
        String s = driver.element().getText(GDSPNR_Confirmation);
        softAssert.assertEquals(s,"GDS PNR Number");
        return this;
    }

    public void assertThatQuoteSaved() {
        driver.verifyThat()
                .element(quoteSavedMsg)
                .isVisible();
    }
}