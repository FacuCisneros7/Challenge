package org.example.challenge.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import challengetecnico.shared.generated.resources.Res
import challengetecnico.shared.generated.resources.busqueda
import challengetecnico.shared.generated.resources.cancha
import challengetecnico.shared.generated.resources.futbolista
import org.example.challenge.ui.home.HomeScreen
import org.example.challenge.ui.home.HomeViewModel
import org.example.challenge.ui.profile.ProfileScreen
import org.example.challenge.ui.profile.ProfileViewModel
import org.example.challenge.ui.search.SearchScreen
import org.example.challenge.ui.search.SearchViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainFlowScreen(
    onSignOut: () -> Unit,
    onMatchClick: (String) -> Unit = {},
    onUserClick: (String) -> Unit = {}
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

    val navItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

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
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(Res.drawable.cancha),
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text("Home") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    colors = navItemColors
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(Res.drawable.busqueda),
                            contentDescription = "Buscar",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text("Buscar") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    colors = navItemColors
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(Res.drawable.futbolista),
                            contentDescription = "Perfil",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text("Perfil") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    colors = navItemColors
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (selectedTab) {
                0 -> HomeScreen(viewModel = homeViewModel, onMatchClick = onMatchClick)
                1 -> SearchScreen(viewModel = searchViewModel, onMatchClick = onMatchClick)
                2 -> ProfileScreen(
                    viewModel = profileViewModel,
                    onSignOut = onSignOut,
                    onMatchClick = onMatchClick,
                    onUserClick = onUserClick
                )
            }
        }
    }
}
