package com.example.todopomodoro.main.di

import com.example.todopomodoro.main.MainViewModel
import com.example.todopomodoro.repository.di.itemsRepository

fun mainViewModel(): MainViewModel = MainViewModel(
    itemsRepository = itemsRepository()
)
