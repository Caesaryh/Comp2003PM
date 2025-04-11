package com.example.pmanager.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
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
            MainLayout(navController) { innerPadding ->
                val dao = AppDatabase.getDatabase(LocalContext.current).passwordInfoDao()
                val browseViewModel: BrowseViewModel = viewModel(
                    factory = BrowseViewModelFactory(dao)
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
            val browseViewModel: BrowseViewModel = viewModel(
                factory = BrowseViewModelFactory(dao)
            )

            CreateScreen(
                onCreate = { newPassword ->
                    browseViewModel.addPassword(newPassword)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }

        composable("details/{id}") { backStackEntry ->
            val passwordId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            val dao = AppDatabase.getDatabase(LocalContext.current).passwordInfoDao()
            val browseViewModel: BrowseViewModel = viewModel(
                factory = BrowseViewModelFactory(dao)
            )

            val password by browseViewModel.getPasswordById(passwordId ?: 0).collectAsState(initial = null)

            if (password != null) {
                DetailScreen(
                    passwordInfo = password!!,
                    onEditClick = {
                        navController.navigate("edit/${password!!.id}")
                    }
                )
            } else {
                Text("Loading...")
            }
        }

        composable("edit/{id}") { backStackEntry ->
            val passwordId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            val dao = AppDatabase.getDatabase(LocalContext.current).passwordInfoDao()
            val browseViewModel: BrowseViewModel = viewModel(
                factory = BrowseViewModelFactory(dao)
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