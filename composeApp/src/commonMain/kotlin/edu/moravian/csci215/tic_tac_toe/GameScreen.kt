package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import tictactoe.composeapp.generated.resources.*

@Composable
fun GameScreen(
    player1Name: String,
    player2Name: String
) {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayerState by remember { mutableStateOf("Strawberry") }
    val displayName = if (currentPlayerState == "Strawberry") player1Name else player2Name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Current Player: $displayName",
            style = MaterialTheme.typography.headlineSmall
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

                                        // Switch turn
                                        currentPlayerState = if (currentPlayerState == "Strawberry") {
                                            "Orange"
                                        } else {
                                            "Strawberry"
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (cellValue != "") {
                                    val icon = if (cellValue == "Strawberry") Res.drawable.strawberry else Res.drawable.orange
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