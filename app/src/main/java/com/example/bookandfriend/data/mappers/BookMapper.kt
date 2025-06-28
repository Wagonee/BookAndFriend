package com.example.bookandfriend.data.mappers

import android.content.Context
import com.example.bookandfriend.R
import com.example.bookandfriend.data.network.dto.BookDetailsDto
import com.example.bookandfriend.data.network.dto.BookDto
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.model.BookDetails
import com.google.gson.internal.LinkedTreeMap
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BookMapper @Inject constructor(@ApplicationContext private val context: Context) {
    fun mapDtoToDomain(dto: BookDto): Book {
        return Book(
            id = dto.key.substringAfter("/works/"),
            title = dto.title,
            author = dto.authorName?.firstOrNull() ?: context.getString(R.string.unknown_author),
            coverId = dto.coverId,
            publishYear = dto.firstPublishYear,
            language = mapLanguageCode(dto.language?.toString())
        )
    }

    fun mapDtoToDomainDetails(dto: BookDetailsDto): BookDetails {
        val descriptionText = when (dto.description) {
            is String -> dto.description
            is LinkedTreeMap<*, *> -> {
                (dto.description["value"] as? String) ?: context.getString(R.string.no_description)
            }
            else -> context.getString(R.string.no_description)
        }
        return BookDetails(
            description = descriptionText,
            genres = dto.subjects
        )
    }

    private fun mapLanguageCode(code: String?): String {
        return when (code) {
            "eng" -> context.getString(R.string.language_english)
            "ger" -> context.getString(R.string.language_german)
            "rus" -> context.getString(R.string.language_russian)
            "fra" -> context.getString(R.string.language_french)
            null -> context.getString(R.string.language_na)
            else -> code.replaceFirstChar { it.uppercaseChar() }
        }
    }
}