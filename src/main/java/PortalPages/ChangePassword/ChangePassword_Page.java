package PortalPages.ChangePassword;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class ChangePassword_Page {
    public ChangePassword_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;


    By Btn_Settings = By.xpath("button[@class='p-element icon-button icon-button--settings ng-star-inserted']");
    By Txt_yourOldPassword = By.xpath("input[@class='p-inputtext p-component p-element ng-tns-c233-14 p-password-input']");
    By Txt_yourNewPassword = By.xpath("input[@class='p-inputtext p-component p-element ng-tns-c233-15 p-password-input']");
    By Txt_enterConfirmPassword = By.xpath("input[@class='p-inputtext p-component p-element ng-tns-c233-16 p-password-input']");
    By Btn_updatePassword = By.xpath("input[@class='p-element save-main-btn p-button p-component']");
    By Old_PasswordConfirmMessage = By.xpath("div[@class='ng-tns-c127-5 toast-message ng-star-inserted']");
    By Btn_NavigateToChangePasswordPage = By.xpath("a[contains(@href, 'change-password')]");



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


}
