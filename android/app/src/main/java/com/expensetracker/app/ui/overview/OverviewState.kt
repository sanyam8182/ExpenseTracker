package com.expensetracker.app.ui.overview

import com.expensetracker.app.domain.budget.CategoryBudget
import com.expensetracker.app.domain.budget.DaysToGo
import com.expensetracker.app.domain.budget.PendingReview

data class CategoryRow(val name: String, val budget: CategoryBudget)

/** Everything the Overview needs for one month and one budget. Stateless: the screen only renders this. */
data class OverviewState(
    /** Shown only in the scaffold's state picker. */
    val pickerLabel: String,
    val categories: List<CategoryRow>,
    val pending: PendingReview = PendingReview(),
    val firstThresholdPercent: Int = 80,
    val daysToGo: DaysToGo? = null,
)
