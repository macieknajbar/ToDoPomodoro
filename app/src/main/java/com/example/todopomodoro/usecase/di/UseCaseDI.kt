package com.example.todopomodoro.usecase.di

import com.example.todopomodoro.repository.di.itemsRepository
import com.example.todopomodoro.usecase.GetItems

fun getItemsUseCase(): GetItems =
    GetItems(itemRepository = itemsRepository())