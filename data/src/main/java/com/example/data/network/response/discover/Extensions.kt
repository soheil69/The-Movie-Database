package com.example.data.network.response.discover

import com.example.core.model.Movie
import com.example.data.network.TMDbRestApi

internal fun ResultsItem.toMovie() = Movie(
    overview,
    originalLanguage,
    originalTitle,
    title,
    TMDbRestApi.IMAGE_BASE_URL + TMDbRestApi.IMAGE_POSTER_SIZE + posterPath,
    TMDbRestApi.IMAGE_BASE_URL + TMDbRestApi.IMAGE_BACKDROP_SIZE + backdropPath,
    releaseDate,
    popularity,
    voteAverage,
    id,
    voteCount
)

