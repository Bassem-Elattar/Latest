package AdminPages.BookingMidOffice.Refund;

import AdminPages.BookingMidOffice.Booking_Common;
import AdminPages.BookingMidOffice.SearchBooking.SearchBooking_Page;
import AdminPages.Reports.LedgerReport.LedgerReportDetails_Page;
import AdminPages.Reports.Reports_Common;
import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RefundPage {
    // The popup text sometimes comes back with its line breaks collapsed ("Net Refundable Amount21,558.00 EGP"),
    // so an amount may directly follow a letter -- only refuse to start mid-number. The possessive digit run
    // stops backtracking into partial matches inside alphanumeric codes (e.g. "ZlC20y").
    private static final Pattern MONEY_PATTERN = Pattern.compile("(?<![\\d.,])[-+]?\\d[\\d,]*+(?:\\.\\d{1,2})?(?![A-Za-z])");

    // Refunded requests grid column positions (1-based, matches <td> order)
    // Confirmed against the live grid header row: Booking Reference, Branch, Agency, PNR,
    // Passenger Name, Refund Type, Request Type, Supplier Penalty, Supplier Refund Amount, Cancellation Charge
    // The grid has no Discount column -- the discount only appears on the refund popup.
    private static final int BOOKING_REFERENCE_COLUMN = 1;
    private static final int BRANCH_COLUMN = 2;
    private static final int REFUND_TYPE_COLUMN = 7;
    private static final int REQUEST_TYPE_COLUMN = 8;
    private static final int SUPPLIER_PENALTY_COLUMN = 9;
    private static final int SUPPLIER_REFUND_AMOUNT_COLUMN = 10;
    private static final int CANCELLATION_CHARGE_COLUMN = 11;
    private static final int NET_REFUNDED_AMOUNT_COLUMN = 12;
    private static final int REFUND_STATUS_COLUMN = 13;

    private final SHAFT.GUI.WebDriver driver;
    private final SHAFT.TestData.JSON bookingData;
    private final SHAFT.TestData.JSON refundData;
    private String searchedBranch;
    private BigDecimal netRefundedAmount;

    private final By firstBookingResult = By.xpath("(//td[@class='ng-star-inserted'])[2]//a");
    private final By lockButton = By.xpath("(//div[contains(@class,'locker') and contains(@class,'exception-buttons')])[2]");
    private final By takeControlButton = By.xpath("//button[normalize-space()='Take Control']");
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
    private final By successReference = By.xpath("(" + REFUND_DETAILS_GRID_XPATH + "//p)[2]");
    private final By successCancelledAmount = By.xpath("(" + REFUND_DETAILS_GRID_XPATH + "//p)[4]");
    private final By successStatus = By.xpath("(" + REFUND_DETAILS_GRID_XPATH + "//p)[6]");
    private final By successAmount = By.xpath("(" + REFUND_DETAILS_GRID_XPATH + "//p)[8]");
    private final By branchDropdown = By.xpath("(//div[@role='button'])[1]");
    private final By branchFilter = By.xpath("//input[contains(@class,'p-dropdown-filter')]");
    // Confirmed against the live Refunded Requests page (/booking/refund-request): the radio
    // inputs are id="id-RequestType-UnderReview" / id="id-RequestType-Automatic", with
    // "Under Review" selected by default -- label-based locator is more robust than an index
    // into the (visually-hidden) p-radiobutton-box divs.
    private final By automaticRadioButton = By.xpath("//label[@for='id-RequestType-Automatic']");
    private final By submitButton = By.xpath("(//span[@class='p-button-label'])[1]");

    public RefundPage(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
        this.bookingData = new SHAFT.TestData.JSON("searchBookingBrData.json");
        this.refundData = new SHAFT.TestData.JSON("Refund.json");
    }

    public RefundPage openBookingDetails(String bookingReference) throws InterruptedException {
        new Booking_Common(driver)
                .ShowMoreMenu()
                .click_Sub_BookingMidOffice()
                .clickSearchBooking();
        new SearchBooking_Page(driver)
                .SelectFlight()
                .SelectBranch(bookingData.getTestData("brName"))
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

    public RefundPage takeControl() {
        driver.element().click(lockButton);
        driver.element().click(takeControlButton);
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

    public String getRefundSuccessReference() {
        return driver.element().getText(successReference);
    }

    public String getRefundSuccessAmount() {
        return driver.element().getText(successAmount);
    }

    public String getRefundSuccessStatus() {
        return driver.element().getText(successStatus);
    }

    public String getRefundCancelledAmount() {
        return driver.element().getText(successCancelledAmount);
    }

    public RefundPage submitRefundRequest() {
        driver.element().click(checkBox);
        driver.element().click(refundSubmitButton);
        driver.element().click(doneButton);
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(90))
                .until(ExpectedConditions.presenceOfElementLocated(refundDetailsCard));
        return this;
    }

    public RefundPage assertRefundSuccessDetails(String popupData) {
        List<BigDecimal> popupAmounts = extractAmounts(popupData);
        Assert.assertTrue(popupAmounts.size() >= 2,
                "Refund popup does not contain enough amounts to compare against the refund success details");

        // Popup breakdown is always: Gross, ...deductions..., Cancellation Charge, Net.
        // "Cancelled Amount" on the success screen matches the Cancellation Charge line, not the Gross amount.
        BigDecimal expectedCancelledAmount = popupAmounts.get(popupAmounts.size() - 2);
        BigDecimal expectedRefundAmount = popupAmounts.get(popupAmounts.size() - 1);

        String refundId = getRefundSuccessReference();
        Assert.assertFalse(refundId.trim().isEmpty(), "Refund success screen does not display a Refund Id");

        String status = getRefundSuccessStatus();
        Assert.assertEquals(status.trim(), refundData.getTestData("refundStatus"),
                "Refund success screen status does not match the expected value");

        BigDecimal cancelledAmount = normalizeAmountValue(getRefundCancelledAmount());
        Assert.assertEquals(cancelledAmount.setScale(2, RoundingMode.HALF_UP), expectedCancelledAmount.setScale(2, RoundingMode.HALF_UP),
                "Refund success screen cancelled amount does not match the popup's original amount");

        BigDecimal refundAmount = normalizeAmountValue(getRefundSuccessAmount());
        Assert.assertEquals(refundAmount.setScale(2, RoundingMode.HALF_UP), expectedRefundAmount.setScale(2, RoundingMode.HALF_UP),
                "Refund success screen refund amount does not match the popup total");

        return this;
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

        // Start with the first amount
        BigDecimal calculatedTotal = amounts.get(0);

        // Subtract all remaining amounts except the displayed total
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

    public RefundPage goToRefundedRequests() {
        new Booking_Common(driver).ShowMoreMenu().click_Sub_BookingMidOffice().clickRefundRequest();
        return this;
    }

    // goToRefundedRequests() above (ShowMoreMenu + generic anchor, with no preceding top-nav
    // click) only works as a *repeat* navigation once the Booking-Mid Office section is already
    // active from an earlier click in the same tab (how Admin's own Refund_TC always calls it,
    // right after openBookingDetails()) -- ShowMoreMenu there is a hamburger that reveals that
    // section's own left sidebar (Search Booking / Refund Request / My Quotes / etc.), not the top
    // nav. Portal's cross-tab refund approval instead opens a brand-new admin tab and logs in
    // fresh, landing on the Dashboard with no Booking-Mid Office section active yet, so it first
    // needs clickBookingMidOffice() to enter that section (confirmed live: it lands on the
    // section's default "Flight Search" page with the same collapsed left sidebar) before the
    // hamburger has anything to reveal.
    public RefundPage goToRefundedRequestsFromFreshLogin() {
        new Booking_Common(driver).clickBookingMidOffice().ShowMoreMenu().click_Sub_BookingMidOffice().clickRefundRequest();
        return this;
    }

    public RefundPage searchRefundedRequest() {
        return searchRefundedRequest(bookingData.getTestData("brName"));
    }

    public RefundPage searchRefundedRequest(String branch) {
        this.searchedBranch = branch;
        driver.element().click(branchDropdown);
        driver.element().type(branchFilter, branch);
        By branchOption = By.xpath("(//li[contains(@aria-label,'" + branch + "')])[1]");
        driver.element().click(branchOption);
        driver.element().click(automaticRadioButton);
        driver.element().click(submitButton);
        return this;
    }

    // Same as searchRefundedRequest(branch) but leaves the request-type radio on its default
    // ("Under Review") instead of switching to "Automatic" -- used for a Portal refund that came
    // in as "Pending" (AGENCY_AUTO_REFUND_ENABLED = 0) and hasn't been approved yet.
    public RefundPage searchRefundedRequestUnderReview(String branch) {
        this.searchedBranch = branch;
        driver.element().click(branchDropdown);
        driver.element().type(branchFilter, branch);
        By branchOption = By.xpath("(//li[contains(@aria-label,'" + branch + "')])[1]");
        driver.element().click(branchOption);
        driver.element().click(submitButton);
        return this;
    }

    // Confirmed live: clicking thumbsUpIcon opens an "Approve refund request <id>" modal with a
    // plain (no id attribute) textarea placeholder="remarks..." and a "Submit" button.
    private final By thumbsUpIcon = By.xpath("(//i[contains(@class,'pi-thumbs-up')])[1]");
    private final By approvalRemarkBox = By.xpath("//textarea[@placeholder='remarks...']");
    private final By approvalConfirmButton = By.xpath("//button[normalize-space()='Submit']");

    public RefundPage approvePendingRefundRequest(String remark) {
        driver.element().click(thumbsUpIcon);
        driver.element().type(approvalRemarkBox, remark);
        driver.element().click(approvalConfirmButton);

        // Approval is processed server-side and the grid re-renders asynchronously -- reading the
        // status column immediately after the Submit click race the backend (confirmed live: it
        // still read "Pending For Approve" a second later). Poll until it flips to Refunded.
        String expectedStatus = refundData.getTestData("refundStatus");
        String refundStatusCell = "";
        long deadline = System.currentTimeMillis() + 30000;
        while (System.currentTimeMillis() < deadline) {
            refundStatusCell = getRefundedRequestColumn(REFUND_STATUS_COLUMN).trim();
            if (refundStatusCell.equals(expectedStatus)) {
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Assert.assertEquals(refundStatusCell, expectedStatus,
                "Refunded request status did not become Refunded after approval");
        return this;
    }

    // Confirmed live: sits right next to thumbsUpIcon in the same row and opens the exact same
    // "remarks..." + Submit modal shape, just for rejection instead of approval.
    private final By thumbsDownIcon = By.xpath("(//i[contains(@class,'pi-thumbs-down')])[1]");

    public RefundPage rejectPendingRefundRequest(String remark) {
        driver.element().click(thumbsDownIcon);
        driver.element().type(approvalRemarkBox, remark);
        driver.element().click(approvalConfirmButton);

        // Same server-side race as approval -- poll until the row flips to Rejected.
        String expectedStatus = "Rejected";
        String refundStatusCell = "";
        long deadline = System.currentTimeMillis() + 30000;
        while (System.currentTimeMillis() < deadline) {
            refundStatusCell = getRefundedRequestColumn(REFUND_STATUS_COLUMN).trim();
            if (refundStatusCell.equals(expectedStatus)) {
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Assert.assertEquals(refundStatusCell, expectedStatus,
                "Refunded request status did not become Rejected after rejection");
        return this;
    }

    public RefundPage assertRefundedRequest(String bookingReference, String popupData) {
        return assertRefundedRequest(bookingReference, popupData,
                refundData.getTestData("requestType"), refundData.getTestData("refundStatus"));
    }

    public RefundPage assertRefundedRequest(String bookingReference, String popupData,
                                             String expectedRequestType, String expectedRefundStatus) {
        String referenceCell = getRefundedRequestColumn(BOOKING_REFERENCE_COLUMN);
        String branchCell = getRefundedRequestColumn(BRANCH_COLUMN);
        String refundTypeCell = getRefundedRequestColumn(REFUND_TYPE_COLUMN);
        String requestTypeCell = getRefundedRequestColumn(REQUEST_TYPE_COLUMN);
        String refundStatusCell = getRefundedRequestColumn(REFUND_STATUS_COLUMN);

        Assert.assertEquals(referenceCell.trim(), bookingReference,
                "Refunded request contains a different booking reference");

        Assert.assertEquals(branchCell.trim(), searchedBranch,
                "Refunded request branch does not match the searched branch");

        Assert.assertEquals(refundTypeCell.trim(), refundData.getTestData("refundType"),
                "Refunded request refund type does not match the expected value");

        Assert.assertEquals(refundStatusCell.trim(), expectedRefundStatus,
                "Refunded request refund status does not match the expected value");

        Assert.assertEquals(requestTypeCell.trim(), expectedRequestType,
                "Refunded request request type does not match the expected value");

        // A request that is still "Pending For Approve" may not have its supplier/cancellation
        // amounts finalized yet -- only cross-check the amount columns once the row is in a final
        // state (Refunded or, confirmed live, Rejected -- its amount columns are already populated
        // with the same figures a Refunded row would show).
        if (refundData.getTestData("refundStatus").equals(expectedRefundStatus) || "Rejected".equals(expectedRefundStatus)) {
            BigDecimal supplierPenalty = normalizeAmountValue(getRefundedRequestColumn(SUPPLIER_PENALTY_COLUMN));
            BigDecimal supplierRefundAmount = normalizeAmountValue(getRefundedRequestColumn(SUPPLIER_REFUND_AMOUNT_COLUMN));
            BigDecimal cancellationCharge = normalizeAmountValue(getRefundedRequestColumn(CANCELLATION_CHARGE_COLUMN));
            this.netRefundedAmount = normalizeAmountValue(getRefundedRequestColumn(NET_REFUNDED_AMOUNT_COLUMN));

            List<BigDecimal> popupAmounts = extractAmounts(popupData);
            Assert.assertFalse(popupAmounts.isEmpty(),
                    "Refund popup does not contain an amount to compare against the refunded request");
            BigDecimal expectedNetTotal = popupAmounts.get(popupAmounts.size() - 1);
            // Not shown on the grid, so it comes from the popup.
            BigDecimal discount = getPopupAmount(popupData, "Discount Amount");

            Assert.assertEquals(cancellationCharge, getPopupAmount(popupData, "Cancellation Charge Amount"),
                    "Refunded request cancellation charge does not match the popup's Cancellation Charge Amount");

            BigDecimal actualNetTotal = supplierRefundAmount.subtract(supplierPenalty).subtract(discount).subtract(cancellationCharge);
            Assert.assertEquals(actualNetTotal.setScale(2, RoundingMode.HALF_UP), expectedNetTotal.setScale(2, RoundingMode.HALF_UP),
                    "Refunded request net amount (Supplier Refund Amount - Supplier Penalty - Discount - Cancellation Charge) does not match the popup total");

            Assert.assertEquals(netRefundedAmount.setScale(2, RoundingMode.HALF_UP), expectedNetTotal.setScale(2, RoundingMode.HALF_UP),
                    "Refunded request net refunded amount column does not match the popup total");
        }

        return this;
    }

    public BigDecimal getNetRefundedAmount() {
        return netRefundedAmount;
    }

    public String getSearchedBranch() {
        return searchedBranch;
    }

    // --- Full refund flow --------------------------------------------------
    // Moved here from Refund_TC (matching Portal's RefundPage structure) so the test class only
    // holds @Test methods. needsTakeControl is false only for PayAfterHoldOneWay -- that booking
    // starts unlocked, so there's nothing to take control of.
    public void runRefundFlow(String bookingReference, boolean needsTakeControl) throws Exception {
        openBookingDetails(bookingReference).selectFirstBooking();
        if (needsTakeControl) {
            takeControl();
        }
        expandPassengerDetails().openRefundPopup();
        String popupData = getRefundPopupData();

        assertPopupReference(bookingReference)
                .assertPopupCalculation()
                .submitRefundRequest()
                .assertRefundSuccessDetails(popupData)
                .goToRefundedRequests()
                .searchRefundedRequest()
                .assertRefundedRequest(bookingReference, popupData)
                // Admin's own refunds are always "Automatic" -- no approve/reject remark was ever
                // typed, so there's nothing to check there.
                .assertRefundDetailsPopup(popupData, null);

        BigDecimal netRefundedAmount = getNetRefundedAmount();
        String today = String.valueOf(LocalDate.now().getDayOfMonth());

        LedgerReportDetails_Page ledgerReportDetailsPage = new LedgerReportDetails_Page(driver);
        new Reports_Common(driver).clickReports().clickARLedger();
        ledgerReportDetailsPage.Lst_BranchName(getSearchedBranch());
        ledgerReportDetailsPage.Dpick_InvoiceFromDate(today);
        ledgerReportDetailsPage.Dpick_InvoiceToDate(today);
        ledgerReportDetailsPage.Btn_SearchGrid();

        ledgerReportDetailsPage.assertBookingRefundWithRetry(bookingReference, netRefundedAmount, 3, 15000);
    }

    private String getRefundedRequestColumn(int columnIndex) {
        return driver.element().getText(By.xpath("(//tr[@class='ng-star-inserted'])[2]/td[" + columnIndex + "]"));
    }

    // --- "View Details" popup (eye icon) ------------------------------------
    // Confirmed live: present on every Refunded Requests row regardless of scenario (automatic,
    // approved, or rejected -- whether the refund was initiated from Admin or Portal). Shows the
    // same cancellation/net figures as the grid row, plus a "Remarks" row that's only present once
    // a remark has actually been typed (i.e. never for a fully automatic refund with no
    // approve/reject action taken).
    private final By viewDetailsIcon = By.xpath("(//tr[@class='ng-star-inserted'])[2]//i[@class='pi pi-eye']");
    private final By refundDetailsPopup = By.xpath("//p-dialog[@header='Refund Details']");
    // Scoped to this dialog specifically -- an unscoped "Close" button locator can match/click the
    // wrong element (confirmed live: the popup's overlay mask was left stuck open for the rest of
    // the test, eventually blocking the end-of-test logout click).
    private final By refundDetailsPopupCloseButton = By.xpath("//p-dialog[@header='Refund Details']//button[@label='Close']");
    private final By refundDetailsPopupNetTotal = By.xpath("//span[@class='total-value']");

    private By refundDetailsPopupRowValue(String label) {
        // Confirmed live: the value span carries an extra "negative"/"positive" modifier class on
        // amount rows (e.g. class="value negative"), so an exact @class='value' match only ever
        // resolves for the two rows without a modifier (Remarks, Supplier Ticketing Price) --
        // contains() is required to match the rest (Penalty, Discount, Cancellation Charge, etc.).
        return By.xpath("//div[contains(@class,'refund-row')][.//span[@class='label'][contains(normalize-space(),'"
                + label + "')]]//span[contains(@class,'value')]");
    }

    public RefundPage assertRefundDetailsPopup(String popupData, String expectedRemark) {
        driver.element().click(viewDetailsIcon);
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(refundDetailsPopup));

        List<BigDecimal> popupAmounts = extractAmounts(popupData);
        Assert.assertTrue(popupAmounts.size() >= 2,
                "Refund popup does not contain enough amounts to compare against the Refund Details popup");
        BigDecimal expectedCancellationCharge = popupAmounts.get(popupAmounts.size() - 2);
        BigDecimal expectedNetTotal = popupAmounts.get(popupAmounts.size() - 1);

        BigDecimal cancellationCharge = normalizeAmountValue(driver.element().getText(refundDetailsPopupRowValue("Cancellation Charge")));
        Assert.assertEquals(cancellationCharge.setScale(2, RoundingMode.HALF_UP), expectedCancellationCharge.setScale(2, RoundingMode.HALF_UP),
                "Refund Details popup's Cancellation Charge does not match the original refund popup");

        BigDecimal discount = normalizeAmountValue(driver.element().getText(refundDetailsPopupRowValue("Discount")));
        Assert.assertEquals(discount.abs(), getPopupAmount(popupData, "Discount Amount").abs(),
                "Refund Details popup's Discount does not match the original refund popup");

        BigDecimal netTotal = normalizeAmountValue(driver.element().getText(refundDetailsPopupNetTotal));
        Assert.assertEquals(netTotal.setScale(2, RoundingMode.HALF_UP), expectedNetTotal.setScale(2, RoundingMode.HALF_UP),
                "Refund Details popup's Net Refunded Amount does not match the original refund popup");

        if (expectedRemark != null) {
            String remarks = driver.element().getText(refundDetailsPopupRowValue("Remarks"));
            Assert.assertEquals(remarks.trim(), expectedRemark,
                    "Refund Details popup does not display the expected remark");
        }

        driver.element().click(refundDetailsPopupCloseButton);
        new WebDriverWait(driver.getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(refundDetailsPopup));
        return this;
    }

    // Refund popup text is label/value pairs, e.g. "Discount Amount (-)\n0.00 EGP" -- or, when the line
    // breaks come back collapsed, "Discount Amount (-)  0.00 EGP". Returns the first amount after the label.
    private BigDecimal getPopupAmount(String popupData, String label) {
        Matcher labelMatcher = Pattern.compile(Pattern.quote(label)).matcher(popupData);
        if (labelMatcher.find()) {
            Matcher amountMatcher = MONEY_PATTERN.matcher(popupData);
            if (amountMatcher.find(labelMatcher.end())) {
                return normalizeAmountValue(amountMatcher.group());
            }
        }
        throw new AssertionError("Refund popup does not contain a '" + label + "' line:\n" + popupData);
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
