package com.example.todopomodoro.main

import com.example.todopomodoro.repository.Repository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

internal class MainViewModelTest {

    @Test
    fun `ON onDoneClicked SHOULD update state`() {
        val value = "Hello world!"
        val itemsRepository: Repository = mock()
        val items = listOf("item 1", "item 2")
        val expected = listOf(
            MainViewModel.ItemModel("item 1", "item 1"),
            MainViewModel.ItemModel("item 2", "item 2"),
        )

        `when`(itemsRepository.getAll()).thenReturn(items)

        val sut = MainViewModel(
            itemsRepository = itemsRepository
        ).apply { onDoneClicked(value) }

        verify(itemsRepository).add(value)
        assertEquals(
            expected,
            sut.items.value
        )
    }

    @Test
    fun `ON init SHOULD get all items`() {
        val itemsRepository: Repository = mock()
        val items = listOf("item 1", "item 2")
        val expected = listOf(
            MainViewModel.ItemModel("item 1", "item 1"),
            MainViewModel.ItemModel("item 2", "item 2"),
        )

        `when`(itemsRepository.getAll()).thenReturn(items)

        val sut = MainViewModel(
            itemsRepository = itemsRepository
        )

        assertEquals(
            expected,
            sut.items.value
        )
    }

    @Test
    fun `ON onCheckChanged SHOULD mark the task as completed`() {
        val items = listOf("Item 1", "Item 2")
        val itemsRepository: Repository = mock()
        val expected = listOf(
            MainViewModel.ItemModel(id = "Item 1", name = "Item 1", isChecked = false),
            MainViewModel.ItemModel(id = "Item 2", name = "Item 2", isChecked = true)
        )
        
        `when`(itemsRepository.getAll()).thenReturn(items)

        val sut = MainViewModel(
            itemsRepository = itemsRepository,
        )
        sut.onCheckChanged("Item 2", true)

        assertEquals(
            expected,
            sut.items.value
        )
    }
}