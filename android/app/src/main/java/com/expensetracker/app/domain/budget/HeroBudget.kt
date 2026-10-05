package com.expensetracker.app.domain.budget

import com.expensetracker.app.domain.money.Paise

/** One category for the viewed month. [spent] is net: counted purchases minus linked refunds (BUD 02). */
data class CategoryBudget(val limit: Paise?, val spent: Paise)

/** Review items that do not count yet (CAT 02). Only known-amount items have a figure. */
data class PendingReview(
    val knownAmount: Paise = Paise.Zero,
    val knownItems: Int = 0,
    val unknownAmountItems: Int = 0,
    val foreignCurrencyItems: Int = 0,
)

data class PendingLine(val amount: Paise, val items: Int)

enum class HeroKind { Left, OverBudget, SpentNoLimit }

enum class HeroLabel { LeftToSpend, OverBudgetBy, SpentThisMonth }

/**
 * What the Overview hero shows. [limitsTotal] and [spentInLimited] are exactly what the Pocket
 * character receives: one limit (null when no category has one) and the spending inside those categories.
 */
data class HeroBudget(
    val kind: HeroKind,
    val label: HeroLabel,
    /** Always zero or positive: the amount left, the overshoot, or the month's spending. */
    val amount: Paise,
    val limitsTotal: Paise?,
    val spentInLimited: Paise,
    val totalSpent: Paise,
    val pending: PendingLine?,
    val unknownAmountItems: Int,
    val foreignCurrencyItems: Int,
)

/**
 * Hero total (decision of 5 October 2026): the sum of the limits that are configured. Left to spend is that
 * sum minus spending in those categories only. Spending in no-limit categories never moves it, and pending
 * review items are never subtracted. A configured limit of zero is a limit.
 */
fun heroBudget(categories: List<CategoryBudget>, pending: PendingReview = PendingReview()): HeroBudget {
    val limited = categories.filter { it.limit != null }
    val totalSpent = categories.fold(Paise.Zero) { sum, category -> sum + category.spent }
    val spentInLimited = limited.fold(Paise.Zero) { sum, category -> sum + category.spent }
    val limitsTotal = if (limited.isEmpty()) null else limited.fold(Paise.Zero) { sum, category -> sum + (category.limit ?: Paise.Zero) }
    val pendingLine = if (pending.knownItems > 0) PendingLine(pending.knownAmount, pending.knownItems) else null

    val kind: HeroKind
    val label: HeroLabel
    val amount: Paise
    if (limitsTotal == null) {
        kind = HeroKind.SpentNoLimit
        label = HeroLabel.SpentThisMonth
        amount = totalSpent
    } else {
        val left = limitsTotal - spentInLimited
        if (left.isNegative) {
            kind = HeroKind.OverBudget
            label = HeroLabel.OverBudgetBy
            amount = -left
        } else {
            kind = HeroKind.Left
            label = HeroLabel.LeftToSpend
            amount = left
        }
    }

    return HeroBudget(
        kind = kind,
        label = label,
        amount = amount,
        limitsTotal = limitsTotal,
        spentInLimited = spentInLimited,
        totalSpent = totalSpent,
        pending = pendingLine,
        unknownAmountItems = pending.unknownAmountItems,
        foreignCurrencyItems = pending.foreignCurrencyItems,
    )
}
