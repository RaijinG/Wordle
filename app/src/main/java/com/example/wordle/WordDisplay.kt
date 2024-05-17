package com.example.wordle.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WordDisplay(word: String, modifier: Modifier = Modifier) {
    Surface(color = androidx.compose.material3.MaterialTheme.colorScheme.background) {
        Text(
            text = word,
            modifier = modifier
                .padding(24.dp)
        )
    }
}
