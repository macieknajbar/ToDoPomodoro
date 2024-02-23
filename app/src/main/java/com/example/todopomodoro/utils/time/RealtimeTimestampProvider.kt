package com.example.todopomodoro.utils.time

class RealtimeTimestampProvider : TimestampProvider {
    override fun get(): Long = System.currentTimeMillis()
}