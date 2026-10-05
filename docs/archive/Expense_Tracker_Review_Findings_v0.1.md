# Review of the pasted requirements and Jira-backlog concerns

> Historical assessment of v0.1. The subsequent v0.2 changes and remaining implementation evidence are recorded in [Gap Resolution](Expense_Tracker_Gap_Resolution.md).

Reviewed 1 October 2026 against the current local requirements, Jira backlog, and official platform documentation. This is an assessment, not approval of new product decisions or changes to the requirements. No Jira or Confluence content was changed.

## Overall verdict

Most concerns are valid. The sharing architecture is not sufficiently specified to promise its permission guarantees, and the backlog is not ready for direct Jira creation. The Task-under-Story hierarchy in the backlog is an error I introduced. Several implementation responsibilities are also missing despite appearing in Epic acceptance criteria.

Some statements are too absolute: protected ranges exist in Sheets; idempotent logical processing is possible with an explicit design; invitations do not inherently require an application server; and the raw-alert policy says discard after successful parsing, not immediately on receipt. The proposed fixes should therefore be evaluated rather than accepted as a bundle.

## The seven principal findings

### 1. Sheet permissions — valid blocker; proposed fix is incomplete

The requirements demand owner-only shared-limit changes, own-transaction-only corrections, and no manual source-tab edits by either participant. They also acknowledge that the write-permission architecture is unresolved in the Architecture constraint after NFR 02. That is a real dependency, not an implementation detail that can safely be deferred.

The assertion that every editor can edit every cell needs qualification: Sheets supports protected ranges. However, the spreadsheet owner can still edit protected ranges. An app using a participant's own Google identity cannot simply use those same identity permissions to permit the app but prohibit that participant's manual edits. These platform facts support the conclusion that the current guarantees need an explicit authority and trust model. [Sheets scopes](https://developers.google.com/workspace/sheets/api/scopes), [Google's protection documentation](https://developers.google.com/apps-script/reference/spreadsheet/protection).

**The two-Sheet suggestion is a candidate design, not a complete fix.** It separates original data writers, but changes the agreed one-shared-Sheet model. It still needs rules for aggregation, revocation, historical snapshots, synchronization when the owner's app is offline, and preventing the owner from changing the invitee's copied rows in the owner's report. It also does not prevent either user from manually editing their own writable Sheet. Personal-only information must not leak through the invitee's export Sheet.

**Idempotency claim: overstated.** The append API exposes no request idempotency key, so blind retries can append duplicates. A single writer also has retry uncertainty. Stable operation IDs, controlled writes and reconciliation can make logical totals idempotent; multiple writers require a more careful design. This is an engineering inference from the API contract, not a guarantee that Sheets supplies database-style uniqueness. [Append API](https://developers.google.com/workspace/sheets/api/reference/rest/v4/spreadsheets.values/append).

**drive.file concern: valid spike.** Per-file app authorization and a user's file-sharing permission are separate concerns. Access to an owner-created file must be exercised with the invitee's account and selected authorization flow. The scope does not establish that shared files are universally inaccessible; Google documents user selection/opening through the app or Picker. [Drive scopes](https://developers.google.com/workspace/drive/api/guides/api-specific-auth).

**Seven-day OAuth concern: valid with conditions.** Google documents seven-day refresh-token expiry for external apps whose consent publishing status is Testing, except when only basic identity scopes are requested. Drive access falls outside that identity-only exception. This is not a claim that all access tokens last seven days or that every production token does. [OAuth documentation](https://developers.google.com/identity/protocols/oauth2).

### 2. Invitation discovery and membership authority — valid gap, not proof a server is mandatory

MEM 01 and US-014 specify invitation by email and acceptance by the intended Google account, but do not specify how a pending invitation reaches the app, how acceptance reaches the owner, or who authoritatively activates and revokes membership.

A user-shared link, QR code, or separately accessible invitation metadata could provide discovery without exposing the financial Sheet. These are design options, not selected requirements. They still need identity binding, replay/expiry behavior, acknowledgement, permission grants and a source of authoritative membership state. Decide these before implementing sharing.

### 3. Pre-sharing transaction visibility — valid privacy decision; opening balances are a candidate

Preserving the owner's current-month spending does not require publishing the underlying transactions. The pasted note is correct that uploading the full historical ledger would resolve BL 01 by accidentally granting access.

A category-level opening amount plus shared-period details can preserve current balances without exposing historical transaction details. However, that design still needs treatment of later corrections/refunds to pre-sharing purchases, reconciliation and contribution reports. It reveals aggregate prior spending, so that visibility also needs an explicit decision.

**Conclusion:** Full historical-detail sharing may remain deferred, but a safe initial exposure rule must be chosen before sharing ships.

### 4. Post-removal owner review versus BL 03 — valid first-release inconsistency

CAT 03, MEM 01 and AC 44 require changes affecting frozen shared totals to enter an owner review queue. BL 03 defers server-backed reconciliation after access is lost. No transport, authority or delivery guarantee for that queue is specified.

The queue does not inherently require a server: a separate correction-request mechanism is possible. It does require an explicit design. Deferring the queue and allowing personal-only corrections is coherent, but changes an agreed baseline rule and AC 44. Similarly, rejecting delayed queued writes after revocation is a plausible policy that needs to distinguish personal preservation from rejected shared propagation.

### 5. Unknown merchants and Uncategorized — valid ambiguity requiring a truth table

CAT 01 and AC 27 exclude low-confidence review items. CAT 02 and AC 07 allow a confirmed posted but unclassified expense to count in overall spending. These can be different states, but the source does not define them clearly enough, and the backlog largely implements only the excluded-pending interpretation.

Specify payment status, ownership confirmation, expense-intent confirmation, review reason, currency, category and deletion state separately. If confirmed-but-uncategorized expenses remain supported, state that they affect overall spending but not a named category's balance. Otherwise revise CAT 02 and AC 07 to the excluded-until-classified model.

The proposed pending amount/count is useful, but should not blindly sum possible duplicate candidates, unknown amounts or non-INR amounts. Label any displayed amount as provisional and distinguish known INR exposure from unresolved items.

### 6. Raw alert deletion versus review — partly valid; retention is under-specified

CAP 3.2, DAT 01 and the agreed baseline say discard raw text **after successful parsing**. They do not explicitly require discarding every unparsed alert on arrival. Review of an already parsed amount or category may need only normalized fields; review of an unsupported message may require more context.

Define what counts as successful parsing and what an unresolved item can retain. Temporary encrypted on-device retention with a bounded lifetime and deletion on resolution is one possible policy change. If adopted, keep it out of exports, Sheets and logs; maintain the existing prohibition on retaining OTPs or unrelated content. Another option is an explicit manual-entry fallback. The pasted suggestion is not the only valid solution.

### 7. Reference-free deduplication — valid design gap; prevalence claim unverified

CAP 05 already permits amount, merchant and time as supporting evidence, so fallback matching is not entirely absent. It lacks operational rules: time windows, merchant normalization, source combinations, account confidence, ambiguous-match handling and treatment of notifications updated in place.

The claim that UPI notifications usually lack both reference and account needs the pilot's actual app/version fixtures. A fuzzy match should not automatically merge genuine same-value purchases merely to reduce review volume. Define strong matches, candidate matches and non-matches, with identity preservation through review.

## Remaining concerns

| Concern | Verdict and needed action |
| --- | --- |
| Date corrections across acceptance/removal/gaps | **Valid.** Define whether a correction can add or remove a shared attribution, which membership interval controls it, and how frozen history is handled. Date-only alerts and manual dates also need a same-day boundary rule when acceptance occurs mid-day. |
| Historical totals versus locked limits | **Valid wording refinement.** BUD 05 ties preservation to changing future limits; it is not a blanket ban on refunds changing past spend. Explicitly say limits are locked while permitted corrections/refunds can recalculate historical totals. |
| Sheet after removal and on re-invite | **Valid.** Define retention, access, future personal writes, Sheet reuse and exposure of records from the membership gap. Reusing a file must not reveal data unintentionally. |
| Invitee inviting a third person | **Valid enforcement case.** BUD 01 already excludes multiple simultaneous memberships, but explicit guards must cover an invitee attempting to share their separate personal budget as well as a third member of the current budget. |
| One or multiple devices per user | **Valid undecided scope.** Two pilot phones do not establish a one-device product restriction. This affects device replacement, concurrent writers, deduplication, history and alerts. |
| Threshold timing across devices | **Valid clarification.** Devices with different sync freshness cannot be promised simultaneous alerts. Define local crossing detection, sync-triggered alerts and duplicate suppression. The source already avoids promising immediate cross-device notifications. |
| Stale-data threshold | **Valid.** Define known-pending changes, failed sync and any time-based stale rule. A time threshold should not hide data already known to be outdated. |
| Investments, EMIs, insurance, wallet top-ups | **Valid classification decisions.** These are not all automatically non-expenses. Insurance is commonly an expense; EMI principal/interest and wallet funding need explicit treatment to avoid double counting. Do not create financial behavior by assuming all belong to one exclusion rule. |
| ATM withdrawals | **Already addressed.** Section 3.1 says a withdrawal is not itself a purchase. Include a concrete acceptance case if needed. |
| Missing Subscriptions/Insurance categories | **Optional taxonomy change.** Existing Utilities and bills, Health, Entertainment or Other may cover these. Evaluate real statements, but the fixed category list was agreed; additions require a product decision. |
| Foreign-currency alerts | **Valid visibility improvement, not missing exchange-rate support.** Keep them outside INR totals and show an unsupported-currency/review outcome. Review must not imply the user can simply treat USD as INR. |
| POST_NOTIFICATIONS | **Confirmed omission.** Receiving notifications via listener access differs from posting the app's own notifications. Modern Android requires the posting permission for ordinary alerts. “Three permissions” is best expressed as three capability/consent flows because SMS can involve multiple manifest permissions. [Android notification permission](https://developer.android.com/develop/ui/compose/notifications/notification-permission). |
| Sideloaded-app restricted settings | **Valid pilot risk; blanket wording too strong.** Android documents restricted settings and an allow flow, but installation route, device and OS behavior need validation. Do not claim every sideloaded APK follows one identical path. [Android restricted settings](https://support.google.com/android/answer/12623953?hl=en). |
| Notification package allowlist | **Valid design recommendation.** Process only approved sources and discard others without persistence. Samsung Messages can duplicate financial SMS if selected; excluding it is one policy, but a deliberately supported notification fallback should instead deduplicate it. |
| Backup and phone loss | **Valid recovery risk.** Keystore keys are non-exportable; copying an encrypted database is not by itself a portable restore design. Configure platform backup/transfer behavior explicitly and verify the password-protected app export on a replacement device. Phone loss does not imply all shared data is lost, but unexported personal data may be. [Keystore](https://developer.android.com/privacy-and-security/keystore), [Android backup rules](https://developer.android.com/identity/data/autobackup). |
| Optional private Sheet or export reminder | **Product options.** A private Sheet changes the agreed personal-data storage model. An export reminder fits the current model more closely, but neither is an already approved requirement. |
| App lock, redacted logs, Android versions, INR formatting | **Mixed.** Redacted logs and supported Android/SDK versions need explicit coverage. Indian grouping such as ₹1,00,000 should be specified. App lock is an additional feature decision, not an established omission from approved scope. |
| Pilot exit criteria | **Valid.** Section 9 explicitly leaves numeric coverage targets for later. Set evidence-backed targets after collecting fixtures and before deciding pilot success; do not invent percentages before the denominator and supported combinations exist. |
| Document hygiene | **Valid.** Section 8 mixes decisions and recap, AC 25–29 follow AC 46, and the header still says version 0.1 / 27 September. The chronology is not a factual contradiction, but version/last-updated metadata would clarify it. Add role terminology and replace repeated rules with references to authoritative definitions. |
| Jira hierarchy | **Confirmed error in my backlog.** Tasks currently use US-* parents despite the import note claiming Stories and Tasks are Epic children. Standard Jira places Story and Task at the same level. Use Epic → Story → Sub-task, with independent technical Tasks under Epics and links to relevant Stories where helpful. Removing all Tasks is optional; fixing the parents is mandatory. [Atlassian hierarchy](https://support.atlassian.com/jira-cloud-administration/docs/what-are-issue-types/). |
| Backlog size | **Judgment call.** 94 issues may be excessive for this pilot, and several repeat rather than meaningfully decompose acceptance criteria. Consolidate overlapping work; the count itself is not proof of a defect. |
| Five bank parsers, auto-categorization, remembered corrections, confidence | **Valid missing implementation coverage.** TASK-004 covers the parser framework/adapters; later fixture validation is not implementation of five parsers. Auto-assignment rules, remembering corrections and confidence policy also need owned delivery work. Separate bank tickets can follow actual fixtures rather than assuming one ticket per bank is sufficient. |
| Local encrypted database | **Valid missing delivery item.** It appears in Epic criteria without a Story/Task implementing the database, keys, migrations and offline persistence. |
| Full local wipe | **Valid missing implementation item.** Define wipe scope, confirmation and effects on remote/shared data, backups and key material as well as deleting local rows. |
| Restore soft-deleted records | **Under-specified.** Baseline says restoration is allowed “if policy permits.” Set the restoration policy and permissions before declaring it an unconditional missing first-release feature. |
| Threshold configuration | **Partly covered.** Roles and configurable thresholds appear in US-013/US-016, but no clear deliverable covers editing, validation and persistence. Add explicit acceptance criteria or a child issue. |
| Sheet schema-version recovery | **Valid.** SYN 03 proposes detection/recovery for incompatible or missing Sheet data; the backlog does not allocate the work. Preserve its proposed status until confirmed. |
| Overview and transaction-list screens | **Valid functional coverage gap.** Required outcomes and filters can be planned without settling visual design or navigation. UI-design deferral does not defer access to transactions and balances. |
| Early risk spikes | **Valid recommendation; source sequence needs qualification.** Requirements Section 9 already puts capture validation first. The backlog defers concrete fixture validation into EPIC-07 and needs earlier risk work for permissions, invitations, alert fixtures and actual device restrictions. |
| Testability | **Valid.** Define dataset, alert fixtures, start/end timestamps, warm/cold process state, sample count, device conditions and pass criterion. Using p95 instead of an absolute two-second target would change the requirement; record that choice explicitly. Accessibility should state measurable criteria suited to native Android rather than relying on “readable.” |
| Per-story traceability | **Valid.** A final list of source areas is not enough to show which Story covers which requirement or AC. Add source IDs, AC IDs and cross-cutting dependencies per Story. |
| Personal release before sharing | **Reasonable option.** It reduces dependency on unresolved sharing architecture. It is a release-scope decision, not a factual correction to the current shared-budget first-release goal. |
| Toolkit stack versus product stack | **Valid mismatch to resolve.** The local .claude/CLAUDE.md includes a Kotlin/Micronaut backend section at line 167. That tooling guidance does not establish the Android app's architecture. Record actual stack and server assumptions; backend-specific rules apply only if that backend is adopted. |

## Recommended next action

1. Resolve the authority model for shared writes, file access, invitation discovery/acceptance and revocation through a short architecture spike using both Google accounts. Evaluate the two-Sheet option against the exact agreed guarantees rather than treating it as a drop-in fix.
2. Decide the initial historical-data exposure, post-removal correction behavior, transaction counting state table and unresolved-alert retention policy. Record each as a product decision.
3. Correct the Jira hierarchy and add missing implementation work, dependencies and per-story requirement/AC references. Treat the current Markdown as a draft until those changes are made.
4. Bring bank fixtures and device capability checks forward, then define measurable pilot exit criteria and decide whether to stage a personal-only release.

The proposed changes to Sheets structure, history visibility, raw retention, correction queues, private cloud backup, category taxonomy and release scope should not be silently applied as editorial fixes: they change existing decisions.
