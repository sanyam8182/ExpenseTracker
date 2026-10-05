# 01 — Overview, Pocket and capture feedback

Requirements: UI 01, BUD 02, BUD 05, CAT 02, FBK 01, FBK 02, FBK 03, CAP 04, AC 21, AC 37, AC 64. Foundations: `README.md`.

## A. Overview

**Purpose:** answer "how much is left to spend this month?" and surface only what needs attention.
**Route:** start destination of the app after setup. Tab 1 of 4.

### Layout (compact, top to bottom)

1. **Top bar** (64 dp): title "Overview" (headline), month selector pill "October ▾" next to it, sync chip right, overflow menu (Settings and recovery). Past months are viewable; their limits are locked.
2. **Budget switch** (only for an active invitee): segmented control "Personal" | "Shared with Rahul". Selected segment = tint + outline + check. Hidden for solo users and owners (an owner has one budget: personal if solo, shared once converted).
3. **Hero card** (24 dp radius, `hero` fill, 20 dp padding, minimum height 196 dp, grows with content):
   - Label "Left to spend" (14 sp, `onHeroSecondary`). When left is 0 or less the label is "Over budget by" and the amount shows the positive overshoot.
   - Amount (`amount-hero`, `onHero`), e.g. `₹6,520`.
   - Two short lines: "of ₹15,600 in limits" and "26 days to go".
   - If known-amount review items exist: two short lines "Not counting" / "₹2,340 pending" (CAT 02: the figure is not complete while items are pending).
   - Pocket bottom-right, 100 to 160 dp wide, drop shadow. If text would collide with Pocket (large font), shrink Pocket to 72 dp, then hide him; never truncate or hide text.
   - No limits set: label "Spent this month", amount = total net spent, line "26 days to go", no Pocket.
4. **Pending-review row** (24 dp radius, `surfaceVariant`, amber inbox icon): "Pending review ₹2,340 (3 items)" (title-small), "Balances are incomplete" (14 sp), chevron. Opens Review. Shown only if known-amount pending count is above 0.
5. **"Needs a look"** (title-small): one white list card, rows separated by hairlines.
   - Row: category name (16/600), right-aligned "₹4,400 of ₹4,000" (14 secondary), status line with icon and words, thin progress bar (6 dp, track `outlineVariant`, fill = status colour; over budget = full bar).
   - Content: only categories over budget or at or above the user's first warning threshold. Order: over budget first, then by percentage used, highest first.
   - Status words: "Over budget by ₹400" (alert-circle, error) and "80% used" (alert-triangle, warning). Use the user's configured percentage in the words.
   - None qualify: one line "All categories on track."
6. **On-track row:** check icon + "2 more on track. 7 have no limit." + chevron. Counts: categories with a limit that are below the threshold, then categories with no limit.
7. **Extended FAB** "Add expense" (56 dp, bottom-right above the nav bar).
8. **Bottom navigation.** Review badge = total inbox items (known-amount plus unknown-amount plus foreign-currency), minimum 20 dp, number 12 sp.

### Calculation (Design decision, BUD 02 compliant)

- `limitsTotal` = sum of the limits of categories that have a limit configured for the viewed month (zero counts as 0).
- `spentInLimited` = net spent (counted purchases minus linked refunds, excluding soft-deleted) in those categories.
- `left = limitsTotal − spentInLimited`. Spending in no-limit categories is not in this figure.
- Pocket pose uses `left / limitsTotal`. If `limitsTotal` is 0, show no level.
- Pending review items are never subtracted (CAT 02, D-05). Do not label the result "complete" while unresolved financial items exist.
- "Days to go" = calendar days remaining after today in the month ("Last day" on the last day). Past months: omit the line.

### Data needed
Month, budget (personal or shared), limits per category, net spent per category (paise), thresholds, pending known-amount total and count, unknown-amount and foreign-currency counts, sync state, show-Pocket preference.

### States
Default; loading (skeleton: hero block, row block, two list rows, no Pocket); first month with no expenses (Pocket Full, "No expenses yet" / "Bank alerts appear here by themselves, or add one.", row "Set limits for October — Optional. See what's left to spend."); no limits set (hero "Spent this month", card "No limits set / Set a limit for a category to see how much is left. Pocket appears once there is something to measure." with "Set limits"); sync failed (see `08`); access revoked (see `08`); stale and pending (chip plus "May be out of date. Last synced 22 min ago." on shared figures); offline (quiet banner, local data fully usable).

### Behaviour
- Tap hero: no action. Tap pending row: Review. Tap a "Needs a look" row: Budget limits for that category (design: open the Budget tab scrolled to it). Tap on-track row: full category list (**not yet designed**, see Open items).
- Changing month reloads figures; past-month limits are read-only.
- After an expense is saved, the hero amount and Pocket level animate (`meter`, 400 ms); Pocket may play Spending once.
- The Review badge updates immediately after a resolve.

### Shared budget additions (requirement, not yet designed on this screen)
Shared reports identify an "Owner opening amount" and "Prior retained spending" aggregate and per-spender contribution for the current epoch (SYN 03, HIST 01). The Members screen shows the two labelled rows (see `03`); the Overview presentation is open.

### Accessibility
Hero is one semantics node: "Left to spend, six thousand five hundred and twenty rupees, of fifteen thousand six hundred in limits, twenty-six days to go. Not counting two thousand three hundred and forty rupees pending." Progress bars expose the percentage. Pocket is decorative (`contentDescription = null`) unless "Show Pocket" is on and the pose adds no information.

### Open items
Full category list screen; past-month hero wording; shared-budget Overview rows; third line for unknown-amount and foreign-currency counts on the pending row (currently only on Review).

---

## B. Capture feedback sheet

**Purpose:** confirm what was just saved and what is left, after the local commit (FBK 01). **Route:** modal bottom sheet over whatever is open; also opened by tapping the capture notification.
**Timing:** the local save of a received alert completes within 2 seconds (D-11); show the sheet only after the durable commit.

### Common structure
Handle, status line (icon + words, 14/600), title (20/600), category chip with "Change", content block, one filled button. Sheet scrolls above its button at large font sizes.

### Variants

| # | Variant | Status line | Title | Content | Buttons | Pocket |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Counted | ✓ "Saved. Counted in Fashion" (success) | "₹1,299 at Myntra" (no merchant: "₹1,299") | "Left in Fashion" / `₹2,701` (amount-hero) / "of ₹4,000" / progress bar of spent share | "Done" | Spending pose, 92 dp |
| 2 | Threshold crossed | same | same | warning container line "⚠ Fashion is at 80%" above the block; bar in warning colour | "Done" | none |
| 3 | Over budget | same | same | error container line "! Over budget by ₹400"; "Fashion this month" / `₹4,400` / "of ₹4,000. It still counts."; red bar | "Done" | none |
| 4 | Needs review | "✉ Saved. Doesn't count yet" (warning) | "₹640 at an unknown shop" | "Pick a category to count it. Until then your balances are incomplete."; warning container "Pending review ₹2,980 (4 items)" | "Review now" (filled), "Later" (text) | none |
| 5 | Invitee, two budgets | as 1 | as 1 | two rows: "Personal, left in Fashion" `₹2,701` of ₹4,000; "Shared with Rahul, left in Fashion" `₹1,100` of ₹3,000 | "Done" | none |

Rules:
- Owner sees one budget (personal if solo, shared once converted). An active invitee sees personal and shared (FBK 01).
- Unknown category never shows a fictitious category balance; it goes to Review (variant 4).
- A category with no limit shows "Fashion has no limit" and the amount spent this month instead of "Left in". A zero limit makes any positive spend over budget immediately.
- Threshold line appears once per eligible crossing (FBK 02): previous confirmed ratio below the threshold and new ratio at or above it. Duplicate delivery, retries and unchanged ratios show nothing. A sync jump across both thresholds shows one line naming the highest. A refund or correction that drops below a threshold re-arms it. Percentage lines are suppressed for no-limit and zero-limit categories. A second threshold at exactly 100% shows "Fashion is at 100%"; negative remaining is "Over budget".
- "Change" opens the category picker sheet (11 fixed categories); on choose, the amounts, bar and threshold line recompute and the sheet updates in place.
- Pocket appears only in variant 1.
- Capture feedback always means data is already saved on the phone; sync status is shown separately.

### Notifications (AC 21)
- Locked phone: generic text only. Counted: "Expense recorded". Needs review: "Needs review". No amount, merchant, category or balance.
- After unlock the notification opens the sheet above. Several alerts at once group into one notification.
- If the notification permission is denied, no notification; the same feedback appears in-app when the app is next opened.
- Content setting: "Lock screen details" in Settings (default Hidden).

### Accessibility
The sheet announces its status line first. Amounts are spoken as rupees and paise. Focus lands on the title; "Done" is last.

### Open items
Unknown-amount and foreign-currency capture variants; grouped-alert summary sheet; exactly 100% wording; notifications-denied in-app prompt.
