package com.example.wordle.ui

import android.content.res.Resources
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wordle.R
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun WordDisplay(word: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFF545454),
        modifier = modifier.fillMaxSize()) {
        Text(
            text = word,
            modifier = modifier.padding(24.dp)
        )
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

