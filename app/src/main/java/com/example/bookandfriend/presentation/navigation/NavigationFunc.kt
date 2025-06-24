package com.example.bookandfriend.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookandfriend.presentation.screens.global_search.MainScreen
import com.example.bookandfriend.presentation.screens.library.LibraryScreen
import com.example.bookandfriend.presentation.screens.random_search.RandomSearchScreen
import com.example.bookandfriend.presentation.screens.settings.SettingsScreen

@Composable
fun NavigationFunc() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "MainScreen"
    ) {
        composable("MainScreen") {
            MainScreen(navController = navController)
        }

        composable("RandomSearch") {
            RandomSearchScreen(navController = navController)
        }

        composable("Library") {
            LibraryScreen(navController = navController)
        }

        composable("Settings") {
            SettingsScreen(navController = navController)
        }

        composable("BookInfo") {

        }
    }
}