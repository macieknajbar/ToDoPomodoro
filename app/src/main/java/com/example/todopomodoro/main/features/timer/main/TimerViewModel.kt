package com.example.todopomodoro.main.features.timer.main

import androidx.lifecycle.ViewModel
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.main.features.timer.main.model.TimerViewState
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.utils.router.Router
import com.example.todopomodoro.utils.time.Timer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
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
    private lateinit var router: Router<TimerRouting>

    init {
        val item = itemsRepository.getAll()
            .first { it.id == itemId }

        state.update { it.copy(title = item.text) }
    }

    fun onStartClicked() {
        timer.start(
            time = state.value.timeLeft,
            onUpdate = { timeLeft -> state.update { it.copy(timeLeft = timeLeft) } },
        ) {
            state.update { it.copy(timeLeft = it.initialTime) }
            router.goTo(TimerRouting.Break)
        }
    }

    fun onNavigation(block: (TimerRouting) -> Unit) {
        router = Router(block)
    }

    data class TimerState(
        val initialTime: Long = TimeUnit.SECONDS.toMillis(5),
        val title: String = "",
        val timeLeft: Long = initialTime,
    )

    sealed class TimerRouting {
        object Break : TimerRouting()
    }
}