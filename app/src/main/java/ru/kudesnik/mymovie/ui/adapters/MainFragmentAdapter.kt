package ru.kudesnik.mymovie.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.MainFragmentRecyclerItemBinding
import ru.kudesnik.mymovie.model.entities.MovieCategory
import ru.kudesnik.mymovie.ui.main.MainFragment

class MainFragmentAdapter(private val itemClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    var onItemViewClickListener: OnMainViewClickListener? = null

    private lateinit var binding: MainFragmentRecyclerItemBinding
    private var movieCategoryData: List<MovieCategory> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setMovieCategory(data: List<MovieCategory>) {
        movieCategoryData = data
        notifyDataSetChanged()
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movieCategory: MovieCategory) = with(binding) {
            startFragmentTextCategory.text = movieCategory.nameMovie
            startFragmentImageCategory.setImageResource(movieCategory.icon)
            root.setOnClickListener { itemClickListener.onItemViewClick(movieCategory) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_fragment_recycler_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movieCategoryData[position])
    }

    override fun getItemCount() = movieCategoryData.size

    interface OnMainViewClickListener {
        fun onItemViewClick(movieCategory: MovieCategory)
    }
}
