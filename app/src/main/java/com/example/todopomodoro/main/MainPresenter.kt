package com.example.todopomodoro.main

class MainPresenter {
    fun onDone(value: String) {
        itemsState.update { it + value }
    }
}