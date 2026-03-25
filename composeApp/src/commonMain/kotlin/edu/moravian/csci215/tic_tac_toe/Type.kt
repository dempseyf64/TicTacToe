package edu.moravian.csci215.tic_tac_toe

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import tictactoe.composeapp.generated.resources.Overlock_Bold
import tictactoe.composeapp.generated.resources.Res


@Composable
fun getAppFontFamily() = FontFamily(
    Font(Res.font.Overlock_Bold, FontWeight.Normal),
)

val baseline = Typography()
