package com.aptech.vungn.mytlu.data.model

import com.aptech.vungn.mytlu.util.types.NotificationStatus
import com.aptech.vungn.mytlu.util.types.NotificationType
import java.util.*

data class Notification(
    val type: NotificationType,
    val title: String,
    val image: String?,
    val createOn: Date,
    val status: NotificationStatus
)