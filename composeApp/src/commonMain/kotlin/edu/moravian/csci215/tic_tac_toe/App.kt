package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.*
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

/**
 * The main entry point for the application's UI.
 * Manages the Scaffold, top app bar visibility, and navigation between screens.
 */
@Serializable
data class Game(
    val player1Name: String,
    val player1Type: String,
    val player2Name: String,
    val player2Type: String
)
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

    val resetToWelcome = {
        p1TotalWins = 0
        p2TotalWins = 0
        totalTies = 0
        navController.navigate(Welcome) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            val curBackStackEntry by navController.currentBackStackEntryAsState()
            val curDestination = curBackStackEntry?.destination

            // No top app bar
            if (curDestination?.hasRoute<Welcome>() != true) {
                TopAppBar(
                    title = { Text(stringResource(Res.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (curDestination?.hasRoute<Game>() == true) {
                                resetToWelcome()
                            } else {
                                navController.popBackStack() // Play Again
                            }
                        }) {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_left),
                                contentDescription = stringResource(Res.string.back)
                            )
                        }
                    }
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
                    onStartGame = { p1Name, p1Type, p2Name, p2Type ->
                        navController.navigate(Game(p1Name, p1Type, p2Name, p2Type))
                    }
                )
            }

            composable<Game> { backStackEntry ->
                val game = backStackEntry.toRoute<Game>()
                GameScreen(
                    player1Name = game.player1Name,
                    player1Type = game.player1Type,
                    player2Name = game.player2Name,
                    player2Type = game.player2Type,
                    snackbarHostState = snackbarHostState,
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
                val data = backStackEntry.toRoute<GameOver>()
                GameOverScreen(
                    resultText = data.resultMessage,
                    p1Wins = data.p1Wins,
                    p2Wins = data.p2Wins,
                    ties = data.ties,
                    onPlayAgain = { navController.popBackStack() },
                    onGoHome = { resetToWelcome() }
                )
            }
        }
    }
}