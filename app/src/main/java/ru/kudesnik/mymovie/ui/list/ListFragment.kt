package ru.kudesnik.mymovie.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.ListFragmentBinding
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.model.entities.MovieCategory
import ru.kudesnik.mymovie.ui.adapters.ListFragmentAdapter
import ru.kudesnik.mymovie.ui.details.DetailsFragment


class ListFragment : Fragment() {
    private val viewModel: ListViewModel by viewModel()
    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: ListFragmentAdapter? = null

    private lateinit var changeMovieCategory: MovieCategory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            listFragmentRecyclerView.adapter = adapter
            changeMovieDataSet()
//            mainFragmentFAB.setOnClickListener { changeMovieDataSet() }
            viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
            viewModel.getMovieFromLocalSourceComedy()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeMovieDataSet() = with(binding) {
        val popupMenuButton = mainFragmentFAB
        val popupMenu = PopupMenu(requireContext(), popupMenuButton)

        popupMenu
            .inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.popup_menu_movie_comedy -> {
                    changeMovieCategory = MovieCategory.COMEDY
                    viewModel.getMovieFromLocalSourceComedy()
                    toolbar.subtitle = MovieCategory.COMEDY.nameMovie

                    true
                }
                R.id.popup_menu_movie_action -> {
                    changeMovieCategory = MovieCategory.ACTION
                    viewModel.getMovieFromLocalSourceAction()
                    toolbar.subtitle = MovieCategory.ACTION.nameMovie
                    true
                }
                R.id.popup_menu_movie_fantastic -> {
                    changeMovieCategory = MovieCategory.FANTASTIC
//                        viewModel.getMovieFromLocalSourceFantastic()
                    toolbar.subtitle = MovieCategory.FANTASTIC.nameMovie

                    true
                }

                else -> false
            }
        }
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }

 */
        popupMenuButton.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                progressBar.visibility = View.GONE
                adapter = ListFragmentAdapter(object : OnItemViewClickListener {
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
                listFragmentRecyclerView.adapter = adapter
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
       const val BUNDLE_EXTRA_COMEDY="COMEDY"
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle) = ListFragment()
    }
}