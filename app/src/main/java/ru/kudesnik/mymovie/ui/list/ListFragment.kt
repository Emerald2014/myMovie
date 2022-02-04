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
import ru.kudesnik.mymovie.ui.favourite.FavouriteFragment


class ListFragment : Fragment() {
    private val viewModel: ListViewModel by viewModel()
    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: ListFragmentAdapter? = null

    private lateinit var changeMovieCategory: MovieCategory

    private lateinit var movieCat: MovieCategory
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
            viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })

            arguments?.getParcelable<MovieCategory>(BUNDLE_EXTRA)?.let {
                when (it) {
                    MovieCategory.COMEDY -> {
                        viewModel.getMovieListFromServer(MovieCategory.COMEDY.nameMovie)
//                        viewModel.getMovieFromLocalSource(MovieCategory.COMEDY)
                        toolbar.subtitle = MovieCategory.COMEDY.nameMovie
                    }
                    MovieCategory.ACTION -> {
                        toolbar.subtitle = MovieCategory.ACTION.nameMovie
                        viewModel.getMovieListFromServer(MovieCategory.ACTION.nameMovie)
//                        viewModel.getMovieFromLocalSource(MovieCategory.ACTION)
                    }
                    MovieCategory.FANTASTIC -> {
                        viewModel.getMovieListFromServer(MovieCategory.FANTASTIC.nameMovie)
                        toolbar.subtitle = MovieCategory.FANTASTIC.nameMovie
//                        viewModel.getMovieFromLocalSource(MovieCategory.FANTASTIC)
                    }
                    MovieCategory.MULT -> {
                        toolbar.subtitle = MovieCategory.MULT.nameMovie
//                        viewModel.getMovieFromLocalSource(MovieCategory.MULT)
                    }
                }
            }
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
                R.id.menu_favourites -> {
                    parentFragmentManager.apply {
                        beginTransaction()
                            .add(R.id.container, FavouriteFragment.newInstance())
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                    true
                }
                R.id.popup_menu_movie_comedy -> {
                    changeMovieCategory = MovieCategory.COMEDY
                    viewModel.getMovieFromLocalSource(MovieCategory.COMEDY)
                    toolbar.subtitle = MovieCategory.COMEDY.nameMovie
                    true
                }
                R.id.popup_menu_movie_action -> {
                    changeMovieCategory = MovieCategory.ACTION
                    viewModel.getMovieFromLocalSource(MovieCategory.ACTION)
                    toolbar.subtitle = MovieCategory.ACTION.nameMovie
                    true
                }
                R.id.popup_menu_movie_fantastic -> {
                    changeMovieCategory = MovieCategory.FANTASTIC
                    viewModel.getMovieFromLocalSource(MovieCategory.FANTASTIC)
                    toolbar.subtitle = MovieCategory.FANTASTIC.nameMovie
                    true
                }
                R.id.popup_menu_movie_mult -> {
                    changeMovieCategory = MovieCategory.MULT
                    viewModel.getMovieFromLocalSource(MovieCategory.MULT)
                    toolbar.subtitle = MovieCategory.MULT.nameMovie
                    true
                }
                else -> false
            }
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            popupMenu.setForceShowIcon(true)
//        }

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
                listFragmentRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getMovieFromLocalSource(
                            MovieCategory.COMEDY
                        )
                    })

                /*Snackbar
                    .make(
                        binding.mainFragmentFAB, getString(R.string.error),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.reload)) {
                        viewModel.getMovieFromLocalSource(
                            MovieCategory.COMEDY
                        )
                    }
                    .show()*/
            }
            else -> {
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    companion object {
        const val BUNDLE_EXTRA = "movieCat"

        fun newInstance(bundle: Bundle): ListFragment {
            val fragment = ListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }
}