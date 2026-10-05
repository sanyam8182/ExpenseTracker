package com.expensetracker.app.domain.budget

import com.expensetracker.pocket.PocketBudget

/**
 * Pocket gets one limit and the confirmed net spending inside the limited categories, in integer paise.
 * No configured limit becomes null (Idle pose). Pending review never enters this API.
 */
fun HeroBudget.toPocketBudget(): PocketBudget =
    PocketBudget(limitPaise = limitsTotal?.value, confirmedNetSpentPaise = spentInLimited.value)
