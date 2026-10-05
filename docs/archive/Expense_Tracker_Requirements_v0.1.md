# Expense Tracker Requirements

**Android application for personal and shared monthly budgets**  
Version 0.1 | 27 September 2026 | Prepared for Sanyam Koul

## 1. Purpose and product outcome

Build an Android expense tracker that automatically captures transactions, places eligible expenses into monthly category budgets, and tells the spender how much remains. Each user starts with a personal budget. When another user accepts an invitation, the owner's existing budget becomes shared between the two. The owner then has only that shared budget; the invited user retains their separate personal budget. Removing the invited user returns the owner's budget to personal use. Records remain on the devices, with Google Sheets as the preferred shared storage and analysis destination.

The app tracks payments made through existing payment services. Payment initiation, bank credentials, lending, investment advice, iOS support, and joint-account attribution are outside the proposed first release.

### 1.1 Confirmed baseline

| Area | Agreed requirement |
| --- | --- |
| Users and devices | Initially two users, both on Android, with separate bank accounts and cards. |
| Intended use | Personal use for the initial two users. A public Play Store release is outside the current scope. |
| Product roles | The product is role-neutral and designed for general users. Any user can create a personal budget, become an owner by inviting someone, or accept an invitation as the invited member. The two pilot users and devices are validation examples, not fixed roles. |
| Functional-first scope | Capture, classification, budgets, membership, permissions, synchronization, privacy, recovery, and correctness are in scope for requirements now. UI layout, navigation defaults, visual styling, and interaction design are deferred until functionality is agreed. |
| Personal budget count | Each user has one personal budget in the first version. Any user may create it, manage their own limits, and later participate in another user's shared budget. |
| Manual expense entry | Amount, date, and category are required. Merchant, account, payment method, and notes are optional. Manual entry supports cash purchases and alerts with incomplete details. |
| Authentication | Require Google account sign-in for each user. Use the authenticated identity for invitations, shared-Sheet access, ownership, membership, and revocation. Never collect bank login credentials. |
| Scope of money flows | Track expenses only. Do not budget salary, income, or unrelated credits in the first version. Process credits only when they are confirmed refunds or reversals linked to an expense. |
| Historical budget limits | Past-month category limits are read-only. Users may configure current and future limits; changing a future month never rewrites past limits or historical totals. |
| No-limit categories | Track spending in categories without a configured limit and label them “No limit.” Do not treat them as over budget or trigger percentage alerts for them. |
| Over-budget expenses | Allow expenses above a configured limit. Continue recording them, show a negative remaining amount, and label the category “Over budget.” |
| Date attribution | Use the bank-provided occurrence date for automatic transactions. If unavailable, use received time and flag the record for review. Manual expenses use the date entered by the user. Apply month attribution in Asia/Kolkata. |
| Deduplication | Prefer transaction reference plus account context. Use amount, merchant, and time only as supporting evidence. Equal amounts alone never merge transactions. Consolidate repeated SMS, notifications, imports, and sync retries. |
| Local performance target | On the pilot devices, persist a supported alert and update local totals and feedback within 2 seconds after the alert reaches the app. Measure network synchronization separately. |
| Local data protection | Encrypt the local database and use Android-protected key storage for encryption keys. Protect transactions, budgets, pending sync changes, memberships, and personal data on the device. |
| Revocation and cache cleanup | Removing or leaving a shared budget revokes access and stops new shared writes online. On the departing user's next connection, remove inaccessible shared data from the app cache. Offline copies cannot be remotely guaranteed to disappear until reconnecting. |
| Post-removal corrections | Freeze retained shared history after removal or departure. The former member may correct their personal copy, but changes affecting retained shared totals go to an owner review queue and do not reopen shared access. |
| Budgeting | Monthly limits for categories such as entertainment, fashion, travel, and utilities. |
| Budget defaults | INR, calendar months, Asia/Kolkata, and no rollover of unused amounts or overspending. Each month starts with its configured limits; previous-month spending remains in history. |
| Sharing lifecycle | Acceptance of an invitation converts the owner's existing personal budget into a shared budget between the two users. Removal converts it back to personal. |
| Budget ownership | During sharing, the owner has only the converted shared budget. The invited user retains their separate personal budget and also participates in the owner's shared budget. |
| Expense attribution | The owner's eligible expenses count once in their budget, whether personal or shared. When a user has both a personal and a shared budget, their eligible expenses count once in each. |
| History after removal | Retain the removed user's expenses from the shared period in the owner's budget and its totals. Removal alone does not change historical spending. The removed user's new expenses affect only their own personal budget. |
| Continuity on acceptance | Keep the owner's existing category limits and current-month spending when the budget becomes shared. The invited user's expenses contribute from acceptance onward; their earlier expenses are not added retroactively. |
| Refund month | A confirmed refund linked to a purchase adjusts the original purchase month's spending. A refund received in a later month does not increase that later month's available budget. |
| Category-limit permissions | Only the owner can change shared category limits. The invited user can view those limits and manage their own separate personal limits. |
| Transaction-edit permissions | Each user can correct only their own transactions, including category, amount, and date. Corrections update every affected budget and retain a change history. Budget ownership does not grant permission to edit another user's transactions. |
| Spreadsheet interaction | The app automatically reads and writes Google Sheets for synchronization. Users make transaction and budget changes through the app; manual spreadsheet-cell edits are unsupported initially. Other members see shared updates in their own app after synchronization. |
| Single-category expenses | Each classified expense belongs to exactly one category for its full amount. Expenses must not be split across categories. Use the agreed initial list in CAT 01; unknown categories use the Uncategorized review bucket. |
| Category configuration | The first version uses the same fixed category list for personal and shared budgets, with independently configured limits. Custom categories are deferred to BL 02. |
| Capture | Automatic transaction detection is essential. Cover UPI, net banking, and card payments. |
| Initial banks | HDFC, SBI, Kotak, Axis, and SBM. Support requires validation of actual alert formats. |
| First pilot bank coverage | HDFC, SBI, SBM, Kotak, and Axis Bank. Validate actual SMS and notification formats for the accounts and payment channels used in the personal pilot before calling any combination supported. |
| First pilot capture methods | Use financial SMS capture and supported bank/payment-app notification capture for UPI, card, net-banking, and auto-debit alerts. Provide manual entry when an alert cannot be captured or classified. |
| Invitation method | Invite users through their Google account email. The recipient must accept before the owner's budget becomes shared. |
| Owner assignment | The user who creates the first budget is its owner. There is no separate administrator role in the first version. |
| Member departure | The invited user may leave the shared budget voluntarily. Leaving has the same effect as owner removal: the owner's budget becomes personal, shared-period expenses remain in the owner's history, and the departing user's new expenses affect only their personal budget. |
| Re-invitation | The owner may invite a previously removed or departed user again. Rejoining creates a new membership interval; expenses during the gap are not added retroactively. |
| Membership size | The first version supports exactly two users per budget: one owner and at most one invited member. Multiple members and multiple shared groups are out of scope. |
| Permission onboarding | Onboarding explains and requests SMS and notification access. It shows capture readiness, permission status, last successful capture, and recovery guidance when access is denied or revoked. |
| Pilot devices | Samsung Galaxy S25 Ultra on Android 16 and Samsung Galaxy Z Flip 5 on Android 16. These are validation devices only; either person can create a budget or accept an invitation. Validate capture, battery behavior, permissions, and sync on both devices. |
| Currency scope | The first version supports INR only. Foreign-currency transactions are excluded until exchange-rate and reporting rules are defined. |
| Pilot installation | Install privately as an APK on the two Android phones. Play Store distribution is outside the current scope; future APK updates will be installed privately. |
| Raw alert retention | Discard raw SMS and notification text after successful parsing. Retain only extracted transaction fields and limited parser metadata needed for troubleshooting. OTPs and unrelated messages are never retained. |
| Historical import | Do not import historical SMS or notification messages in the first version. Capture begins after setup; older expenses can be entered manually. |
| Transaction deletion | Use soft deletion. A deleted transaction leaves a deletion marker and change history for synchronization, is removed from budget totals and normal views, and can be restored if policy permits. Provide a separate full local-data wipe in Settings. |
| Export and restore | Include app-managed export and restore in the first version. Preserve stable transaction, budget, membership, and change-history IDs; exclude raw SMS and notification text, OTPs, and unrelated messages. |
| Export protection | Password-protect export files. Require the password during restore; do not include bank passwords, card PINs, OTPs, raw SMS, raw notification text, or unrelated messages. |
| Notification privacy | Hide expense details on the lock screen by default. Show a generic notification until the device is unlocked; users may change this setting. |
| Warning thresholds | Default alerts occur at 80% and 100% of a category limit, once per threshold crossing. The owner controls shared-budget thresholds; each user controls thresholds for their own personal budget. |
| Google Sheets storage | Use one shared Google Sheet for the owner's shared budget. Keep each user's personal budget private in the app; do not write personal-only data to the shared Sheet. Personal data is protected through password-protected export and restore. |
| Sync triggers | Sync shared changes when connectivity returns, when the app opens, and when the user taps Refresh. Show pending, synced, failed, access-revoked, and stale-data states. Continuous background sync is not promised for the first version. |
| Shared Sheet setup | The app creates and manages the shared Google Sheet automatically. The owner authorizes creation when sending an invitation; the invited user receives access after accepting. The app manages access changes and revocation. |
| Shared Sheet viewing | Both users may view the shared Sheet. App-managed source tabs remain read-only to users; human-readable reporting tabs may be viewed for analysis. All supported edits happen through the app. |
| Shared reports | The shared Sheet includes an app-managed transaction ledger and monthly summaries for category limits, spent, remaining, over-budget categories, and each user's contribution. |
| Account mapping confirmation | Prompt the user to confirm every newly detected bank account or card before assigning transactions to an owner. Do not rely on matching last four digits alone. |
| Classification review | Automatically assign categories only at high confidence. Send low-confidence merchants, ambiguous transfers, unsupported alerts, and possible duplicates to the review inbox before final category assignment. Review-inbox items remain pending and do not affect budget totals until confirmed. |
| Transfer and repayment handling | Exclude own-account transfers, transfers between the two users, and credit-card repayments from expense totals. Treat ambiguous UPI payments as review items until intent is confirmed. |
| Transaction lifecycle | Count only confirmed posted payments in budgets. Keep pending payments visible for review, count failed payments as zero, and apply reversals/refunds to the original purchase. |
| Data preference | Local data storage or, preferably, Google Sheets to combine and review household spending. |

### 1.2 Requirement status

The table above and the decision log in Section 8.1 record the confirmed baseline and subsequent agreements. Detailed rules that explicitly identify an agreed decision reflect those agreements; the remaining rules, screens, acceptance criteria, and first-release boundaries are proposed elaborations for review. Open decisions are listed in Section 8. A proposed default is not a previously approved product decision.

## 2. Budgets and membership

### BUD 01 - Budget structure

Agreed lifecycle: each user has one personal budget in the first version. Any user may create and manage their personal budget. The user who creates a budget is its owner and can invite User B to it. Until B accepts, the owner's budget remains personal. Acceptance converts the owner's existing budget into a shared budget for both users; the owner does not retain a second personal budget. B retains B's separate personal budget. When the owner removes B, the owner's budget becomes personal again and B keeps B's own personal budget. Either person can be the owner or invited member in different sharing relationships, subject to the first-version two-user limit. There is no separate administrator role in the first version.

A budget contains category limits; it is not a separate budget for each transaction. The first version supports one owner and at most one invited member per budget. Multiple members and multiple shared groups are out of scope. Proposed implementation: retain the same budget ID through personal-to-shared and shared-to-personal transitions. Reciprocal invitations and multiple simultaneous shared memberships are out of scope for the first version.

### BUD 02 - Monthly calculation

Calculate category remaining as the applicable monthly limit minus net eligible spending. Refund adjustments reduce net spending. Display negative remaining amounts as an overage; do not block recording an expense.

### BUD 03 - Expense attribution

Store one logical transaction with a stable ID and count it at most once in each applicable budget. A's eligible expenses count in A's budget, which is shared while B is a member. Do not create a second personal-budget attribution for A during sharing, and do not charge A's expenses to B's personal budget.

B's expense routing is agreed: while B has both a personal and a shared budget, each eligible expense automatically counts once in B's personal budget and once in A's shared budget. These remain two budget attributions of one purchase. Never add the two budget totals together as if they represented different purchases.

### BUD 04 - Category consistency

Agreed: personal and shared budgets use the same fixed category list in CAT 01, with independently configured limits. Custom categories are deferred to BL 02. Use stable category IDs and consistent mappings across budgets. Agreed first-release budget defaults are INR, calendar months, and Asia/Kolkata. Store money in integer paise.

### BUD 05 - History and limits

Keep month-specific budget values. Past-month category limits are read-only. Users may configure current and future limits; changing a future month must not rewrite past limits or historical totals. No rollover is agreed for the first release: unused amounts and overspending stay in the previous month's history, and each month starts with its configured limits. Allow expenses above the limit, continue recording them, show a negative remaining amount, and label the category “Over budget.” Track categories without a configured limit, label them “No limit,” and do not trigger percentage alerts for them.

### MEM 01 - Invitation and roles

The owner invites another user through the user's Google account email. Acceptance begins sharing and changes that budget to shared; an unaccepted invitation leaves both budgets unchanged. Removal returns it to personal use. Agreed limit permissions: only the owner can change shared category limits; the invited user can view those limits and manage their own separate personal limits. The owner manages invitations and removal. Agreed transaction permissions: each user can correct only their own transactions, with changes reflected in every affected budget and recorded in change history. The budget owner cannot edit the invited user's transactions, and the invited user cannot edit the owner's transactions. Categories are fixed for the first version; future custom-category permissions belong to BL 02. After access is revoked, retained shared history is frozen and any later correction that would change it goes to owner review without reopening access.

### MEM 02 - Persistent sharing

The converted budget remains shared until the owner removes the invited user or the invited user leaves voluntarily. The owner uses it as their only budget; the invited user's own personal budget remains separate. An invited user with both budgets has eligible expenses automatically tracked in both, without a per-transaction sharing step. The invited user's separate personal limits and pre-membership personal history are not merged into the owner's budget. The proposed visibility rule for the owner's pre-sharing transaction details is deferred to backlog item BL 01.

### MEM 03 - Membership boundaries

Acceptance starts sharing, and removal returns the budget to the owner's personal use. Agreed retention policy: keep the removed user's expenses from the shared period in the owner's budget and its totals. Removal alone must not delete those expenses, subtract them from spending, or restore the amounts they consumed from the current month's budget. The removed user's new expenses affect only their own personal budget; their own personal records remain intact.

Agreed conversion policy: keep the owner's existing category limits and current-month spending when their budget becomes shared. Acceptance does not reset spending or automatically change limits. The invited user's expenses contribute from acceptance onward; their earlier expenses are not added retroactively.

Agreed boundary policy: determine eligibility using the transaction's attributed occurrence date within an accepted membership interval, revoke shared access on removal or voluntary departure, and treat rejoining as a new interval. Expenses during a membership gap are not added retroactively. Use the bank-provided occurrence date when available; otherwise use received time and flag the record for review. Manual expenses use the date entered by the user. Apply month attribution in Asia/Kolkata. Delayed captures and post-removal corrections or refunds that require server-backed reconciliation are deferred to BL 03. Corrections while membership is active follow CAT 03. The proposed visibility rule for the owner's pre-sharing transaction details is deferred to BL 01. Keeping earlier spending in totals does not by itself settle access to individual transaction details.

### MEM 04 - Late and offline events

Offline captures during an active membership can sync through the agreed app and Sheet flow. Server-backed reconciliation for delayed alerts, membership-boundary cases, and post-removal corrections or refunds is deferred to BL 03. Cached membership must not permit new shared writes after access is revoked. Flag uncertain transaction dates and unresolved boundary cases rather than silently assigning them.

### 2.1 Agreed budget lifecycle example

| Event | User A, the inviting owner | User B, the invited user |
| --- | --- | --- |
| Before acceptance | A's personal budget | B's personal budget |
| B accepts A's invitation | A's budget becomes shared; A has no separate personal budget | B keeps their personal budget and also participates in A's shared budget |
| A removes B | A's budget becomes personal again | B keeps their personal budget and loses membership in A's budget |

### 2.2 Invited-user expense example

Under the agreed attribution rule, B's purchase counts in both budgets. A has no separate personal budget while A's budget is shared. The example limits and amounts are illustrative.

| Budget | Limit | Spent before | Purchase | Remaining |
| --- | ---: | ---: | ---: | ---: |
| B's personal budget | INR 5,000 | INR 1,000 | INR 1,000 | INR 3,000 |
| A's converted shared budget | INR 8,000 | INR 3,000 | Same purchase by B | INR 4,000 |

## 3. Automatic transaction capture

### CAP 01 - Sources and permissions

Use financial SMS and supported bank or payment-app notifications with explicit Android permissions. Onboarding explains and requests both permissions, then shows capture readiness and permission status. Process incoming content on the device. Notification capture and SMS capture are distinct integrations; availability of one does not establish coverage for the other.

### CAP 02 - Bank coverage

Provide independently maintainable parsers for HDFC, SBI, Kotak, Axis, and SBM, producing a common transaction record. A fallback parser may suggest a record but must mark uncertain extraction for review. Do not label a bank or channel supported until its relevant samples pass validation.

### CAP 03 - Extracted fields

Extract the following where present:

- amount
- currency
- debit or credit direction
- merchant or payee
- bank
- masked account or card identifier
- transaction reference
- occurrence time

Preserve received time separately. Missing information must remain unknown rather than being invented.

### CAP 04 - Account mapping

Map separate accounts and cards to their owner. Prompt the user to confirm every newly discovered account or card before assigning transactions. Do not confuse masked identifiers from different banks or assume the same last four digits identify one account. Bank balances are not budget balances.

### CAP 05 - Deduplication

Consolidate multiple alerts for the same payment across SMS, app notifications, repeated delivery, import, and sync retries. Prefer transaction references plus account context; use amount, merchant, and time only as supporting evidence. Equal amounts alone never merge transactions. When evidence is insufficient, keep records separate or send the possible duplicate to review rather than silently merging.

### CAP 06 - Capture health

Show permission status, last successful capture, and unsupported alerts requiring review. Continue local capture without internet where the OS permits it. Explain unavailable capture when permissions are revoked or Android restricts execution. Never promise detection before an alert reaches the device.

### 3.1 Initial channel handling

| Channel | Initial target | Important handling |
| --- | --- | --- |
| UPI | Bank alerts and supported UPI-app notifications | Distinguish merchant payments from transfers. SMS and notification capture are both in scope where available; manual entry is the fallback. |
| Debit and credit cards | Purchase and reversal alerts | Count the purchase, not a later card repayment. |
| Net banking | Debit or transfer alerts | Recipient and purpose may require review. |
| Auto-debits | Supported bank or card alerts | Count an actual debit, not a reminder. |
| Cash | Manual purchase entry | A withdrawal is not itself a purchase. Manual entry requires amount, date, and category; merchant, account, payment method, and notes are optional. |

### 3.2 Capture validation dependency

Collect redacted examples for each available bank and channel, including purchases, credits, reversals, failed attempts, and duplicates. During normal operation, discard raw SMS and notification text after successful parsing. Retain only extracted transaction fields and limited parser metadata needed for troubleshooting; never retain OTPs or unrelated personal messages. Generic support for every bank format is not a release promise.

For the first personal pilot, prioritize HDFC, SBI, SBM, Kotak, and Axis Bank. Validation covers only the accounts, cards, UPI apps, and net-banking channels actually used by the two users. Capture begins after setup; historical SMS and notification import is excluded from the first version, with manual entry available for older expenses. Other banks or channels remain future support until their alert formats are validated.

## 4. Classification and transaction feedback

### TXN 01 - Transaction lifecycle

Agreed lifecycle: distinguish pending, posted, failed, and reversed activity. OTPs, payment requests, and promotional messages must not create expenses. Only confirmed posted expenses affect spending; pending records remain visibly pending review, failed payments contribute zero, and reversals/refunds adjust the original purchase when linked.

### TXN 02 - Transfers and repayments

Agreed: exclude own-account transfers, transfers between the two users, and credit-card repayments from expense totals. Track expenses only; salary, income, and unrelated credits do not affect budgets in the first version. Process a credit as a budget adjustment only when it is a confirmed refund or reversal linked to an expense. Do not treat every debit as spending or every credit as a refund. A person-to-person UPI payment may still be a purchase; ambiguous intent enters the review inbox until the user confirms whether it is a purchase or transfer.

### TXN 03 - Refunds and reversals

Link confirmed refunds to the original expense when possible, including partial refunds. Do not treat unrelated credits as refunds. Agreed month policy: a linked refund reduces spending in the original purchase month. For example, an October refund for a September purchase reduces September spending without increasing October's available budget. This changes net spending, not the original month's configured limit, and does not roll the adjustment into a later month.

Proposed attribution policy: apply the adjustment to the budgets that counted the original expense. Server-backed handling of refunds after membership removal is deferred to BL 03; a separate cash-flow view is deferred for later consideration.

### CAT 01 - Category assignment

Use bank and merchant rules plus remembered user corrections. Automatically assign a category only when confidence is high. Send low-confidence merchants, ambiguous transfers, unsupported alerts, and possible duplicates to the review inbox. Review-inbox items remain pending and are excluded from budget totals until the user confirms the transaction and category. A merchant such as Amazon cannot reliably establish the purchased category. Do not silently force low-confidence assignments into a category.

Agreed initial categories:

- Groceries
- Dining and food delivery
- Transport
- Travel
- Fashion
- Entertainment
- Rent and housing
- Utilities and bills
- Health
- Household items
- Other

Uncategorized is the review bucket for expenses awaiting classification. It is distinct from Other, which is a category for classified expenses that do not fit the named categories. The initial list is fixed and common to personal and shared budgets in the first version. Custom-category support and its management permissions are deferred to BL 02.

### CAT 02 - Uncategorized spending

A confirmed expense with an unknown category counts in overall spending in each applicable budget but appears in an Uncategorized bucket until reviewed. Category balances must indicate that uncategorized spending remains. On classification, update all applicable budgets without creating another expense. The owner has one applicable budget; an invited user with both budgets has the expense tracked in both.

### CAT 03 - Corrections

Agreed: allow users to correct only their own transactions, including category, amount, and date. Apply corrections to every affected budget and retain change history. A budget owner has no override to edit another user's transactions. Corrections must adjust old and new category or month totals consistently without creating an extra expense. Use soft deletion: a deleted transaction is removed from budget totals and normal views while its deletion marker and change history remain available for synchronization and recovery. A separate full local-data wipe is available in Settings. After removal or departure, freeze retained shared history: the former member may correct their personal copy, but any change affecting retained shared totals goes to an owner review queue and does not reopen shared access.

Agreed category rule: assign the full expense amount to one category from the initial list in CAT 01. Do not split an expense across categories. Changing its category moves the full amount from the old category to the new category in every applicable budget. Unknown categories remain in the Uncategorized review bucket until classified.

Also propose correction of transaction type and retention of original detection details locally where needed for troubleshooting.

### FBK 01 - Per-transaction feedback

After a classified transaction is recorded, show amount, merchant, category, and the remaining category balance in each applicable budget. For the owner during sharing, show the shared balance only. For an invited user with both budgets, show personal and shared balances. For a user with only a personal budget, show the personal balance only. For unknown categories, request a quick category choice before showing that category's remaining amount.

### FBK 02 - Threshold alerts

Support configurable warning thresholds with agreed defaults at 80 percent and 100 percent. Trigger on crossing a threshold and avoid repeating the same warning because of duplicate alerts or sync retries. The owner controls thresholds for the shared budget; each user controls thresholds for their own personal budget. Define zero-limit and no-limit categories without division errors.

### FBK 03 - Freshness and controls

Label shared balances with last-sync information when other members' recent spending may be missing. Provide change-category and review actions. Hide expense details on the lock screen by default and show a generic notification until the device is unlocked; allow users to change this preference. Do not promise immediate cross-device notifications while both apps are closed.

### 4.1 Example notification

`INR 850 at PVR - Entertainment. Personal remaining INR 2,150. Shared remaining INR 4,650. Shared balance last synced at 11:30. Change category.`

Example amounts are illustrative. This notification illustrates an invited-user expense tracked in both budgets. For the owner during sharing, show only the shared balance; for a user with only a personal budget, show only the personal balance.

## 5. Storage, synchronization, and privacy

### DAT 01 - Local operation

Persist transactions, limits, classifications, memberships, and pending changes on the phone. Recording and budget calculations must work offline. Encrypt the local database and use Android-protected key storage for encryption keys. Never collect bank login passwords, card PINs, or OTPs. Discard raw SMS and notification text after successful parsing; retain only extracted transaction fields and limited parser metadata needed for troubleshooting.

### DAT 02 - Google Sheets separation

Use one shared Google Sheet as the shared record and reporting destination for the owner's converted shared budget. The app creates and manages this Sheet automatically: the owner authorizes creation when sending an invitation, and the invited user receives access after accepting. The app manages access changes and revocation. Keep each user's separate personal limits, personal-only history, and personal-only transactions private in the app; do not write personal-only data to the shared Sheet. Conversion must not create a second personal budget for the owner. App-managed shared projections are linked by stable transaction and budget IDs. A private transaction copy is not a separate personal budget. Password-protected export and restore covers personal data backup. The detailed pre-sharing history visibility rule is deferred to BL 01; no such rule is approved by this storage proposal.

### DAT 03 - Logical identity

A transaction may have a private record and a shared projection for access separation, but remains one logical purchase. Edits, deletion markers, and refund links must converge using the same identity. Shared data must not expose raw SMS or unrelated notifications.

### SYN 01 - Sync behavior

Agreed sync triggers: queue offline changes durably and sync when connectivity returns and the app can run, on app opening, and on manual Refresh. Retry transient failures without adding duplicate rows. Show pending, synced, failed, access-revoked, and stale-data states. Continuous background sync is not promised for the first version.

### SYN 02 - Conflicts and partial writes

Use record versions or an equivalent mechanism to detect conflicts. Do not silently overwrite conflicting financial edits. Recover when the private write succeeds but the shared write fails. Repeated sync of the same change must produce the same totals.

### SYN 03 - Sheet editing

Agreed: the app automatically reads and writes Google Sheets to synchronize expenses, corrections, and budget limits. Both users may view the shared Sheet, including human-readable reporting tabs. The shared Sheet includes an app-managed transaction ledger and monthly summaries for category limits, spent, remaining, over-budget categories, and each user's contribution. Users make transaction and budget changes through the app, which applies the agreed permissions before syncing. Other members see shared updates in their own app after synchronization; personal-only records remain separate. App-managed source tabs are read-only to users and manual spreadsheet-cell edits are unsupported initially. This restriction does not prohibit the app's automated spreadsheet writes.

Proposed layout: app-managed source tabs and separate human-readable reporting tabs. Detect incompatible schema or missing data and show a recovery issue. Both users may view the shared Sheet and its reporting tabs; app-based visibility of shared data does not depend on users opening Sheets.

### SEC 01 - Access and membership

Require Google account sign-in for each user. Use authenticated Google access with the narrowest viable file permissions. Invitation acceptance, membership changes, and revocation must be enforceable, not just hidden UI controls. Access to a shared file must not grant access to another member's private file. Never collect bank login credentials, card PINs, or OTPs.

### SEC 02 - Revocation limits

Agreed: remove a departing member's shared access and stop future sync. The app must remove inaccessible cached shared data when it learns of revocation, including on the departing user's next connection. Offline copies and previously exported copies cannot be remotely guaranteed erased until the device reconnects or the user deletes them.

### NFR 01 - Correctness and recovery

Persist a transaction before confirming it was recorded. Keep totals stable through app restarts, duplicate delivery, and retry. Include an app-managed export and restore path in the first version with identity-preserving deduplication. Password-protect export files and require the password during restore. Export transactions, budgets, memberships, categories, and change history, but exclude bank passwords, card PINs, OTPs, raw SMS, raw notification text, and unrelated messages. Test on both users' actual Android devices.

### NFR 02 - Performance and accessibility

Agreed first-version target: on the pilot devices, persist a supported alert and update local totals and feedback within 2 seconds after the alert reaches the app. Measure bank alert delay and network synchronization separately. Use readable text, screen-reader labels, and status indicators that do not rely only on color.

### Architecture constraint

Manual spreadsheet editing is outside the agreed user workflow, but marking it unsupported does not enforce file permissions. Direct spreadsheet edit permissions may allow changes beyond app roles. The implementation must resolve how automated writes and authenticated access enforce the agreed owner/member restrictions before claiming those restrictions are enforced. Google Sheets is cloud storage, not strictly local storage.

## 6. Screens and logical data model

### 6.1 Essential screens

| Screen | Required user outcome |
| --- | --- |
| Onboarding and accounts | Explain permissions, confirm detected accounts, connect Sheets, and show capture readiness. |
| Overview | Show the owner's single budget as personal or shared according to its current state. An invited user with both budgets can switch between them. Show monthly category limit, spent, remaining, and sync freshness. |
| Transactions | Filter by month, member, category, account, method, and review status; inspect one logical payment. |
| Review inbox | Resolve low-confidence or unknown categories, uncertain extraction, possible duplicates, unsupported alerts, and ambiguous transfers before final classification. |
| Budgets and members | Set category limits and warnings; invite another user to an existing personal budget; show pending acceptance, conversion to shared, and conversion back to personal after removal. Explain sharing scope. |
| Transaction details | Correct own records, assign or change the single category for the full expense, link refunds, and inspect changes. Manual entry requires amount, date, and category; merchant, account, payment method, and notes are optional. |
| Settings and recovery | Manage SMS and notification permissions, account mapping, notification privacy, sync, exports, soft-deleted records, and full local-data wipe. |

### 6.2 Logical entities

| Entity | Essential fields or relationships |
| --- | --- |
| User and account | User ID; display name; account ID; bank; account/card type; masked identifier; owner ID. |
| Budget and category limit | Budget ID; owner; current personal/shared state; state-change history; currency; timezone; category ID; month; amount; warning settings. Proposed stable budget ID across conversion. |
| Membership | Budget ID; user ID; role; invite state; accepted time; ended time; membership version. |
| Transaction | ID; owner; account when known; amount in minor units; currency; attributed occurrence date; original occurrence time when present; received time; date source and review flag; direction; type; status; payee when known; payment method when known; notes; reference. |
| Classification and attribution | One category ID per classified expense, or Uncategorized while awaiting review; review status; confidence; applicable budget IDs and membership intervals. The full expense uses the same category in each applicable budget. One budget attribution for the owner; two for an invited user with personal and shared budgets. |
| Source and adjustment | Source-event fingerprint; parser version; duplicate linkage; original transaction ID for refund/reversal. |
| Change and sync record | Record version; changed fields; actor; time; deletion marker; destination; retry and conflict state. Soft-deleted transactions retain their identity for convergence. |

### 6.3 Data invariants

- Each account has one owner.
- Sharing converts the inviting owner's existing personal budget; it does not give that owner a second budget.
- An invited user retains their separate personal budget; eligible expenses are tracked in both that budget and the shared budget while membership is active.
- Removing the invited user returns the owner's budget to personal use.
- Removal preserves the invited user's shared-period expenses in the owner's budget and totals; new expenses by the removed user affect only their personal budget.
- Each posted purchase contributes at most once to each applicable budget while active; a soft-deleted purchase contributes zero to totals while its identity remains available for synchronization and history.
- Each classified expense has exactly one category for its full amount; unknown categories use Uncategorized until reviewed. Splitting an expense across categories is not supported.
- Failed payments and transfers contribute zero expense.
- Joining a group does not merge private histories.
- Month attribution uses occurrence time in the budget timezone, with uncertain dates flagged.

## 7. Proposed first-release acceptance criteria

These scenarios apply to supported, validated alert formats and the final approved membership and refund policies.

| ID | Scenario and expected result |
| --- | --- |
| AC 01 | A member posts a Fashion expense of INR 1,000. Personal and shared remaining amounts change from INR 4,000 and INR 5,000 to INR 3,000 and INR 4,000. One logical expense exists. [BUD 03] |
| AC 02 | The same purchase arrives by SMS, app notification, and a sync retry. Both budgets count it once. Two real same-amount purchases with different references remain separate. [CAP 05] |
| AC 03 | A owns the converted shared budget and B is the invited member. A's expense changes only the shared budget. B's expense changes both B's personal budget and the shared budget. A has no separate personal-budget view. The shared view includes both users' expenses exactly once and identifies each spender; A's expenses do not change B's personal totals. [BUD 01, BUD 03] |
| AC 04 | A and B initially have separate personal budgets. A's invitation leaves both unchanged until B accepts. Acceptance converts A's budget into a shared budget, leaves A with only that budget, and preserves B's personal budget. Removing B returns A's budget to personal use while retaining B's shared-period expenses in A's budget and totals, including current-month spending. Removal alone changes no spending totals. B's new expenses affect only B's personal budget. Offline boundary records follow the final agreed reconciliation policy. [BUD 01, MEM 01-04] |
| AC 05 | Own-account transfers, a confirmed household transfer, and a card repayment do not increase expense totals. The original card purchase does. [TXN 02] |
| AC 06 | An OTP, payment request, or failed attempt creates no posted expense. A matched partial refund reduces the correct expense and each applicable budget total once: one budget for an owner's expense and both budgets for an invited user's dual-attributed expense, subject to the final refund policy. [TXN 01, TXN 03] |
| AC 07 | An unknown merchant creates an uncategorized posted expense. Selecting Fashion removes it from Uncategorized and updates Fashion in each applicable budget without changing total spending: one budget for the owner and both for an invited user with two budgets. [CAT 02, BUD 03] |
| AC 08 | An offline expense survives restart. Feedback identifies stale shared data; reconnecting syncs once. A partial destination failure recovers without losing the private record. [DAT 01, SYN 01-02] |
| AC 09 | Moving a transaction to another category or month adjusts old and new totals consistently in every applicable budget. A category change moves the full expense amount; it does not change total spending or create another expense. Each expense has only one category, and assigning amounts across multiple categories is not supported. [CAT 03] |
| AC 10 | An expense crosses a warning threshold and later the limit. Appropriate feedback appears once per crossing; over-budget purchases remain recorded. [FBK 01-02] |
| AC 11 | Sharing A's budget does not expose B's separate personal limits or personal-only history to A. Removal returns A's budget to personal use and prevents new shared access/writes by B when online; cached data limitations are disclosed. The proposed rule for B's access to A's pre-sharing transaction details is deferred to BL 01 and excluded from this first-release criterion. [BUD 01, SEC 01-02] |
| AC 12 | Each claimed bank/channel combination passes its fixture set. Permission revocation, unsupported alerts, parsing failures, and sync errors are visible and recoverable. [CAP 02, CAP 06] |
| AC 13 | A has a category limit of INR 5,000 and current-month spending of INR 1,000 when B accepts the invitation. The converted shared budget retains the INR 5,000 limit, INR 1,000 spent, and INR 4,000 remaining. B's earlier expenses are not added. A subsequent eligible expense of INR 500 by B increases shared spending to INR 1,500 and reduces remaining to INR 3,500; it also counts once in B's personal budget. [BUD 03, MEM 03] |
| AC 14 | A September purchase of INR 1,000 receives a linked partial refund of INR 400 in October. In an applicable budget, September spending decreases by INR 400 and September remaining increases by INR 400; its configured limit is unchanged. October spending and available budget do not change because of this refund. Repeated delivery of the refund counts once. [TXN 03, BUD 05, CAP 05] |
| AC 15 | A, the owner, can change a shared category limit. B, the invited user, can view but cannot change that limit, and a rejected attempt leaves it unchanged. B can change B's personal category limit without altering the shared limit. Enforce the shared restriction through all supported write paths, including synchronization. [MEM 01, SEC 01] |
| AC 16 | While sharing is active, B corrects the category, amount, or date of B's own transaction. Both B's personal budget and A's shared budget reflect the correction once, and change history records the edit. A cannot edit B's transaction despite owning the shared budget; B cannot edit A's transaction. Rejected edits leave records and totals unchanged. A's corrections to A's own transactions affect A's budget only. [MEM 01, CAT 03, SEC 01] |
| AC 17 | B records an eligible expense through capture or entry in B's app. The app writes the shared data to the owner's shared Google Sheet automatically. After A's app synchronizes, A sees the expense and updated shared balance without opening or manually editing the spreadsheet. A permitted correction follows the same flow without creating another expense. B's personal-only records and limits remain private and are not written to the shared Sheet; pending synchronization is visible. [SYN 01-03, DAT 02, SEC 01] |
| AC 18 | B soft-deletes B's own shared-period transaction. It disappears from B's personal and A's shared totals and normal views after synchronization, while its stable ID, deletion marker, and change history prevent duplicate recreation. A cannot delete B's transaction. A full local-data wipe is a separate Settings action. [CAT 03, SYN 01-02, NFR 01] |
| AC 19 | A exports the app data, including transactions, budgets, memberships, categories, soft-deletion markers, and change history. Restoring the export on an authorized device preserves stable IDs and totals without creating duplicate expenses. Raw SMS, notification text, OTPs, and unrelated messages are absent from the export. [NFR 01, DAT 01] |
| AC 20 | An export file is password-protected. Restore rejects a wrong or missing password and does not modify local data. The correct password restores the export once, preserves stable IDs, and does not duplicate expenses. Sensitive raw alert content is absent. [NFR 01, SEC 01] |
| AC 21 | A newly recorded expense produces a generic lock-screen notification with no amount, merchant, category, or budget balance by default. After unlocking, the app shows the configured details. Changing notification privacy settings follows the user's choice. [FBK 03, SEC 01] |
| AC 22 | Spending crosses 80% of a category limit and later reaches 100%. The applicable alert appears once at each crossing, remains private on the lock screen by default, and is not repeated by duplicate delivery or sync retry. Shared thresholds can be changed only by the owner; personal thresholds can be changed by that budget's user. [FBK 02, SEC 01] |
| AC 23 | B records a shared expense while offline. B's app shows it as pending and updates B's applicable local totals. When connectivity returns, on app open, or after B taps Refresh, the app syncs it once. A sees the update after A's next app open or Refresh. A stale-data indicator remains until the shared update is received. [SYN 01, FBK 03] |
| AC 24 | The user who creates the first budget is recorded as owner. That user sends an invitation. The app creates the shared Google Sheet after the owner authorizes it. B accepts with the invited Google account and receives shared access. Removing B or B leaving voluntarily revokes shared access and stops future shared writes while preserving retained history. A rejected or pending invitation does not create an active shared budget. No separate administrator role exists. [MEM 01, SEC 01-02, DAT 02] |
| AC 30 | After B leaves or is removed, A can invite B again. Acceptance creates a new membership interval and reactivates shared contributions from that acceptance onward. Expenses during the gap are not added retroactively, and the retained prior shared history remains distinct from the new interval. [MEM 03] |
| AC 31 | Onboarding explains SMS and notification access, requests both permissions, and shows capture readiness. If either permission is denied or later revoked, the app identifies the unavailable source, keeps manual entry available, and provides recovery guidance. Last successful capture is visible. [CAP 01, CAP 06] |
| AC 32 | A user manually records a cash expense with amount, date, and category. The expense is saved and affects each applicable budget once. Merchant, account, payment method, and notes may be left blank. Missing optional fields remain unknown rather than being invented. [CAP 06, CAT 02] |
| AC 33 | The shared Sheet contains an app-managed ledger for shared expenses and monthly reporting views with category limits, spent, remaining, over-budget status, and per-user contributions. Both users can view these reports; personal-only records and limits are absent. A permitted app change updates the ledger and summaries after synchronization. [DAT 02, SYN 03] |
| AC 34 | A user cannot use sharing or shared-Sheet access without Google account sign-in. The authenticated account identifies the budget owner or invited member. Signing out revokes local access until the same or an authorized account signs in again; bank credentials are never requested. [SEC 01, MEM 01] |
| AC 35 | A salary credit or unrelated deposit does not create an expense, refund, or budget adjustment. A confirmed refund or reversal linked to an existing expense adjusts that expense under the original-month rule. An unlinked credit remains outside expense totals and enters review only if needed to resolve its meaning. [TXN 03, BUD 02] |
| AC 36 | A user can configure current and future category limits but cannot edit a past-month limit. Changing a future limit affects only that month and later calculations; past limits and historical totals remain unchanged. [BUD 05] |
| AC 37 | An expense in a category without a configured limit is included in total spending and labeled “No limit.” It does not appear as over budget and does not trigger 80% or 100% percentage alerts. [BUD 05, FBK 02] |
| AC 38 | An expense that exceeds a configured category limit is recorded normally. The category shows a negative remaining amount and “Over budget” status; capture and synchronization are not blocked. [BUD 02, BUD 05] |
| AC 39 | An automatic transaction with a bank occurrence date is attributed to that date and its Asia/Kolkata month. If the occurrence date is missing, received time is used and the record enters review. A manual expense uses the user-entered date. [BUD 04, MEM 03, CAP 03] |
| AC 40 | The same payment delivered by SMS, notification, and sync retry consolidates when transaction reference and account context match. Two real purchases with equal amounts remain separate when their references or account contexts differ. Uncertain matches enter review rather than being silently merged. [CAP 05] |
| AC 41 | On each pilot device, after a supported alert reaches the app, the transaction is persisted and local totals and feedback update within 2 seconds under agreed test conditions. Any network delay is shown separately as pending or stale synchronization. [NFR 02] |
| AC 42 | Inspecting the device database without the app's protected key cannot reveal transaction, budget, membership, or pending-sync contents. The app continues to record and calculate offline, and a restart does not lose encrypted local data. [DAT 01, NFR 01] |
| AC 43 | When B is removed or leaves while online, shared access is revoked and B cannot create new shared writes. When B's app next connects, inaccessible shared records and cached shared projections are removed while B's personal records remain. If B stays offline, the app discloses that cached copies may remain until reconnection. [SEC 02, SYN 01] |
| AC 44 | After B leaves or is removed, B can correct B's personal copy of a prior transaction. The retained shared history remains frozen; any correction or linked refund that would change shared totals enters an owner review queue and does not restore B's shared access. [CAT 03, SEC 02] |
| AC 45 | A backup restore succeeds only when the user provides the export password and is signed in to the same Google account that created the backup. A correct password without the matching account, or a matching account without the correct password, does not restore the backup. [SEC 01-02] |
| AC 46 | Change history remains available for the lifetime of the local dataset and is included in app-managed exports. A soft-deleted transaction keeps its history, while the separate full local-data wipe removes the dataset and its change history. [NFR 01-02] |
| AC 25 | A and B can view the shared Google Sheet and its reporting tabs. Neither can manually edit app-managed source tabs or change transactions, limits, or membership through cells. Permitted changes made in the app appear in the Sheet after synchronization. Personal-only data is absent from the shared Sheet. [SYN 03, SEC 01, DAT 02] |
| AC 26 | A newly detected account or card is held for confirmation. Until the owner confirms the mapping, its transactions are not silently assigned to a personal or shared budget. Two accounts with the same last four digits remain distinct when their bank or account context differs. [CAP 04] |
| AC 27 | A high-confidence supported alert is categorized automatically. A low-confidence merchant, ambiguous transfer, unsupported alert, or possible duplicate enters the review inbox, remains pending, and contributes nothing to budget totals until the user confirms it. After the user resolves it, the full expense is assigned to one category and every applicable budget updates once. [CAT 01-02, CAP 02, CAP 05] |
| AC 28 | An own-account transfer, transfer between A and B, or credit-card repayment increases no expense total. An ambiguous person-to-person UPI payment remains in review until confirmed as a purchase or transfer; only a confirmed purchase affects applicable budgets. [TXN 02, CAT 01] |
| AC 29 | A pending payment remains visible but contributes zero until posted. A failed payment contributes zero. A posted payment contributes once. A linked reversal or refund adjusts the original purchase once, including across a month boundary under the original-month refund rule. [TXN 01, TXN 03] |

## 8. Decisions to finalize before implementation

| Decision | Proposed default or unresolved choice |
| --- | --- |
| Foreign-currency purchases | INR-only support for the first version is agreed. Exchange-rate handling and foreign-currency reporting are deferred. |
| Income and unrelated credits | Expense-only tracking is agreed. Salary, income, and unrelated credits do not affect budgets in the first version. |
| Budget history editing | Past-month limits are locked. Current and future limits can be configured without rewriting historical limits or totals. |
| No-limit category behavior | Track spending and label it “No limit.” Do not trigger percentage alerts without a configured limit. |
| Over-budget behavior | Allow and record expenses above limits, show negative remaining amounts, and label the category “Over budget.” |
| Date and month attribution | Use bank occurrence date when available; otherwise use received time and flag review. Manual entries use the entered date. Apply Asia/Kolkata month boundaries. |
| Deduplication policy | Match reference and account context first; use amount, merchant, and time as supporting evidence. Never merge on equal amount alone. |
| Performance target | Persist supported alerts and update local totals and feedback within 2 seconds after app receipt on the two pilot devices. Measure bank alert delay and network sync separately. |
| Local security | Encrypt local data and protect encryption keys with Android-protected storage. Keep offline recording and calculations available. |
| Revocation behavior | Revoke shared access and stop future writes online; remove inaccessible shared cache on next connection. Disclose offline-cache limitations. |
| Post-removal changes | Freeze retained shared history. Former-member personal corrections are allowed; changes affecting shared totals require owner review without reopening access. |
| Membership boundary events | Retention after removal, preservation of the owner's existing limits and current-month spending at conversion, and invited-user contributions from acceptance onward are agreed. Server-backed occurrence-time reconciliation for delayed captures and late corrections/refunds after the author loses shared access is deferred to BL 03. The proposed pre-sharing history visibility rule is deferred to BL 01. |
| Extended sharing | The personal-to-shared lifecycle, dual tracking for users with both budgets, and two-user membership limit are agreed. Reciprocal invitations, multiple members, and multiple simultaneous shared memberships are out of scope for the first version. |
| Refund attribution and reporting | Adjusting the original purchase month is agreed. Server-backed original-budget reconciliation after membership removal is deferred to BL 03. Decide later whether an additional cash-flow view is needed. |
| Permissions and Sheets | Google account sign-in, owner-only shared-limit editing, own-transaction-only corrections, automated app reads/writes to one app-managed shared Sheet, automatic creation and revocation, both-user read-only Sheet viewing, transaction-ledger and monthly-report views, no supported manual source-tab edits, and privacy of personal-only data are agreed. Confirm enforcement through file access and automated writes. Custom-category permissions are deferred to BL 02. |
| Installation and devices | Personal use, private APK installation, and pilot devices are agreed: Samsung Galaxy S25 Ultra on Android 16 and Samsung Galaxy Z Flip 5 on Android 16. These devices do not assign fixed product roles; either user can be an owner or invited member. Measure battery behavior and alert reliability on both; a public Play Store release is outside the current rollout scope. |
| Recovery and history | Historical SMS and notification import is excluded from the first version. Soft deletion with synchronization markers, password-protected app-managed export/restore, and a separate full local-data wipe are agreed. Restore requires both the export password and sign-in to the same Google account that created the backup. Retain change history indefinitely while the local dataset exists; include it in app-managed exports and remove it only through the full local-data wipe. Raw alert retention is agreed: discard after successful parsing, retaining only extracted fields and limited parser metadata. |

### 8.1 Agreed decisions

| Date | Decision |
| --- | --- |
| 1 October 2026 | The initial rollout is personal use through a private APK for the two pilot users. The product remains role-neutral and general-purpose; the pilot devices do not define fixed roles. A public Play Store release is outside the current rollout scope. |
| 1 October 2026 | Budget defaults are INR, calendar months, Asia/Kolkata, and no rollover. Each month starts with its configured limits; unused amounts and overspending remain in the previous month's history. Foreign-currency purchase handling is deferred beyond the first version. |
| 1 October 2026 | Each user starts with a personal budget. Acceptance of an invitation converts the inviting owner's existing budget into a shared budget between the two users. The owner then has only that shared budget; the invited user retains their separate personal budget. Removing the invited user converts the owner's budget back to personal. This replaces the earlier proposal that every user retains a personal budget alongside a shared budget. |
| 1 October 2026 | A user with both a personal and a shared budget has eligible expenses tracked once in each. The inviting owner's expenses count only in the converted shared budget while sharing is active. |
| 1 October 2026 | Retain the invited user's shared-period expenses in the owner's budget after removal. Removal alone does not change historical or current-month spending totals. The removed user's new expenses affect only their own personal budget. |
| 1 October 2026 | Converting to shared preserves the owner's existing category limits and current-month spending. The invited user's expenses contribute from acceptance onward; earlier invited-user expenses are not added retroactively. Visibility of the owner's pre-sharing transaction details is deferred to BL 01. |
| 1 October 2026 | Defer the proposed pre-sharing transaction-history visibility rule to the backlog. The recommendation to show current-month pre-sharing transactions while keeping earlier months private is not approved for implementation. |
| 1 October 2026 | Confirmed refunds linked to purchases adjust the original purchase month's spending. A refund received in a later month does not increase that later month's available budget. |
| 1 October 2026 | Only the owner can edit shared category limits. The invited user can view shared limits and manage their own separate personal limits. |
| 1 October 2026 | Each user can correct only their own transactions, including category, amount, and date. Corrections update every affected budget and retain change history. The budget owner cannot edit the invited user's transactions. After membership removal, retained shared history is frozen; a correction that would change shared totals enters owner review and does not restore access. |
| 1 October 2026 | The app automatically reads and writes Google Sheets for synchronization. Users make transaction and budget changes through the app; manual spreadsheet-cell edits are unsupported initially. Shared updates appear in the other user's app after synchronization. |
| 1 October 2026 | An expense must not be split across categories. Assign its full amount to one of the fixed first-version categories, using Uncategorized until classification is resolved. This replaces the earlier split-category proposal; split support is not an agreed backlog item. |
| 1 October 2026 | Initial categories are Groceries; Dining and food delivery; Transport; Travel; Fashion; Entertainment; Rent and housing; Utilities and bills; Health; Household items; and Other. Uncategorized holds expenses awaiting classification. This resolves the earlier open category-list decision. |
| 1 October 2026 | Keep the category list fixed for the first version. Personal and shared budgets use the same categories with independently configured limits. Custom categories are deferred to BL 02. |
| 1 October 2026 | First-pilot capture coverage is HDFC, SBI, SBM, Kotak, and Axis Bank. Support is claimed only after validating actual alerts for the accounts and channels used. |
| 1 October 2026 | The pilot includes financial SMS capture and supported bank/payment-app notification capture, including UPI alerts. Manual entry is the fallback when automatic capture is unavailable or uncertain. |
| 1 October 2026 | Invitations use Google account email. The recipient must accept before the owner's budget becomes shared; declining or leaving an invitation pending leaves both budgets unchanged. |
| 1 October 2026 | The user who creates the first budget is the owner. No separate administrator role is included in the first version. |
| 1 October 2026 | The invited user can leave voluntarily. Leaving revokes shared access, returns the owner's budget to personal use, preserves shared-period expenses in the owner's history, and routes the departing user's new expenses only to their personal budget. |
| 1 October 2026 | The owner may invite a previously removed or departed user again. Rejoining creates a new membership interval; expenses during the gap are not added retroactively. |
| 1 October 2026 | Each budget supports exactly two users in the first version: one owner and at most one invited member. Multiple members and multiple shared groups are out of scope. |
| 1 October 2026 | Onboarding explains and requests SMS and notification permissions, shows capture readiness and last successful capture, and provides recovery guidance when access is denied or revoked. |
| 1 October 2026 | Pilot devices are a Samsung Galaxy S25 Ultra on Android 16 and a Samsung Galaxy Z Flip 5 on Android 16. They are validation devices only; either person can create a budget or accept an invitation. |
| 1 October 2026 | Development remains role-neutral for general users. Any user can create one personal budget, invite another user, or accept an invitation; the pilot devices do not assign fixed owner or invited-member roles. |
| 1 October 2026 | Requirements work is functionality-first. UI layout, navigation defaults, visual styling, and interaction design decisions are deferred. |
| 1 October 2026 | Require Google account sign-in for each user. Use the authenticated identity for invitations, shared-Sheet access, ownership, membership, and revocation; never collect bank login credentials. |
| 1 October 2026 | Track expenses only in the first version. Salary, income, and unrelated credits do not affect budgets; only confirmed refunds or reversals linked to an expense adjust spending. |
| 1 October 2026 | Lock past-month category limits. Allow current and future limit configuration without rewriting past limits or historical totals. |
| 1 October 2026 | Track spending in categories without configured limits and label them “No limit.” Do not treat them as over budget or trigger percentage alerts. |
| 1 October 2026 | Allow expenses above configured limits. Continue recording them, show negative remaining amounts, and label the category “Over budget.” |
| 1 October 2026 | Use bank occurrence date for automatic transactions. If unavailable, use received time and flag review. Manual expenses use the entered date. Apply Asia/Kolkata month attribution. |
| 1 October 2026 | Deduplicate using transaction reference plus account context, with amount, merchant, and time as supporting evidence. Equal amounts alone never merge transactions; uncertain matches require review. |
| 1 October 2026 | Target local persistence, totals, and feedback within 2 seconds after a supported alert reaches the app on the pilot devices. Measure bank alert delay and network synchronization separately. |
| 1 October 2026 | Encrypt the local database and use Android-protected key storage. Protect transactions, budgets, memberships, pending changes, and personal data while preserving offline operation. |
| 1 October 2026 | Online removal or voluntary departure revokes shared access and stops new shared writes. Remove inaccessible shared cache on the departing user's next connection; disclose that offline copies cannot be remotely guaranteed erased. |
| 1 October 2026 | Freeze retained shared history after removal or departure. The former member may correct their personal copy; changes affecting retained shared totals go to owner review and do not reopen shared access. |
| 1 October 2026 | Defer server-backed reconciliation for delayed captures and post-removal corrections or refunds to BL 03. The first version flags uncertain boundary cases and does not attempt retroactive server-backed reconciliation. |
| 1 October 2026 | Restoring an app-managed backup requires both its export password and sign-in to the same Google account that created it. |
| 1 October 2026 | Retain change history indefinitely while the local dataset exists, include it in app-managed exports, and remove it only through the separate full local-data wipe. |
| 1 October 2026 | Each user has one personal budget in the first version. Any user can manage it and participate in another user's shared budget without creating a second personal budget. |
| 1 October 2026 | Manual expense entry requires amount, date, and category. Merchant, account, payment method, and notes are optional. |
| 1 October 2026 | The first version supports INR only. Foreign-currency transactions are excluded until exchange-rate handling and reporting rules are defined. |
| 1 October 2026 | The personal pilot will use private APK installation on the two Android phones. Play Store distribution is outside the current scope. |
| 1 October 2026 | Discard raw SMS and notification text after successful parsing. Retain only extracted transaction fields and limited parser metadata for troubleshooting; never retain OTPs or unrelated messages. |
| 1 October 2026 | Exclude historical SMS and notification import from the first version. Capture begins after setup; older expenses can be entered manually. |
| 1 October 2026 | Use soft deletion for transactions: remove deleted records from totals and normal views while retaining identity, deletion markers, and change history for synchronization and recovery. Provide a separate full local-data wipe in Settings. |
| 1 October 2026 | Include app-managed export and restore in the first version. Preserve stable IDs and exclude raw SMS, notification text, OTPs, and unrelated messages. |
| 1 October 2026 | Password-protect export files and require the password during restore. Do not include bank passwords, card PINs, OTPs, raw SMS, raw notification text, or unrelated messages. |
| 1 October 2026 | Hide expense details on the lock screen by default. Show a generic notification until the device is unlocked; users may change this setting. |
| 1 October 2026 | Use 80% and 100% as default category warning thresholds. Alert once per crossing; the owner controls shared thresholds and each user controls personal thresholds. |
| 1 October 2026 | Use one shared Google Sheet for the owner's shared budget. Keep personal budgets and personal-only data private in the app; do not write them to the shared Sheet. |
| 1 October 2026 | Sync shared changes on connectivity restoration, app open, and manual Refresh. Show pending, synced, failed, access-revoked, and stale-data states; continuous background sync is not promised in the first version. |
| 1 October 2026 | The app creates and manages the shared Google Sheet automatically. The owner authorizes creation when sending an invitation; the invited user receives access after acceptance; removal revokes shared access and stops future shared writes. |
| 1 October 2026 | Both users may view the shared Google Sheet and reporting tabs. App-managed source tabs are read-only to users; transaction, budget, and membership edits occur through the app. |
| 1 October 2026 | The shared Sheet includes an app-managed transaction ledger and monthly summaries for category limits, spent, remaining, over-budget status, and each user's contribution. |
| 1 October 2026 | Require user confirmation for every newly detected bank account or card before assigning transactions to an owner. Do not use matching last four digits as sufficient identity. |
| 1 October 2026 | Automatically categorize only high-confidence transactions. Route low-confidence merchants, ambiguous transfers, unsupported alerts, and possible duplicates to the review inbox; keep them pending and outside budget totals until confirmed. |
| 1 October 2026 | Exclude own-account transfers, transfers between the two users, and credit-card repayments from expense totals. Ambiguous UPI payments require review before they can affect a budget. |
| 1 October 2026 | Count only confirmed posted payments in budgets. Pending payments remain visible for review, failed payments count as zero, and linked reversals/refunds adjust the original purchase. |

### 8.2 Backlog

| ID | Item | Status and scope |
| --- | --- | --- |
| BL 01 | Visibility of the owner's pre-sharing transaction details to an invited user | Deferred; not required for now. Recommendation to revisit: show current-month transactions, including those before acceptance, while keeping earlier months private. This recommendation remains unapproved; deferral does not authorize unrestricted history access. The agreed preservation of existing spending in budget totals remains in scope. |
| BL 02 | Custom categories | Deferred beyond the first version. Revisit category creation and management permissions, ownership, and consistent mapping between personal and shared budgets. The first version uses the agreed fixed category list with independent budget limits. |
| BL 03 | Server-backed reconciliation for delayed and post-removal events | Deferred until a server is available. Revisit delayed captures, membership-boundary attribution, and corrections or refunds received after a member leaves or is removed. The first version flags uncertain cases and does not attempt retroactive server-backed reconciliation. |

## 9. Proposed implementation sequence

1. **Validate capture.** Collect redacted fixtures, map accounts, prove parsing and deduplication for the five target banks, and confirm Android permission/distribution feasibility.
2. **Build local budgeting.** Implement transaction lifecycle, category review, personal/shared calculations, notifications, and core screens against the acceptance scenarios.
3. **Add sharing and Sheets.** Implement invitations, membership boundaries, access separation, durable sync, conflicts, and recovery; verify on both phones.
4. **Pilot and release.** Reconcile a sample month against statements, document supported bank/channel combinations, and measure missing or incorrect detections. Agree numerical coverage targets after the fixture set is known.

## 10. Technical references

Platform constraints checked on 27 September 2026. These references support feasibility notes; they do not validate individual bank formats or guarantee app approval.

1. [Android NotificationListenerService API](https://developer.android.com/reference/android/service/notification/NotificationListenerService)
2. [Google Play SMS permission policy](https://support.google.com/googleplay/android-developer/answer/10208820)
3. [Google Sheets authorization scopes](https://developers.google.com/workspace/sheets/api/scopes)
4. [Google Sheets usage limits](https://developers.google.com/workspace/sheets/api/limits)
