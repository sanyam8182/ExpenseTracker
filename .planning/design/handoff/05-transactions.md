# 05 — Transactions

Requirements: UI 01, CAT 03, TXN 01 to TXN 03, MEM 02, MEM 04, BUD 04, AC 64. Foundations: `README.md`.

**Purpose:** one row per logical payment (an SMS and a notification for the same payment are one row with source aliases), filters, details, and corrections. Only the person who made a purchase can change, delete or link a refund to it.
**Route:** tab 2.

## A. List

- Top bar: title "Transactions", sync chip. Under it a month selector pill ("October ▾").
- **Filter chip row** (horizontal, 48 dp touch): "Filters", then one chip per active filter with a remove icon ("Fashion ✕"); when no filter is active the row shows "Filters", "All spenders", "All categories".
- **List** grouped by day with day headings ("Today", "Yesterday", then "Sat 3 Oct") in 14 sp secondary; each day is one white list card with hairline dividers.
- **Row:** merchant or payee (16/600) plus tags, meta line (category and account, 14 secondary), amount right-aligned (16/600). Other person's rows begin with their name ("Meera, Big Bazaar") and read "Groceries, spent by Meera". A refund shows "+₹500" in success colour and the tag "Refund"; meta "Fashion, linked to 28 Sep purchase".
- **Tags** (12 sp, pill, icon + words): "Shared", "⇧ Pending sync", "Refund", "🗑 Deleted" (row also struck through at full contrast). Use the Material Symbols equivalents.
- Extended FAB "Add expense". Bottom navigation, Transactions selected.

### Filters (modal sheet)
Title "Filters". Sections with labels above chips: Month (October, September, August), Spender (Everyone, Me, Meera), Category (all 11, shown as chips with "More"), Account (e.g. "HDFC card 1234", "SBI account 5521"), Payment method (UPI, Card, Cash), Review status (Counted, Needs review, Deleted). The sheet scrolls; the button stays pinned and shows the expected count ("Show 12 results"), with a text button "Clear all". Filters combine with AND within different groups and OR within one group (design decision; confirm).

### States
Default; loading (skeleton rows); empty month ("No expenses yet…" same copy as Overview); no results ("Nothing matches these filters" / "Try fewer filters or another month." / "Clear filters"); error (banner, local data visible). Not yet designed: long merchant names, very large amounts, 100 or more rows (use paging; amounts never truncate).

### Behaviour
Tap a row opens details. The list keeps scroll position and filters across navigation and rotation. Pending-sync rows count locally immediately (TXN 01).

## B. Details

Top bar: back arrow, "Transaction". Amount (`amount-hero`), "Myntra, today", category chip (and "Shared" tag).
Key-value card (14 sp, label left secondary, value right): Date ("5 October, 2:14 pm"), Account ("HDFC card ending 1234"), Method ("Card"), Seen in ("SMS and notification"). Own transactions only: action row **Edit**, **Link refund**, **Delete** (outlined, 48 dp; Delete in error colour). **History** section: each audit entry has who, what, when ("Created from SMS, 2:14 pm. Notification joined it, 2:15 pm.", "Category changed from Other to Fashion by you, 3:10 pm."). History is retained for the life of the dataset (CAT 03).

### Someone else's transaction
Shows what is shared only: Spent by, Date, Method; no account or alert detail. No edit actions. Info card "Only Meera can change this purchase." History shows "Added by Meera, 4 October, 6:41 pm."

## C. Edit

Top bar: close icon, "Edit transaction". Fields (label above): Amount (₹ amount field), Date (date picker), Category (choice chips). Note under: "Changing the amount, date or category moves the whole purchase. Totals update once." Button "Save changes". Only these three fields can change (CAT 03). Moving the amount or category moves the full amount across the old and new projections atomically in the local database; record actor, time, prior and new values, operation ID, and base and result version.

### Date moves out of sharing (dialog)
Title "Move before sharing started?" Body "This date is before sharing started on 1 October. The purchase will no longer count in the shared budget." Buttons "Cancel", "Change date". Rule: a correction that moves a date outside the active interval removes its shared attribution; moving it inside creates attribution only after all review and eligibility gates pass (MEM 04). For date-only entries on the join or end day, ask whether the purchase was before or after the boundary and do not share while ambiguous (BUD 04). Closed shared periods stay frozen; only the personal copy changes.

## D. Delete and restore

Delete is a soft delete with no confirmation dialog (it is reversible): the row leaves the list, totals update once, and a snackbar reads "Deleted Myntra ₹1,299" with **Undo**. Deleted purchases are restorable from Settings > Deleted items. Restoring re-runs validation, duplicate and membership gates, keeps the original ID, and appends a restore event; it may land in Review.

## E. Link refund

Top bar "Link refund". Large "+₹500" and "Myntra refund, 4 October". Title "Which purchase was refunded?" Radio rows of candidate purchases ("Myntra, 28 September — Fashion, ₹1,299"); selected row tint + outline + check. Info: "The refund lowers Fashion for September, the month of the purchase. It does not change September's limit or add room to October." Button "Link refund". Rules (TXN 03): link to the original purchase and apply to its month and category; cumulative refunds cannot exceed the purchase amount (excess goes to Review); a duplicate refund delivery adjusts once; an unlinked credit never becomes a refund automatically; closed shared history follows MEM 04.

## Accessibility
Row spoken label: "Myntra, Fashion, one thousand two hundred and ninety-nine rupees, shared". Tags are part of the label. Struck-through deleted rows are announced as deleted. Filter chips announce selected state.
