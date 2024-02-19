package com.example.todopomodoro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todopomodoro.ui.theme.ToDoPomodoroTheme

val items = mutableListOf<String>()
val itemsState = mutableStateOf(listOf<String>())

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoPomodoroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen()
                }
            }
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Screen() {
    Column {
        val items = listOf(
            "Finish Android course",
            "Second item"
        )
        var value by remember { mutableStateOf("") }


        for (i in items) {
            Row {
                Checkbox(checked = false, onCheckedChange = null)
                Text(text = i)
                Text(text = "19/02/24")
                Icon(imageVector = Icons.Sharp.PlayArrow, contentDescription = "run a timer")
            }
        }
        TextField(value = value, onValueChange = { value = it })
    }
}

@Composable
@Preview(showBackground = true)
fun ScreenPreview() {
    ToDoPomodoroTheme {
        Screen()
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

fun onDoneClicked(value: String) {
    items.add(value)
    itemsState += value
}

private operator fun <T> MutableState<List<T>>.plusAssign(value: T) {
    this.value = this.value + value
}

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