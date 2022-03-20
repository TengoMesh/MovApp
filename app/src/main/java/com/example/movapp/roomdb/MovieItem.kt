package com.example.movapp.roomdb

import androidx.room.*
import androidx.room.OnConflictStrategy.*

import androidx.lifecycle.LiveData
import com.example.movapp.data.MovieListItem

@Dao
interface MovieItemDao{
    @Insert(onConflict = IGNORE)
    fun insert(item: MovieListItem)

    @Query("SELECT * from movie_item")
    fun getAllMovieItemsLiveData(): LiveData<List<MovieListItem>>

    @Query("SELECT * from movie_item")
    fun getAllFavourites(): List<MovieListItem>

    @Query("DELETE FROM movie_item")
    fun deleteAll()

    @Delete()
    fun delete(item: MovieListItem)
}


@Database(entities = [MovieListItem::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieItemDao(): MovieItemDao
}