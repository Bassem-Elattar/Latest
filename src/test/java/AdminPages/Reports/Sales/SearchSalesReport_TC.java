package AdminPages.Reports.Sales;

import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase_TC;
import AdminPages.Reports.Reports_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.DataUtils;
import utilities.JsonDataUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class SearchSalesReport_TC{
    private SearchSalesReport_Page searchSalesReport;
    private LogIn_Page logIn;
    SHAFT.GUI.WebDriver driver;
    @DataProvider(name = "JsonProvider")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String fileName = method.getName();
        String filePath = "./src/test/resources/testDataFiles/" + fileName + ".json";
        return JsonDataUtil.readJsonData(filePath);
    }

    @BeforeTest
    public void sign(){
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();
    }

    @Test(dataProvider = "JsonProvider")
    public void SearchSalesReport(Map<String, String> search){
        searchSalesReport = new SearchSalesReport_Page(driver);
        new Reports_Common(driver).clickReports().clickSales();
        String BranchName = search.get("BranchName");
        //String AgencyName = search.get("AgencyName");
        String Date = search.get("Date");
        String FromYear = search.get("FromYear");
        String FromMonth = search.get("FromMonth");
        String ToYear = search.get("ToYear");
        String ToMonth = search.get("ToMonth");
        String EndDate = search.get("EndDate");
        String InvoiceNumber = search.get("InvoiceNumber");
        String CustomerName = search.get("CustomerName");
        String PaymentDate = search.get("PaymentDate");
        String TransactionID = search.get("TransactionID");
        searchSalesReport.setBranchName(BranchName);
        //searchSalesReport.setAgencyName(AgencyName);
        searchSalesReport.setDate(Date,FromYear,FromMonth);
        searchSalesReport.setEndDate(EndDate,ToYear,ToMonth);
        searchSalesReport.setInvoiceNumber(InvoiceNumber);
        searchSalesReport.setCustomerName(CustomerName);
        searchSalesReport.setPaymentDate(PaymentDate);
        searchSalesReport.setTransactionID(TransactionID);
        searchSalesReport.setSearch();
    }
    @AfterMethod
    public void Reload(){
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }
}
