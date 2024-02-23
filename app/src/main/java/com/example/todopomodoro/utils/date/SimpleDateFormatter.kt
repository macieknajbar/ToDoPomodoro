package com.example.todopomodoro.utils.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SimpleDateFormatter(
    pattern: String,
) : DateFormatter {
    private val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())

    override fun format(timeInMillis: Long): String {
        return simpleDateFormat.format(Date(timeInMillis))
    }
}