package com.example.themoviedb

import android.app.Application
import com.example.data.di.DaggerDataComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TheMovieDbApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerTheMovieDbComponent.builder()
            .applicationContext(applicationContext)
            .dataComponent(DaggerDataComponent.builder().context(applicationContext).build())
            .build().inject(this)
    }

    override fun androidInjector() = dispatchingAndroidInjector
}