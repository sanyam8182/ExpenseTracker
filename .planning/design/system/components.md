# Expense Tracker — Component Inventory

**Date:** 5 October 2026
**Built from:** `tokens.md`, `flows.md`, `UI 01` of the requirements.
Each component lists its variants and the states it must design. "Used by" points to the screens in `flows.md`.

## Core

| Component | Variants | Key states | Used by |
| --- | --- | --- | --- |
| **Button** | Filled (primary), tonal, outlined, text, destructive text | default, pressed, disabled (explain why), loading | everywhere |
| **Extended FAB** "Add expense" | with label; collapses to icon on scroll | default, pressed | Overview, Transactions |
| **Text field** | filled, with prefix ₹ for amounts | focus, error (message below), disabled | manual entry, invite, export password |
| **Amount field** | Large numeric, ₹ prefix, Indian grouping as you type | empty, valid, invalid | manual entry, edit |
| **Date field** | Opens the Android date picker | default, error | manual entry, corrections |
| **Chip** | filter, choice (category), assist | unselected, selected (tint + check), disabled | filters, category pick |
| **Segmented button** | Personal \| Shared | selected, unselected | Overview, Budget (invitee) |
| **Switch / checkbox** | | on, off, disabled | "Remember this", settings |
| **Snackbar** | with Undo | shown, dismissed | resolve, delete |
| **Dialog** | confirm, destructive confirm | default | leave, remove, wipe, move date out of sharing |
| **Bottom sheet** | modal | collapsed, expanded | capture feedback, review resolution, account confirmation, filters |
| **Top app bar** | with sync chip and overflow | default, scrolled | all |
| **Bottom navigation / rail** | 4 items, badge on Review | selected, badge, large-screen rail | app shell |

## Product components

| Component | What it shows | States to design | Used by |
| --- | --- | --- | --- |
| **Sync chip** | Last sync age + status | synced, pending, failed, stale, never synced, access revoked, syncing | top bar, banners |
| **Budget meter card** | Category dot, name, spent / limit, remaining, progress bar, status line | on track, 80%+, over budget (negative remaining), no limit, zero limit, loading skeleton, "incomplete" while pending items exist | Overview, Budget |
| **Hero card ("Left to spend")** | Label, large remaining amount, "of ₹X in limits" and days left, a "Not counting ₹Y pending" line while review items exist (CAT 02), Pocket bottom-right. X is the sum of the limits you set; categories without a limit are excluded and do not move Pocket. Over budget: label becomes "Over budget by" | full, halfway, nearly empty, over budget, no limits set (no Pocket meter, shows month spent), incomplete (pending items), loading skeleton, 200% font size | Overview |
| **Pocket** | Budget character, four budget poses plus Spending, Payday, Idle (see tokens 1.5) | each pose, light and dark, reduced motion (still) | Overview hero, capture feedback sheet, empty states |
| **Attention list ("Needs a look")** | Only categories over budget or at 80%+, each with name, status icon + words, spent of limit, thin progress bar | over budget, 80% used, none (shows "All categories on track"), many items | Overview |
| **On-track row** | "9 other categories on track" with check icon, opens the full category list | all on track, some unknown | Overview |
| **Pending-review banner** | "Pending review ₹X (n items)" + "Balances are incomplete" | n > 0, with unknown-amount / foreign counts, hidden when 0 | Overview, Review |
| **Joined notice** | One-time screen after the app joins: "You joined <owner>'s shared budget", disclosures, Got it and Leave | first show, already in another shared budget (explains why not joined) | Overview (shown once) |
| **Transaction row** | Merchant/category, amount, date, tags (Shared, Pending sync, Deleted) | normal, pending sync, deleted, personal vs shared, long merchant, large amount | Transactions |
| **Review row** | Neutral reason chip with icon and words (amber is kept for the pending summary), amount, merchant, age | each reason type, unknown amount, foreign currency | Review |
| **Resolution actions** | Contextual action set per reason | per reason (see flows F3) | Review item |
| **Category picker** | 11 chips with category dot | none chosen, chosen | manual entry, resolution, correction |
| **Capture feedback sheet** | Amount, merchant, category, remaining per budget, threshold line, Change category | counted, needs review, over budget, threshold crossed, owner/invitee/solo variants | after capture |
| **Permission step card** | Why, benefit, Allow, Not now, status | not asked, granted, denied, needs restricted settings | setup |
| **Capability status row** | Name, status icon + text, Fix button | granted, denied, revoked | Capture health |
| **Disclosure panel** | Plain list: what they will see / what stays private / Google editor warning | static | Joined notice, owner invite |
| **Membership card** | Who, status (Shared and not joined yet, Active since, Left, Removing, Ended), actions (Remove access; Leave for the invitee) | each status, pending removal, waiting for owner's app | Budget → Members |
| **Threshold editor** | Two percentage steppers (1–100) | default 80/100, one value, invalid | Budget |
| **Limit row** | Category, ₹ field, lock icon on past months | editable, locked, empty = No limit, zero | Budget |
| **Audit entry** | Who, when, old → new | created, corrected, deleted, restored | Transaction details |
| **Export card** | Last export date, reminder, Export, Restore | recent, overdue (7+ days), never exported | Settings |
| **Recovery panel** | Plain explanation + action | schema problem, blocked (owner phone lost), access revoked, conflict | Sync issues |

## State patterns (use everywhere)

| Pattern | Design |
| --- | --- |
| **Loading** | Skeleton shapes matching the content, never a blank screen |
| **Empty** | One sentence naming the space, one line of help, one clear action. Illustration only if it adds meaning |
| **Error** | What happened in plain words, what to do, one button (Retry / Fix). Local data stays visible |
| **Offline** | A quiet banner; everything local still works |
| **Stale / pending** | Sync chip plus the affected numbers marked "may be out of date" |
| **Success** | The affected number animates; a short snackbar; Undo where reversible |

## Copy rules (from the requirements)

- Say "doesn't count yet" for review items, never "error".
- Say "Left; the owner's app must remove access" until removal is confirmed. Never claim a removal that has not happened.
- Say that anyone with edit access to the Sheet can read or change it outside the app.
- Money is always `₹` with Indian grouping and paise only when non-zero or in precise views.
- No "successfully", "please" or exclamation marks in system text.
