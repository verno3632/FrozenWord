package jp.developer.retia.frozenword.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.HabbitAndLog

@Dao
interface HabbitDao {
    @Query("SELECT * FROM habbit")
    suspend fun getAll(): List<Habbit>

    @Query("SELECT * FROM habbit")
    fun getAllSync(): List<Habbit>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg habbit: Habbit)

    @Update
    suspend fun updateAll(vararg habbit: Habbit)

    @Delete
    fun delete(habbit: Habbit)

    @Transaction
    @Query("SELECT * FROM Habbit")
    fun getHabbitAndLogs(): List<HabbitAndLog>
}
