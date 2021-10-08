package jp.developer.retia.frozenword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.Task
import jp.developer.retia.frozenword.repository.HabbitRepository
import jp.developer.retia.frozenword.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val habbitRepository: HabbitRepository,
    private val taskRepository: TaskRepository,

    ) :
    ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState.Success(emptyList()))
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = MainUiState.Success(
                getTasks(Date())
            )
        }
    }

    fun onChangedCheckBox(task: Task, checked: Boolean) {
        viewModelScope.launch {
            taskRepository.update(task.copy(checked = checked))
        }
    }

    suspend fun getTasks(date: Date): List<Task> {
        habbitRepository.insertAll(
            listOf(
                Habbit("kindle開く", 1),
                Habbit("ブログ記事を開く", 2),
                Habbit("zen/hatenaブログの編集を開く", 3),
                Habbit("個人開発プロジェクトを開く", 4),
                Habbit("codelabを開く", 5),
                Habbit("リングフィットを起動", 6),
                Habbit("ジョブサイトを開く", 7),
            )
        )
        val habbits = habbitRepository.getAllHabbit()
        val tasks = taskRepository.getTasksByDate(date)

        val emptyHabbits = habbits.filterNot {
            tasks.map { it.habbit }.contains(it)
        }

        val emptyTasks = emptyHabbits.map { Task(date, false, it) }
        taskRepository.insertAll(emptyTasks)

        return taskRepository.getTasksByDate(date)
    }
}

sealed class MainUiState {
    data class Success(val tasks: List<Task>) : MainUiState()
}