package AdminPages.ImportPNR;

import AdminPages.Login.LogIn_Page;
import AdminPages.importPNR.ImportPNR_Page;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DataUtils;

public class ImportPNR_Test {

    SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private ImportPNR_Page importPNRPage;

    @BeforeTest
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();

        testData = new SHAFT.TestData.JSON("ImportPNR.json");
        importPNRPage = new ImportPNR_Page(driver);
        importPNRPage.navigateToImportPNRPage();
    }
    @AfterClass
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void TC01_importPNRSuccessfully() {
        importPNRPage.enterPNRCode(testData.getTestData("validAutoTicketingPnr.pnrCode"))
                .selectBranchName(testData.getTestData("validAutoTicketingPnr.branchName"))
                .selectSupplier(testData.getTestData("validAutoTicketingPnr.supplier"))
                .selectSupplierCredential(testData.getTestData("validAutoTicketingPnr.supplierCredential"))
                .clickSearchButton()
                .checkTermsAndConditionsCheckbox()
                .clickMainPayButton()
                .clickConfirmPayPopupPayButton();


        driver.element().assertThat(importPNRPage.getTicketConfirmedSuccessMessage())
                .text()
                .contains(testData.getTestData("validAutoTicketingPnr.expectedSuccessMessage"));
    }


}
