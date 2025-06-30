package com.example.bookandfriend.data.repository

import com.example.bookandfriend.data.mappers.BookMapper
import com.example.bookandfriend.data.network.OpenLibraryApiService
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.model.BookDetails
import com.example.bookandfriend.domain.repository.LibraryRepository
import com.example.bookandfriend.domain.repository.SearchRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: OpenLibraryApiService,
    private val libraryRepository: LibraryRepository,
    private val mapper: BookMapper
) : SearchRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>> {
        return runCatching {
            val likedBooks = libraryRepository.getAllBook().first()
            val likedBookIds = likedBooks.map { it.id }.toSet()
            val response = apiService.searchBooks(query)
            val networkBooks = response.docs.map { mapper.mapDtoToDomain(it) }

            networkBooks.map { networkBook ->
                networkBook.copy(isLiked = likedBookIds.contains(networkBook.id))
            }
        }
    }

    override suspend fun getRandomBook(
        genre: String?,
        century: Int?,
        language: String?
    ): Result<Book> {
        return try {
            val queryParts = mutableListOf<String>()
            if (!genre.isNullOrBlank()) {
                queryParts.add("subject:\"$genre\"")
            }
            if (century != null) {
                val startYear = (century - 1) * 100 + 1
                val endYear = century * 100
                queryParts.add("first_publish_year:[${startYear} TO ${endYear}]")
            }
            if (!language.isNullOrBlank()) {
                queryParts.add("language:$language")
            }
            if (queryParts.isEmpty()) {
                return Result.failure(IllegalArgumentException("No criteria of random search were added."))
            }
            val query = queryParts.joinToString(" AND ")
            val response = apiService.searchBooks(query)
            val randomBookDto = response.docs.randomOrNull()
                ?: return Result.failure(NoSuchElementException("No books with these parameters were found."))
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