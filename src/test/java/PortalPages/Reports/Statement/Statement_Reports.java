package PortalPages.Reports.Statement;

import PortalPages.Login.Login_Page;
import PortalPages.Login.PortalTestBase_TC;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class Statement_Reports extends PortalTestBase_TC {

    private Statement statement;
    private SHAFT.TestData.JSON testData;

    @BeforeClass
    public void loginToPortal() throws InterruptedException {
        testData = new SHAFT.TestData.JSON("PortalStatementReport.json");
        loginIfNeeded();
    }

    @BeforeMethod
    public void openStatementReport() {
        statement = new Statement(driver);
        statement.openStatementReport();
    }

    private void loginIfNeeded() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            if (isElementPresent(By.id("id-AgencyCode"))) {
                new Login_Page(driver).PortalLogin();
                waitUntilLoginCompletes();
                return;
            }

            if (isElementPresent(By.xpath("//tilde-theme-side-menu"))) {
                return;
            }

            Thread.sleep(500);
        }
    }

    private void waitUntilLoginCompletes() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            if (!isElementPresent(By.id("id-AgencyCode"))) {
                return;
            }

            Thread.sleep(500);
        }
    }

    private boolean isElementPresent(By locator) {
        return !driver.getDriver().findElements(locator).isEmpty();
    }

    @Test
    public void verifyUserCanOpenStatementReport() {
        Assert.assertTrue(statement.isPageTitleDisplayed(), "Statement report page title should be displayed.");
    }

    @Test
    public void verifyStatementReportPageControls() {
        Assert.assertTrue(statement.isInvoiceFromDateDisplayed(), "Invoice From Date should be displayed.");
        Assert.assertTrue(statement.isInvoiceToDateDisplayed(), "Invoice To Date should be displayed.");
        Assert.assertTrue(statement.isPassengerNameDisplayed(), "Passenger Name should be displayed.");
        Assert.assertTrue(statement.isAgentNameDisplayed(), "Agent Name should be displayed.");
        Assert.assertTrue(statement.isBookingReferenceDisplayed(), "Booking Reference should be displayed.");
        Assert.assertTrue(statement.isSearchButtonDisplayed(), "Search button should be displayed.");
    }

    @Test
    public void verifyRequiredValidationWhenInvoiceDatesAreEmpty() {
        statement.clickSearch();

        Assert.assertEquals(statement.getInvoiceFromDateRequiredMessage(), testData.getTestData("ValidationMessages.Required"));
        Assert.assertEquals(statement.getInvoiceToDateRequiredMessage(), testData.getTestData("ValidationMessages.Required"));
    }

    @Test
    public void verifyRequiredValidationWhenInvoiceFromDateIsEmpty() {
        statement
                .enterInvoiceToDate(testData.getTestData("ValidDateRange.InvoiceToDate"))
                .clickSearch();

        Assert.assertEquals(statement.getInvoiceFromDateRequiredMessage(), testData.getTestData("ValidationMessages.Required"));
    }

    @Test
    public void verifyRequiredValidationWhenInvoiceToDateIsEmpty() {
        statement
                .enterInvoiceFromDate(testData.getTestData("ValidDateRange.InvoiceFromDate"))
                .clickSearch();

        Assert.assertEquals(statement.getInvoiceToDateRequiredMessage(), testData.getTestData("ValidationMessages.Required"));
    }

    @Test
    public void verifyValidationWhenInvoiceFromDateIsAfterInvoiceToDate() {
        statement
                .enterInvoiceFromDate(testData.getTestData("InvalidFromAfterTo.InvoiceFromDate"))
                .enterInvoiceToDate(testData.getTestData("InvalidFromAfterTo.InvoiceToDate"))
                .clickSearch();

        Assert.assertEquals(
                statement.getInvalidDateRangeMessage(),
                testData.getTestData("InvalidFromAfterTo.ExpectedError")
        );
    }

    @Test
    public void verifyStatementReportTableHeaders() {
        List<String> expectedHeaders = Arrays.asList(
                testData.getTestData("TableHeaders.SrNo"),
                testData.getTestData("TableHeaders.BranchName"),
                testData.getTestData("TableHeaders.AgencyName"),
                testData.getTestData("TableHeaders.ProductName"),
                testData.getTestData("TableHeaders.DateOfIssue"),
                testData.getTestData("TableHeaders.PassengerNameRoomNo"),
                testData.getTestData("TableHeaders.PnrNumber"),
                testData.getTestData("TableHeaders.TicketNumber"),
                testData.getTestData("TableHeaders.TransactionStatus"),
                testData.getTestData("TableHeaders.TransactionId"),
                testData.getTestData("TableHeaders.Route"),
                testData.getTestData("TableHeaders.TotalPayable"),
                testData.getTestData("TableHeaders.Discount"),
                testData.getTestData("TableHeaders.TransactionAmountDebit"),
                testData.getTestData("TableHeaders.TransactionAmountCredit"),
                testData.getTestData("TableHeaders.WalletBalance"),
                testData.getTestData("TableHeaders.AirlineCode")
        );

        statement
                .enterInvoiceToDate(testData.getTestData("ValidDateRange.InvoiceToDate"))
                .enterInvoiceFromDate(testData.getTestData("ValidDateRange.InvoiceFromDate"));

        Assert.assertEquals(statement.getInvoiceFromDateValue(), testData.getTestData("ValidDateRange.InvoiceFromDate"));
        Assert.assertEquals(statement.getInvoiceToDateValue(), testData.getTestData("ValidDateRange.InvoiceToDate"));

        statement.clickSearch();

        Assert.assertEquals(statement.getTableHeaders(), expectedHeaders);
    }

    @Test
    public void verifySearchUsingValidDateRangeDisplaysResults() {
        statement.searchByInvoiceDateRange(
                testData.getTestData("ValidDateRange.InvoiceFromDate"),
                testData.getTestData("ValidDateRange.InvoiceToDate")
        );

        Assert.assertTrue(statement.isReportTableDisplayed(), "Statement report table should be displayed.");
        Assert.assertTrue(statement.areTableHeadersDisplayed(), "Statement report headers should be displayed.");
        Assert.assertTrue(statement.getTableRowsCount() > 0, "Statement report should display at least one row.");
        Assert.assertTrue(statement.isExportToExcelButtonDisplayed(), "Export To Excel button should be displayed.");
    }

    @Test
    public void verifySearchByPassengerNameReturnsMatchingRows() {
        String passengerName = testData.getTestData("Passenger.ValidName");

        statement
                .enterInvoiceFromDate(testData.getTestData("ValidDateRange.InvoiceFromDate"))
                .enterInvoiceToDate(testData.getTestData("ValidDateRange.InvoiceToDate"))
                .enterPassengerName(passengerName)
                .clickSearch();

        Assert.assertTrue(statement.getTableRowsCount() > 0, "Passenger search should return at least one row.");

        boolean passengerIsFound = false;

        for (String passengerValue : statement.getColumnValues(6)) {
            if (passengerValue.toLowerCase().contains(passengerName.toLowerCase())) {
                passengerIsFound = true;
                break;
            }
        }

        Assert.assertTrue(passengerIsFound, "Passenger column should contain the passenger filter value.");
    }

    @Test
    public void verifySearchUsingSameInvoiceFromAndToDateReturnsSameDateRows() {
        String invoiceDate = testData.getTestData("SameDaySearch.InvoiceDate");

        statement.searchByInvoiceDateRange(invoiceDate, invoiceDate);

        Assert.assertTrue(statement.getTableRowsCount() > 0, "Same-day search should return at least one row.");

        for (String dateOfIssue : statement.getColumnValues(5)) {
            Assert.assertTrue(
                    isSameDate(dateOfIssue, invoiceDate),
                    "Date of Issue should belong to the selected same-day date."
            );
        }
    }

    private boolean isSameDate(String actualDateTime, String expectedDate) {
        String actualDate = actualDateTime.split(" ")[0].replace("-", "/");
        String[] actualParts = actualDate.split("/");
        String[] expectedParts = expectedDate.split("/");

        return Integer.parseInt(actualParts[0]) == Integer.parseInt(expectedParts[0])
                && Integer.parseInt(actualParts[1]) == Integer.parseInt(expectedParts[1])
                && Integer.parseInt(actualParts[2]) == Integer.parseInt(expectedParts[2]);
    }

    @Test
    public void verifyNoDataMessageWhenPassengerDoesNotExist() {
        statement
                .enterInvoiceFromDate(testData.getTestData("ValidDateRange.InvoiceFromDate"))
                .enterInvoiceToDate(testData.getTestData("ValidDateRange.InvoiceToDate"))
                .enterPassengerName(testData.getTestData("Passenger.InvalidName"))
                .clickSearchAndWaitForNoData();

        Assert.assertTrue(
                statement.isNoRecordsMessageDisplayed() || statement.hasNoTableRows(),
                "No data message should be displayed or no table rows should be returned."
        );
    }

    @Test
    public void verifySearchByValidBookingReferenceReturnsMatchingTransaction() {
        String bookingReference = testData.getTestData("BookingReference.ValidReference");

        statement
                .enterInvoiceFromDate(testData.getTestData("ValidDateRange.InvoiceFromDate"))
                .enterInvoiceToDate(testData.getTestData("ValidDateRange.InvoiceToDate"))
                .enterBookingReference(bookingReference)
                .clickSearch();

        Assert.assertTrue(statement.getTableRowsCount() > 0, "Booking reference search should return at least one row.");

        boolean bookingReferenceIsFound = false;

        for (String transactionId : statement.getColumnValues(10)) {
            if (transactionId.contains(bookingReference)) {
                bookingReferenceIsFound = true;
                break;
            }
        }

        Assert.assertTrue(bookingReferenceIsFound, "Transaction ID should contain the booking reference filter value.");
    }

    @Test
    public void verifyNoDataMessageWhenBookingReferenceDoesNotExist() {
        statement
                .enterInvoiceFromDate(testData.getTestData("ValidDateRange.InvoiceFromDate"))
                .enterInvoiceToDate(testData.getTestData("ValidDateRange.InvoiceToDate"))
                .enterBookingReference(testData.getTestData("BookingReference.InvalidReference"))
                .clickSearchAndWaitForNoData();

        Assert.assertTrue(
                statement.isNoRecordsMessageDisplayed() || statement.hasNoTableRows(),
                "No data message should be displayed or no table rows should be returned."
        );
    }

    @Test
    public void verifySearchByAgentNameReturnsMatchingRows() {
        String agentName = testData.getTestData("Agent.Name");

        statement
                .enterInvoiceFromDate(testData.getTestData("ValidDateRange.InvoiceFromDate"))
                .enterInvoiceToDate(testData.getTestData("ValidDateRange.InvoiceToDate"))
                .selectAgentName(agentName)
                .clickSearch();

        Assert.assertTrue(statement.getTableRowsCount() > 0, "Agent search should return at least one row.");

        for (String agencyName : statement.getColumnValues(3)) {
            Assert.assertFalse(agencyName.isBlank(), "Agency Name should be displayed for the selected agent result.");
        }
    }

    @Test
    public void verifyUserCanExportStatementReportToExcel() {
        statement.searchByInvoiceDateRange(
                testData.getTestData("ValidDateRange.InvoiceFromDate"),
                testData.getTestData("ValidDateRange.InvoiceToDate")
        );

        Assert.assertTrue(statement.isExportToExcelButtonDisplayed(), "Export To Excel button should be displayed.");
        Assert.assertTrue(statement.isExportToExcelButtonClickable(), "Export To Excel button should be clickable.");

        statement.clickExportToExcel();
    }
}
