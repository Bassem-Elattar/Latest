package AdminPages.Profile.ChangePassword;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;


public class ChangePassword_TC extends TestBase_TC {
    private LogIn_Page logIn;
    ChangePassword_Page changePassword;
    SHAFT.TestData.JSON testData;

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        testData = new SHAFT.TestData.JSON("ChangePassword.json");
    }

    @Test(priority = 1)
    public void misMatchChangePassword2() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword(testData.getTestData("ValidData.OldPassword"));
        changePassword.SetNewPassword(testData.getTestData("ValidData.ValidNewPassword"));
        changePassword.SetConfirmPassword(testData.getTestData("ValidData.ValidConfirmationPassword"));
        changePassword.ClickConfirm();
        String ActualResult = driver.element().getText(changePassword.ValidMisMatchField);
        Assert.assertEquals(ActualResult,testData.getTestData("ValidData.MisMatchError"));
    }

    @Test(priority = 2)
    public void inValidChangePasswordWithEmptyFields() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword(testData.getTestData("ValidData.OldPassword"));
        changePassword.SetNewPassword("");
        changePassword.SetConfirmPassword("");
        changePassword.ClickConfirm();
        String ActualResult = driver.element().getText(changePassword.ValidEmptyField);
        Assert.assertEquals(ActualResult,testData.getTestData("ValidData.RequiredError"));
    }

    @Test(priority = 3)
    public void minLengthMatchChangePassword2() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword(testData.getTestData("ValidData.OldPassword"));
        changePassword.SetNewPassword(testData.getTestData("InvalidData.InvalidNewPassword"));
        changePassword.SetConfirmPassword(testData.getTestData("InvalidData.InvalidConfirmationPassword"));
        changePassword.ClickConfirm();
        String ActualResult = driver.element().getText(changePassword.ValidMinField);
        Assert.assertEquals(ActualResult,testData.getTestData("InvalidData.LengthError"));
    }

    @Test(priority = 4)
    public void maxLengthMatchChangePassword2() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword(testData.getTestData("ValidData.OldPassword"));
        changePassword.SetNewPassword(testData.getTestData("InvalidData.InvalidNewPassword1"));
        changePassword.SetConfirmPassword(testData.getTestData("InvalidData.InvalidConfirmationPassword1"));
        changePassword.ClickConfirm();
        String ActualResult = driver.element().getText(changePassword.ValidMaxField);
        Assert.assertEquals(ActualResult,testData.getTestData("InvalidData.LengthError"));
    }

    @Test(priority = 6)
    public void iNValidOldChangePassword() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword(testData.getTestData("InvalidData.InvalidOldPassword"));
        changePassword.SetNewPassword(testData.getTestData("ValidData.ValidNewPassword"));
        changePassword.SetConfirmPassword(testData.getTestData("ValidData.ValidNewPassword"));
        changePassword.ClickConfirm();
        String Actual = driver.element().getText(changePassword.InValidOldPassWord);
        String Expected = testData.getTestData("InvalidData.OldPasswordError");
         Assert.assertEquals(Actual,Expected,"this not correct");
    }

    @Test(priority = 7)
    public void ValidChangePassword() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword(testData.getTestData("ValidData.OldPassword"));
        changePassword.SetNewPassword(testData.getTestData("ValidData.OldPassword"));
        changePassword.SetConfirmPassword(testData.getTestData("ValidData.OldPassword"));
        changePassword.ClickConfirm();
        String Actual = driver.element().getText(changePassword.ValidationInOldPass);
        String Expected = testData.getTestData("InvalidData.SameNewOldPassword");
        Assert.assertEquals(Actual,Expected,"Some Thing Error");
    }

    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}

