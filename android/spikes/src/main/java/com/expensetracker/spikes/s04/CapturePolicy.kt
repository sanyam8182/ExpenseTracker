package com.expensetracker.spikes.s04

import android.content.Context

enum class Capture { Text, PackageOnly }

/**
 * Only exact, explicitly listed packages have their text read. Everything else is noted by package name so the
 * list of real senders can be found, and its content is never touched (CAP 02: non-allowlisted alerts are dropped).
 */
object CapturePolicy {
    private const val PREFS = "spike04_allowlist"
    private const val KEY = "packages"
    private const val ADB_SHELL = "com.android.shell"

    fun decide(packageName: String, allowlist: Set<String>): Capture =
        if (packageName in allowlist) Capture.Text else Capture.PackageOnly

    fun defaults(ownPackage: String): Set<String> = setOf(ownPackage, ADB_SHELL)

    fun load(context: Context): Set<String> =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getStringSet(KEY, null) ?: defaults(context.packageName)

    fun add(context: Context, packageName: String) {
        val name = packageName.trim()
        if (name.isEmpty()) return
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putStringSet(KEY, load(context) + name).apply()
    }

    fun reset(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().remove(KEY).apply()
    }
}