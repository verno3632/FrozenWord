package jp.developer.retia.frozenword.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.HabbitAndTask
import jp.developer.retia.frozenword.model.Task

@Database(entities = [Habbit::class, Task::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habbitDao(): HabbitDao
    abstract fun taskDao(): TaskDao
}