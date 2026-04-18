package AdminPages.Reports.TotalDueToNDC;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import static org.openqa.selenium.By.xpath;
//import utilities.DataUtils;


public class TotalDueToNDC_Page {
    private SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;
    private SoftAssert softAssert = new SoftAssert() ;

    public TotalDueToNDC_Page(SHAFT.GUI.WebDriver driver){
        this.driver=driver;
        this.testData = new SHAFT.TestData.JSON("TotalDue.json");
    }

    //public locators//

    By Lst_BranchName = By.xpath("//p-multiselect[.//input[@name=\"Branch Name\"]]");
    By Lst_AgencyName = By.xpath("//p-multiselect[.//input[@id=\"id-AgencyName\"]]");
    private final  By Dpick_fromDateSend = By.xpath("//input[@id='id-InvoiceFromDate']");
    private final  By Dpick_toDateSend = By.xpath("//input[@id='id-InvoiceToDate']");
    private final  By srNo = By.xpath("//*[@id=\"pr_id_6-table\"]/tbody/tr[1]/td[1]");
    By Year = xpath("//button[normalize-space()='2026']");
    By Submit = xpath("//button[@type=\"submit\"]");

    //locators that return no output//
    private final By messageNoOutput =By.xpath("//td[@class='message']");

    //locators for pagination
    private final By Btn_nextButton = By.xpath("//div/button[@class='next']");

    //locators unmandatory field (text fields)
    private final By Txt_invoiceNumber = By.xpath("//input[@id='id-InvoiceNumber']");
    private final By Txt_customerName = By.xpath("//input[@id='id-CustomerName']");
    private final By Txt_bookingReference = By.xpath("//input[@id='id-BookingReference']");

    //export file
    private final By Btn_exportButton = By.xpath("//button[@class='p-element p-ripple p-button-outlined p-button p-component upper-table-btn ng-star-inserted']");

    public TotalDueToNDC_Page searchValidFromDate(String From, String year, String month) throws InterruptedException {

        driver.element().click(Dpick_fromDateSend);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
        return this;
    }

    public TotalDueToNDC_Page searchValidToDate(String to, String year, String month) throws InterruptedException {

        driver.element().click(Dpick_toDateSend);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", to));
        driver.element().click(Day);
        return this;
    }

    public TotalDueToNDC_Page Submit(){
        driver.element().click(Submit);
        return this;
    }

    public TotalDueToNDC_Page setBranchName(String branch){
        driver.element().select(Lst_BranchName,branch);
        return this;
    }

    public TotalDueToNDC_Page setAgencyName(String agency){
        driver.element().select(Lst_AgencyName, agency);
        return this;
    }

    public TotalDueToNDC_Page verifyThatResultsIsDisplayed(){
        softAssert.assertEquals (driver.element().getText(srNo),"1");
        softAssert.assertAll();
        return this;
    }

    public TotalDueToNDC_Page verifyThatNoOutputMessageIsDisplayedWhenThereIsNoOutput(){
        softAssert.assertEquals(driver.element().getText(messageNoOutput),"No data has been found!");
        softAssert.assertAll();
        return this;
    }
    public TotalDueToNDC_Page clickOnNextButton() throws InterruptedException {
        driver.element().click(Btn_nextButton);
        Thread.sleep(5000);
        return this;
    }
    public TotalDueToNDC_Page verifyThatThePaginationIsWorkingCorrectly(){
        int actualValue = Integer.parseInt(driver.element().getText(srNo));
        softAssert.assertTrue(actualValue > 10, "Expected value to be greater than 10 but found: " + actualValue);
        softAssert.assertAll();
        return this;
    }
    public TotalDueToNDC_Page sendInvoiceNumber(){
        driver.element().type(Txt_invoiceNumber,testData.getTestData("validData.invoiceNumber"));
        return this;
    }
    public TotalDueToNDC_Page sendCustomerName() {
        driver.element().type(Txt_customerName,testData.getTestData("validData.customerName"));
        return this;
    }
    public TotalDueToNDC_Page sendBookingRefernce() {
        driver.element().type(Txt_bookingReference,testData.getTestData("validData.bookingReference"));
        return this;
    }
    public TotalDueToNDC_Page VerifyThatTheExportButtonIsClickable() {
        boolean click = driver.element().isElementClickable(Btn_exportButton);
        softAssert.assertTrue(click,"ok");
        softAssert.assertAll();
        return this;
    }
}
