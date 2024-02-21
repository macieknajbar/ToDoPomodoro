package com.example.todopomodoro.usecase

import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.repository.Repository

class GetItems(
    private val itemRepository: Repository<ItemEntity>
) {
    fun exec(): List<ItemEntity> {
        return itemRepository.getAll()
            .sortedBy { it.isComplete }
    }
}