package com.example.bookandfriend.data.mappers

import com.example.bookandfriend.data.network.dto.BookDetailsDto
import com.example.bookandfriend.data.network.dto.BookDto
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.model.BookDetails

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
    fun mapDtoToDomainDetails(dto: BookDetailsDto): BookDetails {
        return BookDetails(
            description = dto.description?.value ?: "No description",
            genres = dto.subjects
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