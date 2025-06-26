package com.example.bookandfriend.domain.usecase

import com.example.bookandfriend.domain.repository.LibraryRepository

class RemoveFromLibraryUseCase(private val repository: LibraryRepository) {
    suspend operator fun invoke(bookId: String) {
        repository.deleteBook(bookId)
    }
}