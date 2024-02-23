package com.example.todopomodoro.main

import androidx.compose.ui.graphics.Color
import com.example.todopomodoro.di.todayTimestampProvider
import com.example.todopomodoro.domain.ItemEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ItemMapper(
    private val dateFormatter: (Long) -> String =
        { SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date(it)) },
    private val timestampProvider: () -> Long = todayTimestampProvider()::get,
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