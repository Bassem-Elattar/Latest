package AdminPages.BookingMidOffice.ManualBookingInvoice;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class PaxDetailsPage {
    public PaxDetailsPage(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    SHAFT.GUI.WebDriver driver;
    private final By titleDropdown = By.xpath("//label[@for='title_0']/following-sibling::p-dropdown");
    private final By firstName = By.id("firstName_0");
    private final By lastName = By.id("lastName_0");
    private final By dateOfBirth = By.id("dob_0");
    private final By email = By.id("email_0");
    private final By phone = By.id("phone_0");
    private final By documentNumber = By.id("document_0");
    private final By documentExpiry = By.id("docExpiry_0");
    private final By nationalityDropdown = By.xpath("//label[@for='nationality_0']/following::p-dropdown[1]//div[@role='button']");
    private final By ChildTraveller = By.xpath("(//span[contains(text(),'Child')])[1]");
    private final By titleDropdown1 = By.xpath("//label[@for='title_1']/following-sibling::p-dropdown");
    private final By firstName1 = By.id("firstName_1");
    private final By lastName1 = By.id("lastName_1");
    private final By dateOfBirth1 = By.id("dob_1");
    private final By email1 = By.id("email_1");
    private final By phone1 = By.id("phone_1");
    private final By documentNumber1 = By.id("document_1");
    private final By documentExpiry1 = By.id("docExpiry_1");
    private final By nationalityDropdown1 = By.xpath("//label[@for='nationality_1']/following::p-dropdown[1]//div[@role='button']");
    private final By INFTraveller = By.xpath("(//span[contains(text(),'Infant')])[1]");
    private final By titleDropdown2 = By.xpath("//label[@for='title_2']/following-sibling::p-dropdown");
    private final By firstName2 = By.id("firstName_2");
    private final By lastName2 = By.id("lastName_2");
    private final By dateOfBirth2 = By.id("dob_2");
    private final By email2 = By.id("email_2");
    private final By phone2 = By.id("phone_2");
    private final By documentNumber2 = By.id("document_2");
    private final By documentExpiry2 = By.id("docExpiry_2");
    private final By nationalityDropdown2 = By.xpath("//label[@for='nationality_2']/following::p-dropdown[1]//div[@role='button']");
    //  private final By saveQuoteBtn = By.xpath("//button[.//span[normalize-space()='Save Quote']]");
    //  private final By saveQuoteBtn = By.xpath("//button[.//span[normalize-space()='Save Quote']]");
    //  private final By QuoteSavedMsg = By.xpath("//span[normalize-space()='Quote Saved']");

//    public PaxDetailsPage SaveQuote() {
//        driver.element().click(saveQuoteBtn);
//        return new PaxDetailsPage(driver);
//    }

    private By dropdownOption(String value) {
        return By.xpath("//li[@aria-label='" + value + "']");
    }

    public void ElementClick(By by) {
        driver.element().click(by);
    }

    public void ElementType(By by, String Value) {
        driver.element().type(by, Value);
    }

    public PaxDetailsPage fillOnePassengerDetails(String Title,
                                                  String FirstName,
                                                  String LastName,
                                                  String DOB,
                                                  String Email,
                                                  String Phone,
                                                  String DocumentNumber,
                                                  String DocumentExpiry,
                                                  String Nationality) {
        ElementClick(titleDropdown);
        ElementClick(dropdownOption(Title));

        ElementType(firstName, FirstName);
        ElementType(lastName, LastName);

        ElementType(dateOfBirth, DOB);

        ElementType(email, Email);
        ElementType(phone, Phone);

        ElementType(documentNumber, DocumentNumber);

        ElementType(documentExpiry, DocumentExpiry);

        ElementClick(nationalityDropdown);
        ElementClick(dropdownOption(Nationality));
        return new PaxDetailsPage(driver);
    }

    public PaxDetailsPage selectCHDTraveller() {
        driver.element().click(ChildTraveller);
        return this;
    }

    public PaxDetailsPage fillCHDPassengerDetails1(String Title1,
                                                   String FirstName1,
                                                   String LastName1,
                                                   String DOB1,
                                                   String Email1,
                                                   String Phone1,
                                                   String DocumentNumber1,
                                                   String DocumentExpiry1,
                                                   String Nationality1) {
        ElementClick(titleDropdown1);
        ElementClick(dropdownOption(Title1));

        ElementType(firstName1, FirstName1);
        ElementType(lastName1, LastName1);

        ElementType(dateOfBirth1, DOB1);

        ElementType(email1, Email1);
        ElementType(phone1, Phone1);

        ElementType(documentNumber1, DocumentNumber1);

        ElementType(documentExpiry1, DocumentExpiry1);

        ElementClick(nationalityDropdown1);
        ElementClick(dropdownOption(Nationality1));
        return new PaxDetailsPage(driver);
    }
    public PaxDetailsPage selectINFTraveller() {
        driver.element().click(INFTraveller);
        return this;
    }
    public PaxDetailsPage fillINFPassengerDetails1(String Title2,
                                                   String FirstName2,
                                                   String LastName2,
                                                   String DOB2,
                                                   String Email2,
                                                   String Phone2,
                                                   String DocumentNumber2,
                                                   String DocumentExpiry2,
                                                   String Nationality2) {
        ElementClick(titleDropdown2);
        ElementClick(dropdownOption(Title2));

        ElementType(firstName2, FirstName2);
        ElementType(lastName2, LastName2);

        ElementType(dateOfBirth2, DOB2);

        ElementType(email2, Email2);
        ElementType(phone2, Phone2);

        ElementType(documentNumber2, DocumentNumber2);

        ElementType(documentExpiry2, DocumentExpiry2);

        ElementClick(nationalityDropdown2);
        ElementClick(dropdownOption(Nationality2));
        return new PaxDetailsPage(driver);
    }
}

