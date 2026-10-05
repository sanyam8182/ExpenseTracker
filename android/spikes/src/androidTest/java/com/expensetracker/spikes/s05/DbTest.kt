package com.expensetracker.spikes.s05

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import java.io.File
import java.security.SecureRandom
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class DbTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val keys = KeystoreKeyProvider(context, alias = "spike_test_wrap", fileName = "spike_test_key.bin")
    private val dbName = "spike05_test.db"

    @Before
    fun clean() {
        context.deleteDatabase(dbName)
        keys.reset()
    }

    @After
    fun tidy() = clean()

    private fun sample(merchant: String) = SpikeExpense(amountPaise = 45_000, merchant = merchant, createdAtMillis = 1_760_000_000_000)

    @Test
    fun rowsSurviveCloseAndReopenWithTheStoredKey() {
        val first = EncryptedDb.open(context, dbName, keys.passphrase())
        first.expenses().insert(sample("Swiggy"))
        first.close()

        // A fresh provider instance must unwrap the same key from disk, as after a process restart.
        val second = EncryptedDb.open(context, dbName, KeystoreKeyProvider(context, "spike_test_wrap", "spike_test_key.bin").passphrase())
        assertEquals(listOf("Swiggy"), second.expenses().all().map { it.merchant })
        second.close()
    }

    @Test
    fun theFileOnDiskIsNotPlainSqlite() {
        val db = EncryptedDb.open(context, dbName, keys.passphrase())
        db.expenses().insert(sample("Swiggy"))
        db.close()

        val bytes = context.getDatabasePath(dbName).readBytes()
        assertFalse(bytes.copyOfRange(0, 15).contentEquals("SQLite format 3".toByteArray()))
        assertFalse(String(bytes, Charsets.ISO_8859_1).contains("Swiggy"))
    }

    @Test
    fun aWrongKeyCannotReadTheDatabase() {
        val db = EncryptedDb.open(context, dbName, keys.passphrase())
        db.expenses().insert(sample("Swiggy"))
        db.close()

        val wrong = ByteArray(32).also(SecureRandom()::nextBytes)
        assertThrows(Exception::class.java) {
            EncryptedDb.open(context, dbName, wrong).also { it.expenses().all() }
        }
    }

    @Test
    fun theKeyOnDiskIsWrappedAndStableAndTheKeystoreKeyCannotBeExported() {
        val passphrase = keys.passphrase()
        val stored = File(context.filesDir, "spike_test_key.bin").readBytes()
        assertFalse(String(stored, Charsets.ISO_8859_1).contains(String(passphrase, Charsets.ISO_8859_1)))
        assertArrayEquals(passphrase, KeystoreKeyProvider(context, "spike_test_wrap", "spike_test_key.bin").passphrase())
        assertNull(keys.wrappingKeyEncoded())
    }

    @Test
    fun recordsWhereTheWrappingKeyLives() {
        // Evidence for the record: software on an emulator, trusted environment or strongbox on a real phone.
        Log.i("Spike05", "wrapping key security level: ${keys.securityLevel()}")
    }

    @Test
    fun resetGivesANewKey() {
        val before = keys.passphrase()
        keys.reset()
        assertNotEquals(before.toList(), keys.passphrase().toList())
    }
}
