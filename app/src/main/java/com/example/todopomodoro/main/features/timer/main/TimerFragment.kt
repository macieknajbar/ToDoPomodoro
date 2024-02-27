package com.example.todopomodoro.main.features.timer.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.todopomodoro.main.features.timer.main.di.timerViewModel
import com.example.todopomodoro.main.features.timer.main.model.TimerViewState
import com.example.todopomodoro.ui.theme.ToDoPomodoroTheme
import com.example.todopomodoro.utils.viewModel

class TimerFragment : Fragment() {
    private val viewModel by viewModel {
        timerViewModel(itemId = requireArguments().getString(EXTRA_ITEM_ID, ""))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Screen(
                    viewState = viewModel.viewState.value
                )
            }
        }
    }

    companion object {
        private const val EXTRA_ITEM_ID = "todopomodoro:TimerFragment:EXTRA_ITEM_ID"

        fun newInstance(itemId: String): TimerFragment {
            return TimerFragment().apply {
                arguments = bundleOf(
                    EXTRA_ITEM_ID to itemId
                )
            }
        }
    }
}

@Composable
private fun Screen(
    viewState: TimerViewState,
) {
    ToDoPomodoroTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = viewState.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp, horizontal = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "20:00",
                    fontSize = 60.sp
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Sharp.PlayArrow,
                    contentDescription = "",
                    modifier = Modifier
                        .size(120.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ScreenPreview() {
    Screen(
        viewState = TimerViewState(title = "Item with a very long title and with long long description")
    )
}