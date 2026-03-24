package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import tictactoe.composeapp.generated.resources.Papernotes
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.checkerBkgd

// variables for same theme consistency
private val CapsuleShape = CircleShape
private val SharedModifier = Modifier.width(180.dp).height(56.dp)
private val primaryColor = Color(0xFFB01212)
private val primaryAltColor = Color(0xFFFc7474)
private val secondaryColor = Color.White


@Composable
fun WelcomeScreen(
    snackbarHostState: SnackbarHostState,
    onStartGame: (String, String, String, String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val starterNames = listOf("Bob", "Chris", "Amanda", "Alexis")


    var name1 by rememberSaveable { mutableStateOf(starterNames.random()) }
    var name2 by rememberSaveable { mutableStateOf(starterNames.random()) }
    var type1 by rememberSaveable { mutableStateOf("Human") }
    var type2 by rememberSaveable { mutableStateOf("Human") }


    Box(modifier = Modifier.fillMaxSize()) {
        // checkered background imagery
        Image(
            painter = painterResource(Res.drawable.checkerBkgd),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            alpha = 0.5f // opacity
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp),
        ) {
            Text(
                text = "Welcome to Tic-Tac-Toe!",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PlayerSetupField("Player 1", name1, { name1 = it }, type1, { type1 = it })
                PlayerSetupField("Player 2", name2, { name2 = it }, type2, { type2 = it })
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    if (name1.isBlank() || name2.isBlank()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Names cannot be empty!")
                        }
                    } else {
                        onStartGame(name1, type1, name2, type2)
                    }
                },
                modifier = SharedModifier,
                shape = CapsuleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = secondaryColor
                )
            ) {
                Text(
                    text = "Start!",
                    style = MaterialTheme.typography.headlineSmall
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
    val types = listOf("Human", "Easy AI", "Medium AI", "Hard AI")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ROW 1
        Text(
            text = label,
            color = primaryColor,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium
        )
        // ROW 2
        Box {
            Button(
                onClick = { expanded = true },
                modifier = SharedModifier,
                shape = CapsuleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryColor,
                    contentColor = primaryColor
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(type)
                    Text("▼")
                }
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                types.forEach { selection ->
                    DropdownMenuItem(
                        text = { Text(selection) },
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
            modifier = SharedModifier,
            shape = CapsuleShape,
            label = { Text("$label Name") },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = secondaryColor,
                unfocusedContainerColor = secondaryColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}