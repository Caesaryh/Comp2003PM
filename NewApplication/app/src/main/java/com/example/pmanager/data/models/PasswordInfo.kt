package com.example.pmanager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_info")
data class PasswordInfo(
    @PrimaryKey val id : Int,

    val account : String?,
    val password : String?,
    val commits : String?
)
