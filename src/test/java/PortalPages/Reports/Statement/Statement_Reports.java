package PortalPages.Reports.Statement;

import Drive_Factory.CommonMethod;
import PortalPages.Login.Login_Page;
import com.shaft.driver.SHAFT;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.DataUtils;

import java.util.Arrays;
import java.util.List;

public class Statement_Reports {

    private SHAFT.GUI.WebDriver driver;
    private Statement statement;
    private SHAFT.TestData.JSON testData;

    @BeforeClass
    public void setup() {
        CommonMethod.setupDriver(DataUtils.get("browser"));
        driver = CommonMethod.getDriver();
        testData = new SHAFT.TestData.JSON("PortalStatementReport.json");

        driver.browser().navigateToURL(DataUtils.get("Portal_Url"));
        new Login_Page(driver).PortalLogin();
    }

    @BeforeMethod
    public void openStatementReport() {
        statement = new Statement(driver);
        statement.openStatementReport();
    }

    @Test
    public void verifyUserCanOpenStatementReport() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(statement.isPageTitleDisplayed(), "Statement report page title should be displayed.");
        softAssert.assertAll();
    }

    @Test
    public void verifyStatementReportPageControls() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(statement.isInvoiceFromDateDisplayed(), "Invoice From Date should be displayed.");
        softAssert.assertTrue(statement.isInvoiceToDateDisplayed(), "Invoice To Date should be displayed.");
        softAssert.assertTrue(statement.isPassengerNameDisplayed(), "Passenger Name should be displayed.");
        softAssert.assertTrue(statement.isAgentNameDisplayed(), "Agent Name should be displayed.");
        softAssert.assertTrue(statement.isBookingReferenceDisplayed(), "Booking Reference should be displayed.");
        softAssert.assertTrue(statement.isSearchButtonDisplayed(), "Search button should be displayed.");
        softAssert.assertAll();
    }

    @Test
    public void verifyRequiredValidationWhenInvoiceDatesAreEmpty() {
        statement.clickSearch();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(statement.getInvoiceFromDateRequiredMessage(), testData.getTestData("ValidationMessages.Required"));
        softAssert.assertEquals(statement.getInvoiceToDateRequiredMessage(), testData.getTestData("ValidationMessages.Required"));
        softAssert.assertAll();
    }

    @Test
    public void verifyRequiredValidationWhenInvoiceFromDateIsEmpty() {
        statement
                .searchValidToDate(
                        testData.getTestData("ValidDateRange.InvoiceToDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Month"),
                        validToDateValue()
                )
                .clickSearch();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(statement.getInvoiceFromDateRequiredMessage(), testData.getTestData("ValidationMessages.Required"));
        softAssert.assertAll();
    }

    @Test
    public void verifyRequiredValidationWhenInvoiceToDateIsEmpty() {
        statement
                .searchValidFromDate(
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Month"),
                        validFromDateValue()
                )
                .clickSearch();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(statement.getInvoiceToDateRequiredMessage(), testData.getTestData("ValidationMessages.Required"));
        softAssert.assertAll();
    }

    @Test
    public void verifyValidationWhenInvoiceFromDateIsAfterInvoiceToDate() {
        statement
                .searchValidFromDate(
                        testData.getTestData("InvalidFromAfterTo.InvoiceFromDate.Day"),
                        testData.getTestData("InvalidFromAfterTo.InvoiceFromDate.Year"),
                        testData.getTestData("InvalidFromAfterTo.InvoiceFromDate.Month"),
                        testData.getTestData("InvalidFromAfterTo.InvoiceFromDate.Value")
                )
                .searchValidToDate(
                        testData.getTestData("InvalidFromAfterTo.InvoiceToDate.Day"),
                        testData.getTestData("InvalidFromAfterTo.InvoiceToDate.Year"),
                        testData.getTestData("InvalidFromAfterTo.InvoiceToDate.Month"),
                        testData.getTestData("InvalidFromAfterTo.InvoiceToDate.Value")
                )
                .clickSearch();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(
                statement.getInvalidDateRangeMessage(),
                testData.getTestData("InvalidFromAfterTo.ExpectedError")
        );
        softAssert.assertAll();
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
                .searchValidToDate(
                        testData.getTestData("ValidDateRange.InvoiceToDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Month"),
                        validToDateValue()
                )
                .searchValidFromDate(
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Month"),
                        validFromDateValue()
                );

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(statement.getInvoiceFromDateValue(), validFromDateValue());
        softAssert.assertEquals(statement.getInvoiceToDateValue(), validToDateValue());

        statement.clickSearch();

        softAssert.assertEquals(statement.getTableHeaders(), expectedHeaders);
        softAssert.assertAll();
    }

    @Test
    public void verifySearchUsingValidDateRangeDisplaysResults() {
        statement.searchByInvoiceDateRange(
                testData.getTestData("ValidDateRange.InvoiceFromDate.Day"),
                testData.getTestData("ValidDateRange.InvoiceFromDate.Year"),
                testData.getTestData("ValidDateRange.InvoiceFromDate.Month"),
                validFromDateValue(),
                testData.getTestData("ValidDateRange.InvoiceToDate.Day"),
                testData.getTestData("ValidDateRange.InvoiceToDate.Year"),
                testData.getTestData("ValidDateRange.InvoiceToDate.Month"),
                validToDateValue()
        );

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(statement.isReportTableDisplayed(), "Statement report table should be displayed.");
        softAssert.assertTrue(statement.areTableHeadersDisplayed(), "Statement report headers should be displayed.");
        softAssert.assertTrue(statement.getTableRowsCount() > 0, "Statement report should display at least one row.");
        softAssert.assertTrue(statement.isExportToExcelButtonDisplayed(), "Export To Excel button should be displayed.");
        softAssert.assertAll();
    }

    @Test
    public void verifySearchByPassengerNameReturnsMatchingRows() {
        String passengerName = testData.getTestData("Passenger.ValidName");

        statement
                .searchValidFromDate(
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Month"),
                        validFromDateValue()
                )
                .searchValidToDate(
                        testData.getTestData("ValidDateRange.InvoiceToDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Month"),
                        validToDateValue()
                )
                .enterPassengerName(passengerName)
                .clickSearch();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(statement.getTableRowsCount() > 0, "Passenger search should return at least one row.");

        boolean passengerIsFound = false;

        for (String passengerValue : statement.getColumnValues(6)) {
            if (passengerValue.toLowerCase().contains(passengerName.toLowerCase())) {
                passengerIsFound = true;
                break;
            }
        }

        softAssert.assertTrue(passengerIsFound, "Passenger column should contain the passenger filter value.");
        softAssert.assertAll();
    }

    @Test
    public void verifySearchUsingSameInvoiceFromAndToDateReturnsSameDateRows() {
        String invoiceDate = testData.getTestData("SameDaySearch.InvoiceDate.Value");

        statement.searchByInvoiceDateRange(
                testData.getTestData("SameDaySearch.InvoiceDate.Day"),
                testData.getTestData("SameDaySearch.InvoiceDate.Year"),
                testData.getTestData("SameDaySearch.InvoiceDate.Month"),
                invoiceDate,
                testData.getTestData("SameDaySearch.InvoiceDate.Day"),
                testData.getTestData("SameDaySearch.InvoiceDate.Year"),
                testData.getTestData("SameDaySearch.InvoiceDate.Month"),
                invoiceDate
        );

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(statement.getTableRowsCount() > 0, "Same-day search should return at least one row.");

        for (String dateOfIssue : statement.getColumnValues(5)) {
            softAssert.assertTrue(
                    isSameDate(dateOfIssue, invoiceDate),
                    "Date of Issue should belong to the selected same-day date."
            );
        }

        softAssert.assertAll();
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
                .searchValidFromDate(
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Month"),
                        validFromDateValue()
                )
                .searchValidToDate(
                        testData.getTestData("ValidDateRange.InvoiceToDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Month"),
                        validToDateValue()
                )
                .enterPassengerName(testData.getTestData("Passenger.InvalidName"))
                .clickSearchAndWaitForNoData();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                statement.isNoRecordsMessageDisplayed() || statement.hasNoTableRows(),
                "No data message should be displayed or no table rows should be returned."
        );
        softAssert.assertAll();
    }

    @Test
    public void verifySearchByValidBookingReferenceReturnsMatchingTransaction() {
        String bookingReference = testData.getTestData("BookingReference.ValidReference");

        statement
                .searchValidFromDate(
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Month"),
                        validFromDateValue()
                )
                .searchValidToDate(
                        testData.getTestData("ValidDateRange.InvoiceToDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Month"),
                        validToDateValue()
                )
                .enterBookingReference(bookingReference)
                .clickSearch();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(statement.getTableRowsCount() > 0, "Booking reference search should return at least one row.");

        boolean bookingReferenceIsFound = false;

        for (String transactionId : statement.getColumnValues(10)) {
            if (transactionId.contains(bookingReference)) {
                bookingReferenceIsFound = true;
                break;
            }
        }

        softAssert.assertTrue(bookingReferenceIsFound, "Transaction ID should contain the booking reference filter value.");
        softAssert.assertAll();
    }

    @Test
    public void verifyNoDataMessageWhenBookingReferenceDoesNotExist() {
        statement
                .searchValidFromDate(
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Month"),
                        validFromDateValue()
                )
                .searchValidToDate(
                        testData.getTestData("ValidDateRange.InvoiceToDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Month"),
                        validToDateValue()
                )
                .enterBookingReference(testData.getTestData("BookingReference.InvalidReference"))
                .clickSearchAndWaitForNoData();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(
                statement.isNoRecordsMessageDisplayed() || statement.hasNoTableRows(),
                "No data message should be displayed or no table rows should be returned."
        );
        softAssert.assertAll();
    }

    @Test
    public void verifySearchByAgentNameReturnsMatchingRows() {
        String agentName = testData.getTestData("Agent.Name");

        statement
                .searchValidFromDate(
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceFromDate.Month"),
                        validFromDateValue()
                )
                .searchValidToDate(
                        testData.getTestData("ValidDateRange.InvoiceToDate.Day"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Year"),
                        testData.getTestData("ValidDateRange.InvoiceToDate.Month"),
                        validToDateValue()
                )
                .selectAgentName(agentName)
                .clickSearch();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(statement.getTableRowsCount() > 0, "Agent search should return at least one row.");

        for (String agencyName : statement.getColumnValues(3)) {
            softAssert.assertFalse(agencyName.isBlank(), "Agency Name should be displayed for the selected agent result.");
        }

        softAssert.assertAll();
    }

    @Test
    public void verifyUserCanExportStatementReportToExcel() {
        statement.searchByInvoiceDateRange(
                testData.getTestData("ValidDateRange.InvoiceFromDate.Day"),
                testData.getTestData("ValidDateRange.InvoiceFromDate.Year"),
                testData.getTestData("ValidDateRange.InvoiceFromDate.Month"),
                validFromDateValue(),
                testData.getTestData("ValidDateRange.InvoiceToDate.Day"),
                testData.getTestData("ValidDateRange.InvoiceToDate.Year"),
                testData.getTestData("ValidDateRange.InvoiceToDate.Month"),
                validToDateValue()
        );

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(statement.isExportToExcelButtonDisplayed(), "Export To Excel button should be displayed.");
        softAssert.assertTrue(statement.isExportToExcelButtonClickable(), "Export To Excel button should be clickable.");
        softAssert.assertAll();

        statement.clickExportToExcel();
    }

    private String validFromDateValue() {
        return testData.getTestData("ValidDateRange.InvoiceFromDate.Value");
    }

    private String validToDateValue() {
        return testData.getTestData("ValidDateRange.InvoiceToDate.Value");
    }
}
