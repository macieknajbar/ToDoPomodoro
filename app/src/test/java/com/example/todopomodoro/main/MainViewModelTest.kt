package com.example.todopomodoro.main

import com.example.todopomodoro.repository.ItemsRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

internal class MainViewModelTest {

    @Test
    fun `ON onDoneClicked SHOULD update state`() {
        val value = "Hello world!"
        val itemsRepository: ItemsRepository = mock()
        val items = listOf("item 1", "item 2")

        `when`(itemsRepository.getAll()).thenReturn(items)

        val sut = MainViewModel(
            itemsRepository = itemsRepository
        ).apply { onDoneClicked(value) }

        verify(itemsRepository).add(value)
        assertEquals(
            items,
            sut.items.value
        )
    }
}