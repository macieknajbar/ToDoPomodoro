package com.example.todopomodoro.di

import com.example.todopomodoro.utils.time.RealtimeTimestampProvider
import com.example.todopomodoro.utils.time.TimestampProvider
import com.example.todopomodoro.utils.time.TodayTimestampProvider

private val timestampProvider: TimestampProvider = RealtimeTimestampProvider()
fun timestampProvider(): TimestampProvider = timestampProvider

val todayTimestampProvider: TimestampProvider = TodayTimestampProvider()
fun todayTimestampProvider(): TimestampProvider = todayTimestampProvider



