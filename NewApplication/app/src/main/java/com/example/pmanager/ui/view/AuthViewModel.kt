package com.example.pmanager.ui.view

import android.database.sqlite.SQLiteConstraintException

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmanager.data.AppDatabase
import com.example.pmanager.data.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val db: AppDatabase) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()


    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState = _uiState.asStateFlow()

    sealed class AuthState {
        data object Idle : AuthState()
        data object Loading : AuthState()
        data class Error(val message: String) : AuthState()
        data class Success(val user: User) : AuthState()
    }


    fun getCurrentUsername(): String? = _currentUser.value?.userName


    fun register(username: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            try {

                if (password != confirmPassword) {
                    _uiState.value = AuthState.Error("password did not match")
                    return@launch
                }


                if (db.userDao().isUsernameExists(username)) {
                    _uiState.value = AuthState.Error("User is exist")
                    return@launch
                }


                val newUser = User(
                    userName = username,
                    password = password
                )

                db.userDao().registerUser(newUser)
                _currentUser.value = newUser
                _uiState.value = AuthState.Success(newUser)
            } catch (e: SQLiteConstraintException) {
                _uiState.value = AuthState.Error("Cannot create user: ${e.message}")
            } catch (e: Exception) {
                _uiState.value = AuthState.Error("Cannot create user: ${e.localizedMessage}")
            }
        }
    }


    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            try {
                val user = db.userDao().getUserByUsername(username)
                when {
                    user == null -> {
                        _uiState.value = AuthState.Error("User is not found")
                    }
                    user.password != password -> {
                        _uiState.value = AuthState.Error("Incorrect username or password")
                    }
                    else -> {
                        _currentUser.value = user // 保存登录用户
                        _uiState.value = AuthState.Success(user)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = AuthState.Error("Login failed: ${e.localizedMessage}")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _uiState.value = AuthState.Idle
    }


    fun updateUserInfo(newUsername: String) {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                try {
                    val updatedUser = user.copy(userName = newUsername)
                    db.userDao().updateUser(updatedUser)
                    _currentUser.value = updatedUser
                } catch (e: Exception) {
                    _uiState.value = AuthState.Error("Update failed: ${e.message}")
                }
            }
        }
    }
}
