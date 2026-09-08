package PortalPages.Login;

import AdminPages.Login.OTPCodeVerification_Page;
import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.DataUtils;
import utilities.GmailReaderUtil;

    /*
                                      Steps to get the App Password:

    1- click on gmail account profile image
    2- click on manage your Google account
    3- enable 2FA in gmail account
    4- go to security and sign-in tab
    5- search for app-password
    6- add app name and click on create
    7- copy the generated password (16 letter)
    8- add the Gmail username and generated app-password in MailCredentials.json
    */

public class MailOTP_TC {

    private SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private Login_Page loginPage;
    OTPVerification Otp;

    @BeforeClass
    public void beforeClass() {
        testData = new SHAFT.TestData.JSON("MailCredentials.json");
    }

    @BeforeMethod
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();

        loginPage = new Login_Page(driver);

        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
        new Login_Page(driver).PortalLogin();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void OTPVerificationInValidCode() throws InterruptedException {
        Otp = new OTPVerification(driver);
        Otp.OTPRequest();
        String otp = OTPVerification.get4DigitRandom(); // random 4 numbers
        Otp.OTPSubmit(otp);
        String ActualResult = Otp.InvalidCode();
        String ExpectedResult = "Invalid";
        Assert.assertTrue(ActualResult.contains(ExpectedResult), "the error message appears successfully");
    }

    @Test
    public void ThreeTimesTrialsError() throws InterruptedException {
        Otp = new OTPVerification(driver);
        Otp.OTPRequest();
        String otp = OTPVerification.get4DigitRandom(); // random 4 numbers
        for (int i=0 ; i<2 ; i++) {
            Otp.OTPSubmit(otp);
        }
        String ActualResult = Otp.ExceededAttempt();
        String ExpectedResult = "Your account has been locked";
        Assert.assertTrue(ActualResult.contains(ExpectedResult), "the error message appears successfully");
    }

    @Test
    public void OTPVerificationValidCode() throws InterruptedException {
        Otp = new OTPVerification(driver);
        Otp.OTPRequest();
        String otp = GmailReaderUtil.getLatestOtp(testData.getTestData("gmail.Username"), testData.getTestData("gmail.Password"));
        Otp.OTPSubmit(otp);
        Thread.sleep(3000);
        String ActualResult = driver.getDriver().getCurrentUrl();
        String ExpectedResult = "dashboard";
        Assert.assertTrue(ActualResult.contains(ExpectedResult), "the user is logged in successfully");
    }

    @Test
    public void ResendOTP() throws InterruptedException {
        Otp = new OTPVerification(driver);
        Otp.OTPRequest();
        String old_otp = GmailReaderUtil.getLatestOtp(testData.getTestData("gmail.Username"), testData.getTestData("gmail.Password"));
        System.out.println("old_otp = " + old_otp);
        Otp.OTPResend();
        Otp.OTPSubmit(old_otp);
        String ActualResult1 = Otp.InvalidCode();
        String ExpectedResult2 = "Invalid";
//        Assert.assertTrue(ActualResult1.contains(ExpectedResult2), "the error message appears successfully");
        String new_otp = GmailReaderUtil.getLatestOtp(testData.getTestData("gmail.Username"), testData.getTestData("gmail.Password"));
        System.out.println("new_otp = " + new_otp);
        Assert.assertNotEquals(old_otp, new_otp, "The new code generated successfully");
        Otp.OTPSubmit(new_otp);
        Thread.sleep(3000);
        String ActualResult = driver.getDriver().getCurrentUrl();
        String ExpectedResult = "dashboard";
        Assert.assertTrue(ActualResult.contains(ExpectedResult), "the user is logged in successfully");
    }

    @Test
    public void ResendOTPError() throws InterruptedException {
        Otp = new OTPVerification(driver);
        Otp.SecondTimeResend();
        driver.browser().navigateBack();
        Otp.SecondTimeResend();
        String ActualResult = Otp.InvalidCode();
        String ExpectedResult = "We can't send the OTP";
        Assert.assertTrue(ActualResult.contains(ExpectedResult), "the error message appears successfully");
    }
}
