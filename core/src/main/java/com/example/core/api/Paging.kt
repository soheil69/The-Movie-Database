package com.example.core.api

import androidx.paging.PagingData
import com.example.core.model.Movie
import io.reactivex.rxjava3.core.Flowable
import java.util.*

interface Paging {

    fun getLatestMoviesPager(gte: Date?, lte: Date): Flowable<PagingData<Movie>>
}