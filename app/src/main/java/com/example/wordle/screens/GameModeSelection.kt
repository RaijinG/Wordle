package com.example.wordle.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wordle.R
import com.example.wordle.util.HeaderImage
/**
 * Composable function that displays the game mode selection screen.
 * @param onGameModeSelected Callback function invoked when a game mode and difficulty are selected.
 * The callback receives the selected game mode and difficulty as parameters.
 */
@Composable
fun GameModeSelection(
    onGameModeSelected: (String, String) -> Unit
) {
    var selectedGameMode by remember { mutableStateOf(R.string.classic) }
    var selectedDifficulty by remember { mutableStateOf(R.string.normal) }

    val gameModeText = stringResource(id = selectedGameMode)
    val difficultyText = stringResource(id = selectedDifficulty)

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeaderImage(id = R.drawable.wordlegamemode)
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { selectedGameMode = R.string.classic },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGameMode == R.string.classic) MaterialTheme.colorScheme.primary else
                        MaterialTheme.colorScheme.secondary,
                    contentColor = if (selectedGameMode == R.string.classic) MaterialTheme.colorScheme.onTertiary else
                        MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(text = stringResource(id = R.string.classic))
            }
            Button(
                onClick = { selectedGameMode = R.string.infinite },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGameMode == R.string.infinite) MaterialTheme.colorScheme.primary else
                        MaterialTheme.colorScheme.secondary,
                    contentColor = if (selectedGameMode == R.string.infinite) MaterialTheme.colorScheme.onTertiary else
                        MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(text = stringResource(id = R.string.infinite))
            }
            Button(
                onClick = { selectedGameMode = R.string.number },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGameMode == R.string.number) MaterialTheme.colorScheme.primary else
                        MaterialTheme.colorScheme.secondary,
                    contentColor = if (selectedGameMode == R.string.number) MaterialTheme.colorScheme.onTertiary else
                        MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(text = stringResource(id = R.string.number))
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (selectedGameMode != R.string.number) {
                HeaderImage(id = R.drawable.wordledifficulty)
                Spacer(modifier = Modifier.height(10.dp))

                val difficulties = listOf(R.string.normal, R.string.hard, R.string.extreme)
                difficulties.forEach { difficulty ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        RadioButton(
                            selected = selectedDifficulty == difficulty,
                            onClick = { selectedDifficulty = difficulty },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.tertiary),
                            modifier = Modifier.padding(start = 105.dp)
                        )
                        Text(
                            text = stringResource(id = difficulty),
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            } else {
                selectedDifficulty = R.string.normal
                Spacer(modifier = Modifier.height(255.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onGameModeSelected(gameModeText, difficultyText)
                },
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Text(text = stringResource(id = R.string.start_game))
            }
        }
    }
}