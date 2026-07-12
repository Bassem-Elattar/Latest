package PortalPages.ChangePassword;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class ChangePassword_Page {
    public ChangePassword_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;


    By Btn_Settings = By.xpath("//button[@class='p-element icon-button icon-button--settings ng-star-inserted']");
    By Txt_yourOldPassword = By.xpath("//p-password[@formcontrolname='oldPassword']//input");
    By Txt_yourNewPassword = By.xpath("//p-password[@formcontrolname='newPassword']//input");
    By Txt_enterConfirmPassword = By.xpath("//p-password[@formcontrolname='confirmPassword']//input");
    By Btn_updatePassword = By.xpath("//button[@class='p-element save-main-btn p-button p-component']");
    By Btn_NavigateToChangePasswordPage = By.xpath("//a[contains(@href, 'change-password')]");
    By newSuccessfulPasswordToastMessage = By.xpath("//div[@aria-label='Password changed successfully']");
    By oldPasswordIsIncorrectToastMessage = By.xpath("//div[@aria-label='Old password is wrong, Please enter correct password']");
    By newPasswordCannotMatchOldPasswordRule  = By.xpath("//span[contains(.,'New password cannot be same as old Password')]");
    By newPasswordMinMaxLengthRule  = By.xpath("//span[contains(.,'Password length should be 8 to 64 characters.')]");
    By newPasswordUppercaseRule = By.xpath("//span[contains(.,'Must contain at least 1 uppercase letter!')]");
    By newPasswordLowercaseRule = By.xpath("//span[contains(.,'Must contain at least 1 lowercase letter!')]");
    By newPasswordNumbersRule = By.xpath("//span[contains(.,'Must contain at least 1 number!')]");
    By newPasswordSpecialCharacterRule = By.xpath("//span[contains(.,'Must contain at least one special character: @ # $ % ^ & * ( ) _ !')]");
    By newPasswordNoSpacesRule = By.xpath("//span[contains(.,'Password can include letters, numbers, and ! @ # $ % ^ & * ( ) _, but no spaces.')]");
    By newPasswordMustMatchConfirmPasswordRule = By.xpath("//span[contains(.,'New password and confirm password should be same.')]");
    By visibilityToggleIcon = By.xpath("//p-password[@formcontrolname='oldPassword']//i");

    public void NavigateToSettingsPage(){
        driver.element().click(Btn_Settings);
    }

    public void NavigateToChangePasswordPage(){
        driver.element().click(Btn_NavigateToChangePasswordPage);
    }

    public void enterOldPassword (String oldPassword){
        driver.element().type(Txt_yourOldPassword,oldPassword);
    }

    public void enterNewPassword (String newPassword){
        driver.element().type(Txt_yourNewPassword,newPassword);
    }

    public void enterConfirmPassword (String newPassword){
        driver.element().type(Txt_enterConfirmPassword,newPassword);
    }

    public void updatePassword(){
        driver.element().click(Btn_updatePassword);
    }

    public String OldPasswordIsIncorrect(){
       return driver.element().getText(oldPasswordIsIncorrectToastMessage);
    }

    public String getNewPasswordMinMaxLengthRule(){
    return driver.element().getText(newPasswordMinMaxLengthRule);
    }

    public String getNewPasswordContainUppercaseRule(){
        return driver.element().getText(newPasswordUppercaseRule);
    }

    public String getNewPasswordContainLowercaseRule(){
        return driver.element().getText(newPasswordLowercaseRule);
    }

    public String getNewPasswordContainNumbersRule(){
        return driver.element().getText(newPasswordNumbersRule);
    }

    public String getNewPasswordContainSpecialCharacterRule(){
        return driver.element().getText(newPasswordSpecialCharacterRule);
    }

    public String getNewPasswordNoSpacesRule(){
        return driver.element().getText(newPasswordNoSpacesRule);
    }

    public String getNewPasswordMustMatchConfirmPasswordRule(){
        return driver.element().getText(newPasswordMustMatchConfirmPasswordRule);
    }

    public String getCurrentPasswordInputType() {
        return driver.element().getAttribute(Txt_yourOldPassword, "type");
    }

    public void clickCurrentPasswordVisibilityToggle(){
         driver.element().click(visibilityToggleIcon);
    }

}
