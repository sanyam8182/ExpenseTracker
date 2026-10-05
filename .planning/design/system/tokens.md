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
| Page | `bg` | `#FBF8F4` | Screen background (warm off-white) | text on it 14.1:1 |
| Card | `surface` | `#FFFFFF` | Cards, sheets, dialogs | text on it 15.0:1 |
| Quiet card | `surface-variant` | `#F3EEE7` | Chips, filled fields, grouped rows | |
| Divider | `outline` | `#D9D1C7` | Hairlines and card borders (decorative only) | 1.4:1, never the only boundary of a control |
| Control edge | `outline-strong` | `#857B71` | Text-field and checkbox outlines | 3.9:1 on bg |
| Text | `text` | `#2B2622` | Body and numbers | 14.1:1 |
| Supporting text | `text-secondary` | `#675E55` | Labels, captions | 6.0:1 on bg |
| Hint text | `text-muted` | `#756B61` | Placeholders, metadata | 4.9:1 on bg |
| Brand | `primary` | `#0C7A7C` | Filled buttons, active tab, links | 5.1:1 with white text |
| Brand text | `primary-text` | `#0A5F61` | Text on the brand tint | 6.3:1 on `primary-tint` |
| Brand tint | `primary-tint` | `#DCEFEF` | Selected chips, active nav pill | |
| Brand graphic | `primary-bright` | `#0F8B8D` | Progress fill, icons (graphics only) | 3.9:1 |
| On brand | `on-primary` | `#FFFFFF` | Text and icons on `primary` | 5.1:1 |
| On track | `success` | `#2A7448` | "On track" text/icon | 4.9:1 on tint |
| On track tint | `success-tint` | `#E3F2E8` | Status chip background | |
| Warning | `warning` | `#9A5B00` | "80% used", "Needs review" text/icon | 4.8:1 on tint |
| Warning tint | `warning-tint` | `#FFF0D1` | Warning chip, banner | |
| Over budget / error | `error` | `#B3261E` | "Over budget", errors | 5.3:1 on tint |
| Error tint | `error-tint` | `#FBE3E0` | Error chip, banner | |

### 1.2 Dark theme

| Token | Hex | Notes |
| --- | --- | --- |
| `bg` | `#14110F` | Warm near-black, not pure black |
| `surface` | `#1E1A17` | Cards |
| `surface-variant` | `#2A2521` | Chips, grouped rows |
| `outline` | `#4A423B` | Dividers |
| `outline-strong` | `#8F857A` | Control edges (3:1 or better) |
| `text` | `#F1EAE2` | 15.8:1 on bg |
| `text-secondary` | `#BDB3A8` | 8.4:1 on surface |
| `text-muted` | `#9A9087` | 6.0:1 on bg |
| `primary` | `#5FD0D2` | 10.2:1 on bg |
| `on-primary` | `#00373A` | 7.1:1 on primary |
| `primary-tint` | `#123F40` | Selected chip, active nav pill |
| `primary-text` | `#5FD0D2` | 6.3:1 on primary-tint |
| `success` / `success-tint` | `#7FD6A0` / `#17301F` | 8.1:1 |
| `warning` / `warning-tint` | `#F2B85C` / `#3A2A0A` | 7.8:1 |
| `error` / `error-tint` | `#F2B8B5` / `#4A1613` | 8.7:1 |

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

Eleven muted hues for the small dot/icon beside a category name. They are never the only identifier (the name is always written), and none uses the status colours.

| Category | Hex | Category | Hex |
| --- | --- | --- | --- |
| Groceries | `#4C8C6A` | Health | `#C4557A` |
| Dining and food delivery | `#C2693A` | Household items | `#7A6A58` |
| Transport | `#3F7FB5` | Rent and housing | `#8E5BA8` |
| Travel | `#4F8AA6` | Utilities and bills | `#B0892A` |
| Fashion | `#8A7BC0` | Other | `#6B7280` |
| Entertainment | `#D2784F` | | |

All are at least 3.2:1 on white. In dark theme use the same hex at 85% lightness shift (implementation derives the lighter variant from the same hue).

## 2. Typography

**Family:** DM Sans (open source, geometric and friendly, tabular figures, includes ₹). Fallback: system sans. Verify the ₹ glyph and tabular numerals on both pilot phones in the first build.

| Token | Size / line | Weight | Use |
| --- | --- | --- | --- |
| `amount-hero` | 40sp / 48 | 700, tabular | Remaining amount on a category or feedback sheet |
| `headline` | 28sp / 36 | 700 | Screen titles |
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

| Window | Example | Layout |
| --- | --- | --- |
| Compact (< 600dp) | S25 Ultra, Z Flip 5 cover/folded | Single column, bottom navigation |
| Medium (600–839dp) | Z Flip 5 unfolded (portrait), S25 Ultra landscape | Navigation rail; Transactions and Review use list + detail panes |
| Expanded (≥ 840dp) | Not a pilot target; behave like Medium | |

Folding: content must survive resize without losing state or scroll position.

## 8. Accessibility rules baked into the tokens

- Every status = colour + icon + words.
- Focus and selected states have a visible shape change (outline or tint pill), not colour alone.
- All icon-only buttons have a content description.
- Currency is announced as "rupees" with paise ("one thousand two hundred rupees and fifty paise").
- Text, icons and controls meet the contrast figures above in both themes.

## 9. Anti-generic checks (applied to every screen)

- No purple gradients, no neon, no blob backgrounds.
- No identical-looking card stacks: category cards carry their own dot colour and a status line; the pending-review banner and invitation banner look different from cards.
- Real wording from the product, never filler.
- One filled primary button per screen; the rest are tonal, outlined or text.
