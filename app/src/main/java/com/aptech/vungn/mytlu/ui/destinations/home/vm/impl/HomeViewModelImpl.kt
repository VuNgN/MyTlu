package com.aptech.vungn.mytlu.ui.destinations.home.vm.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptech.vungn.mytlu.data.model.Notification
import com.aptech.vungn.mytlu.data.model.User
import com.aptech.vungn.mytlu.repo.NotificationRepository
import com.aptech.vungn.mytlu.repo.UserRepository
import com.aptech.vungn.mytlu.ui.destinations.home.vm.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    userRepository: UserRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel(),
    HomeViewModel {
    private val _user = userRepository.user
    private val _badgeNumber = MutableStateFlow(18)
    private val _notifications = MutableStateFlow(listOf<Notification>())
    private val _isNotificationLoading = MutableStateFlow(false)

    override val user: LiveData<User>
        get() = _user
    override val badgeNumber: MutableStateFlow<Int>
        get() = _badgeNumber
    override val notifications: MutableStateFlow<List<Notification>>
        get() = _notifications
    override val isNotificationLoading: MutableStateFlow<Boolean>
        get() = _isNotificationLoading

    override fun getNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            _isNotificationLoading.value = true
            val notifications = notificationRepository.getNotifications()
            _notifications.emit(notifications)
            delay(2000)
            _isNotificationLoading.value = false
            Log.d(TAG, "getNotifications: $notifications")
        }
    }

    companion object {
        private val TAG = HomeViewModelImpl::class.simpleName
    }
}