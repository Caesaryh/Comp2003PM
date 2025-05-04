package com.example.pmanager.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.pmanager.data.models.PasswordInfo

/**
 * Screen displaying detailed information about a password entry.
 *
 * @param passwordInfo Password data object to display
 * @param onEditClick Callback when edit action is requested
 * @param modifier Layout modifier for the root component
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    passwordInfo: PasswordInfo,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onEditClick,
                icon = { Icon(Icons.Default.Edit, "Edit") },
                text = { Text("Edit") }
            )
        }
    ) { innerPadding ->
        PasswordDetailContent(
            passwordInfo = passwordInfo,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/**
 * Main content layout for password detail view.
 *
 * @param passwordInfo Password data object to display
 * @param modifier Layout modifier for the content area
 */
@Composable
private fun PasswordDetailContent(
    passwordInfo: PasswordInfo,
    modifier: Modifier = Modifier
) {
    var showPassword by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            HeaderSection(
                account = passwordInfo.account,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            SecuritySection(
                password = passwordInfo.password,
                showPassword = showPassword,
                onToggleVisibility = { showPassword = !showPassword }
            )
        }

        item {
            NotesSection(notes = passwordInfo.commits)
        }
    }
}

/**
 * Header section displaying account information.
 *
 * @param account Account name to display
 * @param modifier Layout modifier for the section
 */
@Composable
private fun HeaderSection(
    account: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Account Info",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = account ?: "Anno account",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


/**
 * Security section containing password visibility controls.
 *
 * @param password Password value to display
 * @param showPassword Current visibility state
 * @param onToggleVisibility Callback for toggle button click
 */
@Composable
private fun SecuritySection(
    password: String?,
    showPassword: Boolean,
    onToggleVisibility: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Account",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = onToggleVisibility) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = "Change visibility"
                    )
                }
            }

            Text(
                text = if (showPassword) password ?: "No password" else "••••••••",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily.Monospace
                )
            )
        }
    }
}


/**
 * Notes section displaying additional comments.
 *
 * @param notes Comment text to display
 */
@Composable
private fun NotesSection(notes: String?) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Comment",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = notes ?: "No comment",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}