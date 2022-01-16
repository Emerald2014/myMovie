package ru.kudesnik.mymovie.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.MainFragmentBinding
import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.model.entities.MovieCategory
import ru.kudesnik.mymovie.model.entities.getMovieCategory
import ru.kudesnik.mymovie.ui.adapters.MainFragmentAdapter
import ru.kudesnik.mymovie.ui.details.DetailsFragment
import ru.kudesnik.mymovie.ui.list.ListFragment

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: MainFragmentAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.mainFragmentRV)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = MainFragmentAdapter(getMovieCategory())
    }
       /* with(binding) {
            mainFragmentRV
                .adapter = adapter

            mainFragmentRV.layoutManager = LinearLayoutManager(requireContext())
            mainFragmentRV.adapter = MainFragmentAdapter(object :
                ListFragment.OnItemViewClickListener {
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
                setMovieCategory(appState.movieData)
            }
            mainFragmentRV.adapter = adapter})
        }


 */
    private fun fillList(): List<String> {
        val data = mutableListOf<String>()
        (0..30).forEach { i -> data.add("$i element") }
        return data
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movieCategory: MovieCategory)
    }
}

/*
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: MainFragmentAdapter? = null

    private lateinit var changeMovieCategory: MovieCategory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            mainFragmentRV.adapter = adapter

//            mainFragmentFAB.setOnClickListener { changeMovieDataSet() }
            viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
//            viewModel.getMovieFromLocalSourceComedy()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun renderData(appState: AppState) = with(binding) {
                adapter = MainFragmentAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(movieCategory: MovieCategory) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(ListFragment.BUNDLE_EXTRA, movieCategory)
                            }
                            manager.beginTransaction()
                                .add(R.id.container, ListFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
                    }
                }).apply {
//                    setMovieCategory(List<MovieCategory>()()())
//                    setMovie(appState.movieData)
                }
                mainFragmentRV.adapter = adapter
            }






}

 */