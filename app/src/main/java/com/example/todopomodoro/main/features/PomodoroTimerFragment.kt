package com.example.todopomodoro.main.features

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.todopomodoro.repository.di.itemsRepository
import com.example.todopomodoro.ui.theme.ToDoPomodoroTheme

class PomodoroTimerFragment : Fragment() {
    private val itemsRepository = itemsRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val itemId = requireArguments().getString(EXTRA_ITEM_ID)
        val item = itemsRepository.getAll()
            .first { it.id == itemId }
        val viewState = mutableStateOf(ViewState(title = item.text))
        return ComposeView(requireContext()).apply {
            setContent {
                Screen(
                    viewState = viewState.value
                )
            }
        }
    }

    companion object {
        private const val EXTRA_ITEM_ID = "todopomodoro:PomodoroTimerFragment:EXTRA_ITEM_ID"

        fun newInstance(itemId: String): PomodoroTimerFragment {
            return PomodoroTimerFragment().apply {
                arguments = bundleOf(
                    EXTRA_ITEM_ID to itemId
                )
            }
        }
    }
}

data class ViewState(
    val title: String = "",
)

@Composable
private fun Screen(
    viewState: ViewState,
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
        viewState = ViewState(title = "Item with a very long title and with long long description")
    )
}