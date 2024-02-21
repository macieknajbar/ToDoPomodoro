package com.example.todopomodoro.repository

interface Repository<T> {
    fun add(record: T)
    fun getAll(): List<T>
}
