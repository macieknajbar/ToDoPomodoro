package com.example.todopomodoro.repository

interface Repository<T> {
    fun add(record: T)
    fun add(id: String, record: T)
    fun getAll(): List<T>
}
