package com.example.todopomodoro.main

import com.example.todopomodoro.repository.GetAllItems
import com.example.todopomodoro.repository.ItemsRepository

class MainPresenter(
    private val itemsRepository: ItemsRepository = ItemsRepository,
    private val view: MainContract.View,
    private val getAllItems: GetAllItems = itemsRepository,
) {
    fun onDoneClicked(value: String) {
        itemsRepository.add(value)
        view.updateItems(getAllItems.getAll())
    }
}