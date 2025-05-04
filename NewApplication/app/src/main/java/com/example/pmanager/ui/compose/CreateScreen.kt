package com.example.pmanager.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.pmanager.data.models.PasswordInfo


/**
 * Screen for creating new password entries.
 *
 * @param onCreate Callback when new password entry is submitted
 * @param onCancel Callback when creation is cancelled
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    onCreate:  (PasswordInfo) -> Unit,
    onCancel: () -> Unit
) {
    var newAccount by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newNotes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("New password") })
        },
        bottomBar = {
            CreateBottomBar(
                onCreate = {
                    onCreate(
                        PasswordInfo(
                            account = newAccount,
                            password = newPassword,
                            commits = newNotes,
                            id = 0
                        )
                    )
                },
                onCancel = onCancel
            )
        }
    ) { innerPadding ->
        CreateForm(
            newAccount = newAccount,
            onAccountChange = { newAccount = it },
            newPassword = newPassword,
            onPasswordChange = { newPassword = it },
            newNotes = newNotes,
            onNotesChange = { newNotes = it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/**
 * Form component for password entry creation.
 *
 * @param newAccount Current account name input
 * @param onAccountChange Account name change callback
 * @param newPassword Current password input
 * @param onPasswordChange Password change callback
 * @param newNotes Current notes input
 * @param onNotesChange Notes change callback
 * @param modifier Layout modifier
 */
@Composable
private fun CreateForm(
    newAccount: String,
    onAccountChange: (String) -> Unit,
    newPassword: String,
    onPasswordChange: (String) -> Unit,
    newNotes: String,
    onNotesChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = newAccount,
            onValueChange = onAccountChange,
            label = { Text("Account") },
            modifier = Modifier.fillMaxWidth(),
            isError = newAccount.isBlank()
        )

        OutlinedTextField(
            value = newPassword,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = newPassword.isBlank()
        )

        OutlinedTextField(
            value = newNotes,
            onValueChange = onNotesChange,
            label = { Text("Comment") },
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 96.dp)
        )
    }
}

/**
 * Bottom action bar with creation controls.
 *
 * @param onCreate Callback when create button is clicked
 * @param onCancel Callback when cancel button is clicked
 */
@Composable
private fun CreateBottomBar(
    onCreate: () -> Unit,
    onCancel: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text("Cancel")
        }
        Button(
            onClick = onCreate,
        ) {
            Text("Create")
        }
    }
}
