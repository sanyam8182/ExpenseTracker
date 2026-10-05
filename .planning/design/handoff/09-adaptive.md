# 09 — Adaptive layouts and state restoration

Source: `../system/tokens.md` section 7 (corrected 5 October 2026). Foundations: `README.md`. Window sizes below are **estimates**; measure the real window size classes on both pilot phones in the first build and report any difference.

## Window classes for the pilot

| Case | Class | Layout |
| --- | --- | --- |
| S25 Ultra portrait, Z Flip 5 unfolded portrait (a normal tall phone) | Compact | Phone layouts in specs `01` to `08` |
| Z Flip 5 cover screen (about 290 dp square, estimate) | Compact, small | Glance layout (section C) |
| Z Flip 5 flex mode (half folded), any phone in landscape with height under 480 dp | Compact, short | Content above the fold, actions below (section D) |
| S25 Ultra landscape (about 900 dp wide, estimate) | Expanded (840 dp and wider) | Navigation rail, panes (section B) |
| Medium (600 to 839 dp) | None in the pilot | Treat like Expanded for navigation (rail) and list and detail; no frames designed |

Use the Compose window size classes (width and height) and posture information for foldables; do not branch on device model.

## A. State that must survive size changes

Rotation, fold, unfold and process recreation must keep: the selected tab, scroll positions, open sheets and dialogs, the selected row in list and detail layouts, filter selections, text typed in fields (amount, email, password), the chosen chips, the selected month. Use saved state (`rememberSaveable`, saved state handles), not in-memory only. Never lose an unsaved Add expense draft or a half-completed resolution on a size change.

## B. Expanded (S25 Ultra landscape)

- **Navigation rail** (80 dp wide) replaces the bottom bar: four items with icon and label, badge on Review; items are 80 dp wide by 64 dp high. No FAB in the rail; "Add expense" stays an extended FAB at the bottom-right of the content area.
- **Overview, two columns:** left column (about 380 dp) holds the hero (with Pocket) and the pending-review row; right column holds "Needs a look" and the on-track row. Same content and order as the phone; nothing is added. Top bar: title, month pill, sync chip.
- **Transactions, list and detail:** list pane about 340 dp, detail pane fills the rest with a hairline between. The selected row has the tint background. Selecting a row fills the detail pane (same content as the phone details screen, including Edit, Link refund and Delete for own transactions). No back arrow is needed in the detail pane. Edit opens as a modal dialog or an inline pane replacement (design owner to confirm).
- **Review, list and resolution:** list pane about 340 dp with the amber summary on top; resolution pane on the right. After counting, select the next item automatically so a stack of items can be cleared without leaving the pane. Empty selection shows "Choose an item to review."
- **Capture feedback, side panel:** the modal sheet becomes a panel about 360 dp wide on the right edge over a scrim; same content and the one "Done" button; Pocket only in the plain counted variant, as on phones.
- Other sheets (filters, account confirmation, category picker) become side panels or centred dialogs of at most 560 dp width.
- Content max width: keep line length readable; forms (Add expense, setup steps, invite) are centred at about 560 dp.
- Landscape must work at 200% font scale: panes scroll independently; never put two pinned bars on screen at once.

## C. Z Flip 5 cover screen (compact, small)

- Shows only: label "Left to spend", the amount (`amount-hero`), "of ₹15,600 in limits", Pocket (about 90 dp), and one button "Add expense" (white pill on the hero colour, teal text). No navigation, lists, sheets or settings.
- After an expense is saved on the cover screen: "Saved. Counted in Fashion" (14 sp), "₹1,299 at Myntra" (28 sp), "Left in Fashion" and `₹2,701` (`amount-hero`). Needs-review items show "Saved. Doesn't count yet" and the amount only; details wait for the main screen.
- Over budget uses the same "Over budget by" label as the Overview.
- Cover content is read-only except for the one add action; anything else says "Open your phone to continue" (copy proposed, not designed).
- The main screen layouts are used once the phone is opened. State continues across fold and unfold (a half-typed amount is kept).

## D. Flex mode (half folded)

Content in the top half of the screen, actions in the bottom half so thumbs reach them while the phone stands on a surface. Applies to the capture feedback sheet, Add expense and Review resolution: status, title, amounts and bar sit above the fold; the filled button and secondary actions sit just below it. The fold line is never crossed by text or a control. Detect posture with the foldable APIs and fall back to the compact layout if it is unavailable.

## E. Tests
Run Overview, Transactions, Review and Add expense in portrait, landscape, cover and flex mode, in light and dark, at 100% and 200% font, with TalkBack on; fold and unfold while a sheet is open and a field has text; verify nothing is lost and nothing overlaps the fold.
