package com.example.bookandfriend.data.sound

import com.example.bookandfriend.R
import com.example.bookandfriend.domain.repository.SettingsRepository
import com.example.bookandfriend.domain.sound.SoundPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundPlayerImpl @Inject constructor(
    private val soundManager: SoundManager,
    private val settingsRepository: SettingsRepository
) : SoundPlayer {

    override fun playClickSound() {
        CoroutineScope(Dispatchers.Default).launch {
            val settings = settingsRepository.getSettings().first()
            if (settings.soundEnabled) {
                soundManager.playSound(R.raw.click_sound)
            }
        }
    }
}