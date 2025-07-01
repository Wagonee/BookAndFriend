package com.example.bookandfriend.presentation.screens.random_search.random_search_vm

import com.example.bookandfriend.domain.model.Book

data class RandomSearchState(
    val book: Book? = null,
    val century: Int? = null,
    val genre: String? = null,
    val language: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
