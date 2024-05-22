
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordle.R

@Composable
fun HowToPlayDisplay(modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFF545454),
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
fun HeaderSection(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .width(310.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF32f0ef), Color(0xFFfa0907))
                )
            )
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}
@Composable
fun HeaderSectionGamemode(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .width(200.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF32f0ef), Color(0xFF545454))
                )
            )
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}
@Composable
fun HeaderSectionDifficulty(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .width(200.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF545454), Color(0xFFfa0907))
                )
            )
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}

@Composable
fun GameModeSection() {
    HeaderSection(text = stringResource(id = R.string.game_mode))
    Spacer(modifier = Modifier.height(8.dp))

    GameModeCard(title = stringResource(id = R.string.classic), description = stringResource(id = R.string.classic_description))
    GameModeCard(title = stringResource(id = R.string.infinite), description = stringResource(id = R.string.infinite_description))
    GameModeCard(title = stringResource(id = R.string.number), description = stringResource(id = R.string.number_description))
}

@Composable
fun DifficultySection() {
    HeaderSection(text = stringResource(id = R.string.difficulty))
    Spacer(modifier = Modifier.height(8.dp))

    DifficultyCard(title = stringResource(id = R.string.normal), description = stringResource(id = R.string.normal_description))
    DifficultyCard(title = stringResource(id = R.string.hard), description = stringResource(id = R.string.hard_description))
    DifficultyCard(title = stringResource(id = R.string.extreme), description = stringResource(id = R.string.extreme_description))
}

@Composable
fun GameModeCard(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF424141)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                color = Color(0xFF32f0ef),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = description,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun DifficultyCard(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF424141)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                color = Color(0xFF32f0ef),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = description,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}
