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

    private final By reportsMenu = By.xpath("//tilde-theme-side-menu//a[.//span[normalize-space()='Reports'] or normalize-space()='Reports']");
    private final By statementReportMenu = By.xpath("//ndc-reports/div/div/div/div[2]/ndc-card[4]/div/div[3]/button");
    private final By pageTitle = By.xpath("//ndc-statement//p[normalize-space()='Statement reports']");
    private final By invoiceFromDate = By.id("id-InvoiceFromDate");
    private final By invoiceFromDateCalendarButton = By.xpath("//input[@id='id-InvoiceFromDate']/following-sibling::button");
    private final By invoiceFromDateRequiredMessage = By.xpath("//input[@id='id-InvoiceFromDate']/ancestor::ndc-fg-input/span[normalize-space()='Required']");
    private final By invoiceToDate = By.id("id-InvoiceToDate");
    private final By invoiceToDateCalendarButton = By.xpath("//input[@id='id-InvoiceToDate']/following-sibling::button");
    private final By invoiceToDateRequiredMessage = By.xpath("//input[@id='id-InvoiceToDate']/ancestor::ndc-fg-input/span[normalize-space()='Required']");
    private final By invalidDateRangeMessage = By.xpath("//ndc-statement//span[normalize-space()='invoiceFromDate must be smaller than invoiceToDate']");
    private final By validationMessages = By.xpath("//ndc-statement//span[contains(@class,'fg-error') and normalize-space()!='']");
    private final By passengerName = By.id("id-PassengerName");
    private final By agentName = By.id("id-AgentName");
    private final By agentNameDropdown = By.xpath("//input[@id='id-AgentName']/ancestor::p-dropdown//div[contains(@class,'p-dropdown-trigger')]");
    private final By bookingReference = By.id("id-Bookingreference");
    private final By searchButton = By.xpath("//ndc-statement//button[@type='submit' and .//span[normalize-space()='Search']]");
    private final By exportToExcelButton = By.xpath("//ndc-statement//button[contains(normalize-space(),'Export To Excel') or .//*[contains(normalize-space(),'Export To Excel')]]");
    private final By reportTable = By.xpath("//ndc-statement//tilde-data-table//p-table//table");
    private final By tableHeaders = By.xpath("//ndc-statement//tilde-data-table//p-table//table/thead/tr/th");
    private final By tableRows = By.xpath("//ndc-statement//tilde-data-table//p-table//table/tbody/tr");
    private final By noRecordsMessage = By.xpath("//*[contains(normalize-space(),'No data') or contains(normalize-space(),'No Data') or contains(normalize-space(),'No records')]");
    private final By exactNoDataMessage = By.xpath("//*[normalize-space()='No data has been found!']");
    private final By toastMessage = By.xpath("//div[@role='alert' or contains(@class,'toast-message')]");

    public Statement(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    public Statement openStatementReport() {
        driver.browser().navigateToURL(DataUtils.get("Portal_Url").replace("/auth/login", "/reports/statement"));
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
            if (isAnyElementDisplayed(tableRows)
                    || isAnyElementDisplayed(noRecordsMessage)
                    || isAnyElementDisplayed(validationMessages)) {
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

    private void waitForSearchRefreshToStart() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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

    private void waitForTableHeaders() {
        for (int i = 0; i < 60; i++) {
            if (isAnyElementDisplayed(tableHeaders)) {
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

    public Statement enterInvoiceFromDate(String fromDate) {
        selectDateFromCalendar(invoiceFromDate, invoiceFromDateCalendarButton, fromDate);
        return this;
    }

    public Statement openInvoiceFromDateCalendar() {
        driver.element().click(invoiceFromDateCalendarButton);
        return this;
    }

    public Statement enterInvoiceToDate(String toDate) {
        selectDateFromCalendar(invoiceToDate, invoiceToDateCalendarButton, toDate);
        return this;
    }

    private void selectDateFromCalendar(By input, By calendarButton, String date) {
        hideCalendarOverlay();
        clickUsingJavaScript(calendarButton);
        waitForVisibleCalendar();
        selectCalendarDate(date);
        waitForDateValue(input, date);

        if (!date.equals(getInputValue(input))) {
            hideCalendarOverlay();
            clickUsingJavaScript(calendarButton);
            waitForVisibleCalendar();
            selectCalendarDate(date);
            waitForDateValue(input, date);
        }

        hideCalendarOverlay();
    }

    private void selectCalendarYear(String year) {
        clickUsingJavaScript(By.xpath("//div[contains(@class,'p-datepicker') and not(contains(@style,'display: none'))]//button[contains(@class,'p-datepicker-year')]"));
        clickUsingJavaScript(By.xpath("//div[contains(@class,'p-datepicker') and not(contains(@style,'display: none'))]//span[normalize-space()='" + year + "']"));
    }

    private void selectCalendarMonth(String monthNumber) {
        String[] months = {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };
        String month = months[Integer.parseInt(monthNumber) - 1];

        clickUsingJavaScript(By.xpath("//div[contains(@class,'p-datepicker') and not(contains(@style,'display: none'))]//span[normalize-space()='" + month + "']"));
    }

    private void selectCalendarDate(String date) {
        String[] dateParts = date.split("/");
        String day = String.valueOf(Integer.parseInt(dateParts[0]));
        selectCalendarDay(day);
    }

    private void selectCalendarDateLikeAdmin(String date) {
        String[] dateParts = date.split("/");
        String day = String.valueOf(Integer.parseInt(dateParts[0]));
        String month = getCalendarMonthName(dateParts[1]);
        String year = dateParts[2];

        clickVisibleCalendarText(year);
        waitForVisibleCalendar();
        clickVisibleCalendarText(year);
        waitForVisibleCalendar();
        clickVisibleCalendarText(month);
        waitForVisibleCalendar();
        selectCalendarDay(day);
    }

    private void clickVisibleCalendarText(String value) {
        ((JavascriptExecutor) driver.getDriver()).executeScript(
                "const value = arguments[0];" +
                        "const calendars = Array.from(document.querySelectorAll('.p-datepicker, [class*=\"datepicker\"]'))" +
                        ".filter(calendar => {" +
                        "  const style = window.getComputedStyle(calendar);" +
                        "  const rect = calendar.getBoundingClientRect();" +
                        "  return style.display !== 'none' && style.visibility !== 'hidden' && rect.width > 0 && rect.height > 0;" +
                        "});" +
                        "const calendar = calendars[calendars.length - 1];" +
                        "if (!calendar) { throw new Error('No visible calendar popup was found'); }" +
                        "const matches = Array.from(calendar.querySelectorAll('*'))" +
                        ".filter(element => {" +
                        "  const rect = element.getBoundingClientRect();" +
                        "  const disabled = element.disabled || element.classList.contains('p-disabled') || element.closest('.p-disabled');" +
                        "  return rect.width > 0 && rect.height > 0 && !disabled && element.textContent.trim() === value;" +
                        "});" +
                        "if (!matches.length) { throw new Error('Calendar value was not found: ' + value); }" +
                        "matches.sort((first, second) => {" +
                        "  const firstRect = first.getBoundingClientRect();" +
                        "  const secondRect = second.getBoundingClientRect();" +
                        "  return (firstRect.width * firstRect.height) - (secondRect.width * secondRect.height);" +
                        "});" +
                        "const target = matches[0].closest('button') || matches[0];" +
                        "target.click();",
                value
        );
    }

    private String getCalendarMonthName(String monthNumber) {
        String[] months = {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };

        return months[Integer.parseInt(monthNumber) - 1];
    }

    private void clickCalendarTitleValue(String value) {
        clickCalendarValue(value, true);
        waitForVisibleCalendar();
    }

    private void clickCalendarValue(String value) {
        clickCalendarValue(value, false);
        waitForVisibleCalendar();
    }

    private void clickCalendarValue(String value, boolean titleOnly) {
        ((JavascriptExecutor) driver.getDriver()).executeScript(
                "const value = arguments[0];" +
                        "const titleOnly = arguments[1];" +
                        "const calendars = Array.from(document.querySelectorAll('.p-datepicker, [class*=\"datepicker\"]'))" +
                        ".filter(calendar => {" +
                        "  const style = window.getComputedStyle(calendar);" +
                        "  const rect = calendar.getBoundingClientRect();" +
                        "  return style.display !== 'none' && style.visibility !== 'hidden' && rect.width > 0 && rect.height > 0;" +
                        "});" +
                        "const calendar = calendars[calendars.length - 1];" +
                        "if (!calendar) { throw new Error('No visible calendar popup was found'); }" +
                        "const selector = titleOnly ? '.p-datepicker-title button, .p-datepicker-title span' : 'button, span';" +
                        "const elements = Array.from(calendar.querySelectorAll(selector))" +
                        ".filter(element => {" +
                        "  const rect = element.getBoundingClientRect();" +
                        "  const disabled = element.disabled || element.classList.contains('p-disabled')" +
                        "    || element.closest('.p-disabled');" +
                        "  return rect.width > 0 && rect.height > 0 && !disabled && element.textContent.trim() === value;" +
                        "});" +
                        "const target = titleOnly ? elements[0] : elements[elements.length - 1];" +
                        "if (!target) { throw new Error('Calendar value was not found: ' + value); }" +
                        "target.click();",
                value,
                titleOnly
        );
    }

    private void selectCalendarDay(String day) {
        ((JavascriptExecutor) driver.getDriver()).executeScript(
                "const day = arguments[0];" +
                        "const calendars = Array.from(document.querySelectorAll('.p-datepicker, [class*=\"datepicker\"]'))" +
                        ".filter(calendar => {" +
                        "  const style = window.getComputedStyle(calendar);" +
                        "  const rect = calendar.getBoundingClientRect();" +
                        "  return style.display !== 'none' && style.visibility !== 'hidden' && rect.width > 0 && rect.height > 0;" +
                        "});" +
                        "calendars.sort((first, second) => {" +
                        "  const firstRect = first.getBoundingClientRect();" +
                        "  const secondRect = second.getBoundingClientRect();" +
                        "  return (secondRect.width * secondRect.height) - (firstRect.width * firstRect.height);" +
                        "});" +
                        "const calendar = calendars[0];" +
                        "if (!calendar) { throw new Error('No visible calendar popup was found'); }" +
                        "const days = Array.from(calendar.querySelectorAll('td:not(.p-datepicker-other-month):not(.p-disabled) span'));" +
                        "const targetDay = days.find(element => element.textContent.trim() === day);" +
                        "if (!targetDay) { throw new Error('Calendar day was not found: ' + day); }" +
                        "targetDay.click();",
                day
        );
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
        return driver.getDriver().findElement(invoiceFromDate).getAttribute("value");
    }

    public String getInvoiceToDateValue() {
        return driver.getDriver().findElement(invoiceToDate).getAttribute("value");
    }

    public Statement openInvoiceToDateCalendar() {
        driver.element().click(invoiceToDateCalendarButton);
        return this;
    }

    public Statement enterPassengerName(String passenger) {
        setInputValue(passengerName, passenger);
        waitForInputValue(passengerName, passenger);
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
        driver.element().click(agentNameDropdown);
        typeIntoOpenDropdownFilter(agent);
        By agentOption = By.xpath("(//p-dropdownitem//li[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + agent.toLowerCase() + "')])[1]");

        if (isAnyElementDisplayed(agentOption)) {
            driver.element().click(agentOption);
        } else {
            setInputValue(agentName, agent);
            waitForInputValue(agentName, agent);
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
        setInputValue(bookingReference, reference);
        waitForInputValue(bookingReference, reference);
        return this;
    }

    public Statement clickSearch() {
        hideChatWidget();
        clickUsingJavaScript(searchButton);
        waitForSearchResponse();
        return this;
    }

    public Statement clickSearchAndWaitForNoData() {
        hideChatWidget();
        clickUsingJavaScript(searchButton);
        waitForNoDataResponse();
        return this;
    }

    private void waitForNoDataResponse() {
        waitForSearchRefreshToStart();

        for (int i = 0; i < 20; i++) {
            if (isAnyElementDisplayed(exactNoDataMessage) || getTableRowsCount() == 0) {
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

    public Statement searchByInvoiceDateRange(String fromDate, String toDate) {
        enterInvoiceToDate(toDate);
        enterInvoiceFromDate(fromDate);
        clickSearch();
        return this;
    }

    public Statement clickExportToExcel() {
        hideChatWidget();
        clickUsingJavaScript(exportToExcelButton);
        return this;
    }

    public boolean isReportTableDisplayed() {
        return driver.element().isElementDisplayed(reportTable);
    }

    public boolean isPageTitleDisplayed() {
        return driver.element().isElementDisplayed(pageTitle);
    }

    public boolean isInvoiceFromDateDisplayed() {
        return driver.element().isElementDisplayed(invoiceFromDate);
    }

    public boolean isInvoiceToDateDisplayed() {
        return driver.element().isElementDisplayed(invoiceToDate);
    }

    public boolean isPassengerNameDisplayed() {
        return driver.element().isElementDisplayed(passengerName);
    }

    public boolean isAgentNameDisplayed() {
        return driver.element().isElementDisplayed(agentNameDropdown);
    }

    public boolean isBookingReferenceDisplayed() {
        return driver.element().isElementDisplayed(bookingReference);
    }

    public boolean isSearchButtonDisplayed() {
        return driver.element().isElementDisplayed(searchButton);
    }

    public String getInvoiceFromDateRequiredMessage() {
        return driver.element().getText(invoiceFromDateRequiredMessage);
    }

    public String getInvoiceToDateRequiredMessage() {
        return driver.element().getText(invoiceToDateRequiredMessage);
    }

    public String getInvalidDateRangeMessage() {
        return driver.element().getText(invalidDateRangeMessage);
    }

    public boolean areTableHeadersDisplayed() {
        return isAnyElementDisplayed(tableHeaders);
    }

    public List<String> getTableHeaders() {
        waitForTableHeaders();

        List<String> headers = new ArrayList<>();
        List<WebElement> headerElements = driver.getDriver().findElements(tableHeaders);

        for (WebElement headerElement : headerElements) {
            if (headerElement.isDisplayed()) {
                headers.add(headerElement.getText().trim());
            }
        }

        return headers;
    }

    public boolean areTableRowsDisplayed() {
        return driver.element().isElementDisplayed(tableRows);
    }

    public int getTableRowsCount() {
        int rowsCount = 0;
        List<WebElement> rows = driver.getDriver().findElements(tableRows);

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
        return driver.element().isElementDisplayed(exportToExcelButton);
    }

    public boolean isExportToExcelButtonClickable() {
        return driver.element().isElementClickable(exportToExcelButton);
    }

    public boolean isNoRecordsMessageDisplayed() {
        return isAnyElementDisplayed(exactNoDataMessage) || isAnyElementDisplayed(noRecordsMessage);
    }

    public boolean hasNoTableRows() {
        return getTableRowsCount() == 0;
    }

    public String getToastMessage() {
        return driver.element().getText(toastMessage);
    }
}
