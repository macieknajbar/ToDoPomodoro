package com.example.todopomodoro.main.vm

import com.example.todopomodoro.Fakes.Fakes
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.repository.Repository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

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
        val item = Fakes.item.copy(id = "item_1")
        val itemsRepository: Repository<ItemEntity> = mock()

        with(KArgCaptor<(List<ItemEntity>) -> Unit> {}) {
            `when`(itemsRepository.addObserver(capture())).then { value(listOf(item)) }
        }

        sut(itemsRepository = itemsRepository)
            .apply { onCheckChanged(item.id, true) }

        verify(itemsRepository).update(item.id, item.copy(isComplete = true))
    }

    @Test
    fun `ON onDateClicked SHOULD show date picker for specified item`() {
        val items = listOf(Fakes.item.copy(id = "i1"), Fakes.item.copy(id = "i2"))

        val sut = sut()
            .apply { onDateClicked(items.first().id) }

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

        with(KArgCaptor<(List<ItemEntity>) -> Unit> {}) {
            `when`(itemsRepository.addObserver(capture())).then { value(listOf(item)) }
        }

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

        with(KArgCaptor<(List<ItemEntity>) -> Unit> {}) {
            `when`(itemsRepository.addObserver(capture())).then { value(listOf(item)) }
        }

        sut(itemsRepository = itemsRepository)
            .apply {
                onDateClicked(item.id)
                onDateCancelClicked()
            }

        verify(itemsRepository).update(item.id, updatedItem)
    }

    @Test
    fun `ON onTextClicked SHOULD set the item in edit mode`() {
        val itemsRepository: Repository<ItemEntity> = mock()
        val item = Fakes.item.copy(id = "edit_item_id")

        with(KArgCaptor<(List<ItemEntity>) -> Unit> {}) {
            `when`(itemsRepository.addObserver(capture())).then { value(listOf(Fakes.item, item)) }
        }

        val sut = sut(itemsRepository = itemsRepository)
            .apply { onTextClicked(item.id) }

        assertEquals(
            item.id,
            sut.state.value.editItemId,
        )
    }

    class KArgCaptor<T>(private val defValue: T) {
        private val captor: ArgumentCaptor<T> = ArgumentCaptor.captor()
        val value: T get() = captor.value

        fun capture(): T {
            return captor.capture() ?: defValue
        }
    }

    private fun sut(
        itemsRepository: Repository<ItemEntity> = mock(),
        idGenerator: () -> String = mock(),
        dateParser: (year: Int, month: Int, day: Int) -> Long = mock(),
    ) = MainViewModel(
        itemsRepository = itemsRepository,
        idGenerator = idGenerator,
        dateParser = dateParser,
    )
}