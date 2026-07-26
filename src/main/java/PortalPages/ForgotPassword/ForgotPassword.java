package PortalPages.ForgotPassword;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class ForgotPassword {
    private SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;

    public ForgotPassword(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    private final By Btn_forgotPassword = By.xpath("//a[normalize-space()='Forgot Password?']");
    private final By Txt_agencyCodeInput = By.id("id-AgencyCode");
    public  final By Txt_Email = By.id("id-Email");
    public  final By Btn_Confirm = By.xpath("//span[normalize-space()='Confirm']");
    private final By Txt_newPassword = By.id("newPassword");
    private final By Txt_confirmNewPassword = By.id("confNewPassword");
    private final By Btn_submit = By.xpath("//input[@type='submit' and @value='Submit']");
    private final By Txt_successMessage = By.xpath("//div[@role='alert' and @aria-label='Password reset link has been sent to your registered email.']");
    private final By Btn_ok = By.xpath("//a[normalize-space()='OK']");
    private final By Txt_agencyCode = By.id("agencyCodeMain");
    private final By Txt_email = By.id("userAlias");
    private final By Txt_password = By.id("password_password");
    private final By Btn_login = By.xpath("//input[@type='submit' and @value='LOGIN']");
    private final By Btn_SignOut = By.xpath("//a[normalize-space()='Sign Out']");

    private final By Txt_uppercaseValidation = By.id("upper");
    private final By Lbl_AgentCode = By.xpath("//h4[contains(text(),'AGN9881')]");


    /////Methods//////////////
    public ForgotPassword clickForgotPassword() {
        driver.element().click(Btn_forgotPassword);
        return new ForgotPassword(driver);
    }
    public ForgotPassword enterAgencyCode(String agencyCode) {
        driver.element().type(Txt_agencyCodeInput, agencyCode);
        return this;
    }
    public ForgotPassword enterEmail(String email) {
        driver.element().type(Txt_Email, email);
        return this;
    }
    public ForgotPassword clickConfirm() {
        driver.element().click(Btn_Confirm);
        return this;
    }
    public ForgotPassword enterNewPassword(String newPassword) {
        driver.element().type(Txt_newPassword, newPassword);
        return this;
    }
    public ForgotPassword enterConfirmNewPassword(String confirmNewPassword) {
        driver.element().type(Txt_confirmNewPassword, confirmNewPassword);
        return this;
    }
    public ForgotPassword clickSubmit() {
        driver.element().click(Btn_submit);
        return this;
    }
    public ForgotPassword assertSuccessMessageText() {

        driver.element().waitToBeReady(Txt_successMessage);

        driver.assertThat()
                .element(Txt_successMessage)
                .text()
                .isEqualTo("Password reset link has been sent to your registered email.")
                .perform();

        return this;
    }

    public ForgotPassword clickOk() {
        driver.element().click(Btn_ok);
        return this ;
      //  return new LoginPage(driver);
    }
    public ForgotPassword enterAgentCode(String agencyCode) {
        driver.element().type(Txt_agencyCode, agencyCode);
        return this;
    }
    public ForgotPassword enterMail(String email) {
        driver.element().type(Txt_email, email);
        return this;
    }
    public ForgotPassword enterPassword(String password) {
        driver.element().type(Txt_password, password);
        return this;
    }
    public ForgotPassword clickLogin() {
        driver.element().click(Btn_login);
        return this;
    }
    public ForgotPassword clickSignOut() {
        driver.element().click(Btn_SignOut);
        return this;
    }

    public ForgotPassword CheckAgentCode() {
        driver.assertThat()
                .element(Lbl_AgentCode)
                .text()
                .isEqualTo("AGN9881")
                .perform();
        return this;
    }


    public ForgotPassword assertUppercaseValidation() {

        driver.assertThat()
                .element(Txt_uppercaseValidation)
                .text()
                .isEqualTo("At least one uppercase")
                .perform();
        driver.assertThat()
                .element(Txt_uppercaseValidation)
                .cssProperty("color")
                .isEqualTo("rgba(255, 0, 0, 1)")
                .perform();

        return this;
    }















}
