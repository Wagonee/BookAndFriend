package com.example.bookandfriend.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookandfriend.presentation.screens.global_search.MainScreen

@Composable
fun NavigationFunc() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "MainScreen"
    ) {
        composable("MainScreen") {
            MainScreen(navController)
        }

        composable("RandomSearch") {

        }

        composable("Library") {

        }

        composable("Settings") {

        }

        composable("BookInfo") {

        }
    }
}