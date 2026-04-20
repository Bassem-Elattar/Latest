package AdminPages.Reports.SyncPNR;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import static org.openqa.selenium.By.xpath;

public class SyncPNR_Page {
    private SHAFT.GUI.WebDriver driver;
    private SoftAssert softAssert = new SoftAssert();
    private SHAFT.TestData.JSON testData;

    public SyncPNR_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
        this.testData = new SHAFT.TestData.JSON("Sync.json");
    }

    private final By InvoiceFromDate = By.xpath("//input[@id='id-Sync.PNRFromDate']");
    private final By InvoiceToDate = By.xpath("//input[@id='id-Sync.PNRToDate']");
    private final By Txt_orderIdSelect = By.xpath("//input[@id='id-OrderID']");
    private final By Txt_FBBookinQrefNo = By.xpath("//input[@id='id-FBbookingrefno.']");
    private final By Btn_submitClick = By.xpath("//button[@type='submit']");
    private final By message = By.xpath("//td[@class='message']");
    private final By orderId = By.xpath("//tbody/tr[1]/td[1]");
    private final By bookingId = By.xpath("//tbody/tr[1]/td[2]");
    private final By paginate = By.xpath("//div[@class='pager ng-star-inserted']/button[3]");
    private final By showingMessage = By.xpath("//div[@class='results ng-star-inserted']/p");
    private final By Btn_exportExcel = By.xpath("//div[@class='col-6']/button[@type='button']");
    private final By errorMessage = By.xpath("(//span[normalize-space()='Required'])[1]");
    By Year = By.xpath("//button[normalize-space()='2026']");


    public SyncPNR_Page searchValidFromDate(String From, String year, String month) throws InterruptedException {

        driver.element().click(InvoiceFromDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
        return this;
    }

    public SyncPNR_Page searchValidToDate(String to, String year, String month) throws InterruptedException {

        driver.element().click(InvoiceToDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", to));
        driver.element().click(Day);
        return this;
    }


    public SyncPNR_Page VerifyNoFoundMessageIsDisplaying() {
        softAssert.assertEquals(driver.element().getText(message), testData.getTestData("validData.noDataMessage"));
        softAssert.assertAll();
        return this;
    }

    public SyncPNR_Page sendOrderID() {
        driver.element().type(Txt_orderIdSelect, testData.getTestData("validData.orderId"));
        return this;
    }

    public SyncPNR_Page VerifyThatOrderIdDisplaysCorrectly() {
        softAssert.assertEquals(driver.element().getText(orderId), testData.getTestData("validData.orderId"));
        softAssert.assertAll();
        return this;
    }

    public SyncPNR_Page sendFBBookingQRrefNo() {
        driver.element().type(Txt_FBBookinQrefNo, testData.getTestData("validData.FBBookinQrefNo"));
        return this;
    }

    public SyncPNR_Page VerifyThatBookingIdDisplaysCorrectly() {
        softAssert.assertEquals(driver.element().getText(bookingId), testData.getTestData("validData.FBBookinQrefNo"));
        softAssert.assertAll();
        return this;
    }

    public SyncPNR_Page clickthePaginateButton() throws InterruptedException {
        driver.element().click(paginate);
        Thread.sleep(1000);
        return this;
    }

    public SyncPNR_Page VerifyThatsystemPaginateCorrectly() {
        softAssert.assertEquals(driver.element().getText(showingMessage), "Showing 20 results of 80 results");
        softAssert.assertAll();
        return this;
    }

    public SyncPNR_Page verifyThatTheExportToExcelIsClickable(){
    boolean click = driver.element().isElementClickable(Btn_exportExcel);
        softAssert.assertTrue(click,"ok");
        softAssert.assertAll();
        return this;
}

    public SyncPNR_Page clickSubmit() throws InterruptedException {
        driver.element().click(Btn_submitClick);
        Thread.sleep(1000);
        return this;
    }

    public SyncPNR_Page VerifyThatSystemdShowErrorMessage() {
        softAssert.assertEquals(driver.element().getText(errorMessage), testData.getTestData("validData.errorMessage"));
        softAssert.assertAll();
        return this;
    }

}
