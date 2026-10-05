package com.expensetracker.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalExtraColors = staticCompositionLocalOf { LightExtraColors }

/** Colours with no Material 3 role, read as `ExpenseTrackerTheme.extraColors`. */
object ExpenseTrackerTheme {
    val extraColors: ExtraColors
        @Composable
        @ReadOnlyComposable
        get() = LocalExtraColors.current
}

/** Follows the system light or dark setting. Dynamic colour is off: the palette is fixed. */
@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    val extra = if (darkTheme) DarkExtraColors else LightExtraColors
    CompositionLocalProvider(LocalExtraColors provides extra) {
        MaterialTheme(
            colorScheme = colors,
            typography = AppTypography,
            shapes = AppShapes,
            content = content,
        )
    }
}
