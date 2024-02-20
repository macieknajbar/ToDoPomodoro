package com.example.todopomodoro.main

interface MainContract {
    interface View {
        fun updateItems(items: List<String>)
    }
}
