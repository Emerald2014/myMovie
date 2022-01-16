package ru.kudesnik.mymovie.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.mymovie.databinding.ListFragmentRecyclerItemBinding
import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.ui.list.ListFragment

class ListFragmentAdapter(private val itemClickListener: ListFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<ListFragmentAdapter.ListViewHolder>() {
    private var movieData: List<Movie> = listOf()
    private lateinit var binding: ListFragmentRecyclerItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setMovie(data: List<Movie>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        binding = ListFragmentRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount() = movieData.size

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) = with(binding) {
            mainFragmentRecyclerItemTextView.text = movie.name
            mainFragmentRecyclerItemDirector.text = movie.director
            mainFragmentRecyclerItemYear.text = movie.year.toString()
            mainFragmentRecyclerItemRating.text = movie.rating.toString()
            root.setOnClickListener { itemClickListener.onItemViewClick(movie) }
        }
    }
}
