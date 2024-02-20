package com.example.todopomodoro.main

import com.example.todopomodoro.repository.itemsState
import com.example.todopomodoro.utils.update

class MainPresenter(
    private val updateState: UpdateState = UpdateState(),
    val view: MainContract.View,
) {
    fun onDoneClicked(value: String) {
        updateState.exec(value)
        val items = listOf("item 1", "item 2")
        view.updateItems(items)
    }

    class UpdateState {
        fun exec(value: String) {
            itemsState.update { it + value }
        }
    }
}