package jp.developer.retia.frozenword.db

//import androidx.room.*
//import androidx.room.OnConflictStrategy.REPLACE
//import jp.developer.retia.frozenword.model.HabbitAndTask
//import jp.developer.retia.frozenword.model.Task
//import java.util.*
//
//@Dao
//interface TaskDao {
//    @Query("SELECT * FROM task")
//    suspend fun getAll(): List<Task>
//
//    @Query("SELECT * FROM task WHERE date = :date")
//    suspend fun getTasksByDate(date: Date): List<Task>
//
//    @Transaction
//    @Query("SELECT * FROM task")
//    suspend fun getHabbitAndTask(): List<HabbitAndTask>
//
//    @Insert(onConflict = REPLACE)
//    suspend fun insertAll(vararg task: Task)
//
//
//    @Update
//    suspend fun updateAll(vararg task: Task)
//
//    @Delete
//    suspend fun delete(task: Task)
//}
//
