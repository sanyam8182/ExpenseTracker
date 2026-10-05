# 02 — Review inbox and resolutions

Requirements: CAT 02, CAT 01, CAT 03, CAP 03, CAP 04, CAP 05, TXN 01, TXN 02, RAW 01, D-05, D-06. Foundations: `README.md`.

**Purpose:** a first-class place for items that do not count yet. Nothing in Review counts in totals until the user confirms it (D-05: unresolved and Uncategorized items are excluded). Resolving updates one transaction and never creates extra counted spending.
**Route:** tab 3 (badge = total inbox items) and the pending row on Overview.

## A. Inbox

### Layout
- Top bar: title "Review" (headline) and sync chip.
- **Summary** (24 dp radius, warning container): "Pending review ₹2,980 (4 items)" (title-small) / "Doesn't count yet. Balances are incomplete." / "1 item has no amount. 1 is in another currency." The figure covers known-amount items only; a possible-duplicate cluster counts once; clusters already represented in counted spend are excluded (CAT 02).
- **Section "Needs your decision"** (known-amount items) and **"No amount or another currency"** (unknown-amount and non-INR items, listed separately and never in the figure). Each section is one white list card with hairline dividers.
- **Row:** neutral reason chip (surface-variant, icon + words), merchant or payee (16/600), meta line (14 secondary: source and age), amount right-aligned (16/600). Unknown amount shows "No amount" in secondary text; foreign currency shows the original, e.g. `$24.99`.
- Bottom navigation, Review selected.

### Reasons (fixed list, copy exact)
"Unknown shop", "Possible duplicate" (meta: "2 alerts, counts once"), "Is this a transfer?", "New account" (meta: "Card ending 4821, yesterday"), "No date", "Unsupported currency", "Couldn't read this alert". Sort: newest first within a section.

### States
Default; loading (skeleton rows); empty ("Nothing to review" / "New unclear alerts will appear here." with Pocket Idle; no badge); error (banner, local data visible); 100 or more items (paged list, count stays exact — design open).

### Behaviour
- Tap a row to open its resolution (full screen on phones; right pane when wide).
- After resolving: row leaves the list, summary figure and badge update, one snackbar "Counted in Dining and food delivery" with **Undo**; the balance changes exactly once. On wide screens select the next item.
- Undo reverses the resolution fully (counted or excluded).

## B. Resolution screens

Top bar: back arrow and the reason as title (20 sp). Large amount (`amount-hero`) and "Merchant, date" line.

### B1. Unknown shop
- Evidence box (`surfaceVariant`, 16 dp radius): small label "What the alert said. Kept on this phone until you decide." and the **sanitized excerpt**, e.g. "₹640 debited from card ending 1234 to ZXCV SHOP 4821 on 5 Oct." (RAW 01: OTPs, full account or card numbers and unrelated text are stripped before storage; if safe extraction is uncertain, show a generic failure item).
- "Which category?" 11 choice chips (names exact: Groceries; Dining and food delivery; Transport; Travel; Fashion; Entertainment; Health; Household items; Rent and housing; Utilities and bills; Other). Selected chip: tint, outline, check.
- Switch **"Remember this shop"** with help "Next time, count it as Dining. Stays on this phone." Off by default (CAT 01). The remembered rule is private, keyed by normalized payee plus context, sets intent and optionally a fixed category, affects future candidates only, and can be removed in Settings.
- Row "Something else? Transfer, not an expense, dismiss ›" opens a sheet with: "A transfer or repayment", "Not an expense", "Dismiss this alert".
- Pinned primary button: "Count in Dining and food delivery" (names the chosen category). With nothing chosen the button is visible but disabled and reads "Pick a category to count it".

### B2. Possible duplicate
- Text: "These alerts look like one purchase. They are 40 seconds apart with the same amount." (candidate rule: equal INR amount, compatible direction, within 120 seconds, compatible payee, no conflicting account or reference; this is a suggestion, never an automatic merge — CAP 05).
- Two evidence boxes with source and time: "SMS, 2:14 pm" and "Notification, 2:15 pm", each with its excerpt.
- Category field (chip showing the proposed category with a dropdown).
- Line "If it is one purchase, it counts once."
- Buttons: "Same purchase, count once" (filled), "Two separate purchases" (outlined), "Dismiss this alert" (text).
- "Same purchase" merges source aliases under one logical transaction ID and counts it once. "Two separate purchases" keeps both as separate candidates which each go through their own gates.

### B3. Is this a transfer?
- Three large radio rows: "A purchase" (You paid for something. You pick a category next.), "A transfer or repayment" (Money moved between people. It does not count as spending.), "Not an expense" (Anything else. It does not count.). Selected row: tint and outline.
- Switch "Remember Rohan K" with "Treat future payments here the same way."
- Primary button names the outcome: "Mark as transfer" (or "Choose a category" for a purchase, which continues to B1's picker). Disabled until a choice is made.
- UPI person-to-person payments with unclear purpose, EMI or loan repayments, and similar items stay in Review until confirmed (TXN 02).

### B4. Other reasons (specs from requirements; screens follow the B1 pattern)
- **New account:** "Is this card yours?" with "Yes, it's mine", "Not mine", "Decide later" (also appears as the account-confirmation sheet, see `04`). Confirming re-evaluates earlier candidates once using their original occurrence date (CAP 04); items without a usable date stay in Review until dated.
- **No date:** date picker, then count.
- **Unsupported currency:** shows the original amount and currency; it can never be confirmed as INR; offer "Add the INR charge by hand" (opens Add expense). No conversion is inferred (CAP 03).
- **Couldn't read this alert:** shows the sanitized excerpt if one exists, else a generic failure item; actions: "Add by hand", "Dismiss this alert".

### Accessibility
Reason chip text is part of the row's spoken label ("Unknown shop, ZXCV SHOP 4821, six hundred and forty rupees, SMS, two minutes ago"). Radio rows are selectable groups. Disabled primary exposes its reason.

### Edge cases
Dismiss deletes the excerpt; resolving deletes it; wipe deletes it. Excerpts never appear in logs, exports, backups or the shared Sheet (RAW 01).
