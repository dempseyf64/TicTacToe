package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.*
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import androidx.savedstate.SavedState
import kotlinx.coroutines.launch
import kotlin.text.get
import kotlinx.serialization.Serializable

/**
 * The main entry point for the application's UI.
 * Manages the Scaffold, top app bar visibility, and navigation between screens.
 */
// Add this at the top with your other @Serializable objects (Welcome, Game)
@Serializable
data class GameOver(val resultMessage: String, val p1Wins: Int, val p2Wins: Int, val ties: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    var p1TotalWins by remember { mutableStateOf(0) }
    var p2TotalWins by remember { mutableStateOf(0) }
    var totalTies by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = WhiteSecondary,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            val curBackStackEntry by navController.currentBackStackEntryAsState()
            val curDestination = curBackStackEntry?.destination

            if (curDestination?.hasRoute<Welcome>() != true) {
                TopAppBar(
                    title = { Text(stringResource(Res.string.app_name)) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = RedPrimary,
                        titleContentColor = WhiteSecondary,
                        navigationIconContentColor = WhiteSecondary
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            val curDestination = navController.currentBackStackEntry?.destination
                            if (curDestination?.hasRoute<Game>() == true) {
                                p1TotalWins = 0
                                p2TotalWins = 0
                                totalTies = 0

                                navController.navigate(Welcome) {
                                    // Pop everything up to the start of the graph
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            } else {
                                // Default behavior for other screens
                                navController.popBackStack()
                            }
                        }) {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_left),
                                contentDescription = stringResource(Res.string.back)
                            )
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Welcome,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<Welcome> {
                WelcomeScreen(
                    snackbarHostState = snackbarHostState,
                    onStartGame = { p1, _, p2, _ ->
                        navController.navigate(Game(p1, p2))
                    }
                )
            }

            composable<Game> { backStackEntry ->
                val game = backStackEntry.toRoute<Game>()
                GameScreen(
                    player1Name = game.player1Name,
                    player2Name = game.player2Name,
                    // 2. Update scores based on game result before navigating
                    navigateToGameOver = { resultMessage, winner ->
                        when (winner) {
                            1 -> p1TotalWins++
                            2 -> p2TotalWins++
                            else -> totalTies++
                        }
                        navController.navigate(GameOver(resultMessage, p1TotalWins, p2TotalWins, totalTies))
                    }
                )
            }

            composable<GameOver> { backStackEntry ->
                val gameOverData = backStackEntry.toRoute<GameOver>()
                GameOverScreen(
                    resultText = gameOverData.resultMessage,
                    p1Wins = gameOverData.p1Wins,
                    p2Wins = gameOverData.p2Wins,
                    ties = gameOverData.ties,
                    onPlayAgain = {
                        // Return to game screen; state remains
                        navController.popBackStack()
                    },
                    onGoHome = {
                        p1TotalWins = 0
                        p2TotalWins = 0
                        totalTies = 0

                        navController.navigate(Welcome) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}