package com.example.todopomodoro.main

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

internal class MainPresenterTest {

    @Test
    fun `ON onDoneClicked SHOULD update state`() {
        val value = "Hello world!"
        val updateState: MainPresenter.UpdateState = mock()
        val items = listOf("item 1", "item 2")
        val view: MainContract.View = mock()

        MainPresenter(
            view = view,
            updateState = updateState
        ).onDoneClicked(value)

        verify(updateState).exec(value)
        verify(view).updateItems(items)
    }
}