package com.example.bookandfriend.di

import android.content.Context
import androidx.room.Room
import com.example.bookandfriend.data.database.AppDatabase
import com.example.bookandfriend.data.database.InitialSettingsFactory
import com.example.bookandfriend.data.database.dao.LibraryDao
import com.example.bookandfriend.data.database.dao.SettingsDao
import com.example.bookandfriend.data.repository.SettingsRepositoryImpl
import com.example.bookandfriend.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideInitialSettings(database: AppDatabase): InitialSettingsFactory =
        InitialSettingsFactory(database)
}