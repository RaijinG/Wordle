package com.example.wordle

import HowToPlayDisplay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wordle.ui.MainPage
import com.example.wordle.ui.WordDisplay
import com.example.wordle.ui.readWordsFromFile
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
                                val words = readWordsFromFile(resources)
                                navController.navigate("wordDisplay/${words.random()}")
                            },
                            onHowToPlayClicked = {
                                navController.navigate("howToPlay")
                            }
                        )
                    }
                    composable("wordDisplay/{word}") { backStackEntry ->
                        val word = backStackEntry.arguments?.getString("word") ?: ""
                        WordDisplay(word = word)
                    }
                    composable("howToPlay") {
                        HowToPlayDisplay("hovnisko")
                    }
                }
            }
        }
    }
}

