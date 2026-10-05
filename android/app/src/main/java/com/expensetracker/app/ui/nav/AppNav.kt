package com.expensetracker.app.ui.nav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.expensetracker.app.R

/** The four bottom-navigation destinations. Icons are placeholders until Material Symbols are added. */
enum class AppDestination(val route: String, @StringRes val label: Int, val icon: ImageVector) {
    Overview("overview", R.string.tab_overview, Icons.Filled.Home),
    Transactions("transactions", R.string.tab_transactions, Icons.AutoMirrored.Filled.List),
    Review("review", R.string.tab_review, Icons.Filled.Email),
    Budget("budget", R.string.tab_budget, Icons.Filled.DateRange),
    ;

    /** The add-expense button belongs to Overview and Transactions only. */
    val showsAddExpense: Boolean get() = this == Overview || this == Transactions

    companion object {
        fun fromRoute(route: String?): AppDestination = entries.firstOrNull { it.route == route } ?: Overview
    }
}

/** Each tab keeps its own saved state, so switching tabs restores scroll position and selection. */
fun NavController.navigateToTab(destination: AppDestination) {
    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
