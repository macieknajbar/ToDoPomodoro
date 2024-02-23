package com.example.todopomodoro.main.vm

import com.example.todopomodoro.Fakes.Fakes
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.main.vm.mapper.ItemMapper
import com.example.todopomodoro.repository.Repository
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

internal class MainViewModelTest {

    @Test
    fun `ON onDoneClicked SHOULD add new item`() {
        val generatedId = "items_id"
        val value = "Hello world!"
        val itemsRepository: Repository<ItemEntity> = Mockito.mock()

        Mockito.`when`(itemsRepository.getAll()).thenReturn(emptyList())

        sut(
            itemsRepository = itemsRepository,
            idGenerator = { generatedId },
        ).apply { onDoneClicked(value) }

        Mockito.verify(itemsRepository)
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
        val itemsRepository: Repository<ItemEntity> = Mockito.mock()

        Mockito.`when`(itemsRepository.getAll()).thenReturn(items)

        sut(itemsRepository = itemsRepository)
            .apply { onCheckChanged(itemEntity1.id, true) }

        Mockito.verify(itemsRepository).update(itemEntity1.id, itemEntity1.copy(isComplete = true))
        Mockito.verify(itemsRepository, Mockito.times(2)).getAll()
    }

    @Test
    fun `ON onDateClicked SHOULD show date picker for specified item`() {
        val items = listOf(Fakes.item.copy(id = "i1"), Fakes.item.copy(id = "i2"))

        val sut = sut()
            .apply { onDateClicked(items.first().id) }

        Assert.assertEquals(
            items.first().id,
            sut.state.value.dateSelectionItemId
        )
    }

    @Test
    fun `ON onDateSelected SHOULD set date on specified item`() {
        val itemsRepository: Repository<ItemEntity> = Mockito.mock()
        val item = Fakes.item
        val updatedItem = item.copy(dueDate = 987)

        Mockito.`when`(itemsRepository.getAll()).thenReturn(listOf(item))

        sut(
            itemsRepository = itemsRepository,
            dateParser = { _, _, _ -> 987L },
        ).apply {
            onDateClicked(item.id)
            onDateSelected(1, 2, 3)
        }

        Mockito.verify(itemsRepository).update(item.id, updatedItem)
    }

    @Test
    fun `ON onDateCancelClicked SHOULD remove due date`() {
        val itemsRepository: Repository<ItemEntity> = Mockito.mock()
        val item = Fakes.item.copy(dueDate = 987)
        val updatedItem = item.copy(dueDate = null)

        Mockito.`when`(itemsRepository.getAll()).thenReturn(listOf(item))

        sut(itemsRepository = itemsRepository)
            .apply {
                onDateClicked(item.id)
                onDateCancelClicked()
            }

        Mockito.verify(itemsRepository).update(item.id, updatedItem)
    }

    private fun sut(
        itemsRepository: Repository<ItemEntity> = Mockito.mock(),
        idGenerator: () -> String = Mockito.mock(),
        dateParser: (year: Int, month: Int, day: Int) -> Long = Mockito.mock(),
    ) = MainViewModel(
        itemsRepository = itemsRepository,
        idGenerator = idGenerator,
        dateParser = dateParser,
    )
}