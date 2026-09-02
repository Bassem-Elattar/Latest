package PortalPages.Login;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class OTPVerification {

    SHAFT.GUI.WebDriver driver;

    public OTPVerification (SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By gmail_select_mail = By.xpath("//div[@class='p-radiobutton-box']");
    private final By confirm_btn = By.xpath("//button[@type='submit']");
    private final By otp_field = By.xpath("//body//ndc-root//input[1]");
    private final By gmail_submit_btn = By.xpath("//span[normalize-space()='Submit Code']");
    private final By error_Toast = By.xpath("//div[@id='toast-container']");
    private final By resend_OTP = By.xpath("//button[@class='p-button p-component p-element col-sm-12 col-md-6 col-lg-4 mx-3 p-ripple send p-button p-component']");
    private final By three_attempts_error = By.xpath("//div[contains(@aria-label,'Your account has been locked')]");

    public void OTPRequest() throws InterruptedException {
        driver.element().click(gmail_select_mail);
        driver.element().click(confirm_btn);
        Thread.sleep(10000);
    }

    public void OTPSubmit(String OTP) {
        driver.element().click(otp_field);
        driver.element().clear(otp_field);
        driver.element().type(otp_field, OTP);
        driver.element().click(gmail_submit_btn);

    }

    public String InvalidCode() {
        return driver.element().waitToBeReady(error_Toast).getText(error_Toast);
    }

    public String ExceededAttempt() {
        return driver.element().waitToBeReady(three_attempts_error).getText(three_attempts_error);
    }

    public void SecondTimeResend() throws InterruptedException {
        driver.element().click(gmail_select_mail);
        driver.element().click(confirm_btn);
        Thread.sleep(2000);
    }

    public void OTPResend() throws InterruptedException {
        Thread.sleep(54000);
        driver.element().click(resend_OTP);
        Thread.sleep(10000);
    }

    public static String get4DigitRandom() {
        int number = (int)(Math.random() * 9000) + 1000;
        return String.valueOf(number);
    }

}

