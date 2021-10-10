package jp.developer.retia.frozenword.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import jp.developer.retia.frozenword.model.HabbitAndLog

@Dao
interface HabbitAndLogDao {
    @Transaction
    @Query("SELECT * FROM Habbit")
    fun getHabbitAndLogs(): List<HabbitAndLog>
}