package com.example.wordle.util

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import com.example.wordle.R
import java.io.BufferedReader
import java.io.InputStreamReader
/**
 * Composable function that displays header text with a gradient background.
 * @param text The text to be displayed.
 * @param colors The list of colors to be used in the gradient background.
 */
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
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )
    }
}
/**
 * Composable function that displays a header image.
 *
 * @param id The resource ID of the image to be displayed.
 */
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
/**
 * Reads a list of words from a raw resource file.
 * @param resources The resources object to access the raw file.
 * @return A list of words read from the file.
 */
fun readWordsFromFile(resources: Resources): List<String> {
    val inputStream = resources.openRawResource(R.raw.words)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val words = mutableListOf<String>()
    reader.useLines { lines ->
        lines.forEach { words.add(it) }
    }
    return words
}
/**
 * Composable function that displays a customizable dialog.
 * @param title The title of the dialog.
 * @param message The message to be displayed in the dialog.
 * @param confirmButtonText The text to be displayed on the confirm button.
 * @param onConfirm Callback function invoked when the confirm button is pressed.
 * @param dismissButtonText The text to be displayed on the dismiss button. Defaults to null.
 * @param onDismiss Callback function invoked when the dismiss button is pressed. Defaults to null.
 * @param onDismissRequest Callback function invoked when the dialog is dismissed.
 */
@Composable
fun CustomDialog(
    title: String,
    message: String,
    confirmButtonText: String,
    onConfirm: () -> Unit,
    dismissButtonText: String? = null,
    onDismiss: (() -> Unit)? = null,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = message)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    dismissButtonText?.let {
                        Button(
                            onClick = onDismiss ?: onDismissRequest,
                            modifier = Modifier
                                .padding(vertical = 10.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.tertiary
                            )
                        ) {
                            Text(text = it)
                        }
                    }
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .padding(vertical = 10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(text = confirmButtonText)
                    }
                }
            }
        }
    }
}
/**
 * Composable function that displays a key in the keyboard.
 * @param label The label of the key.
 * @param onKeyPressed Callback function invoked when the key is pressed.
 */
@Composable
private fun Key(label: String, onKeyPressed: (String) -> Unit) {
    val keyWidth =
        if (label == stringResource(id = R.string.submit) ||
        label == stringResource(id = R.string.delete))
            110.dp else 34.dp
    val color =
        if (label == stringResource(id = R.string.submit)) Color(0xFF32f0ef)
        else if (label == stringResource(id = R.string.delete)) Color(0xFFfa0907)
        else MaterialTheme.colorScheme.onTertiary
    val color1 =
        if (label == stringResource(id = R.string.submit) ||
            label == stringResource(id = R.string.delete))
            Color.Black else MaterialTheme.colorScheme.tertiary
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
/**
 * Composable function that displays the keyboard for user input.
 * @param onKeyPressed Callback function invoked when a key is pressed.
 */
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
/**
 * Counts the number of characters in the correct positions.
 * @param word The target word.
 * @param guess The guessed word.
 * @return The number of characters in the correct positions.
 */
fun countCorrectPositions(word: String, guess: String): Int {
    return guess.indices.count { index -> word.getOrNull(index)?.equals(guess[index], ignoreCase = true) == true }
}
/**
 * Counts the number of misplaced characters.
 * @param word The target word.
 * @param guess The guessed word.
 * @return The number of misplaced characters.
 */
fun countMisplacedLetters(word: String, guess: String): Int {
    val wordCharCounts = word.groupBy { it }.mapValues { it.value.size }.toMutableMap()
    var misplacedCount = 0
    guess.forEachIndexed { index, c ->
        if (c.lowercaseChar() in word && c.lowercaseChar() != word.getOrNull(index)) {
            if (wordCharCounts[c.lowercaseChar()]!! > 0) {
                misplacedCount++
                wordCharCounts[c.lowercaseChar()] = wordCharCounts[c.lowercaseChar()]!! - 1
            }
        }
    }
    return misplacedCount
}