package com.example.bookandfriend.presentation.screens.random_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.sound.SoundPlayer
import com.example.bookandfriend.domain.usecase.AddToLibraryUseCase
import com.example.bookandfriend.domain.usecase.GetBookDetailsUseCase
import com.example.bookandfriend.domain.usecase.RemoveFromLibraryUseCase
import com.example.bookandfriend.domain.usecase.SearchRandomBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


private data class RandomSearchParams(val genre: String?, val century: Int?, val language: String?)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RandomSearchVM @Inject constructor(
    private val randomBookUseCase: SearchRandomBookUseCase,
    private val addToLibraryUseCase: AddToLibraryUseCase,
    private val removeFromLibraryUseCase: RemoveFromLibraryUseCase,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val soundPlayer: SoundPlayer
) : ViewModel() {

    private val _state = MutableStateFlow(RandomSearchState())
    val state = _state.asStateFlow()
    private val _searchTrigger = MutableStateFlow<RandomSearchParams?>(null)
    fun processCommand(command: RandomSearchCommand) {
        when (command) {
            is RandomSearchCommand.AddBookToLibrary -> addToLibrary(command.book)
            is RandomSearchCommand.GetBookDetails -> getBookDetails(command.book, command.onSuccess)
            is RandomSearchCommand.GetRandomBook -> {
                _searchTrigger.value =
                    RandomSearchParams(command.genre, command.century, command.language)
            }

            is RandomSearchCommand.RemoveBookFromLibrary -> removeFromLibrary(command.bookId)
        }
    }

    fun playSound() {
        soundPlayer.playClickSound()
    }

    init {
        viewModelScope.launch {
            _searchTrigger
                .filterNotNull()
                .flatMapLatest { params ->
                    flow {
                        emit(randomBookUseCase(params.genre, params.century, params.language))
                    }.onStart {
                        _state.update { it.copy(isLoading = true, error = null, book = null) }
                    }
                }
                .collect { result ->
                    result.onSuccess { book ->
                        _state.update { it.copy(isLoading = false, book = book) }
                    }.onFailure { throwable ->
                        _state.update {
                            it.copy(
                                book = null,
                                error = throwable.message,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

    private fun addToLibrary(book: Book) {
        viewModelScope.launch {
            try {
                addToLibraryUseCase(book.copy(isLiked = true))
                _state.update {
                    if (it.book?.id == book.id) {
                        it.copy(book = it.book.copy(isLiked = true))
                    } else {
                        it
                    }
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
                _state.update {
                    if (it.book?.id == bookId) {
                        it.copy(book = it.book.copy(isLiked = false))
                    } else {
                        it
                    }
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
            }.onFailure { throwable -> _state.update { it.copy(error = throwable.message) } }
        }
    }
}


sealed interface RandomSearchCommand {
    data class GetRandomBook(val century: Int?, val genre: String?, val language: String?) :
        RandomSearchCommand

    data class AddBookToLibrary(val book: Book) : RandomSearchCommand
    data class RemoveBookFromLibrary(val bookId: String) : RandomSearchCommand
    data class GetBookDetails(val book: Book, val onSuccess: (Book) -> Unit) : RandomSearchCommand
}

data class RandomSearchState(
    val book: Book? = null,
    val century: Int? = null,
    val genre: String? = null,
    val language: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)