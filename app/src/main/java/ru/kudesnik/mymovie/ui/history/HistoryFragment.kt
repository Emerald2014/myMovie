package ru.kudesnik.mymovie.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.kudesnik.mymovie.databinding.HistoryFragmentBinding
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.ui.adapters.HistoryAdapter

class HistoryFragment : Fragment() {
    private var _binding: HistoryFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var deleteButton: ImageButton
    private val viewModel: HistoryViewModel by inject()

    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter(object :
            OnItemViewLongClickListener {
            override fun onItemViewLongClick(movie: Movie) {
                viewModel.deleteMovie(movie)
                viewModel.getAllHistory()
            }

        }, object: OnDoComment{
            override fun onDoComment(movie: Movie) {
                viewModel.updateMovie(movie)
//                viewModel.getAllHistory()

            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        historyFragmentRecyclerView.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAllHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                historyFragmentRecyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                adapter.setData(appState.movieData)
            }
            is AppState.Loading -> {
                historyFragmentRecyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                progressBar.visibility = View.GONE
            }
        }
    }

    interface OnItemViewLongClickListener {
        fun onItemViewLongClick(movie: Movie)
    }

    interface OnDoComment {
        fun onDoComment(movie: Movie)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}