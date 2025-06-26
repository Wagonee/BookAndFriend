package com.example.bookandfriend.presentation.screens.random_search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bookandfriend.R
import com.example.bookandfriend.presentation.navigation.BottomBar
import com.example.bookandfriend.presentation.ui.theme.BookAndFriendTheme
import com.example.bookandfriend.presentation.ui.theme.CustomColors
import com.example.bookandfriend.presentation.ui.theme.LocalCustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomSearchScreen(
    navController: NavController
) {
    val customColors = LocalCustomColors.current

    val genres = listOf("Novel", "Fantasy", "Romance", "Mystery", "Biography")
    val languages = listOf("ENG", "RUS", "FR", "DE")
    val centuries = (1..21).map{ "$it cent." }

    var selectedGenre by remember { mutableStateOf("Genre")}
    var selectedLanguage by remember { mutableStateOf("Lang.") }
    var selectedDate by remember { mutableStateOf("Date") }

    var expandedGenre by remember { mutableStateOf(false) }
    var expandedLanguage by remember { mutableStateOf(false) }
    var expandedDate by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Random book",
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
            ExposedDropdownMenuBox(
                expanded = expandedGenre,
                onExpandedChange = {expandedGenre = !expandedGenre},
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
                    value = selectedGenre,
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
                            text = { Text(
                                text = genre,
                                color = customColors.textColor
                            ) },
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
                        value = selectedLanguage,
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
                                text = { Text(
                                    text = lang,
                                    color = customColors.textColor
                                ) },
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
                        value = selectedDate,
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
                            .height(200.dp),
                        containerColor = customColors.secondBackground
                    ) {
                        centuries.forEach { date ->
                            DropdownMenuItem(
                                text = { Text(
                                    text = date,
                                    color = customColors.textColor
                                ) },
                                onClick = {
                                    selectedDate = date
                                    expandedDate = false
                                }
                            )
                        }
                    }
                }
            }

            // TODO: реализовать кнопку
            IconButton(
                onClick = {  },
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
        }
    }
}