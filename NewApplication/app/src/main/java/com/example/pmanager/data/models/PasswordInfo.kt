package com.example.pmanager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_info")
data class PasswordInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val account: String?,
    val password: String?,
    val commits: String?,
    val createdAt: Long = System.currentTimeMillis()
)