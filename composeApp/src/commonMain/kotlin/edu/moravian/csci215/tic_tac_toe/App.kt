package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import tictactoe.composeapp.generated.resources.*

@Serializable
data object Title

@Serializable
data object Game

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    MaterialTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                val curBackStackEntry by navController.currentBackStackEntryAsState()
                val curDestination = curBackStackEntry?.destination
                if (curDestination?.hasRoute<Title>() != true) {
                    TopAppBar(
                        title = { Text(stringResource(Res.string.app_name)) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        navigationIcon = {
                            Button(onClick = { navController.navigateUp() }) {
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
            NavHost(
                navController,
                startDestination = Title,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable<Title> {
                    WelcomeScreen{navController.navigate(Game)}
                }
                composable<Game> {
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    startGame: () -> Unit) {
    var name1 by remember { mutableStateOf("filler") }
    var name2 by remember { mutableStateOf("name") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
    ) {
        Text(
            "it's tic-tac-toe",
            style = MaterialTheme.typography.displayMedium,
        )
        TextField(
            value = name1,
            onValueChange = {
                name1 = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {Text("Player 1")}
        )
        TextField(
            value = name2,
            onValueChange = {
                name2 = it
                            },
            modifier = Modifier.fillMaxWidth(),
            label = {Text("Player 2")}
        )
        Button(onClick = {startGame()}) {
            Text("Start")
        }
    }
}

@Composable
fun GameScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
    ) {
        Text(
            "it's the game screen",
            style = MaterialTheme.typography.displayMedium,
        )
        Text(
            "it's not implemented yet"
        )
    }
}