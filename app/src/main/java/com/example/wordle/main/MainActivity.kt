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
import androidx.compose.ui.res.stringResource
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
import com.example.wordle.util.CustomDialog
import com.example.wordle.util.readWordsFromFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
/**
 * MainActivity is the entry point of the application. It handles
 * the main user interface and navigation to other activities. It also
 * manages the application settings and high scores.
 */
class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    private var currentHighScore by mutableStateOf(0)
    private var isDarkTheme by mutableStateOf(false)
    private var selectedLanguage by mutableStateOf("English")
    private var areNotificationsEnabled by mutableStateOf(true)
    private var showResetDialog by mutableStateOf(false)
    /**
     * Initializes the activity. Sets up the database, retrieves settings,
     * and configures the navigation.
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Updates the application's locale based on the selected language.
         * @param language The selected language.
         */
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
                    CustomDialog(
                        title = stringResource(id = R.string.reset_progress),
                        message = stringResource(id = R.string.clear_progress),
                        confirmButtonText = stringResource(id = R.string.yes),
                        onConfirm = {
                            resetGameData()
                            showResetDialog = false
                        },
                        dismissButtonText = stringResource(id = R.string.cancel),
                        onDismiss = { showResetDialog = false },
                        onDismissRequest = { showResetDialog = false }
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
    /**
     * Retrieves the highest score from the database.
     * @return The highest score, or 0 if no score is found.
     */
    private suspend fun retrieveHighScore(): Int {
        return withContext(Dispatchers.IO) {
            val highScoreEntity = db.highScoreDao().getHighScore()
            highScoreEntity?.score ?: 0
        }
    }
    /**
     * Saves a new high score to the database if it is higher than the current high score.
     * @param newScore The new score to be saved.
     */
    private fun saveHighScore(newScore: Int) {
        if (newScore > currentHighScore) {
            currentHighScore = newScore
            lifecycleScope.launch(Dispatchers.IO) {
                db.highScoreDao().insertHighScore(HighScore(score = newScore))
            }
        }
    }
    /**
     * Retrieves the settings from the database.
     * @return The Settings object, or default settings if none are found.
     */
    private suspend fun retrieveSettings(): Settings {
        return withContext(Dispatchers.IO) {
            db.settingsDao().getSettings() ?: Settings()
        }
    }
    /**
     * Saves the settings to the database.
     * @param darkTheme The dark theme setting.
     * @param selectedLanguage The selected language setting.
     * @param notificationsEnabled The notifications enabled setting.
     */
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
    /**
     * Resets the high score.
     */
    private fun resetGameData() {
        lifecycleScope.launch(Dispatchers.IO) {
            db.highScoreDao().insertHighScore(HighScore(score = 0))
            currentHighScore = 0
        }
    }
    /**
     * Called when the activity is no longer visible to the user
     * and checks if notifications are enabled. In that case, schedules the notification.
     */
    override fun onStop() {
        super.onStop()
        if (areNotificationsEnabled) {
            scheduleNotification()
        }
    }
    /**
     * Schedules a notification to be triggered after a day.
     */
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