package com.example.todopomodoro.main.features.timer.di

import com.example.todopomodoro.main.features.timer.TimerViewModel
import com.example.todopomodoro.repository.di.itemsRepository

fun timerViewModel(itemId: String): TimerViewModel =
    TimerViewModel(
        itemId = itemId,
        itemsRepository = itemsRepository(),
    )
