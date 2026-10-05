package com.expensetracker.app.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** Colours that have no Material 3 role. Values come from .planning/design/system/tokens.md. */
@Immutable
data class ExtraColors(
    val textMuted: Color,
    val primaryBright: Color,
    val hero: Color,
    val onHero: Color,
    val onHeroSecondary: Color,
    val success: Color,
    val successContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val badge: Color,
    val onBadge: Color,
)

val LightColorScheme: ColorScheme = lightColorScheme(
    primary = Color(0xFF0C7A7C),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFDCEFEF),
    onPrimaryContainer = Color(0xFF0A5F61),
    secondary = Color(0xFF0C7A7C),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFDCEFEF),
    onSecondaryContainer = Color(0xFF0A5F61),
    tertiary = Color(0xFF0C7A7C),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFDCEFEF),
    onTertiaryContainer = Color(0xFF0A5F61),
    surfaceTint = Color.Transparent,
    surfaceBright = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFE9EFED),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFFFFFF),
    surfaceContainer = Color(0xFFFFFFFF),
    surfaceContainerHigh = Color(0xFFFFFFFF),
    surfaceContainerHighest = Color(0xFFE9EFED),
    background = Color(0xFFF3F6F5),
    onBackground = Color(0xFF1B2625),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B2625),
    surfaceVariant = Color(0xFFE9EFED),
    onSurfaceVariant = Color(0xFF566462),
    outline = Color(0xFF7D8B89),
    outlineVariant = Color(0xFFDDE5E3),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFBE3E0),
    onErrorContainer = Color(0xFFB3261E),
    inverseSurface = Color(0xFF1B2625),
    inverseOnSurface = Color(0xFFF3F6F5),
    inversePrimary = Color(0xFF8FE0DE),
    scrim = Color(0x800A1414),
)

val DarkColorScheme: ColorScheme = darkColorScheme(
    primary = Color(0xFF5FD0D2),
    onPrimary = Color(0xFF00373A),
    primaryContainer = Color(0xFF174E50),
    onPrimaryContainer = Color(0xFF5FD0D2),
    secondary = Color(0xFF5FD0D2),
    onSecondary = Color(0xFF00373A),
    secondaryContainer = Color(0xFF174E50),
    onSecondaryContainer = Color(0xFF5FD0D2),
    tertiary = Color(0xFF5FD0D2),
    onTertiary = Color(0xFF00373A),
    tertiaryContainer = Color(0xFF174E50),
    onTertiaryContainer = Color(0xFF5FD0D2),
    surfaceTint = Color.Transparent,
    surfaceBright = Color(0xFF232E2C),
    surfaceDim = Color(0xFF0F1615),
    surfaceContainerLowest = Color(0xFF0F1615),
    surfaceContainerLow = Color(0xFF192221),
    surfaceContainer = Color(0xFF192221),
    surfaceContainerHigh = Color(0xFF192221),
    surfaceContainerHighest = Color(0xFF232E2C),
    background = Color(0xFF0F1615),
    onBackground = Color(0xFFE9F0EF),
    surface = Color(0xFF192221),
    onSurface = Color(0xFFE9F0EF),
    surfaceVariant = Color(0xFF232E2C),
    onSurfaceVariant = Color(0xFFA9B8B6),
    outline = Color(0xFF7F8F8C),
    outlineVariant = Color(0xFF2A3634),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF4A1613),
    errorContainer = Color(0xFF4A1613),
    onErrorContainer = Color(0xFFF2B8B5),
    inverseSurface = Color(0xFFE9F0EF),
    inverseOnSurface = Color(0xFF0F1615),
    inversePrimary = Color(0xFF0C7A7C),
    scrim = Color(0x99000000),
)

val LightExtraColors = ExtraColors(
    textMuted = Color(0xFF5F6D6B),
    primaryBright = Color(0xFF0F8B8D),
    hero = Color(0xFF0A5C5E),
    onHero = Color(0xFFFFFFFF),
    onHeroSecondary = Color(0xFFCDE9E8),
    success = Color(0xFF2A7448),
    successContainer = Color(0xFFE3F2E8),
    warning = Color(0xFF8A5200),
    warningContainer = Color(0xFFFFF0D1),
    badge = Color(0xFF9A5B00),
    onBadge = Color(0xFFFFFFFF),
)

val DarkExtraColors = ExtraColors(
    textMuted = Color(0xFF8FA09D),
    primaryBright = Color(0xFF5FD0D2),
    hero = Color(0xFF12484A),
    onHero = Color(0xFFEAF8F8),
    onHeroSecondary = Color(0xFFB5DCDD),
    success = Color(0xFF7FD6A0),
    successContainer = Color(0xFF17301F),
    warning = Color(0xFFF2B85C),
    warningContainer = Color(0xFF3A2A0A),
    badge = Color(0xFFF2B85C),
    onBadge = Color(0xFF3A2A0A),
)

/** Category identity colours: graphics only, never the only identifier. */
object CategoryColors {
    val Groceries = Color(0xFF4C8C6A)
    val Dining = Color(0xFFC2693A)
    val Transport = Color(0xFF3F7FB5)
    val Travel = Color(0xFF4F8AA6)
    val Fashion = Color(0xFF8A7BC0)
    val Entertainment = Color(0xFFD2784F)
    val Health = Color(0xFFC4557A)
    val Household = Color(0xFF7A6A58)
    val Rent = Color(0xFF8E5BA8)
    val Utilities = Color(0xFFB0892A)
    val Other = Color(0xFF6B7280)
}
