package com.example.todopomodoro.repository

object ItemsRepository: Repository<String> {
    private val items = mutableMapOf<String, String>()

    override fun add(record: String) {
        items["items_${items.size}"] = record
    }

    override fun add(id: String, record: String) {
        items[id] = record
    }

    override fun getAll(): List<String> {
        return items.values.toList()
    }
}