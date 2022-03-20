package com.example.movapp.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImdbMovieListApi {


    @GET("API/AdvancedSearch/k_vk7ff0p4/?")
    fun searchForTitle(
        @Query("title") expression: String
    ): Call<ImdbApiSearchResponse>


//    https://imdb-api.com/API/SearchAl/k_vk7ff0p4/?title=inception

}