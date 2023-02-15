package com.aptech.vungn.mytlu.util

import kotlinx.coroutines.flow.MutableStateFlow

enum class LoginState {
    LoggedIn,
    NotLoggedIn
}

class LoginStateStorage {
    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.NotLoggedIn)
    val state = _state

    fun onLoggedIn() {
        _state.value = LoginState.LoggedIn
    }
}