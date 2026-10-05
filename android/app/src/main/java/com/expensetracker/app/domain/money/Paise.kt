package com.expensetracker.app.domain.money

/**
 * Money is integer paise, INR only (BUD 02). Arithmetic is exact: an impossible 64-bit result
 * throws instead of wrapping the sign of a balance.
 */
@JvmInline
value class Paise(val value: Long) : Comparable<Paise> {
    val isNegative: Boolean get() = value < 0
    val isZero: Boolean get() = value == 0L

    operator fun plus(other: Paise) = Paise(Math.addExact(value, other.value))
    operator fun minus(other: Paise) = Paise(Math.subtractExact(value, other.value))
    operator fun unaryMinus() = Paise(Math.negateExact(value))

    override fun compareTo(other: Paise): Int = value.compareTo(other.value)

    companion object {
        val Zero = Paise(0)
        const val PAISE_PER_RUPEE = 100L

        fun rupees(rupees: Long) = Paise(Math.multiplyExact(rupees, PAISE_PER_RUPEE))
    }
}
