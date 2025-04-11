package com.example.pmanager.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
    indices = [Index(value = ["userName"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // 自动生成的ID

    @ColumnInfo(name = "userName")
    val userName: String,

    @ColumnInfo(name = "password")
    val password: String
)
