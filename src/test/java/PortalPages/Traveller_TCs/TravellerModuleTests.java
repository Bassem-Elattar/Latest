package PortalPages.Traveller_TCs;

import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import PortalPages.SideMenu;
import PortalPages.TravellerPages.TravellerDetailsCreatePage;
import PortalPages.TravellerPages.TravellerDetailsSearchPage;
import com.github.javafaker.Faker;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.DataUtils;
import java.lang.reflect.Method;

public class TravellerModuleTests {
    public SHAFT.GUI.WebDriver driver;
    SHAFT.TestData.JSON testData;
    Login_Page loginPage;
    private Faker faker;
    String TravellerTitle;
    String TravellerFirstName;
    String TravellerLastName;
    String TravellerDOB;
    String TravellerEmail;
    String TravellerNationality;
    String TravellerGender;
    String TravellerMobileNumber;
    String TravellerAddress;
    String PassportNumber;
    String PassportExpiryDate;
    String PassportCountryOfIssue;
    String IqamaNumber;
    String IqamaExpiryDate;
    String IqamaCountryOfIssue;
    String NationalIDNumber;
    String NationalIDExpiryDate;
    String NationalIDCountryOfIssue;
    String ClientID;
    String Name;
    String Email;
    String PhoneNumber;
    String Country;
    @BeforeClass
    public void DataInitialization(){
        testData = new SHAFT.TestData.JSON("PortalTravellerData.json");
        TravellerTitle = testData.getTestData("Title");
        TravellerFirstName = testData.getTestData("FirstName");
        TravellerLastName = testData.getTestData("LastName");
        TravellerDOB = testData.getTestData("DateOfBirth");
        TravellerEmail = testData.getTestData("Email");
        TravellerNationality = testData.getTestData("Nationality");
        TravellerGender = testData.getTestData("Gender");
        TravellerMobileNumber = testData.getTestData("MobileNumber");
        TravellerAddress = testData.getTestData("Address");
        PassportNumber = testData.getTestData("PassportNumber");;
        PassportExpiryDate = testData.getTestData("PassportExpiryDate");
        PassportCountryOfIssue = testData.getTestData("PassportCountryOfIssue");
        IqamaNumber = testData.getTestData("IqamaNumber");
        IqamaExpiryDate = testData.getTestData("IqamaExpiryDate");
        IqamaCountryOfIssue = testData.getTestData("IqamaCountryOfIssue");
        NationalIDNumber = testData.getTestData("NationalIDNumber");
        NationalIDExpiryDate = testData.getTestData("NationalIDExpiryDate");
        NationalIDCountryOfIssue = testData.getTestData("NationalIDCountryOfIssue");
        ClientID = testData.getTestData("SearchClientID");
        Name = testData.getTestData("SearchName");
        Email = testData.getTestData("SearchEmail");
        PhoneNumber = testData.getTestData("SearchMobileNumber");
        Country = testData.getTestData("SearchCountry");
        faker = new Faker();
    }
    @BeforeMethod
    public void setupBrowse(Method method){
        String testName = method.getName();
        if(testName.equals("VerifyAddingTravellerWithOnlyMandatoryFields")
                ||testName.equals("VerifyAddingTravellerWithMandatoryAndOptionalFields")
                ||testName.equals("VerifyAddingTravellerWithoutSendingAllMandatoryFields"))
        {
            CommonMethod.setupDriver(DataUtils.get("browser"));
            driver = CommonMethod.getDriver();

            driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
            new Login_Page(driver).PortalLogin();

            //Open Add Traveller Page
            new SideMenu(driver).OpenAddTravellerPage();
        }
    }
    @Test(priority = 1)
    public void VerifyAddingTravellerWithOnlyMandatoryFields()
    {
        new TravellerDetailsSearchPage(driver).
                openTravellerDetailsCreationForm();
        new TravellerDetailsCreatePage(driver).AddTravellerWithMandatoryFields(
                TravellerTitle,
                TravellerFirstName,
                TravellerLastName,
                TravellerDOB,
                faker.name().username() + faker.number().numberBetween(1000, 9999) + "@test.com",
                TravellerNationality,
                TravellerGender,
                TravellerMobileNumber,
                TravellerAddress
        ).AssertThatToastMessageDisplayed("Traveller has been added successfully");
    }
    @Test(priority = 2)
    public void VerifyAddingTravellerWithMandatoryAndOptionalFields()
    {
        new TravellerDetailsSearchPage(driver).
                openTravellerDetailsCreationForm();
        new TravellerDetailsCreatePage(driver).AddTravellerWithMandatoryAndOptionalFields(
                TravellerTitle,
                TravellerFirstName,
                TravellerLastName,
                TravellerDOB,
                faker.name().username() + faker.number().numberBetween(1000, 9999) + "@test.com",
                TravellerNationality,
                TravellerGender,
                TravellerMobileNumber,
                TravellerAddress,
                PassportNumber,
                PassportExpiryDate,
                PassportCountryOfIssue,
                IqamaNumber,
                IqamaExpiryDate,
                IqamaCountryOfIssue,
                NationalIDNumber,
                NationalIDExpiryDate,
                NationalIDCountryOfIssue

        ).AssertThatToastMessageDisplayed("Traveller has been added successfully");
    }
    @Test(priority = 3)
    public void VerifyAddingTravellerWithoutSendingAllMandatoryFields()
    {
        new TravellerDetailsSearchPage(driver).
                openTravellerDetailsCreationForm();
        new TravellerDetailsCreatePage(driver).AddTravellerWithMandatoryFields(
                TravellerTitle,
                "",
                TravellerLastName,
                TravellerDOB,
                faker.name().username() + faker.number().numberBetween(1000, 9999) + "@test.com",
                TravellerNationality,
                TravellerGender,
                TravellerMobileNumber,
                TravellerAddress
        ).IsFirstNameRequiredMessageDisplayed();
    }
    @Test(priority = 4)
    public void EditTravellerData() throws InterruptedException {
        //Open Add Traveller Page
        new SideMenu(driver).OpenAddTravellerPage();

        new TravellerDetailsSearchPage(driver).
                SearchByClientID(ClientID);

        new TravellerDetailsCreatePage(driver).
                EditTravellerDetails(ClientID).
                AssertThatToastMessageDisplayed("Traveller has been updated successfully");
        Thread.sleep(5000);
    }
    @Test(priority = 5)
    public void DeactivateTraveller() throws InterruptedException {
        new TravellerDetailsSearchPage(driver).
                SearchByClientID(ClientID).
                DeactivateTraveller(ClientID).
                AssertThatToastMessageDisplayed(Name + " is Deactivated Successfully");
        Thread.sleep(5000);
    }
    @Test(priority = 6)
    public void ActivateTraveller(){
        new TravellerDetailsSearchPage(driver).
                SearchByClientID(ClientID).
                ActivateTraveller(ClientID).
                AssertThatToastMessageDisplayed(Name + " is Activated Successfully");
    }
    @Test(priority = 7)
    public void SearchByClientID()
    {
        new SideMenu(driver).OpenAddTravellerPage();

        boolean result =
                new TravellerDetailsSearchPage(driver).
                        SearchByClientID(ClientID).
                        AssertThatCorrectDataAreDisplayed("Client ID",ClientID);
        Assert.assertTrue(result);
    }
    @Test(priority = 8)
    public void SearchByClientName()
    {
        boolean result =
                new TravellerDetailsSearchPage(driver).
                        SearchByClientName(Name).
                        AssertThatCorrectDataAreDisplayed("Name",Name);
        Assert.assertTrue(result);
    }
    @Test(priority = 9)
    public void SearchByClientEmail()
    {
        boolean result =
                new TravellerDetailsSearchPage(driver).
                        SearchByClientEmail(Email).
                        AssertThatCorrectDataAreDisplayed("Email ID",Email);
        Assert.assertTrue(result);
    }
    @Test(priority = 10)
    public void SearchByClientPhoneNumber()
    {
        boolean result =
                new TravellerDetailsSearchPage(driver).
                        SearchByClientPhoneNumber(PhoneNumber).
                        AssertThatCorrectDataAreDisplayed("Mobile No.",PhoneNumber);
        Assert.assertTrue(result);
    }
    @Test(priority = 11)
    public void SearchByCountry()
    {
        boolean result =
                new TravellerDetailsSearchPage(driver).
                        SearchByClientCoutry(Country).
                        AssertThatCorrectDataAreDisplayed("Nationality",Country);
        Assert.assertTrue(result);
    }
    @Test(priority = 12)
    public void SearchByAllFieldsAtOnce()
    {
        TravellerDetailsSearchPage travellerDetailsSearchPage = new TravellerDetailsSearchPage(driver);
        travellerDetailsSearchPage.SearchByAllFields(ClientID,Name,Email,PhoneNumber,Country);
        boolean result = travellerDetailsSearchPage.AssertThatCorrectDataAreDisplayed("Client ID",ClientID)
                &&travellerDetailsSearchPage.AssertThatCorrectDataAreDisplayed("Name",Name)
                &&travellerDetailsSearchPage.AssertThatCorrectDataAreDisplayed("Email ID",Email)
                &&travellerDetailsSearchPage.AssertThatCorrectDataAreDisplayed("Mobile No.",PhoneNumber)
                &&travellerDetailsSearchPage.AssertThatCorrectDataAreDisplayed("Nationality",Country);
        Assert.assertTrue(result);
    }
    @AfterMethod
    public void tearDown(Method method) {
        String testName = method.getName();
        if (testName.equals("VerifyAddingTravellerWithOnlyMandatoryFields")
                || testName.equals("VerifyAddingTravellerWithMandatoryAndOptionalFields")
                ||testName.equals("SearchByAllFieldsAtOnce")) {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
