package com.example.bookandfriend.presentation.screens.global_search

import com.example.bookandfriend.presentation.components.BookItem
import SearchBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookandfriend.R
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.presentation.navigation.BottomBar
import com.example.bookandfriend.presentation.screens.global_search.global_search_vm.MainScreenCommand
import com.example.bookandfriend.presentation.screens.global_search.global_search_vm.MainScreenVM
import com.example.bookandfriend.presentation.ui.theme.LocalCustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    vm: MainScreenVM = hiltViewModel(),
    onBookClick: (Book) -> Unit
) {
    val customColors = LocalCustomColors.current
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.main_screen_title),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = customColors.textColor
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
                    onClick = {
                        vm.playSound()
                        vm.processCommand(MainScreenCommand.SearchBooks(state.query))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = customColors.switchActive,
                        contentColor = customColors.switchCircle
                    )
                ) {
                    Text(text = stringResource(id = R.string.search_button))
                }
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.error.toString(), color = MaterialTheme.colorScheme.error)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.bookList, key = {book -> book.id}) { book ->
                        BookItem(
                            book,
                            onItemClick = {
                                vm.processCommand(
                                    MainScreenCommand.GetBookDetails(
                                        book,
                                        onSuccess = onBookClick)
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