package PortalPages.ImportPnr;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page object for the Portal "Import PNR" screen (route: /importPnr).
 *
 * Import PNR retrieves a booking that was created outside NDC (directly in a GDS such as
 * Galileo) so the logged-in agency can pay for it. The flow has two phases:
 *   1. Retrieve - PNR code + supplier + supplier credential -> Search. Read-only.
 *   2. Pay      - accept T&C -> Pay -> confirm dialog. Deducts from the agency wallet.
 *
 * Unlike the Admin equivalent there are no Branch/Agency/Agent selectors: the agency is
 * implicit from the logged-in session.
 *
 * Locator notes (all three verified live on staging92 - do not "simplify" them):
 *
 *   1. #id-Supplier and #id-Suppliercredential exist in the DOM but are NOT visible - they
 *      sit inside PrimeNG's .p-hidden-accessible wrapper. Clicking them times out. The
 *      visible .p-dropdown-label must be clicked instead.
 *
 *   2. The Terms & Conditions checkbox is the THIRD .p-checkbox on the page. Two email
 *      preference checkboxes render before it inside the Wallet payment panel, so an
 *      index-based or "first checkbox" locator silently toggles the wrong control and
 *      leaves T&C unaccepted. It is anchored on its label text here.
 *
 *   3. The wallet balance shown on this page is stale after payment. Read the balance from
 *      a freshly loaded page (e.g. the dashboard) when asserting a deduction.
 */
public class ImportPnr_Page {

    private final SHAFT.GUI.WebDriver driver;

    // ---------- Navigation ----------
    private final By Lnk_ImportPnr = By.cssSelector("a[href='/importPnr']");

    // ---------- Search form ----------
    private final By Txt_PnrCode = By.id("id-PNRCode");
    private final By Btn_Search = By.cssSelector("button[type='submit']");

    // Anchored on the SAME stable ids as the hidden accessibility inputs (see locator note 1),
    // then one short step sideways to the visible label span that actually receives clicks.
    // Simpler than matching on label text, and - unlike Admin's `//*[text()='Select Supplier ']`
    // - proven live to keep working after a value is selected, not just on the placeholder:
    //   - clicking the outer .p-dropdown container instead of this span was tried and tested
    //     live on staging92 on 2026-08-12: it does NOT open the panel, it just marks both
    //     fields "touched" and shows Required errors. Do not "simplify" to the container.
    //   - clicking this exact span was tested live both against the placeholder
    //     ("Select Supplier") and after Galileo was already selected ("Galileo") - both
    //     reopen the panel correctly.
    //
    // The values passed to selectSupplier()/selectCredential() below (Galileo/Sabre/Amadeus,
    // Live Egypt PCC/Galileo Live UAE, ...) are read from the test data JSON in the test
    // class - never hardcoded in this page object.
    private final By Ddl_Supplier =
            By.xpath("//input[@id='id-Supplier']/parent::div"
                    + "/following-sibling::span[contains(@class,'p-dropdown-label')]");
    private final By Ddl_Credential =
            By.xpath("//input[@id='id-Suppliercredential']/parent::div"
                    + "/following-sibling::span[contains(@class,'p-dropdown-label')]");

    // Each of the 3 form fields (PNR Code, Supplier, Supplier Credential) renders its own
    // span.fg-error, even when empty - a bare "span.fg-error" throws
    // MultipleElementsFoundException (confirmed live 2026-08-12: 3 matches). Scoped to the
    // PNR Code field's own span, since that is the only field these tests validate.
    private final By Txt_FieldError = By.xpath(
            "//input[@id='id-PNRCode']/ancestor::ndc-fg-input[1]//span[contains(@class,'fg-error')]");

    // ---------- Results ----------
    private final By Txt_StatusBadge = By.cssSelector(".status-badge");
    private final By Txt_TotalFare = By.cssSelector(".fare-total-amount");
    private final By Tbl_Travelers = By.cssSelector("table");
    private final By Txt_FlightDetailsHeading = By.xpath("//*[normalize-space()='Flight Details']");

    // ---------- Payment ----------
    // Anchored on the label text - see locator note 2.
    private final By Cbx_Terms =
            By.xpath("//*[normalize-space()='Terms & Conditions']"
                   + "/ancestor::div[1]//div[contains(@class,'p-checkbox-box')]");

    // The main Pay button, excluding the one inside the confirmation dialog.
    private final By Btn_Pay =
            By.xpath("//button[contains(@class,'pay-btn') and not(ancestor::*[contains(@class,'p-dialog')])]");

    private final By Dlg_ConfirmPay = By.cssSelector(".p-dialog");
    private final By Btn_ConfirmPay =
            By.xpath("//div[contains(@class,'p-dialog')]//button[.//span[normalize-space()='Pay']]");
    private final By Btn_CloseDialog =
            By.xpath("//*[contains(@class,'p-dialog-header-close')]");

    private final By Txt_SuccessMessage =
            By.xpath("//p[contains(normalize-space(.),'successfully Confirmed')]");
    private final By Txt_Toast = By.cssSelector(".p-toast-message");

    // ---------- Wallet ----------
    // The Portal renders the wallet widget TWICE - a live copy and a hidden duplicate
    // inside a second ".wallet-sidebar-section.hidden" (confirmed live 2026-08-12: a bare
    // ".balance-amount"/".outstanding-amount" throws MultipleElementsFoundException).
    // Scoped to the section that is NOT hidden.
    private final By Txt_WalletBalance =
            By.cssSelector(".wallet-sidebar-section:not(.hidden) .balance-amount");
    private final By Txt_Outstanding =
            By.cssSelector(".wallet-sidebar-section:not(.hidden) .outstanding-amount");

    /** Dropdown options expose aria-label, which makes an exact-match factory safe here. */
    private By dropdownOption(String optionText) {
        return By.cssSelector("li[aria-label='" + optionText + "']");
    }

    public ImportPnr_Page(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    // ==================== Navigation ====================

    /**
     * portalLoginUrl is the existing Portal_Url config value (e.g.
     * "http://192.168.1.92:9500/auth/login") - no separate "base URL" config key needed.
     * The "/auth/login" suffix is stripped to get the site root, then "/importPnr" is
     * appended.
     */
    public ImportPnr_Page navigateToImportPnrPage(String portalLoginUrl) {
        String siteRoot = portalLoginUrl.replaceFirst("/auth/login.*$", "");
        driver.browser().navigateToURL(siteRoot + "/importPnr");
        waitUntilLoaded();
        return this;
    }

    /**
     * Same as navigateToImportPnrPage(), but does NOT wait for the PNR Code field - use
     * when the destination might redirect elsewhere instead of rendering the form (e.g.
     * an expired-session check, where the app bounces to /auth/login and #id-PNRCode
     * never appears). Callers should confirm where they actually landed themselves.
     */
    public ImportPnr_Page navigateToImportPnrPageExpectingPossibleRedirect(String portalLoginUrl) {
        String siteRoot = portalLoginUrl.replaceFirst("/auth/login.*$", "");
        driver.browser().navigateToURL(siteRoot + "/importPnr");
        return this;
    }

    public ImportPnr_Page clickImportPnrInSideMenu() {
        driver.element().click(Lnk_ImportPnr);
        waitUntilLoaded();
        return this;
    }

    public ImportPnr_Page waitUntilLoaded() {
        driver.element().waitToBeReady(Txt_PnrCode);
        return this;
    }

    // ==================== Search form ====================

    public ImportPnr_Page enterPnrCode(String pnrCode) {
        driver.element().type(Txt_PnrCode, pnrCode);
        return this;
    }

    /** Types then blurs, so client-side validation fires without submitting. */
    public ImportPnr_Page enterPnrCodeAndBlur(String pnrCode) {
        driver.element().type(Txt_PnrCode, pnrCode);
        driver.element().keyPress(Txt_PnrCode, org.openqa.selenium.Keys.TAB);
        return this;
    }

    /** supplierName is read from the test data JSON (e.g. testData.getTestData("supplier")). */
    public ImportPnr_Page selectSupplier(String supplierName) {
        driver.element().click(Ddl_Supplier);
        driver.element().click(dropdownOption(supplierName));
        return this;
    }

    /** credentialName is read from the test data JSON (e.g. testData.getTestData("supplierCredential")). */
    public ImportPnr_Page selectCredential(String credentialName) {
        driver.element().click(Ddl_Credential);
        driver.element().click(dropdownOption(credentialName));
        return this;
    }

    public ImportPnr_Page openSupplierDropdown() {
        driver.element().click(Ddl_Supplier);
        return this;
    }

    public ImportPnr_Page openCredentialDropdown() {
        driver.element().click(Ddl_Credential);
        return this;
    }

    public ImportPnr_Page clickSearch() {
        driver.element().click(Btn_Search);
        return this;
    }

    // ==================== Payment ====================

    public ImportPnr_Page acceptTermsAndConditions() {
        driver.element().click(Cbx_Terms);
        return this;
    }

    public ImportPnr_Page clickPay() {
        driver.element().click(Btn_Pay);
        return this;
    }

    public ImportPnr_Page confirmPayInDialog() {
        driver.element().waitToBeReady(Btn_ConfirmPay);
        driver.element().click(Btn_ConfirmPay);
        return this;
    }

    public ImportPnr_Page closeConfirmDialog() {
        driver.element().click(Btn_CloseDialog);
        return this;
    }

    // ==================== Getters (values, never elements) ====================

    public String getFieldErrorText() {
        return driver.element().getText(Txt_FieldError);
    }

    public String getStatusText() {
        return driver.element().getText(Txt_StatusBadge);
    }

    public String getTotalFareText() {
        return driver.element().getText(Txt_TotalFare);
    }

    public String getSuccessMessageText() {
        return driver.element().getText(Txt_SuccessMessage);
    }

    public String getToastText() {
        return driver.element().getText(Txt_Toast);
    }

    public String getWalletBalanceText() {
        return driver.element().getText(Txt_WalletBalance);
    }

    public String getOutstandingText() {
        return driver.element().getText(Txt_Outstanding);
    }

    public String getConfirmDialogText() {
        return driver.element().getText(Dlg_ConfirmPay);
    }

    public String getCurrentUrl() {
        return driver.getDriver().getCurrentUrl();
    }

    /** Parses "6,826 EGP" -> 6826.0 so wallet deltas can be asserted numerically. */
    public static double parseAmount(String amountText) {
        if (amountText == null) {
            return 0d;
        }
        String digits = amountText.replaceAll("[^0-9.]", "");
        return digits.isEmpty() ? 0d : Double.parseDouble(digits);
    }

    public double getTotalFareAmount() {
        return parseAmount(getTotalFareText());
    }

    public double getWalletBalanceAmount() {
        return parseAmount(getWalletBalanceText());
    }

    // ==================== State checks ====================

    public boolean isPayButtonEnabled() {
        return driver.element().isElementClickable(Btn_Pay);
    }

    public boolean isPayButtonDisplayed() {
        return elementCount(Btn_Pay) > 0;
    }

    public boolean areResultsDisplayed() {
        return elementCount(Txt_TotalFare) > 0;
    }

    public boolean isStatusDisplayed() {
        return elementCount(Txt_StatusBadge) > 0;
    }

    public boolean isFieldErrorDisplayed() {
        return elementCount(Txt_FieldError) > 0;
    }

    public boolean isConfirmDialogDisplayed() {
        return elementCount(Dlg_ConfirmPay) > 0;
    }

    public boolean isTravelersTableDisplayed() {
        return elementCount(Tbl_Travelers) > 0;
    }

    public boolean isFlightDetailsDisplayed() {
        return elementCount(Txt_FlightDetailsHeading) > 0;
    }

    public boolean isToastDisplayed() {
        return elementCount(Txt_Toast) > 0;
    }

    public boolean isPnrCodeInputInvalid() {
        return driver.getDriver().findElement(Txt_PnrCode)
                .getAttribute("class").contains("ng-invalid");
    }

    public String getPnrCodeValue() {
        return driver.getDriver().findElement(Txt_PnrCode).getAttribute("value");
    }

    public String getPnrCodeMaxLength() {
        return driver.getDriver().findElement(Txt_PnrCode).getAttribute("maxlength");
    }

    public String getSupplierLabelText() {
        return driver.element().getText(Ddl_Supplier);
    }

    public String getCredentialLabelText() {
        return driver.element().getText(Ddl_Credential);
    }

    /** Options currently rendered in the open dropdown panel. */
    public List<String> getOpenDropdownOptions() {
        return driver.getDriver()
                .findElements(By.cssSelector("li[role='option'], .p-dropdown-item"))
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    // ==================== Internals ====================

    private int elementCount(By locator) {
        return driver.getDriver().findElements(locator).size();
    }
}
