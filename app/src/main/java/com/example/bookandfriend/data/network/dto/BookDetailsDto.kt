package com.example.bookandfriend.data.network.dto

import com.google.gson.annotations.SerializedName

data class BookDetailsDto(
    @SerializedName("description")
    val description: DescriptionValue?,
    @SerializedName("subjects")
    val subjects: List<String>?
)

data class DescriptionValue(
    @SerializedName("value")
    val value: String?
)