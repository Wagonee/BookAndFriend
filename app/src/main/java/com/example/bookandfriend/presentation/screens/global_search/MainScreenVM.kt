package com.example.bookandfriend.presentation.screens.global_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.usecase.AddToLibraryUseCase
import com.example.bookandfriend.domain.usecase.GetBookDetailsUseCase
import com.example.bookandfriend.domain.usecase.RemoveFromLibraryUseCase
import com.example.bookandfriend.domain.usecase.SearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenVM @Inject constructor(
    private val searchBooksUseCase: SearchBooksUseCase,
    private val addToLibraryUseCase: AddToLibraryUseCase,
    private val removeFromLibraryUseCase: RemoveFromLibraryUseCase,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    fun processCommand(command: MainScreenCommand) {
        viewModelScope.launch {
            when (command) {
                is MainScreenCommand.AddToLibrary -> addToLibrary(command.book)
                is MainScreenCommand.GetBookDetails -> getBookDetails(command.book, command.onSuccess)
                is MainScreenCommand.RemoveFromLibrary -> removeFromLibrary(command.bookId)
                is MainScreenCommand.SearchBooks -> searchBooks(command.query)
                is MainScreenCommand.UpdateQuery -> updateSearchQuery(command.query)
            }
        }
    }


    private fun updateSearchQuery(query: String) {
        _state.update { it.copy(query = query) }
    }

    private fun searchBooks(query: String) {
        if (query.isBlank()) {
            _state.update { it.copy(bookList = emptyList()) }
            return
        }

        viewModelScope.launch {
            searchBooksUseCase(query)
                .onStart { _state.update { it.copy(isLoading = true) } }.catch { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = throwable.message
                        )
                    }
                }
                .collect { result ->
                    result.onSuccess { books ->
                        _state.update {
                            it.copy(
                                bookList = books,
                                isLoading = false
                            )
                        }
                    }.onFailure { throwable ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = throwable.message
                            )
                        }
                    }
                }
        }

    }


    private fun addToLibrary(book: Book) {
        viewModelScope.launch {
            try {
                val bookWithDetails = getBookDetailsUseCase(book).getOrThrow()
                addToLibraryUseCase(bookWithDetails)
                _state.update { currentState ->
                    val updatedList = currentState.bookList.map {
                        if (it.id == book.id) it.copy(isLiked = true) else it
                    }
                    currentState.copy(bookList = updatedList)
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun removeFromLibrary(bookId: String) {
        viewModelScope.launch {
            try {
                removeFromLibraryUseCase(bookId)
                _state.update { currentState ->
                    val updatedList = currentState.bookList.map {
                        if (it.id == bookId) it.copy(isLiked = false) else it
                    }
                    currentState.copy(bookList = updatedList)
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun getBookDetails(book: Book, onSuccess: (Book) -> Unit) {
        viewModelScope.launch {
            val result = getBookDetailsUseCase(book)
            result.onSuccess { detailedBook ->
                onSuccess(detailedBook)
            }.onFailure { throwable ->
                _state.update { it.copy(error = throwable.message) }
            }
        }
    }
}

sealed interface MainScreenCommand {
    data class SearchBooks(val query: String) : MainScreenCommand
    data class UpdateQuery(val query: String) : MainScreenCommand
    data class AddToLibrary(val book: Book) : MainScreenCommand
    data class RemoveFromLibrary(val bookId: String) : MainScreenCommand
    data class GetBookDetails(val book: Book, val onSuccess: (Book) -> Unit) : MainScreenCommand
}

data class MainScreenState(
    val query: String = "",
    val bookList: List<Book> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)