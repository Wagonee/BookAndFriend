package com.example.bookandfriend.data.repository

import com.example.bookandfriend.data.database.dao.SettingsDao
import com.example.bookandfriend.data.database.entity.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao
) {

    fun getSettings(): Flow<Settings> = flow {
        val initial = settingsDao.getSettings().first()
        if (initial == null) {
            val defaultSettings = Settings()
            settingsDao.insertSettings(defaultSettings)
            emit(defaultSettings)
        } else {
            emit(initial)
        }
        emitAll(settingsDao.getSettings().filterNotNull())
    }

    suspend fun updateSettings(block: (Settings) -> Settings) {
        val current = settingsDao.getSettings().first()
        val newSettings = block(current ?: Settings())
        settingsDao.insertSettings(newSettings)
    }
}