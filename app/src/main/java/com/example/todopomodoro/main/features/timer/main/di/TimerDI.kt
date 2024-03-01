package com.example.todopomodoro.main.features.timer.main.di

import com.example.todopomodoro.di.timer
import com.example.todopomodoro.main.features.timer.main.TimerViewModel
import com.example.todopomodoro.repository.di.itemsRepository

fun timerViewModel(itemId: String): TimerViewModel =
    TimerViewModel(
        itemId = itemId,
        itemsRepository = itemsRepository(),
        timer = timer()
    )
