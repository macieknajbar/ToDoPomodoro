package com.example.todopomodoro.main

import com.example.todopomodoro.Fakes.Fakes
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.repository.Repository
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

        `when`(itemsRepository.getAll()).thenReturn(emptyList())

        sut(
            itemsRepository = itemsRepository,
            idGenerator = { generatedId },
        ).apply { onDoneClicked(value) }

        verify(itemsRepository)
            .update(
                id = generatedId,
                record = Fakes.item.copy(
                    id = generatedId,
                    text = value,
                    isComplete = false
                )
            )
    }

    @Test
    fun `ON onCheckChanged SHOULD mark the task as completed`() {
        val itemEntity1 = Fakes.item.copy(id = "item_1")
        val items = listOf(itemEntity1)
        val itemsRepository: Repository<ItemEntity> = mock()

        `when`(itemsRepository.getAll()).thenReturn(items)

        sut(itemsRepository = itemsRepository)
            .apply { onCheckChanged(itemEntity1.id, true) }

        verify(itemsRepository).update(itemEntity1.id, itemEntity1.copy(isComplete = true))
        verify(itemsRepository, times(2)).getAll()
    }

    @Test
    fun `ON onDateClicked SHOULD show date picker for specified item`() {
        val items = listOf(Fakes.item.copy(id = "i1"), Fakes.item.copy(id = "i2"))
        val itemMapper = ItemMapper(dateFormatter = { "" })

        val sut = sut(
            itemMapper = itemMapper,
        ).apply { onDateClicked(items.first().id) }

        assertEquals(
            items.first().id,
            sut.state.value.dateSelectionItemId
        )
    }

    @Test
    fun `ON onDateSelected SHOULD set date on specified item`() {
        val itemsRepository: Repository<ItemEntity> = mock()
        val item = Fakes.item
        val updatedItem = item.copy(dueDate = 987)

        `when`(itemsRepository.getAll()).thenReturn(listOf(item))

        sut(
            itemsRepository = itemsRepository,
            dateParser = { _, _, _ -> 987L },
        ).apply {
            onDateClicked(item.id)
            onDateSelected(1, 2, 3)
        }

        verify(itemsRepository).update(item.id, updatedItem)
    }

    @Test
    fun `ON onDateCancelClicked SHOULD remove due date`() {
        val itemsRepository: Repository<ItemEntity> = mock()
        val item = Fakes.item.copy(dueDate = 987)
        val updatedItem = item.copy(dueDate = null)

        `when`(itemsRepository.getAll()).thenReturn(listOf(item))

        sut(itemsRepository = itemsRepository)
            .apply {
                onDateClicked(item.id)
                onDateCancelClicked()
            }

        verify(itemsRepository).update(item.id, updatedItem)
    }

    private fun sut(
        itemsRepository: Repository<ItemEntity> = mock(),
        idGenerator: () -> String = mock(),
        dateParser: (year: Int, month: Int, day: Int) -> Long = mock(),
        itemMapper: ItemMapper = ItemMapper(dateFormatter = { "" }),
    ) = MainViewModel(
        itemsRepository = itemsRepository,
        idGenerator = idGenerator,
        dateParser = dateParser,
        itemMapper = itemMapper,
    )
}