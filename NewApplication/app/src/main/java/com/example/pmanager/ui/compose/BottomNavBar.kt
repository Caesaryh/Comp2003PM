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

/**
 * Bottom navigation bar component for main app navigation.
 *
 * Provides consistent navigation between primary app sections:
 * - Home (Password Manager)
 * - User Profile
 * - Application Settings
 *
 * @param navController Navigation controller for handling destination changes
 */
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

    // Get current route from navigation back stack
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Navigation configuration:
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true  // Preserve state when popping
                        }
                        launchSingleTop = true  // Prevent multiple instances
                        restoreState = true    // Restore previous state
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

/**
 * Data class representing a bottom navigation item.
 *
 * @property route Unique navigation route identifier
 * @property label Display text for the navigation item
 * @property icon Vector icon resource for visual representation
 *
 * Note: Consider adding `badgeCount` or `notificationIndicator` properties
 * for future extensibility with notifications/badges.
 */
data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
