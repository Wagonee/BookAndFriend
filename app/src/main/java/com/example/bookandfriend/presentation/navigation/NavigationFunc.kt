package com.example.bookandfriend.presentation.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookandfriend.domain.model.Book
import com.example.bookandfriend.presentation.screens.book_info.BookInfoScreen
import com.example.bookandfriend.presentation.screens.global_search.MainScreen
import com.example.bookandfriend.presentation.screens.library.LibraryScreen
import com.example.bookandfriend.presentation.screens.random_search.RandomSearchScreen
import com.example.bookandfriend.presentation.screens.settings.SettingsScreen
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NavigationFunc() {
    val navController = rememberNavController()

    val onBookClick: (Book) -> Unit = { book ->
        val json = Gson().toJson(book)
        val encodedJson = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
        navController.navigate("BookInfo/$encodedJson")
    }

    Surface {
        NavHost(
            navController = navController,
            startDestination = "MainScreen"
        ) {
            composable("MainScreen") {
                MainScreen(
                    navController = navController,
                    onBookClick = onBookClick
                )
            }

            composable("RandomSearch") {
                RandomSearchScreen(
                    navController = navController,
                    onBookClick = onBookClick
                )
            }

            composable("Library") {
                LibraryScreen(
                    navController = navController,
                    onBookClick = onBookClick
                )
            }

            composable("Settings") {
                SettingsScreen(navController = navController)
            }

            composable(
                route = "BookInfo/{bookJson}",
                arguments = listOf(navArgument("bookJson") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val bookJson = backStackEntry.arguments?.getString("bookJson") ?: return@composable
                val decodedJson = URLDecoder.decode(bookJson ?: "", StandardCharsets.UTF_8.toString())
                val book = Gson().fromJson(decodedJson, Book::class.java)

                BookInfoScreen(navController, book)
            }
        }
    }
}