package com.example.themoviedb.fragments.detail

sealed class DetailFragmentState {
    object ShowInitialViewState : DetailFragmentState()
    object ShowMovieViewState : DetailFragmentState()
}