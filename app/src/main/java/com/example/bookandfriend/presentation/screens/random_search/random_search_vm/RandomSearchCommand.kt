package com.example.bookandfriend.presentation.screens.random_search.random_search_vm

import com.example.bookandfriend.domain.model.Book

sealed interface RandomSearchCommand {
    data class GetRandomBook(val century: Int?, val genre: String?, val language: String?) :
        RandomSearchCommand

    data class AddBookToLibrary(val book: Book) : RandomSearchCommand
    data class RemoveBookFromLibrary(val bookId: String) : RandomSearchCommand
    data class GetBookDetails(val book: Book, val onSuccess: (Book) -> Unit) : RandomSearchCommand
}