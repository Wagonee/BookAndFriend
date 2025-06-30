package com.example.bookandfriend.domain.usecase

import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.repository.SearchRepository
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): Result<List<Book>> {
        return searchRepository.searchBooks(query)
    }
}