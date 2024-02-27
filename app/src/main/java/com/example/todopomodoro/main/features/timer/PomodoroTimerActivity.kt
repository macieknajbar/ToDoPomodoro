package com.example.todopomodoro.main.features.timer

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.example.todopomodoro.main.features.timer.main.TimerFragment

class PomodoroTimerActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentContainerId = View.generateViewId()

        setContent {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    FragmentContainerView(it).apply { id = fragmentContainerId }
                },
                update = { view ->
                    supportFragmentManager.commit {
                        replace(
                            view.id,
                            TimerFragment.newInstance(
                                intent.getStringExtra(EXTRA_ITEM_ID)!!
                            )
                        )
                    }
                }
            )
        }
    }

    companion object {
        const val EXTRA_ITEM_ID = "todopomodorotimer:PomodoroTimer:EXTRA_ITEM_ID"
    }
}