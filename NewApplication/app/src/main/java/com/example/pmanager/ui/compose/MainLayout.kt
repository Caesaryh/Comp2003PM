package com.example.pmanager.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MainLayout(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in setOf("main", "profile", "settings")) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content(innerPadding)
        }
    }
}
