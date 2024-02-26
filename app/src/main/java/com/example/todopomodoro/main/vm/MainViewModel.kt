package com.example.todopomodoro.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val routing = MutableLiveData<Routing>(Routing.Idle)

    private fun MutableStateFlow<Routing>.navigateTo(block: (Routing) -> Routing) {
        update(block)
        update { Routing.Idle }
    }

    sealed class Routing {
        object Idle : Routing()
        data class PomodoroTimer(val itemId: String) : Routing()
    }

    private val itemsRepositoryObserver: (List<ItemEntity>) -> Unit =
        { items -> state.update { it.copy(items = items) } }

    init {
        itemsRepository.addObserver(itemsRepositoryObserver)
    }

    override fun onCleared() {
        itemsRepository.removeObserver(itemsRepositoryObserver)
    }

    fun onDoneClicked(value: String) {
        val record = ItemEntity(id = idGenerator(), text = value)
        itemsRepository.update(record.id, record)
    }

    fun onCheckChanged(itemId: String, isChecked: Boolean) {
        state.value.items
            .first { it.id == itemId }
            .copy(isComplete = isChecked)
            .let { itemsRepository.update(it.id, it) }
    }

    fun onDateClicked(itemId: String) {
        state.update { it.copy(dateSelectionItemId = itemId) }
    }

    fun onDateSelected(year: Int, month: Int, day: Int) {
        val dueDate = dateParser(year, month, day)
        val item = state.value
            .run { items.first { it.id == dateSelectionItemId } }

        itemsRepository.update(item.id, item.copy(dueDate = dueDate))
        state.update { it.copy(dateSelectionItemId = null) }
    }

    fun onDateCancelClicked() {
        val itemId = state.value.dateSelectionItemId
        val item = state.value.items
            .first { it.id == itemId }

        itemsRepository.update(item.id, item.copy(dueDate = null))
        state.update { it.copy(dateSelectionItemId = null) }
    }

    fun onTextClicked(itemId: String) {
        state.update { it.copy(editItemId = itemId) }
    }

    fun onItemDoneClicked(itemId: String, text: String) {
        val updatedItem = state.value.items
            .first { it.id == itemId }
            .copy(text = text)
        itemsRepository.update(updatedItem.id, updatedItem)
        state.update { it.copy(editItemId = null) }
    }

    data class State(
        val items: List<ItemEntity> = emptyList(),
        val dateSelectionItemId: String? = null,
        val editItemId: String? = null,
    )

    data class ViewState(
        val items: List<ItemModel> = emptyList(),
        val shouldShowDatePicker: Boolean = false,
    )
}