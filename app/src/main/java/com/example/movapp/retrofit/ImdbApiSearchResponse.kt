package com.example.movapp.retrofit

import com.google.gson.annotations.SerializedName

data class ImdbApiSearchResponse(
    @SerializedName("searchType")
    var searchType: String?,

    @SerializedName("expression")
    var expression: String?,

    @SerializedName("results")
    var results: List<ImdbApiMovieDescription>
)

data class ImdbApiMovieDescription(
    @SerializedName("id")
    var id: String?,

    @SerializedName("resultType")
    var resultType: String?,

    @SerializedName("title")
    var title: String?,

    @SerializedName("image")
    var image: String?,

    @SerializedName("description")
    var description: String?
)
