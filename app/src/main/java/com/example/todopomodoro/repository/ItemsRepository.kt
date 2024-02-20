package com.example.todopomodoro.repository

import androidx.compose.runtime.mutableStateOf
import com.example.todopomodoro.utils.update

object ItemsRepository: GetAllItems {
    private val itemsState = mutableStateOf(emptyList<String>())

    fun add(value: String) {
        itemsState.update { it + value }
    }

    override fun getAll(): List<String> {
        return itemsState.value
    }
}