package com.aptech.vungn.mytlu.ui.destinations.login.vm.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptech.vungn.mytlu.repo.LoginRepository
import com.aptech.vungn.mytlu.ui.destinations.login.vm.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel(), LoginViewModel {
    private val _isLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _isLoginIncorrect: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val isLoggedIn: MutableStateFlow<Boolean>
        get() = _isLoggedIn
    override val isLoginIncorrect: MutableStateFlow<Boolean>
        get() = _isLoginIncorrect

    override fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val isLoggedIn = loginRepository.login(username, password)
                if (isLoggedIn) {
                    _isLoggedIn.emit(true)
                    _isLoginIncorrect.emit(false)
                } else {
                    _isLoggedIn.emit(false)
                    _isLoginIncorrect.emit(true)
                }
            } catch (networkError: IOException) {
                //TODO: do something
            }
        }
    }
}