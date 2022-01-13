package ru.kudesnik.mymovie.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.mymovie.databinding.MainFragmentBinding
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.entities.Movie


class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observer = Observer<AppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.movieData
                progressBar.visibility = View.GONE
                movieGroup.visibility = View.VISIBLE
                setData(movieData)
            }
            is AppState.Loading -> {
                movieGroup.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                progressBar.visibility = View.GONE
                movieGroup.visibility = View.INVISIBLE
                Snackbar
                    .make(mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Relload") { viewModel.getMovies() }
                    .show()
            }
        }
    }

    private fun setData(movieData: Movie) = with(binding) {
        nameMovie.text = movieData.name
        ratingMovie.text = movieData.rating.toString()
        yearMovie.text = movieData.year.toString()
        directorMovie.text = movieData.director
        categoryMovie.text = movieData.category
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}