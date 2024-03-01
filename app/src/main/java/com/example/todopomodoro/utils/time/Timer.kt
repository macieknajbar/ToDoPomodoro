package com.example.todopomodoro.utils.time

interface Timer {
    fun start(time: Long, onUpdate: (Long) -> Unit, onComplete: () -> Unit)
}