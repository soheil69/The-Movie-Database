package com.example.themoviedb.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.example.core.model.Movie
import com.example.data.TheMovieDbPaging
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val theMovieDbPaging: TheMovieDbPaging
) : ViewModel() {

    private val _homeFragmentState: MutableLiveData<HomeFragmentState> =
        MutableLiveData(HomeFragmentState.ShowInitialViewState)
    val homeFragmentState: LiveData<HomeFragmentState>
        get() = _homeFragmentState

    private var pagingDataFlowDisposable: Disposable? = null
    private val _pagingData: MutableLiveData<PagingData<Movie>> =
        MutableLiveData()
    val pagingData: LiveData<PagingData<Movie>>
        get() = _pagingData

    fun start(gte: Date?, lte: Date) {
        pagingDataFlowDisposable?.dispose()
        pagingDataFlowDisposable = theMovieDbPaging.getLatestMoviesPager(gte, lte)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe { _pagingData.postValue(it) }
    }

    override fun onCleared() {
        pagingDataFlowDisposable?.dispose()
        super.onCleared()
    }

    fun setState(state: HomeFragmentState) {
        _homeFragmentState.postValue(state)
    }
}