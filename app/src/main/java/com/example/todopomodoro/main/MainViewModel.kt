package com.example.todopomodoro.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.usecase.GetItems
import com.example.todopomodoro.utils.update

class MainViewModel(
    private val itemsRepository: Repository<ItemEntity>,
    private val itemMapper: ItemMapper = ItemMapper(),
    private val idGenerator: () -> String,
    private val getItems: GetItems,
) : ViewModel() {

    val items: MutableState<List<ItemModel>> =
        mutableStateOf(getItems.exec().map(itemMapper::map))

    fun onDoneClicked(value: String) {
        val generatedId = idGenerator()
        itemsRepository.update(generatedId, ItemEntity(generatedId, value, false))

        items.update { getItems.exec().map(itemMapper::map) }
    }

    fun onCheckChanged(itemId: String, isChecked: Boolean) {
        itemsRepository
            .getAll()
            .first { it.id == itemId }
            .copy(isComplete = isChecked)
            .let { itemsRepository.update(it.id, it) }

        items.update { getItems.exec().map(itemMapper::map) }
    }

    fun onDateClicked(itemId: String) {
        val item = items.value
            .first { it.id == itemId }

        val updatedItem = item.copy(shouldShowDatePicker = true)

        items.update {
            toMutableList()
                .apply {
                    val idx = indexOf(item)
                    removeAt(idx)
                    add(idx, updatedItem)
                }
        }
    }

    fun onDateSelected(year: Int, month: Int, day: Int) {

    }

    data class ItemModel(
        val id: String,
        val name: String,
        val isChecked: Boolean = false,
        val shouldShowDatePicker: Boolean = false,
    )
}