package PortalPages.MyQuotes;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.ArrayList;
import java.util.List;


public class MyQuotes_Page {

    private final SHAFT.GUI.WebDriver driver;

    // ==================== 1. Flight results ====================

    // Typed straight into the p-calendar input (format dd/MM/yyyy) instead of clicking through
    // the calendar - see enterDepartureDate().
    private final By Dpick_DepartureDate =
            By.xpath("//p-calendar[@formcontrolname='dateOfJourney']//input");

    private final By Lst_FlightCards = By.xpath("//ndc-flight-result-card");
    private final By Btn_SaveQuote =
            By.xpath("//button[contains(@class,'results-action-btn') and normalize-space()='Save Quote']");
    private final By Txt_SelectFlightValidation = By.cssSelector("span.results-action-validation");

    // ---------- Save Quote dialog ----------
    private final String saveQuoteDialog =
            "//div[@role='dialog'][.//span[contains(@class,'p-dialog-title') and normalize-space()='Save Quote']]";
    private final By Rbtn_NewUser = By.xpath(saveQuoteDialog + "//label[@for='id--Newuser']");
    private final By Txt_FirstName = By.xpath(saveQuoteDialog + "//input[@id='id-FirstName']");
    private final By Txt_LastName = By.xpath(saveQuoteDialog + "//input[@id='id-LastName']");
    private final By Txt_Email = By.xpath(saveQuoteDialog + "//input[@id='id-Email']");
    private final By Txt_Phone = By.xpath(saveQuoteDialog + "//input[@id='Phone']");
    private final By Btn_ConfirmSaveQuote = By.xpath(saveQuoteDialog + "//button[@type='submit']");

    // ---------- Quote Saved dialog ----------
    private final String quoteSavedDialog =
            "//div[@role='dialog'][.//span[normalize-space()='Quote Saved']]";
    private final By Txt_QuotationId = By.xpath(quoteSavedDialog + "//h4");
    private final By Btn_QuoteSavedOk =
            By.xpath(quoteSavedDialog + "//div[contains(@class,'p-dialog-footer')]//button[normalize-space()='Ok']");

    // ==================== 2. My Quotes ====================

    private final By Txt_QuotesNo = By.id("id-QuotesNo");
    private final By Txt_ClientName = By.id("id-Client/PassengerName");
    private final By Txt_EmailId = By.id("id-EmailID");
    private final By Lst_QuoteStatus = By.xpath("//input[@id='id-QuoteStatus']/ancestor::p-multiselect");
    private final By Btn_CloseStatusPanel = By.cssSelector(".p-multiselect-panel .p-multiselect-close");
    private final By Rbtn_TravelDate = By.cssSelector("label[for='id-Date-Traveldate']");
    private final By Dpick_FromDate = By.id("id-FromDate");
    private final By Dpick_ToDate = By.id("id-ToDate");
    private final By Btn_Search = By.xpath("//button[@type='submit' and normalize-space()='Search']");
    private final By Txt_QuoteStatusError =
            By.xpath("//input[@id='id-QuoteStatus']/ancestor::ndc-fg-input[1]//span[contains(@class,'fg-error')]");

    // The Select Flight dialog also renders a <table>, so the grid is scoped to tables outside dialogs.
    private final String grid = "//table[not(ancestor::div[@role='dialog'])]";
    private final By Lst_GridHeaders = By.xpath(grid + "//thead//th");

    // ---------- Reject dialog ----------
    private final By Lst_RejectRemarks =
            By.xpath("//div[@role='dialog']//input[@id='id-Remarks']/ancestor::p-dropdown");
    private final By Btn_Reject = By.xpath("//div[@role='dialog']//button[normalize-space()='Reject']");
    private final By Txt_RejectRemarksError =
            By.xpath("//div[@role='dialog']//input[@id='id-Remarks']/ancestor::ndc-fg-input[1]//span[contains(@class,'fg-error')]");
    private final By Btn_CloseDialog = By.xpath("//div[@role='dialog']//button[@aria-label='Close dialog']");
    private final By Txt_Toast = By.cssSelector(".toast-message");

    // ==================== 3. Select Flight dialog ====================

    private final String selectFlightDialog =
            "//div[@role='dialog'][.//span[contains(@class,'p-dialog-title') and normalize-space()='Select Flight']]";
    private final By Dlg_SelectFlight = By.xpath(selectFlightDialog);
    private final By Lst_SelectFlightRows = By.xpath(selectFlightDialog + "//tr[contains(@class,'flight-row')]");
    private final By Btn_CloseSelectFlight =
            By.xpath(selectFlightDialog + "//div[contains(@class,'p-dialog-footer')]//button[normalize-space()='Close']");

    // ==================== 4. Passenger Details ====================

    private final By Lst_TripChips = By.xpath("//ndc-passenger-details-chips//div[contains(@class,'custom-chip')]");
    // waitToBeReady() needs a locator matching ONE element; the page shows 4 chips
    // (MultipleElementsFoundException seen in the 2026-09-29 run), so the wait targets the first chip only.
    private final By Txt_FirstTripChip =
            By.xpath("(//ndc-passenger-details-chips//div[contains(@class,'custom-chip')])[1]");
    private final By Lnk_SegmentsDetails =
            By.xpath("//a[contains(@class,'p-accordion-header-link')][normalize-space()='Segments Details']");
    private final By Txt_DepartureAirport =
            By.xpath("(//div[contains(@class,'airport-info') and contains(@class,'departure')]//div[contains(@class,'airport-code')])[1]");
    private final By Txt_DepartureDateTime =
            By.xpath("(//div[contains(@class,'airport-info') and contains(@class,'departure')]//div[contains(@class,'airport-time')])[1]");
    private final By Txt_ArrivalAirport =
            By.xpath("(//div[contains(@class,'airport-info') and contains(@class,'arrival')]//div[contains(@class,'airport-code')])[last()]");
    private final By Txt_ArrivalDateTime =
            By.xpath("(//div[contains(@class,'airport-info') and contains(@class,'arrival')]//div[contains(@class,'airport-time')])[last()]");
    private final By Txt_CabinClass = By.xpath("(//span[contains(@class,'flight-class')])[1]");
    private final By Txt_TotalPrice = By.xpath("//ndc-total-price//div[contains(@class,'main-price')]");

    // ---------- Locator factories ----------

    private By flightCard(int cardNumber) {
        return By.xpath("(//ndc-flight-result-card)[" + cardNumber + "]");
    }

    private By cardPart(int cardNumber, String relativeXpath) {
        return By.xpath("(//ndc-flight-result-card)[" + cardNumber + "]" + relativeXpath);
    }

    private By statusOption(String status) {
        return By.xpath("//li[contains(@class,'p-multiselect-item') and @aria-label='" + status + "']");
    }

    private By remarkOption(String remark) {
        return By.xpath("//div[contains(@class,'p-dropdown-panel')]//li[@aria-label='" + remark + "']");
    }

    /** Grid row whose Quotes No cell starts with the given quotation id (the cell also shows "(OneWay)"). */
    private String gridRow(String quotationId) {
        return grid + "/tbody/tr[td[1][starts-with(normalize-space(),'" + quotationId + "')]]";
    }

    private By selectFlightCell(int rowNumber, String columnKey) {
        return By.xpath("(" + selectFlightDialog + "//tr[contains(@class,'flight-row')])[" + rowNumber + "]"
                + "/td[contains(@class,'quote-column--" + columnKey + "')]");
    }

    public MyQuotes_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    // ==================== Navigation ====================

    /** portalLoginUrl is the Portal_Url config value (".../auth/login"); the suffix is stripped to get the site root. */
    public MyQuotes_Page navigateToFlightSearch(String portalLoginUrl) {
        driver.browser().navigateToURL(siteRoot(portalLoginUrl) + "/booking/flight-search");
        driver.element().waitToBeReady(Dpick_DepartureDate);
        return this;
    }

    public MyQuotes_Page navigateToMyQuotes(String portalLoginUrl) {
        driver.browser().navigateToURL(siteRoot(portalLoginUrl) + "/myQuotes");
        driver.element().waitToBeReady(Txt_QuotesNo);
        return this;
    }

    private String siteRoot(String portalLoginUrl) {
        return portalLoginUrl.replaceFirst("/auth/login.*$", "");
    }

    // ==================== 1. Flight results - actions ====================

    public MyQuotes_Page enterDepartureDate(String date) {
        driver.element().type(Dpick_DepartureDate, date);
        driver.element().keyPress(Dpick_DepartureDate, Keys.TAB);
        return this;
    }

    /** Waits for the first flight card; the search can take a while (SHAFT default wait is 120 s here). */
    public MyQuotes_Page waitForFlightResults() {
        driver.element().waitToBeReady(flightCard(1));
        return this;
    }

    /** cardNumber is 1-based, in the order the results list shows them. */
    public MyQuotes_Page tickFlightCard(int cardNumber) {
        driver.element().click(cardPart(cardNumber,
                "//p-checkbox[@name='offer-quotation']//div[contains(@class,'p-checkbox-box')]"));
        return this;
    }

    public MyQuotes_Page clickSaveQuote() {
        driver.element().click(Btn_SaveQuote);
        return this;
    }

    public MyQuotes_Page selectNewUser() {
        driver.element().click(Rbtn_NewUser);
        return this;
    }

    public MyQuotes_Page enterFirstName(String firstName) {
        driver.element().type(Txt_FirstName, firstName);
        return this;
    }

    public MyQuotes_Page enterLastName(String lastName) {
        driver.element().type(Txt_LastName, lastName);
        return this;
    }

    public MyQuotes_Page enterEmail(String email) {
        driver.element().type(Txt_Email, email);
        return this;
    }

    /** Phone without the country code - the dialog's country picker defaults to Egypt (+20). */
    public MyQuotes_Page enterPhone(String phone) {
        driver.element().type(Txt_Phone, phone);
        return this;
    }

    public MyQuotes_Page clickConfirmSaveQuote() {
        driver.element().click(Btn_ConfirmSaveQuote);
        return this;
    }

    public MyQuotes_Page clickQuoteSavedOk() {
        driver.element().click(Btn_QuoteSavedOk);
        return this;
    }

    // ==================== 1. Flight results - getters ====================

    public int getFlightCardsCount() {
        return driver.element().getElementsCount(Lst_FlightCards);
    }

    /** e.g. "RX-9331 (RBD - )" - first segment of the card. */
    public String getCardFlightNumber(int cardNumber) {
        return driver.element().getText(cardPart(cardNumber, "//span[contains(@class,'seg-flight-rbd')]")).trim();
    }

    public String getCardAirline(int cardNumber) {
        return driver.element().getText(cardPart(cardNumber, "//span[contains(@class,'airline-name')]")).trim();
    }

    /** e.g. "20:30" */
    public String getCardDepartureTime(int cardNumber) {
        return driver.element().getText(cardPart(cardNumber,
                "//div[contains(@class,'time-col') and not(contains(@class,'time-col--arrival'))]"
                        + "//span[contains(@class,'time-main')]")).trim();
    }

    /** e.g. "23:05" */
    public String getCardArrivalTime(int cardNumber) {
        return driver.element().getText(cardPart(cardNumber,
                "//div[contains(@class,'time-col--arrival')]//span[contains(@class,'time-main')]")).trim();
    }

    /** e.g. "8,489" (currency is shown separately). */
    public String getCardPrice(int cardNumber) {
        return driver.element().getText(cardPart(cardNumber, "//span[contains(@class,'price-amount')]")).trim();
    }

    public String getSelectFlightValidationText() {
        return driver.element().getText(Txt_SelectFlightValidation).trim();
    }

    public boolean isSaveQuoteDialogDisplayed() {
        return driver.element().getElementsCount(By.xpath(saveQuoteDialog)) > 0;
    }

    public boolean isQuoteSavedDialogDisplayed() {
        return driver.element().getElementsCount(By.xpath(quoteSavedDialog)) > 0;
    }

    /** Field error under a Save Quote dialog input; fieldId is e.g. "id-FirstName", "id-Email", "Phone". */
    public String getSaveQuoteFieldError(String fieldId) {
        return driver.element().getText(By.xpath(saveQuoteDialog + "//input[@id='" + fieldId + "']"
                + "/ancestor::ndc-fg-input[1]//span[contains(@class,'fg-error')]")).trim();
    }

    /** The dialog shows "Quotation ID: <id>"; returns only <id>. */
    public String getQuotationId() {
        return driver.element().getText(Txt_QuotationId).replace("Quotation ID:", "").trim();
    }

    // ==================== 2. My Quotes - actions ====================

    public MyQuotes_Page enterQuotesNo(String quotationId) {
        driver.element().type(Txt_QuotesNo, quotationId);
        return this;
    }

    public MyQuotes_Page enterClientName(String clientName) {
        driver.element().type(Txt_ClientName, clientName);
        return this;
    }

    public MyQuotes_Page enterEmailId(String email) {
        driver.element().type(Txt_EmailId, email);
        return this;
    }

    /** status is one of Open / Booked / Reject / Expired. */
    public MyQuotes_Page selectQuoteStatus(String status) {
        driver.element().click(Lst_QuoteStatus);
        driver.element().click(statusOption(status));
        driver.element().click(Btn_CloseStatusPanel);
        return this;
    }

    /** Dates in dd/MM/yyyy. To Date is set first - see locator note 4. */
    public MyQuotes_Page setTravelDateRange(String fromDate, String toDate) {
        driver.element().click(Rbtn_TravelDate);
        driver.element().type(Dpick_ToDate, toDate);
        driver.element().keyPress(Dpick_ToDate, Keys.TAB);
        driver.element().type(Dpick_FromDate, fromDate);
        driver.element().keyPress(Dpick_FromDate, Keys.TAB);
        return this;
    }

    public MyQuotes_Page clickSearch() {
        driver.element().click(Btn_Search);
        return this;
    }

    /** Waits until the grid shows the quote; use after clickSearch() when the quote is expected. */
    public MyQuotes_Page waitForQuoteRow(String quotationId) {
        driver.element().waitToBeReady(By.xpath(gridRow(quotationId)));
        return this;
    }

    public MyQuotes_Page clickQuotesNoLink(String quotationId) {
        driver.element().click(By.xpath(gridRow(quotationId) + "/td[1]//a"));
        driver.element().waitToBeReady(By.xpath("(" + selectFlightDialog + "//tr[contains(@class,'flight-row')])[1]"));
        return this;
    }

    public MyQuotes_Page clickRejectIcon(String quotationId) {
        driver.element().click(By.xpath(gridRow(quotationId) + "//i[contains(@class,'pi-thumbs-down')]"));
        driver.element().waitToBeReady(Btn_Reject);
        return this;
    }

    public MyQuotes_Page selectRejectRemark(String remark) {
        driver.element().click(Lst_RejectRemarks);
        driver.element().click(remarkOption(remark));
        return this;
    }

    public MyQuotes_Page clickReject() {
        driver.element().click(Btn_Reject);
        return this;
    }

    public MyQuotes_Page closeDialog() {
        driver.element().click(Btn_CloseDialog);
        return this;
    }

    // ==================== 2. My Quotes - getters ====================

    public String getQuoteStatusErrorText() {
        return driver.element().getText(Txt_QuoteStatusError).trim();
    }

    public int getQuoteRowsCount(String quotationId) {
        return driver.element().getElementsCount(By.xpath(gridRow(quotationId)));
    }

    /** Cell text of the quote's grid row, by column header text (e.g. "Mobile No"). */
    public String getGridCellText(String quotationId, String columnName) {
        return driver.element().getText(
                By.xpath(gridRow(quotationId) + "/td[" + gridColumnIndex(columnName) + "]")).trim();
    }

    public boolean isQuotesNoALink(String quotationId) {
        return driver.element().getElementsCount(By.xpath(gridRow(quotationId) + "/td[1]//a")) > 0;
    }

    public boolean isRejectIconDisplayed(String quotationId) {
        return driver.element().getElementsCount(
                By.xpath(gridRow(quotationId) + "//i[contains(@class,'pi-thumbs-down')]")) > 0;
    }

    public String getRejectRemarksErrorText() {
        return driver.element().getText(Txt_RejectRemarksError).trim();
    }

    public boolean isRejectDialogDisplayed() {
        return driver.element().getElementsCount(Btn_Reject) > 0;
    }

    public String getToastText() {
        return driver.element().getText(Txt_Toast).trim();
    }

    private int gridColumnIndex(String columnName) {
        int headersCount = driver.element().getElementsCount(Lst_GridHeaders);
        for (int i = 1; i <= headersCount; i++) {
            String header = driver.element().getText(By.xpath("(" + grid + "//thead//th)[" + i + "]")).trim();
            if (header.equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("My Quotes grid has no column named '" + columnName + "'");
    }

    // ==================== 3. Select Flight dialog ====================

    public int getSelectFlightRowsCount() {
        return driver.element().getElementsCount(Lst_SelectFlightRows);
    }

    /**
     * columnKey is the column's CSS suffix: airline, flight, tripType, origin, destination, date,
     */
    public String getSelectFlightCell(int rowNumber, String columnKey) {
        return driver.element().getText(selectFlightCell(rowNumber, columnKey)).trim();
    }

    /** Clicking a row runs a fare check ("Checking fare availability...") and then opens Passenger Details. */
    public MyQuotes_Page clickSelectFlightRow(int rowNumber) {
        driver.element().click(By.xpath("(" + selectFlightDialog + "//tr[contains(@class,'flight-row')])[" + rowNumber + "]"));
        driver.element().waitToBeReady(Txt_FirstTripChip);
        return this;
    }

    public MyQuotes_Page closeSelectFlightDialog() {
        driver.element().click(Btn_CloseSelectFlight);
        return this;
    }

    public boolean isSelectFlightDialogDisplayed() {
        return driver.element().getElementsCount(Dlg_SelectFlight) > 0;
    }

    // ==================== 4. Passenger Details (read-only) ====================

    /** Opens "Segments Details" if it is collapsed - see locator note 5. */
    public MyQuotes_Page expandSegmentsDetails() {
        if ("false".equals(driver.element().getAttribute(Lnk_SegmentsDetails, "aria-expanded"))) {
            driver.element().click(Lnk_SegmentsDetails);
        }
        driver.element().waitToBeReady(Txt_DepartureAirport);
        return this;
    }

    /** e.g. ["OneWay", "Passengers: 1", "Trip Departure: Tue, 20 Oct, 2026", "Trip Arrival: ..."] */
    public List<String> getTripChips() {
        int chipsCount = driver.element().getElementsCount(Lst_TripChips);
        List<String> chips = new ArrayList<>();
        for (int i = 1; i <= chipsCount; i++) {
            chips.add(driver.element().getText(
                    By.xpath("(//ndc-passenger-details-chips//div[contains(@class,'custom-chip')])[" + i + "]")).trim());
        }
        return chips;
    }

    public String getDepartureAirport() {
        return driver.element().getText(Txt_DepartureAirport).trim();
    }

    /** e.g. "20:30 Tue, 20 Oct, 2026" */
    public String getDepartureDateTime() {
        return driver.element().getText(Txt_DepartureDateTime).trim();
    }

    public String getArrivalAirport() {
        return driver.element().getText(Txt_ArrivalAirport).trim();
    }

    public String getArrivalDateTime() {
        return driver.element().getText(Txt_ArrivalDateTime).trim();
    }

    public String getCabinClass() {
        return driver.element().getText(Txt_CabinClass).trim();
    }

    /** e.g. "8,489 EGP" */
    public String getTotalPrice() {
        return driver.element().getText(Txt_TotalPrice).trim();
    }

    public String getCurrentUrl() {
        return driver.browser().getCurrentURL();
    }
}
