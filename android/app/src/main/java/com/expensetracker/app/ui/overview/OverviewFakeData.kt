package com.expensetracker.app.ui.overview

import com.expensetracker.app.domain.budget.CategoryBudget
import com.expensetracker.app.domain.budget.DaysToGo
import com.expensetracker.app.domain.budget.PendingReview
import com.expensetracker.app.domain.money.Paise

/** Fake data for the scaffold only: the six hero states of the design, with the same figures as the mockups. */
object OverviewFakeData {
    private fun rs(rupees: Long) = Paise.rupees(rupees)

    private fun limited(groceries: Long, dining: Long, fashion: Long) = listOf(
        CategoryRow("Groceries", CategoryBudget(rs(8_000), rs(groceries))),
        CategoryRow("Dining and food delivery", CategoryBudget(rs(3_600), rs(dining))),
        CategoryRow("Fashion", CategoryBudget(rs(4_000), rs(fashion))),
        CategoryRow("Health", CategoryBudget(rs(0), rs(0))),
        CategoryRow("Transport", CategoryBudget(null, rs(500))),
        CategoryRow("Travel", CategoryBudget(null, rs(0))),
        CategoryRow("Entertainment", CategoryBudget(null, rs(0))),
        CategoryRow("Rent and housing", CategoryBudget(null, rs(0))),
        CategoryRow("Utilities and bills", CategoryBudget(null, rs(0))),
        CategoryRow("Household items", CategoryBudget(null, rs(0))),
        CategoryRow("Other", CategoryBudget(null, rs(0))),
    )

    private val pendingItems = PendingReview(knownAmount = rs(2_340), knownItems = 3, unknownAmountItems = 1, foreignCurrencyItems = 1)

    val states: List<OverviewState> = listOf(
        OverviewState("Full", limited(0, 0, 0), daysToGo = DaysToGo.Days(30)),
        OverviewState("Halfway", limited(1_800, 2_880, 4_400), pendingItems, daysToGo = DaysToGo.Days(26)),
        OverviewState("Nearly empty", limited(7_000, 3_500, 3_540), pendingItems, daysToGo = DaysToGo.Days(5)),
        OverviewState("Empty", limited(8_000, 3_600, 4_000), daysToGo = DaysToGo.Days(3)),
        OverviewState("Over budget", limited(8_000, 3_600, 4_400), daysToGo = DaysToGo.Days(2)),
        OverviewState(
            pickerLabel = "No limit",
            categories = listOf(
                CategoryRow("Groceries", CategoryBudget(null, rs(1_800))),
                CategoryRow("Dining and food delivery", CategoryBudget(null, rs(2_880))),
                CategoryRow("Fashion", CategoryBudget(null, rs(4_400))),
            ),
            daysToGo = DaysToGo.Days(26),
        ),
    )
}
