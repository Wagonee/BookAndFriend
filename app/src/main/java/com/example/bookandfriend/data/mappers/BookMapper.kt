package com.example.bookandfriend.data.mappers

import com.example.bookandfriend.data.network.dto.BookDto
import com.example.bookandfriend.domain.model.Book

class BookMapper {
    fun mapDtoToDomain(dto: BookDto): Book {
        return Book(
            id = dto.key.substringAfter("/works/"),
            title = dto.title,
            author = dto.authorName.firstOrNull() ?: "Unknown Author",
            coverId = dto.coverId,
            publishYear = dto.firstPublishYear,
            language = mapLanguageCode(dto.language.toString())
        )
    }

    private fun mapLanguageCode(code: String?): String {
        return when (code) {
            "eng" -> "English"
            "ger" -> "German"
            "rus" -> "Russian"
            "fra" -> "French"
            null -> "N/A"
            else -> code.replaceFirstChar { it -> it.uppercaseChar() }
        }
    }
}