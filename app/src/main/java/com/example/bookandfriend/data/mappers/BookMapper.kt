package com.example.bookandfriend.data.mappers

import com.example.bookandfriend.data.network.dto.BookDetailsDto
import com.example.bookandfriend.data.network.dto.BookDto
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.model.BookDetails
import com.google.gson.internal.LinkedTreeMap

class BookMapper {
    fun mapDtoToDomain(dto: BookDto): Book {
        return Book(
            id = dto.key.substringAfter("/works/"),
            title = dto.title,
            author = dto.authorName?.firstOrNull() ?: "Unknown Author",
            coverId = dto.coverId,
            publishYear = dto.firstPublishYear,
            language = mapLanguageCode(dto.language?.toString())
        )
    }

    fun mapDtoToDomainDetails(dto: BookDetailsDto): BookDetails {
        val descriptionText = when (dto.description) {
            is String -> dto.description
            is LinkedTreeMap<*, *> -> {
                (dto.description["value"] as? String) ?: "No description"
            }
            else -> "No description"
        }
        return BookDetails(
            description = descriptionText,
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