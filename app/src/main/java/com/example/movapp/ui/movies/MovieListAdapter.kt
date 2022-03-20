package com.example.movapp.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movapp.R
import com.example.movapp.data.MovieListItem

class MovieListAdapter(
    private val favouriteChangeListener: (movieItem: MovieListItem, isFavouriteChecked: Boolean) -> Unit
) :
    ListAdapter<MovieListItem, MovieListAdapter.ViewHolder>(MovieDifUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from( favouriteChangeListener, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val favouriteChangeListener: (movieItem: MovieListItem, isFavouriteChecked: Boolean) -> Unit,
        view: View
    ) : RecyclerView.ViewHolder(view) {

        private val uImage = view.findViewById<AppCompatImageView>(R.id.uImage)
        private val uName = view.findViewById<AppCompatTextView>(R.id.uMovieName)
        private val uIsFavouriteSwitch = view.findViewById<SwitchCompat>(R.id.uFavouriteSwitch)


        fun bind(item: MovieListItem) {
            Glide.with(uImage.context).load(item.imageUrl).into(uImage)
            uName.text = item.name
            uIsFavouriteSwitch.isChecked = item.isFavourite
            uIsFavouriteSwitch.setOnCheckedChangeListener { _, isChecked ->
                if(item.isFavourite != isChecked) {
                    favouriteChangeListener(item, isChecked)
                }
            }
        }


        companion object {
            fun from(
                favouriteChangeListener: (movieItem: MovieListItem, isFavouriteChecked: Boolean) -> Unit,
                parent: ViewGroup
            ): ViewHolder = ViewHolder(
                favouriteChangeListener,
                LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
            )
        }
    }
}

class MovieDifUtils : DiffUtil.ItemCallback<MovieListItem>() {

    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return oldItem.id == newItem.id
    }
}
