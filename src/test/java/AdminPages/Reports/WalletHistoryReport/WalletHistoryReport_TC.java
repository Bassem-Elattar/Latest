package AdminPages.Reports.WalletHistoryReport;

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

public class WalletHistoryReport_TC {

    private WalletHistoryReport_Page walletHistoryReportPage;
    private LogIn_Page logIn;
    SHAFT.TestData.JSON testData;
    SHAFT.GUI.WebDriver driver;
    @BeforeTest
    public void sign(){
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));

        new LogIn_Page(driver).AdminLogin();
        walletHistoryReportPage = new WalletHistoryReport_Page(driver);
        new Reports_Common(driver).clickReports().clickWalletHistory();
        testData = new SHAFT.TestData.JSON("WalletHistoryReport.json");
    }

    @Test
    public void WalletHistoryReport() throws InterruptedException {
        walletHistoryReportPage.SelectBranch();
        walletHistoryReportPage.searchValidFromDate(testData.getTestData("ValidBranch.InvoiceFromDate"), testData.getTestData("ValidBranch.FromYear"), testData.getTestData("ValidBranch.FromMonth"));
        walletHistoryReportPage.searchValidToDate(testData.getTestData("ValidBranch.InvoiceToDate"), testData.getTestData("ValidBranch.ToYear"), testData.getTestData("ValidBranch.ToMonth"));
        walletHistoryReportPage.setSearchButton();
    }
    @AfterMethod
    public void Reload(){
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }
}
