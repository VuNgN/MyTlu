package com.aptech.vungn.mytlu.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.aptech.vungn.mytlu.util.consts.PreferenceKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun login(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext if (username.trim() == "vu" && password.trim() == "vu") {
                dataStore.edit { pref ->
                    pref[PreferenceKeys.ID] = username.trim()
                    pref[PreferenceKeys.TOKEN] = "0000"
                }
                true
            } else false
        }
    }
}