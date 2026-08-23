package PortalPages.Login;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import utilities.DataUtils;

public class Login_Page {

    private SHAFT.GUI.WebDriver driver;

    // Text Fields
    private By Txt_AgencyCode=By.id("id-AgencyCode");
    private By Txt_Email=By.id("id-Email");
    private By Txt_Password=By.id("id-Password");

    // Checkboxes
    private By Cbox_RememberMe=By.className("p-checkbox-box");

    // Buttons
    private By Btn_Login =By.cssSelector("button[type='submit']");

    // Icons
    private By Icn_ShowPassword =By.cssSelector("i.pi.pi-eye");

    // Links
    private By Lnk_ForgotPassword = By.linkText("Forgot Password?");
    private By Lnk_RequestRegistration = By.linkText("Request for Registration");

    // Text / Page Validation
    private By Txt_LoginPageTitle =By.className("welcome-title");

    // Post-login indicator - only renders once the session is actually established.
    // Verified live: appears on /dashboard immediately after a successful login.
    //
    // The Portal renders the wallet widget TWICE - once live, once as a hidden duplicate
    // inside a second ".wallet-sidebar-section.hidden" (confirmed live 2026-08-12: two
    // ".balance-amount" elements, identical text, one inside a "hidden"-classed ancestor).
    // A bare ".balance-amount" throws MultipleElementsFoundException. Scoped here to the
    // section that is NOT hidden.
    private By Txt_WalletBalance = By.cssSelector(".wallet-sidebar-section:not(.hidden) .balance-amount");

    // Toast Error Messages
    private By Txt_InvalidLoginMessage = By.xpath("//div[@role='alert' and contains(@class,'toast-message') and @aria-label='Invalid credentials, Please try again.']");

    // Validation Messages
    private By Txt_InvalidAgencyCodeValidationMessage = By.xpath("//span[normalize-space()='Please enter a valid agency code']");
    private By Txt_InvalidEmailValidationMessage = By.xpath("//span[normalize-space()='Please enter a valid email']");

    // Required Validation Messages
    private By Txt_AgencyCodeRequiredMessage = By.xpath("//input[@id='id-AgencyCode']/following::span[normalize-space()='Required'][1]");
    private By Txt_EmailRequiredMessage = By.xpath("//input[@id='id-Email']/following::span[normalize-space()='Required'][1]");
    private By Txt_PasswordRequiredMessage = By.xpath("//input[@id='id-Password']/following::span[normalize-space()='Required'][1]");

    // Constructor
    public Login_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }


    public void waitUntilLoaded() {
        driver.element().waitToBeReady(Txt_Email);
        driver.element().waitToBeReady(Txt_Password);
    }

    // Navigate to login page
    public Login_Page navigateToLoginPage(String loginUrl) {
        driver.browser().navigateToURL(loginUrl);
        return this;
    }

    // Enter agency code
    public Login_Page enterAgencyCode(String agencyCode) {
        driver.element().type(Txt_AgencyCode, agencyCode);
        return this;
    }

    // Enter email
    public Login_Page enterEmail(String email) {
        driver.element().type(Txt_Email, email);
        return this;
    }

    // Enter password
    public Login_Page enterPassword(String password) {
        driver.element().type(Txt_Password, password);
        return this;
    }

    // Click remember me checkbox
    public Login_Page clickRememberMeCheckbox() {
        driver.element().click(Cbox_RememberMe);
        return this;
    }

    // Click show password icon
    public Login_Page clickShowPasswordIcon() {
        driver.element().click(Icn_ShowPassword);
        return this;
    }

    // Click login button
    public Login_Page clickLoginButton() {
        driver.element().click(Btn_Login);
        return this;
    }

    /**
     * Waits for a signal that the session is actually established, not just that the
     * button was clicked. clickLoginButton() returns as soon as the click fires - it does
     * NOT wait for the async login call to resolve. Without this, a caller that navigates
     * elsewhere immediately after clickLoginButton() can race the login request: the app
     * treats the session as not-yet-authenticated and bounces back to /auth/login, so the
     * intended destination page never renders its own fields. Confirmed live 2026-08-12 -
     * this exact race caused ImportPnr_TC.setup() to time out waiting for #id-PNRCode.
     */
    public Login_Page waitUntilLoggedIn() {
        driver.element().waitToBeReady(Txt_WalletBalance);
        return this;
    }

    // Click Forgot Password link
    public Login_Page clickForgotPasswordLink() {
        driver.element().click(Lnk_ForgotPassword);
        return this;
    }

    // Click Request for Registration link
    public Login_Page clickRequestRegistrationLink() {
        driver.element().click(Lnk_RequestRegistration);
        return this;
    }

    // Text Getters
    public String getTxt_InvalidLoginMessage() {
        return driver.element().getText(Txt_InvalidLoginMessage);
    }

    public String getTxt_InvalidAgencyCodeValidationMessage() {
        return driver.element().getText(Txt_InvalidAgencyCodeValidationMessage);
    }

    public String getTxt_InvalidEmailValidationMessage() {
        return driver.element().getText(Txt_InvalidEmailValidationMessage);
    }

    public String getTxt_AgencyCodeRequiredMessage() {
        return driver.element().getText(Txt_AgencyCodeRequiredMessage);
    }

    public String getTxt_EmailRequiredMessage() {
        return driver.element().getText(Txt_EmailRequiredMessage);
    }

    public String getTxt_PasswordRequiredMessage() {
        return driver.element().getText(Txt_PasswordRequiredMessage);
    }

    public void PortalLogin() {
        driver.element().type(Txt_AgencyCode, DataUtils.get("Portal_AgencyCode"));
        driver.element().type(Txt_Email, DataUtils.get("Portal_Email"));
        driver.element().type(Txt_Password, DataUtils.get("Portal_Password"));
        driver.element().click(Btn_Login);
    }







}
