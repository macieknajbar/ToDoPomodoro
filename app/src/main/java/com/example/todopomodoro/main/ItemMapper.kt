package com.example.todopomodoro.main

import androidx.compose.ui.graphics.Color
import com.example.todopomodoro.domain.ItemEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ItemMapper(
    private val dateFormatter: (Long) -> String =
        { SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date(it)) },
    private val timestampProvider: () -> Long =
        {
            Calendar.getInstance().apply {
                time = Date(System.currentTimeMillis())
                set(Calendar.HOUR, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }.timeInMillis
        },
) {
    fun map(input: ItemEntity): MainViewModel.ItemModel {
        val today = timestampProvider()
        val dateText = if (input.dueDate == null) {
            "No Date"
        } else {
            dateFormatter(input.dueDate)
        }
        return MainViewModel.ItemModel(
            id = input.id,
            name = input.text,
            isChecked = input.isComplete,
            shouldShowDatePicker = false,
            dateText = dateText,
            dateColor = if (input.dueDate == null || input.dueDate >= today) {
                Color.Black
            } else {
                Color.Red
            },
        )
    }
}