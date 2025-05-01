package com.example.pmanager

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pmanager.data.AppDatabase
import com.example.pmanager.ui.activity.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthIntegrationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun clearDb() = runBlocking {
        val db = AppDatabase.getDatabase(composeTestRule.activity)
        withContext(Dispatchers.IO) {
            db.clearAllTables()
        }
    }



    @Test
    fun testRegisterAndLoginSuccess() {
        composeTestRule.onNodeWithText("Don't have an account? Register here").performClick()

        composeTestRule.onNodeWithText("Username").performTextInput("testuser")
        composeTestRule.onNodeWithText("Password").performTextInput("Test1234")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("Test1234")

        composeTestRule.onNode(
            hasText("Create Account") and hasClickAction()
        ).performClick()

        composeTestRule.onNodeWithText("Home").assertExists()
    }

    @Test
    fun testRegisterPasswordMismatch() {
        composeTestRule.onNodeWithText("Don't have an account? Register here").performClick()

        composeTestRule.onNodeWithText("Username").performTextInput("testuser")
        composeTestRule.onNodeWithText("Password").performTextInput("Test1234")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("WrongPass")

        composeTestRule.onNode(
            hasText("Create Account") and hasClickAction()
        ).performClick()

        composeTestRule.onNodeWithText("password did not match").assertExists()
    }

    @Test
    fun testLoginWithNonExistentUser() {
        composeTestRule.onNodeWithText("Username").performTextInput("invalid_user")
        composeTestRule.onNodeWithText("Password").performTextInput("anypassword")
        composeTestRule.onNodeWithText("Sign In").performClick()
        composeTestRule.onNodeWithText("User is not found").assertExists()
    }

    @Test
    fun testCreateNewPassword() {
        testRegisterAndLoginSuccess()
        composeTestRule.onNodeWithContentDescription("Add password").performClick()
        composeTestRule.apply {
            onNodeWithText("Account").performTextInput("test_account")
            onNodeWithText("Password").performTextInput("S3cr3tP@ss")
            onNodeWithText("Comment").performTextInput("test account")
            onNodeWithText("Create").performClick()
        }
        composeTestRule.apply {
            onNodeWithText("Home").assertExists()
            onNodeWithText("test_account").assertExists()
            onNodeWithText("test account").assertExists()
        }
    }

    @Test
    fun testCancelCreatePassword() {
        testRegisterAndLoginSuccess()

        composeTestRule.apply {
            onNodeWithContentDescription("Add password").performClick()
            onNodeWithText("Account").performTextInput("temp_account")
            onNodeWithText("Password").performTextInput("Temp123!")

            onNodeWithText("Cancel").performClick()
        }

        composeTestRule.apply {
            onNodeWithText("Home").assertExists()
            onNodeWithText("temp_account").assertDoesNotExist()
        }
    }

    fun testCreatePasswordWithEmptyFields() {
        testRegisterAndLoginSuccess()

        composeTestRule.apply {
            onNodeWithContentDescription("Add password").performClick()
            onNodeWithText("Create").performClick()

            onNodeWithText("Account is required").assertExists()
            onNodeWithText("Password is required").assertExists()
        }
    }

    @Test
    fun testFullPasswordLifecycle() {
        testCreateNewPassword()

        composeTestRule.apply {

            onNodeWithText("test_account").performClick()
            onNodeWithContentDescription("Edit").performClick()

            onNodeWithText("Comment").performTextReplacement("new comment")
            onNodeWithText("Save").performClick()

            onNodeWithText("new comment").assertExists()
        }
    }


}
