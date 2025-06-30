package com.example.bookandfriend

import android.app.Application
import com.example.bookandfriend.data.database.InitialSettingsFactory
import com.example.bookandfriend.data.sound.SoundManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BookAndFriendApplication : Application() {
    lateinit var soundManager: SoundManager

    @Inject
    lateinit var initialSettingsFactory: InitialSettingsFactory

    override fun onCreate() {
        super.onCreate()
        soundManager = SoundManager(this)
        initialSettingsFactory.create()
    }
}