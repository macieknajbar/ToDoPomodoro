package com.example.todopomodoro.main

import com.example.todopomodoro.repository.itemsState
import com.example.todopomodoro.utils.update

class MainPresenter(
    private val updateState: UpdateState = UpdateState(),
) {
    fun onDoneClicked(value: String) {
        updateState.exec(value)
    }

    open class UpdateState {
        open fun exec(value: String) {
            itemsState.update { it + value }
        }
    }
}