package com.example.todopomodoro.utils.time

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class TimerImplTest {

    @Test
    fun `ON startSync SHOULD call onUpdate the first time`() {
        val timestampProvider: TimestampProvider = mock()
        val onUpdate: (Long) -> Unit = mock()

        `when`(timestampProvider.get()).thenReturn(10_000L)

        val actual = sut(timestampProvider = timestampProvider).startSync(
            timeEnd = 15_000,
            lastTime = 0L,
            interval = 1_000L,
            onUpdate = onUpdate
        )

        verify(onUpdate).invoke(5_000)
        assertEquals(
            10_000L,
            actual
        )
    }

    @Test
    fun `ON startSync SHOULD call onUpdate the second time`() {
        val timestampProvider: TimestampProvider = mock()
        val onUpdate: (Long) -> Unit = mock()

        `when`(timestampProvider.get()).thenReturn(11_000L)

        val actual = sut(timestampProvider = timestampProvider).startSync(
            timeEnd = 15_000,
            lastTime = 10_000L,
            interval = 1_000L,
            onUpdate = onUpdate
        )

        verify(onUpdate).invoke(4_000)
        assertEquals(
            11_000L,
            actual
        )
    }

    @Test
    fun `ON startSync SHOULD call onUpdate the last time`() {
        val timestampProvider: TimestampProvider = mock()
        val onUpdate: (Long) -> Unit = mock()

        `when`(timestampProvider.get()).thenReturn(15_000L)

        val actual = sut(timestampProvider = timestampProvider).startSync(
            timeEnd = 15_000,
            lastTime = 14_000L,
            interval = 1_000L,
            onUpdate = onUpdate
        )

        verify(onUpdate).invoke(0L)
        assertEquals(
            15_000L,
            actual
        )
    }

    @Test
    fun `ON startSync SHOULD call onUpdate after the last time`() {
        val timestampProvider: TimestampProvider = mock()
        val onUpdate: (Long) -> Unit = mock()

        `when`(timestampProvider.get()).thenReturn(16_000L)

        val actual = sut(timestampProvider = timestampProvider).startSync(
            timeEnd = 15_000,
            lastTime = 15_000L,
            interval = 1_000L,
            onUpdate = onUpdate
        )

        verify(onUpdate).invoke(0L)
        assertEquals(
            16_000L,
            actual
        )
    }

    @Test
    fun `ON startSync SHOULD not call onUpdate`() {
        val timestampProvider: TimestampProvider = mock()
        val onUpdate: (Long) -> Unit = mock()

        `when`(timestampProvider.get()).thenReturn(11_000L)

        val actual = sut(timestampProvider = timestampProvider).startSync(
            timeEnd = 15_000,
            lastTime = 10_500L,
            interval = 1_000L,
            onUpdate = onUpdate
        )

        verify(onUpdate, never()).invoke(anyLong())
        assertEquals(
            10_500L,
            actual
        )
    }

    private fun sut(timestampProvider: TimestampProvider = mock()) =
        TimerImpl(timestampProvider)
}