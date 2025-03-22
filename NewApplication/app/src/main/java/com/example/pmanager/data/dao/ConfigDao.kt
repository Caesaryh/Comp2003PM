package com.example.pmanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pmanager.data.models.Config

@Dao
public interface ConfigDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun persist(config: Config)

    @Query("SELECT * FROM config WHERE configId = 1 LIMIT 1")
    suspend fun getConfiguration(): Config?

    @Query("UPDATE config SET size = :newSize WHERE configId = 1")
    suspend fun updateSize(newSize: Int)

    @Transaction
    open suspend fun updateWithTimestamp(newSize: Int) {
        val existing = getConfiguration() ?: return
        persist(existing.copy(size = newSize))
    }

    @Query("DELETE FROM config WHERE configId = 1")
    suspend fun clear()

    @Query("SELECT EXISTS(SELECT 1 FROM config WHERE configId = 1)")
    suspend fun exists(): Boolean
}