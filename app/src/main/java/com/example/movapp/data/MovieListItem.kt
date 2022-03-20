package com.example.movapp.data

data class MovieListItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    var isFavourite: Boolean
)
