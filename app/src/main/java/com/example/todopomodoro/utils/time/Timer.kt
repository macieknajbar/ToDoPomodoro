package com.example.todopomodoro.utils.time

interface Timer {
    fun start(time: Long, interval: Long, onUpdate: (Long) -> Unit)
}