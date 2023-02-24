package com.aptech.vungn.mytlu.util.date.sort

import com.aptech.vungn.mytlu.data.model.Notification
import java.util.*

fun getThisWeekNotification(items: List<Notification>): List<Notification> {
    return items.filter { item ->
        val yesterday = Calendar.getInstance()
        val aWeekBefore = Calendar.getInstance()
        yesterday.add(Calendar.DATE, -1)
        aWeekBefore.add(Calendar.DATE, -7)
        item.createOn.before(yesterday.time) && item.createOn.after(aWeekBefore.time)
    }.sortedByDescending { item -> item.createOn }
}
