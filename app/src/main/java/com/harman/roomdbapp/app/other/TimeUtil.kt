package com.harman.roomdbapp.app.other

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat")
fun getFormattedTimeFromMillis(millis: Long): String {
    val formatter = SimpleDateFormat("mm:ss")

    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = millis
    return formatter.format(calendar.time)
}

fun getMinutesInMillis(millis: Long): Long {
    val a = TimeUnit.MILLISECONDS.toDays(millis)
    val b = TimeUnit.DAYS.toMillis(a)
    return millis - b
}

fun addSeconds(seconds: Long, addTo: Long): Long {
    return TimeUnit.SECONDS.toMillis(seconds) + addTo
}
