package com.expensetracker.spikes.s05

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class ExportRestoreTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val keys = KeystoreKeyProvider(context, alias = "spike_test_wrap", fileName = "spike_test_key.bin")
    private val dbName = "spike05_export_test.db"
    private val password = "correct horse battery".toCharArray()

    @Before
    fun clean() {
        context.deleteDatabase(dbName)
        keys.reset()
    }

    @After
    fun tidy() = clean()

    private fun seed(): List<SpikeExpense> {
        val db = EncryptedDb.open(context, dbName, keys.passphrase())
        listOf("Swiggy" to 45_000L, "BigBasket" to 123_456L, "Metro card" to 20_000L).forEach { (m, p) ->
            db.expenses().insert(SpikeExpense(amountPaise = p, merchant = m, createdAtMillis = 1_760_000_000_000))
        }
        val rows = db.expenses().all()
        db.close()
        return rows
    }

    private fun wipeLikeACleanInstall() {
        context.deleteDatabase(dbName)
        keys.reset()
    }

    @Test
    fun exportThenRestoreOnACleanInstallWithoutTheDeviceKey() {
        Kdf.entries.forEach { kdf ->
            clean()
            val original = seed()
            val oldKey = keys.passphrase()

            val db = EncryptedDb.open(context, dbName, keys.passphrase())
            val sealStart = System.nanoTime()
            val blob = ExpenseBackup.export(db, password, kdf)
            val sealMs = (System.nanoTime() - sealStart) / 1_000_000
            db.close()

            wipeLikeACleanInstall()
            assertNotEquals(oldKey.toList(), keys.passphrase().toList())

            val fresh = EncryptedDb.open(context, dbName, keys.passphrase())
            assertEquals(0, fresh.expenses().all().size)
            val openStart = System.nanoTime()
            ExpenseBackup.restore(blob, password, fresh)
            val openMs = (System.nanoTime() - openStart) / 1_000_000
            assertEquals(original.map { it.merchant to it.amountPaise }, fresh.expenses().all().map { it.merchant to it.amountPaise })
            fresh.close()

            Log.i("Spike05", "kdf=$kdf export=${sealMs}ms restore=${openMs}ms bytes=${blob.size}")
        }
    }

    @Test
    fun aWrongPasswordRestoresNothing() {
        seed()
        val db = EncryptedDb.open(context, dbName, keys.passphrase())
        val blob = ExpenseBackup.export(db, password, Kdf.Pbkdf2)
        db.close()
        wipeLikeACleanInstall()

        val fresh = EncryptedDb.open(context, dbName, keys.passphrase())
        assertThrows(ExportException.WrongPasswordOrCorrupt::class.java) {
            ExpenseBackup.restore(blob, "wrong".toCharArray(), fresh)
        }
        assertEquals(0, fresh.expenses().all().size)
        fresh.close()
    }
}
