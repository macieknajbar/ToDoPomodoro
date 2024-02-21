package com.example.todopomodoro.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.utils.update

class MainViewModel(
    private val itemsRepository: Repository<ItemEntity>,
    private val itemMapper: ItemMapper = ItemMapper(),
    private val idGenerator: () -> String,
) : ViewModel() {

    val items: MutableState<List<ItemModel>> =
        mutableStateOf(
            itemsRepository.getAll()
                .sortedBy { it.isComplete }
                .map(itemMapper::map)
        )

    fun onDoneClicked(value: String) {
        val generatedId = idGenerator()
        itemsRepository.add(generatedId, ItemEntity(generatedId, value, false))

        items.update { itemsRepository.getAll().map(itemMapper::map) }
    }

    fun onCheckChanged(itemId: String, isChecked: Boolean) {
        itemsRepository
            .getAll()
            .first { it.id == itemId }
            .copy(isComplete = isChecked)
            .let { itemsRepository.add(it.id, it) }

        items.update { itemsRepository.getAll().map(itemMapper::map) }
    }

    data class ItemModel(
        val id: String,
        val name: String,
        val isChecked: Boolean,
    )
}