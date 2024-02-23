package com.example.todopomodoro.utils.date

interface DateFormatter {
    fun format(timeInMillis: Long): String
}