package com.example.todopomodoro.utils.time

import kotlin.concurrent.thread

class TimerImpl(
    private val timestampProvider: TimestampProvider,
) : Timer {

//    override fun start(initTime: Long, intervalSeconds: Int, onUpdate: (Long) -> Unit) {
//        var lastTimestamp = -1L
//        thread {
//            val initTime = timestampProvider.get() + initTime
//            while (initTime - lastTimestamp > 0) {
//                lastTimestamp = startSync(initTime, intervalSeconds * 1000L, lastTimestamp, onUpdate)
//                Thread.sleep(intervalSeconds * 1000L)
//            }
//        }
//    }
//
//    fun startSync(initTime: Long, interval: Long, lastTimestamp: Long, onUpdate: (Long) -> Unit): Long {
//        val timestamp = timestampProvider.get()
//        if (lastTimestamp == -1L) {
//            onUpdate(initTime - timestamp - interval)
//            return timestamp
//        }
//        if (timestamp - lastTimestamp < interval)
//            return lastTimestamp
//
//        onUpdate(initTime - timestamp)
//
//        return timestamp
//    }

    override fun start(time: Long, interval: Long, onUpdate: (Long) -> Unit) {
        startSync()
    }

    fun startSync() {

    }
}