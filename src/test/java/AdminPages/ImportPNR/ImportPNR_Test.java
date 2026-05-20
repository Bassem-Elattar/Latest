package AdminPages.ImportPNR;

import AdminPages.Login.LogIn_Page;
import AdminPages.importPNR.ImportPNR_Page;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
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

@Test
    public void TC02_VerifyOrganizationDoesNotSupportImportPnr () {
    importPNRPage.enterPNRCode(testData.getTestData("validAutoTicketingPnr.pnrCode"))
            .selectBranchName(testData.getTestData("validAutoTicketingPnr.branchName"))
            .selectAgencyName(testData.getTestData("validAutoTicketingPnr.agencyName"))
            .selectAgentName(testData.getTestData("validAutoTicketingPnr.agentName"))
            .selectSupplier(testData.getTestData("validAutoTicketingPnr.supplier"))
            .selectSupplierCredential(testData.getTestData("validAutoTicketingPnr.supplierCredential"))
            .clickSearchButton();

    // TODO :  Assert for Appear message when agency not support or can't The import pnr

    String actualMessage = driver.element().getText(By.xpath("//div[@aria-label='Selected Organization does not support import pnr functionality']"));

   Assert.assertTrue(actualMessage.contains("Selected Organization does not support import pnr functionality"));
}

@Test
    public void TC03_validatePnrCodeLength (){
        importPNRPage.enterPNRCode(testData.getTestData("validAutoTicketingPnr.pnrCode"));

        String actualMessage = driver.element().getText(By.xpath("//span[normalize-space()='PNR code cannot exceed 9 characters']"));
        Assert.assertTrue(actualMessage.matches("PNR code cannot exceed 9 characters554"));
}














}
