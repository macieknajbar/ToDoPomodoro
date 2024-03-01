package com.example.todopomodoro.utils.time

import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class TimerImplTest {

    //    @Test
//    fun `ON start SHOULD call block with time left`() {
//        val update: (Long) -> Unit = mock()
//
//        sut().start(0L, update)
//
//        verify(update).invoke(0)
//    }
//
    @Test
    fun `ON startSync SHOULD call onUpdate`() {
        val onUpdate: (Long) -> Unit = mock()

        sut().startSync(onUpdate)

        verify(onUpdate).invoke(anyLong())
    }

    private fun sut(timestampProvider: TimestampProvider = mock()) =
        TimerImpl(timestampProvider)
}