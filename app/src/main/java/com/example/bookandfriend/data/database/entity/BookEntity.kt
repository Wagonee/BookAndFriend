package com.example.bookandfriend.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "library")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val coverId: Int?,
    val publishYear: Int?,
    val language: String?,
    val description: String?,
    val genres: List<String>?
)