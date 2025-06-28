package com.example.bookandfriend.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookandfriend.R
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.presentation.ui.theme.LocalCustomColors

@Composable
fun BookItem(
    book: Book,
    onItemClick: () -> Unit,
    onLikeClick: () -> Unit
) {
    val customColors = LocalCustomColors.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = customColors.secondBackground
        )
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically)
        {
            AsyncImage(
                model = "https://covers.openlibrary.org/b/id/${book.coverId}-M.jpg",
                contentDescription = stringResource(id = R.string.book_cover_content_description),
                modifier = Modifier.size(width = 50.dp, height = 80.dp),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.title,
                    color = customColors.textColor,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = book.author,
                    color = customColors.textColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onLikeClick) {
                Icon(
                    imageVector = if (book.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.like_button_content_description),
                    tint = if (book.isLiked) Color.Red else Color.Gray
                )
            }
        }
    }
}