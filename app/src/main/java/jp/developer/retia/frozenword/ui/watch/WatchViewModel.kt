package jp.developer.retia.frozenword.ui.watch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.repository.HabbitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    init {
        viewModelScope.launch(ioDispatcher) {
            val habbit = habbitRepository.getHabbit(habbitId)
            _uiState.emit(WatchUiState.Loaded(habbit))
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
}