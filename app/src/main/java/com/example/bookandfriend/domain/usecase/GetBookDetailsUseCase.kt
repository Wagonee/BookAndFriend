package com.example.bookandfriend.domain.usecase

import com.example.bookandfriend.domain.model.BookDetails
import com.example.bookandfriend.domain.repository.SearchRepository

class GetBookDetailsUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke(workId: String) : Result<BookDetails> {
        return repository.getBookDetails(workId)
    }
}