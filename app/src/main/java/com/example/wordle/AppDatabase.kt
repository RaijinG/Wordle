package com.example.wordle

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HighScore::class, Settings::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun highScoreDao(): HighScoreDao
    abstract fun settingsDao(): SettingsDao
}
