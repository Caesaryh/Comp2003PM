package com.example.pmanager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id : Int,

    val userName : String?,
    val password : String?
)
