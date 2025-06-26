package com.example.bookandfriend.presentation.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.usecase.GetAllLibraryBooksUseCase
import com.example.bookandfriend.domain.usecase.RemoveFromLibraryUseCase
import com.example.bookandfriend.domain.usecase.SearchInLibraryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LibraryVM @Inject constructor(
    private val removeFromLibraryUseCase: RemoveFromLibraryUseCase,
    private val inLibrarySearchBooksUseCase: SearchInLibraryUseCase,
    private val getAllLibraryBooksUseCase: GetAllLibraryBooksUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    init {
        _query
            .onEach { input ->
                _state.update { it.copy(query = input) }
            }
            .flatMapLatest { input ->
                if (input.isBlank()) {
                    getAllLibraryBooksUseCase()
                } else {
                    inLibrarySearchBooksUseCase(input)
                }
            }.onEach { books ->
                _state.update { it.copy(books = books) }
            }
            .launchIn(viewModelScope)
    }

    fun processCommand(command: LibraryCommand) {
        when(command) {
            is LibraryCommand.InputSearchQuery -> { _query.update { command.query.trim() }}
            is LibraryCommand.RemoveBook -> removeBook(command.book)
            is LibraryCommand.ShowDetails -> command.onShowed(command.book)
        }
    }

    private fun removeBook(book: Book) {
        viewModelScope.launch {
            removeFromLibraryUseCase(book.id)
        }
    }
}


sealed interface LibraryCommand {
    data class InputSearchQuery(val query: String) : LibraryCommand
    data class RemoveBook(val book: Book) : LibraryCommand
    data class ShowDetails(val book: Book, val onShowed: (Book) -> Unit) : LibraryCommand
}

data class LibraryState(
    val query: String = "",
    val books: List<Book> = listOf()
)