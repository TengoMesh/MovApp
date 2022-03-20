package com.example.movapp.roomdb

import androidx.room.*
import androidx.room.OnConflictStrategy.*

import androidx.lifecycle.LiveData
import com.example.movapp.data.MovieListItem
import java.io.Serializable

@Dao
interface MovieItemDao{
    @Insert(onConflict = REPLACE)
    fun insert(item: MovieListItem)

    @Query("SELECT * from movie_item where isFavourite = 1 AND isHidden = 0")
    fun getAllMovieItemsLiveData(): LiveData<List<MovieListItem>>

    @Query("SELECT * from movie_item where isFavourite = 1")
    fun getAllFavourites(): List<MovieListItem>


    @Query("SELECT * from movie_item where isHidden = 1")
    fun getAllHidden(): List<MovieListItem>

    @Query("DELETE FROM movie_item")
    fun deleteAll()

    @Delete()
    fun delete(item: MovieListItem)
}

@Database(entities = [MovieListItem::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieItemDao(): MovieItemDao
}