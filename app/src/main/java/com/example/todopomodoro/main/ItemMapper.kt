package com.example.todopomodoro.main

import androidx.compose.ui.graphics.Color
import com.example.todopomodoro.domain.ItemEntity
import java.text.SimpleDateFormat
import java.util.Locale

class ItemMapper(
    private val dateFormatter: (Long?) -> String = SimpleDateFormat("dd/MM/yy", Locale.getDefault())::format,
) {
    fun map(input: ItemEntity): MainViewModel.ItemModel {
        return MainViewModel.ItemModel(
            id = input.id,
            name = input.text,
            isChecked = input.isComplete,
            shouldShowDatePicker = false,
            dateText = dateFormatter(input.dueDate),
            dateColor = if (input.dueDate != null && input.dueDate >= 0 || input.dueDate == null) {
                Color.Black
            } else {
                Color.Red
            },
        )
    }
}