package com.example.todopomodoro.main

import com.example.todopomodoro.repository.GetAllItems
import com.example.todopomodoro.repository.itemsState
import com.example.todopomodoro.utils.update

class MainPresenter(
    private val updateState: UpdateState = UpdateState(),
    val view: MainContract.View,
    val getAllItems: GetAllItems = GetItems(),
) {
    fun onDoneClicked(value: String) {
        updateState.exec(value)
        val items = listOf("item 1")
        view.updateItems(getAllItems.exec())
    }

    class UpdateState {
        fun exec(value: String) {
            itemsState.update { it + value }
        }
    }

    class GetItems : GetAllItems {
        override fun exec(): List<String> {
            return itemsState.value
        }
    }
}