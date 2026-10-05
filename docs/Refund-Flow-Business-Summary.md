# Online Refund Automation — Coverage Summary

**Audience:** Product & Business stakeholders
**Last updated:** 2026-09-27

## What this covers

We've built automated tests that simulate a full refund journey for flight bookings, end to end, for two types of users:

1. **Admin staff** processing a refund directly from the Admin back-office.
2. **Travel agencies** requesting a refund themselves through the Agency Portal, which may or may not need Admin approval depending on the agency's settings — including an Admin **rejecting** that request instead of approving it.

Both journeys are tested against the real, live application — not a simulation. A passing run means the actual system behaved correctly, including real money movements (wallet debits/credits) and real accounting records (ledger entries).

## Why this matters

A refund touches several systems at once: the customer-facing booking, the agency's wallet balance, the admin approval workflow, and the accounting ledger. A bug in any one of these could mean a customer isn't refunded correctly, an agency's balance ends up wrong, or the company's books don't reconcile. Automating this flow means that every time something changes in the app, we can quickly confirm all of these pieces still work together correctly — without someone having to manually create a booking, refund it, and check the numbers by hand every time.

## What's tested — Admin flow

An Admin can:
- Create a booking (one-way, round-trip, multi-city, or "pay after hold")
- Take control of the booking and issue an online refund
- See a refund quote before submitting, and a confirmation with a refund reference afterward
- Have that refund appear correctly in the "Refunded Requests" list, correctly tagged and with the right amounts
- Open that request's detail popup and see the same figures (and, where applicable, the remark) confirmed there too
- See it correctly reflected as a matching debit entry in the company's AR Ledger report

**Status:** All four booking types are confirmed working end to end.

## What's tested — Agency Portal flow

An agency user can:
- Create any of the four booking types (one-way, round-trip, multi-city, "pay after hold") and pay for it from their wallet
- Request an online refund themselves, with no Admin involvement needed to start it
- See a refund quote and a confirmation that match what they were originally charged
- Depending on the agency's "auto-refund" setting:
  - **Requires approval:** the request sits as "Pending" until an Admin reviews and approves it. Once approved, the Agency Portal correctly updates to "Refunded", the ledger entry appears correctly, and — importantly — **the agency's wallet balance is credited back by the correct amount.**
  - **Fully automatic:** the refund completes immediately with no Admin step, and is still correctly reflected in the ledger.
- **Or, an Admin can reject the request instead.** When that happens, we confirm:
  - The Agency Portal shows the request as "Rejected", along with Admin's exact rejection remark
  - **The agency's wallet balance is left completely untouched** — no money moves on a rejected request
  - The company's AR Ledger shows **zero** debit for that booking — confirming a rejected refund never quietly posts a real accounting entry

**Status:** All four booking types are confirmed working end to end, for both outcomes an Admin can choose — approving or rejecting a pending request — plus the fully-automatic (no-approval-needed) path.

## Known gaps (not yet automated)

- Partial refunds — automation only covers full refunds so far.
- Refunding a booking that's already been refunded or cancelled.
- Visual/UI issues — automation checks the numbers and text, not layout or styling.

## Bottom line

The full refund journey — on both Admin and the Agency Portal, across every booking type, and every outcome (auto-approved, Admin-approved, or Admin-rejected) — is fully verified end to end against the real application, including confirming the money actually moves correctly (or, for a rejection, correctly does *not* move at all). The remaining gaps are edge cases (partial refunds, already-refunded/cancelled bookings), not the core refund mechanism.
