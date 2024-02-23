package com.example.todopomodoro.utils.time

import java.util.Calendar
import java.util.Date

class TodayTimestampProvider : TimestampProvider {
    override fun get(): Long {
        return Calendar.getInstance().apply {
            time = Date(System.currentTimeMillis())
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis
    }
}