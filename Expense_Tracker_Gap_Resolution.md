# Expense Tracker — Gap Resolution Record

Version 0.2 | 4 October 2026

## Outcome

The requirements and Jira-ready Markdown are revised around the user's explicit choice: **keep a server-free design and accept weaker enforcement**. This closes the specification contradictions and backlog omissions; it does not mean the application exists or platform/device experiments have passed. D-01, D-02, D-03, D-04, D-05, D-06, D-07, D-08, D-09, D-10, D-11 and D-13 are the user-approved decisions. D-12 remains proposed until explicitly approved.

The requirements' Section 8 decision-applicability matrix is authoritative for proposed behavior. A tagged section or AC is a planning assumption until its decision is approved; it is not silently active release behavior.

Current artifacts:
- [Requirements](Expense_Tracker_Requirements.md)
- [Jira backlog](Expense_Tracker_Jira_Backlog.md)
- [Historical review archive](docs/archive/Expense_Tracker_Review_Findings_v0.1.md)
- [Original requirements snapshot](docs/archive/Expense_Tracker_Requirements_v0.1.md)
- [Original backlog snapshot](docs/archive/Expense_Tracker_Jira_Backlog_v0.1.md)

## Material decisions

- App roles are enforced by compliant app flows. Google file owners/editors can bypass them externally. Manual cell editing remains unsupported; no read-only-to-owner or tamper-proof claim.
- Keep one actively shared financial Sheet. Invitation is a normal Google share of a marked Sheet, found automatically by the invitee's app; the invitee's Accept in the app starts sharing (broader Drive listing permission accepted for the pilot, one-time file picker as fallback). This is approved (D-02, 5 October 2026); SPIKE-01 must still verify it.
- Acceptance is the invitee tapping Accept in the app, recorded as a `joined` row. Leaving writes a `left` row and stops local sharing immediately; the owner's app removes the Google access and records `ended`.
- Private historical details stay local. Shared current-month totals use safe opening aggregates. Archive old Sheet owner-only on termination; create new Sheet for each rejoin (approved as D-03, 5 October 2026).
- Retained former-member contributions freeze at closure. No post-removal owner review queue; personal corrections remain possible. Owner's own continuing personal records remain editable without rewriting archived shared snapshot.
- All unresolved review gates, including category, exclude spending. Show provisional known-INR exposure separately; the reversal of counted Uncategorized is approved (D-05, 5 October 2026).
- Retain only safely sanitized unresolved financial excerpts, encrypted locally, until the item is resolved or dismissed, with no time limit (approved D-06, option D, 5 October 2026); never OTP/unrelated text, logs/exports/remote content.
- One active device/account and Android 16 pilot; portable exports and reminders. No application backend or private cloud backup.
- The dedup window, stale-state limit, alert thresholds, retroactive account confirmation, restore of deleted purchases and the strict latency rule are approved (D-11, 5 October 2026). The Gate 3 pilot targets (D-12) remain proposed until SPIKE-03 shows which bank/channel combinations and sample sizes are available.

## Finding disposition

| Review concern | Specification resolution | Delivery responsibility |
| --- | --- | --- |
| Sheet permissions and two-writer concerns | MEM 02 acknowledges limitations; SYN 02 folds stable-ID events rather than promising exactly-once append. | SPIKE-01; US-016; US-018 |
| Drive scopes and OAuth expiry | ARC 01 proposes Drive listing plus Sheets access for automatic discovery, with the file picker as fallback, and requires reauthorization handling. | SPIKE-01; US-001 |
| Missing invitation delivery/authority | MEM 01 invitation by sharing the marked Sheet; automatic discovery; Accept in the app; one-invitee and join-account checks. | SPIKE-02; US-014 |
| Pre-sharing private history | HIST 01 safe aggregates, private identifiers excluded, fresh Sheet on rejoin. | US-012; US-017 |
| Post-removal queue versus BL 03 | MEM 04 defers queue, freezes retained former-member spend and flags rejected late writes. | US-015; US-010 |
| Counting ambiguity | TXN 01 truth table, CAT 02 pending view and revised AC 07. | US-007; US-008 |
| Raw text versus review | RAW 01 sanitized local excerpt kept until resolved or dismissed; explicit exclusions. | US-025 |
| Weak dedup specification | CAP 05 replay/strong/candidate/nonmatch tiers; no automatic fuzzy merges. | US-005 |
| Date correction boundaries | BUD 04 precision and boundary-day confirmation; MEM 04 open/closed behavior. | US-010; US-012 |
| Limits versus historical totals | BUD 05 locks limits, allows valid spend recalculation; closed projections separately defined. | US-011; US-009 |
| Sheet removal/re-invite lifecycle | HIST 01 owner-only archive, no post-removal personal uploads, new Sheet/epoch. | US-015; US-017 |
| Third member/reciprocal guard | BUD 01 rechecks both send and activation; no overlapping shared roles. | US-014; US-016 |
| Device count/handover | ARC 01/SEC 02 one active pilot device, same-owner reinstall and marked-Sheet rediscovery test, supported-flow checks and replacement. | SPIKE-01; SPIKE-05; TASK-109 |
| Alert timing/freshness | FBK 02 per-device crossings/coalescing and FBK 03 pending/failure/15-minute stale rules. | US-013 |
| Non-expense cases/categories | TXN 02 explicit funding/investment/EMI/premium/subscription policy; fixed list retained. | US-008 |
| Foreign-currency visibility | CAP 03 unsupported item, original units preserved, no INR conversion. | US-025 |
| Third Android capability/sideload | CAP 01 separates posting from listener/SMS; exact install behavior tested. | SPIKE-04; US-002 |
| Notification allowlist | CAP 02 exact verified packages, messaging mirrors off by default. | SPIKE-03; US-004; US-025 |
| Backup/phone loss | REC 01 portable password export, key-dependent platform backup exclusions, 7-day reminder and an owner-phone-loss recovery path through marked-Sheet rediscovery. | SPIKE-05; TASK-109; US-019 |
| App lock/logs/Android/formatting | Optional app lock BL 06; redacted logs, Android 16 and Indian INR format specified. | TASK-108; US-023 |
| Exit metrics/testability | NFR 02 exact run conditions, retained-outlier rerun protocol and provisional Gate 3 thresholds with named ground truth. | SPIKE-03; US-021 |
| Document hygiene | Version 0.2, glossary, canonical requirement sections, decision register, sorted AC IDs. | Completed document edit |
| Jira hierarchy and bloat | Epic → Story or Task → Subtask; verified exact Subtask type; 94 to 58 planned issues. | Completed document edit |
| Parser implementation | Five explicit bank parser Tasks plus fixture-gathering prerequisite. | TASK-102–106; SPIKE-03 |
| Classification/memory/confidence | CAT 01 exact validated/remembered rules, explicit opt-in for category and purchase/transfer/excluded intent, no historical rewrite. | US-022 |
| Encryption/local DB/outbox | DAT 01/03 schema, key lifecycle and atomic commit contract. | TASK-101; SPIKE-05 |
| Full wipe and deleted-record restore | REC 02 precise local-only scope; CAT 03 original-ID authorized restore. | US-024; US-010 |
| Threshold configuration | FBK 02 allowed values, persistence, permission and baseline behavior. | US-013 |
| Schema recovery | SYN 03 writes pause, outbox retained, no fabricated lost source records. | TASK-107 |
| Functional screens | UI 01 overview/filters/details/settings outcomes planned without fixed visual design. | US-023 |
| Risk spikes too late | Section 9 Gate 0 precedes dependent work; no claim of completed experiments. | SPIKE-01–05 |
| Traceability | Per-issue requirement/AC IDs and copied AC text, plus AC coverage matrix. | Completed document edit |
| Release slicing | Personal checkpoint then shared checkpoint; entire first release requires both. | Section 9 / issue delivery gates |
| Backend toolkit mismatch | ARC 01 native Android stack; backend toolkit explicitly non-applicable. | TASK-108 |

## Validation performed on the documents

- 7 Epics, 25 Stories, 14 Tasks (5 feasibility spikes included), 12 Subtasks.
- Every Parent reference exists and matches the verified project hierarchy.
- Every dependency exists; dependency graph is acyclic.
- All AC 01–76 have non-Epic delivery coverage.
- All issue requirement references resolve to the updated specification.
- Source issue types fetched from SCRUM: Epic, Story, Task, Subtask.
- Original documents archived. The 58 issues were created in Jira on 5 October 2026 (SCRUM-5 to SCRUM-62); see the backlog's "Jira key mapping".

## Evidence still required before shipping

SPIKE-01–05 are implementation work, not documentation pass marks. Both-account Drive behavior (including automatic discovery of the shared Sheet), invitation and removal, actual bank alert formats, private-APK Samsung behavior, and portable encryption recovery must be demonstrated. A failed mandatory spike blocks its dependent work and requires a documented design revision. Accepted weaker enforcement does not waive data-loss, accidental exposure, counting or revocation-completion correctness.
