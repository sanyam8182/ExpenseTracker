package com.expensetracker.app

import android.app.Application
import com.expensetracker.pocket.PocketRig

class ExpenseTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Parsing the rig on first draw leaves the hero without Pocket for a moment.
        PocketRig.preload(this)
    }
}
