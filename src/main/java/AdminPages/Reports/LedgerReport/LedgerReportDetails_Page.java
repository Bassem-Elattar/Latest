package AdminPages.Reports.LedgerReport;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
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

    public void Lst_BranchName(String branch){
        driver.element().click(Lst_BranchName);
        driver.element().type(By.xpath("//input[contains(@class,'p-dropdown-filter')]"), branch);
        driver.element().click(By.xpath("(//li[contains(@aria-label,'" + branch + "')])[1]"));
    }

    public void Lst_AgencyName(String Select){
        driver.element().select(Lst_AgencyName,Select);
    }

    public void Dpick_InvoiceFromDate(String day){
        driver.element().click(Dpick_InvoiceFromDate);
        driver.element().click(By.xpath("(//span[text()='" + day + "'])[1]"));
    }

    public void Dpick_InvoiceToDate(String day){
        driver.element().click(Dpick_InvoiceToDate);
        driver.element().click(By.xpath("(//span[text()='" + day + "'])[1]"));
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


    public void assertBookingRefundWithRetry(String bookingReference, BigDecimal expectedDebitAmount, int maxAttempts, long retryDelayMillis) throws InterruptedException {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                assertBookingRefund(bookingReference, expectedDebitAmount);
                return;
            } catch (AssertionError | IllegalStateException e) {
                if (attempt == maxAttempts) {
                    throw e;
                }
                // The refund may not have posted to the ledger yet; wait and re-run the search.
                Thread.sleep(retryDelayMillis);
                Btn_SearchGrid();
                // Wait for the grid to actually finish re-rendering before the next scan reads it,
                // otherwise the scan can read a stale or still-empty table and miss the real entry.
                new WebDriverWait(driver.getDriver(), Duration.ofSeconds(15))
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table/thead/tr/th")));
            }
        }
    }

    public void assertBookingRefund(String bookingReference, BigDecimal expectedDebitAmount) throws InterruptedException {
        AdminPages.Helper.PaginationHelper paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
        int totalPages = paginationHelper.getTotalPages();

        int bookingReferenceColumn = findColumnIndex("Booking Reference");
        int debitColumn = findColumnIndex("Debit");
        int runningBalanceColumn = findColumnIndex("Running Balance");

        BigDecimal previousPageLastRunningBalance = null;

        for (int page = 1; page <= totalPages; page++) {
            List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
            for (int i = 1; i <= rows.size(); i++) {
                String cellValue = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[" + bookingReferenceColumn + "]"));
                if (!cellValue.trim().equals(bookingReference)) {
                    continue;
                }

                BigDecimal debitAmount = normalizeAmount(driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[" + debitColumn + "]")));
                if (debitAmount.setScale(2, RoundingMode.HALF_UP).compareTo(expectedDebitAmount.setScale(2, RoundingMode.HALF_UP)) != 0) {
                    // The same booking reference gets one ledger row per passenger, and only one of
                    // those rows carries the refunded amount as its debit; keep searching until it's found.
                    continue;
                }

                BigDecimal currentRunningBalance = normalizeAmount(driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[" + runningBalanceColumn + "]")));
                BigDecimal previousRunningBalance;
                if (i > 1) {
                    previousRunningBalance = normalizeAmount(driver.element().getText(By.xpath("//table/tbody/tr[" + (i - 1) + "]/td[" + runningBalanceColumn + "]")));
                } else {
                    Assert.assertNotNull(previousPageLastRunningBalance,
                            "Booking reference " + bookingReference + " debit row is the very first row of the AR Ledger report; there is no previous row to check the running balance against");
                    previousRunningBalance = previousPageLastRunningBalance;
                }

                BigDecimal runningBalanceDelta = currentRunningBalance.subtract(previousRunningBalance).abs();
                Assert.assertEquals(runningBalanceDelta.setScale(2, RoundingMode.HALF_UP), debitAmount.setScale(2, RoundingMode.HALF_UP),
                        "AR Ledger running balance was not updated by the debit amount for booking " + bookingReference);
                return;
            }

            if (page < totalPages) {
                if (!rows.isEmpty()) {
                    previousPageLastRunningBalance = normalizeAmount(driver.element().getText(
                            By.xpath("//table/tbody/tr[" + rows.size() + "]/td[" + runningBalanceColumn + "]")));
                }
                paginationHelper.navigateToNextPage();
            }
        }

        Assert.fail("Booking reference " + bookingReference + " with a Debit entry of " + expectedDebitAmount.setScale(2, RoundingMode.HALF_UP)
                + " was not found on any page of the AR Ledger report");
    }

    // For a rejected refund request, no debit should ever have been posted -- every ledger row for
    // this booking reference (one per passenger, same as assertBookingRefund) must still show 0.00.
    public void assertBookingRejectedWithRetry(String bookingReference, int maxAttempts, long retryDelayMillis) throws InterruptedException {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                assertBookingRejected(bookingReference);
                return;
            } catch (AssertionError | IllegalStateException e) {
                if (attempt == maxAttempts) {
                    throw e;
                }
                Thread.sleep(retryDelayMillis);
                Btn_SearchGrid();
                new WebDriverWait(driver.getDriver(), Duration.ofSeconds(15))
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table/thead/tr/th")));
            }
        }
    }

    public void assertBookingRejected(String bookingReference) throws InterruptedException {
        AdminPages.Helper.PaginationHelper paginationHelper = new AdminPages.Helper.PaginationHelper(driver);
        int totalPages = paginationHelper.getTotalPages();

        int bookingReferenceColumn = findColumnIndex("Booking Reference");
        int debitColumn = findColumnIndex("Debit");

        boolean foundAnyRow = false;

        for (int page = 1; page <= totalPages; page++) {
            List<WebElement> rows = driver.getDriver().findElements(By.xpath("//table/tbody/tr"));
            for (int i = 1; i <= rows.size(); i++) {
                String cellValue = driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[" + bookingReferenceColumn + "]"));
                if (!cellValue.trim().equals(bookingReference)) {
                    continue;
                }
                foundAnyRow = true;

                BigDecimal debitAmount = normalizeAmount(driver.element().getText(By.xpath("//table/tbody/tr[" + i + "]/td[" + debitColumn + "]")));
                Assert.assertEquals(debitAmount.setScale(2, RoundingMode.HALF_UP), BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP),
                        "Booking reference " + bookingReference + " has a non-zero Debit entry in the AR Ledger report even though its refund request was rejected");
            }

            if (page < totalPages) {
                paginationHelper.navigateToNextPage();
            }
        }

        Assert.assertTrue(foundAnyRow,
                "Booking reference " + bookingReference + " was not found on any page of the AR Ledger report");
    }

    private int findColumnIndex(String headerName) {
        List<WebElement> headers = driver.getDriver().findElements(By.xpath("//table/thead/tr/th"));
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().equalsIgnoreCase(headerName)) {
                return i + 1;
            }
        }
        throw new IllegalStateException("Column header '" + headerName + "' was not found in the AR Ledger report table");
    }

    private BigDecimal normalizeAmount(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Amount value is null or empty");
        }

        String cleanedValue = value
                .trim()
                .replace(",", "")
                .replaceAll("[^0-9.\\-()]", "");

        boolean negative = cleanedValue.startsWith("(") && cleanedValue.endsWith(")");
        cleanedValue = cleanedValue.replace("(", "").replace(")", "");

        BigDecimal amount = new BigDecimal(cleanedValue);
        if (negative) {
            amount = amount.negate();
        }

        return amount.setScale(2, RoundingMode.HALF_UP);
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
