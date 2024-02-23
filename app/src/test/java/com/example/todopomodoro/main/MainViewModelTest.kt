package com.example.todopomodoro.main

import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.usecase.GetItems
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

internal class MainViewModelTest {

    @Test
    fun `ON onDoneClicked SHOULD add new item`() {
        val generatedId = "items_id"
        val value = "Hello world!"
        val itemsRepository: Repository<ItemEntity> = mock()
        val getItems: GetItems = mock()

        `when`(itemsRepository.getAll()).thenReturn(emptyList())
        `when`(getItems.exec()).thenReturn(emptyList())

        sut(
            itemsRepository = itemsRepository,
            idGenerator = { generatedId },
            getItems = getItems,
        ).apply { onDoneClicked(value) }

        verify(itemsRepository)
            .update(
                id = generatedId,
                record = itemEntityFake.copy(
                    id = generatedId,
                    text = value,
                    isComplete = false
                )
            )
        verify(getItems, times(2)).exec()
    }

    @Test
    fun `ON onCheckChanged SHOULD mark the task as completed`() {
        val itemEntity1 = itemEntityFake.copy(id = "item_1")
        val items = listOf(itemEntity1)
        val itemsRepository: Repository<ItemEntity> = mock()
        val getItems: GetItems = mock()

        `when`(itemsRepository.getAll()).thenReturn(items)
        `when`(getItems.exec()).thenReturn(items)

        sut(
            itemsRepository = itemsRepository,
            getItems = getItems
        ).apply { onCheckChanged(itemEntity1.id, true) }

        verify(itemsRepository).update(itemEntity1.id, itemEntity1.copy(isComplete = true))
        verify(itemsRepository).getAll()
        verify(getItems, times(2)).exec()
    }

    @Test
    fun `ON init SHOULD sort the list active to complete`() {
        val itemsRepository: Repository<ItemEntity> = mock()
        val getItems: GetItems = mock()
        val item1 = itemEntityFake.copy(id = "i1", isComplete = true)
        val item2 = itemEntityFake.copy(id = "i2", isComplete = false)
        val items = listOf(item1, item2)
        val expected = items.map { ItemMapper().map(it) }

        `when`(itemsRepository.getAll()).thenReturn(items)
        `when`(getItems.exec()).thenReturn(items)

        val sut = sut(
            itemsRepository = itemsRepository,
            getItems = getItems,
        )

        verify(getItems).exec()
        assertEquals(
            expected,
            sut.items.value
        )
    }

    @Test
    fun `ON onDateClicked SHOULD show date picker for specified item`() {
        val getItems: GetItems = mock()
        val items = listOf(itemEntityFake.copy(id = "i1"), itemEntityFake.copy(id = "i2"))
        val expected = items.map { ItemMapper().map(it) }
            .toMutableList()
            .apply {
                add(1, first().copy(shouldShowDatePicker = true))
                removeAt(0)
            }

        `when`(getItems.exec()).thenReturn(items)

        val sut = sut(getItems = getItems)
            .apply { onDateClicked(items.first().id) }

        assertEquals(
            expected,
            sut.items.value
        )
    }

    @Test
    fun `ON onDateSelected SHOULD set date on specified item`() {
        val itemsRepository: Repository<ItemEntity> = mock()
        val item = itemEntityFake
        val updatedItem = itemEntityFake.copy(dueDate = 987)

        sut(itemsRepository = itemsRepository)
            .onDateSelected(1, 2, 3)

        verify(itemsRepository).update(item.id, updatedItem)
    }

    private fun sut(
        itemsRepository: Repository<ItemEntity> = mock(),
        idGenerator: () -> String = mock(),
        getItems: GetItems = mock(),
    ) = MainViewModel(
        itemsRepository = itemsRepository,
        idGenerator = idGenerator,
        getItems = getItems,
    )

    private val itemEntityFake = ItemEntity(
        id = "item_id",
        text = "Item",
        isComplete = false,
        dueDate = null,
    )
}