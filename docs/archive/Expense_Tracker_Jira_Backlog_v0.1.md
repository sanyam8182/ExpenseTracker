# Expense Tracker — Jira Backlog

**Source:** [Expense_Tracker_Requirements.md](Expense_Tracker_Requirements.md)  
**Target project:** `SCRUM` — My Software Team  
**Scope:** Functional first release for the private Android pilot.

## Import/use notes

Create Epics first, then their Stories, Tasks and Sub-tasks. The IDs in this document (EPIC-01, US-001, etc.) are document references, not Jira keys. Set **Parent** to the listed ID when creating the issue: Story and Task issues point to an Epic; a Sub-task points to its Task. Copy Summary and Description into Jira. Put acceptance criteria in Jira's acceptance-criteria field if available, otherwise append them to Description. Jira issue type names are Epic, Story, Task and Sub-task. No assignees, estimates, sprints, priorities or site-specific custom fields are assumed.

Acceptance criteria are intended to be testable. Requirements described as proposed in the source remain marked proposed. Deferred items are listed at the end and are not first-release commitments.

## Proposed delivery order

1. App foundation, authentication, local persistence and onboarding.
2. Alert capture, manual entry, transaction lifecycle and review.
3. Budgets, attribution, sharing and permissions.
4. Google Sheets synchronization, recovery, revocation and pilot validation.

---

# EPIC-01 — Secure Android foundation and onboarding

**Issue type:** Epic  
**Summary:** Establish identity, local storage and capture readiness  
**Description:** Provide authenticated identity, protected local persistence, permission onboarding and account confirmation as the foundation for expense capture and sharing.  
**Acceptance criteria:**
- Google sign-in identifies each user; bank credentials, card PINs and OTPs are never requested.
- SMS and notification permissions are explained and reported separately.
- Users see capture readiness, last successful capture and recovery guidance.
- Local data is encrypted with Android-protected key storage and works offline.
- New accounts/cards require owner confirmation before transaction assignment.

## US-001 — Sign in with Google

**Issue type:** Story  
**Parent:** EPIC-01  
**Summary:** Authenticate users with their Google account  
**Description:** As a user, I want to sign in with Google so ownership, invitations and Sheet access use an authenticated identity.  
**Acceptance criteria:**
- Successful sign-in associates the session with the authenticated account ID.
- Sharing operations are unavailable while signed out.
- Signing out ends access to authenticated shared data until authorized sign-in.
- No bank password, card PIN or OTP is requested or stored.

### TASK-001 — Implement Google sign-in and session gating

**Issue type:** Task  
**Parent:** US-001  
**Description:** Integrate Google sign-in and require an authenticated session for shared operations.  
**Acceptance criteria:**
- Successful callback creates a session; cancellation or failure does not.
- Shared reads and writes reject a signed-out caller.

#### SUB-001 — Configure Android sign-in callback

**Issue type:** Sub-task  
**Parent:** TASK-001  
**Description:** Configure sign-in client and validate callback identity.  
**Acceptance criteria:**
- Valid callbacks return a stable account identity.
- Invalid callbacks do not authenticate the user.

#### SUB-002 — Gate shared operations

**Issue type:** Sub-task  
**Parent:** TASK-001  
**Description:** Require active authentication for shared-budget and Sheet operations.  
**Acceptance criteria:**
- Signed-out access is denied before shared data is read or written.
- Signing out clears the active session.

## US-002 — Set up capture permissions and health

**Issue type:** Story  
**Parent:** EPIC-01  
**Summary:** Explain and request SMS and notification access  
**Description:** As a user, I want clear permission setup and capture health so I know which payment alerts the app can receive.  
**Acceptance criteria:**
- SMS and notification access are explained and requested independently.
- Permission status, last successful capture and recovery steps are visible.
- Manual entry remains available if permissions are denied or revoked.
- The app does not claim detection before an alert reaches the device.

### TASK-002 — Implement capture permission onboarding

**Issue type:** Task  
**Parent:** US-002  
**Description:** Present permission rationale, status and recovery guidance.  
**Acceptance criteria:**
- Status refreshes after return from Android settings.
- Revoked access identifies the affected source and how to restore it.

#### SUB-003 — Add SMS permission flow

**Issue type:** Sub-task  
**Parent:** TASK-002  
**Description:** Explain and request SMS capture access.  
**Acceptance criteria:**
- The rationale appears before the system request.
- Denial leaves manual entry usable.

#### SUB-004 — Add notification permission flow

**Issue type:** Sub-task  
**Parent:** TASK-002  
**Description:** Guide the user to enable notification-listener access.  
**Acceptance criteria:**
- The app reports whether notification access is enabled after settings return.
- Notification status is independent of SMS status.

## US-003 — Confirm bank account ownership

**Issue type:** Story  
**Parent:** EPIC-01  
**Summary:** Confirm each detected account or card before routing expenses  
**Description:** As a user, I want to confirm newly detected accounts so transactions are attributed to the correct owner.  
**Acceptance criteria:**
- Unconfirmed accounts remain outside budget totals and are visible for review.
- Matching last four digits alone never merges accounts.
- Confirmed mapping associates one account with one owner.

### TASK-003 — Store and review account mappings

**Issue type:** Task  
**Parent:** US-003  
**Description:** Persist account context and require explicit owner confirmation.  
**Acceptance criteria:**
- Confirmation survives restart.
- Different bank/account contexts remain distinct even if masked digits match.

#### SUB-005 — Persist account identity state

**Issue type:** Sub-task  
**Parent:** TASK-003  
**Description:** Store bank, type, masked identifier and confirmation state.  
**Acceptance criteria:**
- New mappings default to unconfirmed.
- Confirmed owner association is durable.

#### SUB-006 — Add account confirmation action

**Issue type:** Sub-task  
**Parent:** TASK-003  
**Description:** Let the user confirm ownership of a discovered account or card.  
**Acceptance criteria:**
- Confirmed accounts become eligible for transaction routing.
- Unresolved accounts can remain unassigned.

---

# EPIC-02 — Capture and normalize transactions

**Issue type:** Epic  
**Summary:** Capture supported expenses and provide manual entry  
**Description:** Process financial SMS and supported bank/payment notifications on-device, normalize validated formats, deduplicate payment alerts and support manual cash entry. Pilot bank targets are HDFC, SBI, SBM, Kotak and Axis.  
**Acceptance criteria:**
- SMS and notification sources are distinct integrations.
- Captured candidates preserve unknown fields instead of inventing values.
- Repeated delivery does not duplicate a payment; equal amounts alone never merge transactions.
- Manual entry requires amount, date and category; merchant, account, method and notes are optional.
- A bank/channel is called supported only after its relevant fixtures pass validation.
- Raw SMS/notification text is discarded after successful parsing; OTPs and unrelated messages are never retained.

## US-004 — Parse supported financial alerts

**Issue type:** Story  
**Parent:** EPIC-02  
**Summary:** Convert supported alerts into normalized transaction candidates  
**Description:** As a user, I want supported payment alerts parsed so eligible transactions do not require manual entry.  
**Acceptance criteria:**
- Extract amount, currency, direction, merchant/payee, bank, masked account/card, reference and occurrence time when present.
- Received time is stored separately.
- Unsupported or uncertain extraction enters review.
- Parsing can operate offline when Android delivers the alert.
- Historical SMS/notification import is excluded from the first release.

### TASK-004 — Build source adapters and parser framework

**Issue type:** Task  
**Parent:** US-004  
**Description:** Route SMS and notification events through separate adapters into maintainable bank parsers.  
**Acceptance criteria:**
- Candidate records include source and parser version, not raw alert text.
- Missing values remain unknown.
- Only validated formats are marked supported.

#### SUB-007 — Implement SMS ingestion

**Issue type:** Sub-task  
**Parent:** TASK-004  
**Description:** Receive eligible financial SMS and invoke local parsing.  
**Acceptance criteria:**
- Eligible messages reach the parser when permission is granted.
- OTPs and unrelated content are not persisted.

#### SUB-008 — Implement notification ingestion

**Issue type:** Sub-task  
**Parent:** TASK-004  
**Description:** Receive supported bank/payment-app notifications separately from SMS.  
**Acceptance criteria:**
- Notification candidates are tagged with their source.
- Disabled notification access is reported as unavailable.

## US-005 — Deduplicate repeated payment alerts

**Issue type:** Story  
**Parent:** EPIC-02  
**Summary:** Consolidate duplicate delivery without merging separate purchases  
**Description:** As a user, I want repeat alerts and sync retries consolidated while real equal-value purchases stay separate.  
**Acceptance criteria:**
- Transaction reference plus account context is the preferred identity evidence.
- Amount, merchant and time are supporting signals only.
- Equal amounts alone never merge records.
- Uncertain matches stay separate or enter review.

### TASK-005 — Implement stable identity and duplicate review

**Issue type:** Task  
**Parent:** US-005  
**Description:** Make event processing idempotent and route ambiguous matches for review.  
**Acceptance criteria:**
- Reprocessing one event does not add another expense.
- Insufficient evidence does not silently merge purchases.

#### SUB-009 — Add source-event fingerprints

**Issue type:** Sub-task  
**Parent:** TASK-005  
**Description:** Persist an event fingerprint with source and parser metadata.  
**Acceptance criteria:**
- Repeated event fingerprints resolve idempotently.
- Fingerprinting does not require raw message retention.

#### SUB-010 — Add possible-duplicate review state

**Issue type:** Sub-task  
**Parent:** TASK-005  
**Description:** Surface candidates whose identity evidence is inconclusive.  
**Acceptance criteria:**
- Ambiguous candidates do not create duplicate counted spending.
- User resolution can preserve two genuine equal-amount purchases.

## US-006 — Enter uncaptured expenses manually

**Issue type:** Story  
**Parent:** EPIC-02  
**Summary:** Record cash and other expenses without an alert  
**Description:** As a user, I want manual entry when capture is unavailable so my budgets include eligible expenses.  
**Acceptance criteria:**
- Amount, date and one category are required.
- Merchant, account, payment method and notes are optional and remain unknown when omitted.
- Manual entries use the date entered by the user and update each applicable budget once.

### TASK-006 — Implement manual expense form and save

**Issue type:** Task  
**Parent:** US-006  
**Description:** Validate required fields, preserve optional fields and persist the logical expense.  
**Acceptance criteria:**
- Missing required fields prevent saving with a clear prompt.
- Valid save persists before success is shown and recalculates applicable local totals.

#### SUB-011 — Validate required fields and INR amount

**Issue type:** Sub-task  
**Parent:** TASK-006  
**Description:** Validate amount, date and category; store money as integer paise.  
**Acceptance criteria:**
- Invalid amount or missing date/category is rejected.
- Valid amount is stored in paise without rounding drift.

#### SUB-012 — Save optional details

**Issue type:** Sub-task  
**Parent:** TASK-006  
**Description:** Allow optional merchant, account, payment method and notes.  
**Acceptance criteria:**
- Optional fields can be blank.
- Blank values remain unknown rather than being generated.

---

# EPIC-03 — Classification and transaction lifecycle

**Issue type:** Epic  
**Summary:** Review uncertain activity and maintain correct expense history  
**Description:** Apply posted/pending/failed states, categorize expenses, exclude transfers and unrelated credits, link refunds and support authorized corrections.  
**Acceptance criteria:**
- Only confirmed posted expenses affect totals.
- Pending and failed items, transfers, card repayments, OTPs, salary and unrelated credits do not count as expenses.
- Each classified expense belongs to exactly one fixed category for its full amount.
- Refunds adjust original purchase month; corrections update all applicable budgets and retain change history.

## US-007 — Review uncertain and uncategorized activity

**Issue type:** Story  
**Parent:** EPIC-03  
**Summary:** Resolve uncertain transactions before they affect budgets  
**Description:** As a user, I want an inbox for uncertain alerts so unconfirmed activity does not change my budget.  
**Acceptance criteria:**
- Low-confidence merchants, ambiguous transfers, unsupported alerts and possible duplicates enter review.
- Unresolved items are excluded from totals.
- Confirming an expense assigns its full amount to one fixed category.
- Uncategorized is a pending review bucket; Other is a valid category.
- Initial categories: Groceries; Dining and food delivery; Transport; Travel; Fashion; Entertainment; Rent and housing; Utilities and bills; Health; Household items; Other.

### TASK-007 — Implement review queue and resolution

**Issue type:** Task  
**Parent:** US-007  
**Description:** Persist reasons for review and resolve candidates without creating duplicate transactions.  
**Acceptance criteria:**
- Unresolved items remain visible after restart and outside totals.
- Resolution updates the existing transaction and recalculates all applicable budgets once.

#### SUB-013 — Persist review status and reason

**Issue type:** Sub-task  
**Parent:** TASK-007  
**Description:** Store pending/review state and reason for each uncertain candidate.  
**Acceptance criteria:**
- Review state survives restart.
- Pending review records contribute zero to category totals.

#### SUB-014 — Add single-category resolution

**Issue type:** Sub-task  
**Parent:** TASK-007  
**Description:** Let the user confirm expense status and select one category for the full amount.  
**Acceptance criteria:**
- Full amount moves to the selected category.
- Split-category allocation cannot be saved.

## US-008 — Apply payment lifecycle and exclusions

**Issue type:** Story  
**Parent:** EPIC-03  
**Summary:** Count posted purchases and exclude non-expense events  
**Description:** As a user, I want payment statuses and transfers handled correctly so budgets reflect spending rather than every bank event.  
**Acceptance criteria:**
- Pending is visible and counts zero until posted; failed counts zero.
- Own-account transfers, transfers between the two users and card repayments count zero.
- Ambiguous person-to-person UPI enters review.
- Salary and unrelated credits do not adjust budgets.
- Only confirmed linked refunds/reversals adjust a purchase.

### TASK-008 — Implement status and transaction-type rules

**Issue type:** Task  
**Parent:** US-008  
**Description:** Apply countability rules and recalculate on status changes.  
**Acceptance criteria:**
- Only posted eligible expenses count.
- State updates and repeated delivery are idempotent.

#### SUB-015 — Add transaction states

**Issue type:** Sub-task  
**Parent:** TASK-008  
**Description:** Persist pending, posted, failed and reversed states.  
**Acceptance criteria:**
- Pending and failed contribute zero.
- Posted eligible purchase contributes once.

#### SUB-016 — Exclude transfers, repayments and unrelated credits

**Issue type:** Sub-task  
**Parent:** TASK-008  
**Description:** Classify confirmed non-expense activity and hold ambiguity for review.  
**Acceptance criteria:**
- Confirmed excluded activity adds no expense.
- Ambiguous UPI intent requires user resolution.

## US-009 — Link refund to original month

**Issue type:** Story  
**Parent:** EPIC-03  
**Summary:** Apply a confirmed refund to the original purchase month  
**Description:** As a user, I want a linked refund to reduce the original purchase month's spending so a later refund does not inflate that month's budget.  
**Acceptance criteria:**
- Full or partial confirmed refund reduces original-month net spending.
- Later-month available budget and configured limit are unchanged.
- Repeated refund delivery adjusts totals once.
- Post-removal server reconciliation remains deferred to BL 03.

### TASK-009 — Link refunds and recalculate original-month totals

**Issue type:** Task  
**Parent:** US-009  
**Description:** Store purchase linkage and apply adjustment to original occurrence month in Asia/Kolkata.  
**Acceptance criteria:**
- Adjustment reaches each budget that counted the original expense (proposed attribution policy).
- Unlinked credits are not automatically treated as refunds.

#### SUB-017 — Store refund relationship

**Issue type:** Sub-task  
**Parent:** TASK-009  
**Description:** Persist original transaction ID and refund amount.  
**Acceptance criteria:**
- Partial refund amount is supported.
- Duplicate refund event does not create another adjustment.

#### SUB-018 — Recalculate original month

**Issue type:** Sub-task  
**Parent:** TASK-009  
**Description:** Apply refund to the original month and applicable budget projections.  
**Acceptance criteria:**
- Original-month net spending decreases by the refund amount.
- Refund-month spending and available limit are unaffected.

## US-010 — Correct and soft-delete own transactions

**Issue type:** Story  
**Parent:** EPIC-03  
**Summary:** Correct only own transactions and retain history  
**Description:** As a user, I want to fix or delete my own records without losing the identity and history needed for sync and recovery.  
**Acceptance criteria:**
- User can correct category, amount and date only on their own records.
- A correction recalculates old/new categories or months in each applicable budget without another expense.
- Soft deletion removes a record from ordinary views and totals but retains its ID, deletion marker and change history.
- Former member can correct personal copy; an edit affecting frozen shared totals enters owner review and does not reopen access.
- Full local-data wipe is a separate Settings action.

### TASK-010 — Add authorized corrections and soft deletion

**Issue type:** Task  
**Parent:** US-010  
**Description:** Check transaction ownership, version accepted edits and maintain deletion markers.  
**Acceptance criteria:**
- Unauthorized change is rejected and leaves totals unchanged.
- Accepted changes are recorded with actor and time.

#### SUB-019 — Record versioned transaction history

**Issue type:** Sub-task  
**Parent:** TASK-010  
**Description:** Record stable ID, changed fields, actor, time and version.  
**Acceptance criteria:**
- Every accepted correction adds one history event.
- Event remains linked to original logical transaction.

#### SUB-020 — Implement soft-delete marker

**Issue type:** Sub-task  
**Parent:** TASK-010  
**Description:** Exclude deleted items while keeping sync/recovery identity.  
**Acceptance criteria:**
- Deleted expense contributes zero and is hidden from normal lists.
- Retry does not recreate it as a new transaction.

---

# EPIC-04 — Monthly budgets and user feedback

**Issue type:** Epic  
**Summary:** Calculate budgets, attribute spending and show useful feedback  
**Description:** Provide INR calendar-month budgeting in Asia/Kolkata, fixed categories, correct sharing attribution, historical limits, no rollover, threshold alerts and sync freshness.  
**Acceptance criteria:**
- Money is stored in paise; remaining = limit minus eligible net spending.
- Past limits are read-only; current and future can be configured; no rollover occurs.
- Over-limit expense is allowed and shows negative remaining.
- No-limit category spending is tracked without percentage alerts.
- One transaction has one category and counts at most once per applicable budget.
- Local persistence, totals and feedback update within 2 seconds after a supported alert reaches the app on each pilot device (network sync measured separately).

## US-011 — Configure monthly category budgets

**Issue type:** Story  
**Parent:** EPIC-04  
**Summary:** Manage month-specific category limits  
**Description:** As a user, I want category limits so I can compare monthly spending with my plan and keep past months stable.  
**Acceptance criteria:**
- One personal budget per user; same fixed category set for personal/shared budgets with independent limits.
- Past limits cannot be edited; current and future limits can.
- No rollover; each month starts at its configured limit.
- No-limit category is labeled “No limit” and has no percentage warning.
- Spending beyond a limit remains recorded, shows negative remaining and “Over budget”.

### TASK-011 — Store category limits and calculate monthly balance

**Issue type:** Task  
**Parent:** US-011  
**Description:** Store limits by budget, category and month and calculate remaining balances.  
**Acceptance criteria:**
- Remaining equals limit minus eligible net spend.
- Updating one month does not change a prior limit or total.

#### SUB-021 — Seed fixed categories and limits

**Issue type:** Sub-task  
**Parent:** TASK-011  
**Description:** Add stable IDs for the agreed category list and month limit records.  
**Acceptance criteria:**
- The category set matches the source requirements.
- Limits are independent per budget and stored as integer paise.

#### SUB-022 — Enforce month editing and no-limit rules

**Issue type:** Sub-task  
**Parent:** TASK-011  
**Description:** Restrict historical edits and represent categories without a configured limit.  
**Acceptance criteria:**
- Past-limit update is rejected without data change.
- No-limit spend is tracked but never shown as over budget or percentage-alerted.

## US-012 — Attribute expense to applicable budgets

**Issue type:** Story  
**Parent:** EPIC-04  
**Summary:** Calculate personal/shared contributions without double counting  
**Description:** As a participant, I want each expense routed to my applicable budget views so my personal and shared balances are accurate.  
**Acceptance criteria:**
- Owner's expense counts only in the converted shared budget while sharing.
- Invitee's expense counts once in invitee personal and once in owner's shared budget during membership.
- Those are two projections of one purchase and are not added as duplicate purchases.
- Acceptance preserves owner limits and current-month spend; invitee pre-acceptance expenses are not imported.
- Removal retains shared-period expenses in owner's history; later invitee expenses affect only invitee's personal budget.
- Rejoin starts a new interval; gap expenses are not retroactive.
- Occurrence date in Asia/Kolkata controls membership/month attribution; uncertain fallback date is flagged.

### TASK-012 — Implement attribution and lifecycle recalculation

**Issue type:** Task  
**Parent:** US-012  
**Description:** Derive budget projections from one transaction and membership intervals.  
**Acceptance criteria:**
- Recalculation and sync are idempotent.
- Acceptance/removal alone does not erase retained spend or reset owner's current budget.

#### SUB-023 — Route owner and invitee expenses

**Issue type:** Sub-task  
**Parent:** TASK-012  
**Description:** Apply one owner projection and the invitee's two applicable projections.  
**Acceptance criteria:**
- Owner transaction changes shared only while sharing.
- Invitee transaction changes personal and shared once each.

#### SUB-024 — Preserve membership boundaries

**Issue type:** Sub-task  
**Parent:** TASK-012  
**Description:** Keep owner state at conversion and retain shared-period history after termination.  
**Acceptance criteria:**
- Acceptance does not reset owner limits or spend or import invitee's earlier expenses.
- Removal does not change retained totals; new invitee expenses route only personal.

## US-013 — Show balances and threshold alerts

**Issue type:** Story  
**Parent:** EPIC-04  
**Summary:** Provide transaction feedback, alerts and freshness indicators  
**Description:** As a user, I want to see how a classified expense changes my budgets and receive useful alerts without repeated notifications.  
**Acceptance criteria:**
- Feedback shows amount, merchant, category and remaining balance per applicable budget.
- Owner sees shared balance only; invitee with two budgets sees both; personal-only user sees personal balance.
- Defaults alert at 80% and 100%, once per threshold crossing.
- Shared thresholds are owner-controlled; personal thresholds are user-controlled.
- Shared balances show last-sync information when data may be stale.
- Lock-screen details are hidden by default and configurable.

### TASK-013 — Implement balance feedback and alerts

**Issue type:** Task  
**Parent:** US-013  
**Description:** Recalculate local balance and alert state after transaction save.  
**Acceptance criteria:**
- Duplicate delivery/retry does not repeat an alert.
- Alerting never blocks an over-budget purchase.

#### SUB-025 — Render applicable balances

**Issue type:** Sub-task  
**Parent:** TASK-013  
**Description:** Build feedback from the viewer's budget memberships.  
**Acceptance criteria:**
- Owner sees shared balance only while sharing.
- Invitee with both budgets sees separate personal and shared balances.

#### SUB-026 — Add threshold crossings and privacy

**Issue type:** Sub-task  
**Parent:** TASK-013  
**Description:** Track crossings and use generic lock-screen notification by default.  
**Acceptance criteria:**
- Each threshold fires once per crossing and can fire again after falling below and recrossing.
- Default lock-screen text reveals no amount, merchant, category or balance.

---

# EPIC-05 — Budget sharing and permissions

**Issue type:** Epic  
**Summary:** Invite one member and enforce shared-budget permissions  
**Description:** Convert the owner's existing budget to shared only on acceptance, retain the invitee's personal budget, support remove/leave/rejoin, and enforce permission boundaries. First release allows one owner plus at most one invitee per budget.  
**Acceptance criteria:**
- Creator is owner; no administrator role.
- Pending/declined invite leaves both budgets unchanged.
- Acceptance converts owner's budget without resetting it; invitee retains own personal budget.
- Owner can remove; invitee can leave; shared-period records remain in owner's history.
- Owner alone edits shared limits; each user corrects only their own records.
- Rejoin starts a new interval. Pre-sharing transaction visibility remains deferred to BL 01.

## US-014 — Invite and accept a member

**Issue type:** Story  
**Parent:** EPIC-05  
**Summary:** Invite a Google account to share an existing budget  
**Description:** As a budget owner, I want to invite one user so sharing begins only when that user accepts.  
**Acceptance criteria:**
- Invitation uses Google account email and binds acceptance to that authenticated account.
- Owner authorizes creation of the shared Sheet when sending.
- Pending/declined invitation leaves both budgets unchanged.
- Acceptance converts owner's budget, preserving limits/current spend; invitee keeps separate personal budget/history.
- Invitee's pre-acceptance expenses are not imported; exactly one invitee can be active.

### TASK-014 — Implement invitation and acceptance lifecycle

**Issue type:** Task  
**Parent:** US-014  
**Description:** Store pending invite, validate accepting identity and create membership interval.  
**Acceptance criteria:**
- Only the invited authenticated account can accept.
- Acceptance timestamp controls start of shared contributions.

#### SUB-027 — Create pending invitation

**Issue type:** Sub-task  
**Parent:** TASK-014  
**Description:** Store invitee email and owner budget without activating sharing.  
**Acceptance criteria:**
- Pending invitation changes neither budget.
- Invitation is visible to its intended account.

#### SUB-028 — Activate shared membership

**Issue type:** Sub-task  
**Parent:** TASK-014  
**Description:** Convert owner's existing budget and activate invitee on acceptance.  
**Acceptance criteria:**
- Owner's limits and current spending are preserved.
- Invitee personal budget remains separate; earlier expenses remain excluded.

## US-015 — End sharing and support rejoin

**Issue type:** Story  
**Parent:** EPIC-05  
**Summary:** Remove or leave a shared budget without losing retained history  
**Description:** As an owner or invitee, I want to end sharing so future access stops while shared-period history remains consistent.  
**Acceptance criteria:**
- Owner can remove; invitee can leave voluntarily.
- Owner budget returns to personal; shared-period spend remains in its history/totals.
- Invitee's post-departure expenses affect only their personal budget.
- Online revocation stops writes; inaccessible shared cache is removed on next connection.
- Offline-copy limitation is disclosed.
- Re-invitation begins a new interval; gap expenses are not added.
- Pre-sharing detail visibility is undecided and must not be inferred from retained totals.

### TASK-015 — Implement termination, revocation and re-invitation

**Issue type:** Task  
**Parent:** US-015  
**Description:** End membership, freeze retained shared history and permit a distinct later membership interval.  
**Acceptance criteria:**
- Termination does not subtract shared-period spending.
- Former member cannot create new shared writes.

#### SUB-029 — Add remove and leave paths

**Issue type:** Sub-task  
**Parent:** TASK-015  
**Description:** Provide owner removal and invitee voluntary departure.  
**Acceptance criteria:**
- Either action ends active membership.
- Owner budget returns to personal state and retained shared history stays intact.

#### SUB-030 — Record a new interval after rejoin

**Issue type:** Sub-task  
**Parent:** TASK-015  
**Description:** Start new membership only after a former invitee accepts again.  
**Acceptance criteria:**
- Expenses in the membership gap remain outside shared totals.
- Earlier shared history remains identifiable and retained.

## US-016 — Enforce role-based permissions

**Issue type:** Story  
**Parent:** EPIC-05  
**Summary:** Enforce budget and transaction permissions across app and sync  
**Description:** As a participant, I want permissions enforced by every supported write path so UI visibility alone does not protect shared data.  
**Acceptance criteria:**
- Only owner changes shared limits and shared thresholds; invitee may view them and manage own personal limits.
- Each user edits only their transactions; owner has no override on invitee's transactions.
- Rejected writes leave records/totals unchanged.
- After termination, a change affecting frozen shared totals goes to owner review without reopening access.
- Sheet/file access design is proven to enforce the agreed restrictions before claiming enforcement.

### TASK-016 — Authorize mutations by actor and membership

**Issue type:** Task  
**Parent:** US-016  
**Description:** Apply the same ownership/membership checks to UI, local queue and remote writes.  
**Acceptance criteria:**
- Every write path checks authenticated identity and current permission.
- Rejected remote or local queued changes cannot bypass the rule.

#### SUB-031 — Gate shared limit changes

**Issue type:** Sub-task  
**Parent:** TASK-016  
**Description:** Permit shared limits and warning changes only for owner.  
**Acceptance criteria:**
- Owner update succeeds.
- Invitee attempt is rejected without changing shared settings.

#### SUB-032 — Gate transaction corrections

**Issue type:** Sub-task  
**Parent:** TASK-016  
**Description:** Permit correction/deletion only by transaction owner during active membership.  
**Acceptance criteria:**
- User can correct own record.
- Other member, including budget owner, cannot change it.

---

# EPIC-06 — Google Sheets synchronization and recovery

**Issue type:** Epic  
**Summary:** Synchronize shared data and recover safely from failure  
**Description:** Automatically manage one shared Sheet while keeping personal-only information private. Support durable offline work, idempotent retries, conflict visibility, export/restore and cache revocation.  
**Acceptance criteria:**
- Personal capture and calculation work offline and persist before success is reported.
- One shared Sheet contains app-managed ledger and monthly reports; users make supported changes in app.
- Sync on connectivity return when possible, app open and manual Refresh; no promise of continuous background sync.
- Pending, synced, failed, revoked and stale states are visible.
- Conflicts are not silently overwritten; partial failures are recoverable.
- Password-protected export/restore preserves stable IDs and history; restore requires password and originating Google account.

## US-017 — Create and maintain shared Sheet

**Issue type:** Story  
**Parent:** EPIC-06  
**Summary:** Keep the owner's shared ledger and reports current  
**Description:** As a budget participant, I want the app-managed shared Sheet to reflect shared expenses and monthly summaries automatically.  
**Acceptance criteria:**
- Owner authorizes Sheet creation at invitation; invitee gets access after acceptance.
- Sheet includes shared ledger and summaries: category limits, spent, remaining, over-budget status and per-user contribution.
- Both members may view Sheet/reporting tabs; app-managed source edits through cells are unsupported.
- Personal-only records and limits never enter the shared Sheet.
- Stable IDs prevent duplicate projected rows.
- App role restrictions are validated against actual Google file/write permissions.

### TASK-017 — Manage Sheet and project shared records

**Issue type:** Task  
**Parent:** US-017  
**Description:** Create and access-control one Sheet; project shared records and report data.  
**Acceptance criteria:**
- No invitee access before acceptance; revocation ends access.
- Projection excludes raw alerts and personal-only data.

#### SUB-033 — Create authorized shared Sheet

**Issue type:** Sub-task  
**Parent:** TASK-017  
**Description:** Create a Sheet for the owner's converted shared budget following authorization.  
**Acceptance criteria:**
- Owner authorization is required.
- Access is granted to invitee only after acceptance.

#### SUB-034 — Write ledger and monthly reports

**Issue type:** Sub-task  
**Parent:** TASK-017  
**Description:** Project ledger and readable monthly summary from stable IDs.  
**Acceptance criteria:**
- Reports include the agreed monthly metrics and per-user contribution.
- Repeated writes do not duplicate rows or totals.

## US-018 — Synchronize offline changes with conflict handling

**Issue type:** Story  
**Parent:** EPIC-06  
**Summary:** Sync shared changes safely and show freshness  
**Description:** As a user, I want queued changes synchronized after connectivity returns so shared totals converge without data loss or duplicate rows.  
**Acceptance criteria:**
- Sync runs on app open, manual Refresh and eligible connectivity return.
- Retries are idempotent; pending/failed/revoked/stale status is visible.
- Version conflict is surfaced rather than silently overwritten.
- Partial shared-write failure preserves private record and retry state.
- Other member sees change after their app synchronizes; immediate cross-device alerts are not promised.

### TASK-018 — Implement durable sync and conflict recovery

**Issue type:** Task  
**Parent:** US-018  
**Description:** Queue changes, trigger synchronization, detect versions and recover partial writes.  
**Acceptance criteria:**
- Replaying a change produces the same totals.
- Financial edit conflict requires resolution.

#### SUB-035 — Add sync triggers and status

**Issue type:** Sub-task  
**Parent:** TASK-018  
**Description:** Run sync at agreed triggers and expose freshness/error states.  
**Acceptance criteria:**
- Pending changes retry at app open, manual Refresh and eligible connectivity return.
- Stale indicator clears only after fresh shared data is received.

#### SUB-036 — Add version checks and partial-write retry

**Issue type:** Sub-task  
**Parent:** TASK-018  
**Description:** Detect conflicting versions and recover a private-success/shared-failure case.  
**Acceptance criteria:**
- Failed shared write does not lose private record.
- Stale edit does not silently overwrite newer financial data.

## US-019 — Protect and restore app-managed data

**Issue type:** Story  
**Parent:** EPIC-06  
**Summary:** Export and restore data without leaking alert content or creating duplicates  
**Description:** As a user, I want a protected backup that preserves budgets and history so I can recover app data safely.  
**Acceptance criteria:**
- Export includes transactions, budgets, memberships, categories and change history with stable IDs.
- Excludes bank passwords, card PINs, OTPs, raw alert text and unrelated messages.
- Export is password-protected; restore requires that password and sign-in to same originating Google account.
- Wrong password or account mismatch does not change local data.
- Change history lasts while local dataset exists; only full local wipe removes it.

### TASK-019 — Implement password-protected export and restore

**Issue type:** Task  
**Parent:** US-019  
**Description:** Package allowed app data, protect it, and validate restore identity before applying.  
**Acceptance criteria:**
- Valid restore preserves IDs/totals without duplicate expenses.
- Any validation failure leaves current dataset intact.

#### SUB-037 — Build secure export package

**Issue type:** Sub-task  
**Parent:** TASK-019  
**Description:** Serialize approved domain records and encrypt with user password.  
**Acceptance criteria:**
- Stable IDs and change history are included.
- Raw alerts and secrets are excluded.

#### SUB-038 — Validate restore credentials

**Issue type:** Sub-task  
**Parent:** TASK-019  
**Description:** Require export password and originating Google identity on restore.  
**Acceptance criteria:**
- Wrong password or mismatched account rejects restore.
- Rejection causes no partial import.

---

# EPIC-07 — Privacy and pilot validation

**Issue type:** Epic  
**Summary:** Verify privacy, accessibility and first-release behavior  
**Description:** Enforce local/shared data protection, remove revoked cache on reconnect, default to private notifications and validate performance and capture on pilot devices.  
**Acceptance criteria:**
- Device database is unreadable without protected key; app still works offline.
- Revocation prevents new shared writes and clears inaccessible shared cache on next connection while retaining personal data.
- Lock screen hides transaction details by default.
- On Samsung Galaxy S25 Ultra and Galaxy Z Flip 5 running Android 16, supported alert receipt to persistence/local totals/feedback is within 2 seconds under recorded conditions.
- Network sync and bank alert delays are measured separately.
- Accessible labels and status cues do not rely on color alone.

## US-020 — Revoke shared cache and protect notifications

**Issue type:** Story  
**Parent:** EPIC-07  
**Summary:** Protect lock-screen details and clean up revoked shared data  
**Description:** As a user, I want private notifications and dependable access removal so shared information is not exposed after membership ends.  
**Acceptance criteria:**
- Default lock-screen notification is generic; user preference may enable details.
- Revocation stops future shared writes.
- On next connection inaccessible shared cache is removed and personal records remain.
- Offline-cache limitation is disclosed until device reconnects.

### TASK-020 — Implement privacy defaults and revocation cleanup

**Issue type:** Task  
**Parent:** US-020  
**Description:** Apply notification preferences and remove projections lacking current access.  
**Acceptance criteria:**
- Stale membership cannot authorize new shared writes.
- Cache cleanup does not remove personal-only records.

#### SUB-039 — Add generic lock-screen notifications

**Issue type:** Sub-task  
**Parent:** TASK-020  
**Description:** Hide transaction/budget details on locked device by default.  
**Acceptance criteria:**
- Default reveals no amount, merchant, category or balance.
- Setting change follows user's selected preference.

#### SUB-040 — Clear inaccessible shared cache

**Issue type:** Sub-task  
**Parent:** TASK-020  
**Description:** Reconcile shared cache after online revocation.  
**Acceptance criteria:**
- Shared rows are removed after the departing device reconnects.
- Personal transactions remain available.

## US-021 — Validate pilot performance and accessibility

**Issue type:** Story  
**Parent:** EPIC-07  
**Summary:** Validate requirements on the two Android pilot phones  
**Description:** As the product team, we want documented device evidence so supported capture and local feedback meet the pilot baseline.  
**Acceptance criteria:**
- Target devices are Samsung Galaxy S25 Ultra and Samsung Galaxy Z Flip 5, both Android 16.
- Local receipt-to-persistence/totals/feedback is at most 2 seconds under documented test conditions.
- Bank alert delivery and network sync are measured separately.
- Actual pilot bank/channel fixtures are validated before support is claimed.
- Capture, battery behavior, permissions, sync and recovery are checked on both devices.
- Core text is readable; controls expose screen-reader labels; status has non-color cues.
- Distribution is private APK; Play Store release is out of current scope.

### TASK-021 — Validate capture coverage and response time

**Issue type:** Task  
**Parent:** US-021  
**Description:** Measure local processing and verify redacted fixtures for pilot accounts/channels.  
**Acceptance criteria:**
- Evidence records device, source, conditions and elapsed time.
- Unsupported variants are documented and routed to review.

#### SUB-041 — Measure local response target

**Issue type:** Sub-task  
**Parent:** TASK-021  
**Description:** Measure alert receipt to saved transaction and updated local feedback.  
**Acceptance criteria:**
- Each pilot phone has recorded results.
- Network delay is excluded and measured separately.

#### SUB-042 — Validate pilot bank/channel fixtures

**Issue type:** Sub-task  
**Parent:** TASK-021  
**Description:** Validate HDFC, SBI, SBM, Kotak and Axis formats actually used in pilot.  
**Acceptance criteria:**
- Supported claim has passing purchase and relevant failure/reversal/duplicate cases.
- Unsupported formats do not silently parse as confirmed expenses.

### TASK-022 — Validate accessibility and recovery

**Issue type:** Task  
**Parent:** US-021  
**Description:** Check assistive labels, permission recovery, offline persistence and restart behavior.  
**Acceptance criteria:**
- Core flow status is understandable without color alone.
- Permission revocation and sync failure have a usable recovery path.

#### SUB-043 — Review accessibility semantics

**Issue type:** Sub-task  
**Parent:** TASK-022  
**Description:** Check accessible labels and readable status for core flows.  
**Acceptance criteria:**
- Onboarding, review, transaction and budget controls have screen-reader labels.
- Status is conveyed with text/icon in addition to color.

#### SUB-044 — Exercise offline and permission recovery

**Issue type:** Sub-task  
**Parent:** TASK-022  
**Description:** Test permission loss, manual fallback, offline restart and reconnect.  
**Acceptance criteria:**
- Saved expenses survive restart and sync once after reconnect.
- User can identify unavailable source and recovery steps.

---

## Deferred backlog — do not create as first-release commitments

Create these only after the product owner brings them into scope.

- **BL 01 — Decide visibility of owner's pre-sharing transaction details.** Retained spending in totals is agreed; access to individual prior transaction details is unresolved. The recommendation to show current-month pre-sharing transactions is not approved.
- **BL 02 — Custom categories and their permissions.** First release uses fixed categories with independent limits.
- **BL 03 — Server-backed reconciliation for delayed and post-removal events.** First release flags uncertain membership-boundary cases.
- Foreign currency/exchange-rate reporting; multi-member or multiple shared groups; reciprocal simultaneous sharing; public Play Store distribution; separate cash-flow view; manual spreadsheet-cell editing.

## Traceability

This backlog maps to source requirements BUD 01–05, MEM 01–04, CAP 01–06, TXN 01–03, CAT 01–03, FBK 01–03, DAT 01–03, SYN 01–03, SEC 01–02, NFR 01–02, architecture constraints, data invariants and first-release acceptance criteria. The source requirements file remains authoritative when refining a Jira issue.  
**Note:** Refund attribution across budgets is marked as a proposed rule in the source; confirm it before treating it as an approved product decision.
