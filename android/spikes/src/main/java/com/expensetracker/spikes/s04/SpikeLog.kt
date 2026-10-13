package com.expensetracker.spikes.s04

import android.content.Context
import android.util.Log
import java.io.File
import java.time.Instant

/** Only redacted text is ever written here. One line per event: UTC time, kind, source, text. */
object SpikeLog {
    private const val TAG = "Spike04"
    private const val FILE_NAME = "spike04_log.txt"
    private const val LAST_LINES = 60

    private fun file(context: Context) = File(context.filesDir, FILE_NAME)

    @Synchronized
    fun add(context: Context, kind: String, source: String, text: String = "") {
        val line = listOf(Instant.now().toString(), kind, source, text.replace('\n', ' ')).joinToString("\t")
        file(context).appendText(line + "\n")
        Log.i(TAG, line)
    }

    @Synchronized
    fun last(context: Context, count: Int = LAST_LINES): List<String> =
        file(context).takeIf { it.exists() }?.readLines()?.takeLast(count)?.reversed() ?: emptyList()

    @Synchronized
    fun clear(context: Context) {
        file(context).delete()
    }
}