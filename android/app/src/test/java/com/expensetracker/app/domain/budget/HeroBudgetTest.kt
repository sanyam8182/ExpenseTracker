package com.expensetracker.app.domain.budget

import com.expensetracker.app.domain.money.Paise
import java.time.LocalDate
import java.time.YearMonth
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

private fun rs(rupees: Long) = Paise.rupees(rupees)
private fun cat(limit: Long?, spent: Long) = CategoryBudget(limit = limit?.let(::rs), spent = rs(spent))

class HeroBudgetTest {
    private val mixed = listOf(
        cat(limit = 8_000, spent = 1_800),   // Groceries
        cat(limit = 3_600, spent = 2_880),   // Dining
        cat(limit = 4_000, spent = 4_400),   // Fashion, over its own limit
        cat(limit = 0, spent = 0),           // Health, zero limit
        cat(limit = null, spent = 500),      // Transport, no limit
    )

    @Test
    fun sumsLimitsAndSpendingOnlyInLimitedCategories() {
        val hero = heroBudget(mixed)
        assertEquals(HeroKind.Left, hero.kind)
        assertEquals(HeroLabel.LeftToSpend, hero.label)
        assertEquals(rs(15_600), hero.limitsTotal)
        assertEquals(rs(9_080), hero.spentInLimited)
        assertEquals(rs(9_580), hero.totalSpent)
        assertEquals(rs(6_520), hero.amount)
    }

    @Test
    fun spendingInNoLimitCategoriesDoesNotMoveTheAmountLeft() {
        val withExtra = mixed + cat(limit = null, spent = 99_999)
        assertEquals(rs(6_520), heroBudget(withExtra).amount)
    }

    @Test
    fun overBudgetShowsThePositiveOvershootAndTheOverLabel() {
        val hero = heroBudget(listOf(cat(limit = 4_000, spent = 4_400)))
        assertEquals(HeroKind.OverBudget, hero.kind)
        assertEquals(HeroLabel.OverBudgetBy, hero.label)
        assertEquals(rs(400), hero.amount)
    }

    @Test
    fun exactlyZeroLeftStaysLeftToSpendWithZero() {
        val hero = heroBudget(listOf(cat(limit = 1_000, spent = 1_000)))
        assertEquals(HeroKind.Left, hero.kind)
        assertEquals(HeroLabel.LeftToSpend, hero.label)
        assertEquals(Paise.Zero, hero.amount)
    }

    @Test
    fun noLimitsShowsSpentThisMonthAndNoPocketLimit() {
        val hero = heroBudget(listOf(cat(limit = null, spent = 9_080), cat(limit = null, spent = 0)))
        assertEquals(HeroKind.SpentNoLimit, hero.kind)
        assertEquals(HeroLabel.SpentThisMonth, hero.label)
        assertEquals(rs(9_080), hero.amount)
        assertNull(hero.limitsTotal)
    }

    @Test
    fun noCategoriesAtAllIsNoLimitWithZeroSpent() {
        val hero = heroBudget(emptyList())
        assertEquals(HeroKind.SpentNoLimit, hero.kind)
        assertEquals(Paise.Zero, hero.amount)
        assertNull(hero.limitsTotal)
    }

    @Test
    fun aConfiguredZeroLimitIsALimit() {
        val untouched = heroBudget(listOf(cat(limit = 0, spent = 0)))
        assertEquals(HeroKind.Left, untouched.kind)
        assertEquals(Paise.Zero, untouched.limitsTotal)
        assertEquals(Paise.Zero, untouched.amount)

        val spent = heroBudget(listOf(cat(limit = 0, spent = 100)))
        assertEquals(HeroKind.OverBudget, spent.kind)
        assertEquals(rs(100), spent.amount)
    }

    @Test
    fun refundsRaiseTheAmountLeftBecauseSpentIsNet() {
        val before = heroBudget(listOf(cat(limit = 4_000, spent = 3_000)))
        val afterRefund = heroBudget(listOf(cat(limit = 4_000, spent = 2_600)))
        assertEquals(rs(1_000), before.amount)
        assertEquals(rs(1_400), afterRefund.amount)
    }

    @Test
    fun pendingLineAppearsOnlyForKnownAmountItemsAndIsNeverSubtracted() {
        val none = heroBudget(mixed, PendingReview())
        assertNull(none.pending)

        val pending = PendingReview(knownAmount = rs(2_340), knownItems = 3, unknownAmountItems = 1, foreignCurrencyItems = 1)
        val hero = heroBudget(mixed, pending)
        assertEquals(PendingLine(rs(2_340), 3), hero.pending)
        assertEquals(rs(6_520), hero.amount)
        assertEquals(1, hero.unknownAmountItems)
        assertEquals(1, hero.foreignCurrencyItems)
    }

    @Test
    fun unknownAndForeignItemsAloneDoNotCreateAPendingFigure() {
        val hero = heroBudget(mixed, PendingReview(unknownAmountItems = 2, foreignCurrencyItems = 1))
        assertNull(hero.pending)
        assertEquals(2, hero.unknownAmountItems)
        assertEquals(1, hero.foreignCurrencyItems)
    }

    @Test
    fun impossibleSumsThrowInsteadOfWrapping() {
        val huge = CategoryBudget(limit = Paise(Long.MAX_VALUE), spent = Paise.Zero)
        assertThrows(ArithmeticException::class.java) { heroBudget(listOf(huge, huge)) }
    }
}

class MonthProgressTest {
    private val october = YearMonth.of(2026, 10)

    @Test
    fun countsDaysAfterTodayInTheCurrentMonth() {
        assertEquals(DaysToGo.Days(26), MonthProgress.daysToGo(LocalDate.of(2026, 10, 5), october))
        assertEquals(DaysToGo.Days(30), MonthProgress.daysToGo(LocalDate.of(2026, 10, 1), october))
        assertEquals(DaysToGo.Days(1), MonthProgress.daysToGo(LocalDate.of(2026, 10, 30), october))
    }

    @Test
    fun lastDayOfTheMonth() {
        assertEquals(DaysToGo.LastDay, MonthProgress.daysToGo(LocalDate.of(2026, 10, 31), october))
    }

    @Test
    fun pastAndFutureMonthsHaveNoCountdown() {
        assertNull(MonthProgress.daysToGo(LocalDate.of(2026, 10, 5), YearMonth.of(2026, 9)))
        assertNull(MonthProgress.daysToGo(LocalDate.of(2026, 10, 5), YearMonth.of(2026, 11)))
    }

    @Test
    fun februaryInALeapYearHasTwentyNineDays() {
        assertEquals(DaysToGo.Days(28), MonthProgress.daysToGo(LocalDate.of(2028, 2, 1), YearMonth.of(2028, 2)))
        assertEquals(DaysToGo.LastDay, MonthProgress.daysToGo(LocalDate.of(2028, 2, 29), YearMonth.of(2028, 2)))
    }
}
