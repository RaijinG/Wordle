package com.example.wordle.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFF32f0ef),
    secondary = Color(0xFF424141),
    tertiary = Color.White,
    onPrimary = Color(0xFF36AABF),
    onTertiary = Color(0xFF383838),
    background = Color(0xFF545454)
)

private val LightColorPalette = lightColorScheme(
    primary = Color(0xFFfa0907),
    secondary = Color(0xFFC0BEBC),
    tertiary = Color(0xFF383838),
    onPrimary = Color(0xFF952928),
    onTertiary = Color.White,
    background = Color(0xFFABA7A7)
)
@Composable
fun WordleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}