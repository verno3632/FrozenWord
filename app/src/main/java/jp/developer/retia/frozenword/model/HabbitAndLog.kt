package jp.developer.retia.frozenword.model

import androidx.room.Embedded
import androidx.room.Relation

data class HabbitAndLog(
    @Embedded val habbit: Habbit,
    @Relation(parentColumn = "id", entityColumn = "habbitId")
    val logs: List<Log>
)
