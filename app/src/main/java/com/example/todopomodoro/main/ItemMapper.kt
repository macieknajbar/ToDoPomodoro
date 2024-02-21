package com.example.todopomodoro.main

class ItemMapper {
    fun map(input: String): MainViewModel.ItemModel {
        return MainViewModel.ItemModel(
            id = "",
            name = "",
            isChecked = false,
        )
    }
}