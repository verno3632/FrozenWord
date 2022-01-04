package jp.developer.retia.frozenword.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.Log

@Database(entities = [Habbit::class, Log::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habbitDao(): HabbitDao
    abstract fun logDao(): LogDao
}
