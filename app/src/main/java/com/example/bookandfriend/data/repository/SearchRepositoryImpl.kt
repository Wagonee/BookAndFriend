package com.example.bookandfriend.data.repository

import com.example.bookandfriend.data.mappers.BookMapper
import com.example.bookandfriend.data.network.OpenLibraryApiService
import com.example.bookandfriend.data.network.RetrofitClient
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.model.BookDetails
import com.example.bookandfriend.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(
    private val apiService: OpenLibraryApiService = RetrofitClient.apiService,
    private val mapper: BookMapper = BookMapper()
) : SearchRepository {
    override suspend fun searchBooks(query: String): Flow<Result<List<Book>>> = flow {
        try {
            val response = apiService.searchBooks(query)
            val books = response.docs.map { mapper.mapDtoToDomain(it) }
            emit(Result.success(books))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun getRandomBook(
        genre: String?,
        century: Int?
    ): Result<Book> {
        return try {
            val query = when {
                !genre.isNullOrBlank() -> "subject:\"$genre\""
                century != null -> {
                    val startYear = (century - 1) * 100 + 1
                    val endYear = century * 100
                    "first_publish_year:[${startYear} TO ${endYear}]"
                }
                else -> return Result.failure(IllegalArgumentException("No criteria of random search were added."))
            }
            val response = apiService.searchBooks(query)
            val randomBookDto = response.docs.randomOrNull()
            if (randomBookDto == null) {
                return Result.failure(NoSuchElementException("No books with these parameters were found."))
            }
            val randomBook = mapper.mapDtoToDomain(randomBookDto)
            Result.success(randomBook)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getBookDetails(workId: String): Result<BookDetails> {
        return try {
            val id = workId.removePrefix("/works/")
            val detailsDto = apiService.getBookDetails(id)
            Result.success(mapper.mapDtoToDomainDetails(detailsDto))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}