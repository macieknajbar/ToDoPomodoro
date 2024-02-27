package com.example.todopomodoro.main.features.timer.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.main.features.timer.main.model.TimerViewState
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.utils.update

class TimerViewModel(
    val itemId: String,
    val itemsRepository: Repository<ItemEntity>,
) : ViewModel() {

    val viewState = mutableStateOf(TimerViewState())

    init {
        val item = itemsRepository.getAll()
            .first { it.id == itemId }

        viewState.update { copy(title = item.text) }
    }
}