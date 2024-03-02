package com.example.todopomodoro.main.features.timer.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.main.features.timer.main.model.TimerViewState
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.utils.time.Timer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TimerViewModel(
    val itemId: String,
    val itemsRepository: Repository<ItemEntity>,
    val timer: Timer,
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
    val routing = MutableLiveData<TimerRouting>(TimerRouting.Idle)

    init {
        val item = itemsRepository.getAll()
            .first { it.id == itemId }

        state.update { it.copy(title = item.text) }
    }

    fun onStartClicked() {
        timer.start(
            time = state.value.timeLeft,
            onUpdate = { timeLeft -> state.update { it.copy(timeLeft = timeLeft) } },
        ) { routing.navigateTo(TimerRouting.Break) }
    }

    fun MutableLiveData<TimerRouting>.navigateTo(screen: TimerRouting) {
        viewModelScope.launch(Dispatchers.Main) {
            value = screen
            value = TimerRouting.Idle
        }
    }

    data class TimerState(
        val title: String = "",
        val timeLeft: Long = TimeUnit.SECONDS.toMillis(20),
    )

    sealed class TimerRouting {
        object Idle : TimerRouting()
        object Break : TimerRouting()
    }
}