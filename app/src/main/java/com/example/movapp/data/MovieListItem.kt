package com.example.movapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "movie_item")
data class MovieListItem(
    @PrimaryKey val id: String,
    val name: String,
    val description: String? = "",
    val imageUrl: String,
    var imdbRating: String?,
    var isFavourite: Boolean,
    var isHidden: Boolean = false
) : Serializable
