package AdminPages.Reports.SyncPNR;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Reports.Reports_Common;
import com.shaft.driver.SHAFT;
import org.testng.annotations.*;

import java.io.FileNotFoundException;

public class SyncPNR_TC extends TestBase_TC {
    SHAFT.TestData.JSON testData;

    @BeforeTest
    public void login() {
        new LogIn_Page(driver).ClickAdmin();
        new LogIn_Page(driver).ClickOnLoginButton();
        testData = new SHAFT.TestData.JSON("Sync.json");
    }

    @Test(priority = 1)
    public void verifyThatTheMessageNotFoundDisplaysCorrectly() throws FileNotFoundException, InterruptedException {
        new Reports_Common(driver).clickReports().clickSyncPNR();
        new SyncPNR_Page(driver)
                .searchValidFromDate(testData.getTestData("validDataNoOutput.FromDate"),testData.getTestData("validDataNoOutput.FromYear"),testData.getTestData("validDataNoOutput.FromMonth"))
                .searchValidToDate(testData.getTestData("validDataNoOutput.ToDate"),testData.getTestData("validDataNoOutput.ToYear"),testData.getTestData("validDataNoOutput.ToMonth"))
                .clickSubmit()
                .VerifyNoFoundMessageIsDisplaying();
    }

    @Test(priority = 2)
    public void verifyThatTheUserSearchDataWithMandatoryFieldOnly() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickSyncPNR();
        new SyncPNR_Page(driver)
                .searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
                .clickSubmit()
                .VerifyThatOrderIdDisplaysCorrectly();
    }

    @Test(priority = 3)
    public void verifyThatUserCanSearchWithAllFields() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickSyncPNR();
        new SyncPNR_Page(driver)
                .searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
                .sendOrderID()
                .sendFBBookingQRrefNo()
                .clickSubmit()
                .VerifyThatOrderIdDisplaysCorrectly()
                .VerifyThatBookingIdDisplaysCorrectly();
    }

    @Test(priority = 4)
    public void verifyThatUserCanSearchWithDateAndOrderId() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickSyncPNR();
        new SyncPNR_Page(driver)
                .searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
                .sendOrderID()
                .clickSubmit()
                .VerifyThatOrderIdDisplaysCorrectly();
    }
    @Test(priority = 5)
    public void verifyThatUserCanSearchWithDateAndBookingId() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickSyncPNR();
        new SyncPNR_Page(driver)
                .searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
                .sendFBBookingQRrefNo()
                .clickSubmit()
                .VerifyThatBookingIdDisplaysCorrectly();
    }

//    @Test(priority = 6)
//    public void verifyThatTheUserCanPaginateCorrectly() throws InterruptedException {
//        new Reports_Common(driver).clickReports().clickSyncPNR();
//        new SyncPNR_Page(driver)
//                .searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
//                .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
//                .clickSubmit()
//                .clickthePaginateButton()
//                .VerifyThatsystemPaginateCorrectly();
//    }

    @Test(priority = 7)
    public void verifyThatUserCanExportTheReportAsExcel() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickSyncPNR();
        new SyncPNR_Page(driver)
                .searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
                .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
                .clickSubmit()
                .verifyThatTheExportToExcelIsClickable();
    }

    @Test(priority = 8)
    public void verifyThatSystemBlockTheUserSearchWithEmptyData() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickSyncPNR();
        new SyncPNR_Page(driver)
                .clickSubmit()
                .VerifyThatSystemdShowErrorMessage();
    }

    @AfterMethod
    public void Reload() {
        driver.browser().navigateToURL("http://192.168.1.70");
    }

}
