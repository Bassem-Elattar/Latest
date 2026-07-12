package PortalPages.Topup;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.FileUploadUtil;

import java.time.Duration;

public class TopUp {
    private SHAFT.GUI.WebDriver driver;

    public TopUp(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    private final By Btn_List = By.xpath("(//button[@icon='pi pi-list'])[2]");
    private final By Btn_TopUpWallet = By.xpath("(//button[contains(.,'Top Up Wallet')])[1]");
    private final By Txt_Amount = By.id("id-Amount");
    private final By Btn_Cash = By.xpath("//span[normalize-space()='Cash']");
    private final By Btn_BanqueMisr = By.xpath("//span[normalize-space()='BANQUE MISR']");
    private final By Btn_Pay = By.xpath("//button[.//span[text()='Pay']]");
    private final By Btn_Fawry = By.xpath("//span[normalize-space()='Fawry']");
    private final By Btn_UploadDocument =
            By.xpath("//span[normalize-space()='Upload document']");
    private final By Lst_Payment =
            By.xpath("//span[text()='Select']");
    private final By Btn_PaymentOption = By.xpath("//li[span[text()='bankTransfer']]");
    private final By Txt_Remark = By.xpath("(//textarea[@placeholder='Remarks ...'])[2]");
    private final By Btn_sendForApproval = By.xpath("//button[.//span[text()='Send for Approval']]");
    private final By lbl_TopUpAmount =
            By.xpath("//span[@class='fw-bold' and contains(text(),'EGP')]");
    private final By Btn_Close =
            By.xpath("//span[contains(@class,'p-dialog-header-close-icon')]/parent::button");
    private final By Txt_CardNumber = By.xpath("//input[@id='cardNumber']");
    private final By Dpick_ExpiryMonth = By.id("expiryMonth");
    private final By Btn_selectMonth = By.xpath("//option[@value='01']");
    private final By Dpick_ExpiryYear = By.id("expiryYear");
    private final By Btn_selectYear = By.xpath("//option[text()='39']");

    private final By Txt_CardHolderName = By.id("cardHolderName");
    private final By Txt_CVV = By.id("csc");
    private final By Btn_Next = By.xpath("(//button[normalize-space()='Next'])[1]");
    private final By Cbox_Confirmation =
            By.id("confirmationCheckbox");
    private final By Btn_PayNow =
            By.xpath("(//button[normalize-space()='Pay now'])[1]");
    private final By Btn_submit =
            By.xpath("//input[@id='acssubmit']");
    private final By Txt_Email = By.id("id-Email");
    private final By Txt_PhoneNumber = By.id("Phone Number");
    private final By Txt_Remarks =
            By.xpath("(//textarea[@placeholder='Remarks ...'])[1]");
    private final By Btn_Submit = By.xpath("(//button[.//span[text()='Submit']])[1]");
    private final By lbl_SuccessMessage =
            By.xpath("//*[contains(text(),'Your top-up will be added to your credit balance once approved')]");


////////////////MethodsCase1///////////////////////


    public TopUp clickListButton() {
        driver.element().click(Btn_List);
        return this;
    }

    public TopUp clickTopUpWalletButton() {
        driver.element().click(Btn_TopUpWallet);
        return this;
    }

    public TopUp enterAmount(String amount) {
        driver.element().click(Txt_Amount);
        driver.element().type(Txt_Amount, amount);
        return this;
    }

    public TopUp openPaymentDropdown() {
        driver.element().click(Lst_Payment);
        return this;
    }

    public TopUp selectCash() {
        driver.element().click(Btn_Cash);
        return this;
    }
    public TopUp closePopUp() {
        driver.element().click(Btn_Close);
        return this;
    }

    public TopUp selectBanqueMisr() {
        driver.element().click(Btn_BanqueMisr);
        return this;
    }
    public TopUp clickOnPay() {
        driver.element().click(Btn_Pay);
        return this;
    }
    //////////////////////////////////////////////////////
  //////////MethhodsCase2////////////////
    public TopUp enterCardNumber(String card) {
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(20))
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                        By.cssSelector("iframe[title='Hosted Checkout']")
                ));
        driver.element().click(Txt_CardNumber);
        driver.element().type(Txt_CardNumber, card);
        return this;
    }
    public TopUp selectExpiryMonth() {
        driver.element().click(Dpick_ExpiryMonth);
        driver.element().click(Btn_selectMonth);
        return this;
    }

    public TopUp selectExpiryYear() {
        driver.element().click(Dpick_ExpiryYear);
        driver.element().click(Btn_selectYear);
        return this;
    }
    public TopUp enterCardHolderName(String cardHolderName) {
        driver.element().type(Txt_CardHolderName, cardHolderName);
        return this;
    }
    public TopUp enterCVV(String cvv) {
        driver.element().click(Txt_CVV);
        driver.element().type(Txt_CVV, cvv);
        return this;
    }
    public TopUp clickNext() {
        driver.element().click(Btn_Next);
        return this;
    }

    public TopUp clickConfirm(){
        driver.element().waitToBeReady(Cbox_Confirmation);



        driver.element().click(Cbox_Confirmation);
        return this;
    }
    public TopUp clickPay(){
        driver.element().waitToBeReady(Btn_PayNow);
        driver.element().click(Btn_PayNow);
        return this;
    }

public TopUp clicksubmit() {

    driver.getDriver().switchTo().defaultContent();

    new WebDriverWait(driver.getDriver(), Duration.ofSeconds(20))
            .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                    By.cssSelector("iframe[title='Hosted Checkout']")));

    new WebDriverWait(driver.getDriver(), Duration.ofSeconds(20))
            .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                    By.id("challengeFrame")));

    driver.element().waitToBeReady(Btn_submit);


    driver.element().click(Btn_submit);

    return this;
}
///////////////////////////////////////////////////////////
/////////////////MethodsCase3//////////////////////////
    public TopUp selectFawry() {
        driver.element().click(Btn_Fawry);
        return this;
    }
    public TopUp enterEmail(String email) {
        driver.element().type(Txt_Email, email);
        return this;
    }

    public TopUp enterPhoneNumber(String phoneNumber) {
        driver.element().type(Txt_PhoneNumber, phoneNumber);
        return this;
    }
    public TopUp enterRemark(String remarks) {
        driver.element().type(Txt_Remark, remarks);
        return this;
    }
    public TopUp clickOnSubmit() {
        driver.element().click(Btn_Submit);
        return this;
    }

    public TopUp selectBankTransfer() {
        driver.element().click(Btn_PaymentOption);
        return this;
    }

    public TopUp enterRemarks(String remarksText) {
    driver.element().click(Txt_Remarks);
        driver.element().type(Txt_Remarks, remarksText);
        return this;
    }

    public TopUp clickUploadDocument() {
        driver.element().click(Btn_UploadDocument);
        return this;
    }

    public TopUp clickSendForApproval() {
        driver.element().click(Btn_sendForApproval);
        return this;
    }
    public TopUp UploadPDF(String filePath) {
        By fileInputLocator = By.xpath("//input[@type='file']");


        FileUploadUtil.uploadFile(driver.getDriver(), fileInputLocator, filePath);

        return this;
    }



    /////////////////////////////////////MethodsAssert/////////////////////////////////////////////////////////

    public By getSuccessMessageLocator() {
        return lbl_SuccessMessage;
    }
    public By getTopUpAmountLocator() {
        return lbl_TopUpAmount;
    }


}


