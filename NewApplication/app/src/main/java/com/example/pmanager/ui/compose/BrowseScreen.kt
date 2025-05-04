package com.example.pmanager.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pmanager.R
import com.example.pmanager.data.models.PasswordInfo
import com.example.pmanager.ui.view.BrowseViewModel


/**
 * Main screen displaying password entries with search and creation capabilities.
 *
 * @param viewModel Provides access to password data and search functionality
 * @param onItemClick Callback for password item selection
 * @param navController Handles navigation between app screens
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseScreen(
    viewModel: BrowseViewModel,
    onItemClick: (PasswordInfo) -> Unit,
    navController: NavController
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val items by viewModel.searchResults.collectAsState()

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create") },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(Icons.Default.Add, "Add password")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            PasswordList(
                items = items,
                onItemClick = onItemClick
            )
        }
    }
}


/**
 * Displays a scrollable list of password entries.
 *
 * @param items List of PasswordInfo objects to display
 * @param onItemClick Callback for item selection events
 */
@Composable
private fun PasswordList(
    items: List<PasswordInfo>,
    onItemClick: (PasswordInfo) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (items.isEmpty()) {
            EmptyStatePrompt()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(items) { item ->
                    PasswordListItem(
                        item = item,
                        onClick = { onItemClick(item) }
                    )
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
    }
}


/**
 * Empty state UI component displayed when no passwords exist.
 */
@Composable
private fun EmptyStatePrompt() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.LockOpen,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "It's seems that you haven't store your password yet.",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = "Press the button to add a new password",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


/**
 * Individual password list item card component.
 *
 * @param item PasswordInfo object to display
 * @param onClick Callback for card selection
 */
@Composable
private fun PasswordListItem(
    item: PasswordInfo,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(3f)) {
                Text(
                    text = item.account ?: "Anno Account",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.commits ?: "No comments...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            PasswordPreview(
                password = item.password,
                modifier = Modifier.weight(2f)
            )
        }
    }
}

/**
 * Interactive password preview component with show/hide functionality.
 *
 * @param password The actual password value to preview
 * @param modifier Layout modifier for styling
 */
@Composable
private fun PasswordPreview(
    password: String?,
    modifier: Modifier = Modifier
) {
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = if (showPassword) password?.take(12) ?: "No password" else "••••••••",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

        TextButton(
            onClick = { showPassword = !showPassword },
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(if (showPassword) "Hide" else "Show")
        }
    }
}

/**
 * Search bar component with expandable advanced options.
 *
 * @param query Current search query text
 * @param onQueryChange Callback for query text changes
 * @param modifier Layout modifier for styling
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var active by remember { mutableStateOf(false) }

    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = { active = false },
        active = active,
        onActiveChange = { active = it },
        modifier = modifier,
        placeholder = { Text("Search account or comment") },
        leadingIcon = { Icon(Icons.Default.Search, "Search") }
    ) {

        AdvancedSearchOptions()
    }
}

/**
 * Container for advanced search filters (currently WIP).
 */
@Composable
private fun AdvancedSearchOptions() {

    var includePassword by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Options",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = includePassword,
                onCheckedChange = { includePassword = it }
            )
            Text(
                text = "Include password",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}