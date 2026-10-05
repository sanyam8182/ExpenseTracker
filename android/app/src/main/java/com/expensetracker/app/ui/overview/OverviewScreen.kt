package com.expensetracker.app.ui.overview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.expensetracker.app.R
import com.expensetracker.app.domain.budget.CategoryBudget
import com.expensetracker.app.domain.budget.CategoryStatus
import com.expensetracker.app.domain.budget.DaysToGo
import com.expensetracker.app.domain.budget.HeroBudget
import com.expensetracker.app.domain.budget.HeroLabel
import com.expensetracker.app.domain.budget.categoryStatus
import com.expensetracker.app.domain.budget.heroBudget
import com.expensetracker.app.domain.budget.needsALook
import com.expensetracker.app.domain.budget.toPocketBudget
import com.expensetracker.app.domain.money.IndianCurrency
import com.expensetracker.app.domain.money.Paise
import com.expensetracker.app.ui.theme.AppText
import com.expensetracker.app.ui.theme.ExpenseTrackerTheme
import com.expensetracker.app.ui.theme.Spacing
import com.expensetracker.pocket.Pocket
import com.expensetracker.pocket.PocketCords
import com.expensetracker.pocket.PocketEvent

private val HeroMinHeight = 196.dp
private val PocketSize = 136.dp

/** The Overview: one hero, one pending row, only the categories that need attention (handoff 01). */
@Composable
fun OverviewScreen(
    state: OverviewState,
    showPocket: Boolean,
    pocketEvent: PocketEvent?,
    modifier: Modifier = Modifier,
) {
    val hero = remember(state) { heroBudget(state.categories.map { it.budget }, state.pending) }
    val attention = remember(state) {
        needsALook(state.categories.map { it.name to it.budget }, state.firstThresholdPercent)
    }
    val limited = state.categories.filter { it.budget.limit != null }
    val onTrackCount = limited.size - attention.size
    val noLimitCount = state.categories.size - limited.size

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).padding(horizontal = Spacing.s4),
        verticalArrangement = Arrangement.spacedBy(Spacing.s3),
    ) {
        HeroCard(hero, state.daysToGo, showPocket, pocketEvent)
        hero.pending?.let { PendingRow(it.amount, it.items) }
        if (limited.isNotEmpty()) {
            NeedsALookList(attention)
            if (onTrackCount > 0 || noLimitCount > 0) OnTrackRow(onTrackCount, noLimitCount)
        }
        Spacer(Modifier.height(96.dp)) // room for the add-expense button
    }
}

@Composable
private fun HeroCard(hero: HeroBudget, daysToGo: DaysToGo?, showPocket: Boolean, pocketEvent: PocketEvent?) {
    val extra = ExpenseTrackerTheme.extraColors
    val label = stringResource(
        when (hero.label) {
            HeroLabel.LeftToSpend -> R.string.hero_left_to_spend
            HeroLabel.OverBudgetBy -> R.string.hero_over_budget_by
            HeroLabel.SpentThisMonth -> R.string.hero_spent_this_month
        },
    )
    val amountText = IndianCurrency.format(hero.amount)
    val limitsText = hero.limitsTotal?.let { stringResource(R.string.hero_of_limits, IndianCurrency.format(it)) }
    val daysText = daysToGo?.let {
        when (it) {
            DaysToGo.LastDay -> stringResource(R.string.hero_last_day)
            is DaysToGo.Days -> if (it.count == 1) stringResource(R.string.hero_one_day_to_go) else stringResource(R.string.hero_days_to_go, it.count)
        }
    }
    val pendingText = hero.pending?.let { stringResource(R.string.hero_pending_amount, IndianCurrency.format(it.amount)) }

    // One announcement for the whole card; Pocket is decorative (handoff README section 8).
    val spoken = buildString {
        append(stringResource(R.string.hero_description, label, IndianCurrency.spoken(hero.amount)))
        hero.limitsTotal?.let { append(", ").append(stringResource(R.string.hero_description_limits, IndianCurrency.spoken(it))) }
        daysText?.let { append(", ").append(it) }
        hero.pending?.let { append(". ").append(stringResource(R.string.hero_description_pending, IndianCurrency.spoken(it.amount))) }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(extra.hero)
            .defaultMinSize(minHeight = HeroMinHeight)
            .semantics(mergeDescendants = true) { contentDescription = spoken },
    ) {
        Column(modifier = Modifier.padding(Spacing.s5), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = extra.onHeroSecondary)
            Text(amountText, style = AppText.amountHero, color = extra.onHero)
            limitsText?.let { Text(it, style = MaterialTheme.typography.bodyMedium, color = extra.onHeroSecondary) }
            daysText?.let { Text(it, style = MaterialTheme.typography.bodyMedium, color = extra.onHeroSecondary) }
            if (pendingText != null) {
                Spacer(Modifier.height(Spacing.s2))
                Text(stringResource(R.string.hero_not_counting), style = MaterialTheme.typography.bodyMedium, color = extra.onHeroSecondary)
                Text(pendingText, style = MaterialTheme.typography.bodyMedium, color = extra.onHeroSecondary)
            }
        }
        if (showPocket) {
            Pocket(
                budget = hero.toPocketBudget(),
                modifier = Modifier.size(PocketSize).align(Alignment.BottomEnd).offset(x = 8.dp, y = 14.dp),
                event = pocketEvent,
                cords = PocketCords.ContrastGold,
            )
        }
    }
}

@Composable
private fun PendingRow(amount: Paise, items: Int) {
    val extra = ExpenseTrackerTheme.extraColors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { /* opens Review once that tab exists */ }
            .padding(horizontal = Spacing.s4, vertical = Spacing.s3),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.s3),
    ) {
        Icon(Icons.Filled.Email, contentDescription = null, tint = extra.warning)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                stringResource(R.string.pending_row_title, IndianCurrency.format(amount), pluralStringResource(R.plurals.review_items, items, items)),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(stringResource(R.string.pending_row_subtitle), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun NeedsALookList(attention: List<Pair<String, CategoryBudget>>) {
    Text(stringResource(R.string.needs_a_look), style = MaterialTheme.typography.titleMedium)
    if (attention.isEmpty()) {
        Text(stringResource(R.string.all_on_track), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        return
    }
    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column {
            attention.forEachIndexed { index, (name, budget) ->
                if (index > 0) Box(Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outlineVariant))
                AttentionRow(name, budget)
            }
        }
    }
}

@Composable
private fun AttentionRow(name: String, budget: CategoryBudget) {
    val extra = ExpenseTrackerTheme.extraColors
    val status = categoryStatus(budget, firstThresholdPercent = 0)
    val limit = budget.limit ?: return
    val (color, text) = when (status) {
        is CategoryStatus.OverBudget -> MaterialTheme.colorScheme.error to stringResource(R.string.status_over_budget_by, IndianCurrency.format(status.by))
        is CategoryStatus.Warning -> extra.warning to stringResource(R.string.status_percent_used, status.percentUsed)
        else -> MaterialTheme.colorScheme.onSurfaceVariant to ""
    }
    val fraction = if (limit.isZero) 1f else (budget.spent.value.toDouble() / limit.value.toDouble()).coerceIn(0.0, 1.0).toFloat()
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.s4, vertical = 14.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            Text(
                stringResource(R.string.category_spent_of_limit, IndianCurrency.format(budget.spent), IndianCurrency.format(limit)),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(Spacing.s1)) {
            Icon(Icons.Filled.Warning, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
            Text(text, style = MaterialTheme.typography.bodyMedium, color = color)
        }
        Spacer(Modifier.height(Spacing.s2))
        ProgressBar(fraction, color)
    }
}

@Composable
private fun ProgressBar(fraction: Float, color: Color) {
    Box(Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(MaterialTheme.colorScheme.outlineVariant)) {
        Box(Modifier.fillMaxWidth(fraction).fillMaxHeight().background(color))
    }
}

@Composable
private fun OnTrackRow(onTrack: Int, noLimit: Int) {
    val parts = buildList {
        if (onTrack > 0) add(pluralStringResource(R.plurals.more_on_track, onTrack, onTrack))
        if (noLimit > 0) add(pluralStringResource(R.plurals.have_no_limit, noLimit, noLimit))
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.s1),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
    ) {
        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = ExpenseTrackerTheme.extraColors.success, modifier = Modifier.size(18.dp))
        Text(parts.joinToString(". ") + ".", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
