# Plan: Android scaffold and Gate 0 spikes

**Spec**: `Expense_Tracker_Requirements.md` v0.2 (no separate `.planning/specs/` file; the requirements are the spec). Behaviour per screen: `.planning/design/handoff/`.
**Epic**: EPIC-01 (foundation) and Gate 0 spikes SPIKE-01 to SPIKE-05 (Jira SCRUM-39, 40, 20, 12, 13).
**Created**: 5 October 2026
**Status**: draft, waiting for approval

## 1. Goal and scope

Two outcomes, in this order of urgency:

1. **Prove the risky assumptions (Gate 0).** The requirements say architecture-dependent work waits for evidence: Google Drive/Sheets discovery and sharing (SPIKE-01, 02), real bank alert formats (SPIKE-03), Samsung APK permission and background behaviour (SPIKE-04), and encrypted storage with portable export (SPIKE-05).
2. **A thin Android scaffold** so the spikes have a home and the design has its first real screen: project structure, the design tokens as a Compose theme, the four-tab shell, money formatting and the hero calculation, and the Pocket library wired into an Overview with fake data.

Out of scope here: any real capture, database, sync or sharing feature (those are TASK-101 onward and stay blocked until their spike passes), and the Pocket licence decision.

**Stack (D-08, ARC 01):** Kotlin, Jetpack Compose, coroutines and Flow, Room with SQLCipher, Android Keystore, WorkManager, Google sign-in with Drive and Sheets APIs. Android 16 (API 36) only. The Micronaut/Postgres toolkit in `.claude/` is not the product stack; only its general habits apply (no `!!`, named arguments, tests first, no workarounds).

## 2. Preconditions (need you, not code)

| # | Need | Why | Who |
| --- | --- | --- | --- |
| P1 | Confirm app name and `applicationId` (proposal: `com.expensetracker.app`, name "Expense Tracker") | Fixed once Google OAuth is registered | You |
| P2 | Two Google accounts for testing (the two pilot users, or two test accounts) | SPIKE-01, 02 | You |
| P3 | A Google Cloud project with an Android OAuth client (needs the debug keystore SHA-1), consent screen in Testing mode with both accounts as test users, Sheets and Drive APIs enabled | SPIKE-01 | You, with my step-by-step |
| P4 | Both pilot phones with USB debugging, one connected at a time | SPIKE-04 | You |
| P5 | A handful of real bank SMS and notification alerts, with amounts, names, card and account numbers blanked | SPIKE-03 | You, using the redaction tool from task 14 |
| P6 | Confirm where Pocket lives for the build (see Open decisions) | Module path | You |

Already in place on this machine (checked 5 October 2026): JDK 21 (Android Studio runtime), Android SDK with platform 36 and build tools 36.x, Android emulator with a Play Store image `android-36.1` and one AVD, cached Gradle 8.13, `adb`. No phone is connected right now.

## 3. Architecture

Three Gradle modules under a new `android/` folder, to keep it simple for a two-person pilot:

| Module | Purpose |
| --- | --- |
| `:app` | The product app: theme, navigation shell, screens, domain logic (money, budget), later data and sync. Layers by package: `ui` → `domain` → `data`. UI never talks to data directly. |
| `:pocket` | The existing character library from `assets/pocket/android/pocket`, included by path, unchanged. |
| `:spikes` | A debug-only app that holds throwaway spike code (one screen per spike). Never shipped, deletable after Gate 0. |

Rules: pure Kotlin logic (money, hero calculation, redaction helpers) has JVM unit tests and no Android dependencies; Compose screens are stateless composables fed by a state object; local save and feedback ordering follows DAT 01 once the database exists.

### Components

| Component | Type | Purpose |
| --- | --- | --- |
| `ExpenseTrackerTheme` | Compose theme | Light and dark colour roles, extra colours, type, shape, spacing from `tokens.md` |
| `Paise`, `IndianCurrencyFormat`, `SpokenCurrency` | Domain (pure Kotlin) | Integer paise, `₹1,00,000.00` grouping, paise only when non-zero, spoken labels |
| `HeroBudget` | Domain (pure Kotlin) | Sum of limits, left to spend, labels, pending line, input for `PocketBudget` (spec `01`) |
| `AppShell`, `AppNav` | UI | Four tabs with Review badge, sync chip slot, add-expense FAB, saved state per tab |
| `OverviewScreen` | UI | Hero with Pocket, pending row, "Needs a look", on-track row, fake data only |
| `SpikeLauncher` and one screen per spike | Spike UI | Run and log each experiment |
| `tools/redact_alert.py` | Dev tool | Strip OTPs, account and card numbers, names from collected alerts (SPIKE-03) |

### File locations (new)

| File | Location | Purpose |
| --- | --- | --- |
| Gradle settings, root build, version catalog, wrapper | `android/` | Project skeleton; pins AGP 8.13.1, Kotlin 2.2.10, Gradle 8.13 (as the Pocket module builds today) |
| App module | `android/app/` | Product app |
| Theme | `android/app/src/main/java/com/expensetracker/app/ui/theme/` | `Color.kt`, `Type.kt`, `Shape.kt`, `Spacing.kt`, `Theme.kt` |
| Fonts | `android/app/src/main/res/font/` | DM Sans (SIL Open Font License) |
| Money and budget logic | `.../app/domain/money/`, `.../app/domain/budget/` | Pure Kotlin |
| Shell and screens | `.../app/ui/` | `AppShell.kt`, `nav/`, `overview/`, `components/` |
| Unit tests | `android/app/src/test/java/com/expensetracker/app/` | Money, hero, redaction mirrors |
| Spikes module | `android/spikes/` | One package per spike |
| Evidence records | `docs/evidence/` | Dated records per spike (template included) |
| Tools | `tools/` | `redact_alert.py` and its test |
| Android README | `android/README.md` | Build, test and run commands |

### Existing files to change

| File | What changes | Why |
| --- | --- | --- |
| `.gitignore` | Ignore `android/**/build/`, `.gradle/`, `local.properties`, `*.keystore`, `*.jks`, `docs/evidence/raw/` | Keep build output, signing keys and unredacted samples out of the public repo |
| `Expense_Tracker_Requirements.md`, backlog, Jira | Only after spike results, if a requirement must change | Evidence may revise D-02, D-11, D-12 or AC 74 |

## 4. Tasks

Each task is one commit and at most three files. Write the test first where logic is involved.

### Phase 1: Scaffold (tasks 1 to 11)

| # | Task | Files |
| --- | --- | --- |
| 1 | Gradle skeleton with version catalog and wrapper 8.13 | `android/settings.gradle.kts`, `android/build.gradle.kts`, `android/gradle/libs.versions.toml` |
| 2 | `:app` module, manifest, Application class, applicationId from P1 | `android/app/build.gradle.kts`, `AndroidManifest.xml`, `ExpenseTrackerApp.kt` |
| 3 | Colour roles, extra colours and type from `tokens.md`, light and dark | `ui/theme/Color.kt`, `Type.kt`, `Theme.kt` |
| 4 | Shape and spacing tokens, 48dp touch constant | `ui/theme/Shape.kt`, `Spacing.kt` |
| 5 | Bundle DM Sans and a font check screen (verify `₹` glyph and tabular figures on both phones) | `res/font/*`, `ui/dev/FontCheck.kt` |
| 6 | **Tests first**, then paise and Indian formatting and spoken labels (`₹1,299`, `₹1,299.50`, `₹1,00,00,000`, negative, zero) | `domain/money/IndianCurrencyFormatTest.kt`, `Paise.kt`, `IndianCurrencyFormat.kt` |
| 7 | **Tests first**, then `HeroBudget`: sum of limits, left, over-budget label, ₹0 label, no-limit mode, zero limit, pending line | `domain/budget/HeroBudgetTest.kt`, `HeroBudget.kt` |
| 8 | Include `:pocket` by path and expose a `PocketBudget` adapter from `HeroBudget` | `android/settings.gradle.kts`, `domain/budget/PocketAdapter.kt` |
| 9 | App shell: four tabs, Review badge, sync chip slot, FAB, saved state per tab | `ui/AppShell.kt`, `ui/nav/AppNav.kt`, `ui/components/SyncChip.kt` |
| 10 | Overview screen from the spec with fake data and all six hero states selectable in a debug menu | `ui/overview/OverviewScreen.kt`, `OverviewState.kt`, `OverviewFakeData.kt` |
| 11 | Android README and verified commands (unit tests, assemble, lint) | `android/README.md`, `.gitignore` |

### Phase 2: Spike harness and tooling (tasks 12 to 14)

| # | Task | Files |
| --- | --- | --- |
| 12 | `:spikes` module with a launcher listing the five spikes | `android/spikes/build.gradle.kts`, `AndroidManifest.xml`, `SpikeLauncher.kt` |
| 13 | Evidence template and rules (dated record, device, OS build, app build, steps, result, pass/block) | `docs/evidence/README.md`, `docs/evidence/_template.md` |
| 14 | **Test first**, then alert redaction tool (OTP, 10 to 16 digit numbers, names, UPI handles, emails) | `tools/redact_alert_test.py`, `tools/redact_alert.py` |

### Phase 3: Gate 0 spikes (tasks 15 to 31)

| # | Spike | Task | Files |
| --- | --- | --- | --- |
| 15 | SPIKE-04 (SCRUM-12) | Minimal SMS receiver (new-message permission only), notification listener, own-notification poster, with redacted logging | `spikes/s04/SmsReceiver.kt`, `AlertListener.kt`, `Notifier.kt` |
| 16 | SPIKE-04 | Permission walkthrough screen reproducing the setup flow, incl. restricted settings | `spikes/s04/PermissionLab.kt` |
| 17 | SPIKE-04 | Run the protocol on S25 Ultra then Z Flip 5: install route, restricted setting, denial, revocation, battery optimisation, background receipt after force stop and reboot; write the evidence record | `docs/evidence/spike-04-<date>.md` |
| 18 | SPIKE-05 (SCRUM-13) | Room plus SQLCipher with a Keystore-wrapped key; insert, restart process, read back | `spikes/s05/EncryptedDb.kt`, `KeystoreKeyProvider.kt`, `DbTest.kt` |
| 19 | SPIKE-05 | Password-based portable export and restore on a clean install without copying the device key (Argon2id or PBKDF2, AES-GCM, versioned header) | `spikes/s05/PortableExport.kt`, `ExportRestoreTest.kt` |
| 20 | SPIKE-05 | Confirm backup rules exclude the database, key and tokens; record library versions pinned; write the evidence record | `docs/evidence/spike-05-<date>.md`, `spikes/s05/backup_rules.xml` |
| 21 | SPIKE-01 (SCRUM-39) | Google sign-in and token handling with the smallest scope set: list Sheets shared with me by name and owner, read and write the marked Sheet | `spikes/s01/GoogleAuth.kt`, `DriveListing.kt`, `SheetsClient.kt` |
| 22 | SPIKE-01 | Sharing experiments: share, remove access, disable resharing, read the share list, wrong account; record 7-day Testing-mode expiry behaviour | `spikes/s01/ShareLab.kt` |
| 23 | SPIKE-01 | Fallback: one-time file picker with per-file access; same-owner reinstall rediscovery (AC 74); evidence record | `spikes/s01/PickerFallback.kt`, `docs/evidence/spike-01-<date>.md` |
| 24 | SPIKE-02 (SCRUM-40), after 23 | Create marked Sheet (`ExpenseTracker Shared –` prefix, `_meta` tab), invitee discovery and automatic `joined` row, owner processing | `spikes/s02/MarkedSheet.kt`, `AutoJoin.kt` |
| 25 | SPIKE-02 | Removal path: `removing`, access removal, permission confirmation, `ended`; leave path; partial failure (access removed, `ended` not written); owner offline | `spikes/s02/Removal.kt`, `Leave.kt` |
| 26 | SPIKE-02 | Negative cases: wrong account, Sheet shared by an unexpected owner, second member, second `joined` row; evidence record | `spikes/s02/NegativeCases.kt`, `docs/evidence/spike-02-<date>.md` |
| 27 | SPIKE-03 (SCRUM-20), starts first | Collection guide for you: how to export and redact alerts for HDFC, SBI, SBM, Kotak, Axis (SMS and notification), where to put them | `docs/evidence/spike-03-collection-guide.md` |
| 28 | SPIKE-03 | Analyse samples: field extraction table per bank and channel, real package names from `adb shell pm list packages` on both phones | `docs/evidence/spike-03-matrix-<date>.md` |
| 29 | SPIKE-03 | Supported matrix, statement and alert-log ground truth, fixture and live sample plan; proposal for D-12 numbers for your approval | `docs/evidence/spike-03-d12-proposal.md` |

### Phase 4: Gate 0 review (tasks 30 and 31)

| # | Task | Files |
| --- | --- | --- |
| 30 | Gate 0 summary: each spike pass, blocked or changed; list requirement revisions needed (D-02 scopes, AC 74, D-11, D-12) | `docs/evidence/gate-0-summary.md` |
| 31 | Apply approved requirement and backlog changes, re-validate, sync Jira and Confluence (only with your approval) | requirements, backlog |

## 5. Order and parallel work

| Parallel group | Tasks | Why |
| --- | --- | --- |
| A, can start now | 27 (SPIKE-03 guide) and 1 to 4 | No dependency; SPIKE-03 waits on your samples |
| B | 5, 6, 7 | Independent files, tests first |
| C | 15 to 17 (SPIKE-04) and 18 to 20 (SPIKE-05) | Both blocked by nothing; need phones |
| D | 21 to 23 (SPIKE-01) | Needs P2, P3 |

| Sequential | Depends on | Why |
| --- | --- | --- |
| 2 | 1 | Module needs the project |
| 8, 9, 10 | 3, 4, 7 | Screens need theme and hero logic |
| 12 | 1, 2 | Spikes module needs the project |
| 15 to 26 | 12 | Spikes live in the harness |
| 24 to 26 (SPIKE-02) | 21 to 23 (SPIKE-01) | Backlog: SPIKE-02 is blocked by SPIKE-01 |
| 28, 29 | 14, 27 and your samples (P5) | Need real data |
| 30 | all spikes | Summary |

Backlog gating (unchanged): TASK-101 waits for SPIKE-05; TASK-108 waits for SPIKE-04 and SPIKE-05; the five parsers wait for SPIKE-03; sharing stories wait for SPIKE-01 and SPIKE-02.

## 6. Testing plan

| Layer | Tests | Ties to |
| --- | --- | --- |
| Money (JVM) | Grouping, paise shown only when non-zero, negative, zero, very large amounts, spoken labels | FBK 01, BUD 02, AC 64 |
| Hero (JVM) | Sum of limits, left, `₹0` label vs over-budget label, zero limit, no limits, pending line, refund restores level, Pocket inputs and boundaries (60, 30 inclusive, exact zero) | BUD 02, CAT 02, AC 37, 38, 52; handoff `01` and README section 8 |
| Redaction tool (Python) | OTP removal, 10 to 16 digit sequences, UPI handles, names, emails, idempotence | RAW 01, AC 51 |
| Compose | Previews of six hero states in light and dark; 200% font size screenshot check; TalkBack labels on hero | NFR 02, AC 65 |
| SPIKE-04 (device) | Permission matrix on both phones, background receipt after force stop and reboot, restricted-setting path | CAP 01, CAP 02, AC 31, AC 66 |
| SPIKE-05 (instrumented) | Restart persistence, encrypted file unreadable without key, export then restore on a clean install | DAT 01, REC 01, AC 19, 20, 42 |
| SPIKE-01 and 02 (two accounts) | Scopes, listing, share and removal, negative cases, partial failure, reinstall rediscovery | MEM 01 to 03, SEC 01, AC 24, 43, 48, 49, 69, 74 |

Evidence for every spike is a dated record in `docs/evidence/` with device, OS build, app build, steps and pass or block outcome. A written proposal alone does not pass a spike.

## 7. Risks and fallbacks

| Risk | Effect | Fallback |
| --- | --- | --- |
| Google refuses broad Drive listing or approval is needed | Automatic join cannot discover Sheets | One-time file picker with per-file access (already allowed by D-02); revisit the join UX |
| OAuth Testing mode expires refresh tokens after 7 days | Silent sign-out of sharing | Detect and re-authorise without deleting data; document consent configuration |
| Restricted settings block notification access on a sideloaded APK | No notification capture | Show the Android steps only when needed; SMS capture and manual entry still work |
| Samsung battery management stops background receipt | Missed alerts | Record behaviour; guide users to battery settings; measure receipt rate separately (NFR 02) |
| Room/SQLCipher version conflict on API 36 | Encrypted DB blocked | Pin compatible versions in SPIKE-05; evaluate the supported alternative before TASK-101 |
| DM Sans lacks a clean `₹` or tabular figures | Wrong amounts shown | Fallback system sans for the glyph; decide after task 5 |
| Compose BOM `2024.09.00` in the Pocket module is old | Lint warning, API drift | Bump to a current stable BOM together in task 1, re-run Pocket's 10 unit tests |

## 8. Open decisions for you

1. **applicationId and app name** (P1).
2. **Where Pocket lives for the build.** The module is untracked, and its licence is undecided. Options: keep including it by path (a fresh clone will not build until you add the folder), or build with a placeholder Pocket interface until the licence is decided.
3. **Do you want the scaffold in Jira?** It is not in the backlog. I would not create issues without your approval.
4. **Which real phone first for SPIKE-04** (S25 Ultra or Z Flip 5).

## 9. Gate 2 checklist

**Architecture**
- [x] Follows the stack in ARC 01 and D-08; layers are UI → domain → data; no backend introduced
- [x] Each layer only calls the layer below it (stated in section 3)
- [x] Components sit in the directories listed in section 3

**Task breakdown**
- [x] New files listed with locations; existing files to change listed
- [x] Each task is small (one commit, at most three files)
- [x] Dependencies marked, including the backlog's blocked-by relations
- [x] Parallel and sequential work marked

**Testing**
- [x] Domain logic tests planned and written first
- [x] Spike evidence planned for every spike
- [x] UI checks planned (previews, 200% font, TalkBack)
- [x] Edge cases from the requirements are covered in the test plan

**Traceability**
- [x] Every task traces to a requirement, a backlog issue or the design handoff; tasks 1 to 11 and 12 to 14 are enabling work not in the backlog (flagged in open decision 3)

**Next:** approve this plan, then I start with tasks 1 to 4 and task 27 in parallel; build tasks follow `/spartan:build`.
