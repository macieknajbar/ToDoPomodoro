package com.example.todopomodoro.main

import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.repository.Repository
import com.example.todopomodoro.usecase.GetItems
import com.example.todopomodoro.usecase.di.getItemsUseCase
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
            .add(
                id = generatedId,
                record = ItemEntity(
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

        sut(itemsRepository = itemsRepository,
        getItems = getItems
        ) .apply { onCheckChanged(itemEntity1.id, true) }

        verify(itemsRepository).add(itemEntity1.id, itemEntity1.copy(isComplete = true))
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
    )
}