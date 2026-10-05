package PortalPages.BookingMidOffice.Refund;

import AdminPages.Reports.LedgerReport.LedgerReportDetails_Page;
import AdminPages.Reports.Reports_Common;
import AdminPages.Settings.AdminSettings.AdminSettings_Page;
import AdminPages.Settings.AdminSettings.Setting_Common;
import PortalPages.BookingMidOffice.Booking_Common;
import PortalPages.BookingMidOffice.SearchBooking.SearchBooking_Page;
import PortalPages.SideMenu;
import AdminPages.Login.LogIn_Page;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.DataUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Portal (agency) equivalent of AdminPages.BookingMidOffice.Refund.RefundPage. Portal has no
// lock/take-control step -- the agent clicks Online Refund directly -- and additionally has to
// cross-check the AGENCY_AUTO_REFUND_ENABLED admin setting and (when the refund lands as
// "Pending") drive the approval from a second, admin-logged-in browser tab.
//
// Every itinerary/refund-popup/refund-details locator below is a best-effort copy of Admin's
// (same underlying component library, confirmed by other existing Portal pages reusing the same
// PrimeNG class names) and is NOT verified against the live Portal DOM -- there is no browser
// automation available in this environment. TODO: confirm/replace against the real app.
public class RefundPage {
    // The popup text sometimes comes back with its line breaks collapsed ("Net Refundable Amount21,558.00 EGP"),
    // so an amount may directly follow a letter -- only refuse to start mid-number. The possessive digit run
    // stops backtracking into partial matches inside alphanumeric codes (e.g. "ZlC20y").
    private static final Pattern MONEY_PATTERN = Pattern.compile("(?<![\\d.,])[-+]?\\d[\\d,]*+(?:\\.\\d{1,2})?(?![A-Za-z])");

    private final SHAFT.GUI.WebDriver driver;
    private final SHAFT.TestData.JSON portalRefundData;

    private String portalWindowHandle;
    private String adminWindowHandle;

    // TODO: confirm "My Bookings" result-row / itinerary locators against the real Portal DOM.
    // Same shape as Admin's locator, but index 1 not 2 -- Portal's results table has no leading
    // branch column (an agency only ever sees its own branch), so Order Id (the clickable link)
    // is the 1st td here instead of the 2nd (confirmed live).
    private final By firstBookingResult = By.xpath("(//td[@class='ng-star-inserted'])[1]//a");
    private final By expandPassengerButtons = By.xpath("(//i[@class='pi pi-caret-down chevron-icon'])[1]");
    private final By refundButton = By.xpath("(//button[normalize-space()='Online Refund'])[1]");
    private final By refundDialog = By.xpath("(//section[@class='detail-card'])[2]");
    private final By refundDialogBookingReference = By.xpath("(//div[@class='detail-value detail-value--link'])[1]");
    private final By checkBox = By.xpath("(//div[@class='p-checkbox-box'])[1]");
    private final By refundSubmitButton = By.xpath("(//button[normalize-space()='Confirm Refund'])[1]");
    private final By doneButton = By.xpath("(//span[normalize-space()='Done'])[1]");
    private static final String REFUND_DETAILS_GRID_XPATH =
            "//div[@class='details-grid ng-star-inserted'][.//p[normalize-space()='Refund Id']]";
    private final By refundDetailsCard = By.xpath(REFUND_DETAILS_GRID_XPATH);
    private final By refundDetailsReference = By.xpath("(" + REFUND_DETAILS_GRID_XPATH + "//p)[2]");
    private final By refundDetailsCancelledAmount = By.xpath("(" + REFUND_DETAILS_GRID_XPATH + "//p)[4]");
    private final By refundDetailsStatus = By.xpath("(" + REFUND_DETAILS_GRID_XPATH + "//p)[6]");
    private final By refundDetailsAmount = By.xpath("(" + REFUND_DETAILS_GRID_XPATH + "//p)[8]");
    // Confirmed live: a 5th field, "Remarks", is appended to this same grid only once the request
    // has been rejected by Admin -- it holds exactly the remark text Admin typed in the reject
    // modal. Not present at all on a Pending/Refunded row.
    private final By refundDetailsRemarks = By.xpath("(" + REFUND_DETAILS_GRID_XPATH + "//p)[10]");

    public RefundPage(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
        this.portalRefundData = new SHAFT.TestData.JSON("PortalRefund.json");
    }

    public RefundPage openBookingDetails(String bookingReference) throws InterruptedException {
        new Booking_Common(driver).clickMyBookings();
        // Unlike Admin's search page, Portal's "Search Booking" page has no Flight/Booking module
        // tabs at all (confirmed live) -- agencies only ever search flight bookings, so there's
        // nothing to select here.
        new SearchBooking_Page(driver)
                .SelectCurrentStartDate()
                .SelectCurrentEndDate()
                .EnterBookingReference(bookingReference)
                .ClickSearch();
        return this;
    }

    public RefundPage selectFirstBooking() {
        driver.element().click(firstBookingResult);
        return this;
    }

    public RefundPage expandPassengerDetails() {
        driver.element().click(expandPassengerButtons);
        return this;
    }

    public RefundPage openRefundPopup() {
        driver.element().click(refundButton);
        return this;
    }

    public String getRefundPopupData() {
        return driver.element().getText(refundDialog);
    }

    public String getRefundPopupReference() {
        return driver.element().getText(refundDialogBookingReference);
    }

    public RefundPage assertPopupReference(String bookingReference) {
        String popUpReference = getRefundPopupReference();
        Assert.assertEquals(bookingReference, popUpReference, "The References are not the same");
        return this;
    }

    public RefundPage assertPopupCalculation() {
        List<BigDecimal> amounts = extractAmounts(getRefundPopupData());

        Assert.assertTrue(
                amounts.size() >= 2,
                "Refund popup does not contain enough amounts for a calculation"
        );

        BigDecimal displayedTotal = amounts.get(amounts.size() - 1);

        BigDecimal calculatedTotal = amounts.get(0);
        for (int index = 1; index < amounts.size() - 1; index++) {
            calculatedTotal = calculatedTotal.subtract(amounts.get(index));
        }

        Assert.assertEquals(
                calculatedTotal.setScale(2, RoundingMode.HALF_UP),
                displayedTotal.setScale(2, RoundingMode.HALF_UP),
                "Refund popup total is not equal to the subtraction of its displayed amounts"
        );

        return this;
    }

    public RefundPage submitRefundRequest() {
        driver.element().click(checkBox);
        driver.element().click(refundSubmitButton);
        driver.element().click(doneButton);
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(90))
                .until(ExpectedConditions.presenceOfElementLocated(refundDetailsCard));
        return this;
    }

    public String getRefundStatusFromDetails() {
        return driver.element().getText(refundDetailsStatus).trim();
    }

    public String getRefundDetailsReference() {
        return driver.element().getText(refundDetailsReference);
    }

    public String getRefundDetailsCancelledAmount() {
        return driver.element().getText(refundDetailsCancelledAmount);
    }

    public String getRefundDetailsAmount() {
        return driver.element().getText(refundDetailsAmount);
    }

    public String getRefundDetailsRemarks() {
        return driver.element().getText(refundDetailsRemarks);
    }

    public RefundPage assertRefundDetailsRemarks(String expectedRemark) {
        Assert.assertEquals(getRefundDetailsRemarks().trim(), expectedRemark,
                "Refund details section does not display Admin's rejection remark");
        return this;
    }

    public RefundPage assertRefundDetailsSection(String popupData) {
        List<BigDecimal> popupAmounts = extractAmounts(popupData);
        Assert.assertTrue(popupAmounts.size() >= 2,
                "Refund popup does not contain enough amounts to compare against the refund details section");

        // Popup breakdown is always: Gross, ...deductions..., Cancellation Charge, Net.
        BigDecimal expectedCancelledAmount = popupAmounts.get(popupAmounts.size() - 2);
        BigDecimal expectedRefundAmount = popupAmounts.get(popupAmounts.size() - 1);

        String refundId = getRefundDetailsReference();
        Assert.assertFalse(refundId.trim().isEmpty(), "Refund details section does not display a Refund Id");

        BigDecimal cancelledAmount = normalizeAmountValue(getRefundDetailsCancelledAmount());
        Assert.assertEquals(cancelledAmount.setScale(2, RoundingMode.HALF_UP), expectedCancelledAmount.setScale(2, RoundingMode.HALF_UP),
                "Refund details section cancelled amount does not match the popup's original amount");

        BigDecimal refundAmount = normalizeAmountValue(getRefundDetailsAmount());
        Assert.assertEquals(refundAmount.setScale(2, RoundingMode.HALF_UP), expectedRefundAmount.setScale(2, RoundingMode.HALF_UP),
                "Refund details section refund amount does not match the popup total");

        return this;
    }

    public RefundPage refreshAndGetRefundStatus() {
        switchToPortalTab();
        driver.browser().refreshCurrentPage();
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(30))
                .until(ExpectedConditions.presenceOfElementLocated(refundDetailsCard));
        return this;
    }

    // Selects an agency in the AR Ledger report's "Agency Name" filter (id-AgencyName) while on
    // the admin tab. Kept here (Portal-only) rather than added to AdminPages' own
    // LedgerReportDetails_Page -- its existing Lst_AgencyName(String) is unused/commented-out in
    // Admin's own tests and calls driver.element().select() on what is actually a PrimeNG
    // p-dropdown, not a native <select>. This instead reuses the same
    // click-dropdown/type-filter/click-option shape as LedgerReportDetails_Page's own (working)
    // Lst_BranchName, just retargeted at the Agency field.
    private final By agencyDropdownInLedgerReport = By.xpath("//p-dropdown[.//input[@id='id-AgencyName']]");

    public RefundPage selectAgencyInLedgerReport(String agency) {
        driver.element().click(agencyDropdownInLedgerReport);
        driver.element().type(By.xpath("//input[contains(@class,'p-dropdown-filter')]"), agency);
        driver.element().click(By.xpath("(//li[contains(@aria-label,'" + agency + "')])[1]"));
        return this;
    }

    // --- Tab management -------------------------------------------------
    // Nothing like this exists elsewhere in the framework yet; only this cross-tab flow needs it,
    // so it's scoped to this page rather than pulled out into a shared helper.

    public RefundPage openNewAdminTabAndLogin() {
        portalWindowHandle = driver.getDriver().getWindowHandle();
        driver.getDriver().switchTo().newWindow(WindowType.TAB);
        adminWindowHandle = driver.getDriver().getWindowHandle();
        driver.browser().navigateToURL(DataUtils.get("baseURL"));
        new LogIn_Page(driver).AdminLogin();
        return this;
    }

    public RefundPage switchToAdminTab() {
        driver.getDriver().switchTo().window(adminWindowHandle);
        return this;
    }

    public RefundPage switchToPortalTab() {
        driver.getDriver().switchTo().window(portalWindowHandle);
        return this;
    }

    public RefundPage closeAdminTab() {
        switchToAdminTab();
        // A just-triggered action (e.g. rejecting a refund request) can leave a success toast
        // floating over the top-right profile icon, intercepting the logout click (confirmed
        // live) -- same class of issue as the chat-widget fix in Booking's SearchBookingBranch.
        hideToasts();
        // Admin enforces a single active session per account -- logging out (not just closing the
        // tab) is required so the next test's openNewAdminTabAndLogin() isn't blocked by this
        // session still being considered active.
        new LogIn_Page(driver).ClickOnLogOuTButton();
        driver.getDriver().close();
        switchToPortalTab();
        return this;
    }

    private void hideToasts() {
        ((JavascriptExecutor) driver.getDriver()).executeScript(
                "document.querySelectorAll('.ngx-toastr, .toast-container').forEach(element => element.style.display = 'none');"
        );
    }

    // --- Full refund flows -----------------------------------------------
    // Moved here from Refund_TC so the test class only holds @Test methods; these orchestrate the
    // whole booking-agnostic refund journey (Portal submission -> Admin cross-check/action ->
    // Portal reflecting the final state) for any booking reference already created by the caller.

    public void runRefundFlow(String bookingReference) throws Exception {
        openBookingDetails(bookingReference)
                .selectFirstBooking()
                .expandPassengerDetails()
                .openRefundPopup();
        String popupData = getRefundPopupData();

        assertPopupReference(bookingReference)
                .assertPopupCalculation()
                .submitRefundRequest()
                .assertRefundDetailsSection(popupData);
        String status = getRefundStatusFromDetails();

        // Captured on the Portal tab, before ever switching to Admin, so the "after" reading
        // (taken once we're back on Portal post-approval) can be checked against it.
        String agencyName = new SideMenu(driver).getAgencyName();
        BigDecimal walletBalanceBeforeApproval = new SideMenu(driver).getWalletBalance();

        openNewAdminTabAndLogin();
        new Setting_Common(driver).clickSetting().clickAdminSetting();
        String autoRefundSetting = new AdminSettings_Page(driver).findSettingValue("AGENCY_AUTO_REFUND_ENABLED");

        String branch = portalRefundData.getTestData("agencyBranch");

        if (status.equals(portalRefundData.getTestData("refundStatusPending"))) {
            Assert.assertEquals(autoRefundSetting, portalRefundData.getTestData("autoRefundDisabledValue"),
                    "AGENCY_AUTO_REFUND_ENABLED should be 0 when the portal refund came back Pending");

            AdminPages.BookingMidOffice.Refund.RefundPage adminRefundPage = new AdminPages.BookingMidOffice.Refund.RefundPage(driver);
            adminRefundPage.goToRefundedRequestsFromFreshLogin()
                    .searchRefundedRequestUnderReview(branch)
                    .assertRefundedRequest(bookingReference, popupData,
                            portalRefundData.getTestData("requestTypeUnderReview"),
                            portalRefundData.getTestData("refundStatusPendingForApprove"))
                    .approvePendingRefundRequest(portalRefundData.getTestData("approvalRemark"));

            // Re-assert the same (now-Refunded) row -- assertRefundedRequest only cross-checks and
            // records the net refunded amount once the row's status is Refunded, which is what the
            // AR Ledger check below needs.
            adminRefundPage.assertRefundedRequest(bookingReference, popupData,
                    portalRefundData.getTestData("requestTypeUnderReview"),
                    portalRefundData.getTestData("refundStatusRefunded"));
            adminRefundPage.assertRefundDetailsPopup(popupData, portalRefundData.getTestData("approvalRemark"));
            BigDecimal netRefundedAmount = adminRefundPage.getNetRefundedAmount();

            String today = String.valueOf(LocalDate.now().getDayOfMonth());
            LedgerReportDetails_Page ledgerReportDetailsPage = new LedgerReportDetails_Page(driver);
            new Reports_Common(driver).clickReports().clickARLedger();
            // Branch is a required field on this page (same as Admin's own flow), and since this
            // is an agency's own booking we additionally scope by Agency (extracted from Portal).
            ledgerReportDetailsPage.Lst_BranchName(branch);
            selectAgencyInLedgerReport(agencyName);
            ledgerReportDetailsPage.Dpick_InvoiceFromDate(today);
            ledgerReportDetailsPage.Dpick_InvoiceToDate(today);
            ledgerReportDetailsPage.Btn_SearchGrid();
            ledgerReportDetailsPage.assertBookingRefundWithRetry(bookingReference, netRefundedAmount, 3, 15000);

            refreshAndGetRefundStatus();
            Assert.assertEquals(getRefundStatusFromDetails(), portalRefundData.getTestData("refundStatusRefunded"),
                    "Portal refund details section did not reflect Refunded after Admin approval");

            BigDecimal walletBalanceAfterApproval = new SideMenu(driver).getWalletBalance();
            Assert.assertEquals(walletBalanceAfterApproval,
                    walletBalanceBeforeApproval.add(netRefundedAmount).setScale(3, RoundingMode.HALF_UP),
                    "Wallet balance was not credited by the net refunded amount after Admin approval");

            closeAdminTab();
        } else {
            assertAutomaticRefund(bookingReference, popupData, autoRefundSetting, branch, agencyName);
            closeAdminTab();
        }
    }

    // Shared by the approve and reject flows for the case where AGENCY_AUTO_REFUND_ENABLED is on and
    // the refund already completed automatically -- expects to be on the freshly logged-in admin tab.
    private void assertAutomaticRefund(String bookingReference, String popupData, String autoRefundSetting,
                                       String branch, String agencyName) throws Exception {
        Assert.assertEquals(autoRefundSetting, portalRefundData.getTestData("autoRefundEnabledValue"),
                "AGENCY_AUTO_REFUND_ENABLED should be 1 when the portal refund came back Refunded");

        AdminPages.BookingMidOffice.Refund.RefundPage adminRefundPage = new AdminPages.BookingMidOffice.Refund.RefundPage(driver);
        adminRefundPage.goToRefundedRequestsFromFreshLogin()
                .searchRefundedRequest(branch)
                .assertRefundedRequest(bookingReference, popupData,
                        portalRefundData.getTestData("requestTypeAutomatic"),
                        portalRefundData.getTestData("refundStatusRefunded"))
                // A fully automatic refund has no approve/reject action, so no remark was typed.
                .assertRefundDetailsPopup(popupData, null);

        BigDecimal netRefundedAmount = adminRefundPage.getNetRefundedAmount();
        String today = String.valueOf(LocalDate.now().getDayOfMonth());

        LedgerReportDetails_Page ledgerReportDetailsPage = new LedgerReportDetails_Page(driver);
        new Reports_Common(driver).clickReports().clickARLedger();
        // Branch is required on this page, plus Agency -- same reasoning as the Pending path.
        ledgerReportDetailsPage.Lst_BranchName(branch);
        selectAgencyInLedgerReport(agencyName);
        ledgerReportDetailsPage.Dpick_InvoiceFromDate(today);
        ledgerReportDetailsPage.Dpick_InvoiceToDate(today);
        ledgerReportDetailsPage.Btn_SearchGrid();
        ledgerReportDetailsPage.assertBookingRefundWithRetry(bookingReference, netRefundedAmount, 3, 15000);
    }

    // Rejecting only applies to a refund that came back "Pending" (AGENCY_AUTO_REFUND_ENABLED = 0).
    // Same as runRefundFlow: if the setting is on for a given run, the refund is already Refunded by
    // the time we reach Admin and there is nothing left to reject, so the automatic refund is
    // verified instead.
    public void runRejectRefundFlow(String bookingReference) throws Exception {
        openBookingDetails(bookingReference)
                .selectFirstBooking()
                .expandPassengerDetails()
                .openRefundPopup();
        String popupData = getRefundPopupData();

        assertPopupReference(bookingReference)
                .assertPopupCalculation()
                .submitRefundRequest()
                .assertRefundDetailsSection(popupData);
        String status = getRefundStatusFromDetails();

        // Captured on the Portal tab, before ever switching to Admin, same reasoning as
        // runRefundFlow -- needed for the AR Ledger's Agency filter below.
        String agencyName = new SideMenu(driver).getAgencyName();
        BigDecimal walletBalanceBeforeRejection = new SideMenu(driver).getWalletBalance();

        openNewAdminTabAndLogin();
        try {
            new Setting_Common(driver).clickSetting().clickAdminSetting();
            String autoRefundSetting = new AdminSettings_Page(driver).findSettingValue("AGENCY_AUTO_REFUND_ENABLED");

            String branch = portalRefundData.getTestData("agencyBranch");

            if (!status.equals(portalRefundData.getTestData("refundStatusPending"))) {
                assertAutomaticRefund(bookingReference, popupData, autoRefundSetting, branch, agencyName);
                return;
            }

            Assert.assertEquals(autoRefundSetting, portalRefundData.getTestData("autoRefundDisabledValue"),
                    "AGENCY_AUTO_REFUND_ENABLED should be 0 for a refund request to be rejectable");

            AdminPages.BookingMidOffice.Refund.RefundPage adminRefundPage = new AdminPages.BookingMidOffice.Refund.RefundPage(driver);
            adminRefundPage.goToRefundedRequestsFromFreshLogin()
                    .searchRefundedRequestUnderReview(branch)
                    .assertRefundedRequest(bookingReference, popupData,
                            portalRefundData.getTestData("requestTypeUnderReview"),
                            portalRefundData.getTestData("refundStatusPendingForApprove"))
                    .rejectPendingRefundRequest(portalRefundData.getTestData("rejectionRemark"));

            // Re-assert the same (now-Rejected) row -- assertRefundedRequest only cross-checks and
            // records the net refunded amount once the row is in a final state (Refunded or
            // Rejected -- confirmed live its amount columns are already populated either way).
            adminRefundPage.assertRefundedRequest(bookingReference, popupData,
                    portalRefundData.getTestData("requestTypeUnderReview"),
                    portalRefundData.getTestData("refundStatusRejected"));
            adminRefundPage.assertRefundDetailsPopup(popupData, portalRefundData.getTestData("rejectionRemark"));

            String today = String.valueOf(LocalDate.now().getDayOfMonth());
            LedgerReportDetails_Page ledgerReportDetailsPage = new LedgerReportDetails_Page(driver);
            new Reports_Common(driver).clickReports().clickARLedger();
            // Branch is a required field on this page, plus Agency -- same reasoning as
            // runRefundFlow. A rejected request must never have posted a debit -- every ledger row
            // for this booking reference (one per passenger) must still read 0.00.
            ledgerReportDetailsPage.Lst_BranchName(branch);
            selectAgencyInLedgerReport(agencyName);
            ledgerReportDetailsPage.Dpick_InvoiceFromDate(today);
            ledgerReportDetailsPage.Dpick_InvoiceToDate(today);
            ledgerReportDetailsPage.Btn_SearchGrid();
            ledgerReportDetailsPage.assertBookingRejectedWithRetry(bookingReference, 3, 15000);

            refreshAndGetRefundStatus();
            Assert.assertEquals(getRefundStatusFromDetails(), portalRefundData.getTestData("refundStatusRejected"),
                    "Portal refund details section did not reflect Rejected after Admin rejected the request");
            assertRefundDetailsRemarks(portalRefundData.getTestData("rejectionRemark"));

            BigDecimal walletBalanceAfterRejection = new SideMenu(driver).getWalletBalance();
            Assert.assertEquals(walletBalanceAfterRejection, walletBalanceBeforeRejection,
                    "Wallet balance should not change when a refund request is rejected");
        } finally {
            closeAdminTab();
        }
    }

    private List<BigDecimal> extractAmounts(String text) {
        List<BigDecimal> amounts = new ArrayList<>();
        Matcher matcher = MONEY_PATTERN.matcher(text.replace("%", ""));
        while (matcher.find()) {
            amounts.add(new BigDecimal(matcher.group().replace(",", "")));
        }
        return amounts;
    }

    private BigDecimal normalizeAmountValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Amount value is null or empty");
        }

        String cleanedValue = value
                .trim()
                .replace(",", "")
                .replaceAll("[^0-9.\\-()]", "");

        boolean negative = cleanedValue.startsWith("(")
                && cleanedValue.endsWith(")");

        cleanedValue = cleanedValue.replace("(", "").replace(")", "");

        BigDecimal amount = new BigDecimal(cleanedValue);

        if (negative) {
            amount = amount.negate();
        }

        return amount.setScale(2, RoundingMode.HALF_UP);
    }
}
