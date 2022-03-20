package com.example.movapp.data

import androidx.fragment.app.Fragment
import com.example.movapp.ui.MovieListFragment

object FragmentPagerData {
    private val fragmentList = listOf<ViewPagerFragmentProvider>(
        MovieFragmentProvider(MovieFragmentType.FAVOURITES),
        MovieFragmentProvider(MovieFragmentType.ALL)
    )

    fun getMovieFragmentProviderList(): List<ViewPagerFragmentProvider> = fragmentList
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
    FAVOURITES("Favourites"), ALL("All Movies")
}