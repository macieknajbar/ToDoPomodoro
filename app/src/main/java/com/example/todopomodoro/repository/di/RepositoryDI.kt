package com.example.todopomodoro.repository.di

import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.repository.ItemsRepository
import com.example.todopomodoro.repository.Repository

fun itemsRepository(): Repository<ItemEntity> = ItemsRepository