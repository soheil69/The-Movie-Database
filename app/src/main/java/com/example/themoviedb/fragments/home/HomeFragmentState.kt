package com.example.themoviedb.fragments.home

sealed class HomeFragmentState {
    object ShowInitialViewState : HomeFragmentState()
    object ShowRecyclerViewState : HomeFragmentState()
}