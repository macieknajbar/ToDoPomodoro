package com.example.todopomodoro.main

class MainPresenter {
    fun onDoneClicked(value: String) {
        itemsState.update { it + value }
    }
}