package com.expensetracker.spikes.s05

import org.json.JSONArray
import org.json.JSONObject

/** Rows are exported as data, not as a copy of the database file, so no device key is involved. */
object ExpenseBackup {
    fun export(db: SpikeDatabase, password: CharArray, kdf: Kdf): ByteArray {
        val rows = JSONArray()
        db.expenses().all().forEach {
            rows.put(
                JSONObject()
                    .put("amount_paise", it.amountPaise)
                    .put("merchant", it.merchant)
                    .put("created_at_millis", it.createdAtMillis),
            )
        }
        return PortableExport.seal(rows.toString().toByteArray(Charsets.UTF_8), password, kdf)
    }

    fun restore(blob: ByteArray, password: CharArray, db: SpikeDatabase) {
        val rows = JSONArray(String(PortableExport.open(blob, password), Charsets.UTF_8))
        db.runInTransaction {
            for (i in 0 until rows.length()) {
                val row = rows.getJSONObject(i)
                db.expenses().insert(
                    SpikeExpense(
                        amountPaise = row.getLong("amount_paise"),
                        merchant = row.getString("merchant"),
                        createdAtMillis = row.getLong("created_at_millis"),
                    ),
                )
            }
        }
    }
}
