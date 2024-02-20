package com.example.todopomodoro.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todopomodoro.ui.theme.ToDoPomodoroTheme

class MainActivity : ComponentActivity(), MainContract.View {

    private val items = mutableStateOf(emptyList<String>())
    private val presenter = MainPresenter(view = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoPomodoroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen(
                        items = presenter.items.value,
                        onDoneClicked = { presenter.onDoneClicked(it) },
                    )
                }
            }
        }
    }

    override fun updateItems(items: List<String>) {
        this.items.value = items
    }

}

@Composable
private fun Screen(
    items: List<String> = emptyList(),
    onDoneClicked: (String) -> Unit = {},
) {
    Column {
        for (item in items) {
            Item(item)
        }

        NewItemField(onDoneClicked)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NewItemField(onDoneClicked: (String) -> Unit) {
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
private fun Item(item: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        var checked by remember { mutableStateOf(false) }
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
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
fun ScreenPreview() {
    ToDoPomodoroTheme {
        Screen(listOf("Task 1", "Task 2"))
    }
}

//@Composable
//fun Item(itemText: String) {
//    var checkboxState by remember { mutableStateOf(false) }
//
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//        Checkbox(
//            checked = checkboxState,
//            onCheckedChange = { checkboxState = it },
//        )
//        Text(
//            text = itemText,
//            modifier = Modifier
//                .weight(1f)
//                .clickable { }
//                .padding(end = 8.dp),
//            softWrap = false,
//            overflow = TextOverflow.Ellipsis,
//        )
//        Text(
//            text = "No Date",
//            fontSize = 12.sp,
//            color = Color.Black,
//            modifier = Modifier.clickable { }
//        )
//        Icon(
//            imageVector = Icons.Sharp.PlayArrow,
//            contentDescription = null,
//            modifier = Modifier
//                .size(48.dp)
//                .padding(8.dp)
//                .clickable { },
//        )
//    }
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun NewItemField(value: String) {
//    var newItemTextState by rememberSaveable { mutableStateOf(value) }
//
//    TextField(
//        value = newItemTextState,
//        onValueChange = { newItemTextState = it },
//        placeholder = { Text(text = "New item…") },
//        modifier = Modifier.fillMaxWidth(),
//        keyboardActions = KeyboardActions(onDone = {
//            onDoneClicked(newItemTextState)
//            newItemTextState = ""
//        }),
//        keyboardOptions = KeyboardOptions(
//            imeAction = ImeAction.Done,
//            capitalization = KeyboardCapitalization.Sentences
//        ),
//    )
//}
//
//fun onDoneClicked(value: String) {
//    items.add(value)
//    itemsState += value
//}
//
//private operator fun <T> MutableState<List<T>>.plusAssign(value: T) {
//    this.value = this.value + value
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ItemPreview() {
//    ToDoPomodoroTheme {
//        Item("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ItemPreview2() {
//    ToDoPomodoroTheme {
//        NewItemField("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
//    }
//}