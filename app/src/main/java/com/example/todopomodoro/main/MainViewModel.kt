package com.example.todopomodoro.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.utils.update

class MainViewModel(
    private val itemsRepository: Repository,
) {

    val items: MutableState<List<String>> = mutableStateOf(listOf())

    fun onDoneClicked(value: String) {
        itemsRepository.add(value)
        items.update { itemsRepository.getAll() }
    }
}