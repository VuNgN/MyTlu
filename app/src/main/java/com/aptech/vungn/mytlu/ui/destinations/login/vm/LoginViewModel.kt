package com.aptech.vungn.mytlu.ui.destinations.login.vm

import kotlinx.coroutines.flow.MutableStateFlow

interface LoginViewModel {
    val isLoggedIn: MutableStateFlow<Boolean>
    val isLoginIncorrect: MutableStateFlow<Boolean>
    fun login(username: String, password: String)
}