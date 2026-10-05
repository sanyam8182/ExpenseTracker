# Expense Tracker — User Flows and Information Architecture

**Date:** 5 October 2026
**Track:** Quick+ (flows → design system → screens)
**Sources:** `Expense_Tracker_Requirements.md` v0.2 (UI 01, FBK 01–03, CAP 01–06, MEM 01–04, HIST 01, REC 01–02, TXN 01–02, CAT 01–03, BUD 01–05), approved decisions D-01 to D-11 and D-13, Jira SCRUM-5 to SCRUM-62.
**Platform:** native Android 16, phone and foldable (Galaxy S25 Ultra, Galaxy Z Flip 5). Layout and visual style are decided here; behaviour is already fixed by the requirements.

## 1. Who uses it

One person, usually alone, sometimes sharing a budget with one other person.

| Situation | What the app shows |
| --- | --- |
| Solo | One personal budget |
| Owner of a shared budget | One budget, labelled shared. No separate personal budget |
| Invitee | Personal budget **and** the owner's shared budget, with a switch |

Roles are the same product: anyone can create a budget, share it with one person, or be joined to one shared budget.

## 2. Design principles (from the requirements)

1. **Never hide uncertainty.** Pending review, stale data, pending sync and "incomplete" balances are always visible, in words or icon, never colour alone.
2. **The balance answers one question:** "how much is left in this category this month?" Everything else supports that.
3. **Saving is instant and local.** Success feedback appears after the data is saved on the phone. Sync status is shown separately.
4. **Private by default.** Lock-screen notifications reveal nothing. Sharing disclosures are explicit when inviting and again in the notice right after joining; there is no Accept step.
5. **Review is a first-class place,** not a hidden queue. Items there do not count until confirmed (D-05).
6. **Honest limits.** The app says what it cannot do (Google editors can edit the Sheet; offline copies may remain).

## 3. Information architecture

### Navigation (phone): bottom bar, 4 destinations + top-bar actions

| Destination | Purpose | Notes |
| --- | --- | --- |
| **Overview** | Month, budget, category limit/spent/remaining, pending review line, sync status | Home. Budget switch for invitees |
| **Transactions** | All logical payments, filters, details, corrections | Filter chips: month, spender, category, account, method, review status |
| **Review** | Inbox of items that do not count yet | Badge shows number of pending items |
| **Budget** | Monthly limits, warning thresholds, members (invite / leave / remove) | Past months are read-only |

**Top bar actions (every screen):** sync status chip (tap = Refresh), overflow → Settings & recovery.
**Floating action:** "Add expense" on Overview and Transactions.

### Navigation (unfolded Z Flip 5 / larger widths)
Navigation rail instead of the bottom bar. Transactions and Review use two panes: list on the left, details on the right.

### Screen inventory

| # | Screen | Reached from |
| --- | --- | --- |
| 1 | Welcome and sign-in | First launch, signed-out |
| 2 | Setup: permissions (3 steps) | After sign-in; Settings |
| 3 | Setup: first budget limits | After permissions (skippable) |
| 4 | Overview | Home |
| 5 | Capture feedback sheet | After a captured expense; notification tap |
| 6 | Add expense (manual) | FAB |
| 7 | Transactions list + filters | Bottom bar |
| 8 | Transaction details (+ audit history, refund link, correct, delete) | List |
| 9 | Review inbox | Bottom bar; Overview pending line |
| 10 | Review item resolution | Inbox |
| 11 | Account confirmation | Prompt when a new account appears; Settings |
| 12 | Budget: limits | Bottom bar |
| 13 | Budget: warning thresholds | Budget |
| 14 | Members: invite | Budget |
| 15 | Shared-budget joined notice (invitee) | Shown once, right after the app joins automatically |
| 16 | Shared budget details and Leave (invitee) | Budget; the notice |
| 17 | Members: status, leave, remove | Budget |
| 18 | Settings and recovery hub | Overflow |
| 19 | Capture health (3 permissions, last capture, sources) | Settings |
| 20 | Remembered rules | Settings |
| 21 | Export / restore | Settings |
| 22 | Deleted items | Settings |
| 23 | Wipe local data | Settings |
| 24 | Sync issues and recovery (schema problem, blocked, revoked) | Sync chip; banners |
| 25 | Device handover (replace phone) | Settings → Export/restore |

## 4. Flows

### F1. First launch and setup
**Entry:** install, open app.
1. Welcome: what the app does, one line each on *on-device storage*, *no bank logins ever*, *shared budgets use Google Sheets*. → **Sign in with Google**.
2. Permission step 1 of 3: **Read bank SMS**. Explain why before the system dialog. Allow / Not now.
3. Permission step 2 of 3: **Read bank and payment-app notifications**. Explain, open Settings. If Android blocks it (restricted settings), show the extra "Allow restricted settings" steps *only then*.
4. Permission step 3 of 3: **Show my own alerts** (notification permission).
5. Capture readiness summary: three statuses, each with text + icon; any denied one says what is unavailable and that manual entry still works.
6. Optional: set category limits for this month (can skip, "No limit" everywhere).
7. Overview.

**Error paths:** sign-in cancelled → stay on welcome. Permission denied → continue with that source marked unavailable. Authorization expired later → quiet re-sign-in prompt, data untouched.
**Edge:** offline at first launch → sign-in needs a connection; message says so.

### F2. A bank alert arrives
**Entry:** SMS or allowlisted notification, app not open.
1. App saves it in the background (≤2 s). Lock screen shows a **generic** notification ("Expense recorded"). Details only after unlock.
2. After unlock, tap → **Capture feedback sheet**: amount, merchant (if known), category, remaining per applicable budget (owner: shared only; invitee: personal and shared), "Change category".
3. If confident → counted, sheet shows balances.
4. If not (unknown shop, ambiguous transfer, duplicate, new account, no date, foreign currency) → the item goes to **Review**; the notification says "Needs review"; the sheet says it is **not counted yet** and offers to review now.
5. A threshold crossing (80%, 100%) adds one line: "Fashion is at 80%".

**Edge:** several alerts at once → one grouped notification. Denied notification permission → feedback appears in-app only.

### F3. Review inbox
**Entry:** Review tab, badge, or "Pending review ₹X (n)" line on Overview.
1. List grouped by reason: *Unknown shop*, *Possible duplicate*, *Is this a transfer?*, *New account*, *No date*, *Unsupported currency*, *Couldn't read this alert*.
2. Tap an item → resolution screen with the evidence (safe excerpt, if any), and actions that depend on the reason: pick category, mark as purchase / transfer / not an expense, confirm account, set date, merge duplicate, enter missing details, dismiss.
3. After choosing a category: optional **"Remember this merchant and intent"** switch (off by default).
4. Confirm → item counts (or is excluded), balances update once, a short confirmation with Undo.

**Rules shown in the UI:** possible-duplicate cluster counts once in the pending line; unknown-amount and foreign-currency items are listed separately; remaining balance is labelled "incomplete" while items are pending.
**Empty:** "Nothing to review. New unclear alerts will appear here."

### F4. Add expense manually
**Entry:** FAB.
1. Amount (₹, large, numeric keypad), date (defaults to today), category (required, chip grid of the 11 fixed categories).
2. "More details" (collapsed): merchant, account, payment method, notes.
3. Save → saved locally → feedback sheet like F2.
**Validation:** amount > 0, date, category required; blocked save shows what is missing.

### F5. Overview
1. Header: month selector (past months viewable, limits locked), budget label (*Personal* / *Shared with <name>*); invitees get a **Personal | Shared** segmented switch.
2. Pending review line (only if > 0): "Pending review ₹X (n items)" and "Balances are incomplete".
3. Category cards (11 + totals): name, spent, limit, remaining, a progress bar plus text status: *On track*, *80% used*, *Over budget by ₹X*, *No limit*.
4. In a shared budget: "Owner opening amount" and "Prior retained spending" labelled rows, per-spender contribution.
5. Sync chip: *Synced 2 min ago* / *Pending* / *Failed* / *Stale* / *Access revoked*.

**Empty:** first month with no expenses: "No expenses yet. Alerts appear here automatically, or add one."
**Error:** sync failed → banner with Retry; local data always shown.

### F6. Transactions and corrections
1. List by date; each row: merchant/category, amount, small tags (*Shared*, *Pending sync*, *Deleted*).
2. Filter sheet: month, spender, category, account, payment method, review status.
3. Details: all fields, source aliases (SMS + notification merged), refund link, audit history.
4. Own records only: **Edit** (category, amount, date), **Delete** (soft; Undo snackbar), **Link refund**. Other person's records show no edit actions.
5. Date change across a join/leave day asks "Before or after?".

### F7. Budget and limits
1. Budget tab: month selector, per-category limit field (₹, blank = No limit, 0 allowed). Past months read-only with a lock icon; current and future editable.
2. Warning thresholds: default 80% and 100%; add/remove one more; values 1–100; owner sets shared, each user sets personal.
3. Shared budget: invitee sees shared limits read-only with "Only <owner> can change these".

### F8. Sharing: owner shares the Sheet
1. Budget → Members → **Invite someone**: email field, plain explanation ("We'll create a Google Sheet and share it with them. Google sends them an email. Their app joins by itself.") plus what they will see and the Google-editor warning, before anything is sent.
2. Confirm → app creates the Sheet and shares it. Status: *Shared, not joined yet* with **Remove access** (there is no Google invitation state, and no Accept step).
3. When the invitee's app joins and the owner's app syncs: status *Active since <date>*; the owner's budget becomes *Shared*.

### F9. Sharing: invitee joins automatically
1. The invitee's app finds the shared Sheet (when it opens or refreshes) and joins at once. No Accept or Decline.
2. **Joined notice**, shown once: "You joined <owner>'s shared budget", what the other person will see (merchant, amount, date, category, spender), "no way to hide a single purchase", what stays private (personal budget, purchases from before joining), "anyone with edit access to the Sheet can read or change it outside the app". Buttons: **Got it** and **Leave**.
3. Then *Joined, waiting for <owner>'s app to sync* until the owner's app confirms. If the person is already in another shared budget, the app does not join and says why.

### F10. Leaving and removal
- **Leave (invitee):** confirm → *Left. <Owner>'s app must remove access* until complete. New purchases are personal only.
- **Remove (owner):** confirm → *Removal pending* until access is confirmed removed, then *Ended*. Shared-period totals stay.
- **After ending:** the invitee keeps their personal budget; offline cached shared data is cleared on next connection (message explains offline copies may remain until then).
- **Rejoin:** new invitation, new Sheet; gap not imported.

### F11. Settings, recovery and data
- **Capture health:** three permissions, last successful capture, unsupported alert count, allowlisted sources.
- **Remembered rules:** list, remove.
- **Show Pocket:** on or off. Off removes the character everywhere.
- **Export and restore:** last export date, Export now (password), Restore (password + same Google account). Reminder after 7 days of unexported changes.
- **Deleted items:** restore own deleted records.
- **Wipe local data:** confirmation ("local data and history will be removed"), offers export first, warns if still in a shared budget.
- **Replace phone:** export → restore on new phone → retire old phone.
- **Sync issues:** schema problem, conflicts, recovery blocked (owner phone lost), access revoked.

### F12. Account confirmation
When a new bank account or card appears: bottom sheet "New account detected: HDFC card ending 1234. Is this yours?" → *Yes, mine* / *Not mine* / *Decide later*. Earlier alerts from it are re-checked once confirmed.

## 5. States every screen needs
Default, loading (skeleton), empty, error with recovery, success, offline, stale, pending sync, access revoked. Long text, ₹1,00,00,000 amounts and 200% font size must not break layouts.

## 6. Open design inputs
Brand personality, colour direction, light/dark, reference apps → asked next in the design system step.
