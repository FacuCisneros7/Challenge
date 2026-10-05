package org.example.challenge

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.challenge.ui.auth.AuthViewModel
import org.example.challenge.ui.auth.LoginScreen
import org.example.challenge.ui.auth.RegisterScreen
import org.example.challenge.ui.navigation.MainFlowScreen
import org.example.challenge.ui.theme.FutbolboxdTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    FutbolboxdTheme {
        val navController = rememberNavController()
        val authViewModel: AuthViewModel = koinViewModel()

        NavHost(
            navController = navController,
            startDestination = "register"
        ) {
            composable("register") {
                RegisterScreen(
                    viewModel = authViewModel,
                    onRegisterSuccess = {
                        navController.navigate("main") {
                            popUpTo("register") { inclusive = true}
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
