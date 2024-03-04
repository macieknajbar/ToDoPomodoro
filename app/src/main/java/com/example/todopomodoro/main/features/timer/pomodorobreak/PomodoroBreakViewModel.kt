package com.example.todopomodoro.main.features.timer.pomodorobreak

import androidx.lifecycle.ViewModel
import com.example.todopomodoro.utils.router.Router
import com.example.todopomodoro.utils.time.Timer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.concurrent.TimeUnit

class PomodoroBreakViewModel(
    timer: Timer,
) : ViewModel() {

    val state = MutableStateFlow(BreakState())
    val viewState: Flow<BreakViewState> = state.map {
        val minutes = TimeUnit.MILLISECONDS
            .toMinutes(it.timeLeft)
        val seconds = TimeUnit.MILLISECONDS
            .toSeconds(it.timeLeft - TimeUnit.MINUTES.toMillis(minutes))
            .toString()

        BreakViewState(
            timeText = "$minutes:${"00$seconds".substring(seconds.length)}"
        )
    }
    private lateinit var router: Router<BreakRouting>

    init {
        timer.start(
            time = state.value.timeLeft,
            onUpdate = { timeLeft -> state.update { it.copy(timeLeft = timeLeft) } }
        ) { router.goTo(BreakRouting.Back) }
    }

    fun onNavigation(block: (BreakRouting) -> Unit) {
        router = Router(block)
    }

    fun onCloseClicked() {
        router.goTo(BreakRouting.Close)
    }

    data class BreakState(
        val timeLeft: Long = TimeUnit.SECONDS.toMillis(3),
    )

    data class BreakViewState(
        val timeText: String = "",
    )

    sealed class BreakRouting {
        object Close : BreakRouting()
        object Back: BreakRouting()
    }
}
