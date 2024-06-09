package com.example.wordle

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun HeaderText(text: String, colors: List<Color>) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .width(200.dp)
            .background(
                Brush.horizontalGradient(colors = colors)
            )
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )
    }
}
@Composable
fun HeaderImage(id: Int) {
    Image(
        painter = painterResource(id = id),
        contentDescription = stringResource(id = R.string.app_name),
        modifier = Modifier
            .height(110.dp)
            .size(300.dp)
    )
}
fun readWordsFromFile(resources: Resources): List<String> {
    val inputStream = resources.openRawResource(R.raw.words)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val words = mutableListOf<String>()
    reader.useLines { lines ->
        lines.forEach { words.add(it) }
    }
    return words
}
@Composable
fun Keyboard(onKeyPressed: (String) -> Unit) {
    val keys = listOf(
        "QWERTYUIOP",
        "ASDFGHJKL",
        "ZXCVBNM"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (row in keys) {
            Row {
                for (char in row) {
                    Key(char.toString(), onKeyPressed)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Row {
            Key(stringResource(id = R.string.delete), onKeyPressed)
            Key(stringResource(id = R.string.submit), onKeyPressed)
        }
    }
}

@Composable
fun Key(label: String, onKeyPressed: (String) -> Unit) {
    val keyWidth = if (label == stringResource(id = R.string.submit) ||
        label == stringResource(id = R.string.delete)) 110.dp else 34.dp
    val color = if (label == stringResource(id = R.string.submit)) Color(0xFF32f0ef)
    else if (label == stringResource(id = R.string.delete)) Color(0xFFfa0907)
    else MaterialTheme.colorScheme.onTertiary
    val color1 = if (label == stringResource(id = R.string.submit) || label == stringResource(id = R.string.delete)) Color.Black
    else MaterialTheme.colorScheme.tertiary
    Button(
        onClick = { onKeyPressed(label) },
        modifier = Modifier
            .padding(2.dp)
            .width(keyWidth)
            .height(60.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 22.sp,
                color = color1
            )
        }
    }
}