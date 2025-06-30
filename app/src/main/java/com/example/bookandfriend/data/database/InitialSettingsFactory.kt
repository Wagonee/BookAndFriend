package com.example.bookandfriend.data.database

import android.util.Log
import com.example.bookandfriend.data.database.entity.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InitialSettingsFactory(
    private val database: AppDatabase
) {

    fun create() {
        CoroutineScope(Dispatchers.IO).launch {
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
}