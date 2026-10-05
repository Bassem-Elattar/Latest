package AdminPages.Reports.LedgerReport;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class LedgerReportDetails_Page {

    public LedgerReportDetails_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;


    By Btn_Report = By.xpath("//a[@href=\"/reports\"]");
    By Btn_LedgerReport = By.xpath("//a[@href=\"../Reports/ledgerDetailsReport\"]");
    By Lst_BranchName = By.xpath("//p-dropdown[.//input[@id=\"id-BranchName\"]]");
    By Lst_AgencyName = By.xpath("//p-dropdown[.//input[@id=\"id-AgencyName\"]]");
    By Dpick_InvoiceFromDate = By.xpath("//input[@id=\"id-InvoiceFromDate\"]");
    By Dpick_InvoiceToDate = By.xpath("//input[@id=\"id-InvoiceToDate\"]");
    By Btn_SearchGrid = By.xpath("//button[@type=\"submit\"]");
    By Btn_ExportExcel = By.linkText("Export To Excel");


    public void Btn_Report(){
        driver.element().click(Btn_Report);
    }

    public void Btn_LedgerReport(){
        driver.element().click(Btn_LedgerReport);
    }

    public void Lst_BranchName(String Select){
        driver.element().select(Lst_BranchName,Select);
    }

    public void Lst_AgencyName(String Select){
        driver.element().select(Lst_AgencyName,Select);
    }

    public void Dpick_InvoiceFromDate(String Select){
        driver.element().select(Dpick_InvoiceFromDate,Select);
    }

    public void Dpick_InvoiceToDate(String Select){
        driver.element().select(Dpick_InvoiceToDate,Select);
    }

    public void Btn_SearchGrid(){
        driver.element().click(Btn_SearchGrid);
    }

    public void Btn_ExportExcel(){
        driver.element().click(Btn_ExportExcel);
    }


    public void performAssertions () {
        String expectedHeaderStatus = "Organization Name";
        String[] allowedStatusValues = {"Test"};


        try {
            // Wait for the table to be present and the text to be loaded
            Thread.sleep(1000);

            // Find and verify the table headers
            String actualHeaderStatus = driver.element().getText(By.xpath("//table/thead/tr/th[2]"));
            Assert.assertEquals(actualHeaderStatus, expectedHeaderStatus, "The 'Status' table header does not match the expected value.");



            // Get the number of rows in the table body
            List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
            int numberOfRows = rows.size();

            // Iterate through each row and verify the data in the relevant columns
            for (int i = 1; i <= numberOfRows; i++) {
                String actualDataStatus = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[2]"));


                Assert.assertTrue(isValueInArray(actualDataStatus, allowedStatusValues),
                        "Row " + i + ": The 'Status' column data (" + actualDataStatus + ") does not match any of the allowed values.");


            }

        } catch (Exception e) {
            // Print the stack trace to help with debugging if an exception occurs
            e.printStackTrace();
            Assert.fail("An exception occurred while trying to verify the table header or data: " + e.getMessage());
        }
    }


    private boolean isValueInArray (String value, String[]array){
        for (String element : array) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
