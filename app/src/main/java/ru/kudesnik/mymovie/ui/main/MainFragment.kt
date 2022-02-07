package ru.kudesnik.mymovie.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.parcelize.Parcelize
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.MainFragmentBinding
import ru.kudesnik.mymovie.model.entities.MovieCategory
import ru.kudesnik.mymovie.model.repository.shortMovieLength
import ru.kudesnik.mymovie.ui.adapters.MainFragmentAdapter
import ru.kudesnik.mymovie.ui.list.ListFragment
import kotlin.properties.Delegates

private const val dataSetKey = "dataSetKey"
var tempVar:String = ""

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var mainFragmentAdapter: MainFragmentAdapter? = null
    private lateinit var movieCat: MovieCategory
    private var isShortMovieLength by Delegates.notNull<Boolean>()

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

        initDataSet()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initDataSet() = with(binding) {
        loadDataSet()
        shortMovieLengthCheckBox.isChecked = isShortMovieLength
        shortMovieLengthCheckBox.setOnClickListener {
            isShortMovieLength = shortMovieLengthCheckBox.isChecked
            saveDataSetToDisk()
        }
    }

    private fun loadDataSet() {
        activity?.let {
            isShortMovieLength = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getBoolean(dataSetKey, true) ?: true
        }
    }

    private fun saveDataSetToDisk() {
        val editor = activity?.getPreferences(Context.MODE_PRIVATE)?.edit()
        editor?.putBoolean(dataSetKey, isShortMovieLength)
        editor?.apply()
    }

    private fun renderDataM() = with(binding) {
        llAction.setOnClickListener(View.OnClickListener {
            tempVar = "боевик"
            movieCat = MovieCategory.ACTION
            doFragmentTransToList(movieCat)
        })
        llComedy.setOnClickListener(View.OnClickListener {
            tempVar = "комедия"
            movieCat = MovieCategory.COMEDY
            doFragmentTransToList(movieCat)
        })
        llFantastic.setOnClickListener(View.OnClickListener {
            tempVar = "фантастика"
            movieCat = MovieCategory.FANTASTIC
            doFragmentTransToList(movieCat)
        })
        llMult.setOnClickListener(View.OnClickListener {

//            Toast.makeText(requireContext(), doURL(), Toast.LENGTH_LONG).show()
//            movieCat = MovieCategory.MULT
//            doFragmentTransToList(movieCat)
        })
    }


    private fun doFragmentTransToList(movieCategory: MovieCategory) {
        val manager = activity?.supportFragmentManager
        manager?.let { manager ->
            val bundle = Bundle().apply {
                                putParcelable(ListFragment.BUNDLE_EXTRA, tempVar)
                putString("keyString", tempVar)
                putBoolean("keyBoolean", isShortMovieLength)
            }
            manager.beginTransaction()
                .add(R.id.container, ListFragment.newInstance(bundle))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun putParcelable(bundleExtra: String, tempVar: String) {

    }

    companion object {
        fun newInstance() = MainFragment()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movieCategory: MovieCategory)
    }
}
