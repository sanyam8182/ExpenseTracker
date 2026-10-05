# Expense Tracker — Screen Catalogue

**Date:** 5 October 2026
**Status:** concept screens designed and reviewed (Design Gate: approved with changes, all must-fix and should-fix items applied). Concept art for Pocket is raster and not cleared for the repo, so the HTML mockups live outside the repository (author's scratch folder) and are described here.
**Binding rules:** `../system/tokens.md`, `../system/components.md`, `../ideation/flows.md`, `../../design-config.md`.

## 1. What was designed

| Area | Frames | Requirements covered |
| --- | --- | --- |
| Overview | Hero with Pocket (four budget states, light and dark), pending-review row, "Needs a look" list, on-track row, add expense button, bottom navigation | UI 01, BUD 02, CAT 02, FBK 03 |
| Capture feedback sheet | Counted, threshold crossed, over budget, needs review, invitee with two budgets, dark, locked-screen notification | FBK 01, FBK 02, CAP 04, AC 21 |
| Review | Inbox, unknown shop, nothing picked yet, possible duplicate, transfer question, after resolving, empty | CAT 02, CAT 01, TXN 01, TXN 02, RAW 01 |
| Sharing | Entry row on Budget, invite form, shared-not-joined, active, remove confirm, removal pending, joined notice, joined waiting, left, leave confirm, cannot join | MEM 01 to MEM 04, HIST 01, SEC 01 (automatic join, no Accept) |
| Setup | Welcome and sign-in, offline, three permission steps, restricted setting, readiness, two things to know, first limits, account confirmation | CAP 01, CAP 04, REC 01, AC 15 |
| Transactions | List, filter sheet, own and other's details, edit, date out of sharing, after delete, link refund, no results | UI 01, CAT 03, TXN 03, MEM 02, MEM 04 |
| Budget | Limits, past month, thresholds, invalid thresholds, invitee shared limits | BUD 02, BUD 05, FBK 02 |
| Settings and recovery | Hub, capture health, export and restore, deleted items, remembered rules, wipe, sync issues, sync chip states, replace phone, wrong password, restore preview | CAP 06, CAT 01, CAT 03, REC 01, REC 02, SEC 02, SYN 03 |
| Add expense and states | Add expense (empty, filled, errors), Overview loading, first month, sync failed, access revoked, no limits | CAP 06, FBK 03 |
| Adaptive | S25 Ultra landscape (Overview, Transactions list and detail, Review list and resolution, capture side panel), Z Flip cover (glance, after an expense), flex mode | tokens section 7 |

Every page has a dark twin except the adaptive page, which shows dark for the four landscape frames.

## 2. Decisions recorded while designing

- **Look:** deep teal hero (`#0A5C5E`), cool off-white page, DM Sans, restrained; Pocket is the one playful element.
- **Hero total:** "of ₹X in limits" is the sum of the limits you set; Pocket's pose follows left divided by that sum; no limits means no level. Pending review items are not subtracted and are named ("Not counting ₹Y pending").
- **Pocket placement:** Overview hero, welcome, empty states, and the plain counted capture sheet only. Never on errors, warnings, over-budget sheets, Review or sharing. Optional "Show Pocket" setting, on by default.
- **Sharing:** no Accept or Decline. The other person's app joins automatically and shows a one-time notice (what they see, what stays private, Google-editor limit, Leave). The owner sees "Shared, not joined yet" and one action, "Remove access". Accepted risk recorded in MEM 01.
- **Status language:** every status has an icon and words; amber is for "needs attention" (pending, 80%), red only for over budget and failure.
- **Wording:** "Doesn't count yet" for review items; never claim removal before Google confirms; no "successfully", "please" or exclamation marks.

## 3. Open items

**Assumptions to confirm**
- "Payday" pose (Pocket kit) is mapped to "new month starts"; income is not in the requirements.
- Window sizes in tokens section 7 are estimates; measure on both pilot phones. Z Flip 5 unfolded is a normal tall phone (compact width).
- App name is not decided; mockups use "Expense Tracker".
- Capture health shows app names, not package names (package discovery is SPIKE-03).

**Production dependencies**
- Real icon set (mockups use placeholder glyphs); DM Sans with ₹ and tabular numerals verified on both phones.
- Pocket artwork: transparent backgrounds, vector or layered files for animation, confirmed rights.
- Google sign-in button follows Google's branding rules.

**Still not designed**
- Overview: invitee Personal and Shared full states beyond the revoked and failed cases; zero-limit category; offline banner on its own.
- Capture: unknown amount, foreign currency, exactly 100%, grouped alerts, notifications denied.
- Review: loading, error, 100+ items. Sharing: invalid email, offline invite, join failed, Ended, Left complete. Setup: permission revoked later, sign-in loading.
- Export: overdue reminder state, progress. Transactions: loading, long merchant, very large amounts, 100+ rows.
- Per-screen reduced-motion notes, and pressed and focus states for every control.
- Medium width (600 to 839dp) has no pilot device; the layout rules exist but there are no frames.

## 4. Next steps in the process

1. Design review with real devices once a first build exists (contrast, 200% font, TalkBack).
2. `design handoff`: per-screen specs for developers (tokens, sizes, behaviour) from this catalogue.
3. Jira: the Jira issues still carry the old Accept wording for sharing and have no design issues; sync only when the owner asks.
