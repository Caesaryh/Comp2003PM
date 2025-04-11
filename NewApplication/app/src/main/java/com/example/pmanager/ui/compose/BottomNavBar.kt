package com.example.pmanager.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(
            route = "main",
            label = "Home",
            icon = Icons.Default.Lock
        ),
        BottomNavItem(
            route = "profile",
            label = "Profile",
            icon = Icons.Default.Person
        ),
        BottomNavItem(
            route = "settings",
            label = "Setting",
            icon = Icons.Default.Settings
        )
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
