package com.example.bookandfriend.domain.usecase

import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchInLibraryUseCase(private val repository: LibraryRepository) {
    suspend operator fun invoke(query: String): Flow<List<Book>> {
        return repository.getAllBook().map { books ->
            if (query.isBlank()) {
                books
            } else {
                books.filter {
                    it.title.contains(query, ignoreCase = true) || it.author.contains(
                        query,
                        ignoreCase = true
                    )
                }
            }
        }
    }
}
