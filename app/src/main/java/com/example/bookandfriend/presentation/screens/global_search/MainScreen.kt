package com.example.bookandfriend.presentation.screens.global_search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.presentation.navigation.BottomBar
import com.example.bookandfriend.presentation.ui.theme.LocalCustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    vm: MainScreenVM = hiltViewModel()
) {
    val customColors = LocalCustomColors.current


    val state by vm.state.collectAsState()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Book and Friend",
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = customColors.background,
                    titleContentColor = customColors.textColor
                ),
                modifier = Modifier.shadow(
                    elevation = 8.dp,
                    shape = RectangleShape,
                    ambientColor = customColors.shadowColor,
                    spotColor = customColors.shadowColor
                )
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(customColors.background)
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(query = state.query, onQueryChange = { newQuery ->
                vm.processCommand(
                    MainScreenCommand.UpdateQuery(newQuery)
                )
            })

            if (state.query.isNotBlank() && !state.searchExecuted) {
                Button(
                    onClick = { vm.processCommand(MainScreenCommand.SearchBooks(state.query)) }
                ) {
                    Text(text = "Search")
                }
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

            }


            else if (state.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.error.toString(), color = MaterialTheme.colorScheme.error)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.bookList) { book ->
                        BookItem(
                            book,
                            onItemClick = {
                                vm.processCommand(
                                    MainScreenCommand.GetBookDetails(
                                        book,
                                        onSuccess = { book ->
                                            Log.d(
                                                "Book detail",
                                                "Description ${book.description}, Genres ${book.genres.toString()}"
                                            )
                                        })
                                )
                            }, onLikeClick = {
                                if (!book.isLiked) {
                                    vm.processCommand(MainScreenCommand.AddToLibrary(book))
                                } else {
                                    vm.processCommand(MainScreenCommand.RemoveFromLibrary(book.id))
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val customColors = LocalCustomColors.current
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(customColors.secondBackground),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            textStyle = TextStyle(
                color = customColors.textColor,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .onFocusChanged { isFocused = it.isFocused },
            cursorBrush = SolidColor(customColors.textColor),
            decorationBox = { innerTextField ->
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (query.isEmpty() && !isFocused) {
                        Text(
                            text = "Type something...",
                            color = customColors.textColor.copy(alpha = 0.5f),
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun BookItem(
    book: Book,
    onItemClick: () -> Unit,
    onLikeClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            })
    {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically)
        {
            AsyncImage(
                model = "https://covers.openlibrary.org/b/id/${book.coverId}-M.jpg",
                contentDescription = "Book cover",
                modifier = Modifier.size(width = 50.dp, height = 80.dp),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                Text(text = book.author, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onLikeClick) {
                Icon(
                    imageVector = if (book.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Like button",
                    tint = if (book.isLiked) Color.Red else Color.Gray
                )
            }
        }
    }
}