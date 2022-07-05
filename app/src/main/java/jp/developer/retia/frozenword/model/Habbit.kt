package jp.developer.retia.frozenword.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Habbit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val simpleHabbitTitle: String,
    val trigger: String,
    val place: String,
)
