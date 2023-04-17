package andryuh.football.core_auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class PhoneStorage
@Inject
constructor(
  private val dataStore: DataStore<Preferences>,
) {

  private val phoneNumberPrefsKey = stringPreferencesKey("phoneNumber")

  suspend fun updatePhone(phone: String) {
    dataStore.edit { prefs -> prefs[phoneNumberPrefsKey] = phone }
  }
  suspend fun getPhoneRequired(): String {
    return getPhone()!!
  }

  suspend fun getPhone(): String? {
    return dataStore.data.first()[phoneNumberPrefsKey]
  }
}
