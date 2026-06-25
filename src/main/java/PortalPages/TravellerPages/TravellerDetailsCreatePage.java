package PortalPages.TravellerPages;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class TravellerDetailsCreatePage {
    SHAFT.GUI.WebDriver driver;
    private final By ddl_Title = By.xpath("//input[@id='id-Title']/ancestor::p-dropdown//div[contains(@class,'p-dropdown-trigger')]");
    private final By txt_FirstName = By.id("id-FirstName");
    private final By txt_LastName = By.id("id-LastName");
    private final By txt_DateOfBirth = By.id("id-DateofBirth");
    private final By txt_Email = By.id("id-Email");
    private final By ddl_Nationality = By.xpath("//input[@id='id-Nationality']/ancestor::p-dropdown//div[contains(@class,'p-dropdown-trigger')]");
    private final By ddl_Gender = By.xpath("//input[@id='id-Gender']/ancestor::p-dropdown//div[contains(@class,'p-dropdown-trigger')]");
    private final By txt_MobileNumber = By.id("Mobile Number");
    private final By txt_Address = By.xpath("//textarea[@placeholder='Address']");
    private final By btn_Save = By.xpath("//button[@type='submit']//span[normalize-space()='Save']");
    private final By chatBotMinimizeIcon = By.id("zsiq_minimize");
    private final By toastMessage = By.xpath("//div[@role='alert']");
    private final By txt_PassportNumber = By.id("id-PassportNumber");
    private final By txt_PassportExpiryDate = By.id("id-passport-expiry-date-ExpiryDate");
    private final By ddl_PassportCountryOfIssue = By.xpath("//input[@id='id-passport-issued-country-CountryofIssue']/ancestor::div[contains(@class,'p-dropdown')]//div[@role='button']");
    private final By txt_IqamaNumber = By.id("id-IqamaNumber");
    private final By txt_IqamaExpiryDate = By.id("id-iqama-expiry-date-ExpiryDate");
    private final By ddl_IqamaCountryOfIssue = By.xpath("//input[@id='id-iqama-issued-country-CountryofIssue']/ancestor::div[contains(@class,'p-dropdown')]//div[@role='button']");
    private final By txt_NationalIDNumber = By.id("id-NationalIDNumber");
    private final By txt_NationalIDExpiryDate = By.id("id-national-id-expiry-date-ExpiryDate");
    private final By ddl_NationalIDCountryOfIssue = By.xpath("//input[@id='id-national-id-issued-country-CountryofIssue']/ancestor::div[contains(@class,'p-dropdown')]//div[@role='button']");
    private final By firstNameRequiredMessage = By.xpath("//input[@id='id-FirstName']/ancestor::ndc-fg-input//span[contains(@class,'fg-error')]");
    public void MinimizeChatBot() {
        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();

        js.executeScript(
                "document.querySelector('.zsiq_floatmain').style.display='none';"
        );
    }
    public TravellerDetailsCreatePage(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    public void ElementClick(By by){

        driver.element().click(by);
    }
    public void ElementType(By by,String Value){
        driver.element().type(by,Value);
    }
    public void selectTitle(String Title){
        ElementClick(ddl_Title);
        ElementClick(By.xpath("//li[@role='option']//span[normalize-space()='" + Title + "']"));
    }
    public void selectNationality(String nationality) {
        ElementClick(ddl_Nationality);
        ElementClick(By.xpath("//li[@role='option']//span[normalize-space()='" + nationality + "']"));
    }
    public void selectGender(String Gender){
        ElementClick(ddl_Gender);
        ElementClick(By.xpath("//li[@role='option']//span[normalize-space()='" + Gender + "']"));
    }
    public void SelectPassportCountryIssue(String CountryIssue){
        driver.element().scrollToElement(ddl_PassportCountryOfIssue);
        ElementClick(ddl_PassportCountryOfIssue);
        ElementClick(By.xpath("//li[@role='option']//span[normalize-space()='" + CountryIssue + "']"));
    }
    public void SelectIqamaCountryIssue(String CountryIssue){
        driver.element().scrollToElement(ddl_IqamaCountryOfIssue);
        ElementClick(ddl_IqamaCountryOfIssue);
        ElementClick(By.xpath("//li[@role='option']//span[normalize-space()='" + CountryIssue + "']"));
    }
    public void SelectNationalIDCountryIssue(String CountryIssue){
        driver.element().scrollToElement(ddl_NationalIDCountryOfIssue);
        ElementClick(ddl_NationalIDCountryOfIssue);
        ElementClick(By.xpath("//li[@role='option']//span[normalize-space()='" + CountryIssue + "']"));
    }
    public TravellerDetailsCreatePage AddTravellerWithMandatoryFields(String Title,
                                                                      String fName,
                                                                      String lName,
                                                                      String DOB,
                                                                      String Email,
                                                                      String Nationality,
                                                                      String Gender,
                                                                      String MobileNumber,
                                                                      String Address){
        MinimizeChatBot();
        selectTitle(Title);
        ElementType(txt_FirstName,fName);
        ElementType(txt_LastName,lName);
        ElementType(txt_DateOfBirth,DOB);
        ElementType(txt_Email,Email);
        selectNationality(Nationality);
        selectGender(Gender);
        ElementType(txt_MobileNumber,MobileNumber);
        ElementType(txt_Address,Address);
        //Scroll to the bottom of the screen
        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        MinimizeChatBot();
        ElementClick(btn_Save);
        return new TravellerDetailsCreatePage(driver);
    }
    public TravellerDetailsCreatePage AddTravellerWithMandatoryAndOptionalFields(String Title,
                                                                                 String fName,
                                                                                 String lName,
                                                                                 String DOB,
                                                                                 String Email,
                                                                                 String Nationality,
                                                                                 String Gender,
                                                                                 String MobileNumber,
                                                                                 String Address,
                                                                                 String PassportNumber,
                                                                                 String PassportExpiryDate,
                                                                                 String PassportCountryOfIssue,
                                                                                 String IqamaNumber,
                                                                                 String IqamaExpiryDate,
                                                                                 String IqamaCountryOfIssue,
                                                                                 String NationalIDNumber,
                                                                                 String NationalIDExpiryDate,
                                                                                 String NationalIDCountryOfIssue)
    {
        MinimizeChatBot();
        selectTitle(Title);
        ElementType(txt_FirstName,fName);
        ElementType(txt_LastName,lName);
        ElementType(txt_DateOfBirth,DOB);
        ElementType(txt_Email,Email);
        selectNationality(Nationality);
        selectGender(Gender);
        ElementType(txt_MobileNumber,MobileNumber);
        ElementType(txt_Address,Address);

        ElementType(txt_PassportNumber,PassportNumber);
        ElementType(txt_PassportExpiryDate,PassportExpiryDate);
        SelectPassportCountryIssue(PassportCountryOfIssue);
        ElementType(txt_IqamaNumber,IqamaNumber);
        ElementType(txt_IqamaExpiryDate,IqamaExpiryDate);
        SelectIqamaCountryIssue(IqamaCountryOfIssue);
        ElementType(txt_NationalIDNumber,NationalIDNumber);
        ElementType(txt_NationalIDExpiryDate,NationalIDExpiryDate);
        SelectNationalIDCountryIssue(NationalIDCountryOfIssue);
        //Scroll to the bottom of the screen
        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        MinimizeChatBot();
        ElementClick(btn_Save);
        return new TravellerDetailsCreatePage(driver);
    }
    public TravellerDetailsCreatePage EditTravellerDetails(String ClientID)
    {
        MinimizeChatBot();
        By EditIcon = By.xpath(
                "//tbody//tr[td[contains(normalize-space(),'" + ClientID + "')]]//*[@ptooltip='Edit']"
        );
        driver.element().waitToBeReady(EditIcon);
        ElementClick(EditIcon);
        //Scroll to the bottom of the screen
        JavascriptExecutor js = (JavascriptExecutor) driver.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        MinimizeChatBot();
        ElementClick(btn_Save);
        return new TravellerDetailsCreatePage(driver);
    }
    public void AssertThatToastMessageDisplayed(String expectedText)
    {
        driver.assertThat()
                .element(toastMessage)
                .text()
                .contains(expectedText)
                .perform();
    }
    public boolean IsFirstNameRequiredMessageDisplayed() {

        driver.element().waitToBeReady(firstNameRequiredMessage);

        String actualMessage = driver.element()
                .getText(firstNameRequiredMessage)
                .trim();

        return actualMessage.equalsIgnoreCase("Required");
    }
}