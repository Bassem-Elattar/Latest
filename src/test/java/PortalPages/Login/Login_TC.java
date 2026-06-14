package PortalPages.Login;

import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.DataUtils;


public class Login_TC {

    private SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private Login_Page loginPage;

    @BeforeClass
    public void beforeClass() {
        testData = new SHAFT.TestData.JSON("loginData.json");
    }

    @BeforeMethod
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();

        loginPage = new Login_Page(driver);

        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


    @Test(priority = 1)
    public void PortalLogin() {
        loginPage.enterAgencyCode(DataUtils.get("Portal_AgencyCode"))
                .enterEmail(DataUtils.get("Portal_Email"))
                .enterPassword(DataUtils.get("Portal_Password"))
                .clickLoginButton();


        String expectedResult = testData.getTestData("dashboardUrl");
        String actualResult = driver.getDriver().getCurrentUrl();

        Assert.assertEquals(
                actualResult,
                expectedResult,
                "User should be redirected to dashboard after successful login"
        );
    }

    @Test(priority = 2)
    public void invalidAgencyCode() {
        loginPage.enterAgencyCode(testData.getTestData("invalidAgencyCode"))
                .enterEmail(testData.getTestData("validEmail"))
                .enterPassword(testData.getTestData("validPassword"))
                .clickLoginButton();


        String actualResult = loginPage.getTxt_InvalidLoginMessage();

        String expectedResult = testData.getTestData("invalidLoginMessage");

        Assert.assertEquals(
                actualResult,
                expectedResult,
                "Invalid credentials message should be displayed"
        );
    }
    @Test(priority = 3)
    public void invalidEmail() {
        loginPage.enterAgencyCode(testData.getTestData("validAgencyCode"))
                .enterEmail(testData.getTestData("invalidEmail"))
                .enterPassword(testData.getTestData("validPassword"))
                .clickLoginButton();

        String actualResult = loginPage.getTxt_InvalidLoginMessage();
        String expectedResult = testData.getTestData("invalidLoginMessage");

        Assert.assertEquals(
                actualResult,
                expectedResult,
                "Invalid credentials message should be displayed"
        );
    }

    @Test(priority = 4)
    public void invalidPassword() {
        loginPage.enterAgencyCode(testData.getTestData("validAgencyCode"))
                .enterEmail(testData.getTestData("validEmail"))
                .enterPassword(testData.getTestData("invalidPassword"))
                .clickLoginButton();

        String actualResult = loginPage.getTxt_InvalidLoginMessage();
        String expectedResult = testData.getTestData("invalidLoginMessage");

        Assert.assertEquals(
                actualResult,
                expectedResult,
                "Invalid credentials message should be displayed"
        );
    }

    @Test(priority = 5)
    public void invalidAgencyCodeValidationField() {
        loginPage.enterAgencyCode(testData.getTestData("invalidAgencyCodeField"))
                .enterEmail(testData.getTestData("validEmail"))
                .enterPassword(testData.getTestData("validPassword"))
                .clickLoginButton();

        String actualResult = loginPage.getTxt_InvalidAgencyCodeValidationMessage().trim();
        String expectedResult = testData.getTestData("invalidAgencyCodeValidationMessage");

        Assert.assertEquals(
                actualResult,
                expectedResult,
                "Agency code validation message should be displayed"
        );
    }

    @Test(priority = 6)
    public void invalidEmailValidationField() {
        loginPage.enterAgencyCode(testData.getTestData("validAgencyCode"))
                .enterEmail(testData.getTestData("invalidEmailField"))
                .enterPassword(testData.getTestData("validPassword"))
                .clickLoginButton();

        String actualResult = loginPage.getTxt_InvalidEmailValidationMessage().trim();
        String expectedResult = testData.getTestData("invalidEmailValidationMessage");

        Assert.assertEquals(
                actualResult,
                expectedResult,
                "Email validation message should be displayed"
        );
    }

    @Test(priority = 7)
    public void emptyLogin() {
        loginPage.clickLoginButton();

        SoftAssert softAssert = new SoftAssert();

        String expectedResult = testData.getTestData("requiredMessage");

        String actualAgencyCodeResult = loginPage.getTxt_AgencyCodeRequiredMessage().trim();
        String actualEmailResult = loginPage.getTxt_EmailRequiredMessage().trim();
        String actualPasswordResult = loginPage.getTxt_PasswordRequiredMessage().trim();

        softAssert.assertEquals(
                actualAgencyCodeResult,
                expectedResult,
                "Agency code required message should be displayed"
        );

        softAssert.assertEquals(
                actualEmailResult,
                expectedResult,
                "Email required message should be displayed"
        );

        softAssert.assertEquals(
                actualPasswordResult,
                expectedResult,
                "Password required message should be displayed"
        );

        softAssert.assertAll();
    }





}
