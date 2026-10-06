package org.example.challenge

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.read
import org.example.challenge.data.remote.DatabaseSeeder
import org.example.challenge.ui.auth.AuthViewModel
import org.example.challenge.ui.auth.LoginScreen
import org.example.challenge.ui.auth.RegisterScreen
import org.example.challenge.ui.detail.DetailScreen
import org.example.challenge.ui.detail.DetailViewModel
import org.example.challenge.ui.navigation.MainFlowScreen
import org.example.challenge.ui.theme.FutbolboxdTheme
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    val seeder: DatabaseSeeder = koinInject()
    LaunchedEffect(Unit) {
        seeder.seedIfEmpty()
    }

    FutbolboxdTheme {
        val authViewModel: AuthViewModel = koinViewModel()
        val currentUserIdState by authViewModel.currentUserId.collectAsState()
        var isAuthChecking by remember { mutableStateOf(true) }

        LaunchedEffect(currentUserIdState) {
            isAuthChecking = false
        }

        if (isAuthChecking) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Futbolboxd",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        } else {
            val startDestination = if (currentUserIdState != null) "main" else "register"
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
                                popUpTo("register") { inclusive = true }
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
                        },
                        onMatchClick = { matchId ->
                            navController.navigate("detail/$matchId")
                        }
                    )
                }
                composable(
                    route = "detail/{matchId}",
                    arguments = listOf(navArgument("matchId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val matchId = backStackEntry.arguments?.read { getString("matchId") } ?: ""
                    val detailViewModel: DetailViewModel = koinViewModel(parameters = { parametersOf(matchId) })
                    DetailScreen(
                        viewModel = detailViewModel,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
