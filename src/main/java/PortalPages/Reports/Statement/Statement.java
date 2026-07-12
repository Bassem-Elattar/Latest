package PortalPages.Reports.Statement;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utilities.DataUtils;

import java.util.ArrayList;
import java.util.List;

public class Statement {

    private final SHAFT.GUI.WebDriver driver;

    private final By Btn_ReportsMenu = By.xpath("//tilde-theme-side-menu//a[.//span[normalize-space()='Reports'] or normalize-space()='Reports']");
    private final By Btn_StatementReportMenu = By.xpath("//ndc-reports/div/div/div/div[2]/ndc-card[4]/div/div[3]/button");
    private final By Txt_PageTitle = By.xpath("//ndc-statement//p[normalize-space()='Statement reports']");
    private final By Dpick_InvoiceFromDate = By.id("id-InvoiceFromDate");
    private final By Btn_InvoiceFromDateCalendar = By.xpath("//input[@id='id-InvoiceFromDate']/ancestor::p-calendar//button");
    private final By Txt_InvoiceFromDateRequiredMessage = By.xpath("//input[@id='id-InvoiceFromDate']/ancestor::ndc-fg-input/span[normalize-space()='Required']");
    private final By Dpick_InvoiceToDate = By.id("id-InvoiceToDate");
    private final By Btn_InvoiceToDateCalendar = By.xpath("//input[@id='id-InvoiceToDate']/ancestor::p-calendar//button");
    private final By Txt_InvoiceToDateRequiredMessage = By.xpath("//input[@id='id-InvoiceToDate']/ancestor::ndc-fg-input/span[normalize-space()='Required']");
    private final By Txt_InvalidDateRangeMessage = By.xpath("//ndc-statement//span[normalize-space()='invoiceFromDate must be smaller than invoiceToDate']");
    private final By Txt_ValidationMessages = By.xpath("//ndc-statement//span[contains(@class,'fg-error') and normalize-space()!='']");
    private final By Txt_PassengerName = By.id("id-PassengerName");
    private final By Txt_AgentName = By.id("id-AgentName");
    private final By Lst_AgentName = By.xpath("//input[@id='id-AgentName']/ancestor::p-dropdown//div[contains(@class,'p-dropdown-trigger')]");
    private final By Txt_BookingReference = By.id("id-Bookingreference");
    private final By Btn_Search = By.xpath("//ndc-statement//button[@type='submit' and .//span[normalize-space()='Search']]");
    private final By Btn_ExportToExcel = By.xpath("//ndc-statement//button[contains(normalize-space(),'Export To Excel') or .//*[contains(normalize-space(),'Export To Excel')]]");
    private final By Lst_ReportTable = By.xpath("//ndc-statement//tilde-data-table//p-table//table");
    private final By Txt_TableHeaders = By.xpath("//ndc-statement//tilde-data-table//p-table//table/thead/tr/th");
    private final By Lst_TableRows = By.xpath("//ndc-statement//tilde-data-table//p-table//table/tbody/tr");
    private final By Txt_NoRecordsMessage = By.xpath("//*[contains(normalize-space(),'No data') or contains(normalize-space(),'No Data') or contains(normalize-space(),'No records')]");
    private final By Txt_ExactNoDataMessage = By.xpath("//*[normalize-space()='No data has been found!']");
    private final By Txt_ToastMessage = By.xpath("//div[@role='alert' or contains(@class,'toast-message')]");

    public Statement(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    public Statement openStatementReport() {
        hideChatWidget();
        driver.element().click(Btn_ReportsMenu);
        waitForStatementReportCard();
        driver.element().click(Btn_StatementReportMenu);
        waitForStatementReportForm();
        hideChatWidget();
        return this;
    }

    private void hideChatWidget() {
        ((JavascriptExecutor) driver.getDriver()).executeScript(
                "document.querySelectorAll('[data-id=\"zsalesiq\"], .zsiq_floatmain, .zsiq_cnt, .zls-sptwndw')" +
                        ".forEach(element => element.style.display = 'none');"
        );
    }

    private void clickUsingJavaScript(By locator) {
        WebElement element = driver.getDriver().findElement(locator);
        ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].click();", element);
    }

    private void waitForSearchResponse() {
        waitForSearchRefreshToStart();

        for (int i = 0; i < 60; i++) {
            if (isAnyElementDisplayed(Lst_TableRows)
                    || isAnyElementDisplayed(Txt_NoRecordsMessage)
                    || isAnyElementDisplayed(Txt_ValidationMessages)) {
                return;
            }

            sleep(500);
        }
    }

    private void waitForSearchRefreshToStart() {
        sleep(1000);
    }

    private boolean isAnyElementDisplayed(By locator) {
        List<WebElement> elements = driver.getDriver().findElements(locator);

        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                return true;
            }
        }

        return false;
    }

    private void waitForStatementReportForm() {
        for (int i = 0; i < 40; i++) {
            if (isAnyElementDisplayed(Txt_PageTitle) && isAnyElementDisplayed(Dpick_InvoiceFromDate)) {
                return;
            }

            sleep(250);
        }
    }

    private void waitForStatementReportCard() {
        for (int i = 0; i < 40; i++) {
            if (isAnyElementDisplayed(Btn_StatementReportMenu)) {
                return;
            }

            sleep(250);
        }
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void waitForTableHeaders() {
        for (int i = 0; i < 60; i++) {
            if (isAnyElementDisplayed(Txt_TableHeaders)) {
                return;
            }

            sleep(500);
        }
    }

    public Statement enterInvoiceFromDate(String from, String year, String month, String expectedDate) {
        return searchValidFromDate(from, year, month, expectedDate);
    }

    public Statement searchValidFromDate(String from, String year, String month, String expectedDate) {
        selectDateFromCalendar(Dpick_InvoiceFromDate, from, year, month, expectedDate);
        return this;
    }

    public Statement openInvoiceFromDateCalendar() {
        driver.element().click(Btn_InvoiceFromDateCalendar);
        return this;
    }

    public Statement enterInvoiceToDate(String to, String year, String month, String expectedDate) {
        return searchValidToDate(to, year, month, expectedDate);
    }

    public Statement searchValidToDate(String to, String year, String month, String expectedDate) {
        selectDateFromCalendar(Dpick_InvoiceToDate, to, year, month, expectedDate);
        return this;
    }

    private void selectDateFromCalendar(By input, String day, String year, String month, String expectedDate) {
        hideCalendarOverlay();
        driver.element().click(input);
        waitForVisibleCalendar();
        clickCalendarYearTitle();
        clickVisibleCalendarValue(year);
        clickVisibleCalendarValue(month);
        clickVisibleCalendarDay(day);
        waitForDateValue(input, expectedDate);

        if (!expectedDate.equals(getInputValue(input))) {
            hideCalendarOverlay();
            driver.element().click(input);
            waitForVisibleCalendar();
            clickCalendarYearTitle();
            clickVisibleCalendarValue(year);
            clickVisibleCalendarValue(month);
            clickVisibleCalendarDay(day);
            waitForDateValue(input, expectedDate);
        }

        hideCalendarOverlay();
    }

    private void clickCalendarYearTitle() {
        By yearTitle = By.xpath("//div[contains(@class,'p-datepicker') and not(contains(@style,'display: none'))]//*[contains(@class,'p-datepicker-year')]");
        driver.element().click(yearTitle);
        waitForVisibleCalendar();
    }

    private void clickVisibleCalendarValue(String value) {
        By calendarValue = By.xpath("//div[contains(@class,'p-datepicker') and not(contains(@style,'display: none'))]//span[normalize-space()='" + value + "']");
        driver.element().click(calendarValue);
        waitForVisibleCalendar();
    }

    private void clickVisibleCalendarDay(String day) {
        By calendarDay = By.xpath("(//div[contains(@class,'p-datepicker') and not(contains(@style,'display: none'))]//td[not(contains(@class,'p-datepicker-other-month')) and not(contains(@class,'p-disabled'))]//span[normalize-space()='" + day + "'])[1]");
        driver.element().click(calendarDay);
    }

    private void waitForVisibleCalendar() {
        for (int i = 0; i < 20; i++) {
            Object visibleCalendarsCount = ((JavascriptExecutor) driver.getDriver()).executeScript(
                    "return Array.from(document.querySelectorAll('.p-datepicker, [class*=\"datepicker\"]'))" +
                            ".filter(calendar => {" +
                            "  const style = window.getComputedStyle(calendar);" +
                            "  const rect = calendar.getBoundingClientRect();" +
                            "  return style.display !== 'none' && style.visibility !== 'hidden' && rect.width > 0 && rect.height > 0;" +
                            "}).length;"
            );

            if (Long.valueOf(visibleCalendarsCount.toString()) > 0) {
                return;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void hideCalendarOverlay() {
        driver.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
    }

    private void waitForDateValue(By input, String expectedDate) {
        for (int i = 0; i < 20; i++) {
            if (expectedDate.equals(getInputValue(input))) {
                return;
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private String getInputValue(By input) {
        return driver.getDriver().findElement(input).getAttribute("value");
    }

    public String getInvoiceFromDateValue() {
        return driver.getDriver().findElement(Dpick_InvoiceFromDate).getAttribute("value");
    }

    public String getInvoiceToDateValue() {
        return driver.getDriver().findElement(Dpick_InvoiceToDate).getAttribute("value");
    }

    public Statement openInvoiceToDateCalendar() {
        driver.element().click(Btn_InvoiceToDateCalendar);
        return this;
    }

    public Statement enterPassengerName(String passenger) {
        setInputValue(Txt_PassengerName, passenger);
        waitForInputValue(Txt_PassengerName, passenger);
        return this;
    }

    private void setInputValue(By input, String value) {
        WebElement element = driver.getDriver().findElement(input);
        ((JavascriptExecutor) driver.getDriver()).executeScript(
                "const element = arguments[0];" +
                        "const value = arguments[1];" +
                        "element.focus();" +
                        "element.value = '';" +
                        "element.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "element.value = value;" +
                        "element.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "element.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "element.blur();",
                element,
                value
        );
    }

    private void waitForInputValue(By input, String expectedValue) {
        for (int i = 0; i < 20; i++) {
            if (expectedValue.equals(getInputValue(input))) {
                return;
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public Statement selectAgentName(String agent) {
        driver.element().click(Lst_AgentName);
        typeIntoOpenDropdownFilter(agent);
        By agentOption = By.xpath("(//p-dropdownitem//li[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + agent.toLowerCase() + "')])[1]");

        if (isAnyElementDisplayed(agentOption)) {
            driver.element().click(agentOption);
        } else {
            setInputValue(Txt_AgentName, agent);
            waitForInputValue(Txt_AgentName, agent);
            hideCalendarOverlay();
        }

        return this;
    }

    private void typeIntoOpenDropdownFilter(String value) {
        List<WebElement> dropdownInputs = driver.getDriver().findElements(
                By.xpath("//div[contains(@class,'p-dropdown-panel') and not(contains(@style,'display: none'))]//input")
        );

        if (!dropdownInputs.isEmpty()) {
            dropdownInputs.get(0).sendKeys(value);
            waitForDropdownOptions();
        }
    }

    private void waitForDropdownOptions() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Statement enterBookingReference(String reference) {
        setInputValue(Txt_BookingReference, reference);
        waitForInputValue(Txt_BookingReference, reference);
        return this;
    }

    public Statement clickSearch() {
        hideChatWidget();
        clickUsingJavaScript(Btn_Search);
        waitForSearchResponse();
        return this;
    }

    public Statement clickSearchAndWaitForNoData() {
        hideChatWidget();
        clickUsingJavaScript(Btn_Search);
        waitForNoDataResponse();
        return this;
    }

    private void waitForNoDataResponse() {
        waitForSearchRefreshToStart();

        for (int i = 0; i < 20; i++) {
            if (isAnyElementDisplayed(Txt_ExactNoDataMessage) || getTableRowsCount() == 0) {
                return;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public Statement searchByInvoiceDateRange(String from, String fromYear, String fromMonth, String fromDate,
                                              String to, String toYear, String toMonth, String toDate) {
        enterInvoiceToDate(to, toYear, toMonth, toDate);
        enterInvoiceFromDate(from, fromYear, fromMonth, fromDate);
        clickSearch();
        return this;
    }

    public Statement clickExportToExcel() {
        hideChatWidget();
        clickUsingJavaScript(Btn_ExportToExcel);
        return this;
    }

    public boolean isReportTableDisplayed() {
        return driver.element().isElementDisplayed(Lst_ReportTable);
    }

    public boolean isPageTitleDisplayed() {
        return driver.element().isElementDisplayed(Txt_PageTitle);
    }

    public boolean isInvoiceFromDateDisplayed() {
        return driver.element().isElementDisplayed(Dpick_InvoiceFromDate);
    }

    public boolean isInvoiceToDateDisplayed() {
        return driver.element().isElementDisplayed(Dpick_InvoiceToDate);
    }

    public boolean isPassengerNameDisplayed() {
        return driver.element().isElementDisplayed(Txt_PassengerName);
    }

    public boolean isAgentNameDisplayed() {
        return driver.element().isElementDisplayed(Lst_AgentName);
    }

    public boolean isBookingReferenceDisplayed() {
        return driver.element().isElementDisplayed(Txt_BookingReference);
    }

    public boolean isSearchButtonDisplayed() {
        return driver.element().isElementDisplayed(Btn_Search);
    }

    public String getInvoiceFromDateRequiredMessage() {
        return driver.element().getText(Txt_InvoiceFromDateRequiredMessage);
    }

    public String getInvoiceToDateRequiredMessage() {
        return driver.element().getText(Txt_InvoiceToDateRequiredMessage);
    }

    public String getInvalidDateRangeMessage() {
        return driver.element().getText(Txt_InvalidDateRangeMessage);
    }

    public boolean areTableHeadersDisplayed() {
        return isAnyElementDisplayed(Txt_TableHeaders);
    }

    public List<String> getTableHeaders() {
        waitForTableHeaders();

        List<String> headers = new ArrayList<>();
        List<WebElement> headerElements = driver.getDriver().findElements(Txt_TableHeaders);

        for (WebElement headerElement : headerElements) {
            if (headerElement.isDisplayed()) {
                headers.add(headerElement.getText().trim());
            }
        }

        return headers;
    }

    public boolean areTableRowsDisplayed() {
        return driver.element().isElementDisplayed(Lst_TableRows);
    }

    public int getTableRowsCount() {
        int rowsCount = 0;
        List<WebElement> rows = driver.getDriver().findElements(Lst_TableRows);

        for (WebElement row : rows) {
            if (row.isDisplayed()) {
                rowsCount++;
            }
        }

        return rowsCount;
    }

    public List<String> getColumnValues(int columnIndex) {
        List<String> values = new ArrayList<>();
        List<WebElement> cellElements = driver.getDriver().findElements(
                By.xpath("//ndc-statement//tilde-data-table//p-table//table/tbody/tr/td[" + columnIndex + "]")
        );

        for (WebElement cellElement : cellElements) {
            if (cellElement.isDisplayed()) {
                values.add(cellElement.getText().trim());
            }
        }

        return values;
    }

    public boolean isExportToExcelButtonDisplayed() {
        return driver.element().isElementDisplayed(Btn_ExportToExcel);
    }

    public boolean isExportToExcelButtonClickable() {
        return driver.element().isElementClickable(Btn_ExportToExcel);
    }

    public boolean isNoRecordsMessageDisplayed() {
        return isAnyElementDisplayed(Txt_ExactNoDataMessage) || isAnyElementDisplayed(Txt_NoRecordsMessage);
    }

    public boolean hasNoTableRows() {
        return getTableRowsCount() == 0;
    }

    public String getToastMessage() {
        return driver.element().getText(Txt_ToastMessage);
    }
}
