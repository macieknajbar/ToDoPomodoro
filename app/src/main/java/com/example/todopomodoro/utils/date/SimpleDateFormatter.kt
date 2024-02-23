package com.example.todopomodoro.utils.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SimpleDateFormatter : DateFormatter {
    override fun format(timeInMillis: Long): String {
        return SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date(timeInMillis))
    }
}