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
    navigateToGameOver: (GameOver) -> Unit
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

                                            // Switch turn
                                            currentPlayerState =
                                                if (currentPlayerState == "Strawberry") {
                                                    "Orange"
                                                } else {
                                                    "Strawberry"
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