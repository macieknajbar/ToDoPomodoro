package com.example.todopomodoro.main.features.timer.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.main.features.timer.main.model.TimerViewState
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.utils.update
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TimerViewModel(
    val itemId: String,
    val itemsRepository: Repository<ItemEntity>,
) : ViewModel() {

    val viewState = mutableStateOf(TimerViewState())

    init {
        val item = itemsRepository.getAll()
            .first { it.id == itemId }

        viewState.update {
            copy(
                title = item.text,
                timeText = "20:00",
            )
        }
    }

    fun onStartClicked() {
        viewModelScope.launch {
            var now = System.currentTimeMillis()
            val time = TimeUnit.MINUTES.toMillis(2)
            val endTime = now + time - 1

            while (now < endTime) {
                var timeLeft = endTime - now
                val minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeft)
                timeLeft -= TimeUnit.MINUTES.toMillis(minutes)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeft)

                val secondsText = if (seconds < 10) "0$seconds" else seconds
                viewState.update { copy(timeText = "$minutes:$secondsText") }
                delay(1000)
                now = System.currentTimeMillis()
            }
        }
    }
}