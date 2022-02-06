package ru.kudesnik.mymovie.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.FragmentFavouriteBinding
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.ui.adapters.FavouriteAdapter
import ru.kudesnik.mymovie.ui.adapters.ListFragmentAdapter
import ru.kudesnik.mymovie.ui.details.DetailsFragment
import ru.kudesnik.mymovie.ui.list.ListFragment

class FavouriteFragment : Fragment() {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouriteViewModel by inject()
//    private var adapter: FavouriteAdapter by lazy { FavouriteAdapter() }
private var adapter: FavouriteAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        favouriteFragmentRecyclerView.adapter = adapter
        viewModel.favouriteLiveData.observe(viewLifecycleOwner, { renderdata(it) })
        viewModel.getAllFavourites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun renderdata(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                favouriteFragmentRecyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
//                adapter.setData(appState.movieData)
//                adapter = ListFragmentAdapter(object : ListFragment.OnItemViewClickListener {
                adapter = FavouriteAdapter(object : OnItemViewClickListener {
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
                })
            }
                        is AppState.Loading -> {
                    favouriteFragmentRecyclerView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                is AppState.Error -> {
                    progressBar.visibility = View.GONE


                }
            }
        }
    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }
        companion object {
        fun newInstance() = FavouriteFragment()
    }
    }