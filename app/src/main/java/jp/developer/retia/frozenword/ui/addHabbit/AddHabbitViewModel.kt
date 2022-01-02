package jp.developer.retia.frozenword.ui.addHabbit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import jp.developer.retia.frozenword.repository.HabbitRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class AddHabbitViewModel @Inject constructor(
    private val habbitRepository: HabbitRepository,
) : ViewModel() {

    private var habbitTitle: String = ""
    private var simpleHabbitTitle: String = ""
    private var trigger: String? = null

    private val titleSuggestion = emptyList<String>()

    private val _uiState =
        MutableStateFlow<AddHabbitUiState>(
            AddHabbitUiState.HabbitTitlePage(
                "",
                titleSuggestion,
                false
            )
        )
    val uiState: StateFlow<AddHabbitUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AddHabbitEvent>()
    val events: SharedFlow<AddHabbitEvent> = _events.asSharedFlow()

    fun onHabbitTitleUpdated(habbitTitle: String) {
        this.habbitTitle = habbitTitle
        viewModelScope.launch {
            _uiState.emit(
                AddHabbitUiState.HabbitTitlePage(
                    habbitTitle,
                    titleSuggestion,
                    habbitTitle.isNotEmpty()
                )
            )
        }
    }

    fun onSimpleHabbitTitleUpdated(simpleHabbitTitle: String) {
        this.simpleHabbitTitle = simpleHabbitTitle
        viewModelScope.launch {
            _uiState.emit(
                AddHabbitUiState.SimpleHabbitTitlePage(
                    habbitTitle,
                    emptyList()
                )
            )
        }
    }

    fun onTriggerUpdated(trigger: String) {
        this.trigger = trigger
    }

    fun onHabbitTitleNextButtonClicked() {
        viewModelScope.launch {
            _uiState.emit(AddHabbitUiState.SimpleHabbitTitlePage(habbitTitle, emptyList()))
        }
    }

    fun onSimpleHabbitTitleNextButtonClicked() {
    }

    fun onSkipButtonClicked() {
    }

    fun onClickedOkButton() {
        viewModelScope.launch {
            habbitRepository.insert(title = habbitTitle, trigger = simpleHabbitTitle)
            _events.emit(AddHabbitEvent.Back)
        }
    }

    fun onSuggestionHabbitTitleClicked(title: String) {
    }
}

sealed class AddHabbitUiState {
    data class HabbitTitlePage(
        val title: String,
        val sampleTitle: List<String>,
        val enableButton: Boolean
    ) : AddHabbitUiState()

    data class SimpleHabbitTitlePage(val title: String, val sampleTitles: List<String>) :
        AddHabbitUiState()

    data class TitlePage(val title: String, val sampleTItles: List<String>) : AddHabbitUiState()
}

sealed class AddHabbitEvent {
    object Back : AddHabbitEvent()
}
