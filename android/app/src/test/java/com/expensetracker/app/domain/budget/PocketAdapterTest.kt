package com.expensetracker.app.domain.budget

import com.expensetracker.app.domain.money.Paise
import com.expensetracker.pocket.PocketState
import com.expensetracker.pocket.visual
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

private fun hero(limit: Long?, spent: Long) =
    heroBudget(listOf(CategoryBudget(limit = limit?.let(Paise::rupees), spent = Paise.rupees(spent))))

private fun poseOf(limit: Long?, spent: Long): PocketState = hero(limit, spent).toPocketBudget().visual().state

class PocketAdapterTest {
    @Test
    fun passesTheSumOfLimitsAndTheSpendingInsideThem() {
        val budget = heroBudget(
            listOf(
                CategoryBudget(Paise.rupees(8_000), Paise.rupees(1_800)),
                CategoryBudget(Paise.rupees(3_600), Paise.rupees(2_880)),
                CategoryBudget(null, Paise.rupees(500)),
            ),
        ).toPocketBudget()
        assertEquals(Paise.rupees(11_600).value, budget.limitPaise)
        assertEquals(Paise.rupees(4_680).value, budget.confirmedNetSpentPaise)
    }

    @Test
    fun noConfiguredLimitPassesNullSoPocketIsIdle() {
        val budget = hero(limit = null, spent = 9_080).toPocketBudget()
        assertNull(budget.limitPaise)
        assertEquals(PocketState.NoLimit, budget.visual().state)
    }

    @Test
    fun allSixHeroStatesMapToTheKitPoses() {
        assertEquals(PocketState.Full, poseOf(limit = 15_600, spent = 0))
        assertEquals(PocketState.Halfway, poseOf(limit = 15_600, spent = 7_800))
        assertEquals(PocketState.Low, poseOf(limit = 15_600, spent = 14_040))
        assertEquals(PocketState.Empty, poseOf(limit = 15_600, spent = 15_600))
        assertEquals(PocketState.OverBudget, poseOf(limit = 15_600, spent = 16_000))
        assertEquals(PocketState.NoLimit, poseOf(limit = null, spent = 9_080))
    }

    @Test
    fun boundariesAreSixtyAndThirtyPercent() {
        // 40% spent leaves exactly 60%: not above 60, so Halfway. One rupee less spent tips it to Full.
        assertEquals(PocketState.Halfway, poseOf(limit = 100, spent = 40))
        assertEquals(PocketState.Full, poseOf(limit = 100, spent = 39))
        // 70% spent leaves exactly 30%: still Halfway. One rupee more is Low.
        assertEquals(PocketState.Halfway, poseOf(limit = 100, spent = 70))
        assertEquals(PocketState.Low, poseOf(limit = 100, spent = 71))
    }

    @Test
    fun aZeroLimitIsEmptyUntilSomethingIsSpent() {
        assertEquals(PocketState.Empty, poseOf(limit = 0, spent = 0))
        assertEquals(PocketState.OverBudget, poseOf(limit = 0, spent = 1))
    }

    @Test
    fun budgetReadyIsOnlyPossibleForAPositiveLimitWithNothingSpent() {
        assertEquals(true, hero(limit = 15_600, spent = 0).toPocketBudget().visual().canCelebrateBudgetReady)
        assertEquals(false, hero(limit = 15_600, spent = 1).toPocketBudget().visual().canCelebrateBudgetReady)
        assertEquals(false, hero(limit = 0, spent = 0).toPocketBudget().visual().canCelebrateBudgetReady)
        assertEquals(false, hero(limit = null, spent = 0).toPocketBudget().visual().canCelebrateBudgetReady)
    }
}
