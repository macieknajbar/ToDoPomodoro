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
    fun `ON onDoneClicked SHOULD add new item`() {
        val generatedId = "items_id"
        val value = "Hello world!"
        val itemsRepository: Repository<ItemEntity> = mock()

        `when`(itemsRepository.getAll()).thenReturn(emptyList())

        sut(
            itemsRepository = itemsRepository,
            idGenerator = { generatedId },
        ).apply { onDoneClicked(value) }

        verify(itemsRepository)
            .add(
                id = generatedId,
                record = ItemEntity(
                    id = generatedId,
                    text = value,
                    isComplete = false
                )
            )
    }

    @Test
    fun `ON init SHOULD get all items`() {
        val itemsRepository: Repository<ItemEntity> = mock()
        val items = listOf(
            itemEntityFake.copy(id = "item_1"),
            itemEntityFake.copy(id = "item_2")
        )
        val expected = items.map { ItemMapper().map(it) }

        `when`(itemsRepository.getAll()).thenReturn(items)

        val sut = sut(itemsRepository = itemsRepository)

        assertEquals(
            expected,
            sut.items.value
        )
    }

    @Test
    fun `ON onCheckChanged SHOULD mark the task as completed`() {
        val itemEntity1 = itemEntityFake.copy(id = "item_1")
        val itemEntity2 = itemEntityFake.copy(id = "item_2")
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

    @Test
    fun `ON onCheckChanged SHOULD sort the list active to complete`() {
        val itemsRepository: Repository<ItemEntity> = mock()
        val item1 = itemEntityFake.copy(id = "i1", isComplete = true)
        val item2 = itemEntityFake.copy(id = "i2", isComplete = false)
        val items = listOf(item1, item2)
        val expected = items
            .map { ItemMapper().map(it) }
            .sortedBy { it.isChecked }

        `when`(itemsRepository.getAll()).thenReturn(items)

        val sut = sut(itemsRepository = itemsRepository)

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