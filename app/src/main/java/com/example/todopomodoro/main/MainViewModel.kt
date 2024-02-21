package com.example.todopomodoro.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.utils.update

class MainViewModel(
    private val itemsRepository: Repository,
) : ViewModel() {

    val items: MutableState<List<ItemModel>> =
        mutableStateOf(
            itemsRepository.getAll()
                .map {
                    ItemModel(
                        id = it,
                        name = it
                    )
                }
        )

    fun onDoneClicked(value: String) {
        itemsRepository.add(value)
        items.update {
            itemsRepository
                .getAll().map {
                    ItemModel(
                        id = it,
                        name = it
                    )
                }
        }
    }

    fun onCheckChanged(itemId: String, isChecked: Boolean) {
        items.update {
            val itemIdx = it.indexOfFirst { it.name == itemId }
            val item = it[itemIdx]

            it.toMutableList()
                .apply { removeAt(itemIdx) }
                .apply { add(itemIdx, ItemModel(item.id, item.name, isChecked = isChecked)) }
        }
    }

    data class ItemModel(
        val id: String,
        val name: String,
        val isChecked: Boolean = false,
    )
}