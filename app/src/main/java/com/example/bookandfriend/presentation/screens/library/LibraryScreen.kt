package com.example.bookandfriend.presentation.screens.library

import com.example.bookandfriend.presentation.components.BookItem
import SearchBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.presentation.navigation.BottomBar
import com.example.bookandfriend.presentation.screens.global_search.MainScreenCommand
import com.example.bookandfriend.presentation.ui.theme.LocalCustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    navController: NavController,
    vm: LibraryVM = hiltViewModel(),
    onBookClick: (Book) -> Unit
) {
    val customColors = LocalCustomColors.current
    val state by vm.state.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Favourites",
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
                    LibraryCommand.InputSearchQuery(newQuery)
                )
            })
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items = state.books) { book ->
                    BookItem(
                        book = book,
                        onItemClick = {
                            vm.processCommand(
                                LibraryCommand.ShowDetails(
                                    book,
                                    onShowed = onBookClick)
                            )
                        },
                        onLikeClick = { vm.processCommand(LibraryCommand.RemoveBook(book)) }
                    )

                }
            }
        }
    }
}



