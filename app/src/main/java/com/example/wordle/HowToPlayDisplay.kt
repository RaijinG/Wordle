
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordle.HeaderImage
import com.example.wordle.R

@Composable
fun HowToPlayDisplay(modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            GameModeSection()

            Spacer(modifier = Modifier.height(10.dp))

            DifficultySection()
        }
    }
}

@Composable
private fun GameModeSection() {
    HeaderImage(id = R.drawable.wordlegamemode)
    Spacer(modifier = Modifier.height(8.dp))

    TextCard(title = stringResource(id = R.string.classic),
        description = stringResource(id = R.string.classic_description))
    TextCard(title = stringResource(id = R.string.infinite),
        description = stringResource(id = R.string.infinite_description))
    TextCard(title = stringResource(id = R.string.number),
        description = stringResource(id = R.string.number_description))
}

@Composable
private fun DifficultySection() {
    HeaderImage(id = R.drawable.wordledifficulty)
    Spacer(modifier = Modifier.height(8.dp))

    TextCard(title = stringResource(id = R.string.normal),
        description = stringResource(id = R.string.normal_description))
    TextCard(title = stringResource(id = R.string.hard),
        description = stringResource(id = R.string.hard_description))
    TextCard(title = stringResource(id = R.string.extreme),
        description = stringResource(id = R.string.extreme_description))
}

@Composable
private fun TextCard(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = description,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 18.sp
            )
        }
    }
}