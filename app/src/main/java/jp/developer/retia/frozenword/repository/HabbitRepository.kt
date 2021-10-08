package jp.developer.retia.frozenword.repository

import jp.developer.retia.frozenword.db.HabbitDao
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.Task
import javax.inject.Inject

class HabbitRepository @Inject constructor(private val habbitDao: HabbitDao) {

    suspend fun insertAll(habbits: List<Habbit>) {
        habbitDao.insertAll(* habbits.toTypedArray())
    }

    suspend fun updateAll(habbits: List<Habbit>) {
        habbitDao.updateAll(* habbits.toTypedArray())
    }

    suspend fun getAllHabbit(): List<Habbit> {
        return habbitDao.getAll()
    }
}