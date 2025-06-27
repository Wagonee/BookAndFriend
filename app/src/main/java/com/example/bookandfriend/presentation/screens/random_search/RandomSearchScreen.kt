package com.example.bookandfriend.presentation.screens.random_search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookandfriend.R
import com.example.bookandfriend.presentation.components.BookItem
import com.example.bookandfriend.presentation.navigation.BottomBar
import com.example.bookandfriend.presentation.ui.theme.LocalCustomColors

private data class SelectableItem(val displayName: String, val apiValue: String)
private data class CenturyItem(val displayName: String, val apiValue: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomSearchScreen(
    navController: NavController,
    vm: RandomSearchVM = hiltViewModel()
) {
    val customColors = LocalCustomColors.current

    val genres = remember {
        listOf(
            SelectableItem("Any genre", ""),
            SelectableItem("Fantasy", "fantasy"),
            SelectableItem("Science Fiction", "science_fiction"),
            SelectableItem("Romance", "romance"),
            SelectableItem("Mystery", "mystery"),
            SelectableItem("Thriller", "thriller"),
            SelectableItem("Horror", "horror"),
            SelectableItem("Historical Fiction", "historical_fiction"),
            SelectableItem("Biography", "biography"),
            SelectableItem("Classic", "classic"),
            SelectableItem("Novel", "novel")
        )
    }

    val languages = remember {
        listOf(
            SelectableItem("Any language", ""),
            SelectableItem("English", "eng"),
            SelectableItem("Russian", "rus"),
            SelectableItem("French", "fra"),
            SelectableItem("German", "ger"),
            SelectableItem("Spanish", "spa"),
            SelectableItem("Italian", "ita")
        )
    }

    val centuries = remember {
        (1..21).map { CenturyItem("$it cent.", it) }
            .toMutableList().apply {
                add(0, CenturyItem("Any cent.", 0))
            }
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
                title = { Text(text = "Random book", textAlign = TextAlign.Center) },
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
            Text(text = "For searching choose at least one parameter.", textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.Thin)
            ExposedDropdownMenuBox(
                expanded = expandedGenre,
                onExpandedChange = { expandedGenre = !expandedGenre },
                modifier = Modifier
                    .width(270.dp)
                    .clip(
                        if (expandedGenre) {
                            RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        } else {
                            RoundedCornerShape(30.dp)
                        }
                    )
            ) {
                TextField(
                    value = selectedGenre.displayName,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedGenre) },
                    modifier = Modifier
                        .menuAnchor()
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
                    containerColor = customColors.secondBackground,
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExposedDropdownMenuBox(
                    expanded = expandedLanguage,
                    onExpandedChange = { expandedLanguage = !expandedLanguage },
                    modifier = Modifier
                        .width(120.dp)
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
                        .width(120.dp)
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
                    contentDescription = "Random",
                    tint = customColors.diceColor,
                    modifier = Modifier.size(200.dp)
                )
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
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
                    onItemClick = { }
                )
            }
        }

    }
}
