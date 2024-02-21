package com.example.todopomodoro.repository

interface Repository<T> {
    fun update(id: String, record: T)
    fun getAll(): List<T>
}
