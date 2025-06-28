package com.example.bookandfriend.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookandfriend.data.database.entity.Settings
import com.example.bookandfriend.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    val settings: StateFlow<Settings> = repository.getSettings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = runBlocking { repository.getSettings().first() ?: Settings() }
        )

    fun setDarkThemeEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateSettings { it.copy(darkThemeEnabled = enabled) }
        }
    }

    fun setSoundEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateSettings { it.copy(soundEnabled = enabled) }
        }
    }
}