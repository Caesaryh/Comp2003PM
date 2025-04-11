package com.example.pmanager.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pmanager.data.dao.PasswordInfoDao
import com.example.pmanager.data.models.PasswordInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PasswordViewModel(private val passwordDao: PasswordInfoDao) : ViewModel() {


    fun getPasswordById(id: Int): Flow<PasswordInfo?> {
        return passwordDao.getPasswordByIdFlow(id)
    }

    fun updatePassword(password: PasswordInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            passwordDao.updatePassword(password)
        }
    }
}


