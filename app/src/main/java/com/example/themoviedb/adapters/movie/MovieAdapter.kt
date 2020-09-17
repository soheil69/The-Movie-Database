package com.example.themoviedb.adapters.movie

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.core.model.Movie

class MovieAdapter :
    PagingDataAdapter<Movie, MovieViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }) {

    private var itemClickListener: ((itemView: View?, movie: Movie, position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: ((itemView: View?, movie: Movie, position: Int) -> Unit)?) {
        this.itemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position), position, itemClickListener)
}