package goshka133.football.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import goshka133.football.core_network.di.NetworkModule
import io.github.osipxd.security.crypto.createEncrypted
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
object CoreModule {

  @Singleton
  @Provides
  fun provideDataStorePreferences(context: Context): DataStore<Preferences> {
    val dataStore =
      PreferenceDataStoreFactory.createEncrypted {
        EncryptedFile.Builder(
            /* file = */ context.dataStoreFile("prefs.preferences_pb"),
            /* context = */ context,
            /* masterKeyAlias = */ MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            /* fileEncryptionScheme = */ EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
          )
          .build()
      }

    return dataStore
  }
}
