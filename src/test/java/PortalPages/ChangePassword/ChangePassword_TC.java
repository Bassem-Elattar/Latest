package PortalPages.ChangePassword;

import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DataUtils;

public class ChangePassword_TC {
    private SHAFT.TestData.JSON testData;
    public SHAFT.GUI.WebDriver driver;
    ChangePassword_Page changePassword;
    Login_Page loginPage;

    @BeforeTest
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
        loginPage = new Login_Page(driver);
        loginPage.PortalLogin();
        testData = new SHAFT.TestData.JSON("PortalChangePassword.json");
        changePassword = new ChangePassword_Page(driver);
        changePassword.NavigateToSettingsPage();
        changePassword.NavigateToChangePasswordPage();
    }


    @Test(priority = 1)
    public void verifyIncorrectCurrentPasswordValidation(){
        changePassword.enterOldPassword(testData.getTestData("passwords.incorrectOldPassword"));
        changePassword.enterNewPassword(testData.getTestData("passwords.newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("passwords.confirmPassword"));
        changePassword.updatePassword();
        String actualResult = changePassword.OldPasswordIsIncorrect();
        Assert.assertEquals(actualResult,testData.getTestData("Toast.incorrectCurrentPasswordToastMessage"));
    }

    @Test(priority = 2)
    public void verifyNewPasswordCannotMatchOldPassword(){
        changePassword.enterOldPassword(testData.getTestData("passwords.oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("passwords.newPasswordMatchesOldPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("passwords.confirmPassword"));
        changePassword.updatePassword();
        String Actual_Result = driver.element().getText(changePassword.newPasswordCannotMatchOldPasswordRule);
        Assert.assertEquals(Actual_Result,testData.getTestData("Rules.newPasswordCannotBeSameAsOldPasswordRule"));
    }

    @Test(priority = 3)
    public void verifyMinimumPasswordLengthValidation(){
        changePassword.enterOldPassword(testData.getTestData("passwords.oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("passwords.passwordLessThanMinLength"));
        changePassword.enterConfirmPassword(testData.getTestData("passwords.passwordLessThanMinLength"));
        changePassword.updatePassword();
        String Actual_Result = driver.element().getText(changePassword.newPasswordMinMaxLengthRule);
        Assert.assertEquals(Actual_Result,testData.getTestData("Rules.passwordMinMaxLengthRule"));
    }

    @Test(priority = 4)
    public void verifyMaximumPasswordLengthValidation(){
        changePassword.enterOldPassword(testData.getTestData("passwords.oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("passwords.passwordGreaterThanMaxLength"));
        changePassword.enterConfirmPassword(testData.getTestData("passwords.passwordGreaterThanMaxLength"));
        changePassword.updatePassword();
        String Actual_Result = changePassword.getNewPasswordMinMaxLengthRule();
        Assert.assertEquals(Actual_Result,testData.getTestData("Rules.passwordMinMaxLengthRule"));
    }

    @Test(priority = 5)
    public void verifyPasswordMustContainUppercase(){
        changePassword.enterOldPassword(testData.getTestData("passwords.oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("passwords.passwordWithoutUppercase"));
        changePassword.enterConfirmPassword(testData.getTestData("passwords.passwordWithoutUppercase"));
        changePassword.updatePassword();
        String Actual_Result = changePassword.getNewPasswordContainUppercaseRule();
        Assert.assertEquals(Actual_Result,testData.getTestData("Rules.passwordMustContainUppercaseRule"));
    }

    @Test(priority = 6)
    public void verifyPasswordMustContainLowercase(){
        changePassword.enterOldPassword(testData.getTestData("passwords.oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("passwords.passwordWithoutLowercase"));
        changePassword.enterConfirmPassword(testData.getTestData("passwords.passwordWithoutLowercase"));
        String Actual_Result = changePassword.getNewPasswordContainLowercaseRule();
        Assert.assertEquals(Actual_Result,testData.getTestData("Rules.passwordMustContainLowercaseRule"));
    }

    @Test(priority = 7)
    public void verifyPasswordMustContainNumber(){
        changePassword.enterOldPassword(testData.getTestData("passwords.oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("passwords.passwordWithoutNumbers"));
        changePassword.enterConfirmPassword(testData.getTestData("passwords.passwordWithoutNumbers"));
        String Actual_Result = changePassword.getNewPasswordContainNumbersRule();
        Assert.assertEquals(Actual_Result,testData.getTestData("Rules.passwordMustContainNumbersRule"));
    }


    @Test(priority = 8)
    public void verifyPasswordMustContainSpecialCharacter(){
        changePassword.enterOldPassword(testData.getTestData("passwords.oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("passwords.passwordWithoutSpecialCharacter"));
        changePassword.enterConfirmPassword(testData.getTestData("passwords.passwordWithoutSpecialCharacter"));
        String Actual_Result = changePassword.getNewPasswordContainSpecialCharacterRule();
        Assert.assertEquals(Actual_Result,testData.getTestData("Rules.passwordMustContainSpecialCharacterRule"));
    }

    @Test(priority = 9)
    public void verifyPasswordCannotContainSpaces(){
        changePassword.enterOldPassword(testData.getTestData("passwords.oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("passwords.passwordContainsSpaces"));
        changePassword.enterConfirmPassword(testData.getTestData("passwords.passwordContainsSpaces"));
        String Actual_Result = changePassword.getNewPasswordNoSpacesRule();
        Assert.assertEquals(Actual_Result,testData.getTestData("Rules.passwordMustNotContainSpacesCharacterRule"));
    }

    @Test(priority = 10)
    public void verifyConfirmPasswordMismatchValidation(){
        changePassword.enterOldPassword(testData.getTestData("passwords.oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("passwords.newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("passwords.mismatchedConfirmPassword"));
        String Actual_Result = changePassword.getNewPasswordMustMatchConfirmPasswordRule();
        Assert.assertEquals(Actual_Result,testData.getTestData("Rules.newPasswordMustMatchConfirmPasswordRule"));
    }

    @Test(priority = 11)
    public void verifyPasswordVisibilityToggle(){
        changePassword.enterOldPassword(testData.getTestData("passwords.oldPassword"));
        Assert.assertEquals(changePassword.getCurrentPasswordInputType(),"password");
        changePassword.clickCurrentPasswordVisibilityToggle();
        Assert.assertEquals(changePassword.getCurrentPasswordInputType(),"text");
        changePassword.clickCurrentPasswordVisibilityToggle();
        Assert.assertEquals(changePassword.getCurrentPasswordInputType(),"password");
    }

    @Test(priority = 12)
    public void verifySuccessfulPasswordChange() {
        changePassword.enterOldPassword(testData.getTestData("passwords.oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("passwords.newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("passwords.confirmPassword"));
        changePassword.updatePassword();
        loginPage.waitUntilLoaded();
        Assert.assertTrue(driver.browser().getCurrentURL().contains("/login"));
        String Actual_Result = driver.element().getText(changePassword.newSuccessfulPasswordToastMessage);
        Assert.assertEquals(Actual_Result,testData.getTestData("Toast.ChangePasswordSuccessfulToastMessage"));
    }
}
