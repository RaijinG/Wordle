package com.example.wordle

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun WordDisplay(word: String, gameMode: String, difficulty: String, navController: NavController, modifier: Modifier = Modifier) {
    val maxGuesses = when (difficulty) {
        stringResource(id = R.string.hard) -> 5
        stringResource(id = R.string.extreme) -> 4
        else -> 6
    }

    var guesses by remember { mutableStateOf(List(maxGuesses) { "" }) }
    var currentGuess by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(
        color = Color(0xFF545454),
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Word: $word", color = Color.White)
            Text(text = "Game Mode: $gameMode", color = Color.White)
            Text(text = "Difficulty: $difficulty", color = Color.White)

            Spacer(modifier = Modifier.height(20.dp))

            Column {
                for (i in guesses.indices) {
                    val guess = if (i == guesses.indexOfFirst { it.isEmpty() }) currentGuess else guesses[i]
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for ((index, char) in guess.lowercase().padEnd(5).withIndex()) {
                            val color1 = if (i == guesses.indexOfFirst { it.isEmpty() }) {
                                Color.LightGray
                            } else {
                                when {
                                    word.getOrNull(index)?.equals(char) == true -> {
                                        Color(0xFF79D91F)
                                    }
                                    char in word -> {
                                        Color(0xFFD6D91F)
                                    }
                                    else -> {
                                        Color(0xFFA09E9B)
                                    }
                                }
                            }
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .border(1.dp, Color.White)
                                    .background(color1),
                            ) {
                                Text(text = char.toString(), color = Color.DarkGray, fontSize = 24.sp)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            Keyboard(
                onKeyPressed = { key ->
                    if (key == "SUBMIT" && currentGuess.length == 5) {
                        if (guesses.contains("")) {
                            val index = guesses.indexOfFirst { it.isEmpty() }
                            guesses = guesses.toMutableList().also { it[index] = currentGuess }

                            if (currentGuess.equals(word, ignoreCase = true)) {
                                showDialog = true
                            }

                            currentGuess = ""
                        }
                    } else if (key == "DELETE") {
                        if (currentGuess.isNotEmpty()) {
                            currentGuess = currentGuess.dropLast(1)
                        }
                    } else if (currentGuess.length < 5 && key.length == 1) {
                        currentGuess += key
                    }
                }
            )
        }
    }

    if (showDialog && gameMode == stringResource(id = R.string.classic)) {
        val words = remember { readWordsFromFile(context.resources) }
        CongratulationDialog(
            onMainMenu = { navController.navigate("mainPage") },
            onNext = {
                navController.navigate("wordDisplay/${words.random()}?gameMode=$gameMode&difficulty=$difficulty") {
                    popUpTo("wordDisplay/{word}?gameMode={gameMode}&difficulty={difficulty}") { inclusive = true }
                }
            }
        )
    }
}

@Composable
fun CongratulationDialog(onMainMenu: () -> Unit, onNext: () -> Unit) {
    Dialog(onDismissRequest = {}) {
        Surface(
            shape = RoundedCornerShape(5.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Congratulations!")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "You guessed the word correctly!")
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onMainMenu,
                        modifier = Modifier
                            .padding(vertical = 10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF424141),
                            contentColor = Color.White
                        )) {
                        Text(text = "MAIN MENU")
                    }
                    Button(
                        onClick = onNext,
                        modifier = Modifier
                            .padding(vertical = 10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF424141),
                            contentColor = Color.White
                        )) {
                        Text(text = "NEXT")
                    }
                }
            }
        }
    }
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
            Key("DELETE", onKeyPressed)
            Key("SUBMIT", onKeyPressed)
        }
    }
}

@Composable
fun Key(label: String, onKeyPressed: (String) -> Unit) {
    val keyWidth = if (label == "SUBMIT" || label == "DELETE") 110.dp else 34.dp
    val color = if (label == "SUBMIT") 0xFF32f0ef
    else if (label == "DELETE") 0xFFfa0907
    else 0xFFffffff
    Button(
        onClick = { onKeyPressed(label) },
        modifier = Modifier
            .padding(2.dp)
            .width(keyWidth)
            .height(60.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(color),
            contentColor = Color.White
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
                color = Color.Black
            )
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

