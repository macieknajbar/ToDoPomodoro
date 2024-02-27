package com.example.todopomodoro.main.features.timer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.main.features.timer.main.model.TimerViewState
import com.example.todopomodoro.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TimerViewModel(
    val itemId: String,
    val itemsRepository: Repository<ItemEntity>,
) : ViewModel() {

    val state = MutableStateFlow(TimerState())
    val viewState: Flow<TimerViewState> = state.map {
        val minutes = TimeUnit.MILLISECONDS
            .toMinutes(it.timeLeft)
        val seconds = TimeUnit.MILLISECONDS
            .toSeconds(it.timeLeft - TimeUnit.MINUTES.toMillis(minutes))
            .toString()

        TimerViewState(
            title = it.title,
            timeText = "$minutes:${"00$seconds".substring(seconds.length)}"
        )
    }

    init {
        val item = itemsRepository.getAll()
            .first { it.id == itemId }

        state.update { it.copy(title = item.text) }
    }

    fun onStartClicked() {
        viewModelScope.launch {
            var now = System.currentTimeMillis()
            val time = state.value.timeLeft
            val endTime = now + time - 1

            while (now < endTime) {
                state.update { it.copy(timeLeft = endTime - now) }
                delay(1000)
                now = System.currentTimeMillis()
            }
        }
    }

    data class TimerState(
        val title: String = "",
        val timeLeft: Long = TimeUnit.MINUTES.toMillis(20),
    )
}