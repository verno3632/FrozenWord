package jp.developer.retia.frozenword.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Log(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val habbitId: Int,
    val time: Date,
    val message: String
)
