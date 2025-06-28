package com.example.bookandfriend.presentation.screens.book_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.bookandfriend.R
import com.example.bookandfriend.domain.model.Book
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
                        text = stringResource(id = R.string.book_info_title),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.book_info_back_button_content_description),
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
                    contentDescription = stringResource(id = R.string.book_cover_content_description),
                    modifier = Modifier
                        .size(width = 200.dp, height = 320.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = stringResource(id = R.string.book_info_book_title, book.title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )

            Text(
                text = stringResource(id = R.string.book_info_author, book.author),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )

            book.publishYear?.let {
                Text(
                    text = stringResource(id = R.string.book_info_published, it),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            book.language?.let {
                Text(
                    text = stringResource(id = R.string.book_info_language, it),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            book.genres?.takeIf { it.isNotEmpty() }?.let {
                Text(
                    text = stringResource(id = R.string.book_info_genres, it.joinToString(", ")),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            book.description?.let {
                Text(
                    text = stringResource(id = R.string.book_info_description, it),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}