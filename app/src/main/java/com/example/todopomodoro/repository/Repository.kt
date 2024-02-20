package com.example.todopomodoro.repository

interface Repository {
    fun add(record: String)
    fun getAll(): List<String>
}
