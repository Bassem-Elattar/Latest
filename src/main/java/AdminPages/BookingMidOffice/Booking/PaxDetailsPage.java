package AdminPages.BookingMidOffice.Booking;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class PaxDetailsPage {
    public PaxDetailsPage(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    private final By titleDropdown = By.xpath("//label[@for='title_0']/following-sibling::p-dropdown");
    private final By firstName = By.id("firstName_0");
    private final By lastName = By.id("lastName_0");
    private final By dateOfBirth = By.id("dob_0");
    private final By email = By.id("email_0");
    private final By phone = By.id("phone_0");
    private final By documentNumber = By.id("document_0");
    private final By documentExpiry = By.id("docExpiry_0");
    private final By nationalityDropdown = By.xpath("//label[@for='nationality_0']/following::p-dropdown[1]//div[@role='button']");
    private final By saveQuoteBtn = By.xpath("//button[.//span[normalize-space()='Save Quote']]");
    private final By QuoteSavedMsg = By.xpath("//span[normalize-space()='Quote Saved']");
    public PaxDetailsPage SaveQuote(){
        driver.element().click(saveQuoteBtn);
        return new PaxDetailsPage(driver);
    }
    private By dropdownOption(String value) {
        return By.xpath("//li[@aria-label='" + value + "']");
    }
    public void ElementClick(By by){
        driver.element().click(by);
    }
    public void ElementType(By by,String Value){
        driver.element().type(by,Value);
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
    public void AssertThatQuoteSaved(){
        driver.verifyThat()
                .element(QuoteSavedMsg)
                .isVisible();
    }
}
