package com.example.bookandfriend.data.network

import com.example.bookandfriend.data.network.dto.BookDto
import com.example.bookandfriend.data.network.dto.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLibraryApiService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String
    ): SearchResultDto

    @GET("works/{workId}.json")
    suspend fun getBookDetails(@Path("workId") workId: String): BookDto
}