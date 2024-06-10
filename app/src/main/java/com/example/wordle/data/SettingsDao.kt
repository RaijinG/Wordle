package com.example.wordle.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
/**
 * Entity class representing the settings in the database.
 */
@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var isDarkTheme: Boolean = true,
    var selectedLanguage: String = "English",
    var areNotificationsEnabled: Boolean = true
)
/**
 * DAO (Data Access Object) interface for accessing settings data from the database.
 */
@Dao
interface SettingsDao {
    /**
     * Inserts or updates the settings in the settings table.
     * @param settings The Settings object to be inserted or updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSettings(settings: Settings)
    /**
     * Retrieves the settings from the settings table.
     */
    @Query("SELECT * FROM settings LIMIT 1")
    fun getSettings(): Settings?
}