package com.example.todopomodoro.main

import androidx.compose.ui.graphics.Color
import com.example.todopomodoro.domain.ItemEntity
import org.junit.Assert.*
import org.junit.Test

class ItemMapperTest {

    @Test
    fun `test map entity no date`() {
        val expected = MainViewModel.ItemModel(
            id = "item_id",
            name = "Item text",
            isChecked = true,
            dateText = "No Date",
            dateColor = Color.Black
        )
        val input = ItemEntity(
            id = "item_id",
            text = "Item text",
            isComplete = true,
            dueDate = null,
        )
        val actual = ItemMapper(
            dateFormatter = { "No Date" },
        ).map(input)

        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun `test map entity with date`() {
        val expected = MainViewModel.ItemModel(
            id = "item_id",
            name = "Item text",
            isChecked = true,
            dateText = "01/01/01",
            dateColor = Color.Black
        )
        val input = ItemEntity(
            id = "item_id",
            text = "Item text",
            isComplete = true,
            dueDate = 0,
        )
        val actual = ItemMapper(
            dateFormatter = { "01/01/01" },
            timestampProvider = { 0 },
        ).map(input)

        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun `test map entity with date past due`() {
        val expected = MainViewModel.ItemModel(
            id = "item_id",
            name = "Item text",
            isChecked = true,
            dateText = "01/01/01",
            dateColor = Color.Red
        )
        val input = ItemEntity(
            id = "item_id",
            text = "Item text",
            isComplete = true,
            dueDate = 0,
        )
        val actual = ItemMapper(
            dateFormatter = { "01/01/01" },
            timestampProvider = { 1 },
        ).map(input)

        assertEquals(
            expected,
            actual
        )
    }
}