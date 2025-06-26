package com.example.bookandfriend.presentation.navigation

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bookandfriend.presentation.ui.theme.LocalCustomColors

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Main,
        BottomNavItem.Random,
        BottomNavItem.Library,
        BottomNavItem.Settings
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = LocalCustomColors.current.background,
        modifier = Modifier.shadow(
            elevation = 16.dp,
            shape = RectangleShape,
            ambientColor = LocalCustomColors.current.shadowColor,
            spotColor = LocalCustomColors.current.shadowColor
        )
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.requiredSize(35.dp)
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LocalCustomColors.current.textColor,
                    unselectedIconColor = LocalCustomColors.current.textColor,
                    indicatorColor = LocalCustomColors.current.background
                )
            )
        }
    }
}