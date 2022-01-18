package ru.kudesnik.mymovie.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.MainFragmentBinding
import ru.kudesnik.mymovie.model.entities.MovieCategory
import ru.kudesnik.mymovie.ui.adapters.MainFragmentAdapter
import ru.kudesnik.mymovie.ui.list.ListFragment

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var mainFragmentAdapter: MainFragmentAdapter? = null
    private lateinit var movieCat: MovieCategory

    private lateinit var viewModelM: MainViewModel
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

        viewModelM = ViewModelProvider(this)[MainViewModel::class.java]
        viewModelM.getLiveData().observe(viewLifecycleOwner, Observer { renderDataM() })
        viewModelM.getMovieCategory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderDataM() = with(binding) {
        llAction.setOnClickListener(View.OnClickListener {
            movieCat = MovieCategory.ACTION
            doFragmentTransToList(movieCat)
        })
        llComedy.setOnClickListener(View.OnClickListener {
            movieCat = MovieCategory.COMEDY
            doFragmentTransToList(movieCat)
        })
        llFantastic.setOnClickListener(View.OnClickListener {
            movieCat = MovieCategory.FANTASTIC
            doFragmentTransToList(movieCat)
        })
        llMult.setOnClickListener(View.OnClickListener {
            movieCat = MovieCategory.MULT
            doFragmentTransToList(movieCat)
        })
    }

    private fun doFragmentTransToList(movieCategory: MovieCategory) {
        val manager = activity?.supportFragmentManager
        manager?.let { manager ->
            val bundle = Bundle().apply {
                putParcelable(ListFragment.BUNDLE_EXTRA, movieCat)
            }
            manager.beginTransaction()
                .add(R.id.container, ListFragment.newInstance(bundle))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movieCategory: MovieCategory)
    }
}
