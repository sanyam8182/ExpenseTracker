# Expense Tracker — Developer Handoff

**Date:** 5 October 2026
**Platform:** native Android 16 (API 36), Kotlin, Jetpack Compose, Material 3 structure with our own colours, shape and type. Pilot phones: Samsung Galaxy S25 Ultra and Galaxy Z Flip 5.
**Source of truth order:** `Expense_Tracker_Requirements.md` (behaviour) → `../system/tokens.md` and `../system/components.md` (look) → these specs (how each screen is put together). If they disagree, the requirements win, then tokens; tell the design owner.
**Visuals:** HTML concept mockups exist outside the repo (Pocket artwork is not cleared for the repo). This handoff is text so it can live here. Ask the design owner for the mockup files.

## How to read a spec

Each screen file lists: purpose and requirement IDs, layout, components, data it needs, states, behaviour, exact copy, accessibility, and adaptive behaviour. "Requirement" means it comes from the requirements; "Design" means it was decided in the design process and may be adjusted if the requirement is silent.

| File | Contents |
| --- | --- |
| `README.md` | Foundations, shared components, conventions (this file) |
| `01-overview-and-capture.md` | Overview, Pocket, capture feedback sheet, notifications |
| `02-review.md` | Review inbox and resolutions |
| `03-sharing.md` | Members, automatic join, joined notice, leave, removal |
| `04-setup.md` | Sign-in, permissions, readiness, first limits, account confirmation |
| `05-transactions.md` | List, filters, details, edit, delete, refund link |
| `06-budget.md` | Limits, past months, thresholds |
| `07-settings-and-recovery.md` | Settings hub, capture health, export and restore, deleted items, rules, wipe, sync issues, replace phone |
| `08-add-expense-and-states.md` | Add expense, loading, empty, failure and revoked states |
| `09-adaptive.md` | Landscape, cover screen, flex mode, state restoration |

## 1. Theme

Provide one `ExpenseTrackerTheme` that follows the system light or dark setting. Do not use dynamic colour (Material You): the palette is fixed.

### 1.1 Colour roles

Map our tokens to the Material 3 `ColorScheme` where a role exists; keep the rest in a custom `ExtraColors` object exposed through a `CompositionLocal`.

| Token | Light | Dark | Compose role |
| --- | --- | --- | --- |
| `bg` | `#F3F6F5` | `#0F1615` | `background` |
| `surface` | `#FFFFFF` | `#192221` | `surface`, `surfaceContainer` |
| `surface-variant` | `#E9EFED` | `#232E2C` | `surfaceVariant` |
| `outline` (hairline) | `#DDE5E3` | `#2A3634` | `outlineVariant` |
| `outline-strong` | `#7D8B89` | `#7F8F8C` | `outline` |
| `text` | `#1B2625` | `#E9F0EF` | `onBackground`, `onSurface` |
| `text-secondary` | `#566462` | `#A9B8B6` | `onSurfaceVariant` |
| `text-muted` | `#5F6D6B` | `#8FA09D` | `ExtraColors.textMuted` |
| `primary` | `#0C7A7C` | `#5FD0D2` | `primary` |
| `on-primary` | `#FFFFFF` | `#00373A` | `onPrimary` |
| `primary-tint` | `#DCEFEF` | `#174E50` | `primaryContainer` |
| `primary-text` | `#0A5F61` | `#5FD0D2` | `onPrimaryContainer` |
| `primary-bright` | `#0F8B8D` | same as `primary` | `ExtraColors.primaryBright` (graphics only) |
| `hero` | `#0A5C5E` | `#12484A` | `ExtraColors.hero` |
| `hero-text` | `#FFFFFF` | `#EAF8F8` | `ExtraColors.onHero` |
| `hero-text-secondary` | `#CDE9E8` | `#B5DCDD` | `ExtraColors.onHeroSecondary` |
| `success` / tint | `#2A7448` / `#E3F2E8` | `#7FD6A0` / `#17301F` | `ExtraColors.success`, `successContainer` |
| `warning` / tint | `#8A5200` / `#FFF0D1` | `#F2B85C` / `#3A2A0A` | `ExtraColors.warning`, `warningContainer` |
| `error` / tint | `#B3261E` / `#FBE3E0` | `#F2B8B5` / `#4A1613` | `error`, `errorContainer` |
| `badge-warning` / number | `#9A5B00` / `#FFFFFF` | `#F2B85C` / `#3A2A0A` | `ExtraColors.badge`, `onBadge` |
| `inverse-surface` / text / primary | `#1B2625` / `#F3F6F5` / `#8FE0DE` | `#E9F0EF` / `#0F1615` / `#0C7A7C` | `inverseSurface`, `inverseOnSurface`, `inversePrimary` |
| `scrim` | teal-black 50% | black 60% | `scrim` |

Category colours (graphics only, never the only identifier): Groceries `#4C8C6A`, Dining and food delivery `#C2693A`, Transport `#3F7FB5`, Travel `#4F8AA6`, Fashion `#8A7BC0`, Entertainment `#D2784F`, Health `#C4557A`, Household items `#7A6A58`, Rent and housing `#8E5BA8`, Utilities and bills `#B0892A`, Other `#6B7280`. Used in the category picker, Transactions and Budget. The Overview does not show them.

### 1.2 Typography

Family DM Sans (bundled font resource; fallback system sans). Verify `₹` and tabular figures on both pilot phones before relying on it. Amounts use `fontFeatureSettings = "tnum"`.

| Token | Size / line | Weight | Compose style |
| --- | --- | --- | --- |
| `amount-hero` | 40 / 48 sp | 600, tabular | custom `TextStyle` |
| `headline` | 28 / 36 | 600 | `headlineMedium` |
| `title` | 20 / 28 | 600 | `titleLarge` |
| `title-small` | 16 / 24 | 600 | `titleMedium` |
| `body` | 16 / 24 | 400 | `bodyLarge` |
| `body-small` | 14 / 20 | 400 | `bodyMedium` |
| `label` | 14 / 20 | 600 | `labelLarge` |
| `caption` | 12 / 16 | 500 | `labelSmall` (metadata only, never essential text) |

Minimum text size is 12 sp. Sentence case everywhere.

### 1.3 Shape, spacing, elevation, motion

- **Shape:** 8 (small tag), 16 (fields, list cards), 24 (cards, hero, dialogs), top corners 28 (bottom sheets), full pill (buttons, chips, FAB, nav indicator, status chips).
- **Spacing (4 dp base):** 4, 8, 12, 16 (screen margin on phones and card padding), 20, 24 (between sections, margin on wide windows), 32, 48 (minimum touch target).
- **Elevation:** cards are flat with a 1 dp `outlineVariant` border; FAB and bottom sheet have a soft shadow; dialogs use the scrim.
- **Motion:** `fast` 150 ms ease-out (press, chip select), `base` 250 ms ease-out (sheets, snackbar), `slow` 350 ms ease-in-out (screen transitions), `meter` 400 ms ease-out (budget level and amount change). When the system animator duration scale is 0 ("Remove animations"), every transition is instant and Pocket is a still image.

### 1.4 Sizes

Button 48 dp high, 24 dp side padding, pill. Extended FAB 56 dp high. Text field 56 dp. Chip 40 dp visual, 48 dp touch. Bottom navigation 80 dp, 4 items, icon and label always shown. Top app bar 64 dp. Category card at least 96 dp high. **Never use fixed heights for content**: use minimum heights so text can grow at 200% font size.

## 2. Navigation and structure

- **Compact width:** bottom navigation with four destinations: Overview, Transactions, Review (badge = number of all inbox items), Budget. The top bar carries the sync chip and an overflow menu leading to Settings. Add expense is an extended FAB on Overview and Transactions.
- **Expanded width (840 dp and wider):** navigation rail instead of the bar; see `09-adaptive.md`.
- Use a single-activity Compose app with a navigation graph; each destination keeps its own back stack so that switching tabs restores scroll position and state.
- Bottom sheets: modal sheets for capture feedback, filters, account confirmation. Dialogs for confirm and destructive actions.

## 3. Shared components

### 3.1 Buttons
One filled primary per screen. Others: tonal, outlined (1.5 dp `outline` border), text, destructive text (error colour). Minimum height 48 dp; grow in height and wrap the label rather than clip. A disabled primary stays visible and a line under it says why ("Add an amount and pick a category."). Disabled colours: `surfaceVariant` background, `onSurfaceVariant` text.

### 3.2 Chips
Filter, choice and assist chips: unselected = `surfaceVariant` fill, no border; selected = `primaryContainer` fill, 1.5 dp `primary` outline, check icon, `onPrimaryContainer` text. 48 dp touch target.

### 3.3 Text field
Label above the field (never placeholder-as-label), 56 dp high, 16 dp radius, 1.5 dp `outline` border (error: `error`; focus: `primary`). Error text sits below in words with an icon. Helper text 14 sp.

### 3.4 Amount field
Large numeric field with a `₹` prefix, `amount-hero` text, underlined in `primary` (error: `error`). Indian digit grouping while typing, paise optional.

### 3.5 Status chip and banner
Every status is colour plus icon plus words; colour alone is never enough.

| Status | Colour | Icon | Words |
| --- | --- | --- | --- |
| On track | success | check-circle | "On track" |
| Getting close (80% and above) | warning | alert-triangle | "80% used" |
| Over budget | error | alert-circle | "Over budget by ₹X" |
| No limit | text-secondary | infinity | "No limit" |
| Needs review | warning | inbox | "Needs review" |
| Pending sync | text-secondary | cloud-upload | "Pending" |
| Stale | warning | clock | "Stale" (with age) |
| Sync failed | error | cloud-off | "Failed" |
| Access revoked | error | lock | "Access revoked" |
| Shared | primary-text on primary-tint | users | "Shared" |
| Deleted | text-secondary | trash | "Deleted" (row also struck through) |

Banners (`surfaceVariant`, warning container or error container) always start with an icon. Use the Material Symbols (rounded) set; the mockups use placeholder glyphs.

### 3.5a Sync chip (top bar, every main screen)
Seven states, requirement FBK 03: "Synced 2 min ago" (neutral), "Syncing", "Pending" (local shared writes awaiting acknowledgement), "Stale, N min ago" (no successful sync for 15 minutes or more while showing a shared view, or after a known failure; "Never synced" counts as stale), "Failed", "Access revoked". Tap = refresh. The chip has a 48 dp touch area. Fifteen minutes is a display threshold, not a background-sync promise.

### 3.6 Snackbar
`inverse-surface` background, `inverse-on-surface` text, action in `inverse-primary`; shown 250 ms in. Use for reversible actions ("Counted in Dining and food delivery" with **Undo**, "Deleted Myntra ₹1,299" with **Undo**). Never use it for errors that need a decision.

### 3.7 Dialogs and sheets
Dialog: 24 dp radius, title 20 sp, body 14 sp, text buttons right-aligned, each 48 dp high; the destructive action is a text button in `error` colour and never the default focus. Modal bottom sheet: top corners 28 dp, drag handle, scrim behind; content scrolls above any pinned button.

## 4. Money and dates

- Money is stored as **integer paise** and is INR only. Format with Indian grouping and `₹`: `₹1,00,000.00` in precise views; in lists show paise only when non-zero (`₹1,299`, `₹1,299.50`). Use `NumberFormat` with locale `en-IN` and verify grouping on device.
- Accessibility: every amount has a `contentDescription` / semantics text that speaks rupees and paise ("one thousand two hundred rupees and fifty paise").
- Calendar months, time zone `Asia/Kolkata`. Instants are UTC in storage and display; show local time at render only.
- A negative remaining amount is shown as "Over budget by ₹X", never as a minus sign alone.

## 5. Wording rules

Say "doesn't count yet" for review items, never "error". Never write "successfully", "please" or exclamation marks in system text. Say "Left. Rahul's app must remove your access" until removal is confirmed; never claim a removal that has not happened. Say that anyone with edit access to a shared Sheet can read or change it outside the app wherever sharing is explained. Errors say what happened and what to do, in one sentence, with no raw exception text.

## 6. Accessibility baseline (NFR 02)

- TalkBack works for every core flow; every icon-only control has a content description; headings are marked as headings; order of focus follows reading order.
- Layouts hold at 200% font scale: no clipped actions, text wraps, sheets and pinned-button screens scroll their content above the buttons. Test with ₹1,00,00,000. Bottom navigation labels are capped at 130% and may wrap to two lines.
- Touch targets are at least 48 dp. Text links and chips that look smaller still get a 48 dp touch area.
- Contrast: text 4.5:1, large text and graphics 3:1, in both themes (values are in `tokens.md`).
- Selected state is tint plus outline plus check, never weight or colour alone. Focus and pressed states are visible. Deleted items are struck through at full contrast.
- Reduced motion: respect the animator duration scale (see 1.3).

## 7. State patterns

| Pattern | Treatment |
| --- | --- |
| Loading | Skeleton shapes matching the final layout; no spinner or blank screen |
| Empty | One sentence naming the space, one line of help, one action |
| Error | What happened, what is safe, one button (Retry or Fix); local data stays visible |
| Offline | Quiet banner; everything local still works |
| Stale or pending | Sync chip plus the affected numbers marked "may be out of date" |
| Success | The affected number animates (`meter`); short snackbar; Undo where reversible |

## 8. Pocket (budget character)

Concept art (raster) is in the Figma file "Pocket / Character kit". Production artwork is still to be supplied: transparent backgrounds, vector or layered assets for animation, and confirmed rights. Until then use placeholder poses behind an interface `PocketPose` so the asset can change.

| Budget left (of the sum of limits) | Pose |
| --- | --- |
| More than 60% | Full |
| 30% to 60% | Halfway |
| Under 30% | Nearly empty |
| 0 or less | Over budget |
| No limits set | No Pocket level (hero shows "Spent this month") |

Extra poses: Spending (an expense was saved), Payday (proposed for "new month starts"), Idle (empty states). Pocket never carries information alone and never appears on errors, warnings, over-budget sheets, Review or sharing screens. A "Show Pocket" setting (default on) removes him everywhere. Pocket is bottom-right on the hero, about 100 to 160 dp wide, with a soft shadow.

## 9. Offline, privacy and data rules every screen must respect

- Saving is local and instant; success feedback appears after the local commit (FBK 01, DAT 01). Sync status is separate.
- Lock-screen notifications reveal nothing (AC 21): "Expense recorded" or "Needs review" only; several at once group into one.
- Review excerpts stay on the phone, encrypted, and never appear in logs, exports or shared storage (RAW 01).
- Never log keys, tokens, raw messages, full account numbers or personal transaction content (DAT 01).
- No raw alert text is stored for parsed items; one-time passwords are never stored.

## 10. Definition of done for any screen

1. Matches its spec in light and dark, at 100% and 200% font size, in portrait and (where specified) landscape.
2. All listed states exist and are reachable in tests (loading, empty, error, offline, stale, pending where relevant).
3. TalkBack reads it in order; amounts are spoken as rupees and paise; touch targets are at least 48 dp.
4. Copy matches this spec word for word, or the change is agreed with the design owner.
5. State survives rotation, fold and process recreation: open sheet, scroll position, selected row, typed text.
