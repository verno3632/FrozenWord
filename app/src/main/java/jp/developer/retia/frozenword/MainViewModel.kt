package jp.developer.retia.frozenword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.retia.frozenword.repository.HabbitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val habbitRepository: HabbitRepository,
//    private val taskRepository: TaskRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState.Success(emptyList()))
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        viewModelScope.launch {
//            _uiState.value = MainUiState.Success(
//                getTasks(Date())
//            )
        }
    }

//    fun onChangedCheckBox(task: Task, checked: Boolean) {
//        viewModelScope.launch {
//            taskRepository.update(task.copy(checked = checked))
//        }
//    }

//    suspend fun getTasks(date: Date): List<Task> {
//        habbitRepository.insertAll(
//            listOf(
//                OldHabbit("kindle開く", 1),
//                OldHabbit("ブログ記事を開く", 2),
//                OldHabbit("zen/hatenaブログの編集を開く", 3),
//                OldHabbit("個人開発プロジェクトを開く", 4),
//                OldHabbit("codelabを開く", 5),
//                OldHabbit("リングフィットを起動", 6),
//                OldHabbit("ジョブサイトを開く", 7),
//            )
//        )
//        val habbits = habbitRepository.getAllHabbit()
//        val tasks = taskRepository.getTasksByDate(date)
//
//        val emptyHabbits = habbits.filterNot {
//            tasks.map { it.oldHabbit }.contains(it)
//        }
//
//        val emptyTasks = emptyHabbits.map { Task(date, false, it) }
//        taskRepository.insertAll(emptyTasks)
//
//        return taskRepository.getTasksByDate(date)
//    }
}

sealed class MainUiState {
    data class Success(val tasks: List<String>) : MainUiState()
}