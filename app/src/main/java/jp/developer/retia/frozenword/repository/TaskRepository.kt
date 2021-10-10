package jp.developer.retia.frozenword.repository

//import jp.developer.retia.frozenword.db.TaskDao
//import jp.developer.retia.frozenword.model.HabbitAndTask
//import jp.developer.retia.frozenword.model.Task
//import java.util.*
//import javax.inject.Inject
//
//class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
//
//    suspend fun insertAll(tasks: List<Task>) {
//        taskDao.insertAll(* tasks.toTypedArray())
//    }
//
//    suspend fun insertAllTasks(tasks: List<Task>) {
//        taskDao.insertAll(* tasks.toTypedArray())
//    }
//
//    suspend fun getAllHabitAndTask(): List<HabbitAndTask> {
//        return taskDao.getHabbitAndTask()
//    }
//
//    suspend fun update(task: Task) {
//        taskDao.updateAll(task)
//    }
//
//    suspend fun getAllTasks(): List<Task> {
//        return taskDao.getAll()
//    }
//
//    suspend fun getTasksByDate(date: Date): List<Task> {
//        return taskDao.getTasksByDate(date)
//    }
//}