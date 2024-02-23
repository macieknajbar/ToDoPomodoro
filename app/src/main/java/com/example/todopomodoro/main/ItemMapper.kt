package com.example.todopomodoro.main

import androidx.compose.ui.graphics.Color
import com.example.todopomodoro.R
import com.example.todopomodoro.di.dateFormatter
import com.example.todopomodoro.di.todayTimestampProvider
import com.example.todopomodoro.domain.ItemEntity

class ItemMapper(
    private val dateFormatter: (Long) -> String = dateFormatter("dd/MM/yy")::format,
    private val timestampProvider: () -> Long = todayTimestampProvider()::get,
    private val stringProvider: (Int) -> String = { "No Date" },
) {
    fun map(input: ItemEntity): MainViewModel.ItemModel {
        val today = timestampProvider()
        val dateText = if (input.dueDate == null) {
            stringProvider(R.string.item_date_empty)
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