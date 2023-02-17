package com.aptech.vungn.mytlu.ui.destinations.home.vm

import androidx.lifecycle.LiveData
import com.aptech.vungn.mytlu.data.model.User

interface HomeViewModel {
    val user: LiveData<User>
}