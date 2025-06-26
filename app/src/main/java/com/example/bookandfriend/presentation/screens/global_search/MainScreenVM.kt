package com.example.bookandfriend.presentation.screens.global_search

import androidx.lifecycle.ViewModel
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.usecase.AddToLibraryUseCase
import com.example.bookandfriend.domain.usecase.GetBookDetailsUseCase
import com.example.bookandfriend.domain.usecase.RemoveFromLibraryUseCase
import com.example.bookandfriend.domain.usecase.SearchBooksUseCase

class MainScreenVM(
    private val searchBooksUseCase: SearchBooksUseCase,
    private val addToLibraryUseCase: AddToLibraryUseCase,
    private val removeFromLibraryUseCase: RemoveFromLibraryUseCase,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
) : ViewModel() {


}


sealed interface MainScreenCommand {
    data class SearchBooks(val query: String) : MainScreenCommand
    data class AddToLibrary(val book: Book) : MainScreenCommand
    data class RemoveFromLibrary(val bookId: String) : MainScreenCommand
    data class GetBookDetails(val bookId: String) : MainScreenCommand
}

data class MainScreenState(
    val query: String = "",
    val bookList: List<Book> = listOf()
)