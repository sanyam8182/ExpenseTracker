package com.expensetracker.spikes.s05

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Test

class PortableExportTest {
    private val plain = "amount_paise=45000;merchant=Swiggy".toByteArray()
    private val password = "correct horse battery".toCharArray()

    @Test
    fun `round trip with PBKDF2`() {
        val blob = PortableExport.seal(plain, password, Kdf.Pbkdf2)
        assertArrayEquals(plain, PortableExport.open(blob, password))
    }

    @Test
    fun `round trip with Argon2id`() {
        val blob = PortableExport.seal(plain, password, Kdf.Argon2id)
        assertArrayEquals(plain, PortableExport.open(blob, password))
    }

    @Test
    fun `wrong password is rejected`() {
        val blob = PortableExport.seal(plain, password, Kdf.Pbkdf2)
        assertThrows(ExportException.WrongPasswordOrCorrupt::class.java) {
            PortableExport.open(blob, "wrong".toCharArray())
        }
    }

    @Test
    fun `changed ciphertext is rejected`() {
        val blob = PortableExport.seal(plain, password, Kdf.Pbkdf2)
        blob[blob.size - 1] = (blob[blob.size - 1].toInt() xor 1).toByte()
        assertThrows(ExportException.WrongPasswordOrCorrupt::class.java) { PortableExport.open(blob, password) }
    }

    @Test
    fun `changed header is rejected`() {
        val blob = PortableExport.seal(plain, password, Kdf.Pbkdf2)
        val salt = PortableExport.HEADER_SALT_OFFSET
        blob[salt] = (blob[salt].toInt() xor 1).toByte()
        assertThrows(ExportException.WrongPasswordOrCorrupt::class.java) { PortableExport.open(blob, password) }
    }

    @Test
    fun `two exports of the same data differ`() {
        val a = PortableExport.seal(plain, password, Kdf.Pbkdf2)
        val b = PortableExport.seal(plain, password, Kdf.Pbkdf2)
        assertFalse(a.contentEquals(b))
    }

    @Test
    fun `plaintext does not appear in the export`() {
        val blob = PortableExport.seal(plain, password, Kdf.Pbkdf2)
        assertFalse(String(blob, Charsets.ISO_8859_1).contains("Swiggy"))
    }

    @Test
    fun `unknown version is refused with its own error`() {
        val blob = PortableExport.seal(plain, password, Kdf.Pbkdf2)
        blob[PortableExport.HEADER_VERSION_OFFSET] = 99
        assertThrows(ExportException.UnsupportedVersion::class.java) { PortableExport.open(blob, password) }
    }

    @Test
    fun `not an export is refused`() {
        assertThrows(ExportException.NotAnExport::class.java) { PortableExport.open("hello".toByteArray(), password) }
    }

    @Test
    fun `header records the kdf so it can change later`() {
        assertEquals(Kdf.Pbkdf2, PortableExport.kdfOf(PortableExport.seal(plain, password, Kdf.Pbkdf2)))
        assertEquals(Kdf.Argon2id, PortableExport.kdfOf(PortableExport.seal(plain, password, Kdf.Argon2id)))
    }
}
