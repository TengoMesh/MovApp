package com.example.movapp.data

class MockMovieListRepository(private val type: MovieFragmentType) : MovieListRepository {

    override fun provideMovieList(page: Int, callback: (list: List<MovieListItem>) -> Unit) {
        callback((0..10).map {
            MovieListItem(
                "${10 * page + it}",
                "https://picsum.photos/200",
                type == MovieFragmentType.FAVOURITES
            )
        })
    }
}


