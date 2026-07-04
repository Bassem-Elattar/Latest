package PortalPages.ChangePassword;

import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import PortalPages.Reports.Booking.Sales.SalesReport;
import com.shaft.driver.SHAFT;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DataUtils;

public class ChangePassword_TC {
    private SHAFT.TestData.JSON testData;
    public SHAFT.GUI.WebDriver driver;
    ChangePassword_Page changePassword;

    @BeforeTest
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
        new Login_Page(driver).PortalLogin();
        testData = new SHAFT.TestData.JSON("PortalChangePassword.json");
        changePassword = new ChangePassword_Page(driver);
        changePassword.NavigateToSettingsPage();
        changePassword.NavigateToChangePasswordPage();
    }



    @Test
    public void verifyIncorrectCurrentPasswordValidation(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }

    @Test
    public void verifyNewPasswordCannotMatchOldPassword(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }

    @Test
    public void verifyMinimumPasswordLengthValidation(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }

    @Test
    public void verifyMaximumPasswordLengthValidation(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }

    @Test
    public void verifyUppercasePasswordRequirement(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }

    @Test
    public void verifyLowercasePasswordRequirement(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }

    @Test
    public void verifyNumberPasswordRequirement(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }


    @Test
    public void verifySpecialCharacterPasswordRequirement(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }

    @Test
    public void verifyPasswordCannotContainSpaces(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }

    @Test
    public void verifyConfirmPasswordMismatchValidation(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }

    @Test
    public void verifyPasswordVisibilityToggle(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }

    @Test
    public void verifySuccessfulPasswordChange(){
        changePassword.enterOldPassword(testData.getTestData("oldPassword"));
        changePassword.enterNewPassword(testData.getTestData("newPassword"));
        changePassword.enterConfirmPassword(testData.getTestData("confirmPassword"));
        changePassword.updatePassword();
    }
}
