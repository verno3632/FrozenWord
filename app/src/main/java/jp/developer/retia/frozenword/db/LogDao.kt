package jp.developer.retia.frozenword.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import jp.developer.retia.frozenword.model.Log

@Dao
interface LogDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg log: Log)

    @Insert(onConflict = REPLACE)
    suspend fun insert(log: Log): Long

    @Query("UPDATE Log SET message=:message WHERE id = :id")
    suspend fun updateMessage(id: Int, message: String)

    @Update
    suspend fun updateAll(vararg log: Log)

    @Delete
    fun delete(log: Log)
}
