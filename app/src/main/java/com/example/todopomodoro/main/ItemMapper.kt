package com.example.todopomodoro.main

import androidx.compose.ui.graphics.Color
import com.example.todopomodoro.domain.ItemEntity

class ItemMapper {
    fun map(input: ItemEntity): MainViewModel.ItemModel {
        return MainViewModel.ItemModel(
            id = input.id,
            name = input.text,
            isChecked = input.isComplete,
            shouldShowDatePicker = false,
            dateText = if (input.dueDate == null) {
                "No Date"
            } else {
                "01/01/01"
            },
            dateColor = if (input.dueDate != null && input.dueDate >= 0 || input.dueDate == null) {
                Color.Black
            } else {
                Color.Red
            },
        )
    }
}