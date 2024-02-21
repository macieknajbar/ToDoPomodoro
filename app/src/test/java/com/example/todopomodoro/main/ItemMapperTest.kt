package com.example.todopomodoro.main

import org.junit.Assert.*
import org.junit.Test

class ItemMapperTest {

    @Test
    fun testMap() {
        val input = "One"
        val expected = MainViewModel.ItemModel(
            id = "One",
            name = "One",
            isChecked = false
        )

        assertEquals(
            expected,
            ItemMapper().map(input)
        )
    }
}