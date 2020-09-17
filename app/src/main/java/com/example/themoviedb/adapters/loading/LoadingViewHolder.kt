package com.example.themoviedb.adapters.loading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.R

class LoadingViewHolder(parent: ViewGroup, retry: () -> Unit) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.adapter_loading, parent, false)
) {
    private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    private val errorMsg: TextView = itemView.findViewById(R.id.errorMsg)
    private val retry: Button = itemView.findViewById<Button>(R.id.retryBtn)
        .also {
            it.setOnClickListener { retry() }
        }

    fun bind(loadState: LoadState) {
        when (loadState) {
            is LoadState.Error -> {
                errorMsg.text = loadState.error.message
                progressBar.visibility = View.GONE
                retry.visibility = View.VISIBLE
                errorMsg.visibility = View.VISIBLE
            }
            is LoadState.Loading -> {
                progressBar.visibility = View.VISIBLE
                retry.visibility = View.GONE
                errorMsg.visibility = View.GONE
            }
            is LoadState.NotLoading -> {
                progressBar.visibility = View.GONE
                retry.visibility = View.GONE
                errorMsg.visibility = View.GONE
            }
        }
    }
}