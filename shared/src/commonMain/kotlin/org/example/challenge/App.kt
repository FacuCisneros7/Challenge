package org.example.challenge

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.challenge.data.remote.DatabaseSeeder
import org.example.challenge.ui.auth.AuthViewModel
import org.example.challenge.ui.auth.LoginScreen
import org.example.challenge.ui.auth.RegisterScreen
import org.example.challenge.ui.navigation.MainFlowScreen
import org.example.challenge.ui.theme.FutbolboxdTheme
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val seeder: DatabaseSeeder = koinInject()
    LaunchedEffect(Unit) {
        seeder.seedIfEmpty()
    }

    FutbolboxdTheme {
        val authViewModel: AuthViewModel = koinViewModel()
        val currentUserId by authViewModel.currentUserId.collectAsState()

        val startDestination = if (currentUserId != null) "main" else "register"
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable("register") {
                RegisterScreen(
                    viewModel = authViewModel,
                    onRegisterSuccess = {
                        navController.navigate("main") {
                            popUpTo("register") { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate("login")
                    }
                )
            }
            composable("login") {
                LoginScreen(
                    viewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.popBackStack()
                    }
                )
            }
            composable("main") {
                MainFlowScreen(
                    onSignOut = {
                        authViewModel.signOut {
                            navController.navigate("login") {
                                popUpTo("main") { inclusive = true }
                            }
                        }
                    }
                )
            }
        }
    }
}
