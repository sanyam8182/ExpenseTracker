package com.expensetracker.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/** Radii from tokens.md: 8 small tag, 16 fields and list cards, 24 cards, hero and dialogs, 28 sheet top corners. */
val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

/** Buttons, chips, the FAB, the navigation indicator and status chips. */
val PillShape = RoundedCornerShape(percent = 50)

/** Bottom sheets: 28dp on the top corners only. */
val SheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
