package com.example.bookandfriend

import android.app.Application
import com.example.bookandfriend.data.sound.SoundManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookAndFriendApplication : Application() {
    lateinit var soundManager: SoundManager

    override fun onCreate() {
        super.onCreate()
        soundManager = SoundManager(this)
    }
}