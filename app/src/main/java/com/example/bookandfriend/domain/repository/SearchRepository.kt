package com.example.bookandfriend.domain.repository

import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.model.BookDetails

interface SearchRepository {
    suspend fun searchBooks(query: String): Result<List<Book>>
    suspend fun getRandomBook(genre: String?, century: Int?, language: String?): Result<Book>
    suspend fun getBookDetails(workId: String): Result<BookDetails>
}