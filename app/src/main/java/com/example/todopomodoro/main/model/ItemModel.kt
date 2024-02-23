package com.example.todopomodoro.main.model

import androidx.compose.ui.graphics.Color

data class ItemModel(
    val id: String,
    val text: String,
    val isChecked: Boolean = false,
    val shouldShowDatePicker: Boolean = false,
    val dateText: String,
    val dateColor: Color,
)