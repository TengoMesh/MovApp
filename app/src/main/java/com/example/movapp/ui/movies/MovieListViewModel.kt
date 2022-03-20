package com.example.movapp.ui.movies

import androidx.lifecycle.*
import com.example.movapp.data.FragmentPagerData
import com.example.movapp.data.MovieFragmentType
import com.example.movapp.data.MovieListItem
import com.example.movapp.data.MovieListRepository

class MovieListViewModel(fragmentName: String) : ViewModel() {

    var movieList = MutableLiveData<List<MovieListItem>>()
        private set

    var textToDisplay = MutableLiveData<String?>()

    private var currentPageLoaded = 0

    private var type = MovieFragmentType.fromTabName(fragmentName)
    private val movieListRepository: MovieListRepository =
        FragmentPagerData.getMovieListRepository()


    fun provideListNextPage() {
        if (type == MovieFragmentType.FAVOURITES) {
            movieListRepository.getFavouritesLiveData().observeForever {
                movieList.value = it
            }
        } else {
            movieListRepository.provideMovieList(currentPageLoaded++, object: MovieListRepository.Callback{
                override fun onSuccess(list: List<MovieListItem>) {
                    movieList.value = list
                }

                override fun onError(exception: Throwable) {
                    textToDisplay.value = "error received with message = ${exception.message}"
                }
            })
        }
    }

    fun onSelectionChanged(listItem: MovieListItem, isChecked: Boolean) {
        if (isChecked) {
            movieListRepository.addToFavourites(listItem)
        } else {
            movieListRepository.removeFromFavourites(listItem)
        }
        textToDisplay.value = "movie ${listItem.name} is favourite = $isChecked"
    }

    fun onTextDisplayed() {
        textToDisplay.value = null
    }

}


class MovieListViewModelFactory(private val fragmentName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieListViewModel(fragmentName) as T
    }

}