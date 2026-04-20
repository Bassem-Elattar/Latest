package AdminPages.Reports.Booking;

import com.shaft.driver.SHAFT;
import com.shaft.gui.element.ElementActions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.openqa.selenium.By.xpath;

public class BookingReport {
    private final SoftAssert softAssert;
    public BookingReport(SHAFT.GUI.WebDriver driver)
    {
        this.softAssert = new SoftAssert();
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver;

    ////////////////////// LOCATORS //////////////////////
    public By Reports=By.xpath("//a[@href=\"/reports\"]");
    By Branch = xpath("//p-multiselect[.//input[@id=\"id-BranchName\"]]");
    By InvoiceFromDate = xpath("//input[@id='id-FromBookingDate']");
    By InvoiceToDate = xpath("//input[@id='id-ToBookingDate']");
    By Submit = xpath("//button[@type=\"submit\"]");
    By Email = xpath("//input[@id='id-Email']");
    By Year = xpath("//button[normalize-space()='2026']");
    public By ClientName=By.xpath("//input[@id=\"id-ClientName\"]");
    public By PhoneNumber=By.xpath("//input[@id='Phone Number']");
    public By ShowAdvanceSearch=By.xpath("//span[normalize-space()='Show Advance Search']");
    public By InvoiceNumber=By.xpath("//input[@id='id-InvoiceNo']");
    public By BookingReference=By.xpath("//input[@id='id-BookingReference']");
    public By AirlinePNR=By.xpath("//input[@id='id-AirlinePNR']");
    public By TicketNumber=By.xpath("//input[@id=\"id-Ticketnumber\"]");
    public By TicketStatus=By.xpath("//span[@class='p-dropdown-label p-inputtext p-placeholder ng-star-inserted']");
    public By Search=By.xpath("//button[@type=\"submit\"]");
    public By DataReturn=By.xpath("(//tr[@class=\"ng-star-inserted\"])[2]");
    public By Locator = By.xpath("//div[@class=\"w-f h-3rem\"]");

    public BookingReport SearchValidBranch(String branch){
        driver.element().select(Branch,branch);
        return this;
    }

    public void searchValidFromDate(String From, String year, String month) throws InterruptedException {

        driver.element().click(InvoiceFromDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", From));
        driver.element().click(Day);
    }

    public void searchValidToDate(String to, String year, String month) throws InterruptedException {

        driver.element().click(InvoiceToDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = xpath(String.format("(//span[text()='%s'])[1]", to));
        driver.element().click(Day);
    }

    public void Submit(){
        driver.element().click(Submit);
    }

    public BookingReport FillMail(String Mail) {
        driver.element().type(Email,Mail);
        return this;
    }
    public BookingReport FromDate(String Date) {
        driver.element().type(InvoiceFromDate,Date);
        return this;
    }
    public BookingReport ToDate(String Date ) {
        driver.element().type(InvoiceToDate,Date);
        return this;
    }
    public BookingReport FillClientName(String S) {
        driver.element().type(ClientName,S);
        return this;
    }
    public BookingReport FillPhoneNumber(String S) {
        driver.element().type(PhoneNumber,S);
        return this;
    }
    public BookingReport ShowAdvance()
    {
        driver.element().click(ShowAdvanceSearch);
        return this;
    }

    public BookingReport FillInvoiceNumber(String S) {
        driver.element().type(InvoiceNumber,S);
        return this;
    }

    public BookingReport FillBookingReference(String S) {
        driver.element().type(BookingReference,S);
        return this;
    }

    public BookingReport FillAirlinePNR(String S) {
        driver.element().type(AirlinePNR,S);
        return this;
    }

    public BookingReport FillTicketNumber(String S) {
        driver.element().type(TicketNumber,S);
        return this;
    }

    public BookingReport ChooseBookingStatus(String Check){

        driver.element().click(TicketStatus);
        final By SelectIndex = By.xpath("//p-dropdown//p-overlay//div//div//ul//p-dropdownitem["+ Check+"]");
        driver.element().click(SelectIndex);
        return this;
    }

    public BookingReport ClickSearch()
    {
        driver.element().click(Search);
        return this;
    }

    public void performAssertions () {
        String expectedHeaderStatus = "Branch Name";
        String[] allowedStatusValues = {"Test"};
        try {
            // Wait for the table to be present and the text to be loaded
            Thread.sleep(1000);

            // Find and verify the table headers
            String actualHeaderStatus = driver.element().getText(By.xpath("//table/thead/tr/th[1]"));
            Assert.assertEquals(actualHeaderStatus, expectedHeaderStatus, "The 'Status' table header does not match the expected value.");


            // Get the number of rows in the table body
            List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
            int numberOfRows = rows.size();

            // Iterate through each row and verify the data in the relevant columns
            for (int i = 1; i <= numberOfRows; i++) {
                String actualDataStatus = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[1]"));

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