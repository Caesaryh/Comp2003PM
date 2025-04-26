package com.example.pmanager.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pmanager.data.AppDatabase
import com.example.pmanager.data.models.PasswordInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PasswordInfoDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: PasswordInfoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.passwordInfoDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun createAndRetrievePassword() = runTest {
        var testItem = createTestPasswordInfo(account = "test@example.com")


        var id = dao.createPassword(testItem).toInt()

        testItem = testItem.copy(id = id)

        val retrieved = dao.getPasswordById(id)
        assertEquals(testItem, retrieved)
    }

    @Test
    fun updatePassword() = runTest {

        val original = createTestPasswordInfo()

        var id = dao.createPassword(original).toInt()

        println(id)


        val updated = original.copy(id = id,password = "new_secure_password")
        dao.updatePassword(updated)


        val result = dao.getPasswordById(id)
        assertEquals(updated.password, result?.password)
    }

    @Test
    fun deletePassword() = runTest {

        val testItem = createTestPasswordInfo().apply { dao.createPassword(this) }


        dao.deletePassword(testItem)


        val result = dao.getPasswordById(testItem.id)
        assertNull(result)
    }

    @Test
    fun searchPasswords() = runTest {

        listOf("google", "github", "gitlab").forEach {
            dao.createPassword(createTestPasswordInfo(account = it))
        }


        val results = dao.searchPasswords("git")


        assertEquals(2, results.size)
        assertTrue(results.any { it.account == "github" })
        assertTrue(results.any { it.account == "gitlab" })
    }


    private fun createTestPasswordInfo(
        id: Int = 0,
        account: String = "default_account",
        password: String = "default_password",
        commits: String = "initial commit"
    ) = PasswordInfo(
        id = id,
        account = account,
        password = password,
        commits = commits
    )
}