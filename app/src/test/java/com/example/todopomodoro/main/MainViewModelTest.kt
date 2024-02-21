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
        val generatedId = "items_id"
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

        val sut = sut(
            itemsRepository = itemsRepository,
            idGenerator = { generatedId },
        ).apply { onDoneClicked(value) }

        verify(itemsRepository)
            .add(generatedId, ItemEntity(id = generatedId, text = value, isComplete = false))
        assertEquals(
            expected,
            sut.items.value
        )
    }

    @Test
    fun `ON init SHOULD get all items`() {
        val itemsRepository: Repository<ItemEntity> = mock()
        val itemEntity1 = itemEntityFake.copy(id = "item_1", text = "Item 1")
        val itemEntity2 = itemEntityFake.copy(id = "item_2", text = "Item 2")
        val items = listOf(itemEntity1, itemEntity2)
        val expected = listOf(
            itemModelFake.copy(id = "item_1", name = "Item 1"),
            itemModelFake.copy(id = "item_2", name = "Item 2"),
        )

        `when`(itemsRepository.getAll()).thenReturn(items)

        val sut = sut(itemsRepository = itemsRepository)

        assertEquals(
            expected,
            sut.items.value
        )
    }

    @Test
    fun `ON onCheckChanged SHOULD mark the task as completed`() {
        val itemEntity1 = itemEntityFake.copy(id = "item_1", text = "Item 1")
        val itemEntity2 = itemEntityFake.copy(id = "item_2", text = "Item 2")
        val items = listOf(itemEntity1, itemEntity2)
        val itemsRepository: Repository<ItemEntity> = mock()
        val expected = listOf(
            itemModelFake.copy(id = itemEntity2.id, name = itemEntity2.text, isChecked = false),
            itemModelFake.copy(id = itemEntity1.id, name = itemEntity1.text, isChecked = true)
        )

        `when`(itemsRepository.getAll()).thenReturn(items)

        val sut = sut(itemsRepository = itemsRepository)
            .apply { onCheckChanged(itemEntity1.id, true) }

        assertEquals(
            expected,
            sut.items.value
        )
    }

    private fun sut(
        itemsRepository: Repository<ItemEntity> = mock(),
        idGenerator: () -> String = mock(),
    ) = MainViewModel(
        itemsRepository = itemsRepository,
        idGenerator = idGenerator
    )

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