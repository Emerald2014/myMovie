package ru.kudesnik.mymovie.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.kudesnik.mymovie.databinding.FragmentFavouriteBinding
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.ui.adapters.FavouriteAdapter

class FavouriteFragment : Fragment() {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouriteViewModel by inject()
    private val adapter: FavouriteAdapter by lazy { FavouriteAdapter() }

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
                adapter.setData(appState.movieData)
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
    companion object {
        fun newInstance() = FavouriteFragment()
    }
}