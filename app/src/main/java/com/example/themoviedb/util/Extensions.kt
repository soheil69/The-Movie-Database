package com.example.themoviedb.util

import androidx.lifecycle.Lifecycle
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.autoDispose(autoDispose: AutoDispose) {
    autoDispose.add(this)
}

fun Disposable.autoDispose(autoDispose: AutoDispose, event: Lifecycle.Event) {
    autoDispose.add(this, event)
}

fun PagingDataAdapter<*, *>.withLoadStateAll(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>,
    refresh: LoadStateAdapter<*>
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.prepend
        footer.loadState = loadStates.append
        refresh.loadState = loadStates.refresh
    }
    return ConcatAdapter(refresh, header, this, footer)
}