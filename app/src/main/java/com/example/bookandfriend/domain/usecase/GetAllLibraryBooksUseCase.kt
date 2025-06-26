package com.example.bookandfriend.domain.usecase

import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLibraryBooksUseCase @Inject constructor(private val repository: LibraryRepository) {
    suspend operator fun invoke(): Flow<List<Book>> {
        return repository.getAllBook()
    }
}