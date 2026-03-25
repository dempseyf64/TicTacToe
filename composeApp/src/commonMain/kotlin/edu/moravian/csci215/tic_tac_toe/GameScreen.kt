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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import tictactoe.composeapp.generated.resources.*

@Serializable
data class Game(
    val player1Name: String,
    val player2Name: String
)

@Composable
fun GameScreen(
    player1Name: String,
    player2Name: String,
    navigateToGameOver: (String, Int) -> Unit
) {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayerState by remember { mutableStateOf("Strawberry") }
    val displayName = if (currentPlayerState == "Strawberry") player1Name else player2Name

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(Res.drawable.checkerBkgd),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )

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
                                        .size(90.dp)
                                        .clickable(enabled = cellValue == "") {
                                            val newBoard = board.toMutableList()
                                            newBoard[cellIndex] = currentPlayerState
                                            board = newBoard

                                            val result = checkGameResult(newBoard)

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
    }
}

/**
 * Checks the board for a winner or a tie.
 * Returns "Strawberry", "Orange", "Tie", or null if game is ongoing.
 */
fun checkGameResult(board: List<String>): String? {
    val winLines = listOf(
        listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
        listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Cols
        listOf(0, 4, 8), listOf(2, 4, 6)                 // Diagonals
    )

    for (line in winLines) {
        if (board[line[0]].isNotEmpty() &&
            board[line[0]] == board[line[1]] &&
            board[line[0]] == board[line[2]]) {
            return board[line[0]]
        }
    }

    if (board.none { it.isEmpty() }) return "Tie"

    return null
}