package com.example.data.paging

import androidx.paging.rxjava3.RxPagingSource
import com.example.core.exception.ConnectivityException
import com.example.core.exception.NetworkException
import com.example.core.exception.TimeoutException
import com.example.core.model.Movie
import com.example.core.model.NetworkError
import com.example.data.network.TMDbRestApi
import com.example.data.network.response.discover.DiscoverErrorResponse
import com.example.data.network.response.discover.DiscoverResponse
import com.example.data.network.response.discover.toMovie
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

internal class LatestMoviesPagingSource constructor(
    private val restApi: TMDbRestApi,
    private val gson: Gson,
    private val gte: String?,
    private val lte: String
) : RxPagingSource<Int, Movie>() {


    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {

        return (if (gte != null) restApi.getLatestMovies(params.key ?: 1, gte, lte)
        else restApi.getLatestMovies(params.key ?: 1, lte))
            .map(this::toLoadResultPage)
            .onErrorReturn(this::toLoadResultError)
            .subscribeOn(Schedulers.io())
    }

    private fun toLoadResultPage(
        response: DiscoverResponse
    ): LoadResult<Int, Movie> {
        return LoadResult.Page(
            response.results.map { it.toMovie() },
            if (response.page > 1) response.page - 1 else null,  // Only paging forward.
            if (response.totalPages > response.page) response.page + 1 else null,
            LoadResult.Page.COUNT_UNDEFINED,
            LoadResult.Page.COUNT_UNDEFINED
        )
    }

    private fun toLoadResultError(
        throwable: Throwable
    ): LoadResult<Int, Movie> {

        return when (throwable) {
            is HttpException -> {
                try {
                    val error = gson.fromJson(
                        throwable.response()?.errorBody()?.string(),
                        DiscoverErrorResponse::class.java
                    )
                    LoadResult.Error(
                        NetworkException(
                            NetworkError(
                                throwable.code(),
                                throwable.message(),
                                error.statusCode,
                                error.statusMessage
                            ), throwable
                        )
                    )
                } catch (exception: Exception) {
                    LoadResult.Error(
                        NetworkException(
                            NetworkError(
                                throwable.code(),
                                throwable.message(),
                                throwable.code(),
                                throwable.message()
                            ), throwable
                        )
                    )
                }
            }
            is SocketTimeoutException -> {
                LoadResult.Error(TimeoutException(throwable))
            }
            is IOException -> {
                LoadResult.Error(ConnectivityException(throwable))
            }
            else -> {
                LoadResult.Error(throwable)
            }
        }
    }
}