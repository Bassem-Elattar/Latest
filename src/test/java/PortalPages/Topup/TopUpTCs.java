package PortalPages.Topup;
import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.shaft.driver.DriverFactory;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DataUtils;

import java.util.HashMap;
import java.util.Map;


public class TopUpTCs {
    private SHAFT.TestData.JSON testData;
    public SHAFT.GUI.WebDriver driver;


    @BeforeTest
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();

        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
        new Login_Page(driver).PortalLogin();
    }
    /////////////////////////////////////////////////

    @Test(priority = 1
    )
    public void verifyThatAgencyCanCreateTopUpSuccessfully() throws InterruptedException {
        testData = new SHAFT.TestData.JSON("TopUpPortal.json");
        Thread.sleep(10000);


        TopUp topupwallet = new TopUp(driver);
        topupwallet
                .clickTopUpWalletButton()
                .enterAmount(testData.getTestData("amount"))
               .UploadPDF("src/test/resources/image_200x200.png")
                .openPaymentDropdown()
                .selectBankTransfer()
                .enterRemarks(testData.getTestData("remark"))
                .clickSendForApproval();
        ///////////////////Assertion/////////////////////////
        driver.assertThat()
                .element(topupwallet.getSuccessMessageLocator())
                .exists()
                .perform();
        /////////////////////////////////////////
        String expectedAmount = testData.getTestData("amount");
        driver.assertThat()
                .element(topupwallet.getTopUpAmountLocator())
                .text()
                .contains(expectedAmount)
                .perform();
        Thread.sleep(10000);
        topupwallet.closePopUp();



    }
    @Test(priority = 2)
    public void verifyThatAgencyCanCreateTopUpSuccessfullywithBanqueMasr() throws InterruptedException {
        testData = new SHAFT.TestData.JSON("TopUpPortal.json");
        Thread.sleep(10000);

        TopUp topup = new TopUp(driver);
        topup
                .clickTopUpWalletButton()
                .enterAmount(testData.getTestData("amount"))
                .selectBanqueMisr()
                .clickOnPay()
                .enterCardNumber(testData.getTestData("cardNum"))
                .selectExpiryMonth()
                .selectExpiryYear()
                .enterCardHolderName(testData.getTestData("CardHolderName"))
                .enterCVV(testData.getTestData("Securitycode"))
                .clickNext()
                        .clickConfirm()
                                .clickPay()
                                        .clicksubmit();
        Thread.sleep(10000);
        ///////////////////Assertion/////////////////////////
        String expectedAmount = testData.getTestData("amount");
        driver.assertThat()
                .element(topup.getTopUpAmountLocator())
                .text()
                .contains(expectedAmount)
                .perform();
                                               topup.closePopUp();


    }
    @Test(priority = 3)
    public void verifyThatAgencyCanCreateTopUpSuccessfullywithFawry() throws InterruptedException {
        testData = new SHAFT.TestData.JSON("TopUpPortal.json");
        Thread.sleep(10000);

        TopUp topup = new TopUp(driver);
        topup
                .clickTopUpWalletButton()
                .enterAmount(testData.getTestData("amount"))
                .selectFawry()
                .enterEmail(testData.getTestData("email"))
                .enterPhoneNumber(testData.getTestData("phoneNumber"))
                        .enterRemark(testData.getTestData("remark"))
                                .clickOnSubmit();
        ///////////////////Assertion/////////////////////////
        driver.assertThat()
                .element(topup.getSuccessMessageLocator())
                .exists()
                .perform();

        Thread.sleep(5000);


    }
    @Test(priority = 4)
    public void endToEndTopUp() throws InterruptedException {
        testData = new SHAFT.TestData.JSON("TopUpPortal.json");
        TopUp newtopup = new TopUp(driver);
        Thread.sleep(10000);

        newtopup.
                clickTopUpWalletButton()
                .enterAmount(testData.getTestData("amount"))
                .UploadPDF("src/test/resources/image_200x200.png")
                .openPaymentDropdown()
                .selectBankTransfer()
                .enterRemarks(testData.getTestData("remark"))
                .clickSendForApproval()
                .closePopUp()
                .enterAmount(testData.getTestData("amount"))
                .selectBanqueMisr()
                .clickOnPay()
                .enterCardNumber(testData.getTestData("cardNum"))
                .selectExpiryMonth()
                .selectExpiryYear()
                .enterCardHolderName(testData.getTestData("CardHolderName"))
                .enterCVV(testData.getTestData("Securitycode"))
                .clickNext()
                .clickConfirm()
                .clickPay()
                .clicksubmit();
        Thread.sleep(10000);

        newtopup
                .closePopUp()
                .enterAmount(testData.getTestData("amount"))
                .selectFawry()
                .enterEmail(testData.getTestData("email"))
                .enterPhoneNumber(testData.getTestData("phoneNumber"))
                .enterRemark(testData.getTestData("remark"))
                .clickOnSubmit()
                .closePopUp();

    }


}
