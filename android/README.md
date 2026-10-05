# Expense Tracker (Android)

Native Kotlin and Jetpack Compose. Android 16 (API 36) only. Money is integer paise throughout.

## Before you build

- JDK 17 and the Android SDK for API 36 (set `sdk.dir` in `android/local.properties`, which is not committed).
- The Pocket character module is included by path from `../assets/pocket/android/pocket`. That folder is not in the repository yet (licence undecided), so a fresh clone will not build until it is copied there.

## Commands

Run from the `android` folder. On Windows use `gradlew.bat`.

```
./gradlew :app:assembleDebug        # debug APK
./gradlew :app:testDebugUnitTest    # app unit tests
./gradlew :pocket:testDebugUnitTest # Pocket module tests
./gradlew :app:lintDebug            # lint
./gradlew :app:installDebug         # install on a connected device or emulator
```

## Layout

- `app/.../domain` - money, hero budget, category status. Pure Kotlin, covered by unit tests.
- `app/.../ui` - theme tokens, navigation, Overview screen, sync chip.
- `pocket` - the character, from `assets/pocket`.

## Scaffold state

The Overview runs on fake data with a picker for the six hero states. The add-expense button only plays Pocket's Spending animation. Other tabs are placeholders. Design specs are in `.planning/design`.
