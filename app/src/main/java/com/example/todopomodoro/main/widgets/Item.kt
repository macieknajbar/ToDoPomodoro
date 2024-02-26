package com.example.todopomodoro.main.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todopomodoro.R
import com.example.todopomodoro.ui.theme.ToDoPomodoroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Item(
    text: String,
    onCheckChanged: (Boolean) -> Unit = {},
    isChecked: Boolean = false,
    onDateClicked: () -> Unit = {},
    dateText: String,
    dateColor: Color,
    isBeingEdited: Boolean = false,
    onTextClicked: () -> Unit = {},
    onDoneClicked: (String) -> Unit = {},
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                onCheckChanged(it)
            }
        )
        if (isBeingEdited) {
            var value by remember { mutableStateOf(text) }
            TextField(
                value = value,
                onValueChange = { value = it },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                keyboardActions = KeyboardActions(onDone = {
                    onDoneClicked(value)
                }),
            )
        } else {
            Text(
                text = text,
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onTextClicked)
                    .padding(end = 8.dp),
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Text(
            text = dateText,
            color = dateColor,
            fontSize = 12.sp,
            modifier = Modifier.clickable(onClick = onDateClicked),
        )
        Icon(
            imageVector = Icons.Sharp.PlayArrow,
            contentDescription = stringResource(R.string.item_icon_description),
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ItemPreview() {
    ToDoPomodoroTheme {
        Column {
            Item("Text", dateText = "01/01/99", dateColor = Color.Black,)
            Item(
                "Text looooooooooooooooooooooooooooooooooooooong",
                dateText = "01/01/99",
                dateColor = Color.Black,
            )
            Item("Checked", isChecked = true, dateText = "01/01/99", dateColor = Color.Black,)
            Item("With Date", dateText = "01/01/99", dateColor = Color.Black,)
            Item("Past due", dateText = "31/12/23", dateColor = Color.Red,)
            Item("Editting", dateText = "01/01/99", dateColor = Color.Black, isBeingEdited = true,)
        }
    }
}