package jp.developer.retia.frozenword.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import jp.developer.retia.frozenword.repository.HabbitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class AddHabbitViewModel @Inject constructor(
    private val habbitRepository: HabbitRepository,
) : ViewModel() {

    private var habbitTitle: String = ""
    private var shortHabbitTitle: String = ""
    private var trigger: String? = null

    private val _uiState = MutableStateFlow(AddHabbitUiState.HabbitTitlePage("", emptyList()))
    val uiState: StateFlow<AddHabbitUiState> = _uiState

    fun onHabbitTitleUpdated(habbitTitle: String) {
        this.habbitTitle = habbitTitle
    }

    fun onShortHabbitTitleUpdated(shortHabbitTitle: String) {
        this.shortHabbitTitle = shortHabbitTitle
    }

    fun onTriggerUpdated(trigger: String) {
        this.trigger = trigger
    }

    fun onHabbitTitleNextButtonClicked() {
    }

    fun onShortHabbitTitleNextButtonClicked() {
    }

    fun onSkipButtonClicked() {
    }

    fun onClickedOkButton() {
    }
}

sealed class AddHabbitUiState {
    data class HabbitTitlePage(val title: String, val sampleTitle: List<String>) :
        AddHabbitUiState()

    data class ShortHabbitTitlePage(val title: String, val sampleTItles: List<String>) :
        AddHabbitUiState()

    data class TitlePage(val title: String, val sampleTItles: List<String>) : AddHabbitUiState()
}
