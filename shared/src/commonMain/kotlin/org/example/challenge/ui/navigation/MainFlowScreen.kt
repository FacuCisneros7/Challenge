package org.example.challenge.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.example.challenge.ui.home.HomeScreen
import org.example.challenge.ui.home.HomeViewModel
import org.example.challenge.ui.profile.ProfileScreen
import org.example.challenge.ui.profile.ProfileViewModel
import org.example.challenge.ui.search.SearchScreen
import org.example.challenge.ui.search.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainFlowScreen(
    onSignOut: () -> Unit,
    onMatchClick: (String) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    val homeViewModel: HomeViewModel = koinViewModel()
    val searchViewModel: SearchViewModel = koinViewModel()
    val profileViewModel: ProfileViewModel = koinViewModel()

    val topBarTitle = when (selectedTab) {
        0 -> "Futbolboxd"
        1 -> "Buscar Partidos"
        else -> "Perfil"
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = topBarTitle,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Text("⚽") },
                    label = { Text("Home") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Text("🔍") },
                    label = { Text("Buscar") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Text("👤") },
                    label = { Text("Perfil") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (selectedTab) {
                0 -> HomeScreen(viewModel = homeViewModel, onMatchClick = onMatchClick)
                1 -> SearchScreen(viewModel = searchViewModel, onMatchClick = onMatchClick)
                2 -> ProfileScreen(viewModel = profileViewModel, onSignOut = onSignOut, onMatchClick = onMatchClick)
            }
        }
    }
}
