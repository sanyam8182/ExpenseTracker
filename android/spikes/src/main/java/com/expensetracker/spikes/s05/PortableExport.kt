package com.expensetracker.spikes.s05

import java.nio.ByteBuffer
import java.security.GeneralSecurityException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import org.bouncycastle.crypto.generators.Argon2BytesGenerator
import org.bouncycastle.crypto.params.Argon2Parameters

enum class Kdf(val id: Byte) {
    Pbkdf2(1),
    Argon2id(2),
}

sealed class ExportException(message: String) : Exception(message) {
    class NotAnExport : ExportException("Not an Expense Tracker export")
    class UnsupportedVersion(val version: Int) : ExportException("Export version $version is not supported")
    class WrongPasswordOrCorrupt : ExportException("Wrong password, or the file was changed")
}

/**
 * Password-protected export that needs nothing from the device: the key comes from the password alone.
 *
 * Layout (header is authenticated, so changing any byte of it fails the open):
 * magic "ETX" | version | kdf id | three kdf parameters (4 bytes each) | salt 16 | iv 12 | AES-GCM ciphertext and tag
 */
object PortableExport {
    private val MAGIC = byteArrayOf('E'.code.toByte(), 'T'.code.toByte(), 'X'.code.toByte())
    const val VERSION: Int = 1
    const val HEADER_VERSION_OFFSET = 3
    private const val KDF_OFFSET = 4
    private const val PARAMS_OFFSET = 5
    private const val SALT_SIZE = 16
    private const val IV_SIZE = 12
    private const val PARAM_COUNT = 3
    const val HEADER_SALT_OFFSET = PARAMS_OFFSET + PARAM_COUNT * Int.SIZE_BYTES
    private const val IV_OFFSET = HEADER_SALT_OFFSET + SALT_SIZE
    private const val HEADER_SIZE = IV_OFFSET + IV_SIZE
    private const val KEY_BYTES = 32
    private const val GCM_TAG_BITS = 128

    // OWASP minimums for 2025: PBKDF2-HMAC-SHA256 600,000 rounds; Argon2id 19 MiB, 2 passes, 1 lane.
    private const val PBKDF2_ROUNDS = 600_000
    private const val ARGON2_MEMORY_KIB = 19_456
    private const val ARGON2_PASSES = 2
    private const val ARGON2_LANES = 1

    // Limits on what a file may ask for, so a hostile file cannot make the phone grind or run out of memory.
    private const val MAX_PBKDF2_ROUNDS = 5_000_000
    private const val MAX_ARGON2_MEMORY_KIB = 262_144
    private const val MAX_ARGON2_PASSES = 10
    private const val MAX_ARGON2_LANES = 8

    private val random = SecureRandom()

    fun seal(plain: ByteArray, password: CharArray, kdf: Kdf): ByteArray {
        val params = when (kdf) {
            Kdf.Pbkdf2 -> intArrayOf(PBKDF2_ROUNDS, 0, 0)
            Kdf.Argon2id -> intArrayOf(ARGON2_MEMORY_KIB, ARGON2_PASSES, ARGON2_LANES)
        }
        val salt = ByteArray(SALT_SIZE).also(random::nextBytes)
        val iv = ByteArray(IV_SIZE).also(random::nextBytes)
        val header = ByteBuffer.allocate(HEADER_SIZE).apply {
            put(MAGIC)
            put(VERSION.toByte())
            put(kdf.id)
            params.forEach(::putInt)
            put(salt)
            put(iv)
        }.array()
        val key = deriveKey(kdf, params, password, salt)
        return header + cipher(Cipher.ENCRYPT_MODE, key, iv, header).doFinal(plain)
    }

    fun open(blob: ByteArray, password: CharArray): ByteArray {
        if (blob.size < HEADER_SIZE || !blob.copyOfRange(0, MAGIC.size).contentEquals(MAGIC)) {
            throw ExportException.NotAnExport()
        }
        val version = blob[HEADER_VERSION_OFFSET].toInt()
        if (version != VERSION) throw ExportException.UnsupportedVersion(version)
        val kdf = kdfOf(blob)
        val buffer = ByteBuffer.wrap(blob, PARAMS_OFFSET, PARAM_COUNT * Int.SIZE_BYTES)
        val params = IntArray(PARAM_COUNT) { buffer.getInt() }
        if (!withinLimits(kdf, params)) throw ExportException.WrongPasswordOrCorrupt()
        val salt = blob.copyOfRange(HEADER_SALT_OFFSET, IV_OFFSET)
        val iv = blob.copyOfRange(IV_OFFSET, HEADER_SIZE)
        val header = blob.copyOfRange(0, HEADER_SIZE)
        val key = deriveKey(kdf, params, password, salt)
        return try {
            cipher(Cipher.DECRYPT_MODE, key, iv, header).doFinal(blob, HEADER_SIZE, blob.size - HEADER_SIZE)
        } catch (_: GeneralSecurityException) {
            throw ExportException.WrongPasswordOrCorrupt()
        }
    }

    fun kdfOf(blob: ByteArray): Kdf {
        if (blob.size < HEADER_SIZE) throw ExportException.NotAnExport()
        return Kdf.entries.firstOrNull { it.id == blob[KDF_OFFSET] } ?: throw ExportException.WrongPasswordOrCorrupt()
    }

    private fun withinLimits(kdf: Kdf, p: IntArray) = when (kdf) {
        Kdf.Pbkdf2 -> p[0] in 1..MAX_PBKDF2_ROUNDS
        Kdf.Argon2id -> p[0] in 8..MAX_ARGON2_MEMORY_KIB && p[1] in 1..MAX_ARGON2_PASSES && p[2] in 1..MAX_ARGON2_LANES
    }

    private fun deriveKey(kdf: Kdf, params: IntArray, password: CharArray, salt: ByteArray): SecretKeySpec {
        val bytes = when (kdf) {
            Kdf.Pbkdf2 -> {
                val spec = PBEKeySpec(password, salt, params[0], KEY_BYTES * Byte.SIZE_BITS)
                try {
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).encoded
                } finally {
                    spec.clearPassword()
                }
            }
            Kdf.Argon2id -> {
                val passwordBytes = Charsets.UTF_8.encode(java.nio.CharBuffer.wrap(password)).let { buf ->
                    ByteArray(buf.remaining()).also(buf::get)
                }
                val out = ByteArray(KEY_BYTES)
                try {
                    val argon = Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                        .withVersion(Argon2Parameters.ARGON2_VERSION_13)
                        .withSalt(salt)
                        .withMemoryAsKB(params[0])
                        .withIterations(params[1])
                        .withParallelism(params[2])
                        .build()
                    Argon2BytesGenerator().apply { init(argon) }.generateBytes(passwordBytes, out)
                } finally {
                    passwordBytes.fill(0)
                }
                out
            }
        }
        return SecretKeySpec(bytes, "AES")
    }

    private fun cipher(mode: Int, key: SecretKeySpec, iv: ByteArray, header: ByteArray): Cipher =
        Cipher.getInstance("AES/GCM/NoPadding").apply {
            init(mode, key, GCMParameterSpec(GCM_TAG_BITS, iv))
            updateAAD(header)
        }
}
