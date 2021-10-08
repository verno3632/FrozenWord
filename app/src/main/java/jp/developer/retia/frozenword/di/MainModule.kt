package jp.developer.retia.frozenword.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.developer.retia.frozenword.db.AppDatabase
import jp.developer.retia.frozenword.db.HabbitDao
import jp.developer.retia.frozenword.db.TaskDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "frozen-world"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHabbitDao(appDatabase: AppDatabase): HabbitDao {
        return appDatabase.habbitDao()
    }

    @Provides
    @Singleton
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.taskDao()
    }
}