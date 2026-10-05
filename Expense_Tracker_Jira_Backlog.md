# Expense Tracker — Jira Backlog

Version 0.2 | Updated 4 October 2026  
Project: **SCRUM — My Software Team**  
Source: [Expense Tracker Requirements v0.2](Expense_Tracker_Requirements.md) and [Confluence requirements](https://sanyam8182.atlassian.net/wiki/spaces/SD/pages/458753/Expense+Tracker+Requirements)

## Creation contract

This Markdown is the copy/MCP creation source; Jira does not directly bulk-import Markdown. Every issue below provides fields suitable for individual creation. The 58 issues were created in SCRUM on 5 October 2026 as SCRUM-5 to SCRUM-62 (see "Jira key mapping"), with 110 "Blocks" links for the dependencies. The Epics in Jira list their AC IDs and point to the child issues for the full criteria text. This document stays the source for refinement.

**Authority:** `Expense_Tracker_Requirements.md` is canonical. Confluence is its published mirror; this backlog is derived from it; files under `docs/archive/` are immutable snapshots. Only D-01, D-02, D-03, D-04, D-05, D-06, D-07, D-08, D-09, D-10, D-11 and D-13 are approved by the user. D-12 is a proposed planning assumption until explicitly approved.

Decision tags in Requirements §8 apply to every listed section and AC in this backlog. Issue-specific wording can clarify ownership or evidence, but it cannot turn a `[D-xx, proposed]` behavior into approved release scope.

Verified project issue types on 4 October 2026 through Jira project metadata: **Epic, Story, Task, Subtask** (exact spelling; Epic/Story/Task are non-subtask types and Subtask is a subtask type). Hierarchy: **Epic → Story or Task → Subtask**. A Task never has a Story parent. Spikes use the ordinary Task type plus a spike label, not an assumed custom type.

There are **7 Epics, 25 Stories, 14 Tasks (including 5 spikes), and 12 Subtasks: 58 issues**. Old TASK-001–022 and SUB-001–044 in v0.1 are retired draft identifiers; no Jira keys exist for them. Preserve US-001–021 references, read their revised content, and use the new IDs below. A v0.1 snapshot is archived for comparison.

Create Epics, then Stories/Tasks, then Subtasks. Replace document Parent/Blocked by IDs with returned Jira keys. If Jira requires relationships after creation, record the ID-to-key mapping and add dependency links after all issues exist. Do not substitute issue links for required parent relationships.

Copy Summary into Summary; copy Description, Source requirements, Acceptance criteria and Evidence into Description unless an actual dedicated field is configured. Labels can be copied as listed. Assignees, estimates, priorities, sprints and custom field IDs are deliberately unset. AC IDs trace to v0.2; issue text may add an ownership or gate clarification, so byte-for-byte text comparison is not a creation requirement. Do not reuse superseded v0.1 acceptance wording.

## Delivery and completion policy

Gate 0 tasks run first; failed feasibility blocks their dependents. Use these labels consistently: **Gate 0 – Feasibility**, **Gate 1 – Personal**, **Gate 2 – Shared**, and **Gate 3 – Pilot**. A mixed issue is labelled **Gate 1 + Gate 2 (mixed)** and its acceptance lines carry the mapped gate. A mixed story closes only at its last gate, Gate 2; Gate 1 evidence is an intermediate checkpoint and cannot close the story. `Inherited from parent gate` is used only for Subtasks. Personal tracking is an intermediate checkpoint; complete first release also includes shared tracking under the accepted server-free limitations. Manual Sheet tampering by authorized editors is not claimed to be prevented.

Each issue must supply implementation/review evidence for its own criteria. A mapped AC is not considered fully covered just because one component is complete. Parent issue acceptance includes its Subtasks; Subtasks do not independently mark their Story done. Each mixed-gate issue must retain the gate tag beside every acceptance criterion when copied into Jira. US-021 signs off requirements Section 9 Gate 3 end to end only after every Gate 0, Gate 1 and Gate 2 dependency listed below is complete.

The backlog is derived from the requirements and may clarify ownership or gate context per issue. Before Jira creation, run `pwsh -File tools/validate_expense_tracker_docs.ps1` when PowerShell 7 is available, or `powershell.exe -ExecutionPolicy Bypass -NoProfile -File tools/validate_expense_tracker_docs.ps1` under the built-in Windows PowerShell 5.1. The checker verifies AC IDs, decision status, issue parents and types, requirement-reference resolution, dependency closure/cycles, decision-field coverage and its agreement with the Requirements §8 matrix and section tags, archive state and US-021 dependency closure. It does not require PowerShell 7.

## Issue index

| Document ID | Jira type | Parent | Summary |
| --- | --- | --- | --- |
| EPIC-01 | Epic | — | Android identity and encrypted foundation |
| EPIC-02 | Epic | — | Validated capture and manual entry |
| EPIC-03 | Epic | — | Transaction review and audit history |
| EPIC-04 | Epic | — | Budgets, functional screens and feedback |
| EPIC-05 | Epic | — | Server-free sharing and lifecycle |
| EPIC-06 | Epic | — | Shared synchronization and data recovery |
| EPIC-07 | Epic | — | Pilot evidence and release gates |
| SPIKE-01 | Task | EPIC-05 | Prove Drive scopes and disclosed permission model |
| SPIKE-02 | Task | EPIC-05 | Prove Sheet-share invitation, discovery and removal |
| SPIKE-03 | Task | EPIC-02 | Collect five-bank fixtures and define supported matrix |
| SPIKE-04 | Task | EPIC-01 | Validate APK permissions and Samsung execution behavior |
| SPIKE-05 | Task | EPIC-01 | Prove encrypted persistence and portable recovery stack |
| TASK-101 | Task | EPIC-01 | Implement encrypted domain database and transactional outbox |
| TASK-102 | Task | EPIC-02 | Implement HDFC pilot alert parser |
| TASK-103 | Task | EPIC-02 | Implement SBI pilot alert parser |
| TASK-104 | Task | EPIC-02 | Implement SBM pilot alert parser |
| TASK-105 | Task | EPIC-02 | Implement Kotak pilot alert parser |
| TASK-106 | Task | EPIC-02 | Implement Axis pilot alert parser |
| TASK-107 | Task | EPIC-06 | Implement Sheet schema validation and non-destructive recovery |
| TASK-108 | Task | EPIC-01 | Package Android 16 pilot and protect diagnostics |
| TASK-109 | Task | EPIC-01 | Implement single-active-device handover controls |
| US-001 | Story | EPIC-01 | Sign in and isolate Google account data |
| US-002 | Story | EPIC-01 | Onboard all capture and notification capabilities |
| US-003 | Story | EPIC-01 | Confirm account/card ownership |
| US-004 | Story | EPIC-02 | Capture and normalize current financial alerts |
| US-005 | Story | EPIC-02 | Resolve duplicate delivery safely |
| US-006 | Story | EPIC-02 | Enter cash or uncaptured purchases manually |
| US-007 | Story | EPIC-03 | Resolve review items and see provisional exposure |
| US-008 | Story | EPIC-03 | Apply payment states and explicit money-flow policy |
| US-009 | Story | EPIC-03 | Apply original-month refunds to open projections |
| US-010 | Story | EPIC-03 | Correct, soft-delete and restore own records |
| US-011 | Story | EPIC-04 | Configure fixed-category monthly budgets |
| US-012 | Story | EPIC-04 | Calculate applicable budgets and safe opening amounts |
| US-013 | Story | EPIC-04 | Configure warnings and show private, fresh feedback |
| US-014 | Story | EPIC-05 | Invite by sharing the Sheet and accept in the app |
| US-015 | Story | EPIC-05 | Complete leave/removal and rejoin with a fresh Sheet |
| US-016 | Story | EPIC-05 | Enforce supported app roles and disclose Google powers |
| US-017 | Story | EPIC-06 | Create epoch files and privacy-safe shared reports |
| US-018 | Story | EPIC-06 | Synchronize stable events and expose conflicts |
| US-019 | Story | EPIC-06 | Export, restore and remind about portable backups |
| US-020 | Story | EPIC-07 | Clean revoked cache and preserve personal data |
| US-021 | Story | EPIC-07 | Complete measurable pilot and accessibility acceptance |
| US-022 | Story | EPIC-03 | Automatically categorize using explicit remembered rules |
| US-023 | Story | EPIC-04 | Browse overview, transactions and audit detail |
| US-024 | Story | EPIC-06 | Wipe local data with explicit scope |
| US-025 | Story | EPIC-02 | Handle safe review excerpts and unsupported sources/currency |
| SUB-101 | Subtask | US-014 | Implement shared-budget discovery and Accept/Decline |
| SUB-102 | Subtask | US-014 | Process the join and handle partial failure |
| SUB-103 | Subtask | US-015 | Implement owner-online revocation state machine |
| SUB-104 | Subtask | US-015 | Archive ended epoch and allocate new Sheet on rejoin |
| SUB-105 | Subtask | US-018 | Fold duplicate append operations deterministically |
| SUB-106 | Subtask | US-018 | Implement version-branch conflict resolution |
| SUB-107 | Subtask | US-019 | Serialize portable encrypted export with exclusions |
| SUB-108 | Subtask | US-019 | Restore transactionally with identity and membership checks |
| SUB-109 | Subtask | US-010 | Apply atomic corrections across categories and boundaries |
| SUB-110 | Subtask | US-010 | Implement tombstone and original-ID restoration |
| SUB-111 | Subtask | US-021 | Measure cold/warm timing and callback reliability |
| SUB-112 | Subtask | US-021 | Audit accessibility and live pilot reconciliation |

## Issue definitions

## EPIC-01 — Android identity and encrypted foundation

**Issue type:** Epic  
**Project:** SCRUM  
**Parent:** None  
**Summary:** Android identity and encrypted foundation  
**Labels:** expense-tracker, v0-2, epic-01  
**Delivery gate:** Gate 0 – Feasibility  
**Source requirements:** ARC 01; CAP 01; CAP 04; DAT 01; SEC 01; SEC 02  
**Source acceptance:** AC 26; AC 31; AC 34; AC 42; AC 61; AC 66  
**Blocked by:** None  

**Description:** Establish the Android 16 app, authenticated per-user data, capability onboarding and confirmed account ownership.

**Acceptance criteria:**

- [ ] **AC 26:** New account remains unconfirmed/unattributed until explicit confirmation; matching last four across different banks does not merge ownership.
- [ ] **AC 31:** SMS receipt, listener access and posting permission each show status/recovery. Denial preserves manual entry and in-app feedback.
- [ ] **AC 34:** Fresh setup requires Google sign-in; valid cached session supports offline; sign-out locks dataset; different account cannot open it.
- [ ] **AC 42:** DB contents are unreadable without protected key; offline restart works; portable export restores with new device key, not by copying the device-bound key.
- [ ] **AC 61:** Replacement device restores portable data and reauthorizes membership; old-device retirement is explained; conflicting active device detected by supported flow blocks shared writes.
- [ ] **AC 66:** Actual private APK install on each pilot phone documents restricted-settings behavior, posting/listener/SMS consent and recovery after battery restrictions.
- [ ] App build and dependency versions are recorded; no backend toolkit is treated as the application stack.

**Evidence:** All child delivery criteria and the mapped integration scenarios have recorded passing evidence; open feasibility blockers cannot be waived as complete.

### SPIKE-04 — Validate APK permissions and Samsung execution behavior

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-01  
**Summary:** Validate APK permissions and Samsung execution behavior  
**Labels:** expense-tracker, v0-2, spike-04, spike  
**Delivery gate:** Gate 0 – Feasibility  
**Source requirements:** ARC 01; CAP 01  
**Source acceptance:** AC 31; AC 66  
**Blocked by:** None  

**Description:** Install minimal private Android 16 APK on both pilot phones and record actual install-source restrictions and receipt/notification behavior.

**Acceptance criteria:**

- [ ] **AC 31:** SMS receipt, listener access and posting permission each show status/recovery. Denial preserves manual entry and in-app feedback.
- [ ] **AC 66:** Actual private APK install on each pilot phone documents restricted-settings behavior, posting/listener/SMS consent and recovery after battery restrictions.
- [ ] Record exact OS builds, installation method, restricted-settings steps if required and battery modes.
- [ ] Exercise SMS/listener/posting denial/revocation independently without losing manual fallback.

**Evidence:** Attach a dated experiment record with both-account/device setup where relevant, observed API behavior, failures and explicit pass/block outcome. A written proposal alone does not pass this spike.

### SPIKE-05 — Prove encrypted persistence and portable recovery stack

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-01  
**Summary:** Prove encrypted persistence and portable recovery stack  
**Labels:** expense-tracker, v0-2, spike-05, spike  
**Delivery gate:** Gate 0 – Feasibility  
**Source requirements:** ARC 01; DAT 01; REC 01  
**Source acceptance:** AC 42; AC 45; AC 61; AC 74  
**Blocked by:** None  

**Description:** Pin compatible Kotlin/Compose/Room/SQLCipher versions and prove Keystore-protected local storage plus export restore under a fresh device key.

**Acceptance criteria:**

- [ ] **AC 42:** DB contents are unreadable without protected key; offline restart works; portable export restores with new device key, not by copying the device-bound key.
- [ ] **AC 45:** Restore requires both correct password and live same-account sign-in; restored expired membership cannot grant access.
- [ ] **AC 61:** Replacement device restores portable data and reauthorizes membership; old-device retirement is explained; conflicting active device detected by supported flow blocks shared writes.
- [ ] Demonstrate clean process restart and rollback of a failed schema migration.
- [ ] A copied database alone is not presented as a portable backup; temporary prototypes contain no real financial data.
 - [ ] **AC 74 [Gate 0]:** Demonstrate same-owner reinstall and rediscovery of the owner's marked Sheet after phone loss; document the blocked state only when rediscovery fails or the Sheet is unreadable.

**Evidence:** Attach a dated experiment record with both-account/device setup where relevant, observed API behavior, failures and explicit pass/block outcome. A written proposal alone does not pass this spike.

### TASK-101 — Implement encrypted domain database and transactional outbox

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-01  
**Summary:** Implement encrypted domain database and transactional outbox  
**Labels:** expense-tracker, v0-2, task-101  
**Delivery gate:** Gate 0 – Feasibility  
**Source requirements:** DAT 01; DAT 03; NFR 01  
**Source acceptance:** AC 08; AC 42; AC 46; AC 70  
**Blocked by:** SPIKE-05  

**Description:** Implement DAT 03 entities, migrations, identity/version constraints, encrypted DB/key lifecycle and atomic commit of record, projections, audit and outbox.

**Acceptance criteria:**

- [ ] **AC 08:** Offline save survives restart; pending/stale state is visible; partial shared failure preserves local data; retry folds once.
- [ ] **AC 42:** DB contents are unreadable without protected key; offline restart works; portable export restores with new device key, not by copying the device-bound key.
- [ ] **AC 46:** Audit history persists for dataset lifetime including tombstones/exports; explicit full local wipe removes local history/key/queue, not remote files or external exports.
- [ ] **AC 70:** Next month without configured limits shows No limit; no rollover/copy assumption; logs/crash reports contain no secrets, raw alerts or private financial payload.
- [ ] Use paise and stable transaction/operation IDs; interrupted write does not acknowledge a missing record.
- [ ] Migration failure preserves recoverable prior data; no financial payload or key material in logs.

**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### TASK-108 — Package Android 16 pilot and protect diagnostics

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-01  
**Summary:** Package Android 16 pilot and protect diagnostics  
**Labels:** expense-tracker, v0-2, task-108  
**Delivery gate:** Gate 0 – Feasibility  
**Source requirements:** ARC 01; CAP 01; REC 01; NFR 01  
**Source acceptance:** AC 31; AC 66; AC 67; AC 70  
**Blocked by:** SPIKE-04, SPIKE-05  

**Description:** Configure native stack, reproducible signed private APK, scoped manifest, redacted diagnostics and backup exclusion rules; record deployment/update steps.

**Acceptance criteria:**

- [ ] **AC 31:** SMS receipt, listener access and posting permission each show status/recovery. Denial preserves manual entry and in-app feedback.
- [ ] **AC 66:** Actual private APK install on each pilot phone documents restricted-settings behavior, posting/listener/SMS consent and recovery after battery restrictions.
- [ ] **AC 67:** After 7 days of unexported changed data reminder appears without blocking use; recent-export date visible; loss risk explained; platform backup excludes key-dependent DB/excerpts/tokens.
- [ ] **AC 70:** Next month without configured limits shows No limit; no rollover/copy assumption; logs/crash reports contain no secrets, raw alerts or private financial payload.
- [ ] Record exact dependency versions and license/build requirements; do not provision Micronaut/Postgres infrastructure.
- [ ] No OAuth refresh token, DB key, review excerpt or financial payload in crash/log output or platform backup.

**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### TASK-109 — Implement single-active-device handover controls

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-01  
**Summary:** Implement single-active-device handover controls  
**Labels:** expense-tracker, v0-2, task-109  
**Delivery gate:** Gate 0 – Feasibility  
**Source requirements:** ARC 01; SEC 01; SEC 02; REC 01  
**Source acceptance:** AC 34; AC 45; AC 61; AC 74  
**Blocked by:** TASK-101, US-001  

**Description:** Provide account-isolated installation identity, replacement handover and supported-flow detection of conflicting devices.

**Acceptance criteria:**

- [ ] **AC 34:** Fresh setup requires Google sign-in; valid cached session supports offline; sign-out locks dataset; different account cannot open it.
- [ ] **AC 45:** Restore requires both correct password and live same-account sign-in; restored expired membership cannot grant access.
- [ ] **AC 61:** Replacement device restores portable data and reauthorizes membership; old-device retirement is explained; conflicting active device detected by supported flow blocks shared writes.
- [ ] Explain that the server-free control cannot stop a hostile modified or permanently offline client.
- [ ] Replacement cannot replay old membership grants and pauses shared work until reauthorized.
 - [ ] **AC 74 [Gate 0]:** After the same-owner reinstall test, if the marked Sheet cannot be rediscovered, preserve invitee data, block rebuild/revocation, and expose a manual closure/export path.

**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-001 — Sign in and isolate Google account data

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-01  
**Summary:** Sign in and isolate Google account data  
**Labels:** expense-tracker, v0-2, us-001  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** SEC 01; REC 01  
**Source acceptance:** AC 34; AC 45  
**Blocked by:** TASK-101  

**Description:** As a user, I want my signed-in account to identify and unlock only my own app data, including offline after an established session. Persist the stable Google account identifier, bind the local database to that identity, and cover sign-out/account-switch and offline-session recovery paths.

**Acceptance criteria:**

- [ ] **AC 34:** Fresh setup requires Google sign-in; valid cached session supports offline; sign-out locks dataset; different account cannot open it.
- [ ] **AC 45:** Restore requires both correct password and live same-account sign-in; restored expired membership cannot grant access.
- [ ] The local database is bound to the Google stable account ID, never to a display name or email; opening the app with a different account does not expose the first account's data.
- [ ] Sign-out locks the dataset; a valid cached session keeps working with no network.
- [ ] Revoked or expired Google authorization is detected and recovered by signing in again, without deleting any local data.
- [ ] No bank credential, card PIN or OTP is requested or stored anywhere in the flow.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-002 — Onboard all capture and notification capabilities

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-01  
**Summary:** Onboard all capture and notification capabilities  
**Labels:** expense-tracker, v0-2, us-002  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** CAP 01; CAP 06  
**Source acceptance:** AC 31; AC 66  
**Blocked by:** SPIKE-04, TASK-108  

**Description:** As a user, I want separate SMS, listener and posting-permission status and actionable recovery while retaining manual entry. Implement a capability state model that refreshes after Settings return or revocation and keeps manual entry available when any capability is denied.

**Acceptance criteria:**

- [ ] **AC 31:** SMS receipt, listener access and posting permission each show status/recovery. Denial preserves manual entry and in-app feedback.
- [ ] **AC 66:** Actual private APK install on each pilot phone documents restricted-settings behavior, posting/listener/SMS consent and recovery after battery restrictions.
- [ ] SMS receipt, notification-listener access and notification-posting permission are tracked as three independent states and refresh when the user returns from Android Settings or a permission is revoked.
- [ ] Each denied or revoked capability names the unavailable source and the recovery step; manual entry stays usable in every combination of the three states.
- [ ] Only the SMS permissions needed for new-message capture are requested; there is no inbox import.
- [ ] The restricted-settings explanation appears only where SPIKE-04 recorded that Android blocks the step.
- [ ] Last successful capture time and the count of unsupported alerts are visible.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-003 — Confirm account/card ownership

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-01  
**Summary:** Confirm account/card ownership  
**Labels:** expense-tracker, v0-2, us-003  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** CAP 04  
**Source acceptance:** AC 26; AC 72  
**Blocked by:** TASK-101, US-001  

**Description:** As an account holder, I want to confirm new account mappings before captured transactions affect a budget. Store an explicit account-holder confirmation event, keep unresolved candidates outside totals, and replay earlier candidates against their original month after confirmation.

**Acceptance criteria:**

- [ ] **AC 26:** New account remains unconfirmed/unattributed until explicit confirmation; matching last four across different banks does not merge ownership.
- [ ] **AC 72 [Gate 1]:** Confirming an account re-evaluates earlier captured candidates in their original occurrence/receipt month; items without a usable date remain in review and do not count merely because the account was confirmed.
- [ ] A newly detected account or card is stored as unconfirmed and attributed to no budget.
- [ ] Account identity comes from bank and account context; two accounts with the same last four digits at different banks stay separate.
- [ ] Confirmation is stored as an explicit account-holder event that survives restart and app updates.
- [ ] On confirmation, earlier candidates are re-evaluated once, in their original month; candidates without a usable date stay in review.
- [ ] Declining or ignoring leaves the transactions unassigned and outside every total.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

## EPIC-02 — Validated capture and manual entry

**Issue type:** Epic  
**Project:** SCRUM  
**Parent:** None  
**Summary:** Validated capture and manual entry  
**Labels:** expense-tracker, v0-2, epic-02  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** CAP 02; CAP 03; CAP 05; CAP 06; RAW 01  
**Source acceptance:** AC 02; AC 12; AC 32; AC 39; AC 40; AC 50; AC 51; AC 62  
**Blocked by:** None  

**Description:** Deliver maintainable parsers for the actual five-bank pilot fixtures, correct normalization/deduplication, safe source handling and manual fallback.

**Acceptance criteria:**

- [ ] **AC 02:** SMS, notification and sync replay of a strongly matched purchase count once; distinct equal-value references remain separate.
- [ ] **AC 12:** Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable.
- [ ] **AC 32:** Manual cash purchase saves with amount/date/category only; optional fields remain unknown and eligible budgets update once.
- [ ] **AC 39:** Bank occurrence controls Asia/Kolkata month; missing date uses review-flagged received time; manual uses entered date; boundary-day ambiguity is not silently shared.
- [ ] **AC 40:** Reference/account strong match consolidates; differing references stay separate; fuzzy no-reference candidate requires explicit duplicate resolution.
- [ ] **AC 50:** Only allowlisted verified packages processed; messaging mirrors excluded by default; same notification key updates one candidate; explicitly enabled fallback deduplicates.
- [ ] **AC 51:** OTP/unrelated content never persists; sanitized unresolved excerpt encrypted locally only and erased on resolution or dismissal (no automatic expiry); export/log/Sheet contains none.
- [ ] **AC 62:** USD alert shows unsupported-currency item with original units, excluded from INR/pending-INR subtotal; no inferred conversion.


**Evidence:** All child delivery criteria and the mapped integration scenarios have recorded passing evidence; open feasibility blockers cannot be waived as complete.

### SPIKE-03 — Collect five-bank fixtures and define supported matrix

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-02  
**Summary:** Collect five-bank fixtures and define supported matrix  
**Labels:** expense-tracker, v0-2, spike-03, spike  
**Delivery gate:** Gate 0 – Feasibility  
**Delivery order:** 0 — start before parser implementation  
**Source requirements:** CAP 02; CAP 05; TXN 02  
**Source acceptance:** AC 12; AC 40; AC 50; AC 63; AC 76  
**Blocked by:** None  

**Description:** Collect only consented/redacted actual HDFC, SBI, SBM, Kotak and Axis pilot bank/channel/source samples and exact installed package identities.

**Acceptance criteria:**

- [ ] **AC 12:** Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable.
- [ ] **AC 40:** Reference/account strong match consolidates; differing references stay separate; fuzzy no-reference candidate requires explicit duplicate resolution.
- [ ] **AC 50:** Only allowlisted verified packages processed; messaging mirrors excluded by default; same notification key updates one candidate; explicitly enabled fallback deduplicates.
- [ ] **AC 63:** ATM/wallet funding excluded; SIP/investment debit, insurance and subscription charges counted in the chosen fixed category (Other by default for investments); ambiguous EMI reviewed and never blindly counted in full.
- [ ] List available and unavailable bank/channel combinations; obtain real-format evidence before claiming support.
- [ ] Label amount/date precision/account/reference/status and expected counts; synthetic edge cases are marked and do not establish support.
- [ ] **AC 76 [Gate 0]:** Define the bank statement or alert log plus user confirmation as receipt-rate ground truth, and propose fixture/live sample sizes for explicit approval before Gate 3.

**Evidence:** Attach a dated experiment record with both-account/device setup where relevant, observed API behavior, failures and explicit pass/block outcome. A written proposal alone does not pass this spike.

### TASK-102 — Implement HDFC pilot alert parser

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-02  
**Summary:** Implement HDFC pilot alert parser  
**Labels:** expense-tracker, v0-2, task-102  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** CAP 02; CAP 03; CAP 05  
**Source acceptance:** AC 12; AC 39; AC 50; AC 62  
**Blocked by:** SPIKE-03, US-004  

**Description:** Implement HDFC-specific SMS and supported notification format families identified in the fixture matrix; integrate the normalized parser contract.

**Acceptance criteria:**

- [ ] **AC 12:** Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable.
- [ ] **AC 39:** Bank occurrence controls Asia/Kolkata month; missing date uses review-flagged received time; manual uses entered date; boundary-day ambiguity is not silently shared.
- [ ] **AC 50:** Only allowlisted verified packages processed; messaging mirrors excluded by default; same notification key updates one candidate; explicitly enabled fallback deduplicates.
- [ ] **AC 62:** USD alert shows unsupported-currency item with original units, excluded from INR/pending-INR subtotal; no inferred conversion.
- [ ] Every claimed HDFC combination satisfies Gate 3 fixture counts and exact expected normalized fields/status; unavailable formats remain explicitly unsupported.
- [ ] Preserve missing fields/date precision; distinguish pending/failed/debit/refund/repayment, reject unsupported currency from INR totals, and flag ambiguity instead of inventing values.

**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### TASK-103 — Implement SBI pilot alert parser

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-02  
**Summary:** Implement SBI pilot alert parser  
**Labels:** expense-tracker, v0-2, task-103  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** CAP 02; CAP 03; CAP 05  
**Source acceptance:** AC 12; AC 39; AC 50; AC 62  
**Blocked by:** SPIKE-03, US-004  

**Description:** Implement SBI-specific SMS and supported notification format families identified in the fixture matrix; integrate the normalized parser contract.

**Acceptance criteria:**

- [ ] **AC 12:** Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable.
- [ ] **AC 39:** Bank occurrence controls Asia/Kolkata month; missing date uses review-flagged received time; manual uses entered date; boundary-day ambiguity is not silently shared.
- [ ] **AC 50:** Only allowlisted verified packages processed; messaging mirrors excluded by default; same notification key updates one candidate; explicitly enabled fallback deduplicates.
- [ ] **AC 62:** USD alert shows unsupported-currency item with original units, excluded from INR/pending-INR subtotal; no inferred conversion.
- [ ] Every claimed SBI combination satisfies Gate 3 fixture counts and exact expected normalized fields/status; unavailable formats remain explicitly unsupported.
- [ ] Preserve missing fields/date precision; distinguish pending/failed/debit/refund/repayment, reject unsupported currency from INR totals, and flag ambiguity instead of inventing values.

**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### TASK-104 — Implement SBM pilot alert parser

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-02  
**Summary:** Implement SBM pilot alert parser  
**Labels:** expense-tracker, v0-2, task-104  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** CAP 02; CAP 03; CAP 05  
**Source acceptance:** AC 12; AC 39; AC 50; AC 62  
**Blocked by:** SPIKE-03, US-004  

**Description:** Implement SBM-specific SMS and supported notification format families identified in the fixture matrix; integrate the normalized parser contract.

**Acceptance criteria:**

- [ ] **AC 12:** Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable.
- [ ] **AC 39:** Bank occurrence controls Asia/Kolkata month; missing date uses review-flagged received time; manual uses entered date; boundary-day ambiguity is not silently shared.
- [ ] **AC 50:** Only allowlisted verified packages processed; messaging mirrors excluded by default; same notification key updates one candidate; explicitly enabled fallback deduplicates.
- [ ] **AC 62:** USD alert shows unsupported-currency item with original units, excluded from INR/pending-INR subtotal; no inferred conversion.
- [ ] Every claimed SBM combination satisfies Gate 3 fixture counts and exact expected normalized fields/status; unavailable formats remain explicitly unsupported.
- [ ] Preserve missing fields/date precision; distinguish pending/failed/debit/refund/repayment, reject unsupported currency from INR totals, and flag ambiguity instead of inventing values.

**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### TASK-105 — Implement Kotak pilot alert parser

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-02  
**Summary:** Implement Kotak pilot alert parser  
**Labels:** expense-tracker, v0-2, task-105  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** CAP 02; CAP 03; CAP 05  
**Source acceptance:** AC 12; AC 39; AC 50; AC 62  
**Blocked by:** SPIKE-03, US-004  

**Description:** Implement Kotak-specific SMS and supported notification format families identified in the fixture matrix; integrate the normalized parser contract.

**Acceptance criteria:**

- [ ] **AC 12:** Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable.
- [ ] **AC 39:** Bank occurrence controls Asia/Kolkata month; missing date uses review-flagged received time; manual uses entered date; boundary-day ambiguity is not silently shared.
- [ ] **AC 50:** Only allowlisted verified packages processed; messaging mirrors excluded by default; same notification key updates one candidate; explicitly enabled fallback deduplicates.
- [ ] **AC 62:** USD alert shows unsupported-currency item with original units, excluded from INR/pending-INR subtotal; no inferred conversion.
- [ ] Every claimed Kotak combination satisfies Gate 3 fixture counts and exact expected normalized fields/status; unavailable formats remain explicitly unsupported.
- [ ] Preserve missing fields/date precision; distinguish pending/failed/debit/refund/repayment, reject unsupported currency from INR totals, and flag ambiguity instead of inventing values.

**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### TASK-106 — Implement Axis pilot alert parser

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-02  
**Summary:** Implement Axis pilot alert parser  
**Labels:** expense-tracker, v0-2, task-106  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** CAP 02; CAP 03; CAP 05  
**Source acceptance:** AC 12; AC 39; AC 50; AC 62  
**Blocked by:** SPIKE-03, US-004  

**Description:** Implement Axis-specific SMS and supported notification format families identified in the fixture matrix; integrate the normalized parser contract.

**Acceptance criteria:**

- [ ] **AC 12:** Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable.
- [ ] **AC 39:** Bank occurrence controls Asia/Kolkata month; missing date uses review-flagged received time; manual uses entered date; boundary-day ambiguity is not silently shared.
- [ ] **AC 50:** Only allowlisted verified packages processed; messaging mirrors excluded by default; same notification key updates one candidate; explicitly enabled fallback deduplicates.
- [ ] **AC 62:** USD alert shows unsupported-currency item with original units, excluded from INR/pending-INR subtotal; no inferred conversion.
- [ ] Every claimed Axis combination satisfies Gate 3 fixture counts and exact expected normalized fields/status; unavailable formats remain explicitly unsupported.
- [ ] Preserve missing fields/date precision; distinguish pending/failed/debit/refund/repayment, reject unsupported currency from INR totals, and flag ambiguity instead of inventing values.

**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-004 — Capture and normalize current financial alerts

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-02  
**Summary:** Capture and normalize current financial alerts  
**Labels:** expense-tracker, v0-2, us-004  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** CAP 01; CAP 02; CAP 03; CAP 06; TXN 01  
**Source acceptance:** AC 06; AC 12; AC 39; AC 50; AC 62  
**Blocked by:** SPIKE-03, US-002, TASK-101  

**Description:** As a user, I want current allowlisted financial alerts delivered into a consistent on-device parser contract with unknown fields and provenance preserved.

**Acceptance criteria:**

- [ ] **AC 06:** OTP/request/failed payment adds no expense; confirmed partial refund changes original open projections once.
- [ ] **AC 12:** Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable.
- [ ] **AC 39:** Bank occurrence controls Asia/Kolkata month; missing date uses review-flagged received time; manual uses entered date; boundary-day ambiguity is not silently shared.
- [ ] **AC 50:** Only allowlisted verified packages processed; messaging mirrors excluded by default; same notification key updates one candidate; explicitly enabled fallback deduplicates.
- [ ] **AC 62:** USD alert shows unsupported-currency item with original units, excluded from INR/pending-INR subtotal; no inferred conversion.
- [ ] Notifications are processed only from the allowlisted verified packages; all others are dropped with nothing stored or logged about them.
- [ ] Each candidate records source, parser version, received time and date source/precision; fields the alert does not contain stay unknown.
- [ ] OTPs, payment requests and promotions never create an expense, and a failed payment counts zero.
- [ ] Parsing works offline whenever Android delivers the alert, and raw text is deleted once parsing succeeds.
- [ ] Unsupported or uncertain alerts enter the review inbox instead of being counted.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-005 — Resolve duplicate delivery safely

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-02  
**Summary:** Resolve duplicate delivery safely  
**Labels:** expense-tracker, v0-2, us-005  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** CAP 05  
**Source acceptance:** AC 02; AC 40  
**Blocked by:** US-004, US-003  

**Description:** As a user, I want one logical expense per real payment, with ambiguous matches reviewed instead of silently merged.

**Acceptance criteria:**

- [ ] **AC 02:** SMS, notification and sync replay of a strongly matched purchase count once; distinct equal-value references remain separate.
- [ ] **AC 40:** Reference/account strong match consolidates; differing references stay separate; fuzzy no-reference candidate requires explicit duplicate resolution.
- [ ] Replaying the same source event (same fingerprint, or same notification package and key) creates no second candidate.
- [ ] A strong match needs the same normalized reference and a confirmed account context; conflicting references or accounts are never merged.
- [ ] Equal INR amount within 120 seconds, compatible direction and payee, and no conflicting reference is flagged as a possible duplicate and sent to review, never merged automatically.
- [ ] Merging a confirmed duplicate keeps one logical transaction ID, and source aliases point to it.
- [ ] Matching thresholds are versioned and evaluated against the SPIKE-03 fixtures.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-006 — Enter cash or uncaptured purchases manually

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-02  
**Summary:** Enter cash or uncaptured purchases manually  
**Labels:** expense-tracker, v0-2, us-006  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** CAP 06; BUD 04  
**Source acceptance:** AC 32; AC 39  
**Blocked by:** TASK-101, US-011  

**Description:** As a user, I want to record an INR purchase using amount, date and one category while optional information stays unknown.

**Acceptance criteria:**

- [ ] **AC 32:** Manual cash purchase saves with amount/date/category only; optional fields remain unknown and eligible budgets update once.
- [ ] **AC 39:** Bank occurrence controls Asia/Kolkata month; missing date uses review-flagged received time; manual uses entered date; boundary-day ambiguity is not silently shared.
- [ ] Saving requires a positive INR amount, an entered date and one fixed category; a missing field blocks the save with a clear prompt.
- [ ] Merchant, account, payment method and notes stay unknown when left blank.
- [ ] The amount is stored in integer paise with no rounding drift.
- [ ] The record is persisted before the success message, and every applicable budget updates exactly once.
- [ ] The month is attributed in Asia/Kolkata from the entered date.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-025 — Handle safe review excerpts and unsupported sources/currency

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-02  
**Summary:** Handle safe review excerpts and unsupported sources/currency  
**Labels:** expense-tracker, v0-2, us-025  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** RAW 01; CAP 02; CAP 03  
**Source acceptance:** AC 50; AC 51; AC 62  
**Blocked by:** US-004, TASK-101  

**Description:** As a user, I want review context retained only when safe and necessary, removed when I resolve or dismiss the item, and visible unsupported currency instead of silent loss.

**Acceptance criteria:**

- [ ] **AC 50:** Only allowlisted verified packages processed; messaging mirrors excluded by default; same notification key updates one candidate; explicitly enabled fallback deduplicates.
- [ ] **AC 51:** OTP/unrelated content never persists; sanitized unresolved excerpt encrypted locally only and erased on resolution or dismissal (no automatic expiry); export/log/Sheet contains none.
- [ ] **AC 62:** USD alert shows unsupported-currency item with original units, excluded from INR/pending-INR subtotal; no inferred conversion.
- [ ] For an allowlisted alert that cannot be parsed, only a sanitized excerpt is stored: OTPs, full account or card numbers and unrelated text are removed first; if safe cleaning is uncertain, only a generic failure item is stored.
- [ ] The excerpt is encrypted and never written to logs, exports, platform backup or the Sheet; it is deleted when the item is resolved or dismissed, or by a full local wipe.
- [ ] Non-INR alerts show as "Unsupported currency" with the original amount and currency, never enter INR totals and cannot be confirmed as INR.
- [ ] An OTP message is never persisted, not even as a failed item.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

## EPIC-03 — Transaction review and audit history

**Issue type:** Epic  
**Project:** SCRUM  
**Parent:** None  
**Summary:** Transaction review and audit history  
**Labels:** expense-tracker, v0-2, epic-03  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** TXN 01; TXN 02; TXN 03; CAT 01; CAT 02; CAT 03; MEM 04  
**Source acceptance:** AC 05; AC 06; AC 07; AC 09; AC 14; AC 18; AC 27; AC 28; AC 29; AC 35; AC 44; AC 52; AC 53; AC 54; AC 63  
**Blocked by:** None  

**Description:** Implement one counting model, deterministic classification, corrections, original-month refunds, tombstones and restore.

**Acceptance criteria:**

- [ ] **AC 05:** Own-account/inter-user transfers and card repayment add zero; original card purchase counts once.
- [ ] **AC 06:** OTP/request/failed payment adds no expense; confirmed partial refund changes original open projections once.
 - [ ] **AC 07:** Unknown merchant/category remains pending and outside all totals. Confirming Fashion adds its full amount once to each eligible budget. This replaces v0.1's counted Uncategorized behavior.
- [ ] **AC 09:** Own category/date/amount correction removes old and applies new totals consistently; a category moves full amount without a split.
- [ ] **AC 14:** ₹400 October refund of a ₹1,000 September purchase reduces September spend by ₹400 without changing September limit or October budget. Duplicate refund counts once, subject to closed-epoch rules.
- [ ] **AC 18:** Own soft-delete removes active eligible spend/views after sync; ID/tombstone/history persist; other user's app deletion is rejected.
- [ ] **AC 27:** Exact validated/remembered unambiguous rule auto-classifies; low confidence, unknown, duplicate or ambiguous transfer remains excluded until all review gates pass.
- [ ] **AC 28:** Ambiguous UPI stays excluded pending purpose; confirmed purchase counts; confirmed transfer/repayment does not.
- [ ] **AC 29:** Pending/failed contributes zero, confirmed posted purchase once, linked refund/reversal once in original month.
- [ ] **AC 35:** Salary/unrelated deposit contributes zero; only linked confirmed refund adjusts spend; unlinked credit cannot silently become a refund.
- [ ] **AC 44:** After termination own personal correction/refund is allowed; closed shared totals remain frozen. No owner queue or retroactive write; rejected late sync is visible to author.
- [ ] **AC 52:** Pending ₹X/count excludes confirmed duplicates, counts candidate cluster at most once, and separates unknown/non-INR counts; confirmed remaining is labeled incomplete while pending.
- [ ] **AC 53:** Remember mapping only after explicit choice; apply to future own candidates, expose removal, preserve old history and other user's rules; competing rules go to review.
- [ ] **AC 54:** Restore own deleted ID adds eligible open projections once; unauthorized restore rejected; closed shared epoch stays frozen.
- [ ] **AC 63:** ATM/wallet funding excluded; SIP/investment debit, insurance and subscription charges counted in the chosen fixed category (Other by default for investments); ambiguous EMI reviewed and never blindly counted in full.


**Evidence:** All child delivery criteria and the mapped integration scenarios have recorded passing evidence; open feasibility blockers cannot be waived as complete.

### US-007 — Resolve review items and see provisional exposure

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-03  
**Summary:** Resolve review items and see provisional exposure  
**Labels:** expense-tracker, v0-2, us-007  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** CAT 01; CAT 02; TXN 01  
**Source acceptance:** AC 07; AC 27; AC 28; AC 52  
**Blocked by:** US-004, US-005  

**Description:** As a user, I want incomplete financial activity visible without treating it as confirmed spending or overstating the completeness of my balance.

**Acceptance criteria:**

- [ ] **AC 07:** Unknown merchant/category remains pending and outside all totals. Confirming Fashion adds its full amount once to each eligible budget. This replaces v0.1's counted Uncategorized behavior.
- [ ] **AC 27:** Exact validated/remembered unambiguous rule auto-classifies; low confidence, unknown, duplicate or ambiguous transfer remains excluded until all review gates pass.
- [ ] **AC 28:** Ambiguous UPI stays excluded pending purpose; confirmed purchase counts; confirmed transfer/repayment does not.
- [ ] **AC 52:** Pending ₹X/count excludes confirmed duplicates, counts candidate cluster at most once, and separates unknown/non-INR counts; confirmed remaining is labeled incomplete while pending.
- [ ] Low-confidence, unknown, possible-duplicate, ambiguous-transfer, unconfirmed-account, undated and non-INR items appear in the inbox with their reason and are excluded from every total.
- [ ] "Pending review: ₹X (n items)" is shown separately: a possible-duplicate cluster counts once, unknown-amount and non-INR items are counted separately, and the confirmed remaining is labelled incomplete while items are pending.
- [ ] Resolving an item (confirm category, intent, account or date; merge; mark non-expense; dismiss; enter missing details) updates the same transaction and never creates a second counted expense.
- [ ] Pending items and their reasons survive a restart.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-008 — Apply payment states and explicit money-flow policy

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-03  
**Summary:** Apply payment states and explicit money-flow policy  
**Labels:** expense-tracker, v0-2, us-008  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** TXN 01; TXN 02; TXN 03  
**Source acceptance:** AC 05; AC 06; AC 28; AC 29; AC 35; AC 63  
**Blocked by:** US-007  

**Description:** As a user, I want only confirmed eligible expenses counted, with transfers, funding movements, credits and ambiguous installments handled consistently.

**Acceptance criteria:**

- [ ] **AC 05:** Own-account/inter-user transfers and card repayment add zero; original card purchase counts once.
- [ ] **AC 06:** OTP/request/failed payment adds no expense; confirmed partial refund changes original open projections once.
- [ ] **AC 28:** Ambiguous UPI stays excluded pending purpose; confirmed purchase counts; confirmed transfer/repayment does not.
- [ ] **AC 29:** Pending/failed contributes zero, confirmed posted purchase once, linked refund/reversal once in original month.
- [ ] **AC 35:** Salary/unrelated deposit contributes zero; only linked confirmed refund adjusts spend; unlinked credit cannot silently become a refund.
- [ ] **AC 63:** ATM/wallet funding excluded; SIP/investment debit, insurance and subscription charges counted in the chosen fixed category (Other by default for investments); ambiguous EMI reviewed and never blindly counted in full.
- [ ] Counting follows the TXN 01 table: pending, failed, OTP or request, excluded transfers and credits count zero; only a posted, confirmed INR purchase with one category counts.
- [ ] Each TXN 02 row has a test: own-account transfer, inter-user transfer, card repayment, salary, cash withdrawal and wallet top-up are excluded; SIP, insurance and subscriptions count in the stated category; EMI goes to review; reminders and mandates count zero until the real debit.
- [ ] An ambiguous UPI payment stays outside totals until confirmed as a purchase or a transfer.
- [ ] Repeated delivery or a status update recalculates once (idempotent).


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-009 — Apply original-month refunds to open projections

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-03  
**Summary:** Apply original-month refunds to open projections  
**Labels:** expense-tracker, v0-2, us-009  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** TXN 03; BUD 05; HIST 01; MEM 04  
**Source acceptance:** AC 14; AC 29; AC 35; AC 44; AC 47  
**Blocked by:** US-008, US-012  

**Description:** As a transaction owner, I want linked partial/full refunds to adjust original purchase spending, respecting private opening data and frozen shared epochs.

**Acceptance criteria:**

- [ ] **AC 14:** ₹400 October refund of a ₹1,000 September purchase reduces September spend by ₹400 without changing September limit or October budget. Duplicate refund counts once, subject to closed-epoch rules.
- [ ] **AC 29:** Pending/failed contributes zero, confirmed posted purchase once, linked refund/reversal once in original month.
- [ ] **AC 35:** Salary/unrelated deposit contributes zero; only linked confirmed refund adjusts spend; unlinked credit cannot silently become a refund.
- [ ] **AC 44:** After termination own personal correction/refund is allowed; closed shared totals remain frozen. No owner queue or retroactive write; rejected late sync is visible to author.
- [ ] **AC 47:** Refund/correction to owner's pre-sharing purchase updates only safe opening aggregate while epoch active; no private transaction identifier/detail appears in Sheet.
- [ ] A refund links to its original purchase, supports partial amounts, and cumulative refunds cannot exceed the purchase; excess or unusual cases go to review.
- [ ] The adjustment lands in the original purchase's month and category and changes neither that month's limit nor the refund month's spending.
- [ ] Repeated delivery of the same refund adjusts once.
- [ ] A refund of a pre-sharing purchase changes only the private purchase and the opening aggregate; a refund affecting an ended epoch changes only the personal copy.
- [ ] Unlinked credits never become refunds automatically.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-010 — Correct, soft-delete and restore own records

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-03  
**Summary:** Correct, soft-delete and restore own records  
**Labels:** expense-tracker, v0-2, us-010  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** CAT 03; MEM 04; BUD 04  
**Source acceptance:** AC 09; AC 16; AC 18; AC 44; AC 46; AC 54  
**Blocked by:** US-008, US-012  

**Description:** As a transaction owner, I want reversible corrections with retained audit history, consistent date-boundary attribution and no change to frozen shared history.

**Acceptance criteria:**

- [ ] **AC 09:** Own category/date/amount correction removes old and applies new totals consistently; a category moves full amount without a split.
- [ ] **AC 16:** Active user corrects only their own record; all open eligible projections update once and audit entry persists. Other user's app edits are rejected, including budget-owner attempts.
- [ ] **AC 18:** Own soft-delete removes active eligible spend/views after sync; ID/tombstone/history persist; other user's app deletion is rejected.
- [ ] **AC 44:** After termination own personal correction/refund is allowed; closed shared totals remain frozen. No owner queue or retroactive write; rejected late sync is visible to author.
- [ ] **AC 46:** Audit history persists for dataset lifetime including tombstones/exports; explicit full local wipe removes local history/key/queue, not remote files or external exports.
- [ ] **AC 54:** Restore own deleted ID adds eligible open projections once; unauthorized restore rejected; closed shared epoch stays frozen.
- [ ] Only the transaction owner can change category, amount or date, delete or restore; the budget owner has no override.
- [ ] A correction moves the full amount between the old and new category or month in one local transaction and writes an audit entry (actor, time, old and new values, operation ID, version).
- [ ] A date correction that crosses a join or leave boundary adds or removes the shared attribution; a same-day boundary asks "before or after".
- [ ] Deleting leaves a tombstone with the original ID; restoring reruns validation, duplicate and membership gates and keeps the ID.
- [ ] After a membership ends, edits change the personal copy only.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-109 — Apply atomic corrections across categories and boundaries

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-010  
**Summary:** Apply atomic corrections across categories and boundaries  
**Labels:** expense-tracker, v0-2, sub-109  
**Delivery gate:** Inherited from parent gate  
**Source requirements:** CAT 03; BUD 04; MEM 04  
**Source acceptance:** AC 09; AC 16; AC 39  
**Blocked by:** None  

**Description:** Adjust full old/new contributions and history in one local transaction; unresolved boundary does not silently share.

**Acceptance criteria:**

- [ ] **AC 09:** Own category/date/amount correction removes old and applies new totals consistently; a category moves full amount without a split.
- [ ] **AC 16:** Active user corrects only their own record; all open eligible projections update once and audit entry persists. Other user's app edits are rejected, including budget-owner attempts.
- [ ] **AC 39:** Bank occurrence controls Asia/Kolkata month; missing date uses review-flagged received time; manual uses entered date; boundary-day ambiguity is not silently shared.
- [ ] A correction is all-or-nothing: a failure part-way leaves the old totals unchanged.
- [ ] A category move changes neither overall spending nor the number of expenses.
- [ ] Tests cover a date moved across the join time, across a month end, and into the gap.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-110 — Implement tombstone and original-ID restoration

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-010  
**Summary:** Implement tombstone and original-ID restoration  
**Labels:** expense-tracker, v0-2, sub-110  
**Delivery gate:** Inherited from parent gate  
**Source requirements:** CAT 03  
**Source acceptance:** AC 18; AC 46; AC 54  
**Blocked by:** None  

**Description:** Restore only own record through all counting/membership gates, retaining prior history and leaving closed epochs frozen.

**Acceptance criteria:**

- [ ] **AC 18:** Own soft-delete removes active eligible spend/views after sync; ID/tombstone/history persist; other user's app deletion is rejected.
- [ ] **AC 46:** Audit history persists for dataset lifetime including tombstones/exports; explicit full local wipe removes local history/key/queue, not remote files or external exports.
- [ ] **AC 54:** Restore own deleted ID adds eligible open projections once; unauthorized restore rejected; closed shared epoch stays frozen.
- [ ] A deleted item disappears from totals and normal lists, while its ID, deletion marker and history persist.
- [ ] A restore attempt by anyone other than the transaction owner is rejected with no change.
- [ ] Restoring after the epoch has closed restores the personal copy only.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-022 — Automatically categorize using explicit remembered rules

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-03  
**Summary:** Automatically categorize using explicit remembered rules  
**Labels:** expense-tracker, v0-2, us-022  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** CAT 01  
**Source acceptance:** AC 27; AC 53; AC 71  
**Blocked by:** US-007, US-008  

**Description:** As a user, I want unambiguous validated rules and my opted-in merchant mappings to categorize future own purchases without rewriting history.

**Acceptance criteria:**

- [ ] **AC 27:** Exact validated/remembered unambiguous rule auto-classifies; low confidence, unknown, duplicate or ambiguous transfer remains excluded until all review gates pass.
- [ ] **AC 53:** Remember mapping only after explicit choice; apply to future own candidates, expose removal, preserve old history and other user's rules; competing rules go to review.
- [ ] **AC 71 [Gate 1]:** A remembered rule can set purchase, transfer/repayment or excluded intent (and a fixed category for purchases); it applies only to future own candidates after normal account/date/duplicate gates, and can be removed.
- [ ] Auto-classification happens only for an exact validated rule or an explicit remembered mapping with no competing rule and all counting gates passed.
- [ ] "Remember" is offered only after the user corrects a candidate, is private to that user, can set intent (purchase, transfer/repayment, excluded) and, for purchases, a fixed category.
- [ ] Remembered rules can be viewed and removed; they apply to future candidates only and never rewrite history or another user's rules.
- [ ] A marketplace name alone never classifies an item, and conflicting rules send it to review.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

## EPIC-04 — Budgets, functional screens and feedback

**Issue type:** Epic  
**Project:** SCRUM  
**Parent:** None  
**Summary:** Budgets, functional screens and feedback  
**Labels:** expense-tracker, v0-2, epic-04  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** BUD 01; BUD 02; BUD 03; BUD 04; BUD 05; FBK 01; FBK 02; FBK 03; HIST 01; UI 01  
**Source acceptance:** AC 01; AC 03; AC 10; AC 13; AC 21; AC 22; AC 36; AC 37; AC 38; AC 56; AC 57; AC 64; AC 70  
**Blocked by:** None  

**Description:** Provide exact monthly calculations, role-appropriate views, opening aggregates, threshold settings and transparent pending/stale states.

**Acceptance criteria:**

- [ ] **AC 01:** Invitee's confirmed ₹1,000 Fashion purchase reduces personal/shared remaining from ₹4,000/₹5,000 to ₹3,000/₹4,000 with one logical ID.
- [ ] **AC 03:** Owner spending affects only converted shared budget; invitee spending affects personal plus shared; owner has no extra personal budget.
- [ ] **AC 10:** Expense crosses thresholds and then limit; one alert per eligible crossing and purchase remains saved above limit.
- [ ] **AC 13:** Owner ₹5,000 limit and ₹1,000 spent remain ₹4,000 available when sharing starts; ₹1,000 is a private-detail-free opening amount. Invitee's later ₹500 adds once to both budgets; earlier invitee transactions stay private.
- [ ] **AC 21:** Default locked-device notification reveals no amount, merchant, category or balance; unlocked content and explicit privacy preference work.
- [ ] **AC 22:** 80%/100% crossings alert once; duplicate replay does not. Owner controls shared configuration, personal user theirs; configuration edits establish baseline without replay.
- [ ] **AC 36:** Past limit edits rejected; current/future edits target one month; legitimate refund/correction may still change historical spent, not its locked limit.
- [ ] **AC 37:** No-limit category counts spending, shows No limit, no numeric remaining/over-budget/percentage warning.
- [ ] **AC 38:** Expense beyond positive limit persists and syncs, showing negative remaining and Over budget.
- [ ] **AC 56:** Invalid/duplicate threshold rejected; zero/no-limit percentages suppressed; sync jump coalesces alerts; devices may notify at different times without promising simultaneous delivery.
- [ ] **AC 57:** Last sync always visible; never-synced/failed or ≥15-minute-old shared state marked stale; known pending shown immediately; successful fetch/apply resets freshness.
- [ ] **AC 64:** Overview and transaction filters expose accurate month/spender/category/account/method/review views, opening labels, sync freshness and Indian currency formatting.
- [ ] **AC 70:** Next month without configured limits shows No limit; no rollover/copy assumption; logs/crash reports contain no secrets, raw alerts or private financial payload.


**Evidence:** All child delivery criteria and the mapped integration scenarios have recorded passing evidence; open feasibility blockers cannot be waived as complete.

### US-011 — Configure fixed-category monthly budgets

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-04  
**Summary:** Configure fixed-category monthly budgets  
**Labels:** expense-tracker, v0-2, us-011  
**Delivery gate:** Gate 1 – Personal  
**Source requirements:** BUD 01; BUD 02; BUD 04; BUD 05  
**Source acceptance:** AC 36; AC 37; AC 38; AC 70  
**Blocked by:** TASK-101  

**Description:** As a user, I want independent current/future limits, explicit no-limit behavior and historical limits that never change through budget settings.

**Acceptance criteria:**

- [ ] **AC 36:** Past limit edits rejected; current/future edits target one month; legitimate refund/correction may still change historical spent, not its locked limit.
- [ ] **AC 37:** No-limit category counts spending, shows No limit, no numeric remaining/over-budget/percentage warning.
- [ ] **AC 38:** Expense beyond positive limit persists and syncs, showing negative remaining and Over budget.
- [ ] **AC 70:** Next month without configured limits shows No limit; no rollover/copy assumption; logs/crash reports contain no secrets, raw alerts or private financial payload.
- [ ] Limits are stored per budget, category and month in integer paise; the same 11 fixed categories apply to personal and shared budgets with independent limits.
- [ ] Editing a past month is rejected; current and future months can be edited and a change affects only that month.
- [ ] A month with no configured limit shows "No limit", no remaining value and no percentage alert; nothing rolls over or is copied from the previous month.
- [ ] A zero limit is valid: any positive spending shows "Over budget".
- [ ] Spending above a limit is recorded normally and shows a negative remaining.
- [ ] Locking limits does not lock spent totals: corrections and refunds still recalculate historical months.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-012 — Calculate applicable budgets and safe opening amounts

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-04  
**Summary:** Calculate applicable budgets and safe opening amounts  
**Labels:** expense-tracker, v0-2, us-012  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** BUD 01; BUD 03; BUD 04; HIST 01; MEM 04  
**Source acceptance:** AC 01; AC 03; AC 04; AC 11; AC 13; AC 30; AC 39; AC 47  
**Acceptance gates:** Gate 1 + Gate 2 (mixed); each AC line is tagged.  
**Blocked by:** US-011, US-008  

**Description:** As an owner or invitee, I want each logical expense routed once to eligible budgets while personal history stays private.

**Acceptance criteria:**

 - [ ] **AC 01 [Gate 2]:** Invitee's confirmed ₹1,000 Fashion purchase reduces personal/shared remaining from ₹4,000/₹5,000 to ₹3,000/₹4,000 with one logical ID.
 - [ ] **AC 03 [Gate 2]:** Owner spending affects only converted shared budget; invitee spending affects personal plus shared; owner has no extra personal budget.
 - [ ] **AC 04 [Gate 2]:** A pending invitation (Sheet shared, not accepted) changes neither budget; the invitee's Accept starts sharing from the recorded join time and converts the owner's budget once the owner's app processes it; completed removal returns it to personal without reducing retained spend. Pending removal is visibly incomplete.
 - [ ] **AC 11 [Gate 2]:** Neither participant sees invitee personal-only data; invitee sees owner opening aggregates but no pre-sharing details; completed revocation blocks future Drive writes.
 - [ ] **AC 13 [Gate 2]:** Owner ₹5,000 limit and ₹1,000 spent remain ₹4,000 available when sharing starts; ₹1,000 is a private-detail-free opening amount. Invitee's later ₹500 adds once to both budgets; earlier invitee transactions stay private.
 - [ ] **AC 30 [Gate 2]:** Rejoin uses new epoch and Sheet; gap expenses/details are not imported; owner retains old snapshot; safe current-month prior-spend aggregates preserve totals.
 - [ ] **AC 39 [Gate 1]:** Bank occurrence controls Asia/Kolkata month; missing date uses review-flagged received time; manual uses entered date; boundary-day ambiguity is not silently shared.
 - [ ] **AC 47 [Gate 2]:** Refund/correction to owner's pre-sharing purchase updates only safe opening aggregate while epoch active; no private transaction identifier/detail appears in Sheet.
- [ ] One logical transaction contributes at most once to each applicable budget: an owner's expense affects only the owner's budget, an invitee's affects their personal budget and the owner's shared budget, and the two are never added together.
- [ ] At the recorded join time the owner's limits and current-month net spending carry over; the opening amount is one aggregate per category with no transaction IDs, merchants, notes, accounts or dates.
- [ ] Corrections to pre-sharing purchases update only the aggregate component, and an opening component is never double-counted with its underlying purchase in the owner's continuing budget.
- [ ] Exact timestamps belong to [joinedAt, endedAt); a date-only entry on a join or leave day prompts "before or after" and is not shared while unanswered.
- [ ] A rejoin starts a new epoch and imports nothing from the gap.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-013 — Configure warnings and show private, fresh feedback

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-04  
**Summary:** Configure warnings and show private, fresh feedback  
**Labels:** expense-tracker, v0-2, us-013  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** FBK 01; FBK 02; FBK 03  
**Source acceptance:** AC 10; AC 21; AC 22; AC 37; AC 56; AC 57; AC 64  
**Blocked by:** US-011, US-012, US-002  

**Description:** As a user, I want explicit warning configuration, correct crossing notifications and readable balances with pending/stale status.

**Acceptance criteria:**

- [ ] **AC 10:** Expense crosses thresholds and then limit; one alert per eligible crossing and purchase remains saved above limit.
- [ ] **AC 21:** Default locked-device notification reveals no amount, merchant, category or balance; unlocked content and explicit privacy preference work.
- [ ] **AC 22:** 80%/100% crossings alert once; duplicate replay does not. Owner controls shared configuration, personal user theirs; configuration edits establish baseline without replay.
- [ ] **AC 37:** No-limit category counts spending, shows No limit, no numeric remaining/over-budget/percentage warning.
- [ ] **AC 56:** Invalid/duplicate threshold rejected; zero/no-limit percentages suppressed; sync jump coalesces alerts; devices may notify at different times without promising simultaneous delivery.
- [ ] **AC 57:** Last sync always visible; never-synced/failed or ≥15-minute-old shared state marked stale; known pending shown immediately; successful fetch/apply resets freshness.
- [ ] **AC 64:** Overview and transaction filters expose accurate month/spender/category/account/method/review views, opening labels, sync freshness and Indian currency formatting.
- [ ] After a durable commit the feedback shows amount, merchant if known, category and each applicable remaining balance (owner: shared only; invitee: personal and shared; no sharing: personal), in INR with Indian digit grouping.
- [ ] Thresholds default to 80% and 100%; one or two distinct integers from 1 to 100; the owner sets shared thresholds and each user their own; invalid or duplicate values are rejected; a change applies to current and future evaluations with a fresh baseline and no alert replay.
- [ ] A crossing alerts once; duplicates and retries do not repeat it; falling below (refund or correction) re-arms it; a sync that jumps over both thresholds gives one notification for the highest and records both.
- [ ] Zero-limit and no-limit categories never produce percentage alerts.
- [ ] The lock screen shows no amount, merchant, category or balance by default and the details preference works; a denied posting permission still gives in-app feedback.
- [ ] Shared status shows Pending, Failed and Access revoked, and Stale after a failure or 15 minutes without a successful sync; Never synced counts as stale.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-023 — Browse overview, transactions and audit detail

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-04  
**Summary:** Browse overview, transactions and audit detail  
**Labels:** expense-tracker, v0-2, us-023  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** UI 01; FBK 01; FBK 03; CAT 02  
**Source acceptance:** AC 03; AC 07; AC 09; AC 33; AC 52; AC 57; AC 64; AC 65  
**Blocked by:** US-010, US-012, US-013  

**Description:** As a user, I want functional month/budget/filter views that explain balances, opening amounts, pending review and one logical transaction's history.

**Acceptance criteria:**

- [ ] **AC 03:** Owner spending affects only converted shared budget; invitee spending affects personal plus shared; owner has no extra personal budget.
- [ ] **AC 07:** Unknown merchant/category remains pending and outside all totals. Confirming Fashion adds its full amount once to each eligible budget. This replaces v0.1's counted Uncategorized behavior.
- [ ] **AC 09:** Own category/date/amount correction removes old and applies new totals consistently; a category moves full amount without a split.
- [ ] **AC 33:** Ledger/report includes monthly limit/spent/remaining/over-budget, visible per-spender contributions and correctly labeled opening/prior-retained aggregates. Private source details absent.
- [ ] **AC 52:** Pending ₹X/count excludes confirmed duplicates, counts candidate cluster at most once, and separates unknown/non-INR counts; confirmed remaining is labeled incomplete while pending.
- [ ] **AC 57:** Last sync always visible; never-synced/failed or ≥15-minute-old shared state marked stale; known pending shown immediately; successful fetch/apply resets freshness.
- [ ] **AC 64:** Overview and transaction filters expose accurate month/spender/category/account/method/review views, opening labels, sync freshness and Indian currency formatting.
- [ ] **AC 65:** Core flows usable with TalkBack and 200% text; critical controls meet 48dp and stated contrast thresholds; status has non-color cue and accessible errors.
- [ ] The overview shows month, applicable budget, limit/spent/remaining per category, opening and retained labels, pending review and last sync.
- [ ] Transactions filter by month, spender, category, account, payment method and review status, and one logical payment shows its source aliases.
- [ ] Details show safe evidence, own-record corrections, refund links and the full audit history; no editing action appears for another user's records.
- [ ] Loading, empty, error, over-budget, no-limit and stale states each carry text or an icon, not colour alone.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

## EPIC-05 — Server-free sharing and lifecycle

**Issue type:** Epic  
**Project:** SCRUM  
**Parent:** None  
**Summary:** Server-free sharing and lifecycle  
**Labels:** expense-tracker, v0-2, epic-05  
**Delivery gate:** Gate 2 – Shared  
**Source requirements:** MEM 01; MEM 02; MEM 03; MEM 04; HIST 01; SEC 01; SEC 02  
**Source acceptance:** AC 04; AC 11; AC 15; AC 16; AC 24; AC 25; AC 30; AC 43; AC 44; AC 47; AC 48; AC 49; AC 69  
**Blocked by:** None  

**Description:** Invite by sharing the marked Sheet, discover it automatically, start sharing when the invitee accepts in the app, and end it by owner removal or invitee leave; disclose app-only enforcement and prevent accidental private-history exposure.

**Acceptance criteria:**

- [ ] **AC 04:** A pending invitation (Sheet shared, not accepted) changes neither budget; the invitee's Accept starts sharing from the recorded join time and converts the owner's budget once the owner's app processes it; completed removal returns it to personal without reducing retained spend. Pending removal is visibly incomplete.
- [ ] **AC 11:** Neither participant sees invitee personal-only data; invitee sees owner opening aggregates but no pre-sharing details; completed revocation blocks future Drive writes.
- [ ] **AC 15:** Owner can edit shared limit through app; invitee app/queued mutation is rejected without change. Invitee can edit personal limit. Onboarding explicitly discloses unsupported Google-editor bypass.
- [ ] **AC 16:** Active user corrects only their own record; all open eligible projections update once and audit entry persists. Other user's app edits are rejected, including budget-owner attempts.
- [ ] **AC 24:** Creator is owner; owner shares the Sheet with the invitee's Google email; the invitee's app discovers it automatically and shows a pending invitation; sharing starts only when the invitee taps Accept. No financial data is written before the join is processed.
- [ ] **AC 25:** Both users may view reports and have disclosed Google edit access for sync. Supported changes use app roles; manual cell edits are unsupported and detectable structural damage blocks writes. No read-only-source guarantee is asserted.
- [ ] **AC 30:** Rejoin uses new epoch and Sheet; gap expenses/details are not imported; owner retains old snapshot; safe current-month prior-spend aggregates preserve totals.
- [ ] **AC 43:** Owner removal completes only after confirmed revocation. Invitee leave pauses local sharing immediately but remote revocation remains pending owner processing. Next reconnect clears revoked cache, retaining personal copy.
- [ ] **AC 44:** After termination own personal correction/refund is allowed; closed shared totals remain frozen. No owner queue or retroactive write; rejected late sync is visible to author.
- [ ] **AC 47:** Refund/correction to owner's pre-sharing purchase updates only safe opening aggregate while epoch active; no private transaction identifier/detail appears in Sheet.
- [ ] **AC 48:** Discovery lists only marked Sheets owned by someone else and shared with the signed-in account; unmarked Sheets are ignored; a join from a non-invited account or a second member is rejected; a two-account test proves the scopes and permissions; a partial failure can retry safely.
- [ ] **AC 49:** Owner offline cannot complete leave; UI states dependency. During closing, app upload pauses; stale queued event after revoked permission is kept personal and visibly rejected for sharing.
- [ ] **AC 69:** A third account with access, an invitee trying to share their personal budget while a member, reciprocal invitations and a second `joined` row are rejected at Accept and when the owner's app processes the join.


**Evidence:** All child delivery criteria and the mapped integration scenarios have recorded passing evidence; open feasibility blockers cannot be waived as complete.

### SPIKE-01 — Prove Drive scopes and disclosed permission model

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-05  
**Summary:** Prove Drive scopes and disclosed permission model  
**Labels:** expense-tracker, v0-2, spike-01, spike  
**Delivery gate:** Gate 0 – Feasibility  
**Source requirements:** ARC 01; MEM 01; MEM 02; SEC 01  
**Source acceptance:** AC 15; AC 25; AC 48; AC 74  
**Blocked by:** None  

**Description:** Use empty synthetic Sheets with both pilot Google accounts to validate the chosen scopes (listing shared Sheets by name and owner, reading/writing the marked Sheet; fallback one-time file picker), sharing, removing access, disabling resharing and reading the share list.

**Acceptance criteria:**

- [ ] **AC 15:** Owner can edit shared limit through app; invitee app/queued mutation is rejected without change. Invitee can edit personal limit. Onboarding explicitly discloses unsupported Google-editor bypass.
- [ ] **AC 25:** Both users may view reports and have disclosed Google edit access for sync. Supported changes use app roles; manual cell edits are unsupported and detectable structural damage blocks writes. No read-only-source guarantee is asserted.
- [ ] **AC 48:** Discovery lists only marked Sheets owned by someone else and shared with the signed-in account; unmarked Sheets are ignored; a join from a non-invited account or a second member is rejected; a two-account test proves the scopes and permissions; a partial failure can retry safely.
- [ ] Record account roles and each API result; demonstrate that manual Google-editor bypass is possible and disclosed, rather than asserting app-only file permissions.
- [ ] Exercise revoked/expired OAuth authorization and seven-day Testing-mode implications; record the smallest working scope set or stop with a documented blocker.
- [ ] **AC 74 [Gate 0]:** Reinstall the same build as the same owner account, test automatic rediscovery of the owner's marked Sheet, and record whether recovery is possible before any blocked-state design is accepted.

**Evidence:** Attach a dated experiment record with both-account/device setup where relevant, observed API behavior, failures and explicit pass/block outcome. A written proposal alone does not pass this spike.

### SPIKE-02 — Prove Sheet-share invitation, discovery and removal

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-05  
**Summary:** Prove Sheet-share invitation, discovery and removal  
**Labels:** expense-tracker, v0-2, spike-02, spike  
**Delivery gate:** Gate 0 – Feasibility  
**Source requirements:** MEM 01; MEM 03  
**Source acceptance:** AC 24; AC 43; AC 48; AC 49; AC 69  
**Blocked by:** SPIKE-01  

**Description:** Prototype the flow: share a marked Sheet, automatic discovery by the invitee's app, Accept/Decline, the `joined` row processed by the owner's app, leave and removal; no financial records.

**Acceptance criteria:**

- [ ] **AC 24:** Creator is owner; owner shares the Sheet with the invitee's Google email; the invitee's app discovers it automatically and shows a pending invitation; sharing starts only when the invitee taps Accept. No financial data is written before the join is processed.
- [ ] **AC 43:** Owner removal completes only after confirmed revocation. Invitee leave pauses local sharing immediately but remote revocation remains pending owner processing. Next reconnect clears revoked cache, retaining personal copy.
- [ ] **AC 48:** Discovery lists only marked Sheets owned by someone else and shared with the signed-in account; unmarked Sheets are ignored; a join from a non-invited account or a second member is rejected; a two-account test proves the scopes and permissions; a partial failure can retry safely.
- [ ] **AC 49:** Owner offline cannot complete leave; UI states dependency. During closing, app upload pauses; stale queued event after revoked permission is kept personal and visibly rejected for sharing.
- [ ] **AC 69:** A third account with access, an invitee trying to share their personal budget while a member, reciprocal invitations and a second `joined` row are rejected at Accept and when the owner's app processes the join.
- [ ] A join from a non-invited account, a second member and a partial failure (access removed but `ended` not written) cannot create or keep a membership.
- [ ] Record expected behavior while the owner's app is offline; no server is introduced and the app sends no email itself.

**Evidence:** Attach a dated experiment record with both-account/device setup where relevant, observed API behavior, failures and explicit pass/block outcome. A written proposal alone does not pass this spike.

### US-014 — Invite by sharing the Sheet and accept in the app

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-05  
**Summary:** Invite by sharing the Sheet and accept in the app  
**Labels:** expense-tracker, v0-2, us-014  
**Delivery gate:** Gate 2 – Shared  
**Source requirements:** MEM 01; BUD 01; ARC 01  
**Source acceptance:** AC 24; AC 48; AC 69; AC 73  
**Blocked by:** SPIKE-01, SPIKE-02, US-001, US-017  

**Description:** As a budget owner and recipient, we want the invitation to be a normal Google share that the recipient's app discovers, so sharing begins only when the recipient taps Accept.

**Acceptance criteria:**

- [ ] **AC 24:** Creator is owner; owner shares the Sheet with the invitee's Google email; the invitee's app discovers it automatically and shows a pending invitation; sharing starts only when the invitee taps Accept. No financial data is written before the join is processed.
- [ ] **AC 48:** Discovery lists only marked Sheets owned by someone else and shared with the signed-in account; unmarked Sheets are ignored; a join from a non-invited account or a second member is rejected; a two-account test proves the scopes and permissions; a partial failure can retry safely.
- [ ] **AC 69:** A third account with access, an invitee trying to share their personal budget while a member, reciprocal invitations and a second `joined` row are rejected at Accept and when the owner's app processes the join.
- [ ] **AC 73 [Gate 2]:** Before acceptance, each member is told that active-epoch merchants, amounts, dates, categories/intents and spender attribution are visible to the other member and that no per-transaction exclusion exists in v0.2.
- [ ] The owner enters a Google email; the app creates the Sheet with the fixed name prefix and a `_meta` tab (budget ID, schema version), shares it with that account as editor with link sharing off and resharing disabled where supported, and writes no financial data.
- [ ] The recipient's app lists only marked Sheets owned by someone else and shared with the signed-in account; unmarked Sheets are never read.
- [ ] Nothing changes until the recipient taps Accept after the visibility disclosures; declining or ignoring writes nothing and is remembered locally.
- [ ] A pending invitation can be cancelled by removing the Google share, leaving both budgets unchanged.
- [ ] Eligibility (one relationship, not already a member, no reciprocal invitation) is checked when sending, at Accept and when the owner's app processes the join.
- [ ] The Google scopes used are the smallest set SPIKE-01 shows working, with the file picker as the fallback.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-101 — Implement shared-budget discovery and Accept/Decline

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-014  
**Summary:** Implement shared-budget discovery and Accept/Decline  
**Labels:** expense-tracker, v0-2, sub-101  
**Delivery gate:** Inherited from parent gate  
**Source requirements:** MEM 01  
**Source acceptance:** AC 24; AC 48  
**Blocked by:** None  

**Description:** List marked Sheets owned by someone else and shared with the signed-in account; show them as pending invitations; Accept writes the `joined` row, Decline is remembered locally; no financial data before the join is processed.

**Acceptance criteria:**

- [ ] **AC 24:** Creator is owner; owner shares the Sheet with the invitee's Google email; the invitee's app discovers it automatically and shows a pending invitation; sharing starts only when the invitee taps Accept. No financial data is written before the join is processed.
- [ ] **AC 48:** Discovery lists only marked Sheets owned by someone else and shared with the signed-in account; unmarked Sheets are ignored; a join from a non-invited account or a second member is rejected; a two-account test proves the scopes and permissions; a partial failure can retry safely.
- [ ] A Sheet is listed only when its name prefix and `_meta` tab both match and the signed-in account does not own it.
- [ ] The pending list shows the owner's email; accepting is blocked while another relationship is active.
- [ ] Accept writes exactly one `joined` row with the account ID and time; tapping again writes no second row.
- [ ] A declined Sheet is remembered locally and not shown as pending.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-102 — Process the join and handle partial failure

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-014  
**Summary:** Process the join and handle partial failure  
**Labels:** expense-tracker, v0-2, sub-102  
**Delivery gate:** Inherited from parent gate  
**Source requirements:** MEM 01; BUD 01  
**Source acceptance:** AC 24; AC 48; AC 69  
**Blocked by:** None  

**Description:** The owner's app checks the `joined` row names the invited account and no other member is active, writes opening amounts as of the join time, and keeps a partial failure pending and retryable.

**Acceptance criteria:**

- [ ] **AC 24:** Creator is owner; owner shares the Sheet with the invitee's Google email; the invitee's app discovers it automatically and shows a pending invitation; sharing starts only when the invitee taps Accept. No financial data is written before the join is processed.
- [ ] **AC 48:** Discovery lists only marked Sheets owned by someone else and shared with the signed-in account; unmarked Sheets are ignored; a join from a non-invited account or a second member is rejected; a two-account test proves the scopes and permissions; a partial failure can retry safely.
- [ ] **AC 69:** A third account with access, an invitee trying to share their personal budget while a member, reciprocal invitations and a second `joined` row are rejected at Accept and when the owner's app processes the join.
- [ ] The join is accepted only if the `joined` row names the invited account and no other member is active; otherwise shared writes stop and a recovery issue is shown.
- [ ] Opening amounts are computed from the current month before the join time and written once; a retry after failure does not duplicate them.
- [ ] A partial failure leaves the state "Joined; waiting for the owner's app to sync" and can be retried.
- [ ] The invitee's expenses from the join time are queued, uploaded and counted from that time.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-015 — Complete leave/removal and rejoin with a fresh Sheet

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-05  
**Summary:** Complete leave/removal and rejoin with a fresh Sheet  
**Labels:** expense-tracker, v0-2, us-015  
**Delivery gate:** Gate 2 – Shared  
**Source requirements:** MEM 03; MEM 04; HIST 01; SEC 02  
**Source acceptance:** AC 04; AC 30; AC 43; AC 44; AC 49  
**Blocked by:** US-014, US-018  

**Description:** As a participant, I want clear pending/completed termination, retained totals and a new epoch on rejoin without leaking gap details.

**Acceptance criteria:**

- [ ] **AC 04:** A pending invitation (Sheet shared, not accepted) changes neither budget; the invitee's Accept starts sharing from the recorded join time and converts the owner's budget once the owner's app processes it; completed removal returns it to personal without reducing retained spend. Pending removal is visibly incomplete.
- [ ] **AC 30:** Rejoin uses new epoch and Sheet; gap expenses/details are not imported; owner retains old snapshot; safe current-month prior-spend aggregates preserve totals.
- [ ] **AC 43:** Owner removal completes only after confirmed revocation. Invitee leave pauses local sharing immediately but remote revocation remains pending owner processing. Next reconnect clears revoked cache, retaining personal copy.
- [ ] **AC 44:** After termination own personal correction/refund is allowed; closed shared totals remain frozen. No owner queue or retroactive write; rejected late sync is visible to author.
- [ ] **AC 49:** Owner offline cannot complete leave; UI states dependency. During closing, app upload pauses; stale queued event after revoked permission is kept personal and visibly rejected for sharing.
- [ ] Owner removal writes `removing`, pauses shared writes, removes the invitee's Google access, confirms the permission state, stores the final ledger snapshot, then writes `ended`; a failure stays "Removal pending" and never claims revocation.
- [ ] Invitee leave pauses their shared queue at once, routes new purchases personal-only and writes a `left` row; until the owner's app removes access the UI says "Left; the owner's app must remove access".
- [ ] Shared-period totals are retained and nothing is subtracted; the owner's budget returns to personal with the same ID.
- [ ] A rejoin needs a fresh accepted invitation, creates a new epoch and a new Sheet (the old one is archived owner-only) and imports nothing from the gap.
- [ ] With the owner's app offline a leave cannot complete, and the UI says so.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-103 — Implement owner-online revocation state machine

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-015  
**Summary:** Implement owner-online revocation state machine  
**Labels:** expense-tracker, v0-2, sub-103  
**Delivery gate:** Inherited from parent gate  
**Source requirements:** MEM 03; SEC 02  
**Source acceptance:** AC 43; AC 49  
**Blocked by:** None  

**Description:** Separate leave request, attribution cutoff, closing and confirmed remote revocation; preserve queued personal changes.

**Acceptance criteria:**

- [ ] **AC 43:** Owner removal completes only after confirmed revocation. Invitee leave pauses local sharing immediately but remote revocation remains pending owner processing. Next reconnect clears revoked cache, retaining personal copy.
- [ ] **AC 49:** Owner offline cannot complete leave; UI states dependency. During closing, app upload pauses; stale queued event after revoked permission is kept personal and visibly rejected for sharing.
- [ ] Leave time, effective attribution cutoff and completed access-removal time are recorded as three separate values.
- [ ] States move invited, active, removing, ended (with leave-requested in between where relevant); a failed step never skips ahead.
- [ ] A queued event that reaches the Sheet after access was removed is rejected and shown as "Saved personally; not added to closed shared period".
- [ ] Personal records are untouched by any state change.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-104 — Archive ended epoch and allocate new Sheet on rejoin

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-015  
**Summary:** Archive ended epoch and allocate new Sheet on rejoin  
**Labels:** expense-tracker, v0-2, sub-104  
**Delivery gate:** Inherited from parent gate  
**Source requirements:** MEM 04; HIST 01  
**Source acceptance:** AC 30; AC 44  
**Blocked by:** None  

**Description:** Freeze old snapshot and never regrant an old Sheet or write gap-period personal data to it.

**Acceptance criteria:**

- [ ] **AC 30:** Rejoin uses new epoch and Sheet; gap expenses/details are not imported; owner retains old snapshot; safe current-month prior-spend aggregates preserve totals.
- [ ] **AC 44:** After termination own personal correction/refund is allowed; closed shared totals remain frozen. No owner queue or retroactive write; rejected late sync is visible to author.
- [ ] On `ended`, the Sheet is archived to owner-only access and automatic writes of personal-period data to it stop.
- [ ] A re-invite creates a new Sheet and a new `_meta` tab, and access to the old Sheet is never granted again.
- [ ] Opening totals for the new epoch may include retained earlier contributions as "Prior retained spending", with no transaction details exposed.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-016 — Enforce supported app roles and disclose Google powers

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-05  
**Summary:** Enforce supported app roles and disclose Google powers  
**Labels:** expense-tracker, v0-2, us-016  
**Delivery gate:** Gate 2 – Shared  
**Source requirements:** MEM 02; SEC 01; BUD 01  
**Source acceptance:** AC 11; AC 15; AC 16; AC 25; AC 69; AC 73  
**Blocked by:** SPIKE-01, US-001, TASK-101  

**Description:** As a participant, I want app actions to respect ownership and active membership while accurately explaining what Google editor access can bypass.

**Acceptance criteria:**

- [ ] **AC 11:** Neither participant sees invitee personal-only data; invitee sees owner opening aggregates but no pre-sharing details; completed revocation blocks future Drive writes.
- [ ] **AC 15:** Owner can edit shared limit through app; invitee app/queued mutation is rejected without change. Invitee can edit personal limit. Onboarding explicitly discloses unsupported Google-editor bypass.
- [ ] **AC 16:** Active user corrects only their own record; all open eligible projections update once and audit entry persists. Other user's app edits are rejected, including budget-owner attempts.
- [ ] **AC 25:** Both users may view reports and have disclosed Google edit access for sync. Supported changes use app roles; manual cell edits are unsupported and detectable structural damage blocks writes. No read-only-source guarantee is asserted.
- [ ] **AC 69:** A third account with access, an invitee trying to share their personal budget while a member, reciprocal invitations and a second `joined` row are rejected at Accept and when the owner's app processes the join.
- [ ] **AC 73 [Gate 2]:** Before acceptance, each member is told that active-epoch merchants, amounts, dates, categories/intents and spender attribution are visible to the other member and that no per-transaction exclusion exists in v0.2.
- [ ] Only the owner can change shared limits and thresholds; an invitee's attempt, including a queued one, is rejected with no change, while the invitee can edit personal limits.
- [ ] Each user can correct or delete only their own transactions through the app, with no owner override, including via sync import.
- [ ] Before accepting, each person is told that active-epoch purchase details are visible to the other, that there is no per-purchase hide, and that an authorized Google editor can read or change the Sheet outside the app.
- [ ] The app never describes Sheet tabs as read-only.
- [ ] An extra account with access, or a second `joined` row, stops shared writes with a recovery message.
- [ ] Enforcement claims match what SPIKE-01 demonstrated.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

## EPIC-06 — Shared synchronization and data recovery

**Issue type:** Epic  
**Project:** SCRUM  
**Parent:** None  
**Summary:** Shared synchronization and data recovery  
**Labels:** expense-tracker, v0-2, epic-06  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** DAT 02; DAT 03; SYN 01; SYN 02; SYN 03; REC 01; REC 02  
**Source acceptance:** AC 08; AC 17; AC 19; AC 20; AC 23; AC 33; AC 45; AC 46; AC 55; AC 58; AC 59; AC 60; AC 67; AC 68  
**Blocked by:** None  

**Description:** Make append retries logically idempotent, preserve conflicts, recover schema/report damage and support portable password-protected exports and local wipe.

**Acceptance criteria:**

- [ ] **AC 08:** Offline save survives restart; pending/stale state is visible; partial shared failure preserves local data; retry folds once.
- [ ] **AC 17:** Invitee saves eligible expense and uploads stable event; owner sees it after sync without opening Sheets. Personal-only records/limits are absent from remote data.
- [ ] **AC 19:** Export/restore includes normalized records, stable IDs, tombstones and history; excludes raw/excerpt content, secrets and device keys; duplicate restore does not duplicate totals.
- [ ] **AC 20:** Wrong/missing password rejects restore with no local mutation; correct password still requires matching Google account.
- [ ] **AC 23:** Offline shared change shows pending, survives restart and synchronizes once logically after eligible trigger; other device updates on its next sync.
- [ ] **AC 33:** Ledger/report includes monthly limit/spent/remaining/over-budget, visible per-spender contributions and correctly labeled opening/prior-retained aggregates. Private source details absent.
- [ ] **AC 45:** Restore requires both correct password and live same-account sign-in; restored expired membership cannot grant access.
- [ ] **AC 46:** Audit history persists for dataset lifetime including tombstones/exports; explicit full local wipe removes local history/key/queue, not remote files or external exports.
- [ ] **AC 55:** Confirmed offline wipe cancels jobs and deletes local data/key/session/excerpts; remote Sheet/export unchanged; active membership caveat and export option shown.
- [ ] **AC 58:** Retry after lost append response may duplicate physical rows but identical operation ID affects totals once; conflicting same-ID payload halts affected processing.
- [ ] **AC 59:** Two successors of same base version surface conflict without silent overwrite; resolution event authored by transaction owner converges both replicas.
- [ ] **AC 60:** Unknown schema/missing canonical data blocks shared writes but preserves outbox; derived report can rebuild from valid events; missing sources never silently fabricated.
- [ ] **AC 67:** After 7 days of unexported changed data reminder appears without blocking use; recent-export date visible; loss risk explained; platform backup excludes key-dependent DB/excerpts/tokens.
- [ ] **AC 68:** Invitee sync can update app ledger before owner report rebuild; report timestamp/pending state makes owner-online dependency explicit.


**Evidence:** All child delivery criteria and the mapped integration scenarios have recorded passing evidence; open feasibility blockers cannot be waived as complete.

### TASK-107 — Implement Sheet schema validation and non-destructive recovery

**Issue type:** Task  
**Project:** SCRUM  
**Parent:** EPIC-06  
**Summary:** Implement Sheet schema validation and non-destructive recovery  
**Labels:** expense-tracker, v0-2, task-107  
**Delivery gate:** Gate 2 – Shared  
**Source requirements:** SYN 02; SYN 03  
**Source acceptance:** AC 58; AC 59; AC 60; AC 68  
**Blocked by:** US-017, US-018  

**Description:** Detect incompatible schemas, missing canonical events and corrupted version chains before accepting shared writes; rebuild only derivable reports.

**Acceptance criteria:**

- [ ] **AC 58:** Retry after lost append response may duplicate physical rows but identical operation ID affects totals once; conflicting same-ID payload halts affected processing.
- [ ] **AC 59:** Two successors of same base version surface conflict without silent overwrite; resolution event authored by transaction owner converges both replicas.
- [ ] **AC 60:** Unknown schema/missing canonical data blocks shared writes but preserves outbox; derived report can rebuild from valid events; missing sources never silently fabricated.
- [ ] **AC 68:** Invitee sync can update app ledger before owner report rebuild; report timestamp/pending state makes owner-online dependency explicit.
- [ ] Preserve outbox and original Sheet before migration; require explicit acknowledgement if canonical data cannot be recovered.
- [ ] Owner-only report reducer shares the same deterministic event logic as app totals; timestamps expose reporting lag.

**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-017 — Create epoch files and privacy-safe shared reports

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-06  
**Summary:** Create epoch files and privacy-safe shared reports  
**Labels:** expense-tracker, v0-2, us-017  
**Delivery gate:** Gate 2 – Shared  
**Source requirements:** DAT 02; DAT 03; HIST 01; SYN 03  
**Source acceptance:** AC 11; AC 13; AC 25; AC 30; AC 33; AC 47; AC 68  
**Blocked by:** SPIKE-01, US-016, US-012  

**Description:** As an owner, I want one active shared Sheet with safe aggregates and reports, a `_meta` tab and membership rows (`joined`, `left`, `removing`, `ended`).

**Acceptance criteria:**

- [ ] **AC 11:** Neither participant sees invitee personal-only data; invitee sees owner opening aggregates but no pre-sharing details; completed revocation blocks future Drive writes.
- [ ] **AC 13:** Owner ₹5,000 limit and ₹1,000 spent remain ₹4,000 available when sharing starts; ₹1,000 is a private-detail-free opening amount. Invitee's later ₹500 adds once to both budgets; earlier invitee transactions stay private.
- [ ] **AC 25:** Both users may view reports and have disclosed Google edit access for sync. Supported changes use app roles; manual cell edits are unsupported and detectable structural damage blocks writes. No read-only-source guarantee is asserted.
- [ ] **AC 30:** Rejoin uses new epoch and Sheet; gap expenses/details are not imported; owner retains old snapshot; safe current-month prior-spend aggregates preserve totals.
- [ ] **AC 33:** Ledger/report includes monthly limit/spent/remaining/over-budget, visible per-spender contributions and correctly labeled opening/prior-retained aggregates. Private source details absent.
- [ ] **AC 47:** Refund/correction to owner's pre-sharing purchase updates only safe opening aggregate while epoch active; no private transaction identifier/detail appears in Sheet.
- [ ] **AC 68:** Invitee sync can update app ledger before owner report rebuild; report timestamp/pending state makes owner-online dependency explicit.
- [ ] Only shared projections, safe opening aggregates, source operations and reports are written; personal budgets, pre-membership history, remembered rules, excerpts and unrelated messages never are.
- [ ] Reports show monthly limit, spent, remaining and over-budget status, current-epoch per-spender contributions, and opening and prior-retained aggregates labelled as such.
- [ ] The owner's app rebuilds the report tabs from the deduplicated log; the report shows its generation time and a pending state until it runs.
- [ ] The Sheet carries its schema version and required tab and column definitions.
- [ ] The invitee never sees the owner's earlier months in the app or the Sheet.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-018 — Synchronize stable events and expose conflicts

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-06  
**Summary:** Synchronize stable events and expose conflicts  
**Labels:** expense-tracker, v0-2, us-018  
**Delivery gate:** Gate 2 – Shared  
**Source requirements:** DAT 01; SYN 01; SYN 02; MEM 04  
**Source acceptance:** AC 08; AC 17; AC 23; AC 49; AC 58; AC 59; AC 68  
**Blocked by:** US-017, TASK-101, US-014  

**Description:** As a user, I want durable offline changes synchronized without logical duplicate spending or silently overwritten conflicts.

**Acceptance criteria:**

- [ ] **AC 08:** Offline save survives restart; pending/stale state is visible; partial shared failure preserves local data; retry folds once.
- [ ] **AC 17:** Invitee saves eligible expense and uploads stable event; owner sees it after sync without opening Sheets. Personal-only records/limits are absent from remote data.
- [ ] **AC 23:** Offline shared change shows pending, survives restart and synchronizes once logically after eligible trigger; other device updates on its next sync.
- [ ] **AC 49:** Owner offline cannot complete leave; UI states dependency. During closing, app upload pauses; stale queued event after revoked permission is kept personal and visibly rejected for sharing.
- [ ] **AC 58:** Retry after lost append response may duplicate physical rows but identical operation ID affects totals once; conflicting same-ID payload halts affected processing.
- [ ] **AC 59:** Two successors of same base version surface conflict without silent overwrite; resolution event authored by transaction owner converges both replicas.
- [ ] **AC 68:** Invitee sync can update app ledger before owner report rebuild; report timestamp/pending state makes owner-online dependency explicit.
- [ ] Sync runs on app open, manual Refresh and connectivity return when Android allows; membership and access are refreshed before uploads and a failure leaves changes queued.
- [ ] Each operation has a stable ID created before the network call and reused on retry; readers ignore duplicate physical rows by ID.
- [ ] The same ID with a different payload is treated as corruption and halts the affected processing.
- [ ] Two successors of the same base version produce a conflict that preserves both, holds the last agreed shared projection and is resolved by the transaction owner.
- [ ] Local saved status and shared sync status are shown separately.
- [ ] Backoff is bounded and reauthorization is prompted without losing the queue.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-105 — Fold duplicate append operations deterministically

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-018  
**Summary:** Fold duplicate append operations deterministically  
**Labels:** expense-tracker, v0-2, sub-105  
**Delivery gate:** Inherited from parent gate  
**Source requirements:** SYN 02  
**Source acceptance:** AC 02; AC 58  
**Blocked by:** None  

**Description:** Reuse stable operation ID after uncertain response and exclude duplicate physical rows from every reducer.

**Acceptance criteria:**

- [ ] **AC 02:** SMS, notification and sync replay of a strongly matched purchase count once; distinct equal-value references remain separate.
- [ ] **AC 58:** Retry after lost append response may duplicate physical rows but identical operation ID affects totals once; conflicting same-ID payload halts affected processing.
- [ ] After a lost append response and a retry, totals count the operation once even if two rows exist.
- [ ] The local and report reducers produce identical totals from the same log.
- [ ] Operation timestamps are treated as client claims, not proof.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-106 — Implement version-branch conflict resolution

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-018  
**Summary:** Implement version-branch conflict resolution  
**Labels:** expense-tracker, v0-2, sub-106  
**Delivery gate:** Inherited from parent gate  
**Source requirements:** SYN 02  
**Source acceptance:** AC 59  
**Blocked by:** None  

**Description:** Preserve conflicting successors, hold last agreed shared projection and accept transaction owner's explicit resolution event.

**Acceptance criteria:**

- [ ] **AC 59:** Two successors of same base version surface conflict without silent overwrite; resolution event authored by transaction owner converges both replicas.
- [ ] Divergent successors never overwrite each other silently; both are kept.
- [ ] The resolution event is authored by the transaction owner and makes both devices converge.
- [ ] Another user cannot resolve the conflict.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-019 — Export, restore and remind about portable backups

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-06  
**Summary:** Export, restore and remind about portable backups  
**Labels:** expense-tracker, v0-2, us-019  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** REC 01; SEC 02; DAT 01  
**Source acceptance:** AC 19; AC 20; AC 42; AC 45; AC 46; AC 61; AC 67; AC 74  
**Blocked by:** SPIKE-05, TASK-101, US-001  

**Description:** As a user, I want an account-bound password-protected export that recovers my history on a replacement phone without reviving stale shared access.

**Acceptance criteria:**

- [ ] **AC 19:** Export/restore includes normalized records, stable IDs, tombstones and history; excludes raw/excerpt content, secrets and device keys; duplicate restore does not duplicate totals.
- [ ] **AC 20:** Wrong/missing password rejects restore with no local mutation; correct password still requires matching Google account.
- [ ] **AC 42:** DB contents are unreadable without protected key; offline restart works; portable export restores with new device key, not by copying the device-bound key.
- [ ] **AC 45:** Restore requires both correct password and live same-account sign-in; restored expired membership cannot grant access.
- [ ] **AC 46:** Audit history persists for dataset lifetime including tombstones/exports; explicit full local wipe removes local history/key/queue, not remote files or external exports.
- [ ] **AC 61:** Replacement device restores portable data and reauthorizes membership; old-device retirement is explained; conflicting active device detected by supported flow blocks shared writes.
- [ ] **AC 67:** After 7 days of unexported changed data reminder appears without blocking use; recent-export date visible; loss risk explained; platform backup excludes key-dependent DB/excerpts/tokens.
 - [ ] **AC 74 [Gate 2]:** Setup and recovery first use the tested same-owner reinstall and rediscovery path; only if the marked Sheet cannot be found or read does the app block rebuild and revocation, preserve invitee data, and offer a manual closure/export path.
- [ ] The export is password-protected in a portable, versioned format, and a new device key is derived on restore.
- [ ] It includes stable IDs, normalized transactions, budgets, categories, memberships, source aliases, outbox state, tombstones and change history, and excludes excerpts, raw alerts, OAuth tokens, bank secrets and device-bound keys.
- [ ] Restore needs the correct password and a live sign-in to the original Google account; any mismatch or corrupt file changes nothing.
- [ ] Restore merges by stable ID and version, previews conflicts, never replays acknowledged operations, revalidates membership before restoring shared access and cannot reopen an ended epoch.
- [ ] The app shows the last export date and reminds after 7 days of unexported changes and before replacement or wipe (dismissible, never blocking), and states that losing the phone without an export can lose personal data.
- [ ] Android cloud backup excludes the database, tokens and excerpts.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-107 — Serialize portable encrypted export with exclusions

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-019  
**Summary:** Serialize portable encrypted export with exclusions  
**Labels:** expense-tracker, v0-2, sub-107  
**Delivery gate:** Inherited from parent gate  
**Source requirements:** REC 01  
**Source acceptance:** AC 19; AC 20; AC 67  
**Blocked by:** None  

**Description:** Include stable normalized domain/history data; exclude excerpts, tokens and device keys; document portable encryption format.

**Acceptance criteria:**

- [ ] **AC 19:** Export/restore includes normalized records, stable IDs, tombstones and history; excludes raw/excerpt content, secrets and device keys; duplicate restore does not duplicate totals.
- [ ] **AC 20:** Wrong/missing password rejects restore with no local mutation; correct password still requires matching Google account.
- [ ] **AC 67:** After 7 days of unexported changed data reminder appears without blocking use; recent-export date visible; loss risk explained; platform backup excludes key-dependent DB/excerpts/tokens.
- [ ] The format and encryption parameters are documented well enough for another build to read the file.
- [ ] A round trip (export, then restore) yields identical totals.
- [ ] A scan of a sample export finds no excerpts, tokens, raw alerts or device key.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-108 — Restore transactionally with identity and membership checks

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-019  
**Summary:** Restore transactionally with identity and membership checks  
**Labels:** expense-tracker, v0-2, sub-108  
**Delivery gate:** Inherited from parent gate  
**Source requirements:** REC 01; SEC 02  
**Source acceptance:** AC 45; AC 61  
**Blocked by:** None  

**Description:** Validate password/account/schema before mutation; create new device key, merge IDs and reauthorize before shared replay.

**Acceptance criteria:**

- [ ] **AC 45:** Restore requires both correct password and live same-account sign-in; restored expired membership cannot grant access.
- [ ] **AC 61:** Replacement device restores portable data and reauthorizes membership; old-device retirement is explained; conflicting active device detected by supported flow blocks shared writes.
- [ ] Checks run in order (format, password, account, schema) and only then mutate; a failure at any step leaves data unchanged.
- [ ] Restoring the same file twice creates no duplicates.
- [ ] Restored memberships grant no access until revalidated.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-024 — Wipe local data with explicit scope

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-06  
**Summary:** Wipe local data with explicit scope  
**Labels:** expense-tracker, v0-2, us-024  
**Delivery gate:** Gate 1 + Gate 2 (mixed)  
**Source requirements:** REC 02  
**Source acceptance:** AC 46; AC 55  
**Blocked by:** US-019, TASK-101  

**Description:** As a user, I want to remove my local financial data/key/history after clear confirmation, without accidentally deleting remote shared files or backups.

**Acceptance criteria:**

- [ ] **AC 46:** Audit history persists for dataset lifetime including tombstones/exports; explicit full local wipe removes local history/key/queue, not remote files or external exports.
- [ ] **AC 55:** Confirmed offline wipe cancels jobs and deletes local data/key/session/excerpts; remote Sheet/export unchanged; active membership caveat and export option shown.
- [ ] Wipe requires explicit confirmation that local data and history will be removed and offers an export first without forcing it.
- [ ] It cancels workers and notifications, then deletes the database, pending queue, excerpts, local sign-in and session, and the protected key.
- [ ] It works offline and does not delete exported files, Sheets or the other person's data.
- [ ] If the user is in an active shared budget, the app explains and offers leave or remove first, and never claims remote revocation.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

## EPIC-07 — Pilot evidence and release gates

**Issue type:** Epic  
**Project:** SCRUM  
**Parent:** None  
**Summary:** Pilot evidence and release gates  
**Labels:** expense-tracker, v0-2, epic-07  
**Delivery gate:** Gate 0 – Feasibility + Gate 3 – Pilot  
**Source requirements:** ARC 01; NFR 01; NFR 02  
**Source acceptance:** AC 12; AC 41; AC 42; AC 65; AC 66; AC 70  
**Blocked by:** None  

**Description:** Complete feasibility spikes before dependent work and validate every required criterion on both pilot devices.

**Acceptance criteria:**

- [ ] **AC 12:** Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable.
- [ ] **AC 41:** All 100 warm and 100 cold delivered-alert runs per pilot device satisfy ≤2,000 ms with seeded 10,000 records; report p50/p95/max and separate delivery/network metrics.
- [ ] **AC 42:** DB contents are unreadable without protected key; offline restart works; portable export restores with new device key, not by copying the device-bound key.
- [ ] **AC 65:** Core flows usable with TalkBack and 200% text; critical controls meet 48dp and stated contrast thresholds; status has non-color cue and accessible errors.
- [ ] **AC 66:** Actual private APK install on each pilot phone documents restricted-settings behavior, posting/listener/SMS consent and recovery after battery restrictions.
- [ ] **AC 70:** Next month without configured limits shows No limit; no rollover/copy assumption; logs/crash reports contain no secrets, raw alerts or private financial payload.


**Evidence:** All child delivery criteria and the mapped integration scenarios have recorded passing evidence; open feasibility blockers cannot be waived as complete.

### US-020 — Clean revoked cache and preserve personal data

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-07  
**Summary:** Clean revoked cache and preserve personal data  
**Labels:** expense-tracker, v0-2, us-020  
**Delivery gate:** Gate 2 – Shared  
**Source requirements:** SEC 02; MEM 03; FBK 03  
**Source acceptance:** AC 21; AC 43; AC 49  
**Blocked by:** US-015, US-013  

**Description:** As a departing member, I want inaccessible shared cache removed when revocation is observed, without losing my own records or being told offline copies were erased.

**Acceptance criteria:**

- [ ] **AC 21:** Default locked-device notification reveals no amount, merchant, category or balance; unlocked content and explicit privacy preference work.
- [ ] **AC 43:** Owner removal completes only after confirmed revocation. Invitee leave pauses local sharing immediately but remote revocation remains pending owner processing. Next reconnect clears revoked cache, retaining personal copy.
- [ ] **AC 49:** Owner offline cannot complete leave; UI states dependency. During closing, app upload pauses; stale queued event after revoked permission is kept personal and visibly rejected for sharing.
- [ ] On the next connection after revocation, inaccessible shared records and cached projections are removed while personal records remain.
- [ ] While offline, the app says cached copies may remain until reconnection and does not claim they were erased.
- [ ] A cached membership cannot authorize new shared writes after revocation.
- [ ] Locked-screen notifications still reveal no details.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

### US-021 — Complete measurable pilot and accessibility acceptance

**Issue type:** Story  
**Project:** SCRUM  
**Parent:** EPIC-07  
**Summary:** Complete measurable pilot and accessibility acceptance  
**Labels:** expense-tracker, v0-2, us-021  
**Delivery gate:** Gate 3 – Pilot  
**Source requirements:** NFR 01; NFR 02; ARC 01  
**Source acceptance:** AC 12; AC 41; AC 42; AC 65; AC 66; AC 70; AC 75; AC 76  
**Blocked by:** SPIKE-01, SPIKE-02, SPIKE-03, SPIKE-04, SPIKE-05, TASK-101, TASK-102, TASK-103, TASK-104, TASK-105, TASK-106, TASK-107, TASK-108, TASK-109, US-001, US-002, US-003, US-004, US-005, US-006, US-007, US-008, US-009, US-010, US-011, US-012, US-013, US-014, US-015, US-016, US-017, US-018, US-019, US-020, US-022, US-023, US-024, US-025  

**Description:** As the pilot team, we want recorded evidence against Gate 3, not unsupported statements of coverage, security or response time. This Story is the final end-to-end sign-off and remains blocked by every Gate 0, Gate 1 and Gate 2 delivery issue listed above.

**Acceptance criteria:**

- [ ] **AC 12:** Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable.
- [ ] **AC 41:** All 100 warm and 100 cold delivered-alert runs per pilot device satisfy ≤2,000 ms with seeded 10,000 records; report p50/p95/max and separate delivery/network metrics.
- [ ] **AC 42:** DB contents are unreadable without protected key; offline restart works; portable export restores with new device key, not by copying the device-bound key.
- [ ] **AC 65:** Core flows usable with TalkBack and 200% text; critical controls meet 48dp and stated contrast thresholds; status has non-color cue and accessible errors.
- [ ] **AC 66:** Actual private APK install on each pilot phone documents restricted-settings behavior, posting/listener/SMS consent and recovery after battery restrictions.
- [ ] **AC 70:** Next month without configured limits shows No limit; no rollover/copy assumption; logs/crash reports contain no secrets, raw alerts or private financial payload.
- [ ] **AC 75 [Gate 3]:** Every >2,000 ms run is retained with cause classification; one controlled rerun may supplement it but cannot hide the original outlier.
- [ ] **AC 76 [Gate 3]:** Gate 3 reports the named statement/alert-log ground truth and approved fixture/live sample sizes; provisional thresholds are not treated as approved.
- [ ] An evidence pack records, for each Section 9 requirement, the device, OS build, app build, conditions and result with a date.
- [ ] Every claimed bank, channel and source combination has fixtures per the Gate 3 plan (the targets in D-12, once approved); unsupported combinations are listed as unsupported, never as passed.
- [ ] Any open defect involving data loss, private-history exposure, incorrect spend or failed revocation blocks sign-off.
- [ ] The accepted limitation that a permitted Google editor can bypass app rules is recorded.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-111 — Measure cold/warm timing and callback reliability

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-021  
**Summary:** Measure cold/warm timing and callback reliability  
**Labels:** expense-tracker, v0-2, sub-111  
**Delivery gate:** Gate 3 – Pilot  
**Source requirements:** NFR 02; CAP 01  
**Source acceptance:** AC 41; AC 66  
**Blocked by:** None  

**Description:** Run both-device samples with defined dataset; report every latency and missed receipt separately; do not weaken max target to p95.

**Acceptance criteria:**

- [ ] **AC 41:** All 100 warm and 100 cold delivered-alert runs per pilot device satisfy ≤2,000 ms with seeded 10,000 records; report p50/p95/max and separate delivery/network metrics.
- [ ] **AC 66:** Actual private APK install on each pilot phone documents restricted-settings behavior, posting/listener/SMS consent and recovery after battery restrictions.
- [ ] Setup uses both phones, a production-like private APK, documented OS and battery settings and a seeded 10,000-transaction dataset.
- [ ] 100 warm and 100 cold runs per phone are timed from the capture callback with a monotonic clock until durable commit, recalculated state and the in-app feedback request.
- [ ] Every run is at most 2,000 ms; median, p95, maximum and failures are reported, every outlier is kept, at most one controlled rerun follows a documented restart, and a repeated failure stays a failure.
- [ ] Missed callbacks are counted separately from slow processing.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

#### SUB-112 — Audit accessibility and live pilot reconciliation

**Issue type:** Subtask  
**Project:** SCRUM  
**Parent:** US-021  
**Summary:** Audit accessibility and live pilot reconciliation  
**Labels:** expense-tracker, v0-2, sub-112  
**Delivery gate:** Gate 3 – Pilot  
**Source requirements:** NFR 01; NFR 02  
**Source acceptance:** AC 12; AC 65; AC 70  
**Blocked by:** None  

**Description:** Execute Gate 3 fixture/live thresholds, TalkBack/text/contrast/touch checks and confirmed-ledger reconciliation.

**Acceptance criteria:**

- [ ] **AC 12:** Every claimed bank/channel format passes its fixture contract; permission loss, unsupported format and sync failure remain visible and recoverable.
- [ ] **AC 65:** Core flows usable with TalkBack and 200% text; critical controls meet 48dp and stated contrast thresholds; status has non-color cue and accessible errors.
- [ ] **AC 70:** Next month without configured limits shows No limit; no rollover/copy assumption; logs/crash reports contain no secrets, raw alerts or private financial payload.
- [ ] Every core action has a TalkBack label and a sensible focus order; the app stays usable at 200% font size, touch targets are at least 48dp, status uses text or an icon as well as colour, contrast is at least 4.5:1 (3:1 for large text), errors are announced, and light and dark themes work.
- [ ] The live run lasts at least 7 consecutive days on both phones and is reconciled against confirmed records or statements; missed callbacks are reported separately from parser failures.
- [ ] Ledger totals reconcile, and late or revoked exclusions and pending items are reported openly.
- [ ] Logs and crash reports are checked for keys, tokens, raw alerts and private financial payloads.


**Evidence:** Record focused validation for the listed outcomes, negative paths and relevant data/permission boundaries. Link dependent integration evidence when the criterion spans multiple issues.

## AC gate map

The gate tag is part of each Jira acceptance criterion. Use this map when copying any criterion that appears in more than one issue: Gate 0 is feasibility evidence, Gate 1 is personal behavior, Gate 2 is shared behavior, and Gate 3 is final pilot sign-off.

| Gate | AC IDs |
| --- | --- |
| Gate 0 | AC 12, 40, 41, 42, 50, 63, 66, 75, 76 |
| Gate 1 | AC 02, 05, 06, 07, 08, 09, 10, 14, 18, 19, 20, 21, 22, 26, 27, 28, 29, 31, 32, 35, 36, 37, 38, 39, 46, 51, 52, 53, 54, 55, 56, 57, 62, 64, 65, 67, 70, 71, 72 |
| Gate 2 | AC 01, 03, 04, 11, 13, 15, 16, 17, 23, 24, 25, 30, 33, 34, 43, 44, 45, 47, 48, 49, 58, 59, 60, 61, 68, 69, 73, 74 |
| Gate 3 | AC 75, 76 (final sign-off evidence) |

Mixed stories (US-009, US-010, US-012, US-013, US-019, US-023 and US-024) carry both Gate 1 and Gate 2 behavior. Their AC lines must retain the map tag when pasted into Jira.

Split-criterion ownership is explicit: SPIKE-01 owns the same-owner marked-Sheet rediscovery experiment for AC 74; SPIKE-05 owns encrypted/replacement recovery evidence; TASK-109 owns handover and blocked-state controls; US-019 owns the user-facing recovery disclosure. SPIKE-03 owns the AC 76 ground-truth and sample-size proposal; US-021 owns final Gate 3 measurement and sign-off. A criterion may appear in several issues because these are separate evidence or implementation responsibilities, not duplicate completion claims.

## Acceptance coverage matrix

Each v0.2 criterion maps to non-Epic delivery work; US-021 provides final integration sign-off.

| Source AC | Delivery Stories/Tasks |
| --- | --- |
| AC 01 | US-012 |
| AC 02 | US-005 |
| AC 03 | US-012, US-023 |
| AC 04 | US-012, US-015 |
| AC 05 | US-008 |
| AC 06 | US-004, US-008 |
| AC 07 | US-007, US-023 |
| AC 08 | TASK-101, US-018 |
| AC 09 | US-010, US-023 |
| AC 10 | US-013 |
| AC 11 | US-012, US-016, US-017 |
| AC 12 | SPIKE-03, TASK-102, TASK-103, TASK-104, TASK-105, TASK-106, US-004, US-021 |
| AC 13 | US-012, US-017 |
| AC 14 | US-009 |
| AC 15 | SPIKE-01, US-016 |
| AC 16 | US-010, US-016 |
| AC 17 | US-018 |
| AC 18 | US-010 |
| AC 19 | US-019 |
| AC 20 | US-019 |
| AC 21 | US-013, US-020 |
| AC 22 | US-013 |
| AC 23 | US-018 |
| AC 24 | SPIKE-02, US-014 |
| AC 25 | SPIKE-01, US-016, US-017 |
| AC 26 | US-003 |
| AC 27 | US-007, US-022 |
| AC 28 | US-007, US-008 |
| AC 29 | US-008, US-009 |
| AC 30 | US-012, US-015, US-017 |
| AC 31 | SPIKE-04, TASK-108, US-002 |
| AC 32 | US-006 |
| AC 33 | US-017, US-023 |
| AC 34 | TASK-109, US-001 |
| AC 35 | US-008, US-009 |
| AC 36 | US-011 |
| AC 37 | US-011, US-013 |
| AC 38 | US-011 |
| AC 39 | TASK-102, TASK-103, TASK-104, TASK-105, TASK-106, US-004, US-006, US-012 |
| AC 40 | SPIKE-03, US-005 |
| AC 41 | US-021 |
| AC 42 | SPIKE-05, TASK-101, US-019, US-021 |
| AC 43 | SPIKE-02, US-015, US-020 |
| AC 44 | US-009, US-010, US-015 |
| AC 45 | SPIKE-05, TASK-109, US-001, US-019 |
| AC 46 | TASK-101, US-010, US-019, US-024 |
| AC 47 | US-009, US-012, US-017 |
| AC 48 | SPIKE-01, SPIKE-02, US-014 |
| AC 49 | SPIKE-02, US-015, US-018, US-020 |
| AC 50 | SPIKE-03, TASK-102, TASK-103, TASK-104, TASK-105, TASK-106, US-004, US-025 |
| AC 51 | US-025 |
| AC 52 | US-007, US-023 |
| AC 53 | US-022 |
| AC 54 | US-010 |
| AC 55 | US-024 |
| AC 56 | US-013 |
| AC 57 | US-013, US-023 |
| AC 58 | TASK-107, US-018 |
| AC 59 | TASK-107, US-018 |
| AC 60 | TASK-107 |
| AC 61 | SPIKE-05, TASK-109, US-019 |
| AC 62 | TASK-102, TASK-103, TASK-104, TASK-105, TASK-106, US-004, US-025 |
| AC 63 | SPIKE-03, US-008 |
| AC 64 | US-013, US-023 |
| AC 65 | US-021, US-023 |
| AC 66 | SPIKE-04, TASK-108, US-002, US-021 |
| AC 67 | TASK-108, US-019 |
| AC 68 | TASK-107, US-017, US-018 |
| AC 69 | SPIKE-02, US-014, US-016 |
| AC 70 | TASK-101, TASK-108, US-011, US-021 |
| AC 71 | US-022 |
| AC 72 | US-003 |
| AC 73 | US-014, US-016 |
| AC 74 | SPIKE-05, TASK-109, US-019 |
| AC 75 | US-021 |
| AC 76 | SPIKE-03, US-021 |

## Deferred work

BL 01 expanded historical detail, BL 02 custom categories, BL 03 trusted reconciliation/owner correction queues, BL 04 multi-device/group extensions, BL 05 currency/platform/distribution expansion and BL 06 app lock/private automatic cloud backup/manual Sheet editing remain deferred. See requirements Section 8 for exact boundaries. Do not create these as first-release commitments.

## Ready-for-creation checks

- All Parent targets exist and match the verified three-level project hierarchy.
- All Blocked by references exist and dependency graph is acyclic.
- Every AC 01–76 has delivery coverage.
- Every planned issue has a Decision tags value in the Decision field map; proposed tags block implementation/sign-off until approval or revision.
- Each Story/Task/Subtask has its own acceptance criteria (at least one issue-specific criterion beyond the copied ACs), source IDs, summary and description.
- Architecture/spike acceptance is evidence still to be produced, not a claim of completed implementation.

## Jira key mapping

Created 5 October 2026 in project SCRUM. Parent and dependency relationships match this document (verified in Jira).

| Document ID | Jira key |
| --- | --- |
| EPIC-01 | SCRUM-5 |
| EPIC-02 | SCRUM-6 |
| EPIC-03 | SCRUM-7 |
| EPIC-04 | SCRUM-8 |
| EPIC-05 | SCRUM-9 |
| EPIC-06 | SCRUM-10 |
| EPIC-07 | SCRUM-11 |
| SPIKE-04 | SCRUM-12 |
| SPIKE-05 | SCRUM-13 |
| TASK-101 | SCRUM-14 |
| TASK-108 | SCRUM-15 |
| TASK-109 | SCRUM-16 |
| US-001 | SCRUM-17 |
| US-002 | SCRUM-18 |
| US-003 | SCRUM-19 |
| SPIKE-03 | SCRUM-20 |
| TASK-102 | SCRUM-21 |
| TASK-103 | SCRUM-22 |
| TASK-104 | SCRUM-23 |
| TASK-105 | SCRUM-24 |
| TASK-106 | SCRUM-25 |
| US-004 | SCRUM-26 |
| US-005 | SCRUM-27 |
| US-006 | SCRUM-28 |
| US-025 | SCRUM-29 |
| US-007 | SCRUM-30 |
| US-008 | SCRUM-31 |
| US-009 | SCRUM-32 |
| US-010 | SCRUM-33 |
| US-022 | SCRUM-34 |
| US-011 | SCRUM-35 |
| US-012 | SCRUM-36 |
| US-013 | SCRUM-37 |
| US-023 | SCRUM-38 |
| SPIKE-01 | SCRUM-39 |
| SPIKE-02 | SCRUM-40 |
| US-014 | SCRUM-41 |
| US-015 | SCRUM-42 |
| US-016 | SCRUM-43 |
| TASK-107 | SCRUM-44 |
| US-017 | SCRUM-45 |
| US-018 | SCRUM-46 |
| US-019 | SCRUM-47 |
| US-024 | SCRUM-48 |
| US-020 | SCRUM-49 |
| US-021 | SCRUM-50 |
| SUB-101 | SCRUM-51 |
| SUB-102 | SCRUM-52 |
| SUB-103 | SCRUM-53 |
| SUB-104 | SCRUM-54 |
| SUB-105 | SCRUM-55 |
| SUB-106 | SCRUM-56 |
| SUB-107 | SCRUM-57 |
| SUB-108 | SCRUM-58 |
| SUB-109 | SCRUM-59 |
| SUB-110 | SCRUM-60 |
| SUB-111 | SCRUM-61 |
| SUB-112 | SCRUM-62 |

## Decision field map

Copy the value in the second column into the Jira issue's **Decision tags** field (or the description if no custom field exists). This is a creation control: any issue carrying a proposed tag remains blocked from implementation/sign-off until that decision is approved or revised. The values are derived from each issue's Source acceptance IDs and the Requirements §8 matrix. `None` means no proposed decision applies to that issue's acceptance criteria.

| Document ID | Decision tags |
| --- | --- |
| EPIC-01 | None |
| SPIKE-04 | None |
| SPIKE-05 | None |
| TASK-101 | None |
| TASK-108 | None |
| TASK-109 | None |
| US-001 | None |
| US-002 | None |
| US-003 | None |
| EPIC-02 | None |
| SPIKE-03 | D-12 |
| TASK-102 | None |
| TASK-103 | None |
| TASK-104 | None |
| TASK-105 | None |
| TASK-106 | None |
| US-004 | None |
| US-005 | None |
| US-006 | None |
| US-025 | None |
| EPIC-03 | None |
| US-007 | None |
| US-008 | None |
| US-009 | None |
| US-010 | None |
| SUB-109 | None |
| SUB-110 | None |
| US-022 | None |
| EPIC-04 | None |
| US-011 | None |
| US-012 | None |
| US-013 | None |
| US-023 | None |
| EPIC-05 | None |
| SPIKE-01 | None |
| SPIKE-02 | None |
| US-014 | None |
| SUB-101 | None |
| SUB-102 | None |
| US-015 | None |
| SUB-103 | None |
| SUB-104 | None |
| US-016 | None |
| EPIC-06 | None |
| TASK-107 | None |
| US-017 | None |
| US-018 | None |
| SUB-105 | None |
| SUB-106 | None |
| US-019 | None |
| SUB-107 | None |
| SUB-108 | None |
| US-024 | None |
| EPIC-07 | None |
| US-020 | None |
| US-021 | D-12 |
| SUB-111 | None |
| SUB-112 | None |
