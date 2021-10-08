package jp.developer.retia.frozenword.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Habbit(
    val title: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
