package com.example.todopomodoro.main.di

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todopomodoro.main.MainViewModel
import com.example.todopomodoro.repository.di.itemsRepository

fun mainViewModel(): MainViewModel = MainViewModel(
    itemsRepository = itemsRepository()
)

fun ComponentActivity.mainVM(): MainViewModel = ViewModelProvider(
    owner = this,
    factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return mainViewModel() as T
        }
    }
)[MainViewModel::class.java]
