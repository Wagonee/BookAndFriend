package com.example.bookandfriend.presentation.navigation

import androidx.annotation.DrawableRes
import com.example.bookandfriend.R

sealed class BottomNavItem(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int
) {
    object Main : BottomNavItem("MainScreen", "Search", R.drawable.loupe)
    object Random : BottomNavItem("RandomSearch", "Random", R.drawable.dice)
    object Library : BottomNavItem("Library", "Library", R.drawable.heart)
    object Settings : BottomNavItem("Settings", "Settings", R.drawable.settings)
}