package AdminPages.BookingMidOffice.MyQuotes;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyQuotesPage {
    SHAFT.GUI.WebDriver driver ;
    private final By branchNameDropdown = By.xpath("//input[@id='id-BranchName']/ancestor::div[contains(@class,'p-dropdown')]");
    private final By agencyNameDropdown = By.xpath("//input[@id='id-AgencyName']/ancestor::div[contains(@class,'p-dropdown')]");
    private final By quotesNoInput = By.id("id-QuotesNo.");
    private final By nameInput = By.id("id-Name");
    private final By emailInput = By.id("id-EmailId");
    private final By creationDateRadioButton = By.xpath("//input[@id='id--CreationDate']/ancestor::div[contains(@class,'p-radiobutton')]");
    private final By travelDateRadioButton = By.xpath("//input[@id='id--TravelDate']/ancestor::div[contains(@class,'p-radiobutton')]//div[contains(@class,'p-radiobutton-box')]");
    private final By fromDate = By.id("id-From");
    private final By toDate = By.id("id-To");
    private final By quoteStatus = By.xpath("//div[contains(@class,'p-multiselect') and .//input[@id='id-QuoteStatus']]");
    private final By searchBtn = By.xpath("//button[.//span[text()='Search']]");
    private final By branchClearIcon = By.xpath("//input[@id='id-BranchName']/ancestor::p-dropdown//i[contains(@class,'p-dropdown-clear-icon')]");
    private final By agencyClearIcon = By.xpath("//input[@id='id-AgencyName']/ancestor::p-dropdown//i[contains(@class,'p-dropdown-clear-icon')]");
    private final By statusClearIcon = By.xpath("//p-multiselect//i[contains(@class,'p-multiselect-clear-icon')]");
    private final By remarksDropdown = By.xpath("//label[contains(.,'Remarks')]/following::p-dropdown[1]");
    private final By DialogPopupForChangingStatus = By.xpath("//div[contains(@class,'p-dialog-content')]");
    private final By dropdownPanel = By.xpath("//div[contains(@class,'p-dropdown-panel') and not(contains(@style,'none'))]");
    private final By dropdownOptions = By.xpath("//div[contains(@class,'p-dropdown-panel')]//li[@role='option']");
    private final By rejectBtn = By.xpath("//button[.//span[text()='Reject']]");
    private final By popupCloseBtn = By.xpath("//div[contains(@class,'p-dialog') and @role='dialog']" + "//button[contains(@class,'p-dialog-header-close')]");
    public void ElementClick(By by){
        driver.element().click(by);
    }
    public void ElementType(By by,String Value){
        driver.element().type(by,Value);
    }
    public MyQuotesPage(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }
    public MyQuotesPage SelectBranch(String Branch) {
        ElementClick(branchNameDropdown);

        ElementClick(
                By.xpath("//li[@role='option']//span[normalize-space()='" + Branch + "']")
        );

        return new MyQuotesPage(driver);
    }
    public MyQuotesPage SelectStatus(String Status) {
        ElementClick(quoteStatus);

        ElementClick(
                By.xpath("//li[contains(@class,'p-multiselect-item') and @aria-label='" + Status + "']")
        );

        ElementClick(quoteStatus);
        return new MyQuotesPage(driver);
    }
    public MyQuotesPage QuoteSearch(){
        ElementClick(searchBtn);
        return new MyQuotesPage(driver);
    }
    public MyQuotesPage SelectAgency(String Agency) {
        ElementClick(agencyNameDropdown);

        ElementClick(
                By.xpath("//li[@role='option']//span[normalize-space()='" + Agency + "']")
        );

        return new MyQuotesPage(driver);
    }
    public boolean VerifyThatQuoteDisplayed(String displayedStringInGrid) {

        return driver.element().getElementsCount(
                By.xpath("//tbody/tr/td[contains(normalize-space(),'" + displayedStringInGrid + "')]")
        ) > 0;
    }
    public boolean isQuoteGridDisplayed() {
        return driver.element().getElementsCount(
                By.xpath("//tbody/tr")
        ) > 0;
    }
    public boolean VerifyThatOnlySelectedStatusDataAreDisplayed(String columnName, String Status){
        int columnIndex = getColumnIndex(columnName);

        int rowsCount = driver.element().getElementsCount(By.xpath("//tbody/tr"));

        for (int i = 1; i <= rowsCount; i++) {

            String actualValue = driver.element().getText(
                    By.xpath("//tbody/tr[" + i + "]/td[" + columnIndex + "]")
            ).trim();

            if (!actualValue.equalsIgnoreCase(Status)) {
                return false;
            }
        }

        return true;
    }
    public boolean VerifyThatOnlySelectedAgencyDataAreDisplayed(
            String columnName,
            String expectedValue) {

        int columnIndex = getColumnIndex(columnName);

        // wait for table body
        driver.element().waitToBeReady(
                By.xpath("//table//tbody")
        );

        List<WebElement> visibleRows = driver.getDriver().findElements(
                By.xpath("//table//tbody//tr[td]")
        );

        if (visibleRows.isEmpty()) {
            return false;
        }

        int visibleRowIndex = 0;

        for (WebElement row : visibleRows) {

            try {

                if (!row.isDisplayed()) {
                    continue;
                }

                visibleRowIndex++;

                By cellLocator = By.xpath(
                        "(//table//tbody//tr[td])[" +
                                visibleRowIndex +
                                "]/td[" + columnIndex + "]"
                );

                String actualValue = driver.element()
                        .getText(cellLocator)
                        .trim();

                if (!actualValue.equalsIgnoreCase(expectedValue)) {
                    return false;
                }

            } catch (Exception ignored) {
            }
        }

        return true;
    }
    public boolean VerifyThatQuotePopupDisplayed(String quoteNumber){
        ElementClick(
                By.xpath("//tbody/tr/td[1]//a[contains(normalize-space(),'" + quoteNumber + "')]")
        );
        // Verify popup is displayed
        boolean isPopupDisplayed = driver.element().isElementDisplayed(
                By.cssSelector(".p-dialog-content")
        );

        // Verify table contains at least one row with data
        int rowsCount = driver.element().getElementsCount(
                By.xpath("//div[contains(@class,'p-dialog-content')]//tbody/tr")
        );

        driver.element().waitToBeReady(popupCloseBtn);
        ElementClick(popupCloseBtn);

        return isPopupDisplayed && rowsCount > 0;
    }
    public boolean VerifyThatOnlyQuotesWithinCreationOrTravelDateAreDisplayed(
            String ColumnName,
            String FromDate,
            String ToDate) {

        int columnIndex = getColumnIndex(ColumnName);

        // wait until table rows appear
        driver.element().waitToBeReady(By.xpath("//tbody/tr"));

        int rowsCount = driver.element().getElementsCount(
                By.xpath("//tbody/tr")
        );

        // no rows returned
        if (rowsCount == 0) {
            return false;
        }

        DateTimeFormatter inputFormatter =
                DateTimeFormatter.ofPattern("MM/dd/yyyy");

        DateTimeFormatter tableFormatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate fromDate = LocalDate.parse(FromDate, inputFormatter);
        LocalDate toDate = LocalDate.parse(ToDate, inputFormatter);

        for (int i = 1; i <= rowsCount; i++) {

            By cellLocator = By.xpath(
                    "//tbody/tr[" + i + "]/td[" + columnIndex + "]"
            );

            if (driver.element().getElementsCount(cellLocator) == 0) {
                return false;
            }

            String actualDateText =
                    driver.element().getText(cellLocator).trim();

            LocalDate actualDate =
                    LocalDate.parse(actualDateText, tableFormatter);

            if (actualDate.isBefore(fromDate)
                    || actualDate.isAfter(toDate)) {

                return false;
            }
        }

        return true;
    }
    public int getColumnIndex(String columnName) {

        int headersCount = driver.element().getElementsCount(By.xpath("//table//thead//th"));

        for (int i = 1; i <= headersCount; i++) {

            String headerText = driver.element().getText(
                    By.xpath("(//table//thead//th)[" + i + "]")
            ).trim();

            if (headerText.equalsIgnoreCase(columnName)) {
                return i;
            }
        }

        throw new RuntimeException("Column not found: " + columnName);
    }

    public MyQuotesPage SearchByBranchAndQuoteNumber(String BranchName,String QuoteNumber){
        ClearFields();
        SelectBranch(BranchName);
        ElementType(quotesNoInput,QuoteNumber);
        QuoteSearch();
        return new MyQuotesPage(driver);
    }
    public MyQuotesPage SearchByBranchAndPassengerName(String BranchName,String PassengerName){
        ClearFields();
        SelectBranch(BranchName);
        ElementType(nameInput,PassengerName);
        QuoteSearch();
        return new MyQuotesPage(driver);
    }
    public MyQuotesPage SearchByBranchAndPassengerEmail(String BranchName,String PassengerEmail){
        ClearFields();
        SelectBranch(BranchName);
        ElementType(emailInput,PassengerEmail);
        QuoteSearch();
        return new MyQuotesPage(driver);
    }
    public MyQuotesPage SearchByBranchAndStatus(String BranchName,String Status){
        ClearFields();
        SelectBranch(BranchName);
        SelectStatus(Status);
        QuoteSearch();
        return new MyQuotesPage(driver);
    }
    public MyQuotesPage SearchByBranchAndCreationData(String BranchName,String FromDate,String ToDate){
        ClearFields();
        SelectBranch(BranchName);
        ElementClick(creationDateRadioButton);
        ElementType(fromDate,FromDate);
        ElementType(toDate,ToDate);
        QuoteSearch();
        return new MyQuotesPage(driver);
    }
    public MyQuotesPage SearchByBranchAndTravelData(String BranchName,String FromDate,String ToDate){
        ClearFields();
        SelectBranch(BranchName);
        ElementClick(travelDateRadioButton);
        ElementType(fromDate,FromDate);
        ElementType(toDate,ToDate);
        QuoteSearch();
        return new MyQuotesPage(driver);
    }
    public MyQuotesPage SearchByBranchAndAgentName(String BranchName,String AgentName){
        ClearFields();
        SelectBranch(BranchName);
        SelectAgency(AgentName);
        QuoteSearch();
        return new MyQuotesPage(driver);
    }
    public MyQuotesPage SearchByAllFiltersWithCreationDate(String BranchName,
                                           String AgentName,
                                           String QuoteID,
                                           String QuotePassangerName,
                                           String QuoteEmailID,
                                           String CreationDateFrom,
                                           String CreationDateTo,
                                           String QuoteStatus)
    {
        ClearFields();
        SelectBranch(BranchName);
        SelectAgency(AgentName);
        ElementType(quotesNoInput,QuoteID);
        ElementType(nameInput,QuotePassangerName);
        ElementType(emailInput,QuoteEmailID);
        ElementClick(creationDateRadioButton);
        ElementType(fromDate,CreationDateFrom);
        ElementType(toDate,CreationDateTo);
        SelectStatus(QuoteStatus);
        QuoteSearch();
        return new MyQuotesPage(driver);
    }
    public MyQuotesPage SearchByAllFiltersWithTravelDate(String BranchName,
                                                           String AgentName,
                                                           String QuoteID,
                                                           String QuotePassangerName,
                                                           String QuoteEmailID,
                                                           String CreationDateFrom,
                                                           String CreationDateTo,
                                                           String QuoteStatus)
    {
        ClearFields();
        SelectBranch(BranchName);
        SelectAgency(AgentName);
        ElementType(quotesNoInput,QuoteID);
        ElementType(nameInput,QuotePassangerName);
        ElementType(emailInput,QuoteEmailID);
        ElementClick(travelDateRadioButton);
        ElementType(fromDate,CreationDateFrom);
        ElementType(toDate,CreationDateTo);
        SelectStatus(QuoteStatus);
        QuoteSearch();
        return new MyQuotesPage(driver);
    }
    public void clearCalendar(By locator) {

        WebElement element = driver.getDriver().findElement(locator);

        ((JavascriptExecutor) driver.getDriver()).executeScript(
                "arguments[0].value='';" +
                        "arguments[0].dispatchEvent(new Event('input'));" +
                        "arguments[0].dispatchEvent(new Event('change'));",
                element
        );
    }
    public void clearIfSelected(By clearIcon) {
        List<WebElement> icons = driver.getDriver().findElements(clearIcon);

        if (!icons.isEmpty() && icons.get(0).isDisplayed()) {
            icons.get(0).click();
        }
    }
    public void ClearFields(){
        // close any opened overlay/dropdown
        driver.element().click(By.xpath("//body"));
        driver.element().clear(quotesNoInput);
        driver.element().clear(nameInput);
        driver.element().clear(emailInput);
        clearCalendar(fromDate);
        clearCalendar(toDate);
        clearIfSelected(branchClearIcon);
        clearIfSelected(agencyClearIcon);
        clearIfSelected(statusClearIcon);
    }
    public MyQuotesPage RejectQuote(String QuoteID) {

        By rejectIcon = By.xpath(
                "//tbody//tr[.//a[contains(normalize-space(),'" + QuoteID + "')]]//i[contains(@class,'pi-thumbs-down')]"
        );
        driver.element().waitToBeReady(rejectIcon);
        ElementClick(rejectIcon);

        driver.element().waitToBeReady(DialogPopupForChangingStatus);

        ElementClick(remarksDropdown);

        // wait ONLY for panel (single element)
        driver.element().waitToBeReady(dropdownPanel);

        // click first option safely
        driver.getDriver()
                .findElements(dropdownOptions)
                .get(0)
                .click();

        ElementClick(rejectBtn);

        return this;
    }
    public boolean ValidateQuoteStatus(String QuoteID,
                                       String StatusColumnName,
                                       String expectedStatus) {

        int statusIndex = getColumnIndex(StatusColumnName);

        By statusCell = By.xpath(
                "//tbody//tr[td[contains(normalize-space(),'" + QuoteID + "')]]/td[" + statusIndex + "]"
        );

        driver.element().waitToBeReady(statusCell);

        driver.assertThat()
                .element(statusCell)
                .text()
                .isEqualTo(expectedStatus)
                .perform();

        return true;
    }
}
