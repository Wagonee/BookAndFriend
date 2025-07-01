package com.example.bookandfriend.presentation.screens.random_search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookandfriend.R
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.presentation.components.BookItem
import com.example.bookandfriend.presentation.navigation.BottomBar
import com.example.bookandfriend.presentation.screens.random_search.random_search_vm.RandomSearchCommand
import com.example.bookandfriend.presentation.screens.random_search.random_search_vm.RandomSearchVM
import com.example.bookandfriend.presentation.ui.theme.LocalCustomColors

private data class SelectableItem(val displayName: String, val apiValue: String)
private data class CenturyItem(val displayName: String, val apiValue: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomSearchScreen(
    navController: NavController,
    vm: RandomSearchVM = hiltViewModel(),
    onBookClick: (Book) -> Unit
) {
    val customColors = LocalCustomColors.current

    val genres =
        listOf(
            SelectableItem(stringResource(id = R.string.genre_any), ""),
            SelectableItem(stringResource(id = R.string.genre_fantasy), "fantasy"),
            SelectableItem(stringResource(id = R.string.genre_science_fiction), "science_fiction"),
            SelectableItem(stringResource(id = R.string.genre_romance), "romance"),
            SelectableItem(stringResource(id = R.string.genre_mystery), "mystery"),
            SelectableItem(stringResource(id = R.string.genre_thriller), "thriller"),
            SelectableItem(stringResource(id = R.string.genre_horror), "horror"),
            SelectableItem(stringResource(id = R.string.genre_historical_fiction), "historical_fiction"),
            SelectableItem(stringResource(id = R.string.genre_biography), "biography"),
            SelectableItem(stringResource(id = R.string.genre_classic), "classic"),
            SelectableItem(stringResource(id = R.string.genre_novel), "novel")
        )


    val languages =
        listOf(
            SelectableItem(stringResource(id = R.string.language_any), ""),
            SelectableItem(stringResource(id = R.string.language_english), "eng"),
            SelectableItem(stringResource(id = R.string.language_russian), "rus"),
            SelectableItem(stringResource(id = R.string.language_french), "fre"),
            SelectableItem(stringResource(id = R.string.language_german), "ger"),
            SelectableItem(stringResource(id = R.string.language_spanish), "spa"),
            SelectableItem(stringResource(id = R.string.language_italian), "ita")
        )


    val centuries =
        (1..21).map { CenturyItem(stringResource(id = R.string.century_format, it), it) }
            .toMutableList().apply {
                add(0, CenturyItem(stringResource(id = R.string.century_any), 0))
            }


    var selectedGenre by remember { mutableStateOf(genres.first()) }
    var selectedLanguage by remember { mutableStateOf(languages.first()) }
    var selectedCentury by remember { mutableStateOf(centuries.first()) }

    var expandedGenre by remember { mutableStateOf(false) }
    var expandedLanguage by remember { mutableStateOf(false) }
    var expandedDate by remember { mutableStateOf(false) }

    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.random_search_title), textAlign = TextAlign.Center) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = customColors.textColor
                )
            )
        },
        bottomBar = { BottomBar(navController = navController) }
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
            Text(
                text = stringResource(id = R.string.random_search_prompt),
                textAlign = TextAlign.Center, fontSize = 16.sp,
                fontWeight = FontWeight.Thin
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ExposedDropdownMenuBox(
                    expanded = expandedGenre,
                    onExpandedChange = { expandedGenre = !expandedGenre },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextField(
                        value = selectedGenre.displayName,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedGenre) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .clip(
                                if (expandedGenre) {
                                    RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                                } else {
                                    RoundedCornerShape(30.dp)
                                }
                            )
                            .background(color = customColors.secondBackground),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = customColors.secondBackground,
                            unfocusedContainerColor = customColors.secondBackground,
                            focusedTextColor = customColors.textColor,
                            unfocusedTextColor = customColors.textColor,
                            focusedIndicatorColor = customColors.secondBackground,
                            unfocusedIndicatorColor = customColors.secondBackground
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedGenre,
                        onDismissRequest = { expandedGenre = false },
                        containerColor = customColors.secondBackground
                    ) {
                        genres.forEach { genre ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = genre.displayName,
                                        color = customColors.textColor
                                    )
                                },
                                onClick = {
                                    selectedGenre = genre
                                    expandedGenre = false
                                }
                            )
                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExposedDropdownMenuBox(
                    expanded = expandedLanguage,
                    onExpandedChange = { expandedLanguage = !expandedLanguage },
                    modifier = Modifier
                        .weight(0.4f)
                        .clip(
                            if (expandedLanguage) {
                                RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                            } else {
                                RoundedCornerShape(30.dp)
                            }
                        )
                ) {
                    TextField(
                        value = selectedLanguage.displayName,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedLanguage) },
                        modifier = Modifier
                            .menuAnchor()
                            .background(customColors.secondBackground),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = customColors.secondBackground,
                            unfocusedContainerColor = customColors.secondBackground,
                            focusedTextColor = customColors.textColor,
                            unfocusedTextColor = customColors.textColor,
                            focusedIndicatorColor = customColors.secondBackground,
                            unfocusedIndicatorColor = customColors.secondBackground
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedLanguage,
                        onDismissRequest = { expandedLanguage = false },
                        containerColor = customColors.secondBackground
                    ) {
                        languages.forEach { lang ->
                            DropdownMenuItem(
                                text = { Text(lang.displayName, color = customColors.textColor) },
                                onClick = {
                                    selectedLanguage = lang
                                    expandedLanguage = false
                                }
                            )
                        }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = expandedDate,
                    onExpandedChange = { expandedDate = !expandedDate },
                    modifier = Modifier
                        .weight(0.4f)
                        .clip(
                            if (expandedDate) {
                                RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                            } else {
                                RoundedCornerShape(30.dp)
                            }
                        )
                ) {
                    TextField(
                        value = selectedCentury.displayName,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedDate) },
                        modifier = Modifier
                            .menuAnchor()
                            .background(customColors.secondBackground),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = customColors.secondBackground,
                            unfocusedContainerColor = customColors.secondBackground,
                            focusedTextColor = customColors.textColor,
                            unfocusedTextColor = customColors.textColor,
                            focusedIndicatorColor = customColors.secondBackground,
                            unfocusedIndicatorColor = customColors.secondBackground
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedDate,
                        onDismissRequest = { expandedDate = false },
                        modifier = Modifier
                            .height(200.dp)
                            .verticalScroll(rememberScrollState()),
                        containerColor = customColors.secondBackground
                    ) {
                        centuries.forEach { date ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = date.displayName,
                                        color = customColors.textColor
                                    )
                                },
                                onClick = {
                                    selectedCentury = date
                                    expandedDate = false
                                }
                            )
                        }
                    }
                }
            }
            IconButton(
                onClick = {
                    vm.playSound()
                    vm.processCommand(
                        RandomSearchCommand.GetRandomBook(
                            century = if (selectedCentury.apiValue == 0) null else selectedCentury.apiValue,
                            genre = selectedGenre.apiValue.ifBlank { null },
                            language = selectedLanguage.apiValue.ifBlank { null }
                        )
                    )
                },
                modifier = Modifier
                    .size(270.dp)
                    .background(
                        color = customColors.thirdBackground,
                        shape = RoundedCornerShape(30.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.dice_3d),
                    contentDescription = stringResource(id = R.string.random_button_content_description),
                    tint = customColors.diceColor,
                    modifier = Modifier.size(200.dp)
                )
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (state.error != null) {
               Text(text = state.error!!, color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            }
            if (state.book != null) {
                BookItem(
                    book = state.book!!, onLikeClick = {
                        if (state.book!!.isLiked) {
                            vm.processCommand(RandomSearchCommand.RemoveBookFromLibrary(bookId = state.book!!.id))
                        } else {
                            vm.processCommand(RandomSearchCommand.AddBookToLibrary(state.book!!))
                        }
                    },
                    onItemClick = {
                        vm.processCommand(
                            RandomSearchCommand.GetBookDetails(
                            state.book!!,
                            onSuccess = onBookClick
                        ))
                    }
                )
            }
        }
    }
}