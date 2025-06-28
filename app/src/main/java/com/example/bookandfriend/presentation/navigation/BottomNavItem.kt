package com.example.bookandfriend.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.bookandfriend.R

sealed class BottomNavItem(
    val route: String,
    @StringRes val labelResId: Int,
    @DrawableRes val icon: Int
) {
    object Main : BottomNavItem("MainScreen", R.string.bottom_nav_search, R.drawable.loupe)
    object Random : BottomNavItem("RandomSearch", R.string.bottom_nav_random, R.drawable.dice)
    object Library : BottomNavItem("Library", R.string.bottom_nav_library, R.drawable.heart)
    object Settings : BottomNavItem("Settings", R.string.bottom_nav_settings, R.drawable.settings)
}