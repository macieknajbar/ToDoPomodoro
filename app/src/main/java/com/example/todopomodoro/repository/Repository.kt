package com.example.todopomodoro.repository

interface Repository<T> {
    fun update(id: String, record: T)
    fun getAll(): List<T>
    fun addObserver(observer: (List<T>) -> Unit)
    fun removeObserver(observer: (List<T>) -> Unit)
}
