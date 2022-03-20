package com.example.movapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movapp.data.FragmentPagerData
import com.example.movapp.data.MovieFragmentType
import com.example.movapp.data.MovieListRepository

class MovieListViewModel(fragmentName: String) : ViewModel() {

    var name = MutableLiveData<String>().apply { value = fragmentName }
        private set

    private var currentPageLoaded = 0;

    val movieListRepository: MovieListRepository = FragmentPagerData.getMovieListRepository(
        MovieFragmentType.fromTabName(fragmentName)
    )

    init {
        movieListRepository.provideMovieList(currentPageLoaded++){
            list ->
            name.value = name.value + "\n\n page $currentPageLoaded \n" +
                     list.joinToString (separator = "\n")
        }
    }

}


class MovieListViewModelFactory(private val fragmentName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieListViewModel(fragmentName) as T
    }
}