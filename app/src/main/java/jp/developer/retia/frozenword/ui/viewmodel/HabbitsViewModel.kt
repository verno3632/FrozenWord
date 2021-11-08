package jp.developer.retia.frozenword.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.retia.frozenword.model.HabbitAndLog
import jp.developer.retia.frozenword.repository.HabbitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabbitsViewModel @Inject constructor(
    private val habbitRepository: HabbitRepository,
    ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(HabbitsUiState.Loaded(emptyList()))
    val uiState: StateFlow<HabbitsUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<HabbitsEvent>()
    val events: SharedFlow<HabbitsEvent> = _events.asSharedFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            val habbits = habbitRepository.getHabbitAndLogs()
            _uiState.emit(HabbitsUiState.Loaded(habbits))
        }
    }
}

sealed class HabbitsUiState {
    data class Loaded(val habbits: List<HabbitAndLog>) : HabbitsUiState()
}

sealed class HabbitsEvent {
    object Back : HabbitsEvent()
}