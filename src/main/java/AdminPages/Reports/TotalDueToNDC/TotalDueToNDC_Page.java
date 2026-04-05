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
       // this.testData = new SHAFT.TestData.JSON("testDataFiles/TotalDue.json");
    }
    //public locators//
    private final By Btn_reportSelect = By.xpath("//a[@href='/reports']");
    private final  By Btn_totalDueSelect = By.xpath("//a[@href='../Reports/totalDueToOdeysys?menu=reports']");
    private final By Lst_branchClick = By.xpath("//ndc-fg-input[1]/ndc-fg-dropdown-input/p-multiselect[@selecteditemslabel='All ({0} selected)']");
    private final By Cbox_branchSelect = By.xpath("//div[@role='checkbox']");
    private final By Lst_agencyClick = By.xpath("//ndc-fg-input[2]/ndc-fg-dropdown-input/p-multiselect[@selecteditemslabel='All ({0} selected)']");
    private final  By Lst_agencySelect = By.xpath("//ndc-fg-input/ndc-fg-dropdown-input[@class='ng-star-inserted']/p-multiselect[@selecteditemslabel='All ({0} selected)']//li[@aria-label='All']//div[@class='p-checkbox-box']");
   // private final  By Dpick_fromDateClick = By.xpath("//span[@class='ng-tns-c47-3 p-calendar p-calendar-w-btn']/button");
    private final  By Dpick_fromDateSend = By.xpath("//input[@id='id-InvoiceFromDate']");
    private final  By Dpick_fromDateSelect = By.xpath("//table[@class='p-datepicker-calendar ng-tns-c47-3']/tbody/tr[1]/td[@class='ng-tns-c47-3 ng-star-inserted'][1]/span");
  //  private final  By toDateClick = By.xpath("//span[@class='ng-tns-c47-4 p-calendar p-calendar-w-btn']/button");
    private final  By Dpick_toDateSelect = By.xpath("//table[@class='p-datepicker-calendar ng-tns-c47-4']/tbody/tr[5]/td[@class='ng-tns-c47-4 ng-star-inserted'][2]/span");
    private final  By Dpick_toDateSend = By.xpath("//input[@id='id-InvoiceToDate']");
    private final  By Btn_searchInGrid = By.xpath("//span[@class='p-button-label']");
    private final  By srNo = By.xpath("//*[@id=\"pr_id_6-table\"]/tbody/tr[1]/td[1]");
    By Year = xpath("//button[normalize-space()='2026']");
    By Submit = xpath("//button[@type=\"submit\"]");

    //locators that return no output//
    private final By Dpick_fromDateNoOutput =By.xpath("//table[@class='p-datepicker-calendar ng-tns-c47-3']/tbody/tr[3]/td[@class='ng-tns-c47-3 ng-star-inserted'][4]/span");
    private final By Dpick_toDateNoOutput =By.xpath("//table[@class='p-datepicker-calendar ng-tns-c47-4']/tbody/tr[3]/td[@class='ng-tns-c47-4 ng-star-inserted'][4]/span");
    private final By messageNoOutput =By.xpath("//td[@class='message']");

    //locators for pagination
    private final By Btn_nextButton = By.xpath("//div/button[@class='next']");

    //locators unmandatory field (text fields)
    private final By Txt_invoiceNumber = By.xpath("//input[@id='id-InvoiceNumber']");
    private final By Txt_customerName = By.xpath("//input[@id='id-CustomerName']");
    private final By Txt_bookingReference = By.xpath("//input[@id='id-BookingReference']");

    //export file
    private final By Btn_exportButton = By.xpath("//button[@class='p-element p-ripple p-button-outlined p-button p-component upper-table-btn ng-star-inserted']");
    public TotalDueToNDC_Page selectTotalDueReport(){
        driver.element().click(Btn_reportSelect);
        driver.element().click(Btn_totalDueSelect);
        return this;
    }
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

    public TotalDueToNDC_Page selectBranch(){
        driver.element().click(Lst_branchClick);
        driver.element().click(Cbox_branchSelect);
        return this;
    }
    public TotalDueToNDC_Page selectAgency(){
        driver.element().click(Lst_agencyClick);
        driver.element().click(Lst_agencySelect);
        return this;
    }

    public TotalDueToNDC_Page verifyThatResultsIsDisplayed(){
        softAssert.assertEquals (driver.element().getText(srNo),"1");
        softAssert.assertAll();
        return this;
    }
    public TotalDueToNDC_Page selectSameDateWithNoOutput(){
        driver.element().type(Dpick_fromDateSend, testData.getTestData("validDataNoOutput.fromDate"));
        driver.element().click(Dpick_fromDateNoOutput);
        driver.element().type(Dpick_toDateSend, testData.getTestData("validDataNoOutput.toDate"));
        driver.element().click(Dpick_toDateNoOutput);
        return this;
    }
    public TotalDueToNDC_Page verifyThatNoOutputMessageIsDisplayedWhenThereIsNoOutput(){
        softAssert.assertEquals(driver.element().getText(messageNoOutput),"No data has been found!");
        softAssert.assertAll();
        return this;
    }
    public TotalDueToNDC_Page clickOnNextButton() throws InterruptedException {
        driver.element().click(Btn_nextButton);
        Thread.sleep(3000);
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
