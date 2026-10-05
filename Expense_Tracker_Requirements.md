# Expense Tracker Requirements

**Android application for personal and shared monthly budgets**  
Version 0.2 | Updated 4 October 2026 | Prepared for Sanyam Koul

## 1. Scope, authority and decisions

This version replaces the contradictory wording in version 0.1. It retains the agreed personal-to-shared budget lifecycle and records the user's 1 October decision: **keep a server-free design and accept weaker enforcement**. The app runs on Android; Google Drive and Sheets supply cloud storage. There is no application backend, Apps Script service, embedded service-account credential, or promise of tamper-proof participant behavior.

The first release remains personal and shared tracking for two pilot users through privately installed APKs. Deliver a personal-tracking checkpoint first and a sharing checkpoint second; both are required for the complete first release. A personal checkpoint is not acceptance of unfinished sharing. Functional outcomes are in scope; visual styling and navigation layout remain a separate design activity.

Version 0.1 is preserved in `docs/archive/Expense_Tracker_Requirements_v0.1.md`. This Markdown file is the canonical product source of truth. The Confluence page is a published mirror, and `Expense_Tracker_Jira_Backlog.md` is the derived creation source for Jira; neither silently changes this file. Archives are immutable snapshots. Existing BUD/MEM/CAP/TXN/CAT/FBK/DAT/SYN/SEC/NFR identifiers and AC 01–76 remain traceable; superseded criteria are explicitly rewritten below. New detailed defaults are proposed planning decisions in this revision, not claims that they were agreed in September. Platform feasibility spikes must produce evidence before their dependent implementation is considered ready.

**Decision status rule:** D-01, D-02, D-03, D-04, D-05, D-06, D-07, D-08, D-09, D-10, D-11 and D-13 are approved. D-12 is proposed and is tagged below as `[D-xx, proposed]`. A tagged behavior is a planning assumption for spike/refinement work, not approved release behavior; only the user can move it to approved. The current approved product behavior is therefore D-01, D-02, D-03, D-04, D-05, D-06, D-07, D-08, D-09, D-10, D-11 and D-13 plus the untagged baseline requirements.

### 1.1 Glossary

| Term | Meaning |
| --- | --- |
| User | A person authenticated with a Google account; local identity uses Google's stable account identifier, not display name. |
| Budget owner | Creator of a budget; manages its limits, invitation and membership through the app. |
| Transaction owner / spender | User responsible for an expense; only that user can correct it through supported app flows. |
| Account holder | User who confirms a bank account/card belongs to them. Matching masked digits is insufficient. |
| Drive file owner | Google identity owning a file. This identity has powers outside the app's role model. |
| Personal budget | A user's own budget before/after sharing, or the invitee's separate ongoing budget. |
| Shared budget | The owner's existing budget converted for an accepted membership interval. |
| Membership interval / epoch | From the recorded join time to completed termination; rejoining creates a new epoch. |
| Candidate | Captured event not yet accepted as a counted expense. A bank-posted payment can still be pending app review. |
| Uncategorized | Review bucket; it is not a counted category in version 0.2. |
| Other | A counted category for confirmed purchases that do not fit a named category. |
| Posted | Bank/payment lifecycle state; it does not by itself mean all app review gates passed. |
| Pending sync | Confirmed local change not yet acknowledged in shared storage; distinct from pending review. |
| Opening amount | Aggregate owner spending brought into a sharing epoch without exposing the underlying private transaction details. |

### 1.2 Pilot and implementation baseline — ARC 01

- Role-neutral product; either pilot user can be owner or invited member. One active device per Google account in the pilot. Concurrent same-account devices are unsupported; replacement follows export/restore and a device handover. This is an operational/app check, not a remotely enforceable security boundary.
- Supported pilot runtime: Android 16/API 36 on Samsung Galaxy S25 Ultra and Galaxy Z Flip 5. Set minimum/target SDK to 36 for this private pilot; expanding supported Android versions is future scope. Record exact OS build, app build and dependency versions in validation evidence.
- Native Kotlin, Jetpack Compose, coroutines/Flow, Room with a supported SQLCipher Android integration, Android Keystore for local key protection, WorkManager for deferred eligible sync, Google sign-in plus Drive/Sheets APIs. Pin compatible stable dependency versions during the build spike.
- The Kotlin/Micronaut/Postgres backend toolkit in `.claude/CLAUDE.md` is not the product stack. No backend is introduced by that tooling document.
- Google authorization: identity plus Sheets access and Drive file listing, so the app can find shared-budget Sheets automatically by name and owner (candidate scopes: `spreadsheets`, `drive.file`, `drive.metadata.readonly`; SPIKE-01 confirms the smallest working set). This is broader than per-file access and is approved for the two-user private pilot (D-02). If Google blocks it, fall back to one-time user selection of the Sheet through Google's file picker with per-file access. Validate with both accounts.
- Google OAuth external Testing mode can cause seven-day refresh-token expiry with Drive scopes. Document chosen consent configuration, detect revoked/expired authorization and recover by sign-in without deleting data.
- No bank credentials, payment initiation, lending, investment advice, iOS, joint-account attribution, historical alert import, public Play Store release, currency conversion or multiple shared groups.

## 2. Budgets and membership

### BUD 01 — Budget identity and lifecycle

A user begins with one personal budget. Acceptance is the invitee tapping Accept in the app (MEM 01); sharing starts at the recorded join time. The owner's existing budget ID then becomes shared; the owner has no additional personal budget. The invitee retains their own personal budget. Ending membership returns the owner's budget to personal use with the same ID and retained contributions. No administrator role exists.

A user may participate in at most one active shared relationship, either as its owner or invitee. An invitee cannot share their separate personal budget with a third person while already a member. Reject reciprocal invitations, third members and overlapping memberships. A pending invitation (Sheet shared, not accepted) can be declined or cancelled (owner removes the Google share) without changing budgets. Recheck eligibility when the invitee taps Accept and when the owner's app processes the join, not only when inviting.

### BUD 02 — Monetary calculation

Use integer paise, INR, calendar months and Asia/Kolkata. Net spent equals counted purchases minus confirmed linked refunds, excluding soft-deleted purchases. Remaining equals configured limit minus net spent. Record expenses exceeding a limit; show negative remaining and “Over budget.” No-limit categories show spending and “No limit,” no fabricated remaining value or percentage warning. Zero is a configured limit: zero spent is not over budget, positive spent is; percentage alerts are suppressed to avoid division by zero.

### BUD 03 — Attribution

One stable logical transaction counts at most once in each applicable budget. An owner's expense affects only their own budget, personal or shared. An active invitee's eligible purchase automatically affects invitee personal and owner's shared budgets, using one category and the same full amount. Owner spending never enters invitee personal totals. Never sum the two projections as two purchases.

Joining preserves owner's limits/current-month net spending. Invitee contributions start at the recorded join time; earlier expenses never backfill. Termination preserves shared-period contributions and changes no spending solely because membership ended. New invitee expenses afterwards are personal-only.

### BUD 04 — Categories and dates

Fixed categories: Groceries; Dining and food delivery; Transport; Travel; Fashion; Entertainment; Rent and housing; Utilities and bills; Health; Household items; Other. Stable category IDs are common to personal/shared budgets, with independent limits. No custom categories or splits.

Automatic records use bank occurrence timestamp when available; preserve received timestamp separately. If occurrence is unavailable, use received time as a provisional date and require review. Manual entries use user-entered date. Store date source and precision. An exact timestamp belongs to an interval [joinedAt, endedAt), where endedAt is the recorded leave/removal time (effective attribution cutoff), distinct from completed Drive access removal. For date-only entries on a join/termination day, request whether purchase occurred before or after the boundary; do not invent a time or share while ambiguous. Dates strictly inside an interval need no artificial precision.

### BUD 05 — Limits and history

Past-month limits are locked. Configure current/future limits only; no automatic propagation to other months and no rollover of unused/overspent amounts. A new month with no configured limit is “No limit,” not a guessed copy of the previous month. Locking limits does not lock spent totals: authorized corrections/refunds can recalculate historical spending under CAT 03/TXN 03. Closed shared history follows MEM 04 instead.

### MEM 01 — Invitation by sharing the Sheet

An invitation is a normal Google share of the budget's Sheet. There are no separate invitation, reply or membership files, and the app does not send email itself.

1. Owner enters the recipient's Google email. The app creates the shared-budget Sheet, named with the fixed prefix `ExpenseTracker Shared –` and with a first tab `_meta` holding the budget ID and schema version, then shares it with that account as editor (link sharing off, resharing disabled where supported). No financial data is written yet. Google sends the invitation email.
2. The recipient's app finds shared budgets automatically: Sheets owned by someone else, shared with the signed-in account, that carry the marker (name prefix and `_meta` tab). It lists them as pending invitations showing the owner's email. Nothing changes until the recipient taps **Accept**.
3. On Accept, after the disclosures in HIST 01 and SEC 01, the invitee's app writes one `joined` row (account ID and time) to its own operations tab. That time is the sharing start. Declining or ignoring writes nothing; the app remembers the declined Sheet locally and the owner can remove the Google share.
4. The owner's app sees the `joined` row on its next sync, checks that it names the invited account and that no other member is active, and writes the opening amounts (HIST 01) as of the join time. Until then the invitee sees “Joined; waiting for the owner's app to sync”. The invitee's expenses from the join time are queued and uploaded and count in shared totals from that time.

The marked Sheet's Google share list plus its `joined`/`left` rows are the membership record. One invitee only: if any other account has access, or a second `joined` row appears, the app stops shared writes and shows a recovery issue. This is coordination between cooperating apps, not a trusted backend: anyone with editor access can edit cells (MEM 02). Cancel a pending invitation by removing the Google share.

### MEM 02 — App roles and acknowledged limits

Only budget owner changes shared limits/thresholds or invites/removes members through the app. Each transaction owner corrects only their own records through the app; budget owner has no app override. These checks apply to local mutations, queued operations and sync import.

Both participants may have Google editor privileges required for automated writes to the shared Sheet. Manual cell edits are unsupported, not technically impossible. Protected ranges can reduce accidents but do not enforce all app roles; file owner can alter protected data. The app cannot promise protection against malicious/coordinated tampering or prove authorship from a cell's claimed actor ID. Detect structural/version mismatches and surface recovery issues where possible. Valid-looking tampering is a stated limitation.

Configure files without public/link-wide grants and disable editors' ability to reshare where supported (`writersCanShare=false`). Verify actual permissions and capabilities in SPIKE-01. Do not describe Sheets tabs as read-only to their Google file owner.

### MEM 03 — Ending and rejoining membership

Owner removal requires connectivity. The owner's app writes a `removing` row, supported clients pause shared writes, the app removes the invitee's Google access to the Sheet, confirms the permission state through the API, takes the final available ledger and stores a closed-epoch snapshot. It writes `ended` only after access removal is confirmed; failure stays “Removal pending” and must not claim access is revoked. A write racing the removal cannot be prevented atomically; removing Google access is the effective remote boundary.

Invitee “Leave” immediately pauses their shared queue, routes new local purchases personal-only and writes a `left` row with the time. Until the owner's app removes the Google access, show “Left; the owner's app must remove access” and do not claim global termination. Invitee transactions after the `left` time are excluded by supported clients once the owner's app processes it. The record keeps leave time, effective attribution cutoff and completed access-removal time separately.

At next connection, revoked invitee clears inaccessible shared cache, preserving own personal records/history. Cached/exported offline copies cannot be remotely erased. Rejoin requires a fresh accepted invitation and epoch. Retained old shared totals stay with owner, but old Sheet grants are never reinstated.

### MEM 04 — Late events, date corrections and closed history

While membership is active, an own-transaction correction that moves its date outside the active interval removes its shared attribution; moving it inside creates it only after all review/eligibility gates pass. Same-day precision follows BUD 04. Unsupported app attempts to change another spender's transaction are rejected.

After completed removal, freeze the archived shared snapshot and the former member's retained contributions in the owner's continuing budget. The budget owner's own canonical transactions remain correctable in their continuing personal budget; these later corrections do not rewrite the archived shared snapshot. Never double-count a historical opening component and its underlying owner transaction in the owner's local continuing budget. Former member edits/refunds/restores their personal copy only. **No post-removal owner correction queue in v0.2**; it is deferred with BL 03. A shared write first attempted after revocation is rejected and shown to its author as “Saved personally; not added to closed shared period.” Preserve their personal data. Do not bypass permissions or retroactively mutate closed totals. Rejoining does not reopen earlier epochs.

Offline captures can queue during active membership. Before each upload, refresh membership and permissions; if unavailable, keep pending rather than assuming authority. Late/ambiguous events affecting closed intervals are flagged personal-only for the first version. This deliberately relaxes eventual completeness of closed shared totals.

### HIST 01 — Pre-sharing privacy and Sheet lifecycle

Only owner category/month opening amounts and shared-interval detail are visible to the invitee. When the owner's app processes the `joined` row, copy current-month net spending before the join time as one opening component per category attributed to the budget owner; do not upload pre-sharing transaction IDs, merchants, notes, accounts or dates. Owner keeps details locally. Limits/current-month totals remain unchanged.

During an active sharing epoch, each member is informed before acceptance that the other member can see the shared-epoch transaction details exposed by the app (merchant/payee when present, amount, date, category/intent and spender). There is no per-transaction privacy switch in v0.2. The disclosure also states that an authorized Google editor can inspect or change Sheet cells outside the app, which the server-free design cannot prevent.

While active, corrections/refunds to the owner's private opening purchases update only the relevant aggregate component and its version. No linked private transaction identifiers are exposed. Later month reports include only that epoch's visible activity. Invitee cannot browse owner's earlier months through app or Sheet. Shared reports identify an aggregate “Owner opening amount”; they must not claim it consists solely of individually visible purchases.

After termination archive the Sheet to owner-only access and stop automatic personal-period writes to it. A re-invite creates a **new financial Sheet** and new `_meta` tab. There is one actively shared Sheet per budget; closed owner-only Sheets may remain. New current-month opening totals can include retained earlier contributions, grouped as “Prior retained spending,” without exposing those records to the new invitee. Report current-epoch contributions separately so old spending is not mislabeled as current member activity. This replaces BL 01's undecided initial visibility; expanded historical detail remains deferred.

## 3. Capture

### CAP 01 — Permission and source capabilities

Explain three separate capability flows: SMS receipt, notification listener access and permission to post the app's own notifications (`POST_NOTIFICATIONS`). Declare/request only SMS permissions needed for new-message capture; no historical inbox import. Denial of any capability does not block manual entry; denied posting still permits in-app feedback. Update readiness after settings return and permission revocation.

Validate restricted-settings behavior for the actual APK installation route and both Samsung devices. Explain user steps only when required; do not promise a universal sideload workaround or background execution guarantee.

### CAP 02 — Parser contracts and coverage

Implement separate maintainable parsers for HDFC, SBI, SBM, Kotak and Axis using redacted samples from actual pilot channels. Each parser yields a normalized candidate, provenance and parser version. Missing fields remain unknown. Support is declared per bank, channel, source app and validated format family, not for every message from a bank.

Notification processing uses a versioned allowlist of exact verified bank/payment-app packages. Discover the installed pilot packages during the device spike; do not invent package names or trust display labels. Exclude messaging-app notification mirrors when SMS capture is enabled. If a messaging fallback is deliberately enabled, treat it as a separate validated source and deduplicate it. Drop non-allowlisted notifications without storage.

### CAP 03 — Extracted fields and unsupported currency

Extract amount, currency, debit/credit direction, merchant/payee, bank, masked account/card, reference and occurrence time when present. Keep receipt time and date source/precision. Never confuse bank balance with budget balance. Non-INR alerts create a visible “Unsupported currency” item with original amount/currency, never enter INR totals and cannot be confirmed as INR without a separately entered genuine INR charge. No conversion is inferred.

### CAP 04 — Account confirmation

Every newly detected account/card requires explicit account-holder confirmation. Bank/account context, not last-four equality, establishes mapping. Manual cash may have no account. Automatic candidates with unresolved ownership remain outside totals.

When an account is later confirmed, previously captured candidates for that account are re-evaluated using their original occurrence date (or the recorded receipt date when occurrence is unavailable), so an eligible confirmed purchase counts in its original month. Confirmation does not backdate an unresolved item without a usable date; such items stay in review until dated. The alternative policy of counting only from confirmation is not used.

### CAP 05 — Deduplication tiers

- Exact source-event replay: idempotent by source fingerprint. Notification updates from the same package/key reuse one candidate; state changes can update it.
- Strong payment match: same normalized transaction reference and confirmed account context, with compatible currency/direction/amount. Conflicts are held for review, not merged.
- Candidate match without strong identifiers: equal INR amount, compatible direction, occurrence/receipt times within 120 seconds, compatible normalized payee and no conflicting account/reference. Different sources are a useful signal. This suggests a possible duplicate; it does not automatically collapse purchases.
- No match: conflicting identities, currencies or distinct references remain separate, regardless of equal amount.

Retain logical transaction IDs when merging confirmed duplicates; source aliases point to that identity. Repeated sync/restore uses stable operation/transaction IDs. Candidate matching thresholds are deterministic initial defaults, versioned and evaluated on fixtures; broad claims about all UPI alerts are not assumed.

### CAP 06 — Health and manual fallback

Show source permissions, last successful capture, unsupported count and recovery guidance. Process locally offline where Android delivers events. Manual entry requires positive INR amount, entered date and one fixed category; merchant/account/method/notes are optional. A withdrawal is not a purchase; users record actual cash purchases manually. Capture starts after setup.

### RAW 01 — Review data retention

Successful parsing retains normalized fields plus minimal parser/source metadata, then deletes raw text. For allowlisted financial alerts that cannot be parsed sufficiently for review, store only a sanitized relevant financial excerpt, encrypted on-device, until the item is resolved or dismissed; there is no automatic expiry. Strip OTPs, full account/card numbers and unrelated text before storage; if safe extraction is uncertain, store a generic failure item and use manual entry. Never persist an OTP message.

Excerpts are excluded from logs, exports, backups and shared storage, and are deleted by resolving, dismissing or a full local wipe. This is the explicit v0.2 exception to blanket raw-text discard: an unreviewed excerpt stays on the phone, encrypted, until the user acts on it.

## 4. Transaction lifecycle and classification

### TXN 01 — Counting truth table

Evaluate these gates in order. Sync state does not determine whether a valid local expense counts.

| Condition | Overall spent | Named category spent | Shared attribution |
| --- | --- | --- | --- |
| OTP/request/promotion, failed payment, excluded transfer/credit | Zero | Zero | None |
| Pending payment (not posted) | Zero | Zero | None |
| Non-INR, ownership/date/intent/duplicate/category review unresolved | Zero | Zero | None until resolved |
| Posted confirmed INR purchase, one category, not deleted | Full net amount | Full net amount | Once per eligible membership |
| Same purchase pending sync | Counts locally | Counts locally | Local projection provisional; remote may be stale |
| Soft-deleted purchase | Zero | Zero | Tombstone removes eligible projection |
| Confirmed linked refund/reversal | Reduces original-month spend | Reduces original category net spend | Original eligible open projection only; closed history follows MEM 04 |

**Uncategorized never counts until classification is confirmed.** This supersedes v0.1 CAT 02 / AC 07 (approved as D-05, 5 October 2026). “Posted” and “review approved” are independent. Restoring or merging candidates runs all gates again.

### TXN 02 — Expense-type policy

| Activity | First-release treatment |
| --- | --- |
| Own-account transfer; transfer between participants; card repayment | Excluded; do not count again after purchase. |
| Salary/unrelated credit | Excluded; not a refund without original-purchase linkage. |
| Cash withdrawal or wallet top-up | Excluded funding movement. Count actual cash/wallet purchase once. |
| UPI person-to-person payment with ambiguous purpose | Review until confirmed purchase or transfer. |
| SIP/investment purchase | Count the actual debit in Other unless the user selects another fixed category; the redemption credit is not a refund and is not counted; investment advice/valuation out of scope. |
| EMI/loan repayment | Review. Exclude principal; allow a separately evidenced interest/fee expense. Do not infer a split or count full installment by default. |
| Insurance premium | Count actual debit; Health for health insurance, Utilities and bills for other insurance unless user selects another fixed category. |
| Subscription | Count actual charge; Entertainment for entertainment services, Utilities and bills for other services unless corrected. |
| Reminder/payment request/autodebit mandate only | Zero; count actual posted debit when received. |

One recorded expense still has one full-amount category. A separately evidenced fee is a distinct logical expense, not an automatic split of the gross EMI. All ambiguous matches remain reviewable.

### TXN 03 — Refunds and reversals

Link full/partial confirmed refunds to the original purchase. Apply to original purchase month and category; do not change configured historical limit or increase refund-month budget. Apply to original budget projections while their epoch remains open; frozen epochs follow MEM 04. Prevent cumulative confirmed refunds exceeding purchase amount; unusual/excess cases enter review. Duplicate refund delivery adjusts once. Unlinked credits do not become refunds automatically. An opening-purchase refund changes only the private purchase and permitted aggregate per HIST 01.

### CAT 01 — Automatic classification

Use explicit supported bank/merchant rules and remembered user-confirmed mappings. “High confidence” initially means an exact validated rule or explicit remembered mapping with no competing rule and all counting gates passed; no invented model confidence score. General marketplace names (such as Amazon) alone are insufficient.

Offer “Remember this merchant/category and intent” when a user corrects a candidate. A remembered rule is private to that user, keyed by normalized payee plus distinguishing context when available, and may set intent to `purchase`, `transfer/repayment`, or `excluded`; a purchase rule may also set a fixed category. Rules can be inspected and removed, affect future candidates only, never rewrite past transactions or another user's rules, and require all normal account/date/duplicate gates before counting. Unknown, conflicting or low-confidence matches enter Uncategorized review.

### CAT 02 — Review inbox and pending exposure

Show review reason, safely retained source context where available, and actions to confirm category/intent/account/date, merge duplicate, mark non-expense, dismiss or enter missing details. Resolution updates one transaction, never creates extra counted spending.

Show “Pending review: ₹X (n known-amount items)” separately from confirmed remaining. Count a possible-duplicate cluster at most once for the provisional display; exclude clusters already represented in counted spend. Show unknown-amount and non-INR item counts separately. Do not subtract provisional figures from confirmed totals or label remaining as complete while unresolved financial items exist.

### CAT 03 — Corrections, deletion and restore

Only transaction owner can change category/amount/date, soft-delete or restore through the app. Move full amount across old/new category/month projections atomically in the local database. Record actor, time, prior/new values, operation ID and base/result version. Retain change history for dataset lifetime.

Restore a soft-deleted own record through Deleted items after rerunning validation, duplicate and membership gates. Keep original ID and append a restore event. Active shared projection restores once if eligible; closed epoch remains frozen and only personal copy can restore. Soft deletion is reversible; full local wipe is separate (REC 02). Manual changes outside the app are outside this guarantee.

## 5. Feedback and functional screens

### FBK 01 — Per-expense feedback and number formatting

After durable local commit, show amount, merchant when known, category and applicable remaining amounts. Owner sees one personal/shared budget; active invitee may see personal plus shared. Use INR with Indian digit grouping (₹1,00,000.00), preserve paise in calculations, and include accessible spoken currency labels. Unknown category prompts review, not a fictitious category balance.

### FBK 02 — Threshold configuration

Defaults 80% and 100%. Allow one or two distinct integer percentages from 1 through 100; owner configures shared thresholds, each user their personal thresholds. Persist settings by budget/category, effective for current/future evaluations only. Reject invalid/duplicate values. Configuration changes initialize a new baseline without retrospective alert spam.

Maintain crossing state by budget/category/month/threshold version/device. Alert when previous confirmed spend ratio is below a threshold and new ratio is at/above it. Duplicate delivery/retries produce none. A refund/correction falling below rearms a future genuine crossing. For zero/no-limit categories suppress percentage alerts. Over-budget status still works for zero limit.

Each device evaluates on receiving new local or synced confirmed data; times can differ. A sync jump across both thresholds produces one notification stating highest threshold crossed and records both crossings. If first opening/restoring initializes a month's state, show current status without historical alert replay. Immediate simultaneous cross-device alerts are not promised.

### FBK 03 — Privacy and freshness

Default lock-screen notification contains no amount, merchant, category or budget balance; allow explicit details preference. Respect POST_NOTIFICATIONS denial and provide in-app feedback.

Always show last successful shared sync. Label “Pending” when local shared writes await acknowledgement, “Failed” on failure, “Access revoked” after revocation, and “Stale” immediately after known failure or when no successful sync occurred in 15 minutes while showing a shared view. “Never synced” is stale. Successful sync clears age/failure stale state only after applying fetched records; 15 minutes is a display threshold, not a background-sync promise.

### UI 01 — Required user outcomes

- Onboarding/accounts: Google sign-in, device status, capability flows, account confirmation, source readiness.
- Overview: month and applicable budget selection; category limit/spent/remaining, opening/retained aggregate labels, pending review and sync freshness.
- Transactions: filter by month, spender, category, account, payment method and review status; one logical payment with source aliases.
- Review/details: safe evidence, single category, own corrections, refund linkage, audit history; no other-user editing actions.
- Budgets/members: current/future limits, warning settings, invite by email, pending invitations to accept or decline, join/leave/removal pending states and permission-limit disclosure.
- Settings/recovery: source packages and permissions, notification privacy, remembered rules, export reminders/export/restore, deleted items, full local wipe.
- Functional screens are implementation work now; their styling/navigation composition remains design work. Do not hide uncertainty or unsupported behavior behind a “synced” label.

## 6. Storage, sync, security and recovery

### DAT 01 — Encrypted local operation

Persist transactions, limits, classifications, memberships, source aliases, versions, deletion markers, audit history, pending events and pending review in an encrypted local DB. Wrap/protect its key with Android Keystore. Commit record, totals/change event and durable outbox transactionally before success feedback. Restart/retry must preserve acknowledged local data. Never log keys, credentials, raw financial messages, complete account numbers or personal transaction contents.

### DAT 02 — Sheet data boundaries

One active shared Google Sheet stores only shared projections, safe opening aggregates, app-managed source operations and readable reports. Personal budgets, invitee pre-membership history, remembered rules, raw excerpts and unrelated messages remain local. Membership is recorded in the Sheet itself (`_meta` tab and `joined`/`left`/`removing`/`ended` rows); there are no separate control files.

Archive closed Sheet owner-only and create a new one on rejoin (HIST 01). Export rather than private Sheets backs up personal data. Financial Sheet access is Google editor access where needed, disclosed as such. Unsupported cell editing is not a permissions guarantee.

### DAT 03 — Logical schema

| Entity | Key fields |
| --- | --- |
| User/device/account | Stable Google ID, canonical email, device installation ID, account ID/bank/type/masked identifier/confirmed holder. |
| Budget/limit | Stable budget ID, owner, current state, INR/timezone, category/month/paise limit and threshold version. |
| Membership | Budget/epoch IDs, invited email, status (invited/active/removing/ended), joined/left/ended timestamps, Sheet ID. |
| Transaction | Stable ID, spender, confirmed account if known, amount/currency, direction/type/payment state, date source/precision, occurrence and received times, payee/method/notes/reference. |
| Review/classification | Explicit gates, reason, duplicate cluster, category, rule ID/version, optional sanitized excerpt. |
| Attribution/opening | Budget/epoch, transaction ID or aggregate component ID, month/category/paise contribution, opening/retained label; never private detail IDs in shared opening rows. |
| Event/sync | Stable operation ID, transaction ID, actor/device, base/result version, operation kind, refund link, deletion/restore flag, destination, acknowledgement/conflict state. |

### SYN 01 — Durable transport

Queue locally and sync on app open, manual Refresh and connectivity return when Android permits. Refresh membership/ACL before uploads; lack of current authorization leaves queue pending. Show independent local saved and shared sync status. Use bounded backoff for transient failures and explicit reauthorization recovery. No continuous background or immediate remote-delivery guarantee.

### SYN 02 — Append/retry/conflict model

Use append-only, per-author operation tabs in one shared Sheet. Every logical operation has a stable operation ID created before network call and reused across retries. Duplicate physical rows are allowed after ambiguous append outcomes; all readers deduplicate identical operation IDs before reducing totals or reports. Never claim Sheets offers an exactly-once append.

Verify schema, IDs and versions. Same operation ID with differing payloads is corruption, not “last row wins.” Operations reference base version; divergent successors create a conflict, preserving both candidates and freezing affected shared projection at last agreed value until transaction owner resolves it through the app. Owner limits have one supported app writer. Upload private-success/shared-failure can retry without losing the local record. Distinct valid events fold deterministically; keep local and report reducers consistent.

With one active device per account, same-user branches normally arise from restore/replacement; retain conflict handling for same-operation-ID payload changes, restore/replacement, detected external Sheet edits and two-author races. A per-author sequence number may reduce comparisons, but it does not remove the need to flag those cases.

Owner app alone rebuilds materialized monthly report tabs from the deduplicated log; other user can read canonical operations immediately after their next sync. Show report generation time and pending reporting state until owner app runs. This dependency is an accepted server-free tradeoff. Shared operation timestamps are client assertions, not authenticated proof against a malicious editor.

### SYN 03 — Sheet reports and schema recovery

Reports show monthly category limit/spent/remaining/over-budget, current-epoch per-spender contributions and opening/retained aggregate labels. Shared app views do not require opening Sheets. Source tabs are app-managed; cell editing is unsupported.

Include schema version and required tab/column definitions. If a newer/incompatible schema, missing source tab, contradictory operation or unexpected financial edit is detected, stop shared financial writes, preserve local outbox, display a recovery issue and avoid destructive automatic rewrite. Owner can rebuild derived reports from valid events; missing canonical events require import from an available valid export/local replica or an explicit data-loss acknowledgement. Do not invent lost data. Version migrations back up the original before mutation.

### SEC 01 — Identity and trust

Google sign-in is required for app use; previous valid local session supports offline operation. A signed-out app cannot open protected data; account switch isolates datasets. Use account-bound credentials, minimum viable API scopes and a check that a join request comes from the invited account. No backend secrets in APK.

App rules protect compliant supported flows; they do not defeat Google file owners/editors who use other tools. Explain this at sharing acceptance. Disable link-wide/public access; having Google access to a marked Sheet does not make anyone a member until they tap Accept. Production security claims must be limited to what the permission spike demonstrates.

### SEC 02 — Revocation and replacement

Use MEM 03 completion states; never equate “requested” with remote revocation. Offline cached data may persist until reconnect. Device replacement restores under same Google account, assigns a new device installation ID, and asks user to retire/wipe old app. Unsupported concurrent devices cannot be made trustworthy without a backend; warn and stop supported writes when conflicting device state is detected.

### REC 01 — Export, backup and restore

Password-protected app-managed export contains stable IDs, confirmed/unresolved normalized transactions, budgets/categories/memberships, source aliases, outbox state, tombstones and change history. Exclude review excerpts, raw alerts, OAuth tokens, bank secrets and device-bound keys. Use a portable, versioned encrypted export format; derive a new device DB key on restore. Restore requires correct password plus live sign-in to original Google identity; mismatched account/password/corrupt format makes no local changes.

Idempotently merge stable IDs/versions; preview conflicts, never blindly replay already acknowledged operations, and revalidate membership/ACL before restoring shared access or upload permission. An old backup cannot reopen an ended epoch. For personal-only unverified historical membership after restore, keep shared actions disabled until validated.

Exclude the device-key-dependent live DB, tokens and review excerpts from Android cloud backup/device transfer unless a tested portable migration is deliberately added. A system copy of ciphertext is not an app recovery promise. Show last successful app export, remind after 7 days with new data and before replacement/wipe; reminder may be dismissed. No automatic private-Sheet backup in v0.2. Losing the phone without an export can lose personal-only data; state this during setup.

If the budget owner's phone is lost, first attempt same-owner recovery: reinstall the same app, sign in to the same Google account and let automatic discovery find the marked Sheets that account owns. SPIKE-01/05 must record this result. If the Sheet is found, membership is read back from it and sharing continues. If it cannot be found or read, the app marks recovery blocked, preserves local data and offers a manual closure/export path; the invitee keeps access to already shared data but cannot rebuild or revoke sharing without the owner.

### REC 02 — Full local wipe and soft-delete recovery

Deleted-item restoration follows CAT 03. Full local wipe requires explicit confirmation including “local data and history will be removed”; offer export first without forcing it. Delete local DB, pending work, review excerpts, local auth/session state and protected DB key. Cancel workers/notifications before deletion. Do not delete exported files elsewhere, Google Sheets or the other user's data; remote membership is a separate action. Explain if user still has an active shared membership and provide leave/remove path before wiping. Wipe works offline and does not claim remote revocation.

### NFR 01 — Correctness, recovery and privacy evidence

Verify stable IDs, durable commit-before-feedback, duplicate operation folding, conflict preservation, zero unauthorized supported-app mutations, opening privacy, restore replay safety and raw-excerpt removal on resolution or dismissal. Redact logs by default, including error/crash diagnostics. An optional biometric app lock is deferred; Google session gating and device protections are in scope.

### NFR 02 — Measurable performance and accessibility

Use both pilot phones, production-like private APK, documented OS/battery settings and a seeded 10,000-transaction local dataset. For each phone run 100 warm-process and 100 cold-process supported-alert deliveries (cold means process absent but app not force-stopped). Timestamp entry into the capture callback with a monotonic clock; end after durable commit, recalculated local state and in-app feedback/notification dispatch request. Every measured run must be ≤2,000 ms; report median/p95/max and failures. Do not replace the maximum criterion with p95. Test source receipt reliability separately; absent callbacks cannot count as fast processing. Notification denial uses in-app state readiness as feedback endpoint; OS presentation latency is recorded separately. Preserve every outlier. If a run exceeds 2,000 ms, classify app versus device/OS infrastructure, record the original result, then perform one controlled rerun after a documented restart and stable conditions; the rerun supplements evidence and cannot erase the original failure. A repeated failure remains a Gate 3 failure until the requirement is revised explicitly.

Use readable controls at 200% font scale without lost critical actions, TalkBack labels/focus for every core action, 48dp touch targets, text/icon status in addition to color, text contrast ≥4.5:1 for normal text and ≥3:1 for large text, and accessible error announcements. Validate light/dark themes; no claim of complete WCAG conformance from this checklist alone.

## 7. Acceptance scenarios

AC 01–70 keep their identifiers and take the revised behavior below. AC 71–76 close the newly recorded gaps. All tests use supported fixtures and the server-free trust model. “Rejected” means supported app/API permissions as specified, not a guarantee against an authorized Sheet editor bypassing the app.

| ID | Requirements | Observable result |
| --- | --- | --- |
| AC 01 | BUD 03 | Invitee's confirmed ₹1,000 Fashion purchase reduces personal/shared remaining from ₹4,000/₹5,000 to ₹3,000/₹4,000 with one logical ID. |
| AC 02 | CAP 05; SYN 02 | SMS, notification and sync replay of a strongly matched purchase count once; distinct equal-value references remain separate. |
| AC 03 | BUD 01; BUD 03 | Owner spending affects only converted shared budget; invitee spending affects personal plus shared; owner has no extra personal budget. |
| AC 04 | MEM 01; MEM 03; MEM 04 | A pending invitation (Sheet shared, not accepted) changes neither budget; the invitee's Accept starts sharing from the recorded join time and converts the owner's budget once the owner's app processes it; completed removal returns it to personal without reducing retained spend. Pending removal is visibly incomplete. |
| AC 05 | TXN 02 | Own-account/inter-user transfers and card repayment add zero; original card purchase counts once. |
| AC 06 | TXN 01; TXN 03 | OTP/request/failed payment adds no expense; confirmed partial refund changes original open projections once. |
| AC 07 | CAT 01; CAT 02; TXN 01 | Unknown merchant/category remains pending and outside all totals. Confirming Fashion adds its full amount once to each eligible budget. This replaces v0.1's counted Uncategorized behavior. |
| AC 08 | DAT 01; SYN 01; SYN 02 | Offline save survives restart; pending/stale state is visible; partial shared failure preserves local data; retry folds once. |
| AC 09 | CAT 03; MEM 04 | Own category/date/amount correction removes old and applies new totals consistently; a category moves full amount without a split. |
| AC 10 | FBK 01; FBK 02 | Expense crosses thresholds and then limit; one alert per eligible crossing and purchase remains saved above limit. |
| AC 11 | HIST 01; DAT 02; SEC 02 | Neither participant sees invitee personal-only data; invitee sees owner opening aggregates but no pre-sharing details; completed revocation blocks future Drive writes. |
| AC 12 | CAP 02; CAP 06 | Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable. |
| AC 13 | BUD 03; HIST 01 | Owner ₹5,000 limit and ₹1,000 spent remain ₹4,000 available when sharing starts; ₹1,000 is a private-detail-free opening amount. Invitee's later ₹500 adds once to both budgets; earlier invitee transactions stay private. |
| AC 14 | TXN 03; BUD 05 | ₹400 October refund of a ₹1,000 September purchase reduces September spend by ₹400 without changing September limit or October budget. Duplicate refund counts once, subject to closed-epoch rules. |
| AC 15 | MEM 02; SEC 01 | Owner can edit shared limit through app; invitee app/queued mutation is rejected without change. Invitee can edit personal limit. Onboarding explicitly discloses unsupported Google-editor bypass. |
| AC 16 | CAT 03; MEM 02 | Active user corrects only their own record; all open eligible projections update once and audit entry persists. Other user's app edits are rejected, including budget-owner attempts. |
| AC 17 | SYN 01; SYN 02; DAT 02 | Invitee saves eligible expense and uploads stable event; owner sees it after sync without opening Sheets. Personal-only records/limits are absent from remote data. |
| AC 18 | CAT 03 | Own soft-delete removes active eligible spend/views after sync; ID/tombstone/history persist; other user's app deletion is rejected. |
| AC 19 | REC 01 | Export/restore includes normalized records, stable IDs, tombstones and history; excludes raw/excerpt content, secrets and device keys; duplicate restore does not duplicate totals. |
| AC 20 | REC 01 | Wrong/missing password rejects restore with no local mutation; correct password still requires matching Google account. |
| AC 21 | FBK 03 | Default locked-device notification reveals no amount, merchant, category or balance; unlocked content and explicit privacy preference work. |
| AC 22 | FBK 02 | 80%/100% crossings alert once; duplicate replay does not. Owner controls shared configuration, personal user theirs; configuration edits establish baseline without replay. |
| AC 23 | SYN 01; FBK 03 | Offline shared change shows pending, survives restart and synchronizes once logically after eligible trigger; other device updates on its next sync. |
| AC 24 | MEM 01 | Creator is owner; owner shares the Sheet with the invitee's Google email; the invitee's app discovers it automatically and shows a pending invitation; sharing starts only when the invitee taps Accept. No financial data is written before the join is processed. |
| AC 25 | MEM 02; SYN 03 | Both users may view reports and have disclosed Google edit access for sync. Supported changes use app roles; manual cell edits are unsupported and detectable structural damage blocks writes. No read-only-source guarantee is asserted. |
| AC 26 | CAP 04 | New account remains unconfirmed/unattributed until explicit confirmation; matching last four across different banks does not merge ownership. |
| AC 27 | CAT 01; CAT 02 | Exact validated/remembered unambiguous rule auto-classifies; low confidence, unknown, duplicate or ambiguous transfer remains excluded until all review gates pass. |
| AC 28 | TXN 02 | Ambiguous UPI stays excluded pending purpose; confirmed purchase counts; confirmed transfer/repayment does not. |
| AC 29 | TXN 01; TXN 03 | Pending/failed contributes zero, confirmed posted purchase once, linked refund/reversal once in original month. |
| AC 30 | MEM 03; HIST 01 | Rejoin uses new epoch and Sheet; gap expenses/details are not imported; owner retains old snapshot; safe current-month prior-spend aggregates preserve totals. |
| AC 31 | CAP 01 | SMS receipt, listener access and posting permission each show status/recovery. Denial preserves manual entry and in-app feedback. |
| AC 32 | CAP 06 | Manual cash purchase saves with amount/date/category only; optional fields remain unknown and eligible budgets update once. |
| AC 33 | SYN 03; HIST 01 | Ledger/report includes monthly limit/spent/remaining/over-budget, visible per-spender contributions and correctly labeled opening/prior-retained aggregates. Private source details absent. |
| AC 34 | SEC 01 | Fresh setup requires Google sign-in; valid cached session supports offline; sign-out locks dataset; different account cannot open it. |
| AC 35 | TXN 02; TXN 03 | Salary/unrelated deposit contributes zero; only linked confirmed refund adjusts spend; unlinked credit cannot silently become a refund. |
| AC 36 | BUD 05 | Past limit edits rejected; current/future edits target one month; legitimate refund/correction may still change historical spent, not its locked limit. |
| AC 37 | BUD 02 | No-limit category counts spending, shows No limit, no numeric remaining/over-budget/percentage warning. |
| AC 38 | BUD 02 | Expense beyond positive limit persists and syncs, showing negative remaining and Over budget. |
| AC 39 | BUD 04 | Bank occurrence controls Asia/Kolkata month; missing date uses review-flagged received time; manual uses entered date; boundary-day ambiguity is not silently shared. |
| AC 40 | CAP 05 | Reference/account strong match consolidates; differing references stay separate; fuzzy no-reference candidate requires explicit duplicate resolution. |
| AC 41 | NFR 02 | All 100 warm and 100 cold delivered-alert runs per pilot device satisfy ≤2,000 ms with seeded 10,000 records; report p50/p95/max and separate delivery/network metrics. |
| AC 42 | DAT 01; REC 01 | DB contents are unreadable without protected key; offline restart works; portable export restores with new device key, not by copying the device-bound key. |
| AC 43 | MEM 03; SEC 02 | Owner removal completes only after confirmed revocation. Invitee leave pauses local sharing immediately but remote revocation remains pending owner processing. Next reconnect clears revoked cache, retaining personal copy. |
| AC 44 | MEM 04 | After termination own personal correction/refund is allowed; closed shared totals remain frozen. No owner queue or retroactive write; rejected late sync is visible to author. |
| AC 45 | REC 01 | Restore requires both correct password and live same-account sign-in; restored expired membership cannot grant access. |
| AC 46 | CAT 03; REC 02 | Audit history persists for dataset lifetime including tombstones/exports; explicit full local wipe removes local history/key/queue, not remote files or external exports. |
| AC 47 | HIST 01; TXN 03 | Refund/correction to owner's pre-sharing purchase updates only safe opening aggregate while epoch active; no private transaction identifier/detail appears in Sheet. |
| AC 48 | MEM 01; ARC 01 | Discovery lists only marked Sheets owned by someone else and shared with the signed-in account; unmarked Sheets are ignored; a join from a non-invited account or a second member is rejected; a two-account test proves the scopes and permissions; a partial failure can retry safely. |
| AC 49 | MEM 03; SYN 02 | Owner offline cannot complete leave; UI states dependency. During closing, app upload pauses; stale queued event after revoked permission is kept personal and visibly rejected for sharing. |
| AC 50 | CAP 02; CAP 05 | Only allowlisted verified packages processed; messaging mirrors excluded by default; same notification key updates one candidate; explicitly enabled fallback deduplicates. |
| AC 51 | RAW 01 | OTP/unrelated content never persists; sanitized unresolved excerpt encrypted locally only and erased on resolution or dismissal (no automatic expiry); export/log/Sheet contains none. |
| AC 52 | CAT 02 | Pending ₹X/count excludes confirmed duplicates, counts candidate cluster at most once, and separates unknown/non-INR counts; confirmed remaining is labeled incomplete while pending. |
| AC 53 | CAT 01 | Remember mapping only after explicit choice; apply to future own candidates, expose removal, preserve old history and other user's rules; competing rules go to review. |
| AC 54 | CAT 03; MEM 04 | Restore own deleted ID adds eligible open projections once; unauthorized restore rejected; closed shared epoch stays frozen. |
| AC 55 | REC 02 | Confirmed offline wipe cancels jobs and deletes local data/key/session/excerpts; remote Sheet/export unchanged; active membership caveat and export option shown. |
| AC 56 | FBK 02; FBK 03 | Invalid/duplicate threshold rejected; zero/no-limit percentages suppressed; sync jump coalesces alerts; devices may notify at different times without promising simultaneous delivery. |
| AC 57 | FBK 03 | Last sync always visible; never-synced/failed or ≥15-minute-old shared state marked stale; known pending shown immediately; successful fetch/apply resets freshness. |
| AC 58 | SYN 02 | Retry after lost append response may duplicate physical rows but identical operation ID affects totals once; conflicting same-ID payload halts affected processing. |
| AC 59 | SYN 02 | Two successors of same base version surface conflict without silent overwrite; resolution event authored by transaction owner converges both replicas. |
| AC 60 | SYN 03 | Unknown schema/missing canonical data blocks shared writes but preserves outbox; derived report can rebuild from valid events; missing sources never silently fabricated. |
| AC 61 | REC 01; SEC 02 | Replacement device restores portable data and reauthorizes membership; old-device retirement is explained; conflicting active device detected by supported flow blocks shared writes. |
| AC 62 | CAP 03; TXN 02 | USD alert shows unsupported-currency item with original units, excluded from INR/pending-INR subtotal; no inferred conversion. |
| AC 63 | TXN 02 | ATM/wallet funding excluded; SIP/investment debit, insurance and subscription charges counted in the chosen fixed category (Other by default for investments); ambiguous EMI reviewed and never blindly counted in full. |
| AC 64 | UI 01; FBK 01 | Overview and transaction filters expose accurate month/spender/category/account/method/review views, opening labels, sync freshness and Indian currency formatting. |
| AC 65 | NFR 02 | Core flows usable with TalkBack and 200% text; critical controls meet 48dp and stated contrast thresholds; status has non-color cue and accessible errors. |
| AC 66 | ARC 01; CAP 01 | Actual private APK install on each pilot phone documents restricted-settings behavior, posting/listener/SMS consent and recovery after battery restrictions. |
| AC 67 | REC 01 | After 7 days of unexported changed data reminder appears without blocking use; recent-export date visible; loss risk explained; platform backup excludes key-dependent DB/excerpts/tokens. |
| AC 68 | SYN 02; SYN 03 | Invitee sync can update app ledger before owner report rebuild; report timestamp/pending state makes owner-online dependency explicit. |
| AC 69 | BUD 01; MEM 01 | A third account with access, an invitee trying to share their personal budget while a member, reciprocal invitations and a second `joined` row are rejected at Accept and when the owner's app processes the join. |
| AC 70 | BUD 05; NFR 01 | Next month without configured limits shows No limit; no rollover/copy assumption; logs/crash reports contain no secrets, raw alerts or private financial payload. |
| AC 71 | CAT 01; TXN 02 | A remembered rule can set purchase, transfer/repayment or excluded intent (and a fixed category for purchases); it applies only to future own candidates after normal account/date/duplicate gates, and can be removed. |
| AC 72 | CAP 04; BUD 04 | Confirming an account re-evaluates earlier captured candidates in their original occurrence/receipt month; items without a usable date remain in review and do not count merely because the account was confirmed. |
| AC 73 | HIST 01; SEC 01 | Before accepting sharing, each member is told that active-epoch merchants, amounts, dates, categories/intents and spender attribution are visible to the other member and that no per-transaction exclusion exists in v0.2. |
| AC 74 | REC 01; SEC 02; SPIKE-01; SPIKE-05 | Setup and recovery first test same-owner reinstall and automatic rediscovery of the owner's marked Sheet. If that fails or the Sheet is unreadable, an invitee cannot rebuild or revoke sharing; the app marks recovery blocked and preserves local data. |
| AC 75 | NFR 02 | Every >2,000 ms run is retained with cause classification; one documented controlled rerun may supplement it, but cannot hide the original outlier or convert a repeated failure into a pass. |
| AC 76 | NFR 02; CAP 02 | [D-12, proposed] Gate 3 parser receipt-rate measurement names its ground truth (statement or alert log plus user confirmation) and reports fixture/live sample sizes; provisional thresholds remain visibly proposed until SPIKE-03 evidence and explicit approval. |

## 8. Decision applicability and deferred scope

The tags in this matrix are the decision tags for the affected normative sections and acceptance criteria. Until a proposal is approved, the tagged behavior is not a current release commitment. Gate 0 may test it; implementation and Gate 3 sign-off must wait for approval or an explicit revision.

| Decision tag | Affected normative sections | Affected acceptance criteria | Ownership note |
| --- | --- | --- | --- |
| [D-12, proposed] | Gate 3 | AC 76 | SPIKE-03 sizes the targets; US-021 measures against them once approved. |

## 8.1 Decision register

| ID | Decision in v0.2 | Approval status | Disposition |
| --- | --- | --- | --- |
| D-01 | Server-free, cooperating-client model; users may retain Google editor powers beyond app rules. | **Approved by user — 1 October 2026** | Replaces all-path/readonly-source security guarantees. |
| D-02 | Invite by sharing the marked Sheet with the invitee's Google email; the app finds shared budgets automatically (broader Drive listing permission accepted for the pilot); the invitee's Accept in the app starts sharing; leave and removal are recorded in the Sheet and the owner removes Google access. | **Approved by user — 5 October 2026** | SPIKE-01/02 verify discovery, scopes, permissions and failure handling before sharing build; fall back to one-time file picker if Google blocks listing. |
| D-03 | Safe opening aggregates; new Sheet per epoch; no pre-sharing detailed history. | **Approved by user — 5 October 2026** | Planning choice that closes the initial BL 01 privacy gap while preserving budgets. |
| D-04 | Frozen closed history; personal-only post-removal edits; no owner review queue in first release. | **Approved by user — 5 October 2026** | Planning choice; extended reconciliation remains deferred. |
| D-05 | All unresolved counting gates, including category, excluded; explicit pending exposure. | **Approved by user — 5 October 2026** | The reversal of counted Uncategorized in old CAT 02/AC 07 is in effect. |
| D-06 | Sanitized unresolved excerpts kept encrypted on the phone until resolved or dismissed (no time limit); never OTP/unrelated text or remote/raw backup. | **Approved by user — 5 October 2026** | Limited exception to immediate successful-parse deletion. |
| D-07 | One active device per account; Android 16 pilot; portable exports with reminders. | **Approved by user — 5 October 2026** | Defines device/backup boundary; no enforcement against hostile duplicate clients. |
| D-08 | Kotlin/Compose/Room+SQLCipher, Google APIs, no app backend; append events folded by stable IDs. | **Approved by user — 5 October 2026** | Stack and sync proposal; exact dependency pinning and feasibility evidence precede implementation. |
| D-09 | Fixed categories retained; explicit money-flow table and date-boundary confirmation. | **Approved by user — 5 October 2026** | No automatic categories, currency conversion or expense splitting unless changed by approval. |
| D-10 | Personal checkpoint then shared checkpoint; complete v0.2 release requires both. | **Approved by user — 5 October 2026** | Delivery sequence proposal, not removal of sharing scope. |
| D-11 | Working rules: 120-second candidate deduplication, 15-minute stale state, 1–100 alert thresholds, retroactive account confirmation, restore of deleted purchases, and the strict every-run 2-second latency rule with its outlier protocol. | **Approved by user — 5 October 2026** | Defaults are testable; SPIKE-03/05 evidence may prompt an explicit revision. |
| D-12 | Gate 3 pilot targets: at least 30 labelled fixtures per claimed bank/channel/source, 7 days and 30 real purchases per phone, 95% capture rate against a named ground truth, zero false or duplicate expenses, 30 correct auto-classification decisions. | **Proposed — requires explicit approval** | Held until SPIKE-03 shows which combinations and sample sizes are available. |
| D-13 | Baseline rules reviewed with the user as a batch: three permission flows with the restricted-settings step (CAP 01); notification package allowlist with messaging apps excluded (CAP 02); one-relationship membership guards (BUD 01); owner-only limits and own-record edits with the Google-editor bypass disclosed (MEM 02); Sheet structure recovery (SYN 03); full local wipe (REC 02); Indian currency formatting (FBK 01); accessibility thresholds (NFR 02); redacted logs and no app lock (NFR 01); functional screen outcomes (UI 01). | **Approved by user — 5 October 2026** | App lock stays deferred (BL 06); package names are discovered in SPIKE-03/04. |

| Backlog ID | Deferred work |
| --- | --- |
| BL 01 | Expanded access to owner's pre-sharing transaction details. Initial behavior is resolved by HIST 01. |
| BL 02 | Custom categories and management permissions. |
| BL 03 | Trusted/server-backed reconciliation, post-removal owner correction requests and retroactive closed-epoch adjustments. |
| BL 04 | Multiple active devices, multiple members/groups, reciprocal sharing. |
| BL 05 | Foreign-currency conversion, investment tracking, separate cash-flow report, public store distribution, other OS versions/platforms. |
| BL 06 | Optional biometric/app lock, automatic private cloud backups and supported manual Sheet-cell editing. |

The register distinguishes the explicitly approved decisions (D-01, D-02, D-03, D-04, D-05, D-06, D-07, D-08, D-09, D-10, D-11, D-13) from proposed product choices. Proposed choices are planning assumptions only; they are not approved release behavior until the user accepts them or Gate 0 evidence triggers a documented revision. Feasibility and implementation evidence remain pending; failed mandatory spikes block the dependent scope.

## 9. Delivery gates and pilot exit

### Gate 0 — Evidence before architecture-dependent implementation

- SPIKE-01: with both Google accounts demonstrate that the chosen scopes can list Sheets shared with the user by name and owner and read/write the marked Sheet (fallback: one-time file picker); sharing, removing access and disabling resharing; whether the invitee's app can read the Sheet's share list. Record manual-edit limitations and OAuth Testing/re-auth behavior (seven-day expiry). Reinstall the same build as the same owner account and test whether the owner's app rediscovers the marked Sheet; distinguish that recoverable case from loss of device-only credentials before designing AC 74's blocked state. No real financial data needed.
- SPIKE-02: execute the full flow: invite, automatic discovery, Accept/decline, owner's app processing the `joined` row, leave and removal, including a wrong account, a second member, owner offline and partial failure (access removed but `ended` not written). No financial data is written before the join is processed.
- SPIKE-03: **start first**; collect/redact actual alert fixtures for HDFC, SBI, SBM, Kotak and Axis pilot combinations and enumerate real package names. Agree the supported matrix, the statement/alert-log ground truth and the fixture/live sample plan before writing broad parser claims or sizing Gate 3 thresholds.
- SPIKE-04: install private APK on both actual Android 16 phones; validate restricted settings, permission denial, battery behavior, background receipt and notification posting.
- SPIKE-05: demonstrate SQLCipher/Room/Keystore compatibility, process restart, portable encrypted export and replacement restore without copying device keys. Include same-owner reinstall with an existing marked Sheet and record whether the app rediscovers it under the same account.

### Gate 1 — Personal tracking checkpoint

Local encryption, capture/manual entry, review/counting, categories, money flows, budgets, corrections/deletion/restore, alerts, overview/filters and export/wipe meet their mapped acceptance criteria. Sharing may not be labeled complete at this gate.

### Gate 2 — Shared checkpoint

Invitation/accept, app roles/trust disclosures, safe opening data, deterministic event folding, conflicts, report freshness, leave/removal, frozen history and fresh-Sheet rejoin meet the mapped criteria. Pass Gate 0 sharing spikes first.

### Gate 3 — Measured pilot exit [D-12, proposed]

- **Status: Proposed acceptance thresholds pending SPIKE-03 evidence and explicit approval.**
- All AC 01–76 pass where their preconditions apply; no skipped mandatory architecture/security/recovery case. Record any unavailable bank/channel as unsupported, never “passed.”
- The provisional plan is at least 30 redacted labeled fixture cases per claimed bank/channel/source combination: minimum 10 eligible purchases, 5 non-expense/failed/pending cases, 5 duplicate/distinct-equal-value cases, and 10 format variations/available refund/reversal cases. SPIKE-03 may resize these numbers after real-format availability is known. Synthetic mutations supplement edge tests but do not establish real-format support. All expected fields/statuses and counting outcomes pass.
- Run at least 7 consecutive days on both phones and at least 30 real eligible purchases per device (extend the window if needed). Reconcile against user-confirmed records/statements locally.
- Provisional eligible capture receipt rate target is ≥95% of supported alerts known to have arrived in allowed sources, measured against a named ground truth such as the bank statement or alert log plus user confirmation. Report missed callbacks separately from parser failures. All received supported purchase alerts either normalize correctly or visibly enter review; no silent loss.
- Zero false confirmed expenses and zero duplicate counted expenses in the evaluated pilot. Evaluate at least 30 auto-classification decisions across the pilot; each must be correct. Report auto-classification coverage separately without requiring unsafe guesses to hit a coverage target.
- Ledger totals reconcile to the confirmed fixture/live dataset; known late/revoked exclusions and pending items are explicitly reported, not hidden as balanced.
- Performance satisfies NFR 02 for all measured runs; accessibility satisfies its explicit checklist; both-device permission/recovery evidence recorded.
- No open data-loss, private-history exposure, incorrect-spend or failed-revocation-completion defect. A permitted external Sheet editor's documented ability to bypass app rules is an accepted limitation, not a passing security enforcement claim.

## 10. Technical references

Verified platform constraints inform the choices, but do not establish that the unimplemented app passes its spikes.

1. [Google Sheets scopes and protected ranges](https://developers.google.com/workspace/sheets/api/scopes) — scopes do not create app-role row authorization.
2. [Google protection ownership limits](https://developers.google.com/apps-script/reference/spreadsheet/protection) — a file owner can edit protected ranges.
3. [Drive per-file authorization](https://developers.google.com/workspace/drive/api/guides/api-specific-auth) and [mobile Picker integration](https://developers.google.com/workspace/drive/picker/guides/desktop-mobile-picker) — listing shared Sheets automatically needs broader scope than per-file access; the picker is the fallback; both must be validated.
4. [Drive sharing/capabilities](https://developers.google.com/workspace/drive/api/guides/manage-sharing) — distinguish file grants from app roles.
5. [OAuth refresh-token expiry](https://developers.google.com/identity/protocols/oauth2) — external Testing/Drive scope needs explicit reauthorization handling.
6. [Sheets append API](https://developers.google.com/workspace/sheets/api/reference/rest/v4/spreadsheets.values/append) — stable-ID deduplication is app responsibility.
7. [Android posting permission](https://developer.android.com/develop/ui/compose/notifications/notification-permission), [NotificationListenerService](https://developer.android.com/reference/android/service/notification/NotificationListenerService) and [restricted settings](https://support.google.com/android/answer/12623953?hl=en).
8. [Android Keystore](https://developer.android.com/privacy-and-security/keystore) and [backup rules](https://developer.android.com/identity/data/autobackup) — device-bound storage does not replace portable app export.
9. [SQLCipher Android migration/integration](https://www.zetetic.net/sqlcipher/sqlcipher-for-android-migration/) and [persistent Android work](https://developer.android.com/develop/background-work/background-tasks/persistent) — verify pinned versions and execution limits.
10. [Jira hierarchy](https://support.atlassian.com/jira-cloud-administration/docs/what-are-issue-types/) — Epic → Story or Task → Sub-task.
