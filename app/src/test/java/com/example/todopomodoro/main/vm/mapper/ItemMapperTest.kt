package com.example.todopomodoro.main.vm.mapper

import androidx.compose.ui.graphics.Color
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.main.model.ItemModel
import org.junit.Assert
import org.junit.Test

class ItemMapperTest {

    @Test
    fun `test map entity no date`() {
        val expected = ItemModel(
            id = "item_id",
            text = "Item text",
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
            stringProvider = { "No Date" },
        ).map(input)

        Assert.assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun `test map entity with date`() {
        val expected = ItemModel(
            id = "item_id",
            text = "Item text",
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

        Assert.assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun `test map entity with date past due`() {
        val expected = ItemModel(
            id = "item_id",
            text = "Item text",
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

        Assert.assertEquals(
            expected,
            actual
        )
    }
}