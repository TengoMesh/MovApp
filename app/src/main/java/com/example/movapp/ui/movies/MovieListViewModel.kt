package com.example.movapp.ui.movies

import androidx.lifecycle.*
import com.example.movapp.data.FragmentPagerData
import com.example.movapp.data.MovieFragmentType
import com.example.movapp.data.MovieListItem
import com.example.movapp.data.MovieListRepository

class MovieListViewModel(fragmentName: String) : ViewModel(), MovieListAdapterCallback {

    var movieList = MutableLiveData<List<MovieListItem>>()
        private set

    var textToDisplay = MutableLiveData<String?>()

    var itemToLaunch = MutableLiveData<MovieListItem?>()

    private var currentSearchQuery: String? = null
    private var currentPageLoaded = 0

    private var type = MovieFragmentType.fromTabName(fragmentName)
    private val movieListRepository: MovieListRepository =
        FragmentPagerData.getMovieListRepository()


    fun onResume() {
        if (type == MovieFragmentType.FAVOURITES) {
            movieListRepository.getFavouritesLiveData().observeForever {
                updateMovieList(it)
            }
        }
    }

    fun onQueryUpdated(newSearchText: String?) {
        textToDisplay.value = newSearchText
        currentSearchQuery = newSearchText

        if (currentSearchQuery.isNullOrEmpty()) {
            textToDisplay.value = "the search expression should not be empty"
        } else {
            movieListRepository.searchForMovies(
                currentSearchQuery!!,
                object : MovieListRepository.Callback {
                    override fun onSuccess(list: List<MovieListItem>) {
                        updateMovieList(list)
                    }

                    override fun onError(exception: Throwable) {
                        textToDisplay.value =
                            "error received with message = ${exception.message}, keeping old content"
                    }
                })
        }
    }

    override fun onSelectionChanged(movieItem: MovieListItem, isFavouriteChecked: Boolean) {
        if (isFavouriteChecked) {
            movieListRepository.addToFavourites(movieItem)
        } else {
            movieListRepository.removeFromFavourites(movieItem)
        }
        textToDisplay.value = "movie ${movieItem.name} is favourite = $isFavouriteChecked"
    }

    override fun onItemSelected(movieItem: MovieListItem) {
        itemToLaunch.value = movieItem
    }

    fun onInnerPageLaunched() {
        itemToLaunch.value = null
    }

    fun onTextDisplayed() {
        textToDisplay.value = null
    }

    private fun updateMovieList(list: List<MovieListItem>) {
        movieList.value = list
//            if (currentSearchQuery.isNullOrEmpty()) {
//            list
//        } else {
//            list.filter { it.name.contains(currentSearchQuery!!) }
//        }
    }
}


class MovieListViewModelFactory(private val fragmentName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieListViewModel(fragmentName) as T
    }

}