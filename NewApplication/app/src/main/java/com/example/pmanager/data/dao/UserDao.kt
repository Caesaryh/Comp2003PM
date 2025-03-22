package com.example.pmanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pmanager.data.models.User

@Dao
interface UserDao {
    @Insert
    suspend fun registerUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user WHERE userName = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT EXISTS(SELECT 1 FROM user WHERE userName = :username AND password = :password)")
    suspend fun validateUser(username: String, password: String): Boolean
}