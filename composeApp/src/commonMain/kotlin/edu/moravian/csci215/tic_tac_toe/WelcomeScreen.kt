package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tictactoe.composeapp.generated.resources.*

/**
 * The Welcome Screen displays a title, along with two rows for player input.
 * It allows the user to customize a game, along with if they want to use AI for the app.
 */
val SharedModifier = Modifier.width(180.dp).height(56.dp)

@Serializable
data object Welcome

@Composable
fun WelcomeScreen(
    snackbarHostState: SnackbarHostState,
    onStartGame: (String, String, String, String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val starterNames = listOf(
        stringResource(Res.string.randomname1),
        stringResource(Res.string.randomname2),
        stringResource(Res.string.randomname3),
        stringResource(Res.string.randomname4)
    )

    var name1 by rememberSaveable { mutableStateOf(starterNames.random()) }
    var name2 by rememberSaveable { mutableStateOf(starterNames.random()) }
    var type1 by rememberSaveable { mutableStateOf("Human") }
    var type2 by rememberSaveable { mutableStateOf("Human") }

    val size = LocalWindowInfo.current.containerDpSize

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
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
            modifier = Modifier.fillMaxSize().padding(if (size.height > size.width) 16.dp else 2.dp),
        ) {
            Text(
                text = stringResource(Res.string.welcome),
                style = TextStyle(
                    fontFamily = OverlockFont(),
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Black,
                    color = RedPrimary,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = (if (size.height > size.width) Arrangement.SpaceEvenly else Arrangement.Center)
            ) {
                PlayerSetupField(stringResource(Res.string.player1), name1, { name1 = it }, type1, { type1 = it })
                PlayerSetupField(stringResource(Res.string.player2), name2, { name2 = it }, type2, { type2 = it })
            }

            Spacer(modifier = Modifier.height(if (size.height > size.width) 48.dp else 12.dp))

            // Start Button
            Button(
                onClick = {
                    if (name1.isBlank() || name2.isBlank()) {
                        coroutineScope.launch {
                            val message = org.jetbrains.compose.resources.getString(Res.string.noNameError)
                            snackbarHostState.showSnackbar(message)
                        }
                    } else {
                        onStartGame(name1, type1, name2, type2)
                    }
                },
                modifier = SharedModifier,
                shape = Capsule,
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedPrimary,
                    contentColor = WhiteSecondary,
                )
            ) {
                Text(
                    text = stringResource(Res.string.start),
                    style = TextStyle(
                        fontFamily = OverlockFont(),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = WhiteSecondary,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
fun PlayerSetupField(
    label: String,
    name: String,
    onNameChange: (String) -> Unit,
    type: String,
    onTypeChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val types = listOf(stringResource(Res.string.humanplayer),
        stringResource(Res.string.easyaiplayer),
        stringResource(Res.string.mediumaiplayer),
        stringResource(Res.string.hardaiplayer))

    val primary = RedPrimary
    val surface = WhiteSecondary
    val capsule = Capsule

    val size = LocalWindowInfo.current.containerDpSize

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(if (size.height > size.width) 12.dp else 8.dp)
    ) {
        //ROW 1
        Text(
            text = label,
            style = TextStyle(
                fontFamily = OverlockFont(),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = RedPrimary,
                textAlign = TextAlign.Center
            )
        )

        // ROW 2
        Box {
            Button(
                onClick = { expanded = true },
                modifier = SharedModifier.border(2.dp, primary, capsule),
                shape = capsule,
                colors = ButtonDefaults.buttonColors(
                    containerColor = surface,
                    contentColor = primary
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = type,
                        style = TextStyle(
                            fontFamily = OverlockFont(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = RedPrimary,
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(stringResource(Res.string.arrow))
                }
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                types.forEach { selection ->
                    DropdownMenuItem(
                        text = { Text(
                            text = selection,
                            style = TextStyle(
                                fontFamily = OverlockFont(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = RedPrimary,
                                textAlign = TextAlign.Center
                            )
                        ) },
                        onClick = {
                            onTypeChange(selection)
                            expanded = false
                        }
                    )
                }
            }
        }

        // ROW 3
        TextField(
            value = name,
            onValueChange = onNameChange,
            modifier = SharedModifier.border(2.dp, primary, capsule),
            shape = capsule,
            label = { Text(
                    text = "$label Name",
                    style = TextStyle(
                        fontFamily = OverlockFont(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = RedPrimary,
                        textAlign = TextAlign.Center
                    ))
                    },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = surface,
                unfocusedContainerColor = surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = primary,
                unfocusedLabelColor = primary.copy(alpha = 0.6f)
            )
        )
    }
}