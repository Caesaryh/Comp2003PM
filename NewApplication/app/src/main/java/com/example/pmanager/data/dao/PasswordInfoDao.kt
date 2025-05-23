package com.example.pmanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pmanager.data.models.PasswordInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordInfoDao {
    @Insert
    suspend fun createPassword(passwordInfo: PasswordInfo):Long

    @Update
    suspend fun updatePassword(passwordInfo: PasswordInfo)

    @Delete
    suspend fun deletePassword(passwordInfo: PasswordInfo)

    @Query("SELECT * FROM password_info ORDER BY id DESC")
    fun getAllPasswords(): Flow<List<PasswordInfo>>

    @Query("SELECT * FROM password_info WHERE id = :passwordId")
    suspend fun getPasswordById(passwordId: Int): PasswordInfo?

    @Query("SELECT * FROM password_info WHERE account LIKE '%' || :query || '%'")
    suspend fun searchPasswords(query: String): List<PasswordInfo>

    @Query("SELECT * FROM password_info WHERE id = :passwordId")
    fun getPasswordByIdFlow(passwordId: Int): Flow<PasswordInfo?>

    @Query("SELECT * FROM password_info WHERE id = :id")
    fun getById(id: Int): Flow<PasswordInfo?>

    @Query("SELECT * FROM password_info WHERE userId = :userId ORDER BY id DESC")
    fun getPasswordsByUserId(userId: Int): Flow<List<PasswordInfo>>

    @Query("""
        SELECT * FROM password_info 
        WHERE userId = :userId 
        AND (account LIKE '%' || :query || '%' OR commits LIKE '%' || :query || '%')
    """)
    suspend fun searchUserPasswords(userId: Int, query: String): List<PasswordInfo>

    @Query("DELETE FROM password_info WHERE id = :passwordId")
    suspend fun deleteById(passwordId: Int)
}