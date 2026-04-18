package AdminPages.Reports.LedgerReportDetails;
import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Reports.LedgerReport.LedgerReportDetails_Page;
import AdminPages.Reports.Reports_Common;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonDataUtil;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class LedgerReportDetails_TC extends TestBase_TC {
    AdminPages.Helper.PaginationHelper paginationHelper;
    LedgerReportDetails_Page ledgerReportDetailsPage;
    private LogIn_Page logIn;

    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeTest
    public void sign(){
        logIn = new LogIn_Page(driver);
        logIn.ClickAdmin();
        logIn.ClickOnLoginButton();

    }

    @Test(dataProvider = "JsonProvider")
    public void SearchLedgerReport(Map<String, String> search) throws InterruptedException {
        ledgerReportDetailsPage= new LedgerReportDetails_Page(driver);
        paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
        String BranchName = search.get("BranchName");
        String AgencyName = search.get("AgencyName");
        String InvoiceDateFrom = search.get("InvoiceDateFrom");
        String InvoiceDateTo = search.get("InvoiceDateTo");
        new Reports_Common(driver).clickReports().clickARLedger();
        ledgerReportDetailsPage.Lst_BranchName(BranchName);
//        ledgerReportDetailsPage.Lst_AgencyName(AgencyName);
        ledgerReportDetailsPage.Dpick_InvoiceFromDate(InvoiceDateFrom);
        ledgerReportDetailsPage.Dpick_InvoiceToDate(InvoiceDateTo);
        ledgerReportDetailsPage.Btn_SearchGrid();

        // Handle pagination and assertions separately
        int totalPages = paginationHelper.getTotalPages();
        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Processing page: " + currentPage);

            // Navigate to the next page if not on the last page
            if (currentPage < totalPages) {
                paginationHelper.navigateToNextPage();
            }
        }
    }
}
