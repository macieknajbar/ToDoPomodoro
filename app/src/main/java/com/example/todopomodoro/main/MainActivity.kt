package com.example.todopomodoro.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todopomodoro.main.di.mainViewModel
import com.example.todopomodoro.main.widgets.Item
import com.example.todopomodoro.main.widgets.NewItemField
import com.example.todopomodoro.ui.theme.ToDoPomodoroTheme
import com.example.todopomodoro.utils.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel { mainViewModel() }

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
                        items = viewModel.items.value,
                        onDoneClicked = viewModel::onDoneClicked,
                        onCheckChanged = { itemId, isChecked ->
                            viewModel.onCheckChanged(itemId, isChecked)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun Screen(
    items: List<MainViewModel.ItemModel> = emptyList(),
    onDoneClicked: (String) -> Unit = {},
    onCheckChanged: (String, Boolean) -> Unit = { _, _ -> },
) {
    Column {
        for (item in items) {
            Item(
                item = item.name,
                onCheckChanged = { onCheckChanged(item.id, it) },
            )
        }

        NewItemField(onDoneClicked)
    }
}

@Composable
@Preview(showBackground = true)
private fun ScreenPreview() {
    ToDoPomodoroTheme {
        Screen(
            items = listOf(
                MainViewModel.ItemModel(id = "1", name = "Task 1", isChecked = false),
                MainViewModel.ItemModel(id = "2", name = "Task 2", isChecked = false),
            )
        )
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