package com.example.bookandfriend.domain.usecase

import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.repository.LibraryRepository
import javax.inject.Inject

class AddToLibraryUseCase @Inject constructor(private val repository: LibraryRepository) {
    suspend operator fun invoke(book: Book) {
        repository.addBook(book)
    }
}