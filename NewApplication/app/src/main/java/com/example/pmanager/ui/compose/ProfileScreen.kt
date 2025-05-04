package com.example.pmanager.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pmanager.ui.view.AuthViewModel
import androidx.compose.runtime.getValue

/**
 * Main profile screen displaying user information and logout functionality.
 *
 * @param authViewModel ViewModel handling user authentication data
 * @param onLogout Callback invoked when user triggers logout
 * @param onBack Callback for navigation back action
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    onLogout: () -> Unit,
    onBack: () -> Unit,
) {
    // Scroll behavior for collapsing app bar
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // Collect current user data from ViewModel
    val currentUsername by authViewModel.currentUser.collectAsState()

    // Main screen scaffold layout
    Scaffold(
        topBar = {
            ProfileTopBar(
                scrollBehavior = scrollBehavior,
                onBack = onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        ProfileContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            username = currentUsername?.userName.toString(),
            onLogout = onLogout
        )
    }
}

/**
 * Custom top app bar for profile screen with scroll behavior.
 *
 * @param scrollBehavior Collapse/expand behavior for app bar
 * @param onBack Callback for back navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit
) {
    // Large top app bar implementation
    LargeTopAppBar(
        title = {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            // Back button implementation
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
        )
    )
}

/**
 * Profile screen content layout displaying user information and logout button.
 *
 * @param modifier Modifier for layout configuration
 * @param username Display name of current user
 * @param onLogout Callback for logout action
 */
@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier,
    username: String,
    onLogout: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User avatar circle
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .size(120.dp)
                .padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User avatar",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Username display
        Text(
            text = username,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Flexible spacer to push content up
        Spacer(modifier = Modifier.weight(1f))

        // Logout button with warning colors
        FilledTonalButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Text(
                text = "Logout",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}