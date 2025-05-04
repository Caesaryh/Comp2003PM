package com.example.pmanager.ui.view

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmanager.data.AppDatabase
import com.example.pmanager.data.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Authentication ViewModel handling user registration, login, and session management.
 *
 * Manages user authentication state through a state flow pattern and coordinates with
 * Room Database for persistence. Implements basic credential validation and error handling.
 *
 * @property db Reference to the application's Room database
 */
class AuthViewModel(private val db: AppDatabase) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    /**
     * Observable current user session state.
     *
     * Emits:
     * - User object when authenticated
     * - null when logged out or session expired
     */
    val currentUser = _currentUser.asStateFlow()

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    /**
     * Authentication workflow state container.
     *
     * Tracks the state of authentication operations through a sealed class hierarchy.
     * UI components should observe this to handle loading states and error messages.
     */
    val uiState = _uiState.asStateFlow()

    /**
     * Sealed class representing authentication process states.
     *
     * States:
     * - Idle: Initial state with no active operations
     * - Loading: Authentication operation in progress
     * - Error: Operation failed with message
     * - Success: Operation completed successfully
     */
    sealed class AuthState {
        data object Idle : AuthState()
        data object Loading : AuthState()
        data class Error(val message: String) : AuthState()
        data class Success(val user: User) : AuthState()
    }

    /**
     * Retrieves the username of currently authenticated user.
     *
     * @return Current username if logged in, null otherwise
     */
    fun getCurrentUsername(): String? = _currentUser.value?.userName

    /**
     * Handles new user registration with credential validation.
     *
     * Implements:
     * 1. Password confirmation match check
     * 2. Unique username validation
     * 3. Secure credential storage
     *
     * @param username Proposed username (must be unique)
     * @param password Raw password input
     * @param confirmPassword Password confirmation input
     *
     * Security Note: Passwords should be hashed before storage - plaintext storage
     * in this implementation is for demonstration purposes only.
     */
    fun register(username: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            try {
                // Validate password match
                if (password != confirmPassword) {
                    _uiState.value = AuthState.Error("password did not match")
                    return@launch
                }

                // Check username availability
                if (db.userDao().isUsernameExists(username)) {
                    _uiState.value = AuthState.Error("User is exist")
                    return@launch
                }

                // Create and store new user
                val newUser = User(
                    userName = username,
                    password = password  // WARNING: Should store hashed value
                )

                db.userDao().registerUser(newUser)
                _currentUser.value = newUser  // Auto-login after registration
                _uiState.value = AuthState.Success(newUser)
            } catch (e: SQLiteConstraintException) {
                // Handle database constraint violations
                _uiState.value = AuthState.Error("Cannot create user: ${e.message}")
            } catch (e: Exception) {
                // General error handling
                _uiState.value = AuthState.Error("Cannot create user: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Authenticates user credentials against stored records.
     *
     * @param username User-provided username
     * @param password User-provided password
     *
     */
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            try {
                val user = db.userDao().getUserByUsername(username)
                when {
                    user == null -> {
                        _uiState.value = AuthState.Error("User is not found")
                    }
                    user.password != password -> {  // WARNING: Compare hashes in production
                        _uiState.value = AuthState.Error("Incorrect username or password")
                    }
                    else -> {
                        _currentUser.value = user // Maintain session state
                        _uiState.value = AuthState.Success(user)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = AuthState.Error("Login failed: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Terminates current user session and resets authentication state.
     */
    fun logout() {
        _currentUser.value = null
        _uiState.value = AuthState.Idle
    }

    /**
     * Updates username for currently authenticated user.
     *
     * @param newUsername Proposed new username
     *
     */
    fun updateUserInfo(newUsername: String) {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                try {
                    val updatedUser = user.copy(userName = newUsername)
                    db.userDao().updateUser(updatedUser)
                    _currentUser.value = updatedUser  // Update session state
                } catch (e: Exception) {
                    _uiState.value = AuthState.Error("Update failed: ${e.message}")
                }
            }
        }
    }
}
