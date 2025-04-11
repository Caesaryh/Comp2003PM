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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    onLogout: () -> Unit,
    onBack: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val currentUsername by authViewModel.currentUser.collectAsState()

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
        )
    )
}

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

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .size(120.dp)
                .padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Icon",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = username,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

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
