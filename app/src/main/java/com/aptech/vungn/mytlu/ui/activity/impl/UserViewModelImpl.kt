package com.aptech.vungn.mytlu.ui.activity.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aptech.vungn.mytlu.data.model.UserPreference
import com.aptech.vungn.mytlu.repo.LogoutRepository
import com.aptech.vungn.mytlu.repo.UserRepository
import com.aptech.vungn.mytlu.ui.activity.UserViewModel
import com.aptech.vungn.mytlu.util.LoginState
import com.aptech.vungn.mytlu.util.LoginStateStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModelImpl @Inject constructor(
    private val loginStateStorage: LoginStateStorage,
    private val logoutRepository: LogoutRepository,
    userRepository: UserRepository
) : ViewModel(), UserViewModel {
    private val _loginState: MutableStateFlow<LoginState> = loginStateStorage.state
    private val _user: LiveData<UserPreference?> = userRepository.userPreferenceFlow.asLiveData()

    override val loginState: MutableStateFlow<LoginState> = _loginState
    override val user: LiveData<UserPreference?> = _user
    override fun onLoggedIn() {
        loginStateStorage.onLoggedIn()
    }

    override suspend fun logout() {
        loginStateStorage.onLogout()
        logoutRepository.logout()
    }
}