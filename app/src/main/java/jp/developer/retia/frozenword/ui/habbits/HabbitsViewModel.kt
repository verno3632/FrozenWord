package jp.developer.retia.frozenword.ui.habbits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.HabbitAndLog
import jp.developer.retia.frozenword.repository.HabbitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class HabbitsViewModel @Inject constructor(
    private val habbitRepository: HabbitRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow(HabbitsUiState.Loaded(emptyList()))
    val uiState: StateFlow<HabbitsUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<HabbitsEvent>()
    val events: SharedFlow<HabbitsEvent> = _events.asSharedFlow()

    init {
        loadAndEmitState()
    }

    private fun loadAndEmitState() {
        viewModelScope.launch(ioDispatcher) {
            val habbits = habbitRepository.getHabbitAndLogs()
            _uiState.emit(HabbitsUiState.Loaded(habbits))
        }
    }

    fun onResume() {
        loadAndEmitState()
    }

    fun onClickActionButton() {
        viewModelScope.launch(ioDispatcher) {
            _events.emit(HabbitsEvent.NavigateToAdd)
        }
    }

    fun onClickHabbitCard(habbit: Habbit) {
        viewModelScope.launch(ioDispatcher) {
            _events.emit(HabbitsEvent.NavigateToStart(habbit))
        }
    }
}

sealed class HabbitsUiState {
    data class Loaded(val habbits: List<HabbitAndLog>) : HabbitsUiState()
}

sealed class HabbitsEvent {
    object Back : HabbitsEvent()
    object NavigateToAdd : HabbitsEvent()
    data class NavigateToStart(val habbit: Habbit) : HabbitsEvent()
}
