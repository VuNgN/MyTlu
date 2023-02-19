package com.aptech.vungn.mytlu.ui.destinations.home.vm.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aptech.vungn.mytlu.data.model.User
import com.aptech.vungn.mytlu.repo.UserRepository
import com.aptech.vungn.mytlu.ui.destinations.home.vm.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(userRepository: UserRepository) : ViewModel(),
    HomeViewModel {
    private val _user = userRepository.user
    private val _badgeNumber = MutableStateFlow(18)
    override val user: LiveData<User>
        get() = _user
    override val badgeNumber: MutableStateFlow<Int>
        get() = _badgeNumber
}