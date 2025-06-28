package com.example.bookandfriend.presentation.screens.book_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.presentation.navigation.BottomBar
import com.example.bookandfriend.presentation.ui.theme.LocalCustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookInfoScreen(
    navController: NavController,
    book: Book
) {
    val customColors = LocalCustomColors.current

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "About the book",
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = customColors.textColor
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = customColors.textColor
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(customColors.background)
                .padding(innerPadding)
                .padding(24.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            book.coverId?.let { coverId ->
                Image(
                    painter = rememberAsyncImagePainter("https://covers.openlibrary.org/b/id/${coverId}-M.jpg"),
                    contentDescription = "Book cover",
                    modifier = Modifier
                        .size(width = 200.dp, height = 320.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = "Title: ${book.title}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )

            Text(
                text = "Author: ${book.author}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )

            book.publishYear?.let {
                Text(
                    text = "Published: $it",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            book.language?.let {
                Text(
                    text = "Language: $it",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            book.genres?.takeIf { it.isNotEmpty() }?.let {
                Text(
                    text = "Genres: ${it.joinToString(", ")}",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            book.description?.let {
                Text(
                    text ="Description:\n$it",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}