package com.aptech.vungn.mytlu.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.MutableLiveData
import com.aptech.vungn.mytlu.data.model.User
import com.aptech.vungn.mytlu.data.model.UserPreference
import com.aptech.vungn.mytlu.util.consts.PreferenceKeys
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.*
import javax.inject.Inject

class UserRepository @Inject constructor(dataStore: DataStore<Preferences>) {
    private val _userPreferenceFlow = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { pref: Preferences ->
        val username: String? = pref[PreferenceKeys.ID]
        val token: String? = pref[PreferenceKeys.TOKEN]
        if (username == null || token == null) {
            null
        } else {
            UserPreference(
                username = username,
                token = token
            )
        }
    }

    private val _user = MutableLiveData(
        User(
            "Vu",
            "Nguyen Ngoc",
            "",
            "https://static.vecteezy.com/system/resources/previews/002/275/847/original/male-avatar-profile-icon-of-smiling-caucasian-man-vector.jpg",
            "",
            "1951061127",
            "",
            "",
            "",
            Date(),
            "",
            "",
            "",
            "",
            ""
        )
    )

    val userPreferenceFlow = _userPreferenceFlow
    val user = _user
}