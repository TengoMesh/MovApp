package com.example.movapp.data

class MockMovieListRepository(private var type: MovieFragmentType) : MovieListRepository {

    companion object{
        var favourites = mutableListOf<MovieListItem>()

        fun getInstance(type: MovieFragmentType) = MockMovieListRepository(type)
    }

    override fun provideMovieList(page: Int, callback: (list: List<MovieListItem>) -> Unit) {
        val items = if (MovieFragmentType.FAVOURITES == type) {
            favourites
        } else {
            (0..10).map {
                MovieListItem(
                    it,
                    "${10 * page + it}",
                    "https://picsum.photos/200",
                    type == MovieFragmentType.FAVOURITES
                )
            }
        }
        print("providing $items")
        callback(items)
    }

    override fun addToFavourites(listItem: MovieListItem) {
        print("adding: $listItem")
        if (!favourites.contains(listItem)) {
            favourites.add(listItem)
        }
    }

    override fun removeFromFavourites(listItem: MovieListItem) {
        print("removing $listItem")
        favourites.removeIf { it.id == listItem.id }
    }

    private fun print(value: String){
        println("mock_movie_tag: $value")
    }
}


