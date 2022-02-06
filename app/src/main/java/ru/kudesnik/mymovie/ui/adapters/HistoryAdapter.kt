package ru.kudesnik.mymovie.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.mymovie.databinding.ItemHistoryFragmentBinding
import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.ui.history.HistoryFragment

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {
    private var data: List<Movie> = arrayListOf()

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            ItemHistoryFragmentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class RecyclerItemViewHolder(private val binding: ItemHistoryFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie) = with(binding) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                numberOfHistory.text = data.id.toString()
                nameMovieOfHistory.text = data.name
                root.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "on click: ${data.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

/*fun bind(data: Movie) = with(binding) {
    if (layoutPosition != RecyclerView.NO_POSITION) {
//                itemMovieNameTest.text = data.name
        numberOfHistory.text = data.id.toString()
        nameMovieOfHistory.text = data.name

        root.setOnClickListener {
            Toast.makeText(itemView.context, data.name, Toast.LENGTH_LONG).show()
            itemClickListener.onItemViewClick(data)
        }
    }
}
}
}*/