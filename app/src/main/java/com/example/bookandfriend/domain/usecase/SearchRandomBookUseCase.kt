package com.example.bookandfriend.domain.usecase

import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.repository.SearchRepository

class SearchRandomBookUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke(genre: String?, century: Int?): Result<Book> {
        return repository.getRandomBook(genre, century)
    }
}