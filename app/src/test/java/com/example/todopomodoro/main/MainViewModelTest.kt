package com.example.todopomodoro.main

import com.example.todopomodoro.domain.ItemEntity
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
        val itemsRepository: Repository<ItemEntity> = mock()
        val itemEntity1 = itemEntityFake.copy(id = "item_1", text = "Item 1")
        val itemEntity2 = itemEntityFake.copy(id = "item_2", text = "Item 2")
        val items = listOf(itemEntity1, itemEntity2)
        val expected = listOf(
            itemModelFake.copy(id = itemEntity1.id, name = itemEntity1.text),
            itemModelFake.copy(id = itemEntity2.id, name = itemEntity2.text),
        )

        `when`(itemsRepository.getAll()).thenReturn(items)

        val sut = MainViewModel(
            itemsRepository = itemsRepository
        ).apply { onDoneClicked(value) }

        verify(itemsRepository)
            .add(ItemEntity(id = "items_id", text = value, isComplete = false))
        assertEquals(
            expected,
            sut.items.value
        )
    }

    @Test
    fun `ON init SHOULD get all items`() {
        val itemsRepository: Repository<ItemEntity> = mock()
        val itemEntity1 = ItemEntity("", "", false)
        val itemEntity2 = ItemEntity("", "", false)
        val items = listOf(itemEntity1, itemEntity2)
        val expected = listOf(
            itemModelFake.copy(id = "item 1", name = "item 1"),
            itemModelFake.copy(id = "item 2", name = "item 2"),
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
        val itemId1 = "Item 1"
        val itemId2 = "Item 2"
        val itemEntity1 = ItemEntity("", "", false)
        val itemEntity2 = ItemEntity("", "", false)
        val items = listOf(itemEntity1, itemEntity2)
        val itemsRepository: Repository<ItemEntity> = mock()
        val expected = listOf(
            itemModelFake.copy(id = itemId2, name = itemId2, isChecked = false),
            itemModelFake.copy(id = itemId1, name = itemId1, isChecked = true)
        )

        `when`(itemsRepository.getAll()).thenReturn(items)

        val sut = MainViewModel(
            itemsRepository = itemsRepository,
        ).apply { onCheckChanged(itemId1, true) }

        assertEquals(
            expected,
            sut.items.value
        )
    }

    private val itemModelFake = MainViewModel.ItemModel(
        id = "item_0",
        name = "Item",
        isChecked = false,
    )

    private val itemEntityFake = ItemEntity(
        id = "item_id",
        text = "Item",
        isComplete = false,
    )
}