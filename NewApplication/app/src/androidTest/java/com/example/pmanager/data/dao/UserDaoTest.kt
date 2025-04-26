package com.example.pmanager.data.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pmanager.data.AppDatabase
import com.example.pmanager.data.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testRegisterAndRetrieveUser() = runTest {
        val testUser = User(userName = "testUser", password = "testPass")
        userDao.registerUser(testUser)


        val retrieved = userDao.getUserByUsername("testUser")
        assertNotNull(retrieved)
        assertEquals("testUser", retrieved?.userName)
        assertEquals("testPass", retrieved?.password)
    }

    @Test
    fun testDuplicateUsernameRegistration() = runTest {
        val testUser = User(userName = "duplicateUser", password = "pass123")
        userDao.registerUser(testUser)

        try {
            userDao.registerUser(testUser.copy(password = "newPass"))
            fail("Should throw exception for duplicate username")
        } catch (e: SQLiteConstraintException) {
        }
    }

    @Test
    fun testUserValidation() = runTest {
        val testUser = User(userName = "validUser", password = "correctPass")
        userDao.registerUser(testUser)

        assertTrue(userDao.validateUser("validUser", "correctPass"))

        assertFalse(userDao.validateUser("validUser", "wrongPass"))

        assertFalse(userDao.validateUser("nonExistingUser", "anyPass"))
    }

    @Test
    fun testUpdateUser() = runTest {
        val originalUser = User(userName = "updateUser", password = "oldPass")
        userDao.registerUser(originalUser)

        var updatedUser = originalUser.copy(password = "newPass")
        val tempUser:User? = userDao.getUserByUsername("updateUser")
        tempUser?.let{
            updatedUser = tempUser
        }

        updatedUser = updatedUser.copy(password = "newPass")

        userDao.updateUser(updatedUser)

        val retrieved = userDao.getUserByUsername("updateUser")
        assertEquals("newPass", retrieved?.password)
    }

    @Test
    fun testDeleteUser() = runTest {
        var testUser = User(userName = "deleteUser", password = "pass123")
        userDao.registerUser(testUser)

        val tempUser:User? = userDao.getUserByUsername("deleteUser")
        tempUser?.let{
            testUser = tempUser
        }

        userDao.deleteUser(testUser)

        assertNull(userDao.getUserByUsername("deleteUser"))
        assertFalse(userDao.isUsernameExists("deleteUser"))
    }

    @Test
    fun testUsernameExistenceCheck() = runTest {

        assertFalse(userDao.isUsernameExists("newUser"))

        userDao.registerUser(User(userName = "newUser", password = "pass"))
        assertTrue(userDao.isUsernameExists("newUser"))
    }
}
