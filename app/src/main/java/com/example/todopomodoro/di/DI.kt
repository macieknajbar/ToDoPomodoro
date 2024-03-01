package com.example.todopomodoro.di

import com.example.todopomodoro.utils.date.DateFormatter
import com.example.todopomodoro.utils.date.SimpleDateFormatter
import com.example.todopomodoro.utils.time.RealtimeTimestampProvider
import com.example.todopomodoro.utils.time.Timer
import com.example.todopomodoro.utils.time.TimerImpl
import com.example.todopomodoro.utils.time.TimestampProvider
import com.example.todopomodoro.utils.time.TodayTimestampProvider

val todayTimestampProvider: TimestampProvider = TodayTimestampProvider()
fun todayTimestampProvider(): TimestampProvider = todayTimestampProvider

fun realtimeTimestampProvider(): TimestampProvider = RealtimeTimestampProvider

fun timer(): Timer = TimerImpl(timestampProvider = realtimeTimestampProvider())

fun dateFormatter(pattern: String): DateFormatter = SimpleDateFormatter(pattern)

fun stringProvider(): (Int) -> String = { "No Date" }

