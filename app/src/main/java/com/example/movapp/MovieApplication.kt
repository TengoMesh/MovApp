package com.example.movapp

import android.app.Application
import androidx.room.Room
import com.example.movapp.roomdb.AppDatabase

class MovieApplication : Application() {

    companion object {
        private var appDb: AppDatabase? = null
        fun getDb() = appDb!!
    }

    override fun onCreate() {
        super.onCreate()
        instantiateRoomDb()
    }

    private fun instantiateRoomDb() {
        synchronized(MovieApplication::class.java) {
            if (appDb == null) {
                appDb = Room.databaseBuilder(this, AppDatabase::class.java, "movie_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}