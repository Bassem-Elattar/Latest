package AdminPages.ImportPNR;

import AdminPages.Login.LogIn_Page;
import AdminPages.importPNR.ImportPNR_Page;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utilities.DataUtils;

public class ImportPNR_Test {

    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private ImportPNR_Page importPNRPage;

    @BeforeMethod
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();

        testData = new SHAFT.TestData.JSON("ImportPNR.json");
        importPNRPage = new ImportPNR_Page(driver);
        importPNRPage.navigateToImportPNRPage();
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void TC01_BranchSuccessfulImport() {
        importPNRPage.enterPNRCode(testData.getTestData("validAutoTicketingPnr.pnrCode"))
                .selectBranchName(testData.getTestData("validAutoTicketingPnr.branchName"))
                .selectSupplier(testData.getTestData("validAutoTicketingPnr.supplier"))
                .selectSupplierCredential(testData.getTestData("validAutoTicketingPnr.supplierCredential"))
                .clickSearchButton();

        String totalFareBeforePay= importPNRPage.getTotalFareBeforePay();

        importPNRPage.checkTermsAndConditionsCheckbox()
                .clickMainPayButton()
                .clickConfirmPayPopupPayButton();

        String totalFareAfterPay= importPNRPage.getTotalFareAfterPay();

        SoftAssert softAssert=new SoftAssert();

        String actual = importPNRPage.getTicketConfirmedSuccessMessageText();
        String expected = testData.getTestData("validAutoTicketingPnr.expectedSuccessMessage");

        softAssert.assertEquals(
                totalFareAfterPay,
                totalFareBeforePay,
                "Total fare before and after payment are not equal. "
                        + "Before Pay: " + totalFareBeforePay
                        + " | After Pay: " + totalFareAfterPay
        );

        softAssert.assertTrue(
                actual.contains(expected),
                "Success message is not correct. Actual: " + actual + " | Expected: " + expected
        );

        softAssert.assertAll();

    }

@Test
    public void TC08_ValidateTicketedPNR() {
        importPNRPage.enterPNRCode(testData.getTestData("validAutoTicketingPnr.pnrCode"))
                .selectBranchName(testData.getTestData("validAutoTicketingPnr.branchName"))
                .selectSupplier(testData.getTestData("validAutoTicketingPnr.supplier"))
                .selectSupplierCredential(testData.getTestData("validAutoTicketingPnr.supplierCredential"))
                .clickSearchButton();

        String actual = importPNRPage.getTicketedPNRStatusText().trim();
        String expected = testData.getTestData("validAutoTicketingPnr.pnrStatus").trim();
        Assert.assertEquals(actual,expected);
        Assert.assertFalse(importPNRPage.isPayButtonClickable());
    }

    @Test
    public void TC011_ValidateCancelledPNR() {
        importPNRPage.enterPNRCode(testData.getTestData("validAutoTicketingPnr.pnrCode"))
                .selectBranchName(testData.getTestData("validAutoTicketingPnr.branchName"))
                .selectSupplier(testData.getTestData("validAutoTicketingPnr.supplier"))
                .selectSupplierCredential(testData.getTestData("validAutoTicketingPnr.supplierCredential"))
                .clickSearchButton();
        String actual = importPNRPage.getCancelledPNRToastMessageText().trim();
        String expected = testData.getTestData("validAutoTicketingPnr.ExpectedCancelledPNRMessage").trim();
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void TC04_showValidationWhenPNRCodeLessThanSixChars(){
        importPNRPage.enterPNRCode(testData.getTestData("validAutoTicketingPnr.pnrCodeLessThanSixChar"));

        String actual=importPNRPage.getPNRCodeLessThanSixCharsValidationMessageText();
        String expected=testData.getTestData("validAutoTicketingPnr.expectedValidationMessageForPnrCodeLessThanSixChars");


        Assert.assertTrue(
                actual.contains(expected),
                "Success message is not correct. Actual: " + actual + " | Expected: " + expected
        );
    }

    @Test
    public void TC09_shouldShowToastMessageWhenPNRDoesNotExist(){
        importPNRPage.enterPNRCode(testData.getTestData("validAutoTicketingPnr.DummyPnrCode"))
                .selectBranchName(testData.getTestData("validAutoTicketingPnr.branchName"))
                .selectSupplier(testData.getTestData("validAutoTicketingPnr.supplier"))
                .selectSupplierCredential(testData.getTestData("validAutoTicketingPnr.supplierCredential"))
                .clickSearchButton();

        String invalidPnr=testData.getTestData("validAutoTicketingPnr.DummyPnrCode");

        String actual=importPNRPage.getUnableToRetrieveToastPNRCodeMessageText();
        String expected=testData.getTestData("validAutoTicketingPnr.toastMessageWhenPNRDoesNotExist")
                +" "+invalidPnr;

        Assert.assertTrue(
                actual.contains(expected),
                "Toast message is not correct. Actual: " + actual + " | Expected: " + expected
        );



    }

    @Test
    public void TC06_showToastWhenBranchDoesNotSupportImportPNR(){
        importPNRPage.enterPNRCode(testData.getTestData("validAutoTicketingPnr.DummyPnrCode"))
                .selectBranchName(testData.getTestData("validAutoTicketingPnr.branchImportPnrClosed"))
                .selectSupplier(testData.getTestData("validAutoTicketingPnr.supplier"))
                .selectSupplierCredential(testData.getTestData("validAutoTicketingPnr.supplierCredential"))
                .clickSearchButton();

        String actual=importPNRPage.getOrganizationDoesNotSupportImportPNRToastText();
        String expected=testData.getTestData("validAutoTicketingPnr.toastMessageWhenBranchImportPnrClosed");

        Assert.assertTrue(
                actual.contains(expected),
                "Toast message is not correct. Actual: " + actual + " | Expected: " + expected
        );


    }


}
