package com.example.movapp.data

import androidx.lifecycle.LiveData
import com.example.movapp.MovieApplication
import com.example.movapp.retrofit.ImdbApiMovieDescription
import com.example.movapp.retrofit.ImdbApiSearchResponse
import com.example.movapp.retrofit.ImdbMovieListApi
import com.example.movapp.retrofit.RetrofitBuilder
import com.example.movapp.roomdb.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImdbMovieListRepository : MovieListRepository {

    override fun searchForMovies(searchExpression: String, callback: MovieListRepository.Callback) {
        RetrofitBuilder.getRetrofitClient().create(ImdbMovieListApi::class.java)
            .searchForTitle(searchExpression)
            .enqueue(object : Callback<ImdbApiSearchResponse> {
                override fun onResponse(
                    call: Call<ImdbApiSearchResponse>,
                    response: Response<ImdbApiSearchResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.results?.let {
                            callback.onSuccess(convertToMovieList(it))
                        }?: callback.onError(Throwable(response.body()?.errorMessage?: "error message was null"))

                    } else {
                        callback.onError(
                            Throwable(
                                response.errorBody()?.string() ?: "error could not be identified"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<ImdbApiSearchResponse>, t: Throwable) {
                    callback.onError(t)
                }
            })

    }

    private fun convertToMovieList(list: List<ImdbApiMovieDescription>): List<MovieListItem> {
        val currentFavourites = getFavourites()
        val currentHidden = getHidden()
        println("current favourites = ${currentFavourites.joinToString { "\n" }}")
        return list.filter { it.id != null }.map {
            MovieListItem(
                it.id!!,
                it.title ?: "",
                it.description?: "",
                it.image ?: "",
                it.imdbRating,
                currentFavourites.any { item ->  it.id == item.id }
            )
        }.filter { item -> !currentHidden.any { it.id == item.id} }
    }

    override fun addToFavourites(listItem: MovieListItem) {
        listItem.isFavourite = true
        getDb().movieItemDao()
            .insert(listItem)
    }

    override fun removeFromFavourites(listItem: MovieListItem) {
        getDb().movieItemDao().delete(listItem)
    }

    override fun getFavouritesLiveData(): LiveData<List<MovieListItem>> {
        return getDb().movieItemDao().getAllMovieItemsLiveData()
    }

    private fun getFavourites() = getDb().movieItemDao().getAllFavourites()
    private fun getHidden() = getDb().movieItemDao().getAllHidden()
    private fun getDb(): AppDatabase = MovieApplication.getDb()
}