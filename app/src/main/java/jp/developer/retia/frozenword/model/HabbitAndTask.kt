package jp.developer.retia.frozenword.model

import androidx.room.Embedded
import androidx.room.Relation

data class HabbitAndTask(
    @Embedded val habbit: Habbit,
    @Relation(parentColumn = "id", entityColumn = "uid")
    val task: Task
)