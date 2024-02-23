package com.example.todopomodoro.main.vm

import androidx.lifecycle.ViewModel
import com.example.todopomodoro.domain.ItemEntity
import com.example.todopomodoro.main.model.ItemModel
import com.example.todopomodoro.main.vm.mapper.ViewStateMapper
import com.example.todopomodoro.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.Calendar

class MainViewModel(
    private val itemsRepository: Repository<ItemEntity>,
    private val viewStateMapper: ViewStateMapper = ViewStateMapper(),
    private val idGenerator: () -> String,
    private val dateParser: (year: Int, month: Int, day: Int) -> Long = { year, month, day ->
        Calendar.getInstance()
            .apply { set(year, month, day) }
            .timeInMillis
    },
) : ViewModel() {

    val state = MutableStateFlow(State())
    val viewState: Flow<ViewState> = state.map(viewStateMapper::map)

    init {
        state.update { it.copy(items = itemsRepository.getAll()) }
    }

    fun onDoneClicked(value: String) {
        val generatedId = idGenerator()
        itemsRepository.update(generatedId, ItemEntity(generatedId, value, false, null))

        state.update { it.copy(items = itemsRepository.getAll()) }
    }

    fun onCheckChanged(itemId: String, isChecked: Boolean) {
        state.value.items
            .first { it.id == itemId }
            .copy(isComplete = isChecked)
            .let { itemsRepository.update(it.id, it) }

        state.update { it.copy(items = itemsRepository.getAll()) }
    }

    fun onDateClicked(itemId: String) {
        state.update { it.copy(dateSelectionItemId = itemId) }
    }

    fun onDateSelected(year: Int, month: Int, day: Int) {
        val itemId = state.value.dateSelectionItemId
        val item = state.value.items
            .first { it.id == itemId }

        val dueDate = dateParser(year, month, day)

        itemsRepository.update(item.id, item.copy(dueDate = dueDate))
        state.update {
            it.copy(
                items = itemsRepository.getAll(),
                dateSelectionItemId = null,
            )
        }
    }

    fun onDateCancelClicked() {
        val itemId = state.value.dateSelectionItemId
        val item = state.value.items
            .first { it.id == itemId }

        itemsRepository.update(item.id, item.copy(dueDate = null))
        state.update {
            it.copy(
                items = itemsRepository.getAll(),
                dateSelectionItemId = null,
            )
        }
    }

    data class State(
        val items: List<ItemEntity> = emptyList(),
        val dateSelectionItemId: String? = null,
    )

    data class ViewState(
        val items: List<ItemModel> = emptyList(),
        val shouldShowDatePicker: Boolean = false,
    )
}