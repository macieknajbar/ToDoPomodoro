package com.example.todopomodoro.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.utils.update

class MainViewModel(
    private val itemsRepository: Repository,
): ViewModel() {

    val items: MutableState<List<ItemModel>> = mutableStateOf(itemsRepository.getAll().map(::ItemModel))

    fun onDoneClicked(value: String) {
        itemsRepository.add(value)
        items.update { itemsRepository.getAll().map(::ItemModel) }
    }

    fun onCheckChanged(isChecked: Boolean) {

    }

    class ItemModel(val name: String)
}