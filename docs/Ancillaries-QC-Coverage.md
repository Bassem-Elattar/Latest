# Ancillaries (Bundle / Meals / Baggage) — QA Automation Coverage

**Audience:** QC / Manual Test team and automation engineers.
**Purpose:** how the ancillaries suite works, how to add or change scenarios from the JSON (no code), what each run verifies, and what is still open.
**Last updated:** 2026-10-11

## Files

| File | Role |
|---|---|
| `src/test/resources/testDataFiles/Ancillaries.json` | **All scenarios and data.** Add/edit scenarios here; no Java changes needed |
| `src/test/java/AdminPages/BookingMidOffice/Booking/Ancillaries_TC.java` | One data-driven test that runs every enabled scenario in the JSON |
| `src/main/java/AdminPages/BookingMidOffice/Booking/Ancillaries_Page.java` | Page object: search, passenger form, bundle / meal / baggage selection rules, and the checks on every page |

No existing file was changed. The suite reuses the existing `SearchBookingBranch`, `PaxDetailsPage`, `Booking_Common`, `SearchBooking_Page` and `LogIn_Page` methods without modifying them. Admin only so far; the Portal uses the same components.

## Business rules

| Ancillary | Charged per | Where |
|---|---|---|
| **Bundle** | journey (route), priced per passenger type ("Price for Adult / Child") | Step 1, "Fare Bundles & Services", one dropdown per journey. Mandatory on flynas. |
| **Meal** | passenger × **segment** (leg) | Step 2, one card per leg, e.g. "Onward · Leg 1" |
| **Extra baggage** | passenger × **journey** | Step 2, one card per journey |
| **Infants** | not eligible | No ancillaries tab |

- A one-way trip is **one journey**, but it can have **1–3 segments**: CAI→RUH→DXB is 1 journey and 2 segments, so 2 meal choices and 1 baggage choice per passenger.
- A round trip is 2 journeys. Multi-city has one journey per leg.
- Expected total = **rounded** base fare total + **rounded** taxes total + Σ bundle + Σ meals + Σ baggage. The system rounds the Base fare total and the Taxes total (each summed over all passengers) **up** when they have decimals, e.g. 12,781.8 → 12,782; ancillaries are added unrounded. Configurable in `Defaults.Rounding` (`UP` default, `DOWN`, `NEAREST`, `NONE`) for suppliers that behave differently.

## Routes, dates and supplier: one place

Routes and dates are set **once**, in `Defaults.Routes`, with one entry per trip type. Scenarios don't repeat them.

```json
"Defaults": {
  "Branch": "TahaBranch",
  "Supplier": "ElJazeeraV4Egy",
  "Routes": {
    "ONE_WAY":    { "Supplier": "", "Journeys": [ { "From": "CAI", "To": "DXB", "Date": "20/10/2026" } ] },
    "ROUND_TRIP": { "Supplier": "", "Journeys": [ { "From": "CAI", "To": "DXB", "Date": "20/10/2026" },
                                                 { "From": "DXB", "To": "CAI", "Date": "05/12/2026" } ] },
    "MULTI_CITY": { "Supplier": "", "Journeys": [ { "From": "CAI", "To": "DXB", "Date": "20/10/2026" },
                                                 { "From": "DXB", "To": "CAI", "Date": "05/12/2026" } ] }
  }
}
```

- **Picking a route:** a scenario says `"Trip": "ROUND_TRIP"` and uses that route.
- **Changing dates or airports** for every round-trip scenario means editing this one block.
- **Extra routes** (e.g. a 3-leg multi-city): add `"MC_3_LEGS": { "Trip": "MULTI_CITY", "Journeys": [...] }` under `Routes` and use `"Route": "MC_3_LEGS"` in the scenario.
- **`Journeys`:** list of `{From, To, Date}` with dates as `DD/MM/YYYY`. One-way takes 1; round trip takes 2 (the site fills the return route, so only its `Date` is used); multi-city takes 2 or more legs.
- **Supplier:** a route's `Supplier` overrides `Defaults.Supplier` only when it's filled in. Empty everywhere means the first flight in the results.

## How to write a scenario

Every object in `Scenarios` is one test run:

```json
{
  "Name": "OW 1A+1C - per-leg control: leg 1 FIRST for all, leg 2 SECOND for pax 1 only",
  "Enabled": true,
  "Tags": ["oneway"],
  "Trip": "ONE_WAY",
  "Passengers": { "Adults": 1, "Children": 1, "Infants": 0 },
  "Flow": "BOOK",
  "Bundle":  [ { "Journey": "ALL", "Pick": "FIRST" } ],
  "Meals":   [ { "Passenger": "ALL", "Journey": 1, "Segment": 1, "Pick": "FIRST" },
               { "Passenger": "ALL", "Journey": 1, "Segment": 2, "Pick": "SECOND" },
               { "Passenger": 2,     "Journey": 1, "Segment": 2, "Pick": "NONE" } ],
  "Baggage": [ { "Passenger": 1, "Journey": 1, "Pick": "FIRST" } ],
  "ChangeBeforeBooking": [],
  "Clear": [],
  "Expect": []
}
```

This example: both passengers get the 1st meal on leg 1. On leg 2, passenger 1 gets the 2nd meal and passenger 2 gets none. Only passenger 1 gets baggage.

### Fields

| Field | Values | Meaning |
|---|---|---|
| `Name` | text | Test name in the TestNG / Allure report |
| `Enabled` | `true` / `false` | `false` parks the scenario |
| `Tags` | list | Used to choose what to run (`-Dtags=...`) |
| `Trip` | `ONE_WAY`, `ROUND_TRIP`, `MULTI_CITY` | Uses `Defaults.Routes.<Trip>`; missing = `ONE_WAY` |
| `Route` | a name in `Defaults.Routes` (optional) | Use a named extra route instead of the trip's default |
| `Passengers` | `Adults`, `Children`, `Infants` | Missing = `Defaults.Passengers` |
| `Flow` | `BOOK`, `HOLD_THEN_PAY`, `NO_BOOKING` | Book from wallet; hold, then pay after hold; or check the passenger page only and stop (free) |
| `Bundle` | list of `{Journey, Pick}` | |
| `Meals` | list of `{Passenger, Journey, Segment, Pick}` | |
| `Baggage` | list of `{Passenger, Journey, Pick}` | |
| `ChangeBeforeBooking` | same rules plus `"Type": "MEAL"` / `"BAGGAGE"` | Re-select after the first picks; only the final choice must be charged |
| `Clear` | `{Type, Passenger, Journey, Segment}` | Remove picks before continuing |
| `Expect` | `BUNDLE_REQUIRED`, `INFANT_HAS_NO_TAB`, `CLEAR_RESTORES_PRICE` | Negative checks, usually with `NO_BOOKING` |

### Rule values

| Part | Values |
|---|---|
| `Passenger` | `ALL`, `ADULTS`, `CHILDREN`, or `1`, `2`, … (order on the ancillaries step) |
| `Journey` | `ALL`, `LAST`, or `1`, `2`, … (one-way `1`; round trip `1` outbound, `2` return; multi-city one per leg) |
| `Segment` (meals) | `ALL`, `LAST`, or `1`, `2`, `3` (legs inside the journey; a direct flight has only `1`) |
| `Pick` | `FIRST` … `TENTH`, `LAST` (position in the dropdown; `1`…`10` also work) · an exact option name (`Value Flex`, `SFML`, `20Kg Baggage`) · `CHEAPEST_PAID`, `MOST_EXPENSIVE`, `FREE` (meals/baggage only) · `NONE` |

- **Shortcut:** a plain string applies to everything, e.g. `"Meals": "FIRST"` means every passenger, every journey, every segment.
- **Order:** rules run top to bottom, and a later rule overrides an earlier one for the same card. Write "everyone `FIRST`" first and the exceptions after it.
- **Missing parts** default to `ALL`; a missing `Pick` means `NONE`.

### How journeys and segments are found (works for any supplier)

Cards are matched to journeys by **route**, not by the supplier's label text. Journey 1 is CAI-DXB, so its segments are the meal cards running from CAI until one lands in DXB (CAI-RUH, then RUH-DXB). The journey routes come from the baggage cards, otherwise the bundle routes, otherwise the searched routes. Only if the routes can't be matched does it fall back to the card labels ("Onward · Leg 1" / "Return · Leg 1"); the log says when that happens.

## Different suppliers

The page is the same NDC UI for every supplier; only the data differs, and everything is read from the page while the test runs. **No code changes are needed**, only JSON.

| Situation | Behaviour |
|---|---|
| Different names, prices, currency | Read when picked; every later page is checked against what was picked |
| Direct or connecting (1–3 segments) | Uses the cards actually shown; `LAST` is always the final leg |
| A rule matches nothing (no bundles, `Segment: 2` on a direct flight, `THIRD` when only 2 options, no `FREE` meal) | Logged as *"Not offered on this flight, skipped"*; the scenario continues |
| Nothing the scenario asked for exists | `BOOK` / `HOLD_THEN_PAY` is **skipped before booking** (no wallet cost) |
| Exact option name not offered | **Fails**, listing the offered options (a name is supplier-specific, so it's a data mistake) |
| No bundles / bundle optional | `BUNDLE_REQUIRED` is skipped with the reason |
| No meals/baggage at all | Meal/baggage rules are logged and skipped; `INFANT_HAS_NO_TAB` and `CLEAR_RESTORES_PRICE` are skipped |
| Bundle card without per-passenger prices | The bundle price is taken from what the fare breakdown added |
| Hold not allowed | `HOLD_THEN_PAY` is skipped with the airline's message |
| "Price Update – fare has changed" dialog after Book Flight | Accepted automatically |
| No flights on the date | Skipped: "update Defaults.Routes" |
| Email / Phone pre-filled and locked by the branch | Left as is (logged), the rest of the form is filled |
| A meal/baggage dropdown with no options | Logged as not offered and skipped |

## Running

| Goal | Command |
|---|---|
| All enabled scenarios | `mvn test -Dtest=Ancillaries_TC` |
| By name (wildcards, comma-separated) | `mvn test -Dtest=Ancillaries_TC "-Dscenario=OW 1A+1C*,RT*"` |
| By tag | `mvn test -Dtest=Ancillaries_TC -Dtags=smoke` |
| Another supplier for every scenario | `"-Dsupplier=Fly Nas NDC"` |
| **Dry run of any scenario** (no booking, passenger-page checks only) | add `-Dflow=NO_BOOKING` |

In IntelliJ, run `Ancillaries_TC` and put the `-D` options in the run configuration's VM options.

Every `BOOK` / `HOLD_THEN_PAY` run creates a real staging booking and moves wallet balance (≈10–17k EGP per one-way). Use `-Dflow=NO_BOOKING` to try a new scenario first.

## What every run checks

| Page | Prices | Data |
|---|---|---|
| **Passenger page: step 1** | Base total captured before any ancillary | Bundle dropdown shows the chosen bundle; bundle card lists its services |
| **Passenger page: step 2** | Each card's price; fare breakdown *Bundle Fare*, *Meal Preference*, *Extra Baggage*, *(+) Ancillaries & SSR*, *Total Amount*; *Net Payable* | One tab per adult/child (none for infants); one baggage card per journey; same legs for every passenger; each card shows exactly the chosen code, description and price; untouched cards empty |
| **Confirmation** | *Meal (+)*, *Baggage (+)*, *Bundle (+)*, *Total Amount* | Each passenger's row lists exactly their meals (`- BB Meal (BBML)`), `-` when none; Bundle column shows the bundle; Extra Baggage per passenger and route with description and price; Bundle Details per route lists exactly the chosen services |
| **E-Ticket** (new tab) | Same lines + *Total* | Same as confirmation |
| **Itinerary** (Search Booking → order link) | Costing & Payment: *Total SSR*, *Meal / Baggage / Bundle Amount*, *Total Booking Amount*, *Total Cost Payable*, *Amount Received* | Segment Ancillaries: meals under the right leg (code, price, description), baggage per passenger, "no ancillaries" badge; Journey Details *Bundle Services* per journey |

`HOLD_THEN_PAY` checks the confirmation and itinerary while the booking is on hold, then pays it (Search Booking → Lock → Take Control → Confirm To Pay → Pay). After payment it checks the confirmation, e-ticket and itinerary again, including *Amount Received* = full total.

## How checks are reported (severity, hard vs soft, money precision)

**Severity.** Every failure message starts with a severity tag, so money problems stand out in the report:

| Tag | Meaning |
|---|---|
| `[CRITICAL]` | Money: amounts, totals, charged / received, ledger, wallet, anything that would be charged by mistake |
| `[MAJOR]` | Wrong data: names, codes, routes, counts that aren't money, missing sections |
| `[MINOR]` | Display only, e.g. empty Bundle column, missing route label, missing badge |

**Hard vs soft.**
- **Soft** checks are collected and reported together at the end, so one booking shows every problem.
- **Hard** checkpoints stop the run immediately. Their message starts with `HARD CHECKPOINT:`, and everything collected so far is still reported. They sit where continuing would cost money or make no sense:

| Hard checkpoint | Why |
|---|---|
| **Money gate before Book / Hold:** fare breakdown Bundle / Meal / Baggage / Ancillaries & SSR / Total Amount / Net Payable, and base fare + taxes unchanged | Never book or hold at a price already known to be wrong; no wallet money is spent |
| Booking / hold created (GDS PNR shown) | Nothing else can be checked without it |
| Pay after hold shows "successfully Confirmed" | The paid checks depend on it (this suite's own check; the shared one never fails, see below) |
| Confirmation **Total Amount** | The total of the booking just created |
| Itinerary **Amount Received** | What was actually charged |
| Negative scenarios: no bundle → can't continue; infant has no tab; clearing restores the price | It's the whole purpose of the test |

**Money precision.**
- Amounts are compared at the precision the page shows them: **exactly (±0.00)** where it shows decimals (`273.00`, `1914.0`, `18,855.4`), **±0.50** where it rounds to whole units (`16,667`). Amounts summed from several lines add up their allowances.
- The old fixed ±1.00 hid sub-unit differences. The new rule found the Total Amount / Net Payable mismatches listed under *Open issues*.
- The base is taken from the exact *Base fare* and *Tax & Fees* rows, then rounded per `Defaults.Rounding` (default `UP`) the way the system does. Totals are summed first, then rounded: 10,651.5 + 10,651.5 = 21,303, not 10,652 + 10,652.

### All checks

| # | Where | Check | Severity | Kind |
|---|---|---|---|---|
| 1 | Pax page | Bundle Fare = Σ bundles (unit price × passengers of each type) | CRITICAL | Gate |
| 2 | Pax page | Meal Preference = Σ meals | CRITICAL | Gate |
| 3 | Pax page | Extra Baggage = Σ baggage | CRITICAL | Gate |
| 4 | Pax page | (+) Ancillaries & SSR = 1 + 2 + 3 | CRITICAL | Gate |
| 5 | Pax page | Total Amount = rounded base + rounded taxes + ancillaries | CRITICAL | Gate |
| 6 | Pax page | Net Payable = exact (unrounded) base + taxes + ancillaries | CRITICAL | Gate |
| 7 | Pax page | Every *Base fare* / *Tax & Fees* row unchanged after adding ancillaries | CRITICAL | Gate |
| 8 | Pax page | Cards nobody chose stay empty (nothing charged by mistake) | CRITICAL | Soft |
| 9 | Pax page | Each meal / baggage card price = the price picked | CRITICAL | Soft |
| 10 | Pax page | Bundle shows a price for every passenger type in the booking | CRITICAL | Soft |
| 11 | Pax page | Card code / description / baggage text = what was picked | MAJOR | Soft |
| 12 | Pax page | Bundle dropdown shows the chosen bundle; card lists services | MAJOR | Soft |
| 13 | Pax page | Tabs = adults + children; one baggage card per journey; same legs for every passenger; one bundle per journey | MAJOR | Soft |
| 14 | Pax page | Every meal / baggage card shows its route | MINOR | Soft |
| 15 | Booking | GDS PNR shown (booking / hold created) | CRITICAL | **Hard** |
| 16 | Booking | Status "Confirmed" for a booked ticket | CRITICAL | Soft |
| 17 | Hold → pay | "successfully Confirmed" after Pay | CRITICAL | **Hard** |
| 18 | Hold → pay | Confirmation Total after payment = total on hold | CRITICAL | Soft |
| 19 | Confirmation & E-Ticket | Base fare and Taxes = captured base (unchanged by ancillaries) | CRITICAL | Soft |
| 20 | Confirmation & E-Ticket | Bundle (+), Meal (+), Baggage (+) = Σ of each | CRITICAL | Soft |
| 21 | Confirmation | **Total Amount** = exact total (= Net Payable) | CRITICAL | **Hard** |
| 22 | E-Ticket | Total = expected total | CRITICAL | Soft |
| 23 | Confirmation & E-Ticket | Number of baggage lines = number bought; each baggage price | CRITICAL | Soft |
| 24 | Confirmation & E-Ticket | Each passenger's meals (code + description), baggage route / description | MAJOR | Soft |
| 25 | Confirmation & E-Ticket | Bundle Details: card per route, exact services | MAJOR | Soft |
| 26 | Confirmation & E-Ticket | Passenger table Bundle column shows the bundle | MINOR | Soft |
| 27 | Itinerary | Bundle / Meal / Baggage Amount, Total SSR, Total Cost Payable (exact); Total Booking Amount (whole total rounded up) | CRITICAL | Soft |
| 28 | Itinerary | **Amount Received** = exact total (= Net Payable) | CRITICAL | **Hard** |
| 29 | Itinerary | Meal / baggage items per passenger: count and price | CRITICAL | Soft |
| 30 | Itinerary | Meal under the right leg, description; Bundle Services per journey | MAJOR | Soft |
| 31 | Itinerary | "No ancillaries" badge for passengers with nothing | MINOR | Soft |
| 32 | AR Ledger | Credit − Debit for the booking reference = exact total (= Net Payable) | CRITICAL | Soft |
| 33 | Wallet | Top Up Balance before payment − amount charged = ledger Running Balance | CRITICAL | Soft |
| 34 | Itinerary | Sidebar Costing card Total Booking / Total Sales / Total Net = the Costing & Payment tab | CRITICAL | Soft |
| 35 | Itinerary | Total Sales − Total Net = Total Profit | CRITICAL | Soft |

**Two shared checks never fail.** `SearchBookingBranch.SuccessPayAfterHoldAssertion()` and `PaxDetailsPage.AssertThatTicketIsHoldSuccessfully()` record their text check in a SoftAssert that is never asserted, so it can't fail (only their waits can). This suite does its own hard checks instead (#15, #17). The shared files weren't changed; worth fixing for the existing `Booking_TC` too.

**Wallet & ledger (#32, #33).** The Top Up Balance is read in the payment section right before Book / Pay. After the booking, the AR Ledger (Reports → AR Ledger, today, the scenario's branch) is searched for the booking reference, retrying while posting lags. Confirmed live on TahaBranch: booking `NDCEG-BR-F261011eShXfu` is one row with Credit **46,853**, Debit 0 and Running Balance **525,563.367**, which equals Top Up Balance before (572,416.367) − 46,853. If another transaction hits the same branch between the two reads, #33 can differ; the message shows all three values.

## Scenarios in the file

| Name | Trip | Flow | What it covers |
|---|---|---|---|
| OW dry run - per-leg rules, no booking | OW | NO_BOOKING | Leg-level rules, override with `NONE`, `ADULTS` baggage |
| OW 1A+1C - bundle only | OW | BOOK | Bundle alone |
| OW 2A - meal on every leg for every passenger | OW | BOOK | Meal per segment per passenger |
| OW 1A+1C - different baggage per passenger | OW | BOOK | Baggage per passenger |
| OW 1A+1C - free meals pax 1, paid meals pax 2, baggage pax 1 | OW | BOOK | Free (0-priced) and paid meals |
| OW 1A+1C - per-leg control | OW | BOOK | Different meal per leg, per passenger |
| OW 1A - change meal and baggage before booking | OW | BOOK | Only the final choice is charged |
| OW 1A+1C+1I - everything, infant gets nothing | OW | BOOK | Infant excluded |
| OW 1A+1C - hold then pay, everything | OW | HOLD_THEN_PAY | Prices kept through hold and payment |
| RT 1A+1C - everything on both journeys | RT | BOOK | Bundle, meals, baggage on both journeys |
| RT 1A+1C - different bundle and baggage per journey | RT | BOOK | Journey-level bundle and baggage |
| RT 2A - meals outbound only, baggage return only | RT | BOOK | Journey filters |
| RT 1A+1C - hold then pay, everything | RT | HOLD_THEN_PAY | |
| MC 1A+1C - everything on every leg | MC | BOOK | |
| MC 2A - pax 1 meal on first leg only, pax 2 baggage on last journey only | MC | BOOK | Uneven selection, `LAST` journey |
| MC 1A+1C - hold then pay, everything | MC | HOLD_THEN_PAY | |
| Negative - bundle is required to continue | OW | NO_BOOKING | `BUNDLE_REQUIRED` |
| Negative - infant has no ancillaries tab | OW | NO_BOOKING | `INFANT_HAS_NO_TAB` |
| Negative - clearing meals and baggage removes their price | OW | NO_BOOKING | `CLEAR_RESTORES_PRICE` |

## Live results (2026-10-11, Admin, branch Rana, flynas CAI→RUH→DXB, 20 Oct 2026)

| Run | Result |
|---|---|
| 4 `dryrun` scenarios (per-leg rules + 3 negatives) | ✅ All passed. Rules applied exactly as written: leg 1 `FIRST` for both, leg 2 `SECOND` for pax 1 and `NONE` for pax 2, baggage for `ADULTS` only |
| `RT … everything` and `MC … everything` with `-Dflow=NO_BOOKING` | ⏭ Skipped: **No Flights Found** on staging for CAI⇄DXB (20 Oct with a 27 Oct or 5 Dec return / second leg). The search was filled correctly ("CAI → DXB & Back", "CAI → CAI (Via: DXB)"). Needs a route and dates with availability |
| `OW 1A+1C - per-leg control` (real booking `NDCEG-BR-F261011CIEmvZ`) | ⚠️ Every price and every meal/baggage detail passed on all 4 pages. Failed only on two app issues: the empty Bundle column, and the split bundle services (see below) |
| Earlier (previous format) `OW … free and paid meals` (bookings `NDCEG-BR-F261011xbBKWk`, `NDCEG-BR-F261011BjpSzu`) | ⚠️ Same picture: everything passed except the Bundle column |

## Open issues found

1. **App: passenger table "Bundle" column is empty ("-")** on the Booking Confirmation page and the E-Ticket, although a bundle was bought (it does appear in Bundle Details, the fare lines and the itinerary).
2. **App: bundle services containing a comma are split into separate items** on the Confirmation page, the E-Ticket and the Itinerary. "40KG Check-in Baggage (BULK, FRAG)" (the "Plus" bundle) is shown as "40KG Check-in Baggage (BULK" + "FRAG)". The passenger page shows it correctly. The test reports this as *"same services but split differently"* with both lists.
3. **App: "Price Update – The fare has changed" dialog** can appear right after Book Flight (seen with an infant: EGP 6,944 → 11,074). This suite accepts it; the existing `Booking_TC` flows don't handle it.
4. **Hold is not available on flynas** for CAI-DXB ("Hold is not available for this flight…"), so `HOLD_THEN_PAY` scenarios skip until a hold-enabled route or supplier is used. Al Jazeera (ElJazeeraV4Egy) does show Hold.
5. **No round-trip / multi-city availability** for CAI⇄DXB on the tried dates in staging, so RT and MC scenarios skip until `Defaults.Routes` points at dates with flights.

## Not covered yet / candidates for manual testing

- Seat selection (shown on the same step; out of scope here).
- Insufficient wallet balance where the base fare fits but base + ancillaries doesn't.
- Hold expiry before payment; ancillary price or availability changing between hold and payment.
- Browser refresh / back on the ancillaries step; double-click on Book.
- Portal/Agency side of the same flow.
- Multi-city with 3+ legs (supported by the search, not yet run).

## Second supplier: Al Jazeera (ElJazeeraV4Egy, branch TahaBranch), 2026-10-11

Dry run (`OW dry run - per-leg rules`) on the same code, with no supplier-specific changes:

| Check | Result |
|---|---|
| Branch form (Email pre-filled and locked) | ✅ Left as is, rest of the form filled |
| Bundle "Basic" priced 0 EGP | ✅ Recorded at 0; services read |
| Meals routed via Kuwait (CAI-KWI, KWI-DXB), different names/prices | ✅ Per-leg rules applied; fare breakdown 1,543.4 = 471.6 × 2 + 600.2 |
| Extra baggage | ⚠️ Card shows **no route** ("→" only) and its dropdown is **empty**: logged as not offered, and the route check fails |
| Hold | Available (Hold button shown) |

6. **App: Al Jazeera extra-baggage card has no route and no options.** The header shows "→" with no airports and the list is empty. The structure check reports *"extra baggage card shows no route"*.

## Money findings from the precise checks (2026-10-11, Al Jazeera, TahaBranch)

7. **Rounding rule (by design):** only the *Total* display fields round the Base fare total and the Taxes total **up**: the passenger page *Total Amount* rounds the Base fare total and the Taxes total up and then adds ancillaries (13,776 + 600.2 = 14,376.2), and the itinerary *Total Booking Amount* rounds the whole total up (14,375.7 → 14,376.00). Everything that is **paid** is exact: *Net Payable*, the confirmation and e-ticket Base fare / Taxes / Total, the itinerary *Total Cost Payable* / *Amount Received*, the AR Ledger and the wallet deduction (14,375.7). The suite checks each against the right one; confirmed by booking `NDCEG-BR-F261011fozVAZ`.
8. **App: amount charged below the total shown.** Round trip `NDCEG-BR-F261011eShXfu` (base 43,081 has no decimals, so rounding doesn't explain it): Total Amount **46,853.8**, but Net Payable, the AR Ledger credit and the wallet deduction are all **46,853**, i.e. 0.80 EGP less. Stays `[CRITICAL]`.
9. **Not an app issue (corrected).** The itinerary did show the booked meal (SRML, Shrimp Biryani, 600.2). The test read the meal code with a flynas-specific locator (code in an inner span); on Al Jazeera the code follows an icon, so it read as empty. Fixed: the code is now read from the whole item-name text.

### Wallet and ledger: verified live (`NDCEG-BR-F261011fozVAZ`, TahaBranch)

| Check | Value | Result |
|---|---|---|
| Top Up Balance before Book | 511,187.667 | read from the payment section |
| AR Ledger credit for the booking | 14,375.7 | = Net Payable ✅ |
| AR Ledger running balance | 496,811.967 | = 511,187.667 − 14,375.7 ✅ |
| Confirmation Total / E-Ticket Total / Amount Received | 14,375.7 | ✅ |
| Itinerary Total Booking Amount | 14,376.00 | whole total rounded up by design ✅ |

