package com.example.todopomodoro.main.features.timer.main

import com.example.todopomodoro.Fakes.Fakes
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.utils.time.Timer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.util.concurrent.TimeUnit

class TimerViewModelTest {

    @Test
    fun `ON init SHOULD set initial state`() {
        val itemsRepository: Repository<ItemEntity> = mock()
        val item = Fakes.item.copy(text = "title")

        `when`(itemsRepository.getAll()).thenReturn(listOf(item))

        val sut = sut(
            itemId = item.id,
            itemsRepository = itemsRepository,
        )

        assertEquals(
            TimerViewModel.TimerState(
                title = item.text,
                timeLeft = TimeUnit.MINUTES.toMillis(20),
            ),
            sut.state.value
        )
    }

    @Test
    fun `ON onStartClicked SHOULD start timer`() {
        val timer: Timer = mock()
        val time = TimeUnit.MINUTES.toMillis(20)
        val itemsRepository: Repository<ItemEntity> = mock()

        `when`(itemsRepository.getAll()).thenReturn(listOf(Fakes.item))

        sut(
            itemId = Fakes.item.id,
            itemsRepository = itemsRepository,
            timer = timer
        ).onStartClicked()

        verify(timer).start(
            time = eq(time),
            onUpdate = any(),
            onComplete = any(),
        )
    }


    @Test
    fun `ON onStartClicked SHOULD update state WHEN timer update triggered`() {
        val timer: Timer = mock()
        val captor: ArgumentCaptor<(Long) -> Unit> = ArgumentCaptor.captor()
        val timeLeft = TimeUnit.MINUTES.toMillis(15)

        with(captor) {
            `when`(timer.start(anyLong(), kapture(), any())).then { value.invoke(timeLeft) }
        }

        val sut = sut(timer = timer)
            .apply { onStartClicked() }

        assertEquals(
            timeLeft,
            sut.state.value.timeLeft,
        )
    }

    fun <T> ArgumentCaptor<T>.kapture(): T {
        return capture()
    }

    fun <T> any(): T {
        return Mockito.any()
    }

    private fun sut(
        itemId: String = Fakes.item.id,
        itemsRepository: Repository<ItemEntity> = mock<Repository<ItemEntity>>()
            .apply { `when`(getAll()).thenReturn(listOf(Fakes.item)) },
        timer: Timer = mock(),
    ) = TimerViewModel(
        itemId = itemId,
        itemsRepository = itemsRepository,
        timer = timer,
    )
}