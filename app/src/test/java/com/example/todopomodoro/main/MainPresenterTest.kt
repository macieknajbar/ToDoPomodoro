package com.example.todopomodoro.main

import com.example.todopomodoro.repository.GetAllItems
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

internal class MainPresenterTest {

    @Test
    fun `ON onDoneClicked SHOULD update state`() {
        val value = "Hello world!"
        val updateState: MainPresenter.UpdateState = mock()
        val items = listOf("item 1", "item 2")
        val view: MainContract.View = mock()
        val getAllItems: GetAllItems = mock()

        `when`(getAllItems.exec()).thenReturn(items)

        MainPresenter(
            view = view,
            updateState = updateState,
            getAllItems = getAllItems
        ).onDoneClicked(value)

        verify(updateState).exec(value)
        verify(view).updateItems(items)
    }
}