# 06 — Budget: limits and warning thresholds

Requirements: BUD 02, BUD 04, BUD 05, FBK 02, MEM 02, AC 15. Foundations: `README.md`.

**Purpose:** set monthly limits per category and the warning percentages. Owner sets shared limits and thresholds; each person sets their own personal ones.
**Route:** tab 4.

## A. Limits screen

- Top bar: title "Budget", sync chip. Month selector pill under it ("October ▾").
- **Card with one row per category** (11 fixed: Groceries; Dining and food delivery; Transport; Travel; Fashion; Entertainment; Rent and housing; Utilities and bills; Health; Household items; Other). Row: category name (16 sp) left, ₹ field right (120 dp wide, 48 dp high, right-aligned digits). Empty = "No limit" shown in secondary text; `₹0` is a valid limit (any spending is then over budget; percentage alerts are suppressed). Rows beyond the first few are folded behind "6 more categories — Show".
- **Second card:** row "Warning thresholds — 80% and 100%" with a chevron, and row "Members — Share this budget with one person" with a chevron (see `03`).
- Limits are stored per month: current and future months are editable; **past months are locked** with no rollover and no automatic copy to other months. A new month with no configured limit is "No limit", never a guessed copy of the previous month (BUD 05).
- Saving a limit takes effect for current and future evaluations only.

### Past month
Banner with a lock icon: "September is locked" / "Past limits can't be changed. Spent totals can still change if a purchase is corrected or refunded." Fields are grey with no border so they do not look editable. Spent totals may still recalculate under CAT 03 and TXN 03; closed shared history follows MEM 04.

### Invitee, shared view
Segmented control "Personal" | "Shared with Rahul" (selected = tint, outline, check). Under Shared: banner "Only Rahul can change these" / "Shared limits and warnings are set by the owner. You can still set your own personal limits." Shared limits are read-only (grey, no border). Under Personal the fields are editable as for a solo user.

## B. Warning thresholds

Title "Warning thresholds". Paragraph "Personal budget. Tell me when a category reaches these percentages. Tap a number to type it, or use the buttons." (For a shared budget the first words are "Shared budget.") Card with two rows: "First warning" and "Second warning", each a stepper: minus button (48 dp, content description "Decrease"), the value (`title` style, 20 sp, tap to type), plus button ("Increase"). Text button "Remove second warning". Info banner: "Changes apply to new spending only. No alerts are sent for what you already spent." Pinned button "Save".

Rules (FBK 02): one or two distinct whole numbers from 1 to 100; default 80 and 100; owner configures shared, each user personal; persisted per budget, effective for current and future evaluations; configuration changes start a new baseline with no retrospective alerts.

### Invalid
Both values equal: error under the fields, "The two warnings need different numbers." with an alert icon; the Save button is disabled with the same message as its reason (not hidden). Out-of-range typed values are rejected with "Use a whole number from 1 to 100."

## C. States
Default; loading (skeleton rows); past month (locked); invitee shared (read-only); error saving (field error "Couldn't save. Try again." and the value stays editable); offline (edits are local and instant, shared edits by the owner queue and show "Pending").

## D. Behaviour details
- Every mutation checks role locally, on queued operations and on sync import: an invitee's attempt to change a shared limit is rejected with no change (AC 15).
- Limit and threshold changes appear in the audit trail with version numbers (the owner is the single supported writer of shared limits).
- Amounts: integer paise, INR, shown with Indian grouping and no paise unless non-zero.

## E. Accessibility
Each limit field has a visible label and a spoken label ("Fashion limit, rupees"). Stepper buttons are labelled; the value is an editable semantics node with its range (1 to 100). Locked fields announce "locked, past month".
