package com.example.todopomodoro.utils.time

import kotlin.concurrent.thread

class TimerImpl(
    private val timestampProvider: TimestampProvider,
) : Timer {

    override fun start(
        time: Long,
        onUpdate: (Long) -> Unit,
        onComplete: () -> Unit,
    ) {
        thread {
            val now = timestampProvider.get()
            val timeEnd = now + time
            var timestamp = 0L
            while (timestamp < timeEnd) {
                timestamp = startSync(
                    timeEnd = timeEnd,
                    lastTime = timestamp,
                    interval = 1_000,
                    onUpdate = onUpdate
                )
            }
            onComplete()
        }
    }

    fun startSync(
        timeEnd: Long,
        lastTime: Long,
        interval: Long,
        onUpdate: (Long) -> Unit,
    ): Long {
        val now = timestampProvider.get()
        if (now - lastTime < interval) {
            return lastTime
        }

        if (timeEnd - now < 0) {
            onUpdate(0)
        } else {
            onUpdate(timeEnd - now)
        }

        return now
    }
}