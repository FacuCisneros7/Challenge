package org.example.challenge

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.example.challenge.domain.repository.UserRepository
import org.example.challenge.ui.auth.AuthViewModel
import org.example.challenge.ui.auth.LoginScreen
import org.example.challenge.ui.auth.RegisterScreen
import org.example.challenge.ui.detail.DetailScreen
import org.example.challenge.ui.detail.DetailViewModel
import org.example.challenge.ui.navigation.MainFlowScreen
import org.example.challenge.ui.profile.UserProfileDetailScreen
import org.example.challenge.ui.profile.UserProfileDetailViewModel
import org.example.challenge.ui.theme.FutbolboxdTheme
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    val userRepository: UserRepository = koinInject()
    val isDarkMode by userRepository.isDarkMode.collectAsState()

    FutbolboxdTheme(darkTheme = isDarkMode) {
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
                            navController.navigate("login") {
                                popUpTo("register") { inclusive = true }
                            }
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
                            navController.navigate("register") {
                                popUpTo("login") { inclusive = true }
                            }
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
                        },
                        onUserClick = { userId ->
                            navController.navigate("user_detail/$userId")
                        }
                    )
                }
                composable(
                    route = "detail/{matchId}",
                    arguments = listOf(navArgument("matchId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val matchId = backStackEntry.arguments?.read { getString("matchId") } ?: ""
                    val detailViewModel: DetailViewModel =
                        koinViewModel(parameters = { parametersOf(matchId) })
                    DetailScreen(
                        viewModel = detailViewModel,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onUserClick = { userId ->
                            navController.navigate("user_detail/$userId")
                        }
                    )
                }
                composable(
                    route = "user_detail/{userId}",
                    arguments = listOf(navArgument("userId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.read { getString("userId") } ?: ""
                    val userDetailViewModel: UserProfileDetailViewModel =
                        koinViewModel(parameters = { parametersOf(userId) })
                    UserProfileDetailScreen(
                        viewModel = userDetailViewModel,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onMatchClick = { matchId ->
                            navController.navigate("detail/$matchId")
                        }
                    )
                }
            }
        }
    }
}
