package com.expensetracker.app.ui.theme

import androidx.compose.ui.unit.dp

/** Spacing scale on a 4dp base (tokens.md section 3). */
object Spacing {
    val s1 = 4.dp
    val s2 = 8.dp
    val s3 = 12.dp
    /** Screen margin on phones and card padding. */
    val s4 = 16.dp
    val s5 = 20.dp
    /** Between sections; screen margin on larger widths. */
    val s6 = 24.dp
    val s8 = 32.dp
    val s12 = 48.dp
}

/** Minimum touch target. Anything smaller looking still gets this much touch area. */
val MinTouchTarget = 48.dp

/** Component sizes from tokens.md section 6.1. */
object Sizes {
    val button = 48.dp
    val extendedFab = 56.dp
    val textField = 56.dp
    val chipVisual = 40.dp
    val bottomNav = 80.dp
    val topBar = 64.dp
}
