# 07 — Settings, recovery and data

Requirements: UI 01, CAP 06, CAT 01, CAT 03, REC 01, REC 02, SEC 02, SYN 03, FBK 03. Foundations: `README.md`.

**Route:** overflow menu (top bar) → Settings. Back arrow returns. All Settings screens use the plain list pattern: rows with title, one-line state underneath, chevron.

## A. Settings hub
Rows (title — state line):
- "Capture health" — "1 needs attention"
- "Lock screen details" — "Hidden" (default; the privacy preference in AC 21)
- "Show Pocket" — "On the Overview and after each expense" (switch; default on)
- "Remembered rules" — "3 shops"
- "Export and restore" — "Not exported yet"
- "Deleted items" — "2 items"
- "Sync issues" — "3 need attention"
Second card: "Account" (signed-in email) and, alone and in error colour, "Wipe local data". Replace phone is reached from Export and restore.

## B. Capture health (CAP 06)
Card of three permission rows: status circle (✓ or !) + name + line + optional link. "Bank SMS — Allowed", "Payment app alerts — Not allowed. Alerts from payment apps aren't captured. Fix in settings", "Our own alerts — Allowed". Second card: "Last capture — Today, 2:14 pm", "Unreadable alerts — 1, in Review", "Apps we listen to — HDFC, SBI, Kotak" (display names; package names come from SPIKE-03 discovery and must not be invented). Info text: "If capture misses something, add it by hand. Manual entry always works." Statuses update after returning from system settings and on revocation.

## C. Remembered rules (CAT 01)
List: payee and what it does ("ZXCV SHOP 4821 — Counts as Dining and food delivery", "Rohan K — Treated as a transfer"), each with a text action "Remove" in error colour (48 dp touch). Info: "Rules are private to you. Removing one affects future alerts only, never past purchases." Rules are keyed by normalized payee plus context, never rewrite past transactions or another person's rules, and still pass all normal gates.

## D. Export and restore (REC 01)
- Banner (when new data has not been exported for 7 days or never): "⚠ Not exported yet" / "You have 9 days of new data. If you lose this phone without an export, personal data can be lost." Reminder after 7 days with new data and before replacement or wipe; it can be dismissed. States: recent (shows last export date), overdue (above), never exported.
- Card "Export": "Saves a password-protected file you keep somewhere safe. Review excerpts and sign-in tokens are not included." Button "Export now" (asks for a password, then writes a portable versioned encrypted file; progress state not yet designed). Export excludes review excerpts, raw alerts, OAuth tokens, bank secrets and device-bound keys.
- Card "Restore": "Needs the file's password and the same Google account." Button "Restore from file".
- Info: "Replacing your phone? Export here, restore on the new phone, then wipe the old one." (leads to Replace phone).
- **Restore wrong password:** banner "backup_5_oct.etbackup — Same Google account needed. You're signed in as …", field "File password" with error "That password doesn't match this file. Nothing was changed." Same pattern for a different Google account or a damaged file. No local change on any mismatch.
- **Restore preview:** card "412 transactions found" / "From 1 August to 5 October. 12 are already on this phone and won't be added twice." Info "Sharing is checked again" / "Shared access is only used once the app confirms it with Google. An old backup can't reopen a sharing period that has ended." Buttons "Restore 400 transactions" (filled), "Cancel". Restore merges idempotently by stable IDs and versions; derive a new device database key; revalidate membership and permissions before restoring shared access; keep shared actions disabled for unverified historical membership.

## E. Replace phone (SEC 02)
Three numbered steps: "1 Export on this phone — Saves a password-protected file. Keep it somewhere safe." / "2 Restore on the new phone — Sign in with the same Google account, then choose the file and enter its password." / "3 Retire this phone — Wipe it once the new one works. Two phones on one account can mix up data." Info "Only one phone works at a time": "If the app sees another phone using this account, it stops sharing on this one and tells you." Button "Export now". A restore assigns a new device installation ID. Unsupported concurrent devices cannot be trusted without a backend: warn and stop supported shared writes when a conflict is detected.

## F. Deleted items (CAT 03)
List of the user's own deleted purchases ("Ola — ₹180, Transport, deleted yesterday") with a text action "Restore". Info: "Only your own deleted purchases show here. Restoring checks everything again before it counts." Restore reruns validation, duplicate and membership gates; an active shared projection restores once if eligible; a closed epoch stays frozen and only the personal copy restores.

## G. Wipe local data (REC 02)
Dialog title "Remove everything on this phone?" Body "Local data and history will be removed. This can't be undone." If the person is in a shared budget add: "You are still in Rahul's shared budget. Leave it first if you want to end sharing. Wiping does not remove your access." (owner wording: "You are still sharing a budget with Meera. Remove access first if you want to end sharing.") Buttons: text "Export first" (offered, not forced), "Cancel", destructive "Wipe". Wipe cancels workers and notifications first, then deletes the local database, pending work, review excerpts, local auth and session state and the protected database key. It works offline, does not delete exported files, Google Sheets or the other person's data, and does not claim remote revocation.

## H. Sync issues (SYN 03, REC 01)
Screen title "Sync issues" with a "Failed" chip when blocked. Issue cards (icon, bold title, plain explanation, one next step):
- "Shared budget paused" — "The shared Sheet has a format this app doesn't recognise. Your own data is safe and still saving on this phone." → "See what to do ›". Cause: newer or incompatible schema, missing source tab, contradictory operation, or unexpected financial edit. Stop shared financial writes, keep the local outbox, never auto-rewrite; owner can rebuild derived reports from valid events; migrations back up the original first.
- "Recovery blocked" — "Rahul's phone can't be reached to rebuild sharing. You keep access to what was already shared, and you can export your own data." → "Export your data ›". Owner phone lost and the marked Sheet cannot be rediscovered after reinstall (REC 01).
- "Conflict on one purchase" — "Two versions of the same purchase were saved. The shared total holds at the last agreed value until its owner picks one." → "Review the purchase ›". Conflicting successors preserve both candidates and freeze the affected projection until the transaction owner resolves it.
Local data stays visible throughout. Never show raw errors.

## I. Sync status legend (reference)
| Chip | Meaning |
| --- | --- |
| ☁ Synced 2 min ago | All shared data applied |
| ☁ Syncing | In progress |
| ⇧ Pending | Saved here, waiting to send |
| ⏱ Stale, 22 min ago | No sync for 15 minutes or more |
| ⏱ Never synced | Counts as stale |
| ⚠ Failed | Last attempt failed; retry |
| 🔒 Access revoked | Shared access ended |
Banner under the legend: "Saving on your phone and sharing are separate. Local saves never wait for sync."

## Accessibility
All list rows are single focusable nodes with state in the spoken label. Destructive actions are never the initial focus and sit last. "Wipe local data" is separated from other rows and announced as destructive.
