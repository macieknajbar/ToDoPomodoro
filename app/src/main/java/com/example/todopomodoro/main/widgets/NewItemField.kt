package com.example.todopomodoro.main.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NewItemField(onDoneClicked: (String) -> Unit) {
    var value by remember { mutableStateOf("Finish Android course") }
    TextField(
        value = value,
        onValueChange = { value = it },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onDoneClicked(value)
            value = ""
        }),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
@Preview
private fun NewItemFieldPreview() {
    NewItemField(onDoneClicked = {})
}