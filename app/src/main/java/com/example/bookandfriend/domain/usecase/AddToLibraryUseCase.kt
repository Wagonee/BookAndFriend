package com.example.bookandfriend.domain.usecase

import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.repository.LibraryRepository

class AddToLibraryUseCase(private val repository: LibraryRepository) {
    suspend operator fun invoke(book: Book) {
        repository.addBook(book)
    }
}