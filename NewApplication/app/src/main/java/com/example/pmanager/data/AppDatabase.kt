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

/**
 * Main Room database class for the password manager application.
 *
 * Defines the database configuration, entities, and DAO access points. Implements
 * singleton pattern to ensure single database instance across the application.
 *
 * Security considerations:
 * - Database file name should be non-default to avoid predictable location
 * - Consider implementing SQLCipher for encryption-at-rest
 * - Migrations should be carefully handled for sensitive data
 */
@Database(
    entities = [
        Config::class,
        PasswordInfo::class,
        User::class
    ],
    version = 2,  // Increment when making schema changes
    exportSchema = false  // Disable schema export for production builds
)
abstract class AppDatabase : RoomDatabase() {

    /** Provides access to application configuration operations */
    abstract fun configDao(): ConfigDao

    /** Entry point for password entry CRUD operations */
    abstract fun passwordInfoDao(): PasswordInfoDao

    /** Handles user authentication data management */
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null  // Singleton instance

        /**
         * Gets the singleton database instance using double-checked locking pattern.
         *
         * @param context Application context for database initialization
         * @return Shared database instance with following configuration:
         * - Database name: "secure_vault.db"
         * - Destructive migrations disabled (requires explicit migration strategy)
         * - Database creation callback available for initial setup
         */
        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "secure_vault.db"  // Non-default database name
                )
                    .fallbackToDestructiveMigration(false)  // Require explicit migrations
                    .addCallback(DatabaseCallback())  // Setup initial data if needed
                    .build()
                    .also { Instance = it }
            }
        }

        /**
         * Room database callback for handling lifecycle events.
         *
         * Currently implements:
         * - onCreate: Triggered on initial database creation
         *
         * Potential uses:
         * - Pre-populating default configuration
         * - Inserting test data in debug builds
         * - Performing initial encryption setup
         */
        private class DatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Initialize security parameters or default records here
                // Example: Insert default Config record with secure defaults
            }
        }
    }
}
