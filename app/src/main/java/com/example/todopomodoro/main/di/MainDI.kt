package com.example.todopomodoro.main.di

import com.example.todopomodoro.main.MainViewModel
import com.example.todopomodoro.repository.di.itemsRepository
import com.example.todopomodoro.usecase.di.getItemsUseCase
import java.util.UUID

fun mainViewModel(): MainViewModel = MainViewModel(
    itemsRepository = itemsRepository(),
    idGenerator = { UUID.randomUUID().toString() },
    getItems = getItemsUseCase()
)
