package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.*

/**
 * The Game Over Screen displays the final result and the tournament scores.
 * It allows the user to play again or return to the main menu.
 */
@Composable
fun GameOverScreen(
    resultText: String,
    p1Wins: Int,
    p2Wins: Int,
    ties: Int,
    onPlayAgain: () -> Unit,
    onGoHome: () -> Unit
) {
    val size = LocalWindowInfo.current.containerDpSize
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.checkerBkgd),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(Res.string.gameover),
                style = TextStyle(
                    fontFamily = OverlockFont(),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    color = RedPrimary
                )
            )

            Spacer(modifier = Modifier.height(if (size.height > size.width) 16.dp else 8.dp))

            Text(
                text = resultText,
                style = TextStyle(
                    fontFamily = OverlockFont(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = RedLight,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(if (size.height > size.width) 32.dp else 16.dp))

            Row {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(Res.string.strawberry),
                        style = TextStyle(
                            fontFamily = OverlockFont(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary,
                            textAlign = TextAlign.Center
                        ))
                    Text(p1Wins.toString(),
                        style = TextStyle(
                            fontFamily = OverlockFont(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center

                        ))
                }

                Spacer(modifier = Modifier.width(6.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(Res.string.tie),
                        style = TextStyle(
                            fontFamily = OverlockFont(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary,
                            textAlign = TextAlign.Center
                        ))
                    Text(ties.toString(),
                        style = TextStyle(
                            fontFamily = OverlockFont(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ))
                }

                Spacer(modifier = Modifier.width(if (size.height > size.width) 16.dp else 8.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(Res.string.orange),
                        style = TextStyle(
                            fontFamily = OverlockFont(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary,
                            textAlign = TextAlign.Center
                        ))
                    Text(p2Wins.toString(),
                        style = TextStyle(
                            fontFamily = OverlockFont(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ))
                }
            }

            Spacer(modifier = Modifier.height(if (size.height > size.width) 32.dp else 16.dp))

            Button(
                onClick = onPlayAgain,
                modifier = SharedSize,
                shape = Capsule,
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedPrimary,
                    contentColor = WhiteSecondary
                )
            ) {
                Text(
                    text = stringResource(Res.string.playagain),
                    style = TextStyle(
                        fontFamily = OverlockFont(),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onGoHome,
                modifier = SharedSize,
                shape = Capsule,
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedPrimary,
                    contentColor = WhiteSecondary
                )
            ) {
                Text(
                    text = stringResource(Res.string.gohome),
                    style = TextStyle(
                        fontFamily = OverlockFont(),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}