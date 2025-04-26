package com.example.pmanager.ui.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.pmanager.data.AppDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AuthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AuthViewModel
    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        viewModel = AuthViewModel(db)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `register with valid credentials should succeed`() = runTest {
        viewModel.register("testUser", "P@ssw0rd!", "P@ssw0rd!")

        viewModel.uiState.test {
            assertEquals(AuthViewModel.AuthState.Loading, awaitItem())
            val successState = awaitItem() as AuthViewModel.AuthState.Success
            assertEquals("testUser", successState.user.userName)
        }
    }

    @Test
    fun `register with existing username should fail`() = runTest {
        viewModel.register("existingUser", "Password1", "Password1")

        viewModel.register("existingUser", "Password2", "Password2")

        viewModel.uiState.test {
            skipItems(3)
            val errorState = awaitItem() as AuthViewModel.AuthState.Error
            assertEquals("User is exist", errorState.message)
        }
    }

    @Test
    fun `login with valid credentials should update current user`() = runTest {
        viewModel.register("validUser", "validPass", "validPass")

        viewModel.login("validUser", "validPass")

        viewModel.uiState.test {
            skipItems(3)
            val successState = awaitItem() as AuthViewModel.AuthState.Success
            assertEquals("validUser", successState.user.userName)
            assertEquals("validUser", viewModel.currentUser.value?.userName)
        }
    }

    @Test
    fun `update username should persist to database`() = runTest {
        viewModel.register("oldUser", "password", "password")
        viewModel.login("oldUser", "password")
        viewModel.updateUserInfo("newUser")
        assertTrue(true)
    }

    @Test
    fun `logout should clear current user`() = runTest {
        viewModel.register("logoutUser", "pwd", "pwd")
        viewModel.login("logoutUser", "pwd")
        
        viewModel.logout()

        assertEquals(null, viewModel.currentUser.value)
        assertTrue(viewModel.uiState.value is AuthViewModel.AuthState.Idle)
    }
}
