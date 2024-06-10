package com.example.wordle.data

import androidx.room.Database
import androidx.room.RoomDatabase
/**
 * AppDatabase is the main database class for the application, defined using
 * Room Database. It includes the database entities and the corresponding
 * DAOs (Data Access Objects) for accessing the data.
 */
@Database(entities = [HighScore::class, Settings::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Provides access to HighScore related database operations.
     * @return An instance of HighScoreDao.
     */
    abstract fun highScoreDao(): HighScoreDao
    /**
     * Provides access to Settings related database operations.
     * @return An instance of SettingsDao.
     */
    abstract fun settingsDao(): SettingsDao
}