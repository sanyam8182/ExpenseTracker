package com.expensetracker.app.domain.budget

import com.expensetracker.app.domain.money.Paise
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

private fun rs(rupees: Long) = Paise.rupees(rupees)

class CategoryStatusTest {
    private fun status(limit: Long?, spent: Long, threshold: Int = 80) =
        categoryStatus(CategoryBudget(limit?.let(::rs), rs(spent)), threshold)

    @Test
    fun noLimitIsNoLimitWhateverIsSpent() {
        assertEquals(CategoryStatus.NoLimit, status(limit = null, spent = 99_999))
    }

    @Test
    fun belowTheThresholdIsOnTrack() {
        assertEquals(CategoryStatus.OnTrack, status(limit = 4_000, spent = 3_199)) // 79.975%, floors to 79
        assertEquals(CategoryStatus.OnTrack, status(limit = 4_000, spent = 0))
    }

    @Test
    fun atTheThresholdItWarnsWithTheActualPercentUsed() {
        assertEquals(CategoryStatus.Warning(80), status(limit = 4_000, spent = 3_200))
        assertEquals(CategoryStatus.Warning(85), status(limit = 4_000, spent = 3_400))
    }

    @Test
    fun exactlyAtTheLimitIsAWarningNotOverBudget() {
        assertEquals(CategoryStatus.Warning(100), status(limit = 4_000, spent = 4_000))
    }

    @Test
    fun beyondTheLimitIsOverBudgetByTheOvershoot() {
        assertEquals(CategoryStatus.OverBudget(rs(400)), status(limit = 4_000, spent = 4_400))
    }

    @Test
    fun zeroLimitIsOnTrackWhenUntouchedAndOverBudgetOnceSpent() {
        assertEquals(CategoryStatus.OnTrack, status(limit = 0, spent = 0))
        assertEquals(CategoryStatus.OverBudget(rs(100)), status(limit = 0, spent = 100))
    }

    @Test
    fun thresholdIsConfigurable() {
        assertEquals(CategoryStatus.Warning(72), status(limit = 1_000, spent = 725, threshold = 70))
        assertEquals(CategoryStatus.OnTrack, status(limit = 1_000, spent = 725, threshold = 80))
    }

    @Test
    fun impossiblePercentagesThrowInsteadOfWrapping() {
        assertThrows(ArithmeticException::class.java) {
            categoryStatus(CategoryBudget(Paise(Long.MAX_VALUE), Paise(Long.MAX_VALUE - 1)), 80)
        }
    }
}

class NeedsALookTest {
    private data class Row(val name: String, val budget: CategoryBudget)

    private fun row(name: String, limit: Long?, spent: Long) = Row(name, CategoryBudget(limit?.let(::rs), rs(spent)))

    private fun names(rows: List<Row>, threshold: Int = 80) =
        needsALook(rows.map { it.name to it.budget }, threshold).map { it.first }

    @Test
    fun keepsOnlyOverBudgetAndWarningCategories() {
        val rows = listOf(
            row("Groceries", 8_000, 1_800),
            row("Dining", 3_600, 2_880),
            row("Fashion", 4_000, 4_400),
            row("Health", 0, 0),
            row("Transport", null, 500),
        )
        assertEquals(listOf("Fashion", "Dining"), names(rows))
    }

    @Test
    fun overBudgetFirstThenByPercentUsedHighestFirst() {
        val rows = listOf(
            row("A", 1_000, 850),
            row("B", 1_000, 990),
            row("C", 1_000, 1_200),
            row("D", 1_000, 1_001),
        )
        assertEquals(listOf("C", "D", "B", "A"), names(rows))
    }

    @Test
    fun nothingNeedsALookWhenAllAreOnTrack() {
        assertEquals(emptyList<String>(), names(listOf(row("A", 1_000, 100), row("B", null, 5))))
    }
}
