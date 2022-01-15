package ru.kudesnik.mymovie.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.MainFragmentBinding
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.model.entities.MovieCategory
import ru.kudesnik.mymovie.ui.adapters.MainFragmentAdapter
import ru.kudesnik.mymovie.ui.details.DetailsFragment


class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: MainFragmentAdapter? = null
    private var isDataSetComedy: Boolean = true

    private lateinit var changeMovieCategory: MovieCategory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            changeMovieDataSet(view)
//            mainFragmentFAB.setOnClickListener { changeMovieDataSet() }
            viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
            viewModel.getMovieFromLocalSourceComedy()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeMovieDataSet(view: View) = with(binding) {
        val popupMenuButton = mainFragmentFAB
        val popupMenu: PopupMenu? = view?.let { PopupMenu(requireContext(), popupMenuButton) }

        if (popupMenu != null) {
            popupMenu
                .inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
                when (it.itemId) {
                    R.id.popup_menu_movie_comedy -> {
                        changeMovieCategory = MovieCategory.COMEDY
                        viewModel.getMovieFromLocalSourceComedy()
                        true
                    }
                    R.id.popup_menu_movie_action -> {
                        changeMovieCategory = MovieCategory.ACTION
                        viewModel.getMovieFromLocalSourceAction()
                        true
                    }
                    R.id.popup_menu_movie_fantastic -> {
                        changeMovieCategory = MovieCategory.FANTASTIC
//                        viewModel.getMovieFromLocalSourceAction()
                        true
                    }

                    else -> false
                }
            })
        }
        popupMenuButton.setOnClickListener {
            if (popupMenu != null) {
                popupMenu.show()
            }
        }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                progressBar.visibility = View.GONE
                adapter = MainFragmentAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(movie: Movie) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(DetailsFragment.BUNDLE_EXTRA, movie)
                            }
                            manager.beginTransaction()
                                .add(R.id.container, DetailsFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
                    }
                }).apply {
                    setMovie(appState.movieData)
                }
                mainFragmentRecyclerView.adapter = adapter
            }
            is AppState.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                progressBar.visibility = View.GONE
                Snackbar
                    .make(
                        binding.mainFragmentFAB, getString(R.string.error),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.reload)) { viewModel.getMovieFromLocalSourceComedy() }
                    .show()
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}