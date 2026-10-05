package com.expensetracker.app.domain.budget

import java.time.LocalDate
import java.time.YearMonth

sealed interface DaysToGo {
    data object LastDay : DaysToGo
    data class Days(val count: Int) : DaysToGo
}

object MonthProgress {
    /** Calendar days left after today, only for the month that contains today. Past and future months have none. */
    fun daysToGo(today: LocalDate, month: YearMonth): DaysToGo? {
        if (YearMonth.from(today) != month) return null
        val remaining = month.lengthOfMonth() - today.dayOfMonth
        return if (remaining == 0) DaysToGo.LastDay else DaysToGo.Days(remaining)
    }
}
