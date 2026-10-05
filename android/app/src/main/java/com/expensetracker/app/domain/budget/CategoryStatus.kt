package com.expensetracker.app.domain.budget

import com.expensetracker.app.domain.money.Paise

/** Status of one category for the viewed month (BUD 02). Every status is shown with an icon and words, never colour alone. */
sealed interface CategoryStatus {
    data object NoLimit : CategoryStatus
    data object OnTrack : CategoryStatus

    /** At or past the first warning threshold but not over the limit. [percentUsed] is the real figure, rounded down. */
    data class Warning(val percentUsed: Int) : CategoryStatus

    data class OverBudget(val by: Paise) : CategoryStatus
}

/**
 * No limit has no percentage. A limit of zero is never "used" while nothing is spent and is over budget as soon
 * as anything is. Exactly at the limit is a warning at 100%, not over budget. Overflow throws.
 */
fun categoryStatus(budget: CategoryBudget, firstThresholdPercent: Int): CategoryStatus {
    val limit = budget.limit ?: return CategoryStatus.NoLimit
    val spent = budget.spent
    if (spent > limit) return CategoryStatus.OverBudget(spent - limit)
    if (limit.isZero) return CategoryStatus.OnTrack
    val percentUsed = Math.multiplyExact(spent.value, 100L) / limit.value
    return if (percentUsed >= firstThresholdPercent) CategoryStatus.Warning(percentUsed.toInt()) else CategoryStatus.OnTrack
}

/**
 * The "Needs a look" list: categories that are over budget or at the first warning threshold.
 * Over budget first (largest overshoot first), then warnings by percent used, highest first.
 */
fun <T> needsALook(items: List<Pair<T, CategoryBudget>>, firstThresholdPercent: Int): List<Pair<T, CategoryBudget>> =
    items
        .map { it to categoryStatus(it.second, firstThresholdPercent) }
        .filter { it.second is CategoryStatus.OverBudget || it.second is CategoryStatus.Warning }
        .sortedWith(
            compareBy<Pair<Pair<T, CategoryBudget>, CategoryStatus>> { if (it.second is CategoryStatus.OverBudget) 0 else 1 }
                .thenByDescending { (it.second as? CategoryStatus.OverBudget)?.by?.value ?: 0L }
                .thenByDescending { (it.second as? CategoryStatus.Warning)?.percentUsed ?: 0 },
        )
        .map { it.first }
