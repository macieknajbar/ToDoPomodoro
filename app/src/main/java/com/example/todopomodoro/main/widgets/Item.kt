package com.example.todopomodoro.main.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todopomodoro.R
import com.example.todopomodoro.ui.theme.ToDoPomodoroTheme

@Composable
fun Item(
    text: String,
    onCheckChanged: (Boolean) -> Unit = {},
    isChecked: Boolean = false,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                onCheckChanged(it)
            }
        )
        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
        )
        Text(text = stringResource(R.string.item_date_empty))
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
            Item("Text", isChecked = true)
            Item("Text", isChecked = false)
        }
    }
}