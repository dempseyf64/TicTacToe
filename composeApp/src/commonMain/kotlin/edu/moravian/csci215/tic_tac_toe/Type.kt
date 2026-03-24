package edu.moravian.csci215.tic_tac_toe

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import tictactoe.composeapp.generated.resources.Papernotes
import tictactoe.composeapp.generated.resources.Res


@Composable
fun getAppFontFamily() = FontFamily(
    Font(Res.font.Papernotes, FontWeight.Normal),
)

val baseline = Typography()
