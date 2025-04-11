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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    passwordInfo: PasswordInfo,
    onSave: (PasswordInfo) -> Unit,
    onCancel: () -> Unit
) {
    var editedAccount by remember { mutableStateOf(passwordInfo.account ?: "") }
    var editedPassword by remember { mutableStateOf(passwordInfo.password ?: "") }
    var editedNotes by remember { mutableStateOf(passwordInfo.commits ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit") })
        },
        bottomBar = {
            EditBottomBar(
                onSave = {
                    onSave(
                        passwordInfo.copy(
                            account = editedAccount,
                            password = editedPassword,
                            commits = editedNotes
                        )
                    )
                },
                onCancel = onCancel
            )
        }
    ) { innerPadding ->
        EditForm(
            editedAccount = editedAccount,
            onAccountChange = { editedAccount = it },
            editedPassword = editedPassword,
            onPasswordChange = { editedPassword = it },
            editedNotes = editedNotes,
            onNotesChange = { editedNotes = it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun EditForm(
    editedAccount: String,
    onAccountChange: (String) -> Unit,
    editedPassword: String,
    onPasswordChange: (String) -> Unit,
    editedNotes: String,
    onNotesChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = editedAccount,
            onValueChange = onAccountChange,
            label = { Text("Account") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = editedPassword,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = editedNotes,
            onValueChange = onNotesChange,
            label = { Text("Comment") },
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 96.dp)
        )
    }
}

@Composable
private fun EditBottomBar(
    onSave: () -> Unit,
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
        Button(onClick = onSave) {
            Text("Save")
        }
    }
}
