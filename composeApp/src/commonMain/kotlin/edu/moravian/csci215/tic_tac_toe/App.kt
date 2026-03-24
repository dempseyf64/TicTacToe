package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.*
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.savedstate.SavedState

/**
 * The main entry point for the application's UI.
 * Manages the Scaffold, top app bar visibility, and navigation between screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val curBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = curBackStackEntry?.destination?.route

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                if (currentRoute != null && currentRoute.startsWith("game")) {
                    TopAppBar(
                        title = { Text(stringResource(Res.string.app_name)) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(painterResource(Res.drawable.arrow_left), contentDescription = "Back")
                            }
                        },
                    )
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "welcome",
                modifier = Modifier.padding(innerPadding),
            ) {
                composable("welcome") {
                    WelcomeScreen(
                        snackbarHostState = snackbarHostState,
                        onStartGame = { p1, _, p2, _ ->
                            navController.navigate("game/$p1/$p2")
                        }
                    )
                }

                composable(
                    route = "game/{p1Name}/{p2Name}",
                    arguments = listOf(
                        navArgument("p1Name") { type = NavType.StringType },
                        navArgument("p2Name") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val p1 = "Player 1" // backStackEntry.arguments?.getString("p1Name") ?: "Player 1"
                    val p2 = "Player 2" // backStackEntry.arguments?.getString("p2Name") ?: "Player 2"
                    GameScreen(player1Name = p1, player2Name = p2)
                }
            }
        }
    }
}