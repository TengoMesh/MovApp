package com.example.movapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_item")
data class MovieListItem(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    var isFavourite: Boolean
)
