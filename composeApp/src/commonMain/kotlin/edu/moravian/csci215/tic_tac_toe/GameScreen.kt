package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.moravian.csci215.tic_tac_toe.GameLogic
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import tictactoe.composeapp.generated.resources.*

/**
 * The Game Screen displays the active tic tac toe game, including the grid and game pieces.
 * A subheading is shown for the current player's turn.
 */
@Composable
fun GameScreen(
    player1Name: String,
    player1Type: String,
    player2Name: String,
    player2Type: String,
    snackbarHostState: SnackbarHostState,
    navigateToGameOver: (String, Int) -> Unit
) {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayerState by remember { mutableStateOf("Strawberry") }
    val scope = rememberCoroutineScope()
    var isAiThinking by remember { mutableStateOf(false) }

    LaunchedEffect(currentPlayerState) {
        val currentType = if (currentPlayerState == "Strawberry") player1Type else player2Type
        val humanString = org.jetbrains.compose.resources.getString(Res.string.humanplayer)

        if (currentType != humanString && GameLogic.checkGameResult(board) == null) {
            isAiThinking = true
            kotlinx.coroutines.delay(1000)

            val aiMove = GameLogic.getEasyAiMove(board)

            if (aiMove != -1) {
                val newBoard = board.toMutableList()
                newBoard[aiMove] = currentPlayerState
                board = newBoard

                val result = GameLogic.checkGameResult(newBoard)
                if (result != null) {
                    val (message, winnerId) = when (result) {
                        "Strawberry" -> "$player1Name Wins!\n(Strawberry)" to 1
                        "Orange" -> "$player2Name Wins!\n(Orange)" to 2
                        else -> "It's a Tie!" to 0
                    }
                    navigateToGameOver(message, winnerId)
                } else {
                    currentPlayerState = if (currentPlayerState == "Strawberry") "Orange" else "Strawberry"
                }
            }
            isAiThinking = false
        }
    }

    val displayName = if (currentPlayerState == "Strawberry") player1Name else player2Name

    val size = LocalWindowInfo.current.containerDpSize

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(Res.drawable.checkerBkgd),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )

        if (size.height > size.width) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$displayName's turn\n( $currentPlayerState )",
                    style = TextStyle(
                        fontFamily = OverlockFont(),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Black,
                        color = RedPrimary,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier.size(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.grid),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(3) { rowIndex ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                repeat(3) { columnIndex ->
                                    val cellIndex = rowIndex * 3 + columnIndex
                                    val cellValue = board[cellIndex]

                                    Box(
                                        modifier = Modifier
                                            .size(if (size.height > size.width) 90.dp else 45.dp)
                                            .clickable {

                                                if (isAiThinking) return@clickable

                                                if (cellValue != "") {
                                                    scope.launch {
                                                        snackbarHostState.showSnackbar(org.jetbrains.compose.resources.getString(Res.string.takenSpot))
                                                    }
                                                    return@clickable // Exit early
                                                }

                                                val newBoard = board.toMutableList()
                                                newBoard[cellIndex] = currentPlayerState
                                                board = newBoard

                                                val result = GameLogic.checkGameResult(newBoard)

                                                if (result != null) {
                                                    val (message, winnerId) = when (result) {
                                                        "Strawberry" -> "$player1Name Wins!\n(Strawberry)" to 1
                                                        "Orange" -> "$player2Name Wins!\n(Orange)" to 2
                                                        else -> "It's a Tie!" to 0
                                                    }
                                                    navigateToGameOver(message, winnerId)
                                                } else {
                                                    currentPlayerState = if (currentPlayerState == "Strawberry") "Orange" else "Strawberry"
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (cellValue != "") {
                                            val icon =
                                                if (cellValue == "Strawberry") Res.drawable.strawberry else Res.drawable.orange
                                            Image(
                                                painter = painterResource(icon),
                                                contentDescription = cellValue,
                                                modifier = Modifier.padding(12.dp).fillMaxSize()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$displayName's turn\n( $currentPlayerState )",
                    style = TextStyle(
                        fontFamily = OverlockFont(),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Black,
                        color = RedPrimary,
                        textAlign = TextAlign.Center
                    )
                )

                Box(
                    modifier = Modifier.size(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.grid),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(3) { rowIndex ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                repeat(3) { columnIndex ->
                                    val cellIndex = rowIndex * 3 + columnIndex
                                    val cellValue = board[cellIndex]

                                    Box(
                                        modifier = Modifier
                                            .size(90.dp)
                                            .clickable(enabled = cellValue == "") {
                                                val newBoard = board.toMutableList()
                                                newBoard[cellIndex] = currentPlayerState
                                                board = newBoard

                                                val result = GameLogic.checkGameResult(newBoard)

                                                if (result != null) {
                                                    val (message, winnerId) = when (result) {
                                                        "Strawberry" -> "$player1Name Wins!\n(Strawberry)" to 1
                                                        "Orange" -> "$player2Name Wins!\n(Orange)" to 2
                                                        else -> "It's a Tie!" to 0
                                                    }
                                                    navigateToGameOver(message, winnerId)
                                                } else {
                                                    currentPlayerState = if (currentPlayerState == "Strawberry") "Orange" else "Strawberry"
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (cellValue != "") {
                                            val icon =
                                                if (cellValue == "Strawberry") Res.drawable.strawberry else Res.drawable.orange
                                            Image(
                                                painter = painterResource(icon),
                                                contentDescription = cellValue,
                                                modifier = Modifier.padding(6.dp).fillMaxSize()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

