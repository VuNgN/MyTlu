package com.aptech.vungn.mytlu.util.date.sort

import com.aptech.vungn.mytlu.data.model.Notification
import java.util.*

fun getEarlierNotification(items: List<Notification>): List<Notification> {
    return items.filter { item ->
        val earlier = Calendar.getInstance()
        earlier.add(Calendar.DATE, -7)
        item.createOn.before(earlier.time)
    }.sortedByDescending { item -> item.createOn }
}
