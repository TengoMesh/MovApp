package com.example.movapp.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbMovieListApi {


    @GET("API/Search/k_aaaaaaaa/{search_word}/")
    fun searchForTitle(
        @Path("search_word") expression: String
    ): Call<ImdbApiSearchResponse>


}