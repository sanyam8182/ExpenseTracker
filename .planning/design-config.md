# Design Config — Expense Tracker

**Platform:** native Android 16 (Jetpack Compose), phone and foldable.
**Industry:** personal finance (budgeting).
**Personality:** warm and friendly; calm, readable, plain wording; numbers are the hero.
**Brand colour:** warm teal `#0C7A7C` (light) / `#5FD0D2` (dark). Overview hero card uses the deeper `#0A5C5E` (dark `#12484A`). Page background is a cool off-white `#F3F6F5` so the teal stands out.
**Character:** Pocket, a golden money pouch (concept approved 5 October 2026; Figma "Pocket / Character kit"). Shows how much budget is left; never carries information alone and never scolds.
**Overview rule:** one hero, one pending-review row, only categories that need attention. No decorative dots.
**Themes:** light and dark, follow the system setting.
**Font:** DM Sans (fallback system sans). Verify ₹ glyph and tabular numerals on both pilot phones.
**Shape:** pill buttons, 16–24dp rounded cards.
**Reference apps:** none.
**Binding artifacts:** `.planning/design/system/tokens.md`, `.planning/design/system/components.md`, `.planning/design/ideation/flows.md`.
**Rules:** every colour, size and radius comes from the tokens; every status uses colour + icon + words; layouts hold at 200% font size; 48dp touch targets; minimum 12sp text.
