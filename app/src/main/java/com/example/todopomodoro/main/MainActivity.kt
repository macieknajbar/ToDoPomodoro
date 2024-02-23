package com.example.todopomodoro.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
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
                        onCheckChanged = viewModel::onCheckChanged,
                        onDateClicked = viewModel::onDateClicked,
                        onDateSelected = viewModel::onDateSelected,
                        onDateCancelClicked = viewModel::onDateCancelClicked,
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
    onDateClicked: (String) -> Unit = {},
    onDateSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onDateCancelClicked: () -> Unit = {},
) {
    Column {
        for (item in items) {
            Item(
                text = item.name,
                onCheckChanged = { onCheckChanged(item.id, it) },
                isChecked = item.isChecked,
                onDateClicked = { onDateClicked(item.id) },
                dateText = item.dateText, //stringResource(R.string.item_date_empty),
                dateColor = item.dateColor,
            )
        }

        NewItemField(onDoneClicked)
    }

    if (items.any { it.shouldShowDatePicker }) {
        AndroidView(
            factory = {
                DatePickerDialog(it).apply {
                    setOnCancelListener { onDateCancelClicked() }
                    setOnDateSetListener { _, year, month, dayOfMonth ->
                        onDateSelected(year, month, dayOfMonth)
                    }
                }.show()

                View(it).apply { isVisible = false }
            },
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ScreenPreview() {
    val item = MainViewModel.ItemModel(
        id = "1",
        name = "Item 1",
        dateText = "No Date",
        dateColor = Color.Black,
    )
    ToDoPomodoroTheme {
        Screen(
            items = listOf(
                item.copy(
                    id = "1",
                    name = "Item 1",
                    dateText = "No Date",
                    dateColor = Color.Black,
                ),
                item.copy(
                    id = "2",
                    name = "Item 2",
                    dateText = "01/01/23",
                    dateColor = Color.Red,
                ),
                item.copy(
                    id = "3",
                    name = "Item 3",
                    dateText = "01/01/30",
                    dateColor = Color.Black,
                ),
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