package com.example.todopomodoro.main

import com.example.todopomodoro.domain.ItemEntity
import org.junit.Assert.*
import org.junit.Test

class ItemMapperTest {

    @Test
    fun `test map entity`() {
        val expected = MainViewModel.ItemModel(
            id = "item_id",
            name = "Item text",
            isChecked = true,
        )
        val input = ItemEntity(
            id = "item_id",
            text = "Item text",
            isComplete = true,
            dueDate = null,
        )
        val actual = ItemMapper().map(input)

        assertEquals(
            expected,
            actual
        )
    }
}