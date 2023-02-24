package com.aptech.vungn.mytlu.util.date.formatter

import java.text.SimpleDateFormat
import java.util.*

fun dateToString(date: Date, pattern: String): String {
    return SimpleDateFormat(pattern, Locale.ENGLISH).format(date)
}