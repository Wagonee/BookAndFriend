package com.example.bookandfriend.domain.usecase

import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchBooksUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): Flow<Result<List<Book>>> {
        return searchRepository.searchBooks(query)
    }
}