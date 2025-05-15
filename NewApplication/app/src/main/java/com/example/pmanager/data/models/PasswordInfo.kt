package com.example.pmanager.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Represents a stored password entry in the vault.
 *
 * Entity stores sensitive credential information with auto-generated unique ID and
 * audit timestamps. All sensitive fields should be encrypted before storage.
 *
 * @property id Auto-generated primary key for database operations
 * @property account Service name or username (optional for service-only entries)
 * @property password Encrypted password value (base64 or encrypted string)
 * @property commits Additional notes or metadata about the entry
 * @property createdAt Creation timestamp (auto-populated)
 */
@Entity(
    tableName = "password_info",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PasswordInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val account: String?,
    val password: String?,
    val commits: String?,
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "userId", index = true)
    val userId: Int
)