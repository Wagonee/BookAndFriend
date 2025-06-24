package com.example.bookandfriend.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookandfriend.data.database.dao.SettingsDao
import com.example.bookandfriend.data.database.entity.Settings

@Database(
    entities = [
        Settings::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // DAO для настроек приложения.
    abstract fun settingsDao(): SettingsDao
}