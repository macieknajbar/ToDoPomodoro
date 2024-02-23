package com.example.todopomodoro.usecase

import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.repository.Repository
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetItemsTest {

    @Test
    fun `ON exec SHOULD get items sorted active to complete`() {
        val itemsRepository: Repository<ItemEntity> = mock()
        val item1 = item.copy(id = "1", isComplete = true)
        val item2 = item.copy(id = "2", isComplete = false)
        val items = listOf(item1, item2)
        val expected = listOf(item2, item1)

        `when`(itemsRepository.getAll()).thenReturn(items)

        val actual = GetItems(itemsRepository).exec()

        assertEquals(
            expected,
            actual
        )
    }

    val item = ItemEntity(
        id = "item_id",
        text = "Item",
        isComplete = false,
        dueDate = null,
    )
}