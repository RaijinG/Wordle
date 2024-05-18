
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordle.R

@Composable
fun HowToPlayDisplay(text: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFF545454),
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                SectionHeader(stringResource(id = R.string.game_mode))
                SectionHeader2(stringResource(id = R.string.classic))
                SectionText(stringResource(id = R.string.classic_description))
                SectionHeader2(stringResource(id = R.string.infinite))
                SectionText(stringResource(id = R.string.infinite_description))
                SectionHeader2(stringResource(id = R.string.number))
                SectionText(stringResource(id = R.string.number_description))

                SectionHeader(stringResource(id = R.string.difficulty))
                SectionHeader2(stringResource(id = R.string.normal))
                SectionText(stringResource(id = R.string.normal_description))
                SectionHeader2(stringResource(id = R.string.hard))
                SectionText(stringResource(id = R.string.hard_description))
                SectionHeader2(stringResource(id = R.string.extreme))
                SectionText(stringResource(id = R.string.extreme_description))
            }
        }
    }
}




@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        color = Color.White,
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun SectionHeader2(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Start,
        color = Color.White,
        fontSize = 20.sp,
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 5.dp,
            )
            .fillMaxWidth()
    )
}

@Composable
fun SectionText(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Start,
        color = Color.White,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(
                start = 40.dp,
                top = 10.dp,
                bottom = 10.dp
            )
            .fillMaxWidth()
    )
}
