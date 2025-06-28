package com.example.bookandfriend.di

import android.content.Context
import com.example.bookandfriend.data.sound.SoundManager
import com.example.bookandfriend.data.sound.SoundPlayerImpl
import com.example.bookandfriend.domain.repository.SettingsRepository
import com.example.bookandfriend.domain.sound.SoundPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SoundModule {

    @Provides
    @Singleton
    fun provideSoundManager(@ApplicationContext context: Context): SoundManager {
        return SoundManager(context)
    }

    @Provides
    @Singleton
    fun provideSoundPlayer(
        soundManager: SoundManager,
        settingsRepository: SettingsRepository
    ): SoundPlayer {
        return SoundPlayerImpl(soundManager, settingsRepository)
    }
}