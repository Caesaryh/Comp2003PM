package com.example.pmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pmanager.data.dao.ConfigDao
import com.example.pmanager.data.dao.PasswordInfoDao
import com.example.pmanager.data.dao.UserDao
import com.example.pmanager.data.models.PasswordInfo
import com.example.pmanager.data.models.Config
import com.example.pmanager.data.models.User


@Database(
    entities = [
        Config::class,
        PasswordInfo::class,
        User::class
    ],
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun configDao(): ConfigDao
    abstract fun passwordInfoDao(): PasswordInfoDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "secure_vault.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback())
                    .build()
                    .also { Instance = it }
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // here are some logical after create database.
            }
        }
    }
}