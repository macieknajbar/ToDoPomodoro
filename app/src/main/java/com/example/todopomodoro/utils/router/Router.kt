package com.example.todopomodoro.utils.router

class Router<T>(private val routing: (T) -> Unit) {
    fun goTo(screen: T) {
        try {
            routing(screen)
        } catch (e: Exception) {
            // intentionally empty
        }
    }
}
