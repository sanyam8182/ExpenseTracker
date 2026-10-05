# 03 — Sharing: members, automatic join, leave and removal

Requirements: MEM 01 to MEM 04 (as revised 5 October 2026: no Accept or Decline), HIST 01, SEC 01, SEC 02, BUD 01, BUD 03, SYN 03, AC 04, 11, 15, 24, 43, 48, 69, 73. Foundations: `README.md`.

**Model in one paragraph.** Sharing is a normal Google share of a marked Sheet (name prefix `ExpenseTracker Shared –`, first tab `_meta`). There is no invitation file and the app sends no email; Google sends its usual sharing email. The other person's app finds the Sheet and joins automatically by writing one `joined` row, then shows a one-time notice with a Leave action. The owner removes access by removing the Google share. One invitee only; one shared relationship per person.

## A. Owner

### A1. Budget tab entry
On Budget, under the limits and thresholds, a row "Members — Share this budget with one person ›" opens the Members screen. (Same list pattern as the Budget screen; there is no separate card on the tab.)

### A2. Members screen, solo owner
Top bar: back arrow, "Members", sync chip. One card: title "Share this budget", text "Invite one person. They see your shared purchases from the day they join. Your earlier spending stays private.", filled button "Invite someone".

### A3. Invite form
- Title "Invite someone". Label above the field: "Their Google email"; email text field (keyboard type email); help "Use the email they sign in to Google with." Validation: valid email format; error below the field (**invalid and offline states not yet designed**; use the standard field error pattern).
- Box "What happens": "We create a Google Sheet and share it with them." / "Google sends them its usual sharing email. We don't send one." / "Their app joins by itself when it finds the Sheet. They can leave any time."
- Box "Once they join, they can see": "Merchant, amount, date, category and who spent it, for purchases from that day on." / "One "Owner opening amount" per category. Not your earlier purchases."
- Warning box "Good to know": "Anyone with edit access to the Sheet can read or change it outside the app." (MEM 02, AC 15, AC 73.)
- Pinned buttons: "Share budget" (filled), "Cancel" (text). Content scrolls above them.
- On confirm: create the Sheet (marker prefix, `_meta` with budget ID and schema version), share it with that account as editor with link sharing off and resharing disabled where supported, write **no financial data**. Eligibility is checked before sending: not already in a shared relationship, no reciprocal invitation, no other member.

### A4. Members screen states

| State | Pill (icon + words) | Text | Action |
| --- | --- | --- | --- |
| Shared, not joined yet | ⌛ "Shared, not joined yet" | "Meera has access to the Sheet. Her app joins by itself the next time it opens." | Outlined destructive "Remove access"; help "Removing access stops her from joining. Nothing financial is in the Sheet yet." |
| Active | ✓ "Active since 1 October" | — | Red text "Remove Meera" |
| Removal pending | ⚠ "Removal pending" | "Her access is not removed yet. We'll finish when you're online and Google confirms it." | Outlined "Try again"; sync chip shows "Pending" |
| Ended | "Ended" (neutral) | "Sharing ended on {date}. Shared spending up to then stays in your totals." (**copy proposed, screen not yet designed**) | "Invite someone" for a new Sheet |

Member card: avatar with initials, name, email.
Below it (Active): card "Shared budget": "Only you can change shared limits and warning thresholds. Meera corrects only her own purchases." with rows "Owner opening amount" `₹6,420` and "Prior retained spending" `₹0` (HIST 01 labels; do not claim the opening amount is only individually visible purchases).

### A5. Remove confirmation (dialog)
Title "Remove Meera?" Body "Her access to the Sheet will be removed. Spending shared so far stays in your totals. She keeps her own personal budget. Removing access needs an internet connection." Buttons: text "Cancel", destructive text "Remove".

### A6. Removal sequence (MEM 03, requirement)
Requires connectivity. Write a `removing` row; supported clients pause shared writes; remove the Google access; confirm the permission state through the API; take the final available ledger and store a closed-epoch snapshot; write `ended` only after removal is confirmed. A failure stays "Removal pending" and never claims revocation. After removal, archive the Sheet owner-only and stop automatic writes to it. A new invite creates a new Sheet and `_meta`; the old Sheet grant is never reinstated. Frozen history follows MEM 04.

## B. Invitee

### B1. Automatic join
The app looks for Sheets owned by someone else, shared with the signed-in account, carrying the marker (name prefix and `_meta`). Discovery runs when the app opens and on its normal refresh; no background timing is promised. If found and the person is not in another shared relationship, the app writes one `joined` row (account ID, time). That time is the sharing start. Repeated discovery writes no second row. A join from a non-invited account, a second member, or a second `joined` row stops shared writes and shows a recovery issue (MEM 01, AC 48, AC 69).

### B2. Joined notice (shown once, full screen)
Title "You joined Rahul's shared budget". Sub "Sharing started just now. Rahul Menon, rahul.menon@example.com." One card with three sections separated by a hairline:
- "What Rahul sees": "Merchant, amount, date, category and who spent it, for purchases you make from now on." / "There is no way to hide a single purchase."
- "What stays private": "Your personal budget." / "Your purchases from before you joined."
- "What you see": "Rahul's purchases from now on." / "One "Owner opening amount" per category, not his earlier purchases."
Then the warning box "Good to know" (Google-editor limit, same text as A3). Buttons: "Got it" (filled), "Leave shared budget" (outlined, destructive text). It informs; it is not a consent step because sharing has already started (SEC 01, AC 73).

### B3. Cannot join
Overview banner: "Rahul shared a budget with you" / "Your app didn't join because you're already in a shared budget. Rahul can remove the share from his app, or you can leave your current one first." with a "Got it" action. One shared relationship at a time (BUD 01).

### B4. Joined, waiting
Overview with the Personal | Shared switch. Banner (info icon): "Joined. Waiting for Rahul's app" / "His app has to sync before shared totals appear. Your purchases from now on are saved and will be added." Shared hero: label "Left to spend, shared", "No figures yet", "Available after Rahul's app syncs." The owner's app writes opening amounts as of the join time on its next sync.

### B5. Budget tab for an invitee
Segmented Personal | Shared with Rahul. Under Shared: banner "Only Rahul can change these" ("Shared limits and warnings are set by the owner. You can still set your own personal limits."); limits shown read-only (grey, no border).

### B6. Leave
Dialog "Leave this shared budget?" / "Your new purchases become personal only, right away. Rahul's app must then remove your access. Your personal budget and history stay." Buttons "Cancel", destructive "Leave". On Leave: pause the shared queue immediately, route new purchases personal-only, write a `left` row with the time. Status afterwards (Budget tab): card "Shared with Rahul", pill ⚠ "You left", text "You left. Rahul's app must remove your access. Your new purchases are personal only." and card "If your access ends": "Your personal budget and history stay. Shared data saved on this phone is cleared the next time you're online. Copies already saved or exported can't be erased remotely." Never claim global termination until removal is confirmed.

### B7. Access revoked
Chip "Access revoked"; Overview banner "Sharing with Rahul ended" / "Your personal budget and history are unchanged. Shared data saved on this phone is cleared the next time you're online." The Personal | Shared switch disappears. On next connection clear the inaccessible shared cache, keep personal records.

A write first attempted after revocation is rejected and shown to its author as "Saved personally; not added to closed shared period." (MEM 04.)

## C. Rules that affect other screens
- An owner's expense affects only the owner's budget; an active invitee's expense affects personal and the owner's shared budget once each, never summed twice (BUD 03).
- Invitee contributions start at the join time; earlier expenses never backfill. Owner spending never enters invitee personal totals.
- Only the owner changes shared limits, thresholds, invites and removes. Each person changes only their own transactions (MEM 02); enforce on local mutations, queued operations and sync import.
- Rejoin needs a fresh share of a new Sheet and a new epoch; the gap is not imported.

## D. Accessibility
Status pills are part of the spoken text ("Shared, not joined yet"). Destructive dialog buttons are not the initial focus. Disclosure text must be reachable in full by TalkBack before the buttons.

## E. Open items
Invalid email and offline invite states; join failed; Ended state frame; wording for a join that is blocked by a second member (recovery issue screen).
