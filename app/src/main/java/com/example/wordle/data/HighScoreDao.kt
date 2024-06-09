package com.example.wordle.data
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
@Entity(tableName = "high_score")
data class HighScore(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val score: Int
)
@Dao
interface HighScoreDao {
    @Query("SELECT * FROM high_score ORDER BY score DESC LIMIT 1")
    fun getHighScore(): HighScore?

    @Insert
    fun insertHighScore(highScore: HighScore)
    @Query("DELETE FROM high_score")
    fun deleteAll()
}
