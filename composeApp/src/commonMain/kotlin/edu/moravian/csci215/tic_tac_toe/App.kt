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

/**
 * The main entry point for the application's UI.
 * Manages the Scaffold, top app bar visibility, and navigation between screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    // Track current destination to show/hide the TopAppBar
    val curBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = curBackStackEntry?.destination?.route

    MaterialTheme {
        // Requirement: Scaffold must be outside the NavHost
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                // Requirement: No top app bar on Welcome Screen (route "welcome")
                if (currentRoute != "welcome") {
                    TopAppBar(
                        title = { Text(stringResource(Res.string.app_name)) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        navigationIcon = {
                            // Requirement: Back button to return to Welcome Screen
                            IconButton(onClick = { navController.navigate("welcome") }) {
                                Icon(
                                    painterResource(Res.drawable.arrow_left),
                                    contentDescription = "Back",
                                )
                            }
                        },
                    )
                }
            },
        ) { innerPadding ->
            // Requirement: Exactly 3 major navigation routes
            NavHost(
                navController = navController,
                startDestination = "welcome",
                modifier = Modifier.padding(innerPadding),
            ) {
                composable("welcome") {
                    WelcomeScreen(
                        snackbarHostState = snackbarHostState,
                        onStartGame = { p1Name, p1Type, p2Name, p2Type ->
                            // TODO: In the next step, we will initialize the Game class with these values
                            navController.navigate("game")
                        }
                    )
                }

                composable("game") {
                    GameScreen()
                }

                /**
                composable("gameOver") {
                    GameOverScreen()
                }
                */
            }
        }
    }
}