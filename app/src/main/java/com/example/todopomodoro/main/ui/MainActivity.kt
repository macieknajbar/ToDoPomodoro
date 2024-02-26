package com.example.todopomodoro.main.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import com.example.todopomodoro.R
import com.example.todopomodoro.main.di.mainViewModel
import com.example.todopomodoro.main.model.ItemModel
import com.example.todopomodoro.main.vm.MainViewModel
import com.example.todopomodoro.main.widgets.Item
import com.example.todopomodoro.main.widgets.NewItemField
import com.example.todopomodoro.ui.theme.ToDoPomodoroTheme
import com.example.todopomodoro.utils.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel { mainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.routing.observe(this) {
            when (it) {
                MainViewModel.Routing.Idle -> Unit
                is MainViewModel.Routing.PomodoroTimer -> Unit
            }
        }

        setContent {
            ToDoPomodoroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen(
                        viewState = viewModel.viewState.collectAsState(initial = MainViewModel.ViewState()).value,
                        onDoneClicked = viewModel::onDoneClicked,
                        onCheckChanged = viewModel::onCheckChanged,
                        onDateClicked = viewModel::onDateClicked,
                        onDateSelected = viewModel::onDateSelected,
                        onDateCancelClicked = viewModel::onDateCancelClicked,
                        onTextClicked = viewModel::onTextClicked,
                        onItemDoneClicked = viewModel::onItemDoneClicked,
                    )
                }
            }
        }
    }
}

@Composable
private fun Screen(
    viewState: MainViewModel.ViewState = MainViewModel.ViewState(),
    onDoneClicked: (String) -> Unit = {},
    onCheckChanged: (String, Boolean) -> Unit = { _, _ -> },
    onDateClicked: (String) -> Unit = {},
    onDateSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onDateCancelClicked: () -> Unit = {},
    onTextClicked: (String) -> Unit = {},
    onItemDoneClicked: (String, String) -> Unit = { _, _ -> },
) {
    Column {
        for (item in viewState.items) {
            Item(
                text = item.text,
                onCheckChanged = { onCheckChanged(item.id, it) },
                isChecked = item.isChecked,
                onDateClicked = { onDateClicked(item.id) },
                dateText = item.dateText,
                dateColor = item.dateColor,
                isBeingEdited = item.isBeingEdited,
                onTextClicked = { onTextClicked(item.id) },
                onDoneClicked = { onItemDoneClicked(item.id, it) },
            )
        }

        NewItemField(onDoneClicked)
    }

    if (viewState.shouldShowDatePicker) {
        val negativeButtonText = stringResource(id = R.string.datePicker_remove)
        AndroidView(
            factory = {
                DatePickerDialog(it).apply {
                    setButton(DatePickerDialog.BUTTON_NEGATIVE, negativeButtonText) { _, _ ->
                        onDateCancelClicked()
                    }
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
    val item = ItemModel(
        id = "1",
        text = "Item 1",
        dateText = "No Date",
        dateColor = Color.Black,
    )
    ToDoPomodoroTheme {
        Screen(
            viewState = MainViewModel.ViewState(
                listOf(
                    item.copy(
                        id = "1",
                        text = "Item 1",
                        dateText = "No Date",
                        dateColor = Color.Black,
                    ),
                    item.copy(
                        id = "2",
                        text = "Item 2",
                        dateText = "01/01/23",
                        dateColor = Color.Red,
                    ),
                    item.copy(
                        id = "3",
                        text = "Item 3",
                        dateText = "01/01/30",
                        dateColor = Color.Black,
                    ),
                )
            )
        )
    }
}