package com.example.wordle.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import com.example.wordle.R
import com.example.wordle.data.AppDatabase
import com.example.wordle.data.HighScore
import com.example.wordle.data.Settings
import com.example.wordle.notification.NotificationReceiver
import com.example.wordle.screens.GameModeSelection
import com.example.wordle.screens.HowToPlayDisplay
import com.example.wordle.screens.MainPage
import com.example.wordle.screens.SettingsDisplay
import com.example.wordle.screens.SplashScreen
import com.example.wordle.screens.WordDisplay
import com.example.wordle.ui.theme.WordleTheme
import com.example.wordle.util.ResetGameDataDialog
import com.example.wordle.util.readWordsFromFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    private var currentHighScore by mutableStateOf(0)
    private var isDarkTheme by mutableStateOf(false)
    private var selectedLanguage by mutableStateOf("English")
    private var areNotificationsEnabled by mutableStateOf(true)
    private var showResetDialog by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun updateLocale(language: String) {
            val locale = when (language) {
                "Spanish" -> Locale("es")
                "French" -> Locale("fr")
                "German" -> Locale("de")
                else -> Locale("en")
            }
            Locale.setDefault(locale)
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "wordle-database-v2"
        ).build()

        lifecycleScope.launch {
            currentHighScore = retrieveHighScore()
            val settings = retrieveSettings()
            isDarkTheme = settings.isDarkTheme
            selectedLanguage = settings.selectedLanguage
            updateLocale(selectedLanguage)
            areNotificationsEnabled = settings.areNotificationsEnabled
        }

        setContent {
            WordleTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                if (showResetDialog) {
                    ResetGameDataDialog(
                        onConfirm = {
                            resetGameData()
                            showResetDialog = false
                        },
                        onCancel = { showResetDialog = false }
                    )
                }

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
                        val gameMode = backStackEntry.arguments?.getString("gameMode") ?: getString(
                            R.string.classic
                        )
                        val difficulty = backStackEntry.arguments?.getString("difficulty") ?: getString(
                            R.string.normal
                        )
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
                                updateLocale(language)
                            },
                            areNotificationsEnabled = areNotificationsEnabled,
                            onNotificationsToggle = { enabled ->
                                areNotificationsEnabled = enabled
                                saveSettings(notificationsEnabled = enabled)
                            },
                            onResetGameData = { showResetDialog = true }
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

    private fun saveHighScore(newScore: Int) {
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
    override fun onStop() {
        super.onStop()
        if (areNotificationsEnabled) {
            scheduleNotification()
        }
    }
    private fun scheduleNotification() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val triggerAtMillis = System.currentTimeMillis() + AlarmManager.INTERVAL_DAY

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }
}