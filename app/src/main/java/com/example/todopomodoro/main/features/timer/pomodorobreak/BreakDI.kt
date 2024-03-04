package com.example.todopomodoro.main.features.timer.pomodorobreak

import com.example.todopomodoro.di.timer

fun breakViewModel(): PomodoroBreakViewModel = PomodoroBreakViewModel(timer = timer())