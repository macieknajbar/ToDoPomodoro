package com.example.todopomodoro.main

import org.junit.Assert.assertEquals
import org.junit.Test

internal class MainPresenterTest {

    @Test
    fun `ON onDoneClicked SHOULD update state`() {
        val value = "Hello world!"
        val updateState = UpdateStateMock(value)

        MainPresenter(updateState = updateState)
            .onDoneClicked(value)

        updateState.verify()
    }

    class UpdateStateMock(val expected: String): MainPresenter.UpdateState() {
        private var counter = 0
        private lateinit var actual: String

        override fun exec(value: String) {
            counter++
            actual = value
        }

        fun verify() {
            assertEquals(1, counter)
            assertEquals(expected, actual)
        }
    }
}