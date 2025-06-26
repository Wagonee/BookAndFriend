package com.example.bookandfriend.domain.repository

import com.example.bookandfriend.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {
    suspend fun addBook(book: Book)
    suspend fun deleteBook(bookId: String)
    suspend fun getAllBook(): Flow<List<Book>>
}