package com.example.todopomodoro.main

import com.example.todopomodoro.utils.update

class MainPresenter {
    fun onDoneClicked(value: String) {
        itemsState.update { it + value }
    }
}