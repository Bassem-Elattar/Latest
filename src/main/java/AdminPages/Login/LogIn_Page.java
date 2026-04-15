package AdminPages.Login;

//import PreferAirline.PreferAirLine;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;
import utilities.DataUtils;

import java.util.Scanner;

public class LogIn_Page {
    SHAFT.GUI.WebDriver driver;
    private final SoftAssert softAssert;

    // Locators
    private final By Txt_Username = By.id("id-Username");
    private final By Txt_Password = By.id("id-Password");
    private final By Btn_RememberMyInfoNextTime = By.xpath("//div[@class='p-checkbox-box']");
    private final By Btn_LogINButton = By.xpath("// button[@loadingicon=\"pi pi-spin pi-spinner\"]");
    private final By Btn_ForgetPasswordButton = By.xpath("//a[@href='/auth/forgot']");
    private final By Btn_LogOutButtonLinlk = By.xpath("//i[@class='pi pi-angle-down profile-name__icon']");
    private final By Btn_LogOutButton=By.xpath("//span[normalize-space()='Logout']");
    private final By Txt_EmailField=By.id("id=\"id-Email\"");
    private final By Btn_ConfirmButton=By.xpath("//button[@class=\"p-element p-button-raised p-button-rounded p-button p-component\"]");
    public By Txt_ErrorOfUserName=By.xpath("/html/body/ndc-root/ndc-login-page/ndc-auth-container/div/div/div[1]/ndc-auth-form-container/div/div[2]/ndc-fg-form-generator/form/ndc-fg-input[1]/span");
    public By Txt_ErrorOfPassword=By.xpath("/html/body/ndc-root/ndc-login-page/ndc-auth-container/div/div/div[1]/ndc-auth-form-container/div/div[2]/ndc-fg-form-generator/form/ndc-fg-input[2]/span");
    public By Txt_InValidCred=By.xpath("//div[@aria-label=\"Invalid credentials, Please try again.\"]");

    public LogIn_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
        this.softAssert = new SoftAssert();
    }

    // Methods
    public void LogInNavigateToUrl() {
        driver.browser().maximizeWindow();
        String url = "http://192.168.1.70/auth/login";
        driver.browser().navigateToURL(url);
        //  driver.browser().maximizeWindow();  // Initialize the driver here

    }

    public LogIn_Page EnterUserName(String username) {
        driver.element().type(Txt_Username, username);
        return this;
    }

    public LogIn_Page EnterPassword(String password) {
        driver.element().type(Txt_Password, password);
        return this;
    }

    public LogIn_Page ClickRememberMe() {
        driver.element().click(Btn_RememberMyInfoNextTime);
        return this;
    }

    public LogIn_Page ClickOnLoginButton() {
        driver.element().click(Btn_LogINButton);
        return this;
    }

    public LogIn_Page ClickOnForgetPasswordButton() {
        driver.element().click(Btn_ForgetPasswordButton);
        return this;
    }
    public LogIn_Page ClickOnLogOutLinkButton(String LogOut) {
//        driver.element().click(LogOutButtonLinlk);
//        By option = xpath(String.format("//span[text()='%s']", LogOut));
//            driver.element().click(option);
        driver.element().select(Btn_LogOutButtonLinlk,LogOut);
        driver.element().click(Btn_LogOutButton);
        return this;
    }
    public LogIn_Page ClickOnLogOuTButton() {
//        driver.element().click(LogOutButtonLinlk);
//        By option = xpath(String.format("//span[text()='%s']", LogOut));
//            driver.element().click(option);
        driver.element().click(Btn_LogOutButtonLinlk);
        driver.element().click(Btn_LogOutButton);
        return this;
    }

    public LogIn_Page EnterUserEmail(String Email) {
        driver.element().type(Txt_EmailField, Email);
        return this;
    }
    public LogIn_Page ClickOnConfirmButton() {
        driver.element().click(Btn_ConfirmButton);
        return this;
    }
    public   void handleOTPPage() {
        String OTPUrl =driver.getDriver().getCurrentUrl();

        // Check if the OTP page is displayed
        if ( OTPUrl=="") {
            System.out.println("OTP page appeared.");

            // Select OTP delivery method (Assuming SMS is selected)
            driver.element().click(By.xpath("//input[@value='SMS']")); // Adjust locator as needed
            driver.element().click(By.id("confirmButton")); // Adjust locator as needed

            // Prompt user to enter OTP manually
            System.out.println("Please enter the OTP sent to your device:");
            Scanner scanner = new Scanner(System.in);
            String otpCode = scanner.nextLine();

            // Enter OTP in the OTP input fields
            driver.element().type(By.xpath("//input[@id='otpInput']"), otpCode); // Adjust locator as needed
            driver.element().click(By.id("submitCodeButton")); // Adjust locator as needed


        } else {
            System.out.println("User is trusted; OTP page did not appear.");
        }
    }

    public LogIn_Page ClickSuperAdmin() {
        driver.element().type(Txt_Username, "odeysysadmin");
        driver.element().type(Txt_Password, "qqE6)Cxp6>B8");
        return this;



    }
    public LogIn_Page ClickAdmin() {
        driver.element().type(Txt_Username, "E.saady");
        driver.element().type(Txt_Password, "qqE6)Cxp6>B8");
        return this;
    }
    public LogIn_Page AdminLogin() {
        driver.element().type(Txt_Username, DataUtils.get("Admin"));
        driver.element().type(Txt_Password, DataUtils.get("Password"));
        driver.element().click(Btn_LogINButton);
        return this;
    }
    public LogIn_Page ClickUser(String username,String password) {
        driver.element().type(Txt_Username, username);
        driver.element().type(Txt_Password, password);
        return this;



    }

}