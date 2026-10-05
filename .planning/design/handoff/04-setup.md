# 04 — Setup, sign-in and permissions

Requirements: SEC 01, CAP 01, CAP 04, CAP 06, REC 01, AC 15. Foundations: `README.md`.

**Purpose:** first launch to a working app. Three separate capability flows (SMS, notification access, our own notifications), each explained before the Android dialog; denial never blocks manual entry.
**Route:** shown on first launch while signed out; the permission steps are reachable again from Settings > Capture health.

## Flow

Welcome and sign-in → permission 1 of 3 (SMS) → permission 2 of 3 (notification access, with the restricted-setting variant only when needed) → permission 3 of 3 (own alerts) → readiness → "Two things to know" → optional first limits → Overview. An account-confirmation sheet appears later whenever a new account or card is detected.

Layout for all steps: no bottom navigation; screen margin 16 to 24 dp; pinned button area at the bottom with content scrolling above it.

## 1. Welcome and sign-in
- Pocket (Full pose, about 150 dp) is shown here and nowhere else in setup.
- Title "Know what's left to spend"; text "Your bank alerts become expenses, on this phone."
- Three promises, each with a check: "Your data stays on your phone." / "No bank logins, ever." / "Shared budgets use a Google Sheet."
- Button "Sign in with Google" (follow Google's sign-in button branding rules in the build). Google sign-in is required to use the app; a previous valid local session works offline (SEC 01).
- States: sign-in cancelled → stay here. Offline → error variant: icon (no Pocket), title "You're offline", text "Signing in needs an internet connection. Connect and try again.", button "Try again". Loading and sign-in error states are not yet designed (use the standard error pattern).

## 2. Permission steps (shared layout)
Header "Step N of 3" with a three-segment progress line; icon tile (56 dp, 16 dp radius, primary tint); title (headline); lead text; two grouped info boxes; pinned buttons: filled primary and a text "Not now" of the same prominence.

### 2.1 Step 1 of 3, SMS
Title "Read new bank messages". Lead "So an SMS from your bank can become an expense without you typing it." Box "What we use": "Only new messages from your bank, from now on. We never import your old messages." Box "What we don't do": "Keep one-time passwords. They are never saved." / "Read personal chats." Buttons "Allow" (triggers the system SMS-receive permission; request only what new-message capture needs, no inbox import) and "Not now".

### 2.2 Step 2 of 3, notification access
Title "Read payment app alerts". Lead "So alerts from your bank and payment apps can become expenses too." Box "What we use": "Only alerts from a short list of bank and payment apps. Everything else is ignored and not stored." Box "What happens next": "Android opens its notification access settings. Turn on Expense Tracker, then come back." Buttons "Open settings", "Not now". Update status when the user returns.
- **Restricted-setting variant**, shown only when Android blocks the setting for a sideloaded APK: lead "Android is blocking this setting for apps installed outside the Play Store."; info banner "Only if Android says "Restricted setting" — These steps can differ slightly on your phone." with numbered steps: "Open Settings, then Apps, then Expense Tracker." / "Tap the three dots at the top right." / "Tap "Allow restricted settings"." / "Come back here and tap Open settings again." Buttons "Open app info", "Not now". Do not promise a universal workaround (CAP 01); validate the actual APK install route on both Samsung phones.
- The listener uses a versioned allowlist of exact verified package names; messaging-app mirrors are excluded when SMS capture is on; non-allowlisted notifications are dropped without storage (CAP 02).

### 2.3 Step 3 of 3, own alerts
Title "Show your own alerts". Lead "So we can tell you when an expense is recorded or needs a look." Box "Private by default": "On a locked phone, alerts say only "Expense recorded". Amounts and shops show after you unlock." Box "If you skip this": "You still see the same information inside the app." Buttons "Allow" (POST_NOTIFICATIONS), "Not now".

## 3. Readiness
Header "All set"; title "Here's what is ready". Three rows: status circle (✓ success or ! warning) + name + one line:
- "Bank SMS" — "Allowed. New messages are captured."
- "Payment app alerts" — denied: "Not allowed. Alerts from payment apps won't be captured." with link "Fix in settings".
- "Our own alerts" — "Allowed."
Info box "Manual entry always works": "Capture starts now. Alerts from before setup aren't imported." Button "Continue". Statuses re-evaluate after returning from settings and when a permission is revoked later (CAP 01); the same list is Settings > Capture health.

## 4. "Two things to know" (required statements)
Header "Before you start"; title "Two things to know". Box "Back up your data": "Everything is stored on this phone. If you lose it without an export, personal data can be lost. Export now and then in Settings, and we'll remind you." (REC 01). Box "Sharing has limits": "If you share a budget, anyone with edit access to its Google Sheet can read or change it outside the app. The app can't prevent that." (AC 15). Button "I understand". Shown once.

## 5. First limits (optional)
Header "Optional"; title "Set limits for October"; lead "Leave a category empty for no limit. You can change these any time." One 120 dp by 48 dp ₹ field per category (empty = "No limit"; zero is a valid limit), first five categories shown, a "6 more categories — Show" row expands. Pinned: "Save limits", text "Skip for now". Past months are never editable afterwards; current and future months are (BUD 05).

## 6. Account confirmation (modal sheet, any time)
Shown when a new bank account or card is first detected (CAP 04). Status "✉ New account found"; title "Is this card yours?"; text "HDFC card ending 1234. Earlier alerts from it are checked again once you confirm."; buttons "Yes, it's mine" (filled), "Not mine" (outlined), "Decide later" (text). Bank and account context, not last-four equality, establishes the mapping. Confirming re-runs earlier candidates once using their original occurrence date; undated items stay in Review. "Decide later" keeps the alerts in Review, outside every total (candidates with unresolved ownership never count).

## Accessibility and states
Each permission explanation is readable before the system dialog; "Allow" and "Not now" are equal-size buttons. Progress is spoken ("Step 2 of 3"). Denied or revoked states show in readiness and Capture health with words and an icon. Not yet designed: permission revoked after setup (use the Capture health row), sign-in loading.
