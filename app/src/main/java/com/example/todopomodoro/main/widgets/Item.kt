package com.example.todopomodoro.main.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todopomodoro.ui.theme.ToDoPomodoroTheme

@Composable
fun Item(
    item: String,
    onCheckChanged: (Boolean) -> Unit = {},
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        var checked by remember { mutableStateOf(false) }
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                onCheckChanged(it)
            }
        )
        Text(
            text = item,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
        )
        Text(text = "No Date")
        Icon(
            imageVector = Icons.Sharp.PlayArrow,
            contentDescription = "run a timer",
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
        Item("Text")
    }
}