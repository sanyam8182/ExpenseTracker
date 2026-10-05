package com.expensetracker.app.domain.money

/**
 * Display and spoken forms of an amount (FBK 01).
 * Indian grouping: the last three digits, then pairs (₹1,00,00,000). Paise appear only when non-zero,
 * or always in precise views. Spoken labels use rupees, paise, lakh and crore for TalkBack.
 */
object IndianCurrency {
    private const val SYMBOL = "₹"

    fun format(amount: Paise, precise: Boolean = false): String {
        val parts = split(amount)
        val grouped = groupIndian(parts.rupees.toString())
        val fraction = if (precise || parts.paise != 0) "." + parts.paise.toString().padStart(2, '0') else ""
        return (if (parts.negative) "-" else "") + SYMBOL + grouped + fraction
    }

    fun spoken(amount: Paise): String {
        val parts = split(amount)
        val pieces = mutableListOf<String>()
        if (parts.rupees > 0 || parts.paise == 0) {
            pieces += words(parts.rupees) + if (parts.rupees == 1L) " rupee" else " rupees"
        }
        if (parts.paise > 0) {
            pieces += words(parts.paise.toLong()) + if (parts.paise == 1) " paisa" else " paise"
        }
        val text = pieces.joinToString(" and ")
        return if (parts.negative) "minus $text" else text
    }

    private data class Parts(val negative: Boolean, val rupees: Long, val paise: Int)

    /** Splits through negative numbers so Long.MIN_VALUE cannot overflow on negation. */
    private fun split(amount: Paise): Parts {
        val value = amount.value
        val negative = value < 0
        val nonPositive = if (negative) value else -value
        return Parts(
            negative = negative,
            rupees = -(nonPositive / Paise.PAISE_PER_RUPEE),
            paise = (-(nonPositive % Paise.PAISE_PER_RUPEE)).toInt(),
        )
    }

    private fun groupIndian(digits: String): String {
        if (digits.length <= 3) return digits
        val tail = digits.takeLast(3)
        val groups = digits.dropLast(3).reversed().chunked(2).map { it.reversed() }.reversed()
        return (groups + tail).joinToString(",")
    }

    private val ones = listOf(
        "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
        "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen",
    )
    private val tens = listOf("", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")

    private const val CRORE = 10_000_000L
    private const val LAKH = 100_000L
    private const val THOUSAND = 1_000L

    private fun words(number: Long): String {
        if (number == 0L) return ones[0]
        var rest = number
        val crore = rest / CRORE
        rest %= CRORE
        val lakh = rest / LAKH
        rest %= LAKH
        val thousand = rest / THOUSAND
        rest %= THOUSAND

        val parts = mutableListOf<String>()
        if (crore > 0) parts += words(crore) + " crore"
        if (lakh > 0) parts += below1000(lakh.toInt()) + " lakh"
        if (thousand > 0) parts += below1000(thousand.toInt()) + " thousand"
        if (rest > 0) {
            val last = below1000(rest.toInt())
            parts += if (parts.isNotEmpty() && rest < 100) "and $last" else last
        }
        return parts.joinToString(" ")
    }

    private fun below1000(number: Int): String {
        val hundreds = number / 100
        val remainder = number % 100
        val pieces = mutableListOf<String>()
        if (hundreds > 0) pieces += ones[hundreds] + " hundred"
        if (remainder > 0) pieces += (if (hundreds > 0) "and " else "") + below100(remainder)
        return pieces.joinToString(" ")
    }

    private fun below100(number: Int): String {
        if (number < 20) return ones[number]
        val unit = number % 10
        return tens[number / 10] + if (unit > 0) "-" + ones[unit] else ""
    }
}
