package com.example.todopomodoro.repository

import com.example.todopomodoro.domain.ItemEntity

object ItemsRepository: Repository<ItemEntity> {
    private val items = mutableMapOf<String, ItemEntity>()

    override fun update(id: String, record: ItemEntity) {
        items[id] = record
    }

    override fun getAll(): List<ItemEntity> {
        return items.values.toList()
    }
}