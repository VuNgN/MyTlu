package com.aptech.vungn.mytlu.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun logout() {
        withContext(Dispatchers.IO) {
            dataStore.edit { it.clear() }
        }
    }
}