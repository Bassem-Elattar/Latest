package AdminPages.Reports.Quotation;


import AdminPages.Login.LogIn_Page;
import AdminPages.Login.TestBase;
import AdminPages.Reports.Reports_Common;
import Drive_Factory.CommonMethod;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.DataUtils;

public class QuotationTCs {
   private SHAFT.TestData.JSON testData;
    SHAFT.GUI.WebDriver driver;
   @BeforeTest
    public void login(){
       testData = new SHAFT.TestData.JSON("QuotationReport.json");
       CommonMethod.setupDriver(DataUtils.get("browser"));
       driver = CommonMethod.getDriver();
       driver.browser().navigateToURL(DataUtils.get("baseURL"));

       new LogIn_Page(driver).AdminLogin();
       new Reports_Common(driver).clickReports().clickQuotation();
   }


   @Test
    public void VerifyThatUserCanSearchWithMandatoryFieldOnly() throws InterruptedException {
       new Reports_Common(driver).clickReports().clickQuotation();
       new Quotation_Page(driver)
               .SelectBranch()
               .SelectDate_CreationDate()
               .SelectDuration_DateRange().searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
               .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
               .Submit()
               .VerifyThatTheExportButtonIsClickable();
   }

   @Test
   public void VerifyThatUserCanSearchWithBranchAndAgency() {
       new Reports_Common(driver).clickReports().clickQuotation();
       new Quotation_Page(driver)
              .SelectBranch()
              .SelectAgency()
              .SelectDate_CreationDate()
              .SelectDuration_Today()
              .Submit()
              .VerifyThatNoFoundDataShowsCorrectly();
   }

   @Test
   public void VerifyThatUserCanSearchWithBranchAndAgencyWithSpecificDate() throws InterruptedException {
      new Reports_Common(driver).clickReports().clickQuotation();
      new Quotation_Page(driver)
              .SelectBranch()
              .SelectAgency()
              .SelectDate_CreationDate()
              .SelectDuration_DateRange().searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
              .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
              .Submit()
              .VerifyThatTheExportButtonIsClickable();
   }

   @Test
   public void VerifyThatUserCanSearchWithBranchAndAgencyWithTravelDate() throws InterruptedException {
       new Reports_Common(driver).clickReports().clickQuotation();
      new Quotation_Page(driver)
              .SelectBranch()
              .SelectAgency()
              .SelectDate_TravelDate()
              .SelectDuration_DateRange().searchValidFromDate(testData.getTestData("TravelDate.FromDate"),testData.getTestData("TravelDate.FromYear"),testData.getTestData("TravelDate.FromMonth"))
              .searchValidToDate(testData.getTestData("TravelDate.ToDate"),testData.getTestData("TravelDate.ToYear"),testData.getTestData("TravelDate.ToMonth"))
              .Submit()
              .VerifyThatTheExportButtonIsClickable();
   }

   @Test
   public void VerifyThatUserCanSearchWithName() throws InterruptedException {
       new Reports_Common(driver).clickReports().clickQuotation();
      new Quotation_Page(driver)
              .SelectBranch()
              .SendName(testData.getTestData("validData.Name"))
              .SelectDate_CreationDate()
              .SelectDuration_DateRange().searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
              .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
              .Submit()
              .VerifyThatClientNameDisplaysCorrectly();
   }

   @Test
   public void VerifyThatUserCanSearchWithEmailID() throws InterruptedException {
       new Reports_Common(driver).clickReports().clickQuotation();
      new Quotation_Page(driver)
              .SelectBranch()
              .SendEmail(testData.getTestData("validData.EmailID"))
              .SelectDate_CreationDate()
              .SelectDuration_DateRange().searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
              .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
              .Submit()
              .VerifyThatEmailIDDisplaysCorrectly();
   }

   @Test
   public void VerifyThatUserCanSearchWithQuotesNo() throws InterruptedException {
       new Reports_Common(driver).clickReports().clickQuotation();
      new Quotation_Page(driver)
              .SelectBranch()
              .SelectDate_CreationDate()
              .SelectDuration_DateRange().searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
              .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
              .Submit()
              .VerifyThatQuotesNoDisplaysCorrectly();
   }

   @Test
   public void VerifyThatUserCanSearchWithTodayAndNoDataMessageShowsCorrectly(){
       new Reports_Common(driver).clickReports().clickQuotation();
      new Quotation_Page(driver)
              .SelectBranch()
              .SelectAgency()
              .SelectDate_CreationDate()
              .SelectDuration_Today()
              .Submit()
              .VerifyThatNoFoundDataShowsCorrectly();
   }

   @Test
   public void VerifyThatUserCanSearchWithAllFields() throws InterruptedException {
       new Reports_Common(driver).clickReports().clickQuotation();
      new Quotation_Page(driver)
              .SelectBranch()
              .SendName(testData.getTestData("validData.Name"))
              .SendEmail(testData.getTestData("validData.EmailID"))
              .SendQuoteNo(testData.getTestData("validData.SendQuotesNo"))
              .SelectQuoteStatus()
              .SelectDate_CreationDate()
              .SelectDuration_DateRange()
              .searchValidFromDate(testData.getTestData("validData.FromDate"),testData.getTestData("validData.FromYear"),testData.getTestData("validData.FromMonth"))
              .searchValidToDate(testData.getTestData("validData.ToDate"),testData.getTestData("validData.ToYear"),testData.getTestData("validData.ToMonth"))
              .Submit()
              .VerifyThatClientNameDisplaysCorrectly();
   }
    @AfterMethod
    public void Reload(){
        new LogIn_Page(driver).ClickOnLogOuTButton();
    }

}
