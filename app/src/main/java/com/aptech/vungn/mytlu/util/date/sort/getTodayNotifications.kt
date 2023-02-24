package com.aptech.vungn.mytlu.util.date.sort

import com.aptech.vungn.mytlu.data.model.Notification
import java.text.SimpleDateFormat
import java.util.*

fun getTodayNotification(items: List<Notification>): List<Notification> {
    return items.filter { item ->
        val dateFormat = SimpleDateFormat("dd-MM-yyy", Locale.ENGLISH)
        val cal = Calendar.getInstance()
        dateFormat.format(item.createOn) == dateFormat.format(cal.time)
    }.sortedByDescending { item -> item.createOn }
}