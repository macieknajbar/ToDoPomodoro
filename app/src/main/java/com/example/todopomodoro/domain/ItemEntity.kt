package com.example.todopomodoro.domain

data class ItemEntity(
    val id: String,
    val text: String,
    val isComplete: Boolean = false,
    val dueDate: Long? = null,
)