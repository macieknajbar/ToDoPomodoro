package com.example.todopomodoro.main.di

import com.example.todopomodoro.main.vm.MainViewModel
import com.example.todopomodoro.repository.di.itemsRepository
import java.util.UUID

fun mainViewModel(): MainViewModel = MainViewModel(
    itemsRepository = itemsRepository(),
    idGenerator = { UUID.randomUUID().toString() },
)
