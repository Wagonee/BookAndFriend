package com.example.bookandfriend.presentation.screens.library.library_vm

import com.example.bookandfriend.domain.model.Book

sealed interface LibraryCommand {
    data class InputSearchQuery(val query: String) : LibraryCommand
    data class RemoveBook(val book: Book) : LibraryCommand
    data class ShowDetails(val book: Book, val onShowed: (Book) -> Unit) : LibraryCommand
}