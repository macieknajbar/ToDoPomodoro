package com.example.todopomodoro.main

import com.example.todopomodoro.repository.GetAllItems
import com.example.todopomodoro.repository.itemsState
import com.example.todopomodoro.utils.update

class MainPresenter(
    private val updateState: UpdateState = UpdateState(),
    private val view: MainContract.View,
    private val getAllItems: GetAllItems = updateState,
) {
    fun onDoneClicked(value: String) {
        updateState.exec(value)
        view.updateItems(getAllItems.exec())
    }

    class UpdateState: GetAllItems {
        fun exec(value: String) {
            itemsState.update { it + value }
        }

        override fun exec(): List<String> {
            return itemsState.value
        }
    }

    class GetItems : GetAllItems {
        override fun exec(): List<String> {
            return itemsState.value
        }
    }
}