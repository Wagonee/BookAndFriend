package com.example.bookandfriend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.bookandfriend.data.database.AppDatabase
import com.example.bookandfriend.data.repository.LibraryRepositoryImpl
import com.example.bookandfriend.data.repository.SearchRepositoryImpl
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.domain.usecase.GetBookDetailsUseCase
import com.example.bookandfriend.domain.usecase.SearchBooksUseCase
import com.example.bookandfriend.domain.usecase.SearchRandomBookUseCase
import kotlinx.coroutines.launch

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "book-and-friend-db"
            )
                .fallbackToDestructiveMigration(false)
                .build()
            val libraryDao = db.libraryDao()

            val libraryRepository = LibraryRepositoryImpl(libraryDao)
            val searchRepository = SearchRepositoryImpl(libraryRepository = libraryRepository)

            val searchBooksUseCase = SearchBooksUseCase(searchRepository)
            val searchRandomBookUseCase = SearchRandomBookUseCase(searchRepository)
            val getBookDetailsUseCase = GetBookDetailsUseCase(searchRepository)


            var query by remember { mutableStateOf("") }
            var searchResult by remember { mutableStateOf<List<Book>>(emptyList()) }
            var bookDetails by remember { mutableStateOf<Book?>(null) }
            var isLoading by remember { mutableStateOf(false) }
            var errorMessage by remember { mutableStateOf("") }

            val coroutineScope = rememberCoroutineScope()

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Spacer(modifier = Modifier.height(32.dp))
                TextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Search Query") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(onClick = {
                        isLoading = true
                        errorMessage = ""
                        bookDetails = null
                        coroutineScope.launch {
                            searchBooksUseCase(query).collect { result ->
                                result.fold(
                                    onSuccess = { books -> searchResult = books },
                                    onFailure = { error -> errorMessage = error.message ?: "Unknown error" }
                                )
                                isLoading = false
                            }
                        }
                    }) {
                        Text("Search")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        isLoading = true
                        errorMessage = ""
                        bookDetails = null
                        coroutineScope.launch {
                            val result = searchRandomBookUseCase(genre = null, century = 20)
                            result.fold(
                                onSuccess = { book -> searchResult = listOf(book) },
                                onFailure = { error -> errorMessage = error.message ?: "Unknown error" }
                            )
                            isLoading = false
                        }
                    }) {
                        Text("Get Random Book")
                    }
                }

                if (isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                }

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(searchResult) { book ->
                        BookItem(book = book, onClick = {
                            isLoading = true
                            errorMessage = ""
                            coroutineScope.launch {
                                val detailsResult = getBookDetailsUseCase(book)
                                detailsResult.fold(
                                    onSuccess = { detailedBook -> bookDetails = detailedBook },
                                    onFailure = { error -> errorMessage = error.message ?: "Details error" }
                                )
                                isLoading = false
                            }
                        })
                    }
                }

                bookDetails?.let { details ->
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text("Details:", style = MaterialTheme.typography.titleMedium)
                    Text("Title: ${details.title}")
                    Text("Description: ${details.description ?: "Not available"}")
                    Text("Genres: ${details.genres?.joinToString(", ") ?: "Not available"}")
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Text(text = book.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = book.author, fontStyle = FontStyle.Italic)
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}