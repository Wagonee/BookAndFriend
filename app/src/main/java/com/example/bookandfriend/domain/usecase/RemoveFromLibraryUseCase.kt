package com.example.bookandfriend.domain.usecase

import com.example.bookandfriend.domain.repository.LibraryRepository
import javax.inject.Inject

class RemoveFromLibraryUseCase @Inject constructor(private val repository: LibraryRepository) {
    suspend operator fun invoke(bookId: String) {
        repository.deleteBook(bookId)
    }
}