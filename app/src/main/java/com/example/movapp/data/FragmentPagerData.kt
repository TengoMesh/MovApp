package com.example.movapp.data

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.movapp.ui.movies.MovieListFragment
import java.lang.UnsupportedOperationException

object FragmentPagerData {


    private val fragmentList = listOf<ViewPagerFragmentProvider>(
        MovieFragmentProvider(MovieFragmentType.FAVOURITES),
        MovieFragmentProvider(MovieFragmentType.ALL)
    )

    fun getMovieFragmentProviderList(): List<ViewPagerFragmentProvider> = fragmentList

    fun getMovieListRepository(): MovieListRepository{
        return ImdbMovieListRepository()
    }

}

interface MovieListRepository{
    fun provideMovieList(page: Int = 0, callback: Callback)
    fun addToFavourites(listItem: MovieListItem)
    fun removeFromFavourites(listItem: MovieListItem)
    fun getFavouritesLiveData(): LiveData<List<MovieListItem>>

    interface Callback{
        fun onSuccess(list: List<MovieListItem>)
        fun onError(exception: Throwable)
    }
}

interface ViewPagerFragmentProvider{
    fun getName(): String
    fun createFragment(): Fragment
}

class MovieFragmentProvider(private val fragmentType: MovieFragmentType): ViewPagerFragmentProvider{
    override fun getName() = fragmentType.tabName

    override fun createFragment(): Fragment = MovieListFragment.newInstance(fragmentType)
}
enum class MovieFragmentType(val tabName: String) {
    FAVOURITES("Favourites"), ALL("All Movies");
    companion object {
        fun fromTabName(tabName: String): MovieFragmentType{
            return when(tabName){
                FAVOURITES.tabName ->
                    FAVOURITES
                ALL.tabName ->
                    ALL
                else -> throw UnsupportedOperationException("couldn't find enum for tabname = $tabName")
            }

        }
    }


}