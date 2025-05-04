package com.example.pmanager.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pmanager.ui.view.AuthViewModel

/**
 * Registration screen handling user account creation.
 *
 * @param navController Navigation controller for screen transitions
 * @param viewModel ViewModel handling registration logic and state
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    // User input states
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Observe authentication state changes
    val uiState by viewModel.uiState.collectAsState()

    // Main content column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Screen title
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Username input field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password input field with hidden text
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password confirmation field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Registration button with basic input validation
        Button(
            onClick = {
                viewModel.register(username, password, confirmPassword)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = username.isNotEmpty() &&
                    password.isNotEmpty() &&
                    confirmPassword.isNotEmpty()
        ) {
            Text("Create Account")
        }

        // Alternative action for existing users
        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Already have an account? Sign in")
        }

        // Handle different authentication states
        when (val state = uiState) {
            is AuthViewModel.AuthState.Error -> {
                // Display error message
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            is AuthViewModel.AuthState.Success -> {
                // Navigate back on successful registration
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }
            AuthViewModel.AuthState.Loading -> {
                // Show loading indicator
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
            else -> {}
        }
    }
}