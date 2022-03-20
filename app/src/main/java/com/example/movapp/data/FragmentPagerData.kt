package com.example.movapp.data

import androidx.fragment.app.Fragment
import com.example.movapp.ui.MovieListFragment
import java.lang.UnsupportedOperationException

object FragmentPagerData {
    private val fragmentList = listOf<ViewPagerFragmentProvider>(
        MovieFragmentProvider(MovieFragmentType.FAVOURITES),
        MovieFragmentProvider(MovieFragmentType.ALL)
    )

    fun getMovieFragmentProviderList(): List<ViewPagerFragmentProvider> = fragmentList

    fun getMovieListRepository(forFragmentType: MovieFragmentType): MovieListRepository{
        return MockMovieListRepository(forFragmentType)
    }

}

interface MovieListRepository{
    fun provideMovieList(page: Int = 0, callback: (list: List<MovieListItem>) -> Unit)
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