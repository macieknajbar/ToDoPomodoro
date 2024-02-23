package com.example.todopomodoro.Fakes

import com.example.todopomodoro.domain.ItemEntity

object Fakes {
    val item = ItemEntity(
        id = "item_id",
        text = "Item",
        isComplete = false,
        dueDate = null,
    )
}