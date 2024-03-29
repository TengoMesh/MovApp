package com.example.movapp.ui.movies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movapp.R
import com.example.movapp.data.MovieFragmentType
import com.example.movapp.databinding.MovieListFragmentBinding
import com.example.movapp.ui.MovieDetailedActivity

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
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentFactory =
            MovieListViewModelFactory(requireArguments().getString(BUNDLE_TYPE_NAME)!!)
        viewModel = ViewModelProvider(this, fragmentFactory).get(MovieListViewModel::class.java)
        movieListAdapter = MovieListAdapter(viewModel)
        val binding = setupBinding(inflater, container)

        viewModel.movieList.observe(viewLifecycleOwner) {
            movieListAdapter.submitList(it)
            movieListAdapter.notifyDataSetChanged()
        }

        viewModel.textToDisplay.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.onTextDisplayed()
            }
        }

        viewModel.itemToLaunch.observe(viewLifecycleOwner){
            it?.let { movieItem ->
                context?.let {
                    it.startActivity(MovieDetailedActivity.getLaunchingIntent(movieItem, it))
                }
                viewModel.onInnerPageLaunched()
            }
        }

        viewModel.shouldSearchBeDisplayed.observe(viewLifecycleOwner){
            if(it) {
                binding.uSearch.visibility = View.VISIBLE
            }else{
                binding.uSearch.visibility = View.GONE
            }
        }

        binding.uSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onQueryUpdated(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    private fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MovieListFragmentBinding {
        val binding =
            DataBindingUtil.inflate<MovieListFragmentBinding>(
                inflater,
                R.layout.movie_list_fragment,
                container,
                false
            )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.uMovieList.layoutManager = LinearLayoutManager(context)
        binding.uMovieList.adapter = movieListAdapter

        return binding
    }

}