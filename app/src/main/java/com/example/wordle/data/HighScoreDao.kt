package com.example.wordle.data
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
/**
 * Entity class representing a high score in the database.
 */
@Entity(tableName = "high_score")
data class HighScore(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val score: Int
)
/**
 * DAO (Data Access Object) interface for accessing high score data from the database.
 */
@Dao
interface HighScoreDao {
    /**
     * Retrieves the highest score from the high_score table.
     * @return The HighScore object with the highest score, or null if no scores are found.
     */
    @Query("SELECT * FROM high_score ORDER BY score DESC LIMIT 1")
    fun getHighScore(): HighScore?
    /**
     * Inserts a new high score into the high_score table.
     * @param highScore The HighScore object to be inserted.
     */
    @Insert
    fun insertHighScore(highScore: HighScore)
}
