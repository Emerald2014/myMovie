package ru.kudesnik.mymovie.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.ItemFavouriteListBinding
import ru.kudesnik.mymovie.model.entities.Movie

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.RecyclerItemViewHolder>() {
    private var data: List<Movie> = arrayListOf()

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            ItemFavouriteListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size


    inner class RecyclerItemViewHolder(private val binding: ItemFavouriteListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie) = with(binding) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
//                itemMovieNameTest.text = data.name
                itemFavouriteMovieName.text = data.name
                itemFavouriteRating.text = data.rating.toString()
                itemFavouritePoster.load(data.poster) {
                    crossfade(true)
                    placeholder(R.drawable.no_poster)
                }
                root.setOnClickListener {
                    Toast.makeText(itemView.context, data.name, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}