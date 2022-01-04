package jp.developer.retia.frozenword.repository

import javax.inject.Inject
import jp.developer.retia.frozenword.db.HabbitDao
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.HabbitAndLog

class HabbitRepository @Inject constructor(private val habbitDao: HabbitDao) {
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
}
