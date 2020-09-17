package com.example.themoviedb.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themoviedb.util.SingleEvent
import javax.inject.Inject

class SharedHomeViewModel @Inject constructor() : ViewModel() {

    private val _singleEventDateFilter: MutableLiveData<SingleEvent<DateFilter>> =
        MutableLiveData()
    val singleEventDateFilter: LiveData<SingleEvent<DateFilter>>
        get() = _singleEventDateFilter

    fun postDateFilter(dateFilter: DateFilter) {
        _singleEventDateFilter.postValue(SingleEvent(dateFilter))
    }
}