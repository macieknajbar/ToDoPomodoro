package com.example.todopomodoro.main

import com.example.todopomodoro.repository.ItemsRepository

class MainPresenter(
    private val itemsRepository: ItemsRepository = ItemsRepository,
    private val view: MainContract.View,
) {
    fun onDoneClicked(value: String) {
        itemsRepository.add(value)
        view.updateItems(itemsRepository.getAll())
    }
}