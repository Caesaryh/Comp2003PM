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

class BrowseViewModel(
    private val dao: PasswordInfoDao
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val _searchResults = MutableStateFlow<List<PasswordInfo>>(emptyList())
    private val _allItems = MutableStateFlow<List<PasswordInfo>>(emptyList())

    val searchQuery = _searchQuery.asStateFlow()
    val searchResults = _searchResults.asStateFlow()
    val allItems = _allItems.asStateFlow()

    init {
        viewModelScope.launch {
            dao.getAllPasswords().collect { passwords ->
                _allItems.value = passwords
                if (_searchQuery.value.isEmpty()) {
                    _searchResults.value = passwords
                }
            }
        }

        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .collect { query ->
                    if (query.isNotEmpty()) {
                        _searchResults.value = dao.searchPasswords(query)
                    } else {
                        _searchResults.value = _allItems.value
                    }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun addPassword(passwordInfo: PasswordInfo) {
        viewModelScope.launch {
            dao.createPassword(passwordInfo)
        }
    }
    fun getPasswordById(id: Int): Flow<PasswordInfo?> = dao.getById(id)

    fun updatePassword(passwordInfo: PasswordInfo) {
        viewModelScope.launch {
            dao.updatePassword(passwordInfo)
        }
    }
}

class BrowseViewModelFactory(
    private val dao: PasswordInfoDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowseViewModel::class.java)) {
            return BrowseViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}