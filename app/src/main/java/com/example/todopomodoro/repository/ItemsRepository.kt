package com.example.todopomodoro.repository

import androidx.compose.runtime.mutableStateOf
import com.example.todopomodoro.utils.update

object ItemsRepository: Repository {
    private val itemsState = mutableStateOf(emptyList<String>())

    override fun add(record: String) {
        itemsState.update { it + record }
    }

    override fun getAll(): List<String> {
        return itemsState.value
    }
}