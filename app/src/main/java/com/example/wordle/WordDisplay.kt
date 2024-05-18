package com.example.wordle

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun WordDisplay(word: String, gameMode: String, difficulty: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFF545454),
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Word: $word", color = Color.White)
            Text(text = "Game Mode: $gameMode", color = Color.White)
            Text(text = "Difficulty: $difficulty", color = Color.White)
        }
    }
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