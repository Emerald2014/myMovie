package ru.kudesnik.mymovie.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.MainFragmentBinding
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.entities.MovieCategory
import ru.kudesnik.mymovie.ui.adapters.MainFragmentAdapter
import ru.kudesnik.mymovie.ui.list.ListFragment


/*
Вешаем слушатель, пробую на вход адаптера передать не лист, а листенер
 */

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movieCategory: MovieCategory)
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var mainFragmentAdapter: MainFragmentAdapter? = null

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

        // Из методички
//        binding.mainFragmentRV.adapter = mainFragmentAdapter
        viewModelM = ViewModelProvider(this)[MainViewModel::class.java]
        viewModelM.getLiveData().observe(viewLifecycleOwner, Observer { renderDataM(it) })
        viewModelM.getMovieCategory()

//        val observer = Observer<Any> { renderDataM(it) }
//        viewModelM.getData().observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderDataM(appState: AppState) =with(binding) {
        llComedy.setOnClickListener(View.OnClickListener {
            val manager = activity?.supportFragmentManager
            manager?.let { manager ->
                val bundle = Bundle().apply {
//                    putParcelable(ListFragment.BUNDLE_EXTRA_COMEDY, movieCategory)
                }
                            manager.beginTransaction()
                                .add(R.id.container, ListFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
            }
        })
/*        when (appState) {
            is AppState.SuccessCat -> {
//                Snackbar.make(binding.mainFragmentRV, "Success", Snackbar.LENGTH_LONG).show()
                mainFragmentAdapter?.setMovieCategory(appState.movieData)

                mainFragmentAdapter = MainFragmentAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(movieCategory: MovieCategory) {
                        Toast.makeText(context, "RenderDataM", Toast.LENGTH_SHORT).show()

                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(ListFragment.BUNDLE_EXTRA, movieCategory)
                            }
//                            manager.beginTransaction()
//                                .add(R.id.container, ListFragment.newInstance(bundle))
//                                .addToBackStack("")
//                                .commitAllowingStateLoss()
                        }
                    }
                }).apply {
                    setMovieCategory(appState.movieData)
                }
//                mainFragmentRV.adapter = mainFragmentAdapter
            }
            {
                val movieData = appState.movieData
                setData(movieData)
                Snackbar.make(binding.mainFragmentRV, "Success", Snackbar.LENGTH_LONG).show()
                mainFragmentAdapter.setMovieCategory(appState.movieData)
            }


            is AppState.Loading -> {}
            is AppState.Error -> {}*/


    }
    private fun setData(movieData: List<MovieCategory>) = with(binding) {

    }
}
//        val recyclerView: RecyclerView = view.findViewById(R.id.mainFragmentRV)
//        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
//        recyclerView.adapter = MainFragmentAdapter(getMovieCategory())

/*

        recyclerView.adapter = MainFragmentAdapter(object : MainFragmentAdapter.OnMainViewClickListener {
            override fun onItemViewClick(movieCategory: MovieCategory) {
                                Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT)

//                val manager = activity?.supportFragmentManager
//                manager?.let { manager ->
//                    val bundle = Bundle().apply {
//                        putParcelable(DetailsFragment.BUNDLE_EXTRA, movieCategory)
//                    }
//                    manager.beginTransaction()
//                        .add(R.id.container, DetailsFragment.newInstance(bundle))
//                        .addToBackStack("")
//                        .commitAllowingStateLoss()
//                }
            }


        })
//            override fun onItemViewClick(movieCategory: MovieCategory) {
//                Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT)
            }

//
//
//            }
//        })
//    }
            /*
        adapter?.onItemViewClickListener =
            object : MainFragmentAdapter.OnItemViewClickListener {
                override fun onItemViewClick(movieCategory: MovieCategory) {
                    Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT)
                    val manager = activity?.supportFragmentManager
                    manager?.let { manager ->
                        val bundle = Bundle().apply {
                            putParcelable(ListFragment.BUNDLE_EXTRA, movieCategory)
                        }
                        manager.beginTransaction()
                            .add(R.id.container, DetailsFragment.newInstance(bundle))
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                }
            }
    }

         */

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