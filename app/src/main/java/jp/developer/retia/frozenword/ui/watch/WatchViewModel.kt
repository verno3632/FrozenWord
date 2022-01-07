package jp.developer.retia.frozenword.ui.watch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.repository.HabbitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WatchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val habbitRepository: HabbitRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val habbitId by lazy { requireNotNull(savedStateHandle.get<Int>(WatchActivity.BUNDLE_KEY_ID)) }

    private val _uiState = MutableStateFlow<WatchUiState>(WatchUiState.NotLoaded)
    val uiState: StateFlow<WatchUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<WatchEvent>()
    val events: SharedFlow<WatchEvent> = _events.asSharedFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            val habbit = habbitRepository.getHabbit(habbitId)
            _uiState.emit(WatchUiState.Loaded(habbit))
        }
    }

    fun onCompleted() {
        viewModelScope.launch(ioDispatcher) {
            val logId = habbitRepository.insertLog(habbitId, Date(), "")
            _uiState.emit(WatchUiState.EditMemo(logId.toInt(), ""))
        }
    }

    fun onMemoSaved(logId: Int, message: String) {
        viewModelScope.launch(ioDispatcher) {
            habbitRepository.updateMessage(logId, message)

            _events.emit(WatchEvent.Back)
        }
    }
}

sealed class WatchUiState {
    object NotLoaded : WatchUiState()
    data class Loaded(val habbit: Habbit) : WatchUiState()
    data class EditMemo(val logId: Int, val message: String) : WatchUiState()
}

sealed class WatchEvent {
    object Back : WatchEvent()
}