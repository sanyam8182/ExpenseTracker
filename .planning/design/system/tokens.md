# Expense Tracker — Design Tokens

**Date:** 5 October 2026
**Platform:** native Android (Jetpack Compose, Material 3 structure with our own colours, shape and type)
**Personality:** warm and friendly. Calm, readable, human wording. Numbers are the hero.
**Brand colour:** warm teal. **Themes:** light and dark (follow the phone setting). **Reference apps:** none.
**Units:** dp for size and spacing, sp for text. These are the binding values; do not invent new ones.

Contrast figures are WCAG ratios computed on the exact hex values below (checked 5 October 2026). Text needs 4.5:1, large text (18sp or bold 14sp+) and graphics need 3:1.

## 1. Colour

### 1.1 Light theme

| Role | Token | Hex | Use | Contrast |
| --- | --- | --- | --- | --- |
| Page | `bg` | `#F3F6F5` | Screen background (cool off-white that lets the teal stand out) | text on it 14.3:1 |
| Card | `surface` | `#FFFFFF` | Cards, sheets, dialogs | text on it 15.5:1 |
| Quiet card | `surface-variant` | `#E9EFED` | Chips, filled fields, grouped rows | |
| Divider | `outline` | `#DDE5E3` | Hairlines and card borders (decorative only) | 1.2:1, never the only boundary of a control |
| Control edge | `outline-strong` | `#7D8B89` | Text-field and checkbox outlines | 3.3:1 on bg |
| Text | `text` | `#1B2625` | Body and numbers | 14.3:1 |
| Supporting text | `text-secondary` | `#566462` | Labels, captions | 5.7:1 on bg |
| Hint text | `text-muted` | `#5F6D6B` | Placeholders, metadata | 5.0:1 on bg |
| Brand | `primary` | `#0C7A7C` | Filled buttons, active tab, links | 5.1:1 with white text |
| Brand text | `primary-text` | `#0A5F61` | Text on the brand tint | 6.3:1 on `primary-tint` |
| Brand tint | `primary-tint` | `#DCEFEF` | Selected chips, active nav pill | |
| Brand graphic | `primary-bright` | `#0F8B8D` | Progress fill, icons (graphics only) | 3.9:1 |
| On brand | `on-primary` | `#FFFFFF` | Text and icons on `primary` | 5.1:1 |
| Hero card | `hero` | `#0A5C5E` | Overview "Left to spend" card (deeper than `primary` so white text reads at 7.8:1) | white on it 7.8:1 |
| Hero supporting text | `hero-text-secondary` | `#CDE9E8` | Label and "of ₹12,000" lines on the hero | 6.1:1 on `hero` |
| Badge fill | `badge-warning` | `#9A5B00` | Review count badge (white number) | 5.4:1 |
| On track | `success` | `#2A7448` | "On track" text/icon | 4.9:1 on tint |
| On track tint | `success-tint` | `#E3F2E8` | Status chip background | |
| Warning | `warning` | `#8A5200` | "80% used", "Needs review" text/icon | 5.7:1 on tint |
| Warning tint | `warning-tint` | `#FFF0D1` | Warning chip, banner | |
| Over budget / error | `error` | `#B3261E` | "Over budget", errors | 5.3:1 on tint |
| Error tint | `error-tint` | `#FBE3E0` | Error chip, banner | |

### 1.2 Dark theme

| Token | Hex | Notes |
| --- | --- | --- |
| `bg` | `#0F1615` | Teal-tinted near-black, not pure black |
| `surface` | `#192221` | Cards |
| `surface-variant` | `#232E2C` | Chips, grouped rows |
| `outline` | `#2A3634` | Dividers |
| `outline-strong` | `#7F8F8C` | Control edges (5.4:1 on bg) |
| `text` | `#E9F0EF` | 15.9:1 on bg |
| `text-secondary` | `#A9B8B6` | 7.9:1 on surface |
| `text-muted` | `#8FA09D` | 6.7:1 on bg |
| `primary` | `#5FD0D2` | 10.2:1 on bg |
| `on-primary` | `#00373A` | 7.1:1 on primary |
| `primary-tint` | `#174E50` | Selected chip, active nav pill |
| `primary-text` | `#5FD0D2` | 5.1:1 on primary-tint |
| `success` / `success-tint` | `#7FD6A0` / `#17301F` | 8.1:1 |
| `warning` / `warning-tint` | `#F2B85C` / `#3A2A0A` | 7.8:1 |
| `error` / `error-tint` | `#F2B8B5` / `#4A1613` | 8.7:1 |
| `hero` / `hero-text` / `hero-text-secondary` | `#12484A` / `#EAF8F8` / `#B5DCDD` | 9.4:1 and 6.9:1 |
| `badge-warning` / number | `#F2B85C` / `#3A2A0A` | 7.8:1 |

### 1.3 Status meaning (fixed, same in both themes)

Colour is never the only signal. Every status has an icon **and** words.

| Status | Colour | Icon | Words |
| --- | --- | --- | --- |
| On track | `success` | check-circle | "On track" |
| Getting close (80%+) | `warning` | alert-triangle | "80% used" |
| Over budget | `error` | alert-circle | "Over budget by ₹X" |
| No limit | `text-secondary` | infinity | "No limit" |
| Needs review | `warning` | inbox | "Needs review" |
| Pending sync | `text-secondary` | cloud-upload | "Pending" |
| Stale | `warning` | clock | "Stale" |
| Sync failed | `error` | cloud-off | "Failed" |
| Access revoked | `error` | lock | "Access revoked" |
| Shared | `primary-text` on `primary-tint` | users | "Shared" |
| Deleted | `text-secondary` | trash | "Deleted" |

### 1.4 Category identity (graphics only)

Eleven muted hues for the small dot/icon beside a category name in the category picker, Transactions and Budget. The Overview does not show them (decision 5 October 2026: no decoration; Overview rows are named and carry a status). They are never the only identifier (the name is always written), and none uses the status colours.

| Category | Hex | Category | Hex |
| --- | --- | --- | --- |
| Groceries | `#4C8C6A` | Health | `#C4557A` |
| Dining and food delivery | `#C2693A` | Household items | `#7A6A58` |
| Transport | `#3F7FB5` | Rent and housing | `#8E5BA8` |
| Travel | `#4F8AA6` | Utilities and bills | `#B0892A` |
| Fashion | `#8A7BC0` | Other | `#6B7280` |
| Entertainment | `#D2784F` | | |

All are at least 3.2:1 on white. In dark theme use the same hex at 85% lightness shift (implementation derives the lighter variant from the same hue).

### 1.4a Inverse surface and scrim

| Token | Light | Dark | Use |
| --- | --- | --- | --- |
| `inverse-surface` | `#1B2625` | `#E9F0EF` | Snackbar background |
| `inverse-text` | `#F3F6F5` | `#0F1615` | Text on the snackbar |
| `inverse-primary` | `#8FE0DE` | `#0C7A7C` | Snackbar action (Undo) |
| `scrim` | black-teal at 50% | black at 60% | Behind dialogs and sheets |

### 1.5 Pocket, the budget character (concept approved 5 October 2026)

Pocket is a golden money pouch with a face, arms and legs. Concept art lives in the Figma file "Pocket / Character kit". The artwork is raster, so this section fixes how he is used, not how he is drawn.

| Budget left | Pose | Where the number says |
| --- | --- | --- |
| More than 60% | Full: proud, hands on hips | "Left to spend" |
| 30% to 60% | Halfway: wink, thumbs up | "Left to spend" |
| Under 30% | Nearly empty: floppy, one last coin | "Left to spend" |
| 0 or less | Over budget: empty lining, long receipt, sheepish shrug | "Over budget by" |

- Extra poses: **Spending** (coin leaves, a little pffft, settles to the new size), **Payday** (proposed for "new month starts"; income is not in the requirements), **Idle** (blink, foot tap, sideways glance).
- Hero total (decision 5 October 2026): "of ₹X in limits" is the sum of the category limits you set. Left to spend is that sum minus counted spending in those categories. Pocket's pose uses left divided by that sum. With no limits set there is no total and no Pocket level. Pending review items are not subtracted; the hero says "Not counting ₹Y pending".
- Pocket never carries information alone: the amount and status words always sit beside him.
- He never scolds. Over budget uses the shrug, not an alarm. The status colour and words stay on the category rows.
- Placed bottom-right on the hero card, about 100 to 160dp wide, with a soft drop shadow. The amount stays the largest element.
- With Android "remove animations" on, every pose is a still image and transitions are instant.
- Use only on the Overview hero, the welcome screen, empty states, and the capture feedback sheet when the purchase counted and no threshold was crossed. Never on errors, warnings, over-budget sheets, Review or sharing screens: his pose follows the whole month, so he does not react to one category.
- Optional: Settings has a "Show Pocket" switch (on by default) that turns him off everywhere; layouts then close the gap.
- Open for production: transparent backgrounds, vector or layered artwork for animation, confirmed rights to the artwork.

## 2. Typography

**Family:** DM Sans (open source, geometric and friendly, tabular figures, includes ₹). Fallback: system sans. Verify the ₹ glyph and tabular numerals on both pilot phones in the first build.

| Token | Size / line | Weight | Use |
| --- | --- | --- | --- |
| `amount-hero` | 40sp / 48 | 600, tabular | Remaining amount on a category or feedback sheet |
| `headline` | 28sp / 36 | 600 | Screen titles |
| `title` | 20sp / 28 | 600 | Section titles, card titles |
| `title-small` | 16sp / 24 | 600 | List row titles |
| `body` | 16sp / 24 | 400 | Default text |
| `body-small` | 14sp / 20 | 400 | Secondary text |
| `label` | 14sp / 20 | 600 | Buttons, chips, tabs |
| `caption` | 12sp / 16 | 500 | Metadata only, never essential information |

- Amounts use tabular numerals and Indian grouping: `₹1,00,000.00`.
- Minimum text size 12sp. Layouts must hold at **200% font size**: no clipped actions, text wraps instead of truncating essential values.
- Sentence case everywhere. Write plainly: "You've used 80% of Fashion", not "Threshold exceeded".

## 3. Spacing

Base unit 4dp.

| Token | dp | Use |
| --- | --- | --- |
| `space-1` | 4 | Icon-to-text gap |
| `space-2` | 8 | Tight related items |
| `space-3` | 12 | Chip gaps, row inner gap |
| `space-4` | 16 | **Screen margin on phones**, card padding |
| `space-5` | 20 | Card padding on tall cards |
| `space-6` | 24 | Between sections; screen margin on larger widths |
| `space-8` | 32 | Large gaps |
| `space-12` | 48 | Minimum touch target; hero spacing |

Touch targets are at least **48 × 48dp**. Lists have 8dp between cards, 24dp between sections.

## 4. Shape

| Token | dp | Use |
| --- | --- | --- |
| `radius-sm` | 8 | Small chips, tags |
| `radius-md` | 16 | Text fields, list cards |
| `radius-lg` | 24 | Category cards, sheets (top corners 28) |
| `radius-full` | 999 | Buttons, FAB, nav pill, status chips |

Buttons are pill-shaped. Cards are generous and soft.

## 5. Elevation

Mostly flat. Separation comes from surface colour and a 1dp `outline` border, not shadows.

| Level | Treatment | Use |
| --- | --- | --- |
| 0 | `bg` | Page |
| 1 | `surface` + 1dp `outline` | Cards |
| 2 | `surface` + soft shadow (0 2 8 at 8% `text`) | FAB, bottom sheet |
| 3 | `surface` + shadow (0 6 20 at 12%) + scrim | Dialogs |

## 6. Motion

| Token | Duration | Easing | Use |
| --- | --- | --- | --- |
| `fast` | 150ms | ease-out | Press feedback, chip select |
| `base` | 250ms | ease-out | Sheets, card expand, snackbar |
| `slow` | 350ms | ease-in-out | Screen transitions |
| `meter` | 400ms | ease-out | Budget meter filling after a new expense |

- When Android "remove animations" is on, replace all motion with instant changes.
- Success moments are small: the meter moves, a short check appears. No confetti.

## 6.1 Sizes of key components

| Component | Size |
| --- | --- |
| Button | 48dp high, 24dp horizontal padding, pill |
| Extended FAB ("Add expense") | 56dp high |
| Text field | 56dp high |
| Chip | 40dp visual, 48dp touch |
| Bottom navigation | 80dp, 4 items, icon + label always shown |
| Category card | min 96dp high |
| Top app bar | 64dp |

## 7. Breakpoints (adaptive layout)

Corrected 5 October 2026: the Z Flip 5 is a clamshell, so unfolded it is a normal tall phone. Window sizes below are estimates from the screen specs and must be measured on both pilot phones in the first build.

| Window | Pilot case | Layout |
| --- | --- | --- |
| Compact (< 600dp wide) | S25 Ultra portrait, Z Flip 5 unfolded portrait | Single column, bottom navigation |
| Compact, small and nearly square (about 290dp, estimate) | Z Flip 5 cover screen | Glance layout: one number and one action, no navigation bar (see adaptive_v1) |
| Compact, short (height < 480dp) | Z Flip 5 in flex mode (half folded), any phone in landscape | Content above the fold, actions below it; no pinned bars taller than 80dp |
| Medium (600 to 839dp) | None in the pilot (kept for tablets and split screen) | Navigation rail; list and detail panes |
| Expanded (840dp and wider) | S25 Ultra landscape (about 900dp, estimate) | Navigation rail; Overview in two columns; Transactions and Review as list plus detail; sheets become side panels |

Folding and rotation: content must survive resize without losing state, scroll position, an open sheet, or text typed into a field (use saved state; do not rely on the activity recreating cleanly).

## 8. Accessibility rules baked into the tokens

- Touch: any tappable text, icon or chip without a 48dp visual box gets a 48dp touch area (links such as "Show", "Remove", "Restore", the sync chip, filter chips).
- 200% font size: no fixed heights on content. Cards, hero and buttons use minimum heights and grow; sheets and pinned-button screens scroll their content above the buttons; amounts wrap instead of clipping, including ₹1,00,00,000. Bottom navigation labels are capped at 130% scale and may wrap to two lines.
- Status banners always carry an icon as well as words.
- Selected state on segmented switches and chips is a tint, an outline and a check, never weight or colour alone.
- Deleted items are struck through at full contrast, never faded.

- Every status = colour + icon + words.
- Focus and selected states have a visible shape change (outline or tint pill), not colour alone.
- All icon-only buttons have a content description.
- Currency is announced as "rupees" with paise ("one thousand two hundred rupees and fifty paise").
- Text, icons and controls meet the contrast figures above in both themes.

## 9. Anti-generic checks (applied to every screen)

- No purple gradients, no neon, no blob backgrounds.
- No identical-looking card stacks: the hero card, the pending-review row and the attention list each look different. Overview category rows carry a status line, not a decorative dot.
- Real wording from the product, never filler.
- One filled primary button per screen; the rest are tonal, outlined or text.
