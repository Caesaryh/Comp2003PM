package com.example.pmanager.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Main application layout container that handles navigation-aware UI structure.
 *
 * @param navController Navigation controller for route observation
 * @param content Main content composable that receives padding values
 */
@Composable
fun MainLayout(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    // Observe current navigation route
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        // Show bottom navigation only for main screens
        bottomBar = {
            if (currentRoute in setOf("main", "profile", "settings")) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        // Apply scaffold padding to content
        Box(modifier = Modifier.padding(innerPadding)) {
            content(innerPadding)
        }
    }
}