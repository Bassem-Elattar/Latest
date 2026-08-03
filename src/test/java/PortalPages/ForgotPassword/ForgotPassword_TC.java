package PortalPages.ForgotPassword;

import Drive_Factory.CommonMethod;
import com.github.javafaker.Faker;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DBHelper;
import utilities.DataUtils;


public class ForgotPassword_TC {
    private SHAFT.TestData.JSON testData;
    public SHAFT.GUI.WebDriver driver;


    @BeforeMethod
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();

        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
    }
    @Test(priority = 1)
    public void VerifyForgotPasswordFunctionality() throws InterruptedException {
        testData = new SHAFT.TestData.JSON("ForgotPassword.json");
        Faker faker = new Faker();
        String newPassword = faker.internet().password(8, 12, true, true, true);
        ForgotPassword pass = new ForgotPassword(driver);

        pass.clickForgotPassword()
                .enterAgencyCode(testData.getTestData("agencyCode"))
                .enterEmail(testData.getTestData("email"))
                .clickConfirm()
                .assertSuccessMessageText();

        String resetLink = DBHelper.getForgotPasswordLink(
                testData.getTestData("agencyCode"));
        System.out.println(resetLink);
        driver.browser().navigateToURL(resetLink);

        pass.enterNewPassword(newPassword)
                .enterConfirmNewPassword(newPassword)
                .clickSubmit()
                .clickOk()
                .enterAgentCode(testData.getTestData("agencyCode"))
                .enterMail(testData.getTestData("email"))
                .enterPassword(newPassword)
                .clickLogin()
                .CheckAgentCode();
        Thread.sleep(10000);
        pass.clickSignOut();


    }
    @Test(priority = 2)
    public void VerifyForgotPasswordwithInvalidData() throws InterruptedException {
        testData = new SHAFT.TestData.JSON("ForgotPassword.json");
        Faker faker = new Faker();
        String newPassword = faker.internet().password(8, 12, false, true, true);
        ForgotPassword pass = new ForgotPassword(driver);

        pass.clickForgotPassword()
                .enterAgencyCode(testData.getTestData("agencyCode"))
                .enterEmail(testData.getTestData("email"))
                .clickConfirm()
                .assertSuccessMessageText();

        String resetLink = DBHelper.getForgotPasswordLink(
                testData.getTestData("agencyCode"));
        System.out.println(resetLink);
        driver.browser().navigateToURL(resetLink);

        pass.enterNewPassword(newPassword)
                .assertUppercaseValidation();
        Thread.sleep(10000);
    }
    @AfterMethod
    public void tearDown() {
        CommonMethod.quitDriver();
    }




}
