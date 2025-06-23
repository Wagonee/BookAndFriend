package com.example.bookandfriend.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val id: Int = 1,
    val darkThemeEnabled: Boolean = false,
    val soundEnabled: Boolean = true
)