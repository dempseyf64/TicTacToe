package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/**
 * The main game screen containing the 3x3 grid.
 */
@Composable
fun GameScreen() {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayer by remember { mutableStateOf("X") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Current Player: $currentPlayer", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurface) // The line color
        ) {
            repeat(3) { rowIndex ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    repeat(3) { columnIndex ->
                        val cellIndex = rowIndex * 3 + columnIndex
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .clickable(enabled = board[cellIndex] == "") {
                                    val newBoard = board.toMutableList()
                                    newBoard[cellIndex] = currentPlayer
                                    board = newBoard
                                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = board[cellIndex],
                                style = MaterialTheme.typography.displayMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}