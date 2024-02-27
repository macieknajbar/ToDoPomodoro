package com.example.todopomodoro.main.features

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit

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
                    if (savedInstanceState == null) {
                        supportFragmentManager.commit {
                            replace(view.id, PomodoroTimerFragment(intent.extras!!.getString(EXTRA_ITEM_ID)!!))
                        }
                    }
                }
            )
        }
    }

    companion object {
        const val EXTRA_ITEM_ID = "todopomodorotimer:PomodoroTimer:EXTRA_ITEM_ID"
    }
}