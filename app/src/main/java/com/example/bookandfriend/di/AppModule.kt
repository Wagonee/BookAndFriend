package com.example.bookandfriend.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.bookandfriend.data.database.AppDatabase
import com.example.bookandfriend.data.database.dao.LibraryDao
import com.example.bookandfriend.data.database.dao.SettingsDao
import com.example.bookandfriend.data.database.entity.Settings
import com.example.bookandfriend.data.repository.SettingsRepositoryImpl
import com.example.bookandfriend.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Database и DAO
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "book-and-friend-db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    // Репозиторий для работы с настройками
    @Provides
    @Singleton
    fun provideSettingsRepository(
        dao: SettingsDao
    ): SettingsRepository = SettingsRepositoryImpl(dao)

    // DAO для работы с настройками.
    @Provides
    @Singleton
    fun provideSettingsDao(database: AppDatabase): SettingsDao = database.settingsDao()

    @Provides
    @Singleton
    fun provideLibraryDao(db: AppDatabase): LibraryDao = db.libraryDao()


    // Вставка дефолтных параметров настроек если их нет в БД.
    @Provides
    @Singleton
    fun provideInitialSettings(database: AppDatabase) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val count = database.settingsDao().settingsCount()
            if (count == 0) {
                database.settingsDao().insertSettings(
                    Settings(
                        darkThemeEnabled = false,
                        soundEnabled = true
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("Settings", "Error initializing settings", e)
        }
    }
}