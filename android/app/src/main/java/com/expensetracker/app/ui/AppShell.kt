package com.expensetracker.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.expensetracker.app.R
import com.expensetracker.app.ui.components.SyncChip
import com.expensetracker.app.ui.components.SyncStatus
import com.expensetracker.app.ui.nav.AppDestination
import com.expensetracker.app.ui.nav.navigateToTab
import com.expensetracker.app.ui.overview.OverviewFakeData
import com.expensetracker.app.ui.overview.OverviewScreen
import com.expensetracker.app.ui.theme.ExpenseTrackerTheme
import com.expensetracker.app.ui.theme.PillShape
import com.expensetracker.app.ui.theme.Sizes
import com.expensetracker.app.ui.theme.Spacing
import com.expensetracker.pocket.PocketAction
import com.expensetracker.pocket.PocketEvent

/**
 * Four tabs, a sync chip in the top bar and the add-expense button (handoff README section 2).
 * Scaffold stage: the Overview runs on fake data with a picker for the six hero states, and the
 * add-expense button only plays Pocket's Spending animation. Other tabs are placeholders.
 */
@Composable
fun AppShell(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val current = AppDestination.fromRoute(backStack?.destination?.route)

    var stateIndex by rememberSaveable { mutableIntStateOf(1) }
    var eventId by rememberSaveable { mutableLongStateOf(0L) }
    val pocketEvent = if (eventId > 0) PocketEvent(eventId, PocketAction.Spending) else null
    val reviewBadgeCount = 5

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { TopBar(title = stringResource(current.label), sync = SyncStatus.Synced(minutesAgo = 2)) },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                AppDestination.entries.forEach { destination ->
                    val selected = destination == current
                    NavigationBarItem(
                        selected = selected,
                        onClick = { navController.navigateToTab(destination) },
                        icon = {
                            if (destination == AppDestination.Review && reviewBadgeCount > 0) {
                                BadgedBox(badge = {
                                    Badge(
                                        containerColor = ExpenseTrackerTheme.extraColors.badge,
                                        contentColor = ExpenseTrackerTheme.extraColors.onBadge,
                                        modifier = Modifier.defaultMinSize(minWidth = 20.dp, minHeight = 20.dp),
                                    ) { Text(reviewBadgeCount.toString(), style = MaterialTheme.typography.labelSmall) }
                                }) { Icon(destination.icon, contentDescription = null) }
                            } else {
                                Icon(destination.icon, contentDescription = null)
                            }
                        },
                        label = { Text(stringResource(destination.label), style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onSurface,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    )
                }
            }
        },
        floatingActionButton = {
            if (current.showsAddExpense) {
                ExtendedFloatingActionButton(
                    onClick = { eventId += 1 },
                    icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                    text = { Text(stringResource(R.string.add_expense), style = MaterialTheme.typography.titleMedium) },
                    shape = PillShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.defaultMinSize(minHeight = Sizes.extendedFab),
                )
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Overview.route,
            modifier = Modifier.fillMaxSize().padding(padding),
        ) {
            composable(AppDestination.Overview.route) {
                Column {
                    StatePicker(selected = stateIndex, onSelect = { stateIndex = it })
                    OverviewScreen(
                        state = OverviewFakeData.states[stateIndex],
                        showPocket = true,
                        pocketEvent = pocketEvent,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
            AppDestination.entries.filter { it != AppDestination.Overview }.forEach { destination ->
                composable(destination.route) { Placeholder(stringResource(destination.label)) }
            }
        }
    }
}

@Composable
private fun TopBar(title: String, sync: SyncStatus) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .defaultMinSize(minHeight = Sizes.topBar)
            .padding(horizontal = Spacing.s4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            SyncChip(status = sync, onRefresh = { /* manual refresh arrives with sync */ })
            IconButton(onClick = { /* Settings and recovery */ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = stringResource(R.string.more_options))
            }
        }
    }
}

/** Scaffold only: switches between the six hero states of the design. Remove once real data exists. */
@Composable
private fun StatePicker(selected: Int, onSelect: (Int) -> Unit) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = Spacing.s4, vertical = Spacing.s2),
        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
    ) {
        OverviewFakeData.states.forEachIndexed { index, state ->
            FilterChip(
                selected = index == selected,
                onClick = { onSelect(index) },
                label = { Text(state.pickerLabel, style = MaterialTheme.typography.labelLarge) },
                shape = PillShape,
            )
        }
    }
}

@Composable
private fun Placeholder(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(name, style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.coming_soon), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
