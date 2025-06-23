package com.example.bookandfriend.domain.repository

import com.example.bookandfriend.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchBooks(query: String): Flow<Result<List<Book>>>
    suspend fun getRandomBook(genre: String?, century: Int?): Result<Book>
}