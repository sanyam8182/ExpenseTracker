package com.expensetracker.app.domain.money

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class PaiseTest {
    @Test
    fun rupeesAreStoredAsPaise() {
        assertEquals(129_900L, Paise.rupees(1_299).value)
    }

    @Test
    fun addAndSubtractAreExact() {
        assertEquals(Paise(150), Paise(100) + Paise(50))
        assertEquals(Paise(-50), Paise(100) - Paise(150))
    }

    @Test
    fun overflowThrowsInsteadOfWrappingTheSign() {
        assertThrows(ArithmeticException::class.java) { Paise(Long.MAX_VALUE) + Paise(1) }
        assertThrows(ArithmeticException::class.java) { Paise(Long.MIN_VALUE) - Paise(1) }
        assertThrows(ArithmeticException::class.java) { Paise.rupees(Long.MAX_VALUE) }
    }

    @Test
    fun negativeAndZeroChecks() {
        assertTrue(Paise(-1).isNegative)
        assertTrue(Paise(0).isZero)
        assertEquals(Paise(40_000), -Paise(-40_000))
    }

    @Test
    fun comparesByValue() {
        assertTrue(Paise(1) < Paise(2))
        assertEquals(Paise(7), listOf(Paise(3), Paise(7), Paise(5)).max())
    }
}

class IndianCurrencyFormatTest {
    private fun fmt(paise: Long, precise: Boolean = false) = IndianCurrency.format(Paise(paise), precise)

    @Test
    fun zero() {
        assertEquals("₹0", fmt(0))
    }

    @Test
    fun wholeRupeesShowNoPaise() {
        assertEquals("₹1,299", fmt(129_900))
        assertEquals("₹4,000", fmt(400_000))
    }

    @Test
    fun paiseShownOnlyWhenNonZero() {
        assertEquals("₹1,299.50", fmt(129_950))
        assertEquals("₹0.05", fmt(5))
        assertEquals("₹0.50", fmt(50))
    }

    @Test
    fun indianGroupingUsesLastThreeThenPairs() {
        assertEquals("₹999", fmt(99_900))
        assertEquals("₹1,000", fmt(100_000))
        assertEquals("₹1,00,000", fmt(10_000_000))
        assertEquals("₹1,00,00,000", fmt(1_000_000_000))
    }

    @Test
    fun largeCroreAmountsGroupCorrectly() {
        assertEquals("₹12,34,56,789", fmt(12_34_56_789L * 100))
    }

    @Test
    fun preciseViewAlwaysShowsTwoDecimals() {
        assertEquals("₹1,299.00", fmt(129_900, precise = true))
        assertEquals("₹0.00", fmt(0, precise = true))
        assertEquals("₹1,00,000.00", fmt(10_000_000, precise = true))
    }

    @Test
    fun negativeAmountsKeepTheSignBeforeTheSymbol() {
        assertEquals("-₹400", fmt(-40_000))
        assertEquals("-₹0.05", fmt(-5))
    }

    @Test
    fun largestPossibleAmountFormats() {
        assertEquals("₹92,23,37,20,36,85,47,758.07", fmt(Long.MAX_VALUE))
    }

    @Test
    fun smallestPossibleAmountDoesNotCrash() {
        val text = fmt(Long.MIN_VALUE)
        assertTrue(text.startsWith("-₹"))
        assertTrue(text.endsWith(".08"))
    }
}

class SpokenCurrencyTest {
    private fun say(paise: Long) = IndianCurrency.spoken(Paise(paise))

    @Test
    fun zero() {
        assertEquals("zero rupees", say(0))
    }

    @Test
    fun rupeesOnly() {
        assertEquals("one rupee", say(100))
        assertEquals("one thousand two hundred and ninety-nine rupees", say(129_900))
        assertEquals("forty rupees", say(4_000))
    }

    @Test
    fun rupeesAndPaise() {
        assertEquals("one thousand two hundred rupees and fifty paise", say(120_050))
        assertEquals("one rupee and one paisa", say(101))
    }

    @Test
    fun paiseOnly() {
        assertEquals("fifty paise", say(50))
        assertEquals("one paisa", say(1))
    }

    @Test
    fun andBeforeASmallLastPart() {
        assertEquals("one thousand and five rupees", say(100_500))
    }

    @Test
    fun usesLakhAndCrore() {
        assertEquals("one lakh rupees", say(10_000_000))
        assertEquals("one crore rupees", say(1_000_000_000))
        assertEquals("twelve lakh thirty-four thousand five hundred and sixty-seven rupees", say(123_456_700))
    }

    @Test
    fun negativeAmountsAreSpokenWithMinus() {
        assertEquals("minus one thousand two hundred and ninety-nine rupees", say(-129_900))
    }
}
