package com.expensetracker.spikes.s04

/** Same rules as tools/redact_alert.py. Keeps amounts, dates and merchants; blanks everything personal. */
object AlertRedactor {
    private val otpWords = Regex("\\b(otp|one[- ]time|verification code|passcode)\\b", RegexOption.IGNORE_CASE)
    private val currencyBefore = Regex("(rs\\.?|inr|₹)\\s*$", RegexOption.IGNORE_CASE)
    private val code = Regex("(?<![\\dX,.])\\d{4,8}(?![\\d,]|\\.\\d)")
    private val mobile = Regex("(?<!\\d)(?:\\+?91[\\s-]?|0)?[6-9]\\d{4}[\\s-]?\\d{5}(?!\\d)")
    private val longNumber = Regex("(?<!\\d)\\d{10,16}(?!\\d)")
    private val email = Regex("[\\w.+-]+@[\\w-]+(?:\\.[\\w-]+)+")
    private val upiHandle = Regex("[\\w.\\-]{2,}@[A-Za-z]{2,}\\b(?!\\.\\w)")
    private const val NOT_A_NAME = "(?!(?:Customer|CUSTOMER|Sir|Madam)\\b)"
    private const val NAME_WORDS = "[A-Z][a-z]+(?:\\s[A-Z][a-z]+)*"
    private val greeting = Regex("\\b(Dear|Hi|Hello)\\s+$NOT_A_NAME$NAME_WORDS")
    private val title = Regex("\\b(Mr\\.?|Mrs\\.?|Ms\\.?|Shri|Smt\\.?)\\s+$NOT_A_NAME$NAME_WORDS")

    fun redact(text: String): String {
        var out = mobile.replace(text, "XXXXXXXXXX")
        out = longNumber.replace(out) { "X".repeat(it.value.length - 4) + it.value.takeLast(4) }
        if (otpWords.containsMatchIn(out)) {
            val source = out
            out = code.replace(source) { m ->
                if (currencyBefore.containsMatchIn(source.substring(0, m.range.first))) m.value else "X".repeat(m.value.length)
            }
        }
        out = email.replace(out, "redacted@example.com")
        out = upiHandle.replace(out, "REDACTED@upi")
        out = greeting.replace(out) { "${it.groupValues[1]} CUSTOMER" }
        out = title.replace(out) { "${it.groupValues[1]} CUSTOMER" }
        return out
    }
}