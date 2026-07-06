package PortalPages.Reports.Booking.Sales;

import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import PortalPages.SideMenu;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import utilities.DataUtils;

public class SalesReport_TC {

    private SHAFT.TestData.JSON testData;
    public SHAFT.GUI.WebDriver driver;
    SalesReport sales;
    SideMenu sideMenu;

    @BeforeClass
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
        new Login_Page(driver).PortalLogin();
        testData = new SHAFT.TestData.JSON("SearchSalesReport.json");
        sideMenu = new SideMenu(driver);
        sales = new SalesReport(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
    }

    @Test
    public void validSearchForSalesReport() throws InterruptedException {
        sideMenu.openReports().openSalesReport();
        sales
                .setDate(testData.getTestData("[0].Date"), testData.getTestData("[0].FromYear"), testData.getTestData("[0].FromMonth"))
                .setEndDate(testData.getTestData("[0].EndDate"), testData.getTestData("[0].ToYear"), testData.getTestData("[0].ToMonth"))
                .clickSearch()
                .verifyThatResultsIsDisplayed();
    }

    @Test
    public void validateThatUserCanSearchWithAllFields() throws InterruptedException {
        sideMenu.openReports().openSalesReport();
        sales
                .setDate(testData.getTestData("[0].Date"), testData.getTestData("[0].FromYear"), testData.getTestData("[0].FromMonth"))
                .setEndDate(testData.getTestData("[0].EndDate"), testData.getTestData("[0].ToYear"), testData.getTestData("[0].ToMonth"))
                .setPaymentDate(testData.getTestData("[0].PaymentDate"), testData.getTestData("[0].FromYear"), testData.getTestData("[0].FromMonth"))
                .setInvoiceNumber(testData.getTestData("[0].InvoiceNumber"))
                .setCustomerName(testData.getTestData("[0].CustomerName"))
                .setTransactionID(testData.getTestData("[0].TransactionID"))
                .setAgentName(testData.getTestData("[0].PortalAgentName"))
                .clickSearch()
                .verifyThatResultsIsDisplayed();
    }

    @Test
    public void searchWithNoOutput() throws InterruptedException {
        sideMenu.openReports().openSalesReport();
        sales
                .setDate(testData.getTestData("[1].Date"), testData.getTestData("[1].FromYear"), testData.getTestData("[1].FromMonth"))
                .setEndDate(testData.getTestData("[1].EndDate"), testData.getTestData("[1].ToYear"), testData.getTestData("[1].ToMonth"))
                .clickSearch()
                .verifyThatNoOutputMessageIsDisplayedWhenThereIsNoOutput();
    }

    @Test
    public void validateThatUserCanExportTheFile() throws InterruptedException {
        sideMenu.openReports().openSalesReport();
        sales
                .setDate(testData.getTestData("[0].Date"), testData.getTestData("[0].FromYear"), testData.getTestData("[0].FromMonth"))
                .setEndDate(testData.getTestData("[0].EndDate"), testData.getTestData("[0].ToYear"), testData.getTestData("[0].ToMonth"))
                .clickSearch()
                .verifyThatTheExportButtonIsClickable();
    }

    @Test
    public void validatePagination() throws InterruptedException {
        sideMenu.openReports().openSalesReport();
        sales
                .setDate(testData.getTestData("[0].Date"), testData.getTestData("[0].FromYear"), testData.getTestData("[0].FromMonth"))
                .setEndDate(testData.getTestData("[0].EndDate"), testData.getTestData("[0].ToYear"), testData.getTestData("[0].ToMonth"))
                .clickSearch()
                .clickOnNextButton()
                .verifyThatThePaginationIsWorkingCorrectly();
    }
}