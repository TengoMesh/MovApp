package com.example.movapp.roomdb

import androidx.room.*
import androidx.room.OnConflictStrategy.*

import androidx.lifecycle.LiveData

@Entity(tableName = "movie_item")
data class MovieItem(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    var isFavourite: Boolean
)

@Dao
interface MovieItemDao{
    @Insert(onConflict = IGNORE)
    suspend fun insert(word: MovieItem)

    @Query("SELECT * from movie_item")
    fun getAllMovieItems(): LiveData<List<MovieItem>>

    @Query("DELETE FROM movie_item")
    suspend fun deleteAll()
}


@Database(entities = [MovieItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieItemDao(): MovieItemDao
}