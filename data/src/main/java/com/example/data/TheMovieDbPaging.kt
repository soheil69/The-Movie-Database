package com.example.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.example.core.api.Paging
import com.example.core.model.Movie
import com.example.data.network.TMDbRestApi
import com.example.data.paging.LatestMoviesPagingSource
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Flowable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TheMovieDbPaging @Inject internal constructor(
    private val restApi: TMDbRestApi,
    private val gson: Gson,
    private val dateFormat: SimpleDateFormat
) : Paging {

    override fun getLatestMoviesPager(gte: Date?, lte: Date): Flowable<PagingData<Movie>> {
        return Pager(
            PagingConfig(TMDbRestApi.DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            null,
            null,
            {
                LatestMoviesPagingSource(
                    restApi,
                    gson,
                    gte?.let { dateFormat.format(gte) },
                    dateFormat.format(lte)
                )
            }
        ).flowable
    }
}