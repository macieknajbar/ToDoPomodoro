package com.example.todopomodoro.repository

import com.example.todopomodoro.domain.ItemEntity

object ItemsRepository: Repository<ItemEntity> {
    private val items = mutableMapOf<String, ItemEntity>()
    private val observers: MutableList<(List<ItemEntity>) -> Unit> = mutableListOf()

    override fun update(id: String, record: ItemEntity) {
        items[id] = record
        observers.forEach { it(items.values.toList()) }
    }

    override fun getAll(): List<ItemEntity> {
        return items.values.toList()
    }

    override fun addObserver(observer: (List<ItemEntity>) -> Unit) {
        observers.add(observer)
        observer(items.values.toList())
    }

    override fun removeObserver(observer: (List<ItemEntity>) -> Unit) {
        observers.remove(observer)
    }
}