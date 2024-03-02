package com.example.todopomodoro.main.features.timer.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.todopomodoro.R
import com.example.todopomodoro.main.features.timer.main.di.timerViewModel
import com.example.todopomodoro.main.features.timer.main.model.TimerViewState
import com.example.todopomodoro.ui.theme.ToDoPomodoroTheme
import com.example.todopomodoro.utils.viewModel

class TimerFragment : Fragment() {

    private val itemId by lazy { requireArguments().getString(EXTRA_ITEM_ID, "") }
    private val containerId by lazy { requireArguments().getInt(EXTRA_CONTAINER_ID, 0) }

    private val viewModel by viewModel {
        timerViewModel(itemId = itemId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel.routing.observe(viewLifecycleOwner) {
            println("Magic: $it")
            when (it) {
                TimerViewModel.TimerRouting.Idle -> Unit
                TimerViewModel.TimerRouting.Break -> parentFragmentManager.commit {
                    replace(containerId, newInstance(itemId, containerId, true))
                    addToBackStack(null)
                }
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                Screen(
                    viewState = viewModel.viewState.collectAsState(TimerViewState()).value,
                    onStartClicked = viewModel::onStartClicked,
                )
            }
        }
    }

    companion object {
        private const val EXTRA_ITEM_ID = "todopomodoro:TimerFragment:EXTRA_ITEM_ID"
        private const val EXTRA_CONTAINER_ID = "todopomodoro:TimerFragment:EXTRA_CONTAINER_ID"
        private const val EXTRA_IS_BREAK = "todopomodoro:TimerFragment:EXTRA_IS_BREAK"

        fun newInstance(itemId: String, containerId: Int, isBreak: Boolean = false): TimerFragment {
            return TimerFragment().apply {
                arguments = bundleOf(
                    EXTRA_ITEM_ID to itemId,
                    EXTRA_CONTAINER_ID to containerId,
                    EXTRA_IS_BREAK to isBreak,
                )
            }
        }
    }
}

@Composable
private fun Screen(
    viewState: TimerViewState,
    onStartClicked: () -> Unit = {},
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
                    text = viewState.timeText,
                    fontSize = 60.sp
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Sharp.PlayArrow,
                    contentDescription = stringResource(R.string.timer_play),
                    modifier = Modifier
                        .size(120.dp)
                        .clickable { onStartClicked() }
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
        viewState = TimerViewState(
            title = "Item with a very long title and with long long description",
            timeText = "19:59",
        )
    )
}