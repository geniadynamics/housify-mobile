package org.geniadynamics.housify.utility.auth
import org.geniadynamics.housify.utility.generateSecretKey

import android.content.Context
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object SafeSharedPref {
    fun encryptAndStore(context: Context, alias: String, token: String): String {
        val secretKey = generateSecretKey(alias)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptionIv = cipher.iv
        val encryptedToken = cipher.doFinal(token.toByteArray(Charsets.UTF_8))

        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString("${alias}_iv", Base64.encodeToString(encryptionIv, Base64.DEFAULT))
            .putString(alias, Base64.encodeToString(encryptedToken, Base64.DEFAULT))
            .apply()

        return Base64.encodeToString(encryptedToken, Base64.DEFAULT)
    }

    fun decrypt(context: Context, alias: String): String? {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val encryptedToken = sharedPreferences.getString(alias, null)
        val encryptionIv = sharedPreferences.getString("${alias}_iv", null)

        if (encryptedToken != null && encryptionIv != null) {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val secretKey = keyStore.getKey(alias, null) as SecretKey

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val spec = GCMParameterSpec(128, Base64.decode(encryptionIv, Base64.DEFAULT))
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

            val decryptedBytes = cipher.doFinal(Base64.decode(encryptedToken, Base64.DEFAULT))
            return String(decryptedBytes, Charsets.UTF_8)
        }

        return null
    }
}