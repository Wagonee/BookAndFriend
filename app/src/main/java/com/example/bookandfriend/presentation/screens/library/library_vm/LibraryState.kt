package com.example.bookandfriend.presentation.screens.library.library_vm

import com.example.bookandfriend.domain.model.Book

data class LibraryState(
    val query: String = "",
    val books: List<Book> = listOf()
)
