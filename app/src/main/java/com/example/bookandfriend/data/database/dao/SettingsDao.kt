package com.example.bookandfriend.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookandfriend.data.database.entity.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    // Получаем настройки в виде потока.
    @Query("SELECT * FROM settings WHERE id = 1")
    fun getSettings(): Flow<Settings?>

    // Вставка или замена (при конфликте ID) настроек.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: Settings)

    // Проверка наличия записей в таблице (для инициализации).
    @Query("SELECT COUNT(*) FROM settings WHERE id = 1")
    suspend fun settingsCount(): Int
}