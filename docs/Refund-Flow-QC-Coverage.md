# Refund Flow — QA Automation Coverage

**Audience:** QC / Manual Test team
**Purpose:** know exactly what the automated suite already verifies for the online refund flow, so manual test effort can focus on the gaps instead of re-checking what's already covered.
**Last updated:** 2026-09-27

## Test suite locations

- **Admin:** `src/test/java/AdminPages/BookingMidOffice/Refund/Refund_TC.java`
  (page object: `src/main/java/AdminPages/BookingMidOffice/Refund/RefundPage.java`)
- **Portal/Agency:** `src/test/java/PortalPages/BookingMidOffice/Refund/Refund_TC.java`
  (page object: `src/main/java/PortalPages/BookingMidOffice/Refund/RefundPage.java`)

## Environment / test data notes

- Runs against the real staging application — every run creates a real booking and moves real wallet balance. Don't run repeatedly against a shared/important environment without accounting for that.
- Portal test agency: **AGN10066 / "Automation Agency"**, branch **"Rana"**.
- `AGENCY_AUTO_REFUND_ENABLED` is not pinned to one value in this environment — it's been observed flipping between runs (outside the automation's control), so a given Portal run may land on either the "Pending For Approval" branch or the "fully automatic" branch. Both have now been confirmed to pass live (see coverage matrix).
- The one-way booking-creation step used to be duplicated separately inside each `RefundPage` (Admin and Portal); it's since been consolidated to reuse the same `Booking_TC.bookOneWay()` each side's own booking tests already use, removing the duplicate code and picking up the segment/fare consistency assertion for free.
- All 4 Portal variants (One-way, Round-trip, Multi-city, Pay-after-hold one-way) are now automated and have each been run live to a passing result (previously only One-way had been exercised), for both the "Pending" and "Reject" flows.
- Rejecting a refund only applies to a request that came back "Pending" — since `AGENCY_AUTO_REFUND_ENABLED` isn't pinned (see above), a reject test run when that setting happens to be on will fail with a clear assertion message (there is nothing to reject once a refund has already posted automatically), rather than silently skipping.

## Fixes applied 2026-09-27 (Portal side)

A full pass of all 4 Portal `Refund_TC` variants surfaced several real bugs, all fixed and re-verified live:

- **Admin single-session restriction:** the admin browser tab opened mid-test (to check settings / approve refunds) was only being *closed*, not logged out. Since Admin only allows one active session per account, the next test's admin login would then be blocked. `RefundPage.closeAdminTab()` now logs out of Admin before closing the tab, and the admin-tab section of `runRefundFlow` is wrapped in try/finally so this cleanup always runs — even if an assertion inside fails — otherwise the *next* test's `@BeforeMethod` would fail on a stale/wrong browser tab.
- **Multi-city search — wrong "second destination" locator:** the field for the 2nd route's destination was targeted by counting chevron-down icons on the page (index 8 of only 6 that ever exist there), so it could never resolve. Confirmed live that multi-city renders each leg with its own independent From/To pair (the 2nd leg's origin auto-fills from the 1st leg's destination — no click needed there at all); fixed to match the correct placeholder element instead.
- **Pay-after-hold — wrong flow entirely:** the code mimicked Admin's flow (open the booking's itinerary page, then a "Confirm To Pay" button), but Portal doesn't work that way. Confirmed live: Portal pays a held booking directly from the **"Pay And Book"** action on its "My Bookings" search-results row, which opens a right-hand sidebar (Segment/Fare/Passenger tabs) with a Terms & Conditions checkbox and a **Pay** button — there is no separate itinerary page and no "Confirm To Pay" step on Portal. Also removed a stray `SelectFlight()` call before searching "My Bookings" — Portal's search page has no Flight/Booking module tabs at all (Admin's does).
- **Pay button click intercepted by chat widget:** the Zoho SalesIQ live-chat bubble floats over the Pay button at some viewport sizes and swallows the click. Fixed by hiding it first (same pattern already used in `PortalPages.Reports.Statement.Statement.hideChatWidget()`).
- **Test data typo:** `PortalRefund.json`'s `refundStatusPendingForApprove` was `"Pending For Approve"`; the Admin grid's actual text is `"Pending For Approval"`.
- **Reject click intercepted by a success toast:** right after Admin rejects a refund request, a toast notification (top-right) can float over the profile-menu icon and swallow the logout click in `closeAdminTab()`, which then breaks the *next* test's login too. Fixed the same way as the chat-widget issue — hide any toast via JS before interacting.
- **Same multi-city locator bug existed on Admin too:** `AdminPages`' `SearchBookingBranch.java` had the identical chevron-icon-index[8] bug for the 2nd route's destination. Applied the same confirmed-live fix there.
- **"Refund Details" popup (eye icon) — two real locator bugs found via live debugging:** the popup's amount `<span>`s carry an extra `negative`/`positive` modifier class (e.g. `class="value negative"`), so an exact `@class='value'` match only ever resolved for the two unmodified rows (Remarks, Supplier Ticketing Price) and silently timed out on the rest (Penalty, Discount, Cancellation Charge) — fixed to `contains(@class,'value')`. Separately, the popup's "Close" button locator wasn't scoped to the dialog, which could click the wrong element and leave the overlay mask stuck open for the rest of the test, eventually breaking the end-of-test logout click — fixed by scoping it to `//p-dialog[@header='Refund Details']`.

## Refactor 2026-09-27: flow orchestration moved into the page objects

Both `Refund_TC.java` files now only contain `@Test` methods — the actual step-by-step flow (open booking → refund → Admin cross-check/action → back to Portal, or Admin's own booking → refund → Refunded Requests → AR Ledger) has been moved into `RefundPage.runRefundFlow(...)` (both Admin and Portal) and Portal's `RefundPage.runRejectRefundFlow(...)`. Each test method just creates its booking variant and calls the shared flow. Purely structural — re-verified live with no behavior change.

## "Refund Details" popup (eye icon) assertions added 2026-09-27

Every row on Admin's Refunded Requests grid has an "eye" (View Details) action — confirmed present regardless of scenario (automatic, approved, or rejected; Admin- or Portal-initiated). Clicking it opens a "Refund Details" dialog with a full breakdown (Supplier Ticketing Price, Penalty, Supplier Refunded Amount, Discount, Cancellation Charge, Net Refunded Amount) plus a **Remarks** row that's only present once a remark has actually been typed (never for a fully-automatic refund with no approve/reject action). New `RefundPage.assertRefundDetailsPopup(popupData, expectedRemark)` (Admin-side, reused by Portal) opens it, cross-checks Cancellation Charge and Net Refunded Amount against the original refund popup, checks the remark when one is expected, then closes it. Wired into all four call sites: Admin's own `runRefundFlow`, Portal's `runRefundFlow` (both the Pending/approved and fully-automatic branches), and Portal's `runRejectRefundFlow`.

## Reject-refund scenario added 2026-09-27 (Portal side)

4 new test cases, `verifyRejectRefundFlowOneWay` / `RoundTrip` / `MultiCity` / `PayAfterHoldOneWay`, added alongside the existing approve/auto ones — same booking-creation methods, same popup/submission assertions, but Admin **rejects** the pending request instead of approving it. Confirmed live, all 4 passing:

- `AdminPages...RefundPage.rejectPendingRefundRequest(remark)` clicks the thumbs-down icon next to Approve (same modal shape: a `remarks...` textarea + Submit), then polls the grid row until its status flips to **"Rejected"**.
- The row's amount columns (Supplier Penalty / Supplier Refund Amount / Cancellation Charge / Net Refunded Amount) are confirmed live to already be finalized on a Rejected row, exactly like a Refunded one — `assertRefundedRequest`'s cross-check now runs for either final status, not just "Refunded".
- On Portal, confirmed live: rejecting appends a **5th field, "Remarks"**, to the Refund Details card (only ever present after a rejection) holding Admin's exact remark text — new `assertRefundDetailsRemarks(expected)` checks it.
- **Assert** — the wallet balance is unchanged after rejection (compared before-vs-after, same precision as the approve flow's credit check).
- **Assert** — the AR Ledger shows a **zero** debit for every row matching this booking reference (one row per passenger, same as the approve flow's ledger check) — new `LedgerReportDetails_Page.assertBookingRejectedWithRetry(...)` confirms a rejected request never quietly posts a real debit.

## Coverage matrix

| Scenario | Admin | Portal / Agency |
|---|---|---|
| One-way booking refund | ✅ Automated | ✅ Automated |
| Round-trip booking refund | ✅ Automated | ✅ Automated |
| Multi-city booking refund | ✅ Automated | ✅ Automated |
| Pay-after-hold one-way refund | ✅ Automated | ✅ Automated |
| Refund requiring Admin approval ("Pending") | N/A — Admin refunds are always immediate | ✅ Automated, including the wallet-credit check |
| Fully automatic refund (no approval step) | ✅ Automated  | ✅ Automated |
| Partial refund | ⬜ Not automated | ⬜ Not automated |
| Admin rejects a pending refund request | N/A — Admin refunds are always immediate, nothing to reject | ✅ Automated, including the "Remarks" display and the no-wallet-change check |
| Refunding an already-refunded or already-cancelled booking | ⬜ Not automated | ⬜ Not automated |

## Step-by-step: what each automated test actually checks

### Admin — `verifyRefundFlowOneWay` / `verifyRefundFlowRoundTrip` / `verifyRefundFlowMultiCity` / `verifyRefundFlowPayAfterHoldOneWay`

All four share the same flow below; they only differ in how the booking is created (all four now go through the shared `Booking_TC` booking-creation methods), and `PayAfterHoldOneWay` skips the "Take Control" step (its booking starts unlocked).

1. Create the booking. **Assert** — while creating it, the flight's segment and fare details shown in the search-results side panel match what's actually shown on the selected flight card and the final fare breakdown (i.e. the price/route summary doesn't change between search and selection).
2. Open it, take control, expand passenger details, click **Online Refund**.
3. **Assert** — the refund popup shows the correct booking reference.
4. **Assert** — the popup's own numbers reconcile: Gross − deductions − Cancellation Charge = Net.
5. Submit the refund (checkbox → Confirm Refund → Done).
6. **Assert** — the confirmation screen shows a non-empty Refund Id, status = "Refunded", and its Cancelled Amount / Refund Amount exactly match the popup's figures.
7. Go to Refunded Requests, search by branch (request type defaults to "Automatic").
8. **Assert** — the row's Booking Reference, Branch, Refund Type (`FULL`), Request Type (`Automatic`) and Status (`Refunded`) all match; **and** (Supplier Refund Amount − Supplier Penalty − Cancellation Charge) equals the popup's net figure; **and** the row's own Net Refunded Amount column also matches it.
9. Open that row's "eye" (View Details) popup. **Assert** — its Cancellation Charge and Net Refunded Amount match the original refund popup (no remark expected — Admin's own refunds are always "Automatic").
10. Go to Reports → AR Ledger, filter by branch + today's date, search.
11. **Assert** — the booking's debit entry appears with the correct amount, and the running balance changed by exactly that amount (retried up to 3× / 15s apart to allow for posting lag).

### Portal — `verifyRefundFlowOneWay` / `verifyRefundFlowRoundTrip` / `verifyRefundFlowMultiCity` / `verifyRefundFlowPayAfterHoldOneWay`

All four share the same flow below and only differ in how the booking is created (all four go through the shared `Booking_TC` booking-creation methods — Portal has no "Take Control" step; the agent refunds directly).

1. Create the booking, paid from wallet, via the shared `Booking_TC` method for that variant. **Assert** — same segment/fare consistency check as Admin's, above.
2. Open it via My Bookings, expand passenger details, click **Online Refund**.
3. **Assert** — popup reference matches; popup's own numbers reconcile (same check as Admin).
4. Submit the refund.
5. **Assert** — the Refund Details section shows a non-empty Refund Id, and its amounts match the popup.
6. Record the current wallet balance and agency name from the Portal sidebar — **before** touching Admin at all.
7. Open a second (Admin) browser tab, log in, read the `AGENCY_AUTO_REFUND_ENABLED` setting.
8. **Assert** — that setting's value (`0` or `1`) is consistent with whether the refund actually came back "Pending For Approval" or "Refunded". Which branch below actually runs depends on that live setting value at the time, not on which test method it is.
9. **["Pending" branch — setting = 0]**
   - Admin searches Refunded Requests by branch (request type left on its default, "Under Review").
   - **Assert** — the row matches: reference / branch / refund type / request type ("Under Review") / status ("Pending For Approval").
   - Admin approves it (thumbs-up icon → remark → submit); the test polls (up to 30s) for the row's status to flip to "Refunded".
   - **Assert** — re-checking that same row now also cross-checks the amounts, the same way Admin's own check does.
   - Open that row's "eye" (View Details) popup. **Assert** — Cancellation Charge and Net Refunded Amount match the original refund popup, **and** its Remarks field shows Admin's exact approval remark.
   - Go to AR Ledger, filtered by **Branch** (required field) **and Agency** (agency name captured live from Portal, not hardcoded) + today's date.
   - **Assert** — debit entry and running-balance delta, same style as Admin's ledger check.
   - Switch back to the Portal tab, reload the page.
   - **Assert** — the Refund Details section now shows "Refunded".
   - **Assert** — the wallet balance increased by exactly the net refunded amount (before-vs-after comparison, computed as Supplier Refund Amount − Supplier Penalty − Cancellation Charge).
10. **["Fully automatic" branch — setting = 1]** Same shape, but Admin searches with request type "Automatic" instead of approving anything. **Assert** — the same eye-icon popup checks (Cancellation Charge, Net Refunded Amount), no remark expected here (no approve/reject action was taken). Then runs the same Branch+Agency AR Ledger check. There is no wallet-delta check on this path, since the wallet credit would already have happened before the "before" balance was captured.

Branch (9), "Pending", has been confirmed passing live across all four variants. Branch (10), "Fully automatic", has been confirmed passing live via `verifyRefundFlowOneWay` (the live environment's `AGENCY_AUTO_REFUND_ENABLED` setting happened to be `1` on that run); it hasn't yet been specifically confirmed for the other three variants, though they share the exact same code path.

#### Pay-after-hold-specific step, confirmed live

Unlike Admin (which opens the held booking's itinerary page and clicks "Confirm To Pay"), Portal completes a held booking's payment directly from the **"Pay And Book"** action on its "My Bookings" search-results row — there is no itinerary page and no "Confirm To Pay" step on Portal. Clicking it opens a right-hand sidebar (Segment Details / Fare & Baggage Details / Passenger Details tabs) with a Terms & Conditions checkbox and a **Pay** button.

### Portal — `verifyRejectRefundFlowOneWay` / `verifyRejectRefundFlowRoundTrip` / `verifyRejectRefundFlowMultiCity` / `verifyRejectRefundFlowPayAfterHoldOneWay`

Steps 1–7 are identical to the approve/auto flow above (create booking → submit refund → capture wallet balance → open Admin tab → read the setting). From there:

8. **Assert** — the refund actually came back "Pending For Approval" (only a Pending request can be rejected) **and** `AGENCY_AUTO_REFUND_ENABLED` reads `0`. If the setting happened to be `1` this run, the refund already auto-completed and there's nothing to reject — the test fails here with a clear message rather than silently skipping.
9. Admin searches Refunded Requests by branch (request type left on its default, "Under Review").
10. **Assert** — the row matches: reference / branch / refund type / request type ("Under Review") / status ("Pending For Approval").
11. Admin rejects it (thumbs-down icon → remark → Submit); the test polls (up to 30s) for the row's status to flip to "Rejected".
12. **Assert** — re-checking that same row now also cross-checks the amount columns (Supplier Penalty / Supplier Refund Amount / Cancellation Charge / Net Refunded Amount), the same way an approved row's check does — confirmed live these are already finalized on a Rejected row too.
13. Open that row's "eye" (View Details) popup. **Assert** — Cancellation Charge and Net Refunded Amount match the original refund popup, **and** its Remarks field shows Admin's exact rejection remark.
14. Go to AR Ledger, filtered by Branch + Agency + today's date, same as the approve flow. **Assert** — every row matching this booking reference (one per passenger) shows a **zero** Debit — a rejected request must never post a real debit entry.
15. Switch back to the Portal tab, reload the page.
16. **Assert** — the Refund Details section now shows "Rejected".
17. **Assert** — the Refund Details section's "Remarks" field (only present once rejected) shows Admin's exact rejection remark.
18. **Assert** — the wallet balance is unchanged from the value captured in step 6 — rejecting must never credit the wallet.

## What QC should focus manual testing on

- Partial refunds — automation only covers full refunds so far.
- Attempting to refund a booking that's already refunded or already cancelled.
- Any UI/visual issues — automation checks values and text only, not layout, styling, or responsiveness.

## A note on precision

The wallet-balance check compares amounts at the Portal UI's own precision (3 decimal places, matching how the wallet balance is actually displayed, e.g. "974,274.400 EGP"), so it won't produce a false failure over sub-cent rounding differences.
