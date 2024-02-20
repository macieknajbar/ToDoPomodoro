package com.example.todopomodoro.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.todopomodoro.repository.ItemsRepository
import com.example.todopomodoro.utils.update

class MainPresenter(
    private val itemsRepository: ItemsRepository = ItemsRepository,
    private val view: MainContract.View,
) {

    val items: MutableState<List<String>> = mutableStateOf(listOf())

    fun onDoneClicked(value: String) {
        itemsRepository.add(value)
        view.updateItems(itemsRepository.getAll())
        items.update { itemsRepository.getAll() }
    }
}