package jp.developer.retia.frozenword.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Update
import jp.developer.retia.frozenword.model.Log

@Dao
interface LogDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg log: Log)

    @Insert(onConflict = REPLACE)
    suspend fun insert(log: Log)

    @Update
    suspend fun updateAll(vararg log: Log)

    @Delete
    fun delete(log: Log)
}
