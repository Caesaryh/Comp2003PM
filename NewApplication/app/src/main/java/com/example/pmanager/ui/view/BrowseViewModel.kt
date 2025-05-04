package com.example.pmanager.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pmanager.data.dao.PasswordInfoDao
import com.example.pmanager.data.models.PasswordInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

/**
 * ViewModel for managing password entries browsing and searching.
 *
 * Handles:
 * - Password entry CRUD operations
 * - Real-time search with debouncing
 * - Data synchronization between UI and database
 *
 * @property dao Data access object for password entries
 */
class BrowseViewModel(
    private val dao: PasswordInfoDao
) : ViewModel() {
    // region Search State Management
    private val _searchQuery = MutableStateFlow("")
    /**
     * Observable search query input with debounce support.
     * Updates trigger automatic search results refresh.
     */
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<PasswordInfo>>(emptyList())
    /**
     * Current filtered password entries matching search criteria.
     * Automatically updated based on query changes.
     */
    val searchResults = _searchResults.asStateFlow()

    private val _allItems = MutableStateFlow<List<PasswordInfo>>(emptyList())
    /**
     * Complete collection of password entries for offline search.
     * Maintained as a cache to optimize search performance.
     */
    val allItems = _allItems.asStateFlow()
    // endregion

    init {
        // Initialize data synchronization
        viewModelScope.launch {
            dao.getAllPasswords().collect { passwords ->
                _allItems.value = passwords
                // Refresh search results when base data changes
                if (_searchQuery.value.isEmpty()) {
                    _searchResults.value = passwords
                }
            }
        }

        // Configure search debouncing
        viewModelScope.launch {
            _searchQuery
                .debounce(300)  // Wait 300ms after last input
                .collect { query ->
                    _searchResults.value = when {
                        query.isNotEmpty() -> dao.searchPasswords(query)
                        else -> _allItems.value  // Fallback to full list
                    }
                }
        }
    }

    /**
     * Updates current search query and triggers results refresh.
     *
     * @param query Search input string (empty string clears filter)
     */
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    /**
     * Inserts new password entry into database.
     *
     * @param passwordInfo Complete password entry to create
     */
    fun addPassword(passwordInfo: PasswordInfo) {
        viewModelScope.launch {
            dao.createPassword(passwordInfo)
        }
    }

    /**
     * Retrieves single password entry by ID.
     *
     * @param id Database ID of the password entry
     * @return Flow emitting PasswordInfo or null if not found
     */
    fun getPasswordById(id: Int): Flow<PasswordInfo?> = dao.getById(id)

    /**
     * Updates existing password entry in database.
     *
     * @param passwordInfo Modified password entry with existing ID
     */
    fun updatePassword(passwordInfo: PasswordInfo) {
        viewModelScope.launch {
            dao.updatePassword(passwordInfo)
        }
    }
}

/**
 * Factory for creating [BrowseViewModel] instances with dependency injection.
 *
 * @property dao PasswordInfoDao instance to provide to ViewModel
 */
class BrowseViewModelFactory(
    private val dao: PasswordInfoDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BrowseViewModel::class.java) ->
                BrowseViewModel(dao) as T
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
