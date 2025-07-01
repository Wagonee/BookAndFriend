package com.example.bookandfriend.presentation.screens.global_search.global_search_vm

import com.example.bookandfriend.domain.model.Book

data class MainScreenState(
    var query: String = "",
    val bookList: List<Book> = listOf(),
    val isLoading: Boolean = false,
    val searchExecuted: Boolean = false,
    val error: String? = null
)
