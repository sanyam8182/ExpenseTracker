package com.expensetracker.spikes.s05

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory

@Entity(tableName = "spike_expenses")
data class SpikeExpense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amountPaise: Long,
    val merchant: String,
    val createdAtMillis: Long,
)

@Dao
interface SpikeExpenseDao {
    @Insert
    fun insert(expense: SpikeExpense): Long

    @Query("SELECT * FROM spike_expenses ORDER BY id")
    fun all(): List<SpikeExpense>
}

@Database(entities = [SpikeExpense::class], version = 1, exportSchema = false)
abstract class SpikeDatabase : RoomDatabase() {
    abstract fun expenses(): SpikeExpenseDao
}

object EncryptedDb {
    init {
        System.loadLibrary("sqlcipher")
    }

    /** SQLCipher wipes the passphrase array it is given once the database is open, so it gets a copy. */
    fun open(context: Context, name: String, passphrase: ByteArray): SpikeDatabase =
        Room.databaseBuilder(context, SpikeDatabase::class.java, name)
            .openHelperFactory(SupportOpenHelperFactory(passphrase.copyOf()))
            .build()
}
