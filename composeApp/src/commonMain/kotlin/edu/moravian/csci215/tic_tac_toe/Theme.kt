package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import tictactoe.composeapp.generated.resources.*

// Colors
val RedPrimary = Color(0xFFB01212)
val RedLight = Color(0xFFFc7474)
val WhiteSecondary = Color.White

// Shapes & Modifiers
val Capsule = CircleShape
val SharedSize = Modifier.width(180.dp).height(56.dp)

// Fonts
@Composable
fun OverlockFont() = FontFamily(
    Font(Res.font.Overlock_Regular, FontWeight.Normal),
    Font(Res.font.Overlock_Bold, FontWeight.Bold),
    Font(Res.font.Overlock_Black, FontWeight.Black),
    Font(Res.font.Overlock_Italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.Overlock_BoldItalic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.Overlock_BlackItalic, FontWeight.Black, FontStyle.Italic),
)
