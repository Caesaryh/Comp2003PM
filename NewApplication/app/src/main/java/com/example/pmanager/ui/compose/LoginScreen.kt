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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.login(username, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = username.isNotEmpty() && password.isNotEmpty()
        ) {
            Text("Sign In")
        }

        TextButton(
            onClick = { navController.navigate("register") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Don't have an account? Register here")
        }

        when (val state = uiState) {
            is AuthViewModel.AuthState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            is AuthViewModel.AuthState.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
            AuthViewModel.AuthState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
            else -> {}
        }
    }
}
