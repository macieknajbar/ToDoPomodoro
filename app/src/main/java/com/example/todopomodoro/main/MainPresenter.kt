package com.example.todopomodoro.main

import com.example.todopomodoro.repository.itemsState
import com.example.todopomodoro.utils.update

class MainPresenter {
    fun onDoneClicked(value: String) {
        updateState(value)
    }

    private fun updateState(value: String) {
        itemsState.update { it + value }
    }
}