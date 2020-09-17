package com.example.themoviedb.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class AutoDispose @Inject constructor() : LifecycleObserver {

    private lateinit var onCreateCompositeDisposable: CompositeDisposable
    private lateinit var onStartCompositeDisposable: CompositeDisposable
    private lateinit var onResumeCompositeDisposable: CompositeDisposable
    private lateinit var lastLifecycleEvent: Lifecycle.Event

    fun bindTo(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
        onCreateCompositeDisposable = CompositeDisposable()
        onStartCompositeDisposable = CompositeDisposable()
        onResumeCompositeDisposable = CompositeDisposable()
    }

    fun add(disposable: Disposable) {
        if (::lastLifecycleEvent.isInitialized)
            when (lastLifecycleEvent) {
                Lifecycle.Event.ON_CREATE -> onCreateCompositeDisposable.add(disposable)
                Lifecycle.Event.ON_START -> onStartCompositeDisposable.add(disposable)
                Lifecycle.Event.ON_RESUME -> onStartCompositeDisposable.add(disposable)
                else -> throw IllegalAccessError("must add Disposable in proper Lifecycle(onCreate, onStart, onResume)")
            }
        else throw NotImplementedError("must bind AutoDisposable to a Lifecycle first")
    }

    fun add(disposable: Disposable, event: Lifecycle.Event) {
        if (::lastLifecycleEvent.isInitialized)
            when (event) {
                Lifecycle.Event.ON_CREATE -> onCreateCompositeDisposable.add(disposable)
                Lifecycle.Event.ON_START -> onStartCompositeDisposable.add(disposable)
                Lifecycle.Event.ON_RESUME -> onStartCompositeDisposable.add(disposable)
                else -> throw IllegalAccessError("must add Disposable in proper Lifecycle(onCreate, onStart, onResume)")
            }
        else throw NotImplementedError("must bind AutoDisposable to a Lifecycle first")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        lastLifecycleEvent = Lifecycle.Event.ON_CREATE
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        lastLifecycleEvent = Lifecycle.Event.ON_START
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        lastLifecycleEvent = Lifecycle.Event.ON_RESUME
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        lastLifecycleEvent = Lifecycle.Event.ON_PAUSE
        onResumeCompositeDisposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        lastLifecycleEvent = Lifecycle.Event.ON_STOP
        onStartCompositeDisposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        lastLifecycleEvent = Lifecycle.Event.ON_DESTROY
        onCreateCompositeDisposable.clear()
    }
}