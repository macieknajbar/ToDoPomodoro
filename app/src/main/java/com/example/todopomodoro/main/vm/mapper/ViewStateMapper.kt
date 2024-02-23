package com.example.todopomodoro.main.vm.mapper

import com.example.todopomodoro.main.vm.MainViewModel

class ViewStateMapper(
    private val itemMapper: ItemMapper = ItemMapper(),
) {
    fun map(state: MainViewModel.State): MainViewModel.ViewState {
        return MainViewModel.ViewState(
            items = state.items
                .sortedBy { it.isComplete }
                .map(itemMapper::map),
            shouldShowDatePicker = state.dateSelectionItemId != null,
        )
    }
}