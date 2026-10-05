package com.expensetracker.spikes.s05

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyInfo
import android.security.keystore.KeyProperties
import java.io.File
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec

/**
 * The database passphrase is 32 random bytes. It is stored only as an AES-GCM blob wrapped by a
 * non-exportable Android Keystore key, so the file on disk is useless without this device's key.
 */
class KeystoreKeyProvider(private val context: Context, private val alias: String, private val fileName: String) {
    private val file get() = File(context.filesDir, fileName)
    private val keyStore: KeyStore get() = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }

    fun passphrase(): ByteArray {
        if (file.exists()) return unwrap(file.readBytes())
        val fresh = ByteArray(PASSPHRASE_BYTES).also(SecureRandom()::nextBytes)
        // Write then rename so a crash cannot leave a half-written key file, which would lose the data.
        val temp = File(context.filesDir, "$fileName.tmp")
        temp.writeBytes(wrap(fresh))
        check(temp.renameTo(file)) { "Could not store the wrapped key" }
        return fresh
    }

    /** Forgets the key, as a clean install would. */
    fun reset() {
        file.delete()
        if (keyStore.containsAlias(alias)) keyStore.deleteEntry(alias)
    }

    /** Null for a hardware- or Keystore-held key: the raw key material cannot be read out. */
    fun wrappingKeyEncoded(): ByteArray? = wrappingKey().encoded

    /** "software", "trusted environment" or "strongbox", as reported by the device for the wrapping key. */
    fun securityLevel(): String {
        val key = wrappingKey()
        val info = SecretKeyFactory.getInstance(key.algorithm, ANDROID_KEYSTORE).getKeySpec(key, KeyInfo::class.java) as KeyInfo
        return when (info.securityLevel) {
            KeyProperties.SECURITY_LEVEL_STRONGBOX -> "strongbox"
            KeyProperties.SECURITY_LEVEL_TRUSTED_ENVIRONMENT -> "trusted environment"
            KeyProperties.SECURITY_LEVEL_SOFTWARE -> "software"
            else -> "unknown (${info.securityLevel})"
        }
    }

    private fun wrappingKey(): SecretKey {
        (keyStore.getKey(alias, null) as? SecretKey)?.let { return it }
        val spec = KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(WRAPPING_KEY_BITS)
            .build()
        return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE).apply { init(spec) }.generateKey()
    }

    private fun wrap(plain: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION).apply { init(Cipher.ENCRYPT_MODE, wrappingKey()) }
        return byteArrayOf(cipher.iv.size.toByte()) + cipher.iv + cipher.doFinal(plain)
    }

    private fun unwrap(blob: ByteArray): ByteArray {
        val ivSize = blob[0].toInt()
        val cipher = Cipher.getInstance(TRANSFORMATION)
            .apply { init(Cipher.DECRYPT_MODE, wrappingKey(), GCMParameterSpec(GCM_TAG_BITS, blob, 1, ivSize)) }
        return cipher.doFinal(blob, 1 + ivSize, blob.size - 1 - ivSize)
    }

    private companion object {
        const val ANDROID_KEYSTORE = "AndroidKeyStore"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val PASSPHRASE_BYTES = 32
        const val WRAPPING_KEY_BITS = 256
        const val GCM_TAG_BITS = 128
    }
}
