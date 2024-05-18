
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HowToPlayDisplay(text: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFF545454),
        modifier = modifier.fillMaxSize()) {
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}



