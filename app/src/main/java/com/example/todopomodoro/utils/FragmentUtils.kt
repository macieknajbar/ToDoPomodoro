package com.example.todopomodoro.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <VM> Fragment.viewModel(block: () -> VM): Lazy<VM> {
    return viewModels(ownerProducer = { requireActivity() }) {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return block() as T
            }
        }
    }
}
