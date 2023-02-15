package com.aptech.vungn.mytlu.ui.activity

import androidx.lifecycle.LiveData
import com.aptech.vungn.mytlu.data.model.UserPreference
import com.aptech.vungn.mytlu.util.LoginState
import kotlinx.coroutines.flow.MutableStateFlow

interface UserViewModel {
    val loginState: MutableStateFlow<LoginState>
    val user: LiveData<UserPreference?>
    fun onLoggedIn()
}