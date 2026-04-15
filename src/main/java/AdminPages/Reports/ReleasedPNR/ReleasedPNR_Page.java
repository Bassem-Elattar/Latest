package AdminPages.Reports.ReleasedPNR;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static org.openqa.selenium.By.xpath;

public class ReleasedPNR_Page {

                                                //Create object from needed classes
    private SHAFT.GUI.WebDriver driver;
    private SHAFT.TestData.JSON testData;


    //constructor

   public ReleasedPNR_Page (SHAFT.GUI.WebDriver driver){
       this.driver =driver;
       this.testData= new SHAFT.TestData.JSON("ReleasePNRReport.json");
   }

                                                 //Define locators of used elements

   private final By Btn_reportSelect = By.xpath("//a[@href='/reports']"); // Report module
   private final By Btn_SelectRealesedPNRReport = By.xpath("//a[@href='../Reports/releasedPNRReport']//div[@class='row']//div[@class='col-md-4 col Statement_report']"); //Release Report
   private final By InvoiceFromDate = By.id("id-Released.PNRFromDate"); // Select date from
   private final By InvoiceToDate = By.id("id-Released.PNRToDate"); // Select date to
   private final By Txt_OrderID = By.xpath("//input[@id='id-OrderID']"); // order id ( Optional )
   private final By Txt_BookingReference = By.xpath("//input[@id='id-FBBookingRef.No.']"); //Booking reference (Optional)
   private final By Btn_Search = By.className("p-button-label"); //search
   private final By Btn_ExportToExcel = By.xpath("//button[@class='p-element p-ripple p-button-outlined p-button p-component upper-table-btn']"); //Export To excel
   private final By BookingReference = By.xpath("//tbody/tr[1]/td[@class='ng-star-inserted'][3]"); //booking reference that assert on it
   private final By OrderID = By.xpath("(//tbody/tr[1]/td[@class='ng-star-inserted'][2])[1]"); //Order id that assert on it
   private final By NoDataFoundMessage = By.xpath("//td[@class='message']"); //no data found message
   private final By ErrorMessage = By.xpath("(//span[@class='fg-error has-error'][normalize-space()='Required'])[1]"); //Required message
   private final By Paginate = By.xpath("//div[@class='pager ng-star-inserted']//button[3]"); //pagination 3
   private final By OrderIDColum = By.xpath("//th[normalize-space()='Order ID']"); //pagination 3
   private final By ShowingMessage =By.xpath("//p[normalize-space()='Showing 20 results of 26 results']");
    By Year = xpath("//button[normalize-space()='2026']");
    By Submit = xpath("//button[@type=\"submit\"]");



    //Actions

    public void searchValidFromDate() throws InterruptedException {

        driver.element().click(InvoiceFromDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + testData.getTestData("ValidBranch.FromYear") + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + testData.getTestData("ValidBranch.FromMonth") + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", testData.getTestData("ValidBranch.InvoiceFromDate")));
        driver.element().click(Day);
    }

    public void searchValidToDate() throws InterruptedException {

        driver.element().click(InvoiceToDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + testData.getTestData("ValidBranch.ToYear") + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + testData.getTestData("ValidBranch.ToMonth") + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", testData.getTestData("ValidBranch.InvoiceToDate")));
        driver.element().click(Day);
    }

    public void Submit(){
        driver.element().click(Submit);
    }

    public void SendOrderID (){
        driver.element().type(Txt_OrderID,testData.getTestData("ValidBranch.orderId"));
    }

    public void SendBookingReference(){
        driver.element().type(Txt_BookingReference,testData.getTestData("ValidBranch.FBBookinQrefNo"));
    }

    public void ClickOnSearch (){
        driver.element().click(Btn_Search);
    }
    public void ClickOnExport (){
        driver.element().click(Btn_ExportToExcel);
    }

    public void AssertOnOrderID (){

        Assert.assertEquals(driver.element().getText(OrderID), testData.getTestData("validData.orderId"));
    }

    public void AssertOnBookingRef (){

        Assert.assertEquals(driver.element().getText(BookingReference), testData.getTestData("validData.FBBookinQrefNo"));
    }

    public void NoDateSelected (){

        Assert.assertEquals(driver.element().getText(ErrorMessage), "Required");
    }

    public void OrderIDColumExist (){
        Assert.assertEquals(driver.element().getText(OrderIDColum), testData.getTestData("validData.orderIdColum"));
    }

    public void NoDataMatched (){
        Assert.assertEquals(driver.element().getText(NoDataFoundMessage), testData.getTestData("validData.noDataMessage"));
    }

    public void SystemPaginateCorrectly (){
        driver.element().click(Paginate);
        Assert.assertEquals(driver.element().getText(ShowingMessage), testData.getTestData("validData.ShowingMessage"));
    }


























}
