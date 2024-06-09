package com.example.wordle

import GameModeSelection
import HowToPlayDisplay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.wordle.ui.theme.WordleTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    private var currentHighScore by mutableStateOf(0)
    private var isDarkTheme by mutableStateOf(false)
    private var selectedLanguage by mutableStateOf("English")
    private var areNotificationsEnabled by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "wordle-database-v2"
        ).build()
        lifecycleScope.launch {
            currentHighScore = retrieveHighScore()
            val settings = retrieveSettings()
            isDarkTheme = settings.isDarkTheme
            selectedLanguage = settings.selectedLanguage
            areNotificationsEnabled = settings.areNotificationsEnabled
        }

        setContent {
            WordleTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "splashScreen") {
                    composable("splashScreen") {
                        SplashScreen(navController)
                    }
                    composable("mainPage") {
                        MainPage(
                            onPlayClicked = {
                                navController.navigate("gameModeSelection")
                            },
                            onHowToPlayClicked = {
                                navController.navigate("howToPlay")
                            },
                            onSettingsClicked = {
                                navController.navigate("settings")
                            }
                        )
                    }
                    composable("gameModeSelection") {
                        GameModeSelection(
                            onGameModeSelected = { gameMode, difficulty ->
                                val words = readWordsFromFile(resources)
                                navController.navigate("wordDisplay/${words.random()}?gameMode=$gameMode&difficulty=$difficulty") {
                                    popUpTo("gameModeSelection") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                    composable("wordDisplay/{word}?gameMode={gameMode}&difficulty={difficulty}&score={score}") { backStackEntry ->
                        val word = backStackEntry.arguments?.getString("word") ?: ""
                        val gameMode = backStackEntry.arguments?.getString("gameMode") ?: getString(R.string.classic)
                        val difficulty = backStackEntry.arguments?.getString("difficulty") ?: getString(R.string.normal)
                        val initialScore = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
                        val highScore = currentHighScore
                        WordDisplay(word = word,
                            gameMode = gameMode,
                            difficulty = difficulty,
                            navController = navController,
                            initialScore = initialScore,
                            highScore = highScore,
                            saveHighScore = { score -> saveHighScore(score) }
                        )
                    }
                    composable("howToPlay") {
                        HowToPlayDisplay()
                    }
                    composable("settings") {
                        SettingsDisplay(
                            isDarkTheme = isDarkTheme,
                            onThemeChange = { darkTheme ->
                                isDarkTheme = darkTheme
                                saveSettings(darkTheme = darkTheme)
                            },
                            selectedLanguage = selectedLanguage,
                            onLanguageChange = { language ->
                                selectedLanguage = language
                                saveSettings(selectedLanguage = language)
                            },
                            areNotificationsEnabled = areNotificationsEnabled,
                            onNotificationsToggle = { enabled ->
                                areNotificationsEnabled = enabled
                                saveSettings(notificationsEnabled = enabled)
                            },
                            onResetGameData = { resetGameData() }
                        )
                    }
                }
            }
        }
    }

    private suspend fun retrieveHighScore(): Int {
        return withContext(Dispatchers.IO) {
            val highScoreEntity = db.highScoreDao().getHighScore()
            highScoreEntity?.score ?: 0
        }
    }

    fun saveHighScore(newScore: Int) {
        if (newScore > currentHighScore) {
            currentHighScore = newScore
            lifecycleScope.launch(Dispatchers.IO) {
                db.highScoreDao().insertHighScore(HighScore(score = newScore))
            }
        }
    }

    private suspend fun retrieveSettings(): Settings {
        return withContext(Dispatchers.IO) {
            db.settingsDao().getSettings() ?: Settings()
        }
    }

    private fun saveSettings(
        darkTheme: Boolean? = null,
        selectedLanguage: String? = null,
        notificationsEnabled: Boolean? = null
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            val currentSettings = db.settingsDao().getSettings() ?: Settings()
            darkTheme?.let { currentSettings.isDarkTheme = it }
            selectedLanguage?.let { currentSettings.selectedLanguage = it }
            notificationsEnabled?.let { currentSettings.areNotificationsEnabled = it }
            db.settingsDao().insertSettings(currentSettings)
        }
    }

    private fun resetGameData() {
        lifecycleScope.launch(Dispatchers.IO) {
            db.highScoreDao().insertHighScore(HighScore(score = 0))
            currentHighScore = 0
        }
    }
}

