package com.example.todopomodoro.main.features.timer.pomodorobreak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.todopomodoro.R
import com.example.todopomodoro.ui.theme.ToDoPomodoroTheme
import com.example.todopomodoro.utils.viewModel

class PomodoroBreakFragment : Fragment() {

    private val vm by viewModel { breakViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        vm.onNavigation {
            when (it) {
                PomodoroBreakViewModel.BreakRouting.Close -> requireActivity().finish()
                PomodoroBreakViewModel.BreakRouting.Back -> parentFragmentManager.popBackStack()
            }
        }
        return ComposeView(requireContext()).apply {
            setContent {
                Screen(
                    value = vm.viewState
                        .collectAsState(PomodoroBreakViewModel.BreakViewState())
                        .value,
                    onCloseClicked = vm::onCloseClicked
                )
            }
        }
    }
}

@Composable
private fun Screen(
    value: PomodoroBreakViewModel.BreakViewState,
    onCloseClicked: () -> Unit = {},
) {
    ToDoPomodoroTheme {
        Column(Modifier.fillMaxSize()) {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "BREAK!",
                    fontSize = 40.sp,
                    modifier = Modifier.padding(40.dp),
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            var visible by remember { mutableStateOf(false) }
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(durationMillis = 2_000)),
            ) {
                Column {
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Breath 4-7-8",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(top = 40.dp),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Breath in for 4 sec, hold for 7 and breath out for 8.",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp),
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            visible = true
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = value.timeText,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(60.dp),
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(R.string.break_close),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onCloseClicked() }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ScreenPreview() {
    Screen(
        PomodoroBreakViewModel.BreakViewState(
            timeText = "4:59"
        )
    )
}