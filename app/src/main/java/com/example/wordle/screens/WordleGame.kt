package com.example.wordle.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavController
import com.example.wordle.R
import com.example.wordle.util.CongratulationDialog
import com.example.wordle.util.FailureDialog
import com.example.wordle.util.HeaderText
import com.example.wordle.util.Keyboard
import com.example.wordle.util.countCorrectPositions
import com.example.wordle.util.countMisplacedLetters
import com.example.wordle.util.readWordsFromFile

@Composable
fun WordDisplay(word: String,
                gameMode: String,
                difficulty: String,
                navController: NavController,
                modifier: Modifier = Modifier,
                initialScore: Int = 0,
                highScore: Int,
                saveHighScore: (Int) -> Unit) {
    val maxGuesses = when (difficulty) {
        stringResource(id = R.string.hard) -> 5
        stringResource(id = R.string.extreme) -> 4
        else -> 6
    }
    val difficultyMultiplier = when (difficulty) {
        stringResource(id = R.string.hard) -> 2
        stringResource(id = R.string.extreme) -> 3
        else -> 1
    }
    val numberGameMode = stringResource(id = R.string.number)
    val infiniteGameMode = stringResource(id = R.string.infinite)
    val submitString = stringResource(id = R.string.submit)
    val deleteString = stringResource(id = R.string.delete)

    var guesses by remember { mutableStateOf(List(maxGuesses) { "" }) }
    var currentGuess by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var showFailureDialog by remember { mutableStateOf(false) }
    var correctPositions by remember { mutableStateOf(List(maxGuesses) { 0 }) }
    var misplacedCounts by remember { mutableStateOf(List(maxGuesses) { 0 }) }
    var score by remember { mutableStateOf(initialScore) }
    val context = LocalContext.current

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(text = "Word: $word", color = Color.White)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HeaderText(text = gameMode,
                        colors = listOf(Color(0xFF32f0ef), MaterialTheme.colorScheme.background))
                    HeaderText(text = difficulty,
                        colors = listOf(MaterialTheme.colorScheme.background, Color(0xFFfa0907)))
                }

                Spacer(modifier = Modifier.height(5.dp))
                if (gameMode == infiniteGameMode) {
                    Text(
                        text = "${stringResource(id = R.string.score)}: $score",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 24.sp)
                    Text(
                        text = "${stringResource(id = R.string.high_score)}: $highScore",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.height(5.dp))

                Column {
                    for (i in guesses.indices) {
                        val guess = if (i == guesses.indexOfFirst { it.isEmpty() }) currentGuess else guesses[i]
                        val correctPositionCount = if (gameMode == numberGameMode && guess.isNotEmpty()) correctPositions[i] else 0
                        val misplacedCount = if (gameMode == numberGameMode && guess.isNotEmpty()) misplacedCounts[i] else 0
                        val isCurrentRow = i == guesses.indexOfFirst { it.isEmpty() }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (gameMode == numberGameMode) {
                                Text(
                                    text = correctPositionCount.toString(),
                                    color = Color(0xFF79D91F),
                                    fontSize = 32.sp,
                                    modifier = Modifier
                                        .padding(end = 14.dp)
                                )
                            }
                            for ((index, char) in guess.lowercase().padEnd(5).withIndex()) {
                                val color1 = if (isCurrentRow) {
                                    Color(0xFFA09E9B)
                                } else {
                                    if (gameMode != numberGameMode) {
                                        when {
                                            word.getOrNull(index)?.equals(char) == true -> {
                                                Color(0xFF79D91F)
                                            }
                                            char in word -> {
                                                Color(0xFFD6D91F)
                                            }
                                            else -> {
                                                Color.LightGray
                                            }
                                        }
                                    } else {
                                        Color.LightGray
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

                            if (gameMode == numberGameMode) {
                                Text(
                                    text = misplacedCount.toString(),
                                    color = Color(0xFFD6D91F),
                                    fontSize = 32.sp,
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Keyboard(
                onKeyPressed = { key ->
                    if (key == submitString && currentGuess.length == 5) {
                        if (guesses.contains("")) {
                            val index = guesses.indexOfFirst { it.isEmpty() }
                            guesses = guesses.toMutableList().also { it[index] = currentGuess }

                            if (currentGuess.equals(word, ignoreCase = true)) {
                                val unusedRows = guesses.count { it.isEmpty() }
                                score += 1 + (unusedRows * difficultyMultiplier)
                                showDialog = true
                                saveHighScore(score)
                            } else if (!guesses.contains("")) {
                                showFailureDialog = true
                                saveHighScore(score)
                            }

                            if (gameMode == numberGameMode) {
                                correctPositions = correctPositions.toMutableList().also {
                                    it[index] = countCorrectPositions(word, currentGuess)
                                }
                                misplacedCounts = misplacedCounts.toMutableList().also {
                                    it[index] = countMisplacedLetters(word, currentGuess)
                                }
                            }

                            currentGuess = ""
                        }
                    } else if (key == deleteString) {
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

    if (showDialog) {
        val words = remember { readWordsFromFile(context.resources) }
        CongratulationDialog(
            onMainMenu = { navController.navigate("mainPage") },
            onNext = {
                val nextScore = if (gameMode == infiniteGameMode) score else 0
                navController.navigate("wordDisplay/${words.random()}?gameMode=$gameMode&difficulty=$difficulty&score=$nextScore") {
                    popUpTo("wordDisplay/{word}?gameMode={gameMode}&difficulty={difficulty}&score={score}") {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )
    }
    if (showFailureDialog) {
        FailureDialog(
            onRetry = {
                val words = readWordsFromFile(context.resources)
                navController.navigate("wordDisplay/${words.random()}?gameMode=$gameMode&difficulty=$difficulty&score=0") {
                    popUpTo("wordDisplay/{word}?gameMode={gameMode}&difficulty={difficulty}") { inclusive = true }
                }
            },
            onMainMenu = { navController.navigate("mainPage") }
        )
    }
}