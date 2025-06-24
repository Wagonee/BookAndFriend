package com.example.bookandfriend.data.network.dto

import com.google.gson.annotations.SerializedName

data class BookDto(
    @SerializedName("key")
    val key: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("author_name")
    val authorName: List<String?>,
    @SerializedName("cover_i")
    val coverId: Int?,
    @SerializedName("first_publish_year")
    val firstPublishYear: Int?,
    @SerializedName("language")
    val language: List<String?>
)