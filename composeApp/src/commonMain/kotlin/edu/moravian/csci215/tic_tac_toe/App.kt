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

/**
 * The main entry point for the application's UI.
 * Manages the Scaffold, top app bar visibility, and navigation between screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                val curBackStackEntry by navController.currentBackStackEntryAsState()
                val curDestination = curBackStackEntry?.destination
                if (curDestination?.hasRoute<Welcome>() != true) {
                    TopAppBar(
                        title = { Text(stringResource(Res.string.app_name)) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(painterResource(Res.drawable.arrow_left), contentDescription = stringResource(Res.string.back))
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
                            navController.navigate(Game(p1,p2))
                        }
                    )
                }
                composable<Game> { backStackEntry ->
                    val game = backStackEntry.toRoute<Game>()
                    val p1Name = game.player1Name
                    val p2Name = game.player2Name
                    GameScreen(p1Name,p2Name){
                        navController.navigate(it)
                    }
                }
            }
        }
    }
}