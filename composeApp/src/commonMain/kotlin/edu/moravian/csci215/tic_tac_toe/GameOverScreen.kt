package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import tictactoe.composeapp.generated.resources.*

@Composable
fun GameOverScreen(
    resultText: String,
    onPlayAgain: () -> Unit
) {
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
                text = "GAME OVER",
                style = TextStyle(
                    fontFamily = OverlockFont(),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    color = RedPrimary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "placeholder",
                style = TextStyle(
                    fontFamily = OverlockFont(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = RedLight,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

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
                    text = "Play Again",
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