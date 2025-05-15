package com.example.pmanager.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pmanager.data.AppDatabase
import com.example.pmanager.ui.compose.BrowseScreen
import com.example.pmanager.ui.compose.CreateScreen
import com.example.pmanager.ui.compose.DetailScreen
import com.example.pmanager.ui.compose.EditScreen
import com.example.pmanager.ui.compose.LoginScreen
import com.example.pmanager.ui.compose.MainLayout
import com.example.pmanager.ui.compose.ProfileScreen
import com.example.pmanager.ui.compose.RegisterScreen
import com.example.pmanager.ui.compose.SettingsScreen
import com.example.pmanager.ui.theme.PManagerTheme
import com.example.pmanager.ui.view.AuthViewModel
import com.example.pmanager.ui.view.BrowseViewModel
import com.example.pmanager.ui.view.BrowseViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PManagerTheme {
                val db = remember { AppDatabase.getDatabase(this) }
                val authViewModel = viewModel<AuthViewModel>(factory = AuthViewModelFactory(db))

                MainNavigation(authViewModel)
            }
        }
    }
}

// 添加ViewModel工厂类
class AuthViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(db) as T
    }
}

// 主导航结构
@Composable
fun MainNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // 登录界面
        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }

        // 注册界面
        composable("register") {
            RegisterScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }

        composable("main") {
            val currentUser by authViewModel.currentUser.collectAsState()
            MainLayout(navController) { innerPadding ->
                val dao = AppDatabase.getDatabase(LocalContext.current).passwordInfoDao()
                val browseViewModel: BrowseViewModel = viewModel(
                    factory = BrowseViewModelFactory(
                        dao = dao,
                        userId = currentUser!!.id
                    )
                )
                BrowseScreen(
                    viewModel = browseViewModel,
                    onItemClick = { password ->
                        navController.navigate("details/${password.id}")
                    },
                    navController=navController
                )
            }
        }

        composable("profile") {
            MainLayout(navController) { innerPadding ->
                ProfileScreen(
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate("login") {
                            popUpTo("profile")
                        }
                    },
                    onBack = { navController.popBackStack() },
                    authViewModel = authViewModel
                )
            }
        }

        composable("settings") {
            MainLayout(navController) { innerPadding ->
                SettingsScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable("create") {
            val dao = AppDatabase.getDatabase(LocalContext.current).passwordInfoDao()
            val currentUser by authViewModel.currentUser.collectAsState()
            val browseViewModel: BrowseViewModel = viewModel(
                factory = BrowseViewModelFactory(
                    dao = dao,
                    userId = currentUser!!.id
                )
            )

            CreateScreen(
                onCreate = { newPassword ->
                    browseViewModel.addPassword(
                        account = newPassword.account,
                        password = newPassword.password,
                        commits = newPassword.commits,
                        currentUserId = currentUser!!.id

                    )
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }

        composable("details/{id}") { backStackEntry ->
            val passwordId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            val dao = AppDatabase.getDatabase(LocalContext.current).passwordInfoDao()
            val currentUser by authViewModel.currentUser.collectAsState()
            val browseViewModel: BrowseViewModel = viewModel(
                factory = BrowseViewModelFactory(
                    dao = dao,
                    userId = currentUser!!.id
                )
            )


            val password by browseViewModel.getPasswordById(passwordId ?: 0).collectAsState(initial = null)

            if (password != null) {
                DetailScreen(
                    passwordInfo = password!!,
                    onEditClick = {
                        navController.navigate("edit/${password!!.id}")
                    },
                    onDeleteClick = {
                                browseViewModel.deletePasswordById(passwordId ?: 0)
                                navController.navigate("main")
                    }
                )
            } else {
                Text("Loading...")
            }
        }

        composable("edit/{id}") { backStackEntry ->
            val passwordId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            val dao = AppDatabase.getDatabase(LocalContext.current).passwordInfoDao()
            val currentUser by authViewModel.currentUser.collectAsState()
            val browseViewModel: BrowseViewModel = viewModel(
                factory = BrowseViewModelFactory(
                    dao = dao,
                    userId = currentUser!!.id
                )
            )

            val originalItem by browseViewModel.getPasswordById(passwordId ?: 0)
                .collectAsState(initial = null)

            originalItem?.let { password ->
                EditScreen(
                    passwordInfo = password,
                    onSave = { updatedInfo ->
                        browseViewModel.updatePassword(updatedInfo)
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() }
                )
            } ?: Text("Cannot load data.")
        }
    }
}

@Composable
fun deleteConfirmationDialog(
    navController: NavController,
    onConfirmDelete: suspend () -> Unit,
    dialogConfig: DeleteDialogConfig = DeleteDialogConfig.default()
): () -> Unit {
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(dialogConfig.title) },
            text = { Text(dialogConfig.message) },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                onConfirmDelete()
                                navController.popBackStack()
                                dialogConfig.onSuccess?.invoke()
                            } catch (e: Exception) {
                                dialogConfig.onError?.invoke(e)
                            }
                        }
                        showDialog = false
                    }
                ) {
                    Text(dialogConfig.confirmText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        dialogConfig.onDismiss?.invoke()
                    }
                ) {
                    Text(dialogConfig.cancelText)
                }
            }
        )
    }

    return { showDialog = true }
}

data class DeleteDialogConfig(
    val title: String,
    val message: String,
    val confirmText: String,
    val cancelText: String,
    val onSuccess: (() -> Unit)?,
    val onError: ((Exception) -> Unit)?,
    val onDismiss: (() -> Unit)?
) {
    companion object {
        fun default() = DeleteDialogConfig(
            title = "Confirm Deletion",
            message = "Are you sure you want to permanently delete this item?",
            confirmText = "Delete",
            cancelText = "Cancel",
            onSuccess = null,
            onError = null,
            onDismiss = null
        )
    }
}


