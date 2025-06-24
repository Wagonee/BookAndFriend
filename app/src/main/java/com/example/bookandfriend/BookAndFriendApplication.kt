package com.example.bookandfriend

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookAndFriendApplication : Application() {
    lateinit var soundManager: SoundManager

    override fun onCreate() {
        super.onCreate()
        soundManager = SoundManager(this)
    }
}