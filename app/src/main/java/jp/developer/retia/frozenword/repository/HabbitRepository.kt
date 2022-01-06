package jp.developer.retia.frozenword.repository

import java.util.Date
import javax.inject.Inject
import jp.developer.retia.frozenword.db.HabbitDao
import jp.developer.retia.frozenword.db.LogDao
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.HabbitAndLog
import jp.developer.retia.frozenword.model.Log

class HabbitRepository @Inject constructor(
    private val habbitDao: HabbitDao,
    private val logDao: LogDao,
) {
    suspend fun insert(
        title: String,
        simpleHabbitTitle: String,
        trigger: String,
        place: String
    ) {
        habbitDao.insertAll(
            Habbit(
                title = title,
                simpleHabbitTitle = simpleHabbitTitle,
                trigger = trigger,
                place = place
            )
        )
    }

    suspend fun insertAll(habbits: List<Habbit>) {
        habbitDao.insertAll(* habbits.toTypedArray())
    }

    suspend fun updateAll(habbits: List<Habbit>) {
        habbitDao.updateAll(* habbits.toTypedArray())
    }

    suspend fun getAllHabbit(): List<Habbit> {
        return habbitDao.getAll()
    }

    suspend fun getHabbitAndLogs(): List<HabbitAndLog> {
        return habbitDao.getHabbitAndLogs()
    }

    suspend fun getHabbit(habbitId: Int): Habbit {
        return habbitDao.getHabbit(habbitId)
    }

    suspend fun insertLog(habbitId: Int, time: Date, message: String): Long {
        return logDao.insert(Log(habbitId = habbitId, time = time, message = message))
    }

    suspend fun updateMessage(logId: Int, message: String) {
        logDao.updateMessage(logId, message)
    }
}
