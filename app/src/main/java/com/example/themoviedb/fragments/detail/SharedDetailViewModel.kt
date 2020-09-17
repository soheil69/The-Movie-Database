package com.example.themoviedb.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.model.Movie
import javax.inject.Inject

class SharedDetailViewModel @Inject constructor() : ViewModel() {

    private val _detailFragmentState: MutableLiveData<DetailFragmentState> =
        MutableLiveData(DetailFragmentState.ShowInitialViewState)
    val detailFragmentState: LiveData<DetailFragmentState>
        get() = _detailFragmentState

    private val _movie: MutableLiveData<Movie> =
        MutableLiveData()
    val movie: LiveData<Movie>
        get() = _movie

    fun postMovie(movie: Movie) {
        _movie.postValue(movie)
    }

    fun setState(state: DetailFragmentState) {
        _detailFragmentState.postValue(state)
    }
}