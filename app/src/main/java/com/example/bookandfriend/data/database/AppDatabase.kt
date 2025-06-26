package com.example.bookandfriend.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bookandfriend.data.database.dao.LibraryDao
import com.example.bookandfriend.data.database.dao.SettingsDao
import com.example.bookandfriend.data.database.entity.BookEntity
import com.example.bookandfriend.data.database.entity.Settings

@Database(
    entities = [
        Settings::class,
        BookEntity::class
    ],
    version = 2,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    // DAO для настроек приложения.
    abstract fun settingsDao(): SettingsDao
    // DAO для управления библиотекой.
    abstract fun libraryDao() : LibraryDao
}