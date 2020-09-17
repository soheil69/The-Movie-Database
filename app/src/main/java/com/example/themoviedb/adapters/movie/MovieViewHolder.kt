package com.example.themoviedb.adapters.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.Movie
import com.example.themoviedb.R

class MovieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.adapter_movie, parent, false)
) {
    private val titleTxt: TextView = itemView.findViewById(R.id.titleTxt)

    fun bind(
        item: Movie?,
        position: Int,
        itemClickListener: ((itemView: View?, movie: Movie, position: Int) -> Unit)?
    ) {
        item ?: return
        itemView.setOnClickListener {
            if (itemClickListener != null) itemClickListener(it, item, position)
        }
        titleTxt.text = item.title
    }
}