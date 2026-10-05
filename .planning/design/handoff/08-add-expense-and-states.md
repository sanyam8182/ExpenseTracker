# 08 — Add expense and system states

Requirements: CAP 06, BUD 04, FBK 01, FBK 03, MEM 03, SEC 02, DAT 01. Foundations: `README.md`.

## A. Add expense (manual entry, F4)

**Route:** extended FAB "Add expense" on Overview and Transactions. Full screen (modal on wide windows).
**Rules (CAP 06):** manual entry needs a positive INR amount, a date and exactly one fixed category; merchant, account, method and notes are optional. A withdrawal is not a purchase; users record actual cash purchases here.

### Layout
Top bar: close icon, "Add expense". Fields, label above each:
1. **Amount** (label "Amount"): ₹ prefix in 28 sp, value in `amount-hero`, underlined (primary; error colour on error). Numeric keypad, Indian grouping as you type, paise optional. Help "Rupees and paise, like 1,299.50."
2. **Date** ("Date"): defaults to "Today, 5 October 2026"; opens the date picker. Manual entries use the entered date.
3. **Category** ("Category"): the 11 categories as choice chips (selected = tint, outline, check).
4. **More details** (collapsed row, expands inline): "Merchant (optional)", "Account (optional)" (dropdown of confirmed accounts, may be empty for cash), "Payment method (optional)" (UPI, Card, Cash, Other), "Note (optional)".
Pinned: filled button "Save expense". Content scrolls above it.

### States
- Empty: button visible but disabled (surface-variant), help under it "Add an amount and pick a category."
- Errors under their fields, with an alert icon: amount at 0 or empty → "Enter an amount above ₹0."; no category → "Pick a category."
- Filled: button enabled.
- Typed text, chosen chips and scroll position survive rotation and fold.

### On save
Commit to the local database (record, totals change event and outbox in one transaction), then show the capture feedback sheet exactly as for a captured alert (see `01`, variants 1 to 3). Manual entries are user-confirmed, so they count immediately subject to the counting gates in TXN 01 (soft-deleted, membership and date rules still apply).

## B. Overview and shared states

### Loading
Skeleton: a hero-shaped block (24 dp radius, `surfaceVariant`) with three bars, a pill-shaped block, the "Needs a look" title and two skeleton rows. Sync chip reads "Syncing". No spinner, no Pocket.

### First month, nothing yet
Top bar and chip, then Pocket (Full if limits are set, Idle if none are, about 150 dp), title "No expenses yet" (20/600), line "Bank alerts appear here by themselves, or add one.", card "Set limits for October" with "Optional. See what's left to spend." and a chevron, FAB "Add expense".

### Sync failed
Chip "⚠ Failed". Error banner with an alert-circle icon: "Couldn't sync" / "Your data is saved on this phone. Shared totals may be out of date." and a text button "Try again" (48 dp). Shared hero stays visible with "May be out of date. Last synced 22 min ago." Retry uses bounded backoff for transient failures and an explicit re-authorisation prompt when authorisation expired (data untouched).

### Access revoked (invitee)
Chip "🔒 Access revoked". Warning banner "Sharing with Rahul ended" / "Your personal budget and history are unchanged. Shared data saved on this phone is cleared the next time you're online." The personal hero remains; the Personal | Shared switch is gone. See `03`.

### No limits
Hero label "Spent this month", amount = total net spent, "26 days to go", Pocket in the Idle pose. Card "No limits set" / "Set a limit for a category to see how much is left. Pocket appears once there is something to measure." and an outlined button "Set limits". Per BUD 02: no "left" value, no percentage and no warning for no-limit categories. Pocket receives no limit and shows Idle; once a first budget is saved with a positive limit and nothing spent, he plays Budget ready once.

### Offline
A quiet banner appears under the top bar; no blocking UI. Copy proposed, not in the mockups: "You're offline. Everything saved here still works." Sign-in needs a connection; everything else is local.

## C. Not yet designed (from the design review)
Overview: invitee full states, zero-limit category row, grouped alerts. Review: loading, error, 100+ items. Sharing: invalid email, offline invite, join failed. Export: progress and overdue frames. Transactions: loading and extreme values. Per-screen reduced-motion annotations and pressed and focus visuals for every control (use Material defaults tinted with `primary` meanwhile).
