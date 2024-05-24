
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wordle.R

@Composable
fun GameModeSelection(
    onGameModeSelected: (String, String) -> Unit
) {
    var selectedGameMode by remember { mutableStateOf(R.string.classic) }
    var selectedDifficulty by remember { mutableStateOf(R.string.normal) }

    val gameModeText = stringResource(id = selectedGameMode)
    val difficultyText = stringResource(id = selectedDifficulty)

    Surface(
        color = Color(0xFF545454),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImageGamemode()
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { selectedGameMode = R.string.classic },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGameMode == R.string.classic) Color(0xFF32f0ef) else Color(0xFF424141),
                    contentColor = if (selectedGameMode == R.string.classic) Color.Black else Color.White,
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
                    containerColor = if (selectedGameMode == R.string.infinite) Color(0xFF32f0ef) else Color(0xFF424141),
                    contentColor = if (selectedGameMode == R.string.infinite) Color.Black else Color.White
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
                    containerColor = if (selectedGameMode == R.string.number) Color(0xFF32f0ef) else Color(0xFF424141),
                    contentColor = if (selectedGameMode == R.string.number) Color.Black else Color.White
                )
            ) {
                Text(text = stringResource(id = R.string.number))
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (selectedGameMode != R.string.number) {
                ImageDifficulty()
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
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF32f0ef), unselectedColor = Color.White)
                        )
                        Text(
                            text = stringResource(id = difficulty),
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            } else {
                selectedDifficulty = R.string.normal
                Spacer(modifier = Modifier.height(255.dp))
            }

            Button(
                onClick = {
                    onGameModeSelected(gameModeText, difficultyText)
                },
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF32f0ef),
                    contentColor = Color.Black
                )
            ) {
                Text(text = stringResource(id = R.string.start_game))
            }
        }
    }
}
