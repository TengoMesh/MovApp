package com.example.movapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.movapp.R
import com.example.movapp.data.MovieFragmentType
import com.example.movapp.databinding.MovieListFragmentBinding

class MovieListFragment : Fragment() {

    companion object {
        private const val BUNDLE_TYPE_NAME = "type_name"

        fun newInstance(fragmentType: MovieFragmentType) = MovieListFragment().apply {
            arguments = Bundle().apply {
                putString(BUNDLE_TYPE_NAME, fragmentType.tabName)
            }
        }
    }

    private lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentFactory =
            MovieListViewModelFactory(requireArguments().getString(BUNDLE_TYPE_NAME)!!)
        viewModel = ViewModelProvider(this, fragmentFactory).get(MovieListViewModel::class.java)


        val binding = DataBindingUtil.inflate<MovieListFragmentBinding>(
            inflater,
            R.layout.movie_list_fragment,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}