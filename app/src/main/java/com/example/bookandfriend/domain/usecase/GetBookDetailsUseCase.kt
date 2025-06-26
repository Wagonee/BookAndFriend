package com.example.bookandfriend.domain.usecase

import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.repository.SearchRepository
import javax.inject.Inject

class GetBookDetailsUseCase @Inject constructor(private val repository: SearchRepository) {
    suspend operator fun invoke(book: Book): Result<Book> {

        if (book.description != null && book.genres != null) {
            return Result.success(book)
        }

        val detailsResult = repository.getBookDetails(book.id)
        return detailsResult.map { details ->
            val enrichedBook = book.copy(
                description = details.description,
                genres = details.genres
            )
            enrichedBook
        }
    }
}