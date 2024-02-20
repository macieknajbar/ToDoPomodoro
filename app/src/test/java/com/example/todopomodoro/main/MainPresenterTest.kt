package com.example.todopomodoro.main

import com.example.todopomodoro.repository.GetAllItems
import com.example.todopomodoro.repository.ItemsRepository
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

internal class MainPresenterTest {

    @Test
    fun `ON onDoneClicked SHOULD update state`() {
        val value = "Hello world!"
        val itemsRepository: ItemsRepository = mock()
        val items = listOf("item 1", "item 2")
        val view: MainContract.View = mock()
        val getAllItems: GetAllItems = mock()

        `when`(getAllItems.getAll()).thenReturn(items)

        MainPresenter(
            view = view,
            itemsRepository = itemsRepository,
            getAllItems = getAllItems
        ).onDoneClicked(value)

        verify(itemsRepository).add(value)
        verify(view).updateItems(items)
    }
}