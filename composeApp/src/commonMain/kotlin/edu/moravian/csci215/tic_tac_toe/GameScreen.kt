package edu.moravian.csci215.tic_tac_toe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * The main game screen containing the 3x3 grid.
 * This is a placeholder for the March 18th check-in. [cite: 51]
 */
@Composable
fun GameScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Requirement: Text saying the name of the current player [cite: 12]
        Text("Current Player: Placeholder", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Basic 3x3 Grid Placeholder for check-in [cite: 11]
        Column {
            repeat(3) {
                Row {
                    repeat(3) {
                        Button(
                            onClick = { /* Will handle logic later */ },
                            modifier = Modifier.size(80.dp).padding(4.dp)
                        ) {
                            Text("")
                        }
                    }
                }
            }
        }
    }
}