package com.example.data.network

import com.example.data.network.response.discover.DiscoverResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface TMDbRestApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org"
        const val API_KEY = "36ef50e74a671c88c94ddc3ee1b15486"
        const val SORT_OPTION = "primary_release_date.desc"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val IMAGE_BACKDROP_SIZE = "w780"
        const val IMAGE_POSTER_SIZE = "w780"
        const val DEFAULT_PAGE_SIZE = 20
    }

    @GET("/3/discover/movie?api_key=$API_KEY&sort_by=$SORT_OPTION")
    fun getLatestMovies(
        @Query("page") page: Int,
        @Query("primary_release_date.gte") gte: String,
        @Query("primary_release_date.lte") lte: String
    ): Single<DiscoverResponse>

    @GET("/3/discover/movie?api_key=$API_KEY&sort_by=$SORT_OPTION")
    fun getLatestMovies(
        @Query("page") page: Int,
        @Query("primary_release_date.lte") lte: String
    ): Single<DiscoverResponse>

}