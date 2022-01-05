package jp.developer.retia.frozenword.ui.watch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.Date
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.Log
import jp.developer.retia.frozenword.repository.HabbitRepository
import jp.developer.retia.frozenword.ui.addHabbit.AddHabbitEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WatchViewModel @AssistedInject constructor(
    @Assisted private val habbitId: Int,
    private val habbitRepository: HabbitRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    @AssistedFactory
    interface ViewModelAssistedFactory {
        fun create(habbitId: Int): WatchViewModel
    }

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

    companion object {
        fun provideFactory(
            assistedFactory: ViewModelAssistedFactory,
            habbitId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(habbitId) as T
            }
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