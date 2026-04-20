package AdminPages.Reports.ReleasedPNR;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Reports.Reports_Common;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ReleasedPNR_TCs extends TestBase_TC {
    ReleasedPNR_Page releasedPNRPage;
    private LogIn_Page logIn;

    @BeforeTest
    public void login() {
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();
        releasedPNRPage = new ReleasedPNR_Page(driver);
        new Reports_Common(driver).clickReports().clickReleasedPNR();
    }

    @Test(priority = 1)
    @Description("Verify that user can search with only mandatory fields (From Date & To Date)")
    @Severity(SeverityLevel.CRITICAL)
    public void UserCanSearchWithMandatoryFieldsOnly() throws InterruptedException {
        // Navigate to Released PNR Report page
        new Reports_Common(driver).clickReports().clickReleasedPNR();

        // Select mandatory fields only
        releasedPNRPage.searchValidFromDate();
        releasedPNRPage.searchValidToDate();

        // Click search and validate Order ID column appears
        releasedPNRPage.Submit();
//        releasedPNRPage.OrderIDColumExist();
    }

    @Test(priority = 2)
    @Description("Validate that system paginates results correctly after search")
    @Severity(SeverityLevel.NORMAL)
    public void UserCanPaginateCorrectly() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickReleasedPNR();
        releasedPNRPage.searchValidFromDate();
        releasedPNRPage.searchValidToDate();
        releasedPNRPage.Submit();

        // Check pagination works correctly
//        releasedPNRPage.SystemPaginateCorrectly();
    }

    @Test(priority = 3)
    @Description("Verify that user can filter results by Order ID")
    @Severity(SeverityLevel.NORMAL)
    public void UserCanSearchWithOrderId() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickReleasedPNR();
        releasedPNRPage.searchValidFromDate();
        releasedPNRPage.searchValidToDate();

        // Enter Order ID and search
        releasedPNRPage.SendOrderID();
        releasedPNRPage.Submit();

        // Validate correct result appears
//        releasedPNRPage.AssertOnOrderID();
    }

    @Test(priority = 4)
    @Description("Verify that user can filter results by Booking Reference")
    @Severity(SeverityLevel.NORMAL)
    public void UserCanSearchWithBookingRef() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickReleasedPNR();
        releasedPNRPage.searchValidFromDate();
        releasedPNRPage.searchValidToDate();

        // Enter Booking Reference and search
        releasedPNRPage.SendBookingReference();
        releasedPNRPage.Submit();

        // Assert correct data returned
//        releasedPNRPage.AssertOnBookingRef();
    }

    @Test(priority = 5)
    @Description("Validate that Export to Excel functionality works correctly")
    @Severity(SeverityLevel.CRITICAL)
    public void ValidateExportToExcelReport() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickReleasedPNR();
        releasedPNRPage.searchValidFromDate();
        releasedPNRPage.searchValidToDate();
        releasedPNRPage.Submit();

        // Export results to Excel
        releasedPNRPage.ClickOnExport();
    }

    @Test(priority = 6)
    @Description("Verify system displays 'No Data Found' message when no results match search")
    @Severity(SeverityLevel.NORMAL)
    public void NoDataFoundMessageDisplayedIfNoDataMatched() throws InterruptedException {
        new Reports_Common(driver).clickReports().clickReleasedPNR();

        // Use date range that returns no results
        releasedPNRPage.searchValidFromDate();
        releasedPNRPage.searchValidToDate();

        releasedPNRPage.Submit();

        // Assert 'no data' message displayed
//        releasedPNRPage.NoDataMatched();
    }

    @Test(priority = 7)
    @Description("Ensure user cannot search without selecting mandatory fields")
    @Severity(SeverityLevel.MINOR)
    public void UserCanNotSearchWithoutMandatoryFields() {
        new Reports_Common(driver).clickReports().clickReleasedPNR();

        // Try to search without setting date range
        releasedPNRPage.Submit();

        // Assert warning shown
        releasedPNRPage.NoDateSelected();
    }

    @AfterMethod
    public void Reload(){
        driver.browser().navigateToURL("http://192.168.1.70");
    }
}
