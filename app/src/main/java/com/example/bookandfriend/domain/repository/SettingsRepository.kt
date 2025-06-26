package com.example.bookandfriend.domain.repository

import com.example.bookandfriend.data.database.entity.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<Settings>

    suspend fun updateSettings(block: (Settings) -> Settings)
}