package PortalPages.Reports.Booking.Sales;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.openqa.selenium.By.xpath;

public class SalesReport {
    public SalesReport(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    SHAFT.GUI.WebDriver driver ;
    private SoftAssert softAssert = new SoftAssert();


    By Txt_InvoiceNumber = By.xpath("//input[@placeholder=\"Invoice number\"]");
    By Txt_CustomerName = By.xpath("//input[@id=\"id-CustomerName\"]");
    By Dpick_PaymentDate = By.xpath("//input[@id='id-PaymentDate']");
    By Txt_TransactionID = By.xpath("//input[@id=\"id-TransactionID\"]");
    By Btn_Search = By.xpath("//button[@type=\"submit\"]");
    By InvoiceFromDate = xpath("//input[@id='id-InvoiceFromDate']");
    By InvoiceToDate = xpath("//input[@id='id-InvoiceToDate']");
    By Year = xpath("//button[normalize-space()='2026']");
    By AgentName = By.xpath("//p-dropdown[.//input[@id='id-AgentName']]");
    By srNo = By.xpath("(//tbody/tr[1]/td[1])[1]");
    By messageNoOutput = By.xpath("//td[@class='message']");
    By Btn_nextButton = By.xpath("//div/button[@class='next']");
    By Btn_exportButton = By.xpath("//button[contains(@class,'upper-table-btn')]");
    By rows = By.xpath("//tbody/tr");
    private static final String AGENT_OPTION_XPATH =
            "//li[@role='option']//span[normalize-space()='%s']";

    private final By reportsMenu =
            By.xpath("//a[.//span[normalize-space()='Reports']]");

    private final By salesButton =
            By.xpath("//ndc-card[.//h3[normalize-space()='Sales']]//button");

    public SalesReport openSalesReport() {
        driver.element().click(reportsMenu);
        driver.element().click(salesButton);
        return this;
    }


    public SalesReport setDate(String From, String year, String month){
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

    public SalesReport setEndDate(String to, String year, String month){
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

    public SalesReport setPaymentDate(String date, String year, String month){
        driver.element().click(Dpick_PaymentDate);
        driver.element().click(Year);
        By year1 = By.xpath("//span[normalize-space()='" + year + "']");
        driver.element().click(year1);
        By month1 = By.xpath("//span[normalize-space()='" + month + "']");
        driver.element().click(month1);
        By Day = By.xpath(String.format("//td[not(contains(@class,'p-disabled'))]//span[normalize-space()='%s']", date));
        driver.element().click(Day);
        return this;
    }

    public SalesReport setInvoiceNumber(String invoiceNumber) {
        driver.element().type(Txt_InvoiceNumber, invoiceNumber);
        return this;
    }

    public SalesReport setCustomerName(String customerName){
        driver.element().type(Txt_CustomerName,customerName);
        return this;
    }

    public SalesReport setAgentName(String agentName) {
        driver.element().click(AgentName);
        By option = By.xpath(String.format(AGENT_OPTION_XPATH, agentName));
        driver.element().click(option);
        return this;
    }

    public SalesReport setTransactionID(String transactionID){
        driver.element().type(Txt_TransactionID,transactionID);
        return this;
    }

    public SalesReport setSearch()
    {
        driver.element().click(Btn_Search);
        return this;
    }

    private double parseAmount(String amount) {
        return Double.parseDouble(amount.replace(",", "").trim());
    }

    public SalesReport verifyThatResultsIsDisplayed() {

        List<WebElement> tableRows = driver.getDriver().findElements(rows);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate previousDate = null;
        for (int i = 0; i < tableRows.size(); i++) {
            List<WebElement> cells = tableRows.get(i).findElements(By.tagName("td"));
            int srNo = Integer.parseInt(cells.get(0).getText().trim());
            softAssert.assertEquals(srNo, i + 1, "Wrong Sr.No at row " + (i + 1));

            LocalDate currentDate = LocalDate.parse(cells.get(2).getText().trim(), formatter);
            if (previousDate != null) {
                softAssert.assertTrue(!currentDate.isAfter(previousDate), "Payment Date isn't sorted Descending at row " + (i + 1));
            }

            previousDate = currentDate;
            double payable = parseAmount(cells.get(7).getText());
            double markup = parseAmount(cells.get(8).getText());
            double sales = parseAmount(cells.get(9).getText());
            softAssert.assertEquals(sales, payable + markup, 0.01, "Sales Amount mismatch at row " + (i + 1));
        }
        softAssert.assertAll();
        return this;
    }

    public SalesReport verifyThatNoOutputMessageIsDisplayedWhenThereIsNoOutput() {
        softAssert.assertEquals(driver.element().getText(messageNoOutput), "No data has been found!");
        softAssert.assertAll();
        return this;
    }

    public SalesReport clickOnNextButton() throws InterruptedException {
        driver.element().click(Btn_nextButton);
        Thread.sleep(5000);
        return this;
    }

    public SalesReport verifyThatThePaginationIsWorkingCorrectly() {
        int actualValue = Integer.parseInt(driver.element().getText(srNo));
        softAssert.assertTrue(actualValue > 10,
                "Expected value to be greater than 10 but found: " + actualValue);
        softAssert.assertAll();
        return this;
    }

    public SalesReport verifyThatTheExportButtonIsClickable() {
        boolean click = driver.element().isElementClickable(Btn_exportButton);
        softAssert.assertTrue(click, "Export button should be clickable");
        softAssert.assertAll();
        return this;
    }

    public SalesReport clickSearch() {
        driver.element().click(Btn_Search);
        return this;
    }



}
