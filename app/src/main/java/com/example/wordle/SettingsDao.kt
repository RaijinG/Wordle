package com.example.wordle

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var isDarkTheme: Boolean = true,
    var selectedLanguage: String = "English",
    var areNotificationsEnabled: Boolean = true
)
@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSettings(settings: Settings)

    @Query("SELECT * FROM settings LIMIT 1")
    fun getSettings(): Settings?
}