package AdminPages.Reports.ImportPNRRepo;

import AdminPages.Reports.Booking.BookingReport;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.By.xpath;

public class ImportPNRReport_Page {
    public ImportPNRReport_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    SHAFT.GUI.WebDriver driver;

    By Branch = By.xpath("//p-multiselect[.//input[@id=\"id-BranchName\"]]");
    By Agency = By.xpath("//div[@class='p-multiselect-label p-placeholder']");
    By InvoiceFromDate = By.xpath("//input[@id='id-InvoiceFromDate']");
    By InvoiceToDate = By.xpath("//input[@id='id-InvoiceToDate']");
    By Submit = By.xpath("//button[@type=\"submit\"]");
    By Txt_DateError = By.xpath("//span[@class='fg-error has-error']");
    By Year = By.xpath("//button[normalize-space()='2026']");

    public void SearchValidBranch(String branch){
        driver.element().select(Branch,branch);
    }
    public void SearchValidAgency(String agency){
        driver.element().select(Agency,agency);
    }
    public void searchValidFromDate(String From, String year, String month) throws InterruptedException {

        driver.element().click(InvoiceFromDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = By.xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
    }

    public void searchValidToDate(String to, String year, String month) throws InterruptedException {

        driver.element().click(InvoiceToDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = By.xpath(String.format("(//span[text()='%s'])[1]", to));
        driver.element().click(Day);
    }

    public void Submit(){
        driver.element().click(Submit);
    }

    public String Table(int ColumnSearch, String ExpectedSearch) {
        WebElement table = driver.getDriver().findElement(By.xpath("//div[@class=\"table-area\"]")); // Adjust the XPath to match your table
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        String actualResult = ExpectedSearch;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > ColumnSearch) {
                String columnText = cells.get(ColumnSearch).getText();
                if (!columnText.equals(actualResult)) {
                    actualResult = "Test";
                    break;
                }
            }
        }
        return actualResult;
    }
}
