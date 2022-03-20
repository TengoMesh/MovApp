package com.example.movapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieListViewModel(fragmentName: String) : ViewModel() {

    var name = MutableLiveData<String>().apply { value = fragmentName }
     private set

}


class MovieListViewModelFactory(private val fragmentName: String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieListViewModel(fragmentName) as T
    }
}