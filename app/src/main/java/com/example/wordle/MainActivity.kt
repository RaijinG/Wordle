package com.example.wordle

import GameModeSelection
import HowToPlayDisplay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wordle.ui.theme.WordleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordleTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "mainPage") {
                    composable("mainPage") {
                        MainPage(
                            onPlayClicked = {
                                navController.navigate("gameModeSelection")
                            },
                            onHowToPlayClicked = {
                                navController.navigate("howToPlay")
                            }
                        )
                    }
                    composable("gameModeSelection") {
                        GameModeSelection(
                            navController = navController,
                            onGameModeSelected = { gameMode, difficulty ->
                                val words = readWordsFromFile(resources)
                                navController.navigate("wordDisplay/${words.random()}?gameMode=$gameMode&difficulty=$difficulty")
                            }
                        )
                    }
                    composable("wordDisplay/{word}?gameMode={gameMode}&difficulty={difficulty}") { backStackEntry ->
                        val word = backStackEntry.arguments?.getString("word") ?: ""
                        val gameMode = backStackEntry.arguments?.getString("gameMode") ?: getString(R.string.classic)
                        val difficulty = backStackEntry.arguments?.getString("difficulty") ?: getString(R.string.normal)
                        WordDisplay(word = word, gameMode = gameMode, difficulty = difficulty)
                    }
                    composable("howToPlay") {
                        HowToPlayDisplay()
                    }
                }
            }
        }
    }
}
