package com.example.todopomodoro.main

import com.example.todopomodoro.domain.ItemEntity

class ItemMapper {
    fun map(input: String): MainViewModel.ItemModel {
        return MainViewModel.ItemModel(
            id = input,
            name = input,
            isChecked = false,
            shouldShowDatePicker = false,
        )
    }
    fun map(input: ItemEntity): MainViewModel.ItemModel {
        return MainViewModel.ItemModel(
            id = input.id,
            name = input.text,
            isChecked = input.isComplete,
            shouldShowDatePicker = false,
        )
    }
}