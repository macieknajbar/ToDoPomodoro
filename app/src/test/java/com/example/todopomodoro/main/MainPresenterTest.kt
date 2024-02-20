package com.example.todopomodoro.main

import com.example.todopomodoro.repository.ItemsRepository
import org.junit.Assert.assertEquals
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

        `when`(itemsRepository.getAll()).thenReturn(items)

        val sut = MainPresenter(
            itemsRepository = itemsRepository,
            view = view
        ).apply { onDoneClicked(value) }

        verify(itemsRepository).add(value)
        verify(view).updateItems(items)
        assertEquals(
            items,
            sut.items.value
        )
    }
}