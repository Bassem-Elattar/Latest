package PortalPages.Reports.Booking.TotalDueToNDC;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.openqa.selenium.By.xpath;

public class TotalDueToNDCReport {

    public SHAFT.GUI.WebDriver driver;
    public SHAFT.TestData.JSON testData;
    private SoftAssert softAssert = new SoftAssert() ;


    public TotalDueToNDCReport(SHAFT.GUI.WebDriver driver){
        this.driver=driver;
        this.testData = new SHAFT.TestData.JSON("TotalDue.json");
    }

    private final By Dpick_fromDateSend = By.xpath("//input[@id='id-InvoiceFromDate']");
    private final By Dpick_toDateSend = By.xpath("//input[@id='id-InvoiceToDate']");
    private final By srNo = By.xpath("(//tbody/tr[1]/td[1])[1]");
    private final By Year = xpath("//button[normalize-space()='2026']");
    private final By Submit = xpath("//button[@type=\"submit\"]");

    private final By messageNoOutput =By.xpath("//td[@class='message']");
    private final By Btn_nextButton = By.xpath("//div/button[@class='next']");
    private final By Txt_invoiceNumber = By.xpath("//input[@id='id-InvoiceNumber']");
    private final By Txt_customerName = By.xpath("//input[@id='id-CustomerName']");
    private final By Txt_bookingReference = By.xpath("//input[@id='id-BookingReference']");
    private final By Btn_exportButton = By.xpath("//button[.//span[normalize-space()='Export To Excel']]");

    private final By tableRows = By.xpath("//tbody/tr");
    private final By agencyCurrency = By.xpath("(//span[@class='currency'])[1]");

    public TotalDueToNDCReport searchValidFromDate(String From, String year, String month) throws InterruptedException {

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

    public TotalDueToNDCReport searchValidToDate(String to, String year, String month) throws InterruptedException {

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

    public TotalDueToNDCReport Submit(){
        driver.element().click(Submit);
        return this;
    }

    private double parseAmount(String amount) {
        return Double.parseDouble(amount.replace(",", "").trim());
    }

    public TotalDueToNDCReport verifyThatResultsIsDisplayed() {

        List<WebElement> rows = driver.getDriver().findElements(tableRows);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String expectedCurrency = driver.element().getText(agencyCurrency).trim();
        LocalDate previousDate = null;
        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            softAssert.assertEquals(Integer.parseInt(cells.get(0).getText().trim()), i + 1, "Invalid Sr.No at row " + (i + 1));

            softAssert.assertEquals(cells.get(1).getText().trim(), "Flight", "Invalid Product Type at row " + (i + 1));

            LocalDate currentDate = LocalDate.parse(cells.get(2).getText().trim(), formatter);

            if (previousDate != null) {
                softAssert.assertTrue(!currentDate.isAfter(previousDate), "Invoice Date is not sorted Descending at row " + (i + 1));
            }
            previousDate = currentDate;

            softAssert.assertEquals(cells.get(6).getText().trim(), "Retail", "Invalid Customer Type at row " + (i + 1));

            double amount = parseAmount(cells.get(7).getText());
            softAssert.assertTrue(amount > 0, "Amount Payable To NDC should be greater than zero at row " + (i + 1));

            softAssert.assertEquals(cells.get(8).getText().trim(), expectedCurrency, "Currency mismatch at row " + (i + 1));
        }

        softAssert.assertAll();
        return this;
    }

    public TotalDueToNDCReport verifyThatNoOutputMessageIsDisplayedWhenThereIsNoOutput(){
        softAssert.assertEquals(driver.element().getText(messageNoOutput),"No data has been found!");
        softAssert.assertAll();
        return this;
    }
    public TotalDueToNDCReport clickOnNextButton() throws InterruptedException {
        driver.element().scrollToElement(Btn_nextButton);
        ((JavascriptExecutor) driver.getDriver()).executeScript(
                "document.querySelector('.zsiq_floatmain').remove();"
        );
        driver.element().click(Btn_nextButton);
        Thread.sleep(5000);
        return this;
    }
    public TotalDueToNDCReport verifyThatThePaginationIsWorkingCorrectly(){
        int actualValue = Integer.parseInt(driver.element().getText(srNo));
        softAssert.assertTrue(actualValue > 10, "Expected value to be greater than 10 but found: " + actualValue);
        softAssert.assertAll();
        return this;
    }
    public TotalDueToNDCReport setInvoiceNumber(){
        driver.element().type(Txt_invoiceNumber,testData.getTestData("validData.invoiceNumber"));
        return this;
    }
    public TotalDueToNDCReport setCustomerName() {
        driver.element().type(Txt_customerName,testData.getTestData("validData.customerName"));
        return this;
    }
    public TotalDueToNDCReport setBookingRefernce() {
        driver.element().type(Txt_bookingReference,testData.getTestData("validData.bookingReference"));
        return this;
    }
    public TotalDueToNDCReport VerifyThatTheExportButtonIsClickable() {
        boolean click = driver.element().isElementClickable(Btn_exportButton);
        softAssert.assertTrue(click,"ok");
        softAssert.assertAll();
        return this;
    }

}
