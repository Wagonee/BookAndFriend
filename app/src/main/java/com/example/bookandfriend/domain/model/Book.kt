package com.example.bookandfriend.domain.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val coverId: Int?,
    val publishYear: Int?,
    val language: String?,
    val description: String? = null,
    val genres: List<String>? = null
)