package com.example.jetpackcomposebase.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.jetpackcomposebase.base.LocaleManager
import com.example.jetpackcomposebase.utils.MyPreference
import com.example.jetpackcomposebase.utils.PrefKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
//        try {
//            var spec = KeyGenParameterSpec.Builder(
//                MasterKey.DEFAULT_MASTER_KEY_ALIAS,
//                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
//            )
//                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
//                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
//                .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
//                .build()
//            val masterKey = MasterKey.Builder(context)
//                .setKeyGenParameterSpec(spec)
//                .build()
//            EncryptedSharedPreferences.create(
//                context,
//                PrefKey.PREFERENCE_NAME,
//                masterKey, // masterKey created above
//                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//            )
//        } catch (e: Exception) {
//            FirebaseCrashlytics.getInstance().recordException(e)
//            e.printStackTrace()
//            throw e
//        }
//    old Depricated Code

        /* EncryptedSharedPreferences.create(
             PrefKey.PREFERENCE_NAME,
             MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
             context,
             EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
             EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
         )*/

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            EncryptedSharedPreferences.create(
                PrefKey.PREFERENCE_NAME,
                MasterKeys.getOrCreate(   MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            context.getSharedPreferences(
                PrefKey.PREFERENCE_NAME, Context.MODE_PRIVATE
            )
        }
    @Singleton
    @Provides
    fun provideMyPreference(mSharedPreferences: SharedPreferences) =
        MyPreference(mSharedPreferences)

    @Singleton
    @Provides
    fun provideLocaleManager() =
        LocaleManager()

}