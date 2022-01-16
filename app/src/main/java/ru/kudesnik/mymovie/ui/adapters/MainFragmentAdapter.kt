package ru.kudesnik.mymovie.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.MainFragmentRecyclerItemBinding
import ru.kudesnik.mymovie.model.entities.MovieCategory
import ru.kudesnik.mymovie.ui.list.ListFragment
import ru.kudesnik.mymovie.ui.main.MainFragment

class MainFragmentAdapter(private val itemClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private lateinit var binding: MainFragmentRecyclerItemBinding
    private var movieCategoryData: List<MovieCategory> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setMovieCategory(data: List<MovieCategory>) {
        movieCategoryData = data
        notifyDataSetChanged()
    }
    /*




     */

   inner  class MainViewHolder(view: View ) : RecyclerView.ViewHolder(view) {
        fun bind(movieCategory: MovieCategory) = with(binding) {
            val largeTextView: TextView = itemView.findViewById(R.id.start_fragment_text_category)

            startFragmentTextCategory.text = movieCategory.nameMovie
            startFragmentImageCategory.setImageResource(movieCategory.icon)
            root.setOnClickListener { itemClickListener.onItemViewClick(movieCategory) }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainFragmentAdapter.MainViewHolder {
//        binding = MainFragmentRecyclerItemBinding.inflate(
//            LayoutInflater.from(parent.context), parent, false
//        )
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_fragment_recycler_item, parent, false)
//        return MainFragmentAdapter.MainViewHolder(itemView)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) = with(binding){
//        holder.largeTextView.text = names[position]
//        startFragmentTextCategory.text = movieCategory.nameMovie
//        startFragmentImageCategory.setImageResource(movieCategory.icon)

        holder.bind(movieCategoryData[position])
    }

    override fun getItemCount() = movieCategoryData.size


}

/*
class MainFragmentAdapter(private val itemClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var movieCategoryData: List<MovieCategory> = listOf()
    private lateinit var binding: MainFragmentRecyclerItemBinding



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        binding = MainFragmentRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movieCategoryData[position])
    }

    override fun getItemCount() = movieCategoryData.size


}

 */
