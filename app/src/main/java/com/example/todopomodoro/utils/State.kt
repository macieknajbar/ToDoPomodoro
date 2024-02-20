package com.example.todopomodoro.utils

import androidx.compose.runtime.MutableState

fun <T> MutableState<T>.update(block: (T) -> T) {
    value = block(value)
}