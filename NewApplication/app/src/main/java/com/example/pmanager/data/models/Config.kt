package com.example.pmanager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "config")
data class Config(
    @PrimaryKey(autoGenerate = false)
    val configId: Int = 1,  // Fixed ID ensures single configuration record
    val size: Int,
    val lastModified: Long = System.currentTimeMillis()
)
