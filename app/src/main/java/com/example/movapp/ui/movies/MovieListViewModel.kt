package com.example.movapp.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movapp.data.FragmentPagerData
import com.example.movapp.data.MovieFragmentType
import com.example.movapp.data.MovieListItem
import com.example.movapp.data.MovieListRepository

class MovieListViewModel(fragmentName: String) : ViewModel() {

    var movieList = MutableLiveData<List<MovieListItem>>()
        private set

    var textToDisplay = MutableLiveData<String?>()

    private var currentPageLoaded = 0;

    private val movieListRepository: MovieListRepository = FragmentPagerData.getMovieListRepository(
        MovieFragmentType.fromTabName(fragmentName)
    )


    fun provideListNextPage(){
        movieListRepository.provideMovieList(currentPageLoaded++){
                list ->
            movieList.value = list
//           = mutableListOf<MovieListItem>().apply {
//                addAll(movieList.value?: emptyList());
//                addAll(list)
//            }
        }
    }

    fun onSelectionChanged(listItem: MovieListItem, isChecked: Boolean){
        if(isChecked){
            movieListRepository.addToFavourites(listItem)
        }else{
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