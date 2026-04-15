package AdminPages.Profile.ChangePassword;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
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

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();

    }

    @Test(priority = 1)
    public void misMatchChangePassword2() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword("qqE6)Cxp6>B8");
        changePassword.SetNewPassword("Abcd@1234");
        changePassword.SetConfirmPassword("Abcd@12345");
        changePassword.ClickConfirm();
        String ActualResult=driver.element().getText(changePassword.ValidMisMatchField);
        Assert.assertEquals(ActualResult,"New password and confirm password should be same.");

    }
    @Test(priority = 2)
    public void inValidChangePasswordWithEmptyFields() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword("qqE6)Cxp6>B8\n");
        changePassword.SetNewPassword("");
        changePassword.SetConfirmPassword("");
        changePassword.ClickConfirm();
        String ActualResult=driver.element().getText(changePassword.ValidEmptyField);
        Assert.assertEquals(ActualResult,"Required");
       // Thread.sleep(3000);
    }
    @Test(priority = 3)
    public void minLengthMatchChangePassword2() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword("qqE6)Cxp6>B8\n");
        changePassword.SetNewPassword("qqE6)\n");
        changePassword.SetConfirmPassword("qqE6)\n");
        changePassword.ClickConfirm();
        String ActualResult=driver.element().getText(changePassword.ValidMinField);
        Assert.assertEquals(ActualResult,"Password length should be 8 to 64 characters.");
        //Thread.sleep(3000);
    }
    @Test(priority = 4)
    public void maxLengthMatchChangePassword2() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword("qqE6)Cxp6>B8");
        changePassword.SetNewPassword("Abcd@1234asdasdasdasdasdasdasdasdasdasdasdasdasddaasddfaasddfasdd");
        changePassword.SetConfirmPassword("Abcd@1234asdasdasdasdasdasdasdasdasdasdasdasdasddaasddfaasddfasdd");
        changePassword.ClickConfirm();
        String ActualResult=driver.element().getText(changePassword.ValidMaxField);
        Assert.assertEquals(ActualResult,"Password length should be 8 to 64 characters.");
       // Thread.sleep(3000);
    }
//    @Test(priority = 5)
//    public void maxLengthAndMisMatchChangePassword2() throws InterruptedException {
//        changePassword= new ChangePassword_Page(driver);
//        changePassword.ClickDropdwnSign();
//        changePassword.ChangePasswordButton();
//        changePassword.SelectOldPassword("qqE6)Cxp6>B8\n");
//        changePassword.SetNewPassword("Abcd@1234asdasdasdasdasdasdasdasdasdasdasdasdasddaasddfaasddfasddb");
//        changePassword.SetConfirmPassword("Abcd@1234asdasdasdasdasdasdasdasdasdasdasdasdasddaasddfaasddfasdd");
//        changePassword.ClickConfirm();
//        String ActualResult1=driver.element().getText(changePassword.ValidMaxField);
//        Assert.assertEquals(ActualResult1,"Password length should be 8 to 64 characters.");
//        String ActualResult2=driver.element().getText(changePassword.ValidMisMatchField);
//        Assert.assertEquals(ActualResult2,"New password and confirm password should be same.");
//    }

    @Test(priority = 6)
    public void iNValidOldChangePassword() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword("qqE6)Cxp6>B");
        changePassword.SetNewPassword("Abcd@12345");
        changePassword.SetConfirmPassword("Abcd@12345");
        changePassword.ClickConfirm();
        String Actual=driver.element().getText(changePassword.InValidOldPassWord);
        String Expected="Password change was not successful: Old password is wrong, Please enter correct password";
         Assert.assertEquals(Actual,Expected,"this not correct");
        //Thread.sleep(3000);
    }

    @Test(priority = 7)
    public void ValidChangePassword() throws InterruptedException {
        changePassword= new ChangePassword_Page(driver);
        changePassword.ClickDropdwnSign();
        changePassword.ChangePasswordButton();
        changePassword.SelectOldPassword("qqE6)Cxp6B8");
        changePassword.SetNewPassword("qqE6)Cxp6B8");
        changePassword.SetConfirmPassword("qqE6)Cxp6B8");
        changePassword.ClickConfirm();
        String Actual=driver.element().getText(changePassword.ValidationInOldPass);
        String Expected="New password cannot be same as old Password.";
        Assert.assertEquals(Actual,Expected,"Some Thing Error");
        Thread.sleep(1000);
    }


    @AfterMethod
    public void navigateBackToURL() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }



}

