package com.expensetracker.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** DM Sans is bundled in plan task 5; until then the system sans is used so the app builds. */
val AppFontFamily: FontFamily = FontFamily.SansSerif

private const val TABULAR_FIGURES = "tnum"

/** The one text style Material 3 has no slot for: the large remaining amount. */
object AppText {
    val amountHero = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 40.sp,
        lineHeight = 48.sp,
        fontWeight = FontWeight.SemiBold,
        fontFeatureSettings = TABULAR_FIGURES,
    )
}

val AppTypography = Typography(
    headlineMedium = TextStyle(fontFamily = AppFontFamily, fontSize = 28.sp, lineHeight = 36.sp, fontWeight = FontWeight.SemiBold),
    titleLarge = TextStyle(fontFamily = AppFontFamily, fontSize = 20.sp, lineHeight = 28.sp, fontWeight = FontWeight.SemiBold),
    titleMedium = TextStyle(fontFamily = AppFontFamily, fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = TextStyle(fontFamily = AppFontFamily, fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight.Normal),
    bodyMedium = TextStyle(fontFamily = AppFontFamily, fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Normal),
    labelLarge = TextStyle(fontFamily = AppFontFamily, fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.SemiBold),
    labelSmall = TextStyle(fontFamily = AppFontFamily, fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium),
)
