package jp.developer.retia.frozenword.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import java.util.*

//@Dao
//interface HabbitAndTaskDao {
//    @Transaction
//    @Query("SELECT * FROM User")
//    fun getUsersWithPlaylists(): List<UserWithPlaylists>
//    @Query("SELECT * FROM habbitAndTask")
//    suspend fun getAll(): List<HabbitAndTask>
//
//    @Insert(onConflict = REPLACE)
//    suspend fun insertAll(vararg habbitAndTask: HabbitAndTask)
//
//    @Update
//    suspend fun updateAll(vararg task: HabbitAndTask)
//
//    @Delete
//    fun delete(task: HabbitAndTask)
//}
//
