package com.example.movapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.example.movapp.MovieApplication
import com.example.movapp.R
import com.example.movapp.data.MovieListItem
import com.example.movapp.roomdb.AppDatabase

class MovieDetailedActivity : AppCompatActivity() {
    companion object {
        private const val BUNDLE_KEY_MOVIE_ITEM = "movie_item"
        fun getLaunchingIntent(movieListItem: MovieListItem, context: Context) : Intent {
            return Intent(context, MovieDetailedActivity::class.java).apply {
                putExtra(BUNDLE_KEY_MOVIE_ITEM, movieListItem)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detailed_view_item)

        val listItem: MovieListItem = intent.getSerializableExtra(BUNDLE_KEY_MOVIE_ITEM) as MovieListItem

        findViewById<AppCompatTextView>(R.id.uTitle).text = listItem.name
        findViewById<AppCompatTextView>(R.id.uDescription).text = listItem.description
        findViewById<AppCompatTextView>(R.id.uImdbRating).text = "Imdb: ${listItem.imdbRating?:"no rating"}"

        findViewById<AppCompatImageView>(R.id.uImage).let{
            Glide.with(this).load(listItem.imageUrl).into(it)
        }

        findViewById<AppCompatButton>(R.id.uHideButton).setOnClickListener {
            listItem.isHidden = true
            MovieApplication.getDb().movieItemDao().insert(listItem)
            Toast.makeText(this, "Movie hidden successfully", Toast.LENGTH_SHORT).show()
        }
    }
}