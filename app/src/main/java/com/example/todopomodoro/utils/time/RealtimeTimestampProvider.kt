package com.example.todopomodoro.utils.time

object RealtimeTimestampProvider : TimestampProvider {
    override fun get(): Long {
        return System.currentTimeMillis()
    }
}