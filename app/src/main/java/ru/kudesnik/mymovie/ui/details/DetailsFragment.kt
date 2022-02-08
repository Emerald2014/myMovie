package ru.kudesnik.mymovie.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.api.load
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.DetailsFragmentBinding
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.entities.Movie


class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModel()
    var isFavourite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addToFavourites()
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let {
            with(binding) {
                idMovie.text = it.id.toString()
                nameMovie.text = it.name
                ratingMovie.text = it.rating.toString()
                yearMovie.text = it.year.toString()
                directorMovie.text = it.director
                genresMovie.text = it.category

                viewModel.movieLiveData.observe(viewLifecycleOwner, { appState ->
                    when (appState) {
                        is AppState.Error -> {
                            mainView.visibility = View.INVISIBLE
                        }
                        AppState.Loading -> {
                            mainView.visibility = View.INVISIBLE
                        }
                        is AppState.Success -> {
                            mainView.visibility = View.VISIBLE
                            idMovie.text = appState.movieData[0].id.toString()
                            nameMovie.text = appState.movieData[0].name
                            ratingMovie.text = appState.movieData[0].rating.toString()
                            yearMovie.text = appState.movieData[0].year.toString()
                            directorMovie.text = appState.movieData[0].director
                            genresMovie.text = appState.movieData[0].category
                            poster.load(appState.movieData[0].poster) {
                                crossfade(true)
                                placeholder(R.drawable.no_poster)
                            }
                        }
                    }
                })
            }
            viewModel.loadData(it.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun addToFavourites() = with(binding) {
        val floatButton = FABFavourites
        floatButton.setOnClickListener {
            if (isFavourite == false) {
                isFavourite = true
                Toast.makeText(requireContext(), "Добавлено в избранное", Toast.LENGTH_SHORT).show()
            } else {
                isFavourite = false
                Toast.makeText(requireContext(), "Удалено из избранного", Toast.LENGTH_SHORT).show()
            }

        }
    }


    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}