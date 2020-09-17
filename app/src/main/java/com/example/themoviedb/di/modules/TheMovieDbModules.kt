package com.example.themoviedb.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedb.TheMovieDbViewModelFactory
import com.example.themoviedb.activities.main.MainActivity
import com.example.themoviedb.activities.main.MainViewModel
import com.example.themoviedb.di.ActivityScope
import com.example.themoviedb.di.ViewModelKey
import com.example.themoviedb.fragments.detail.SharedDetailViewModel
import com.example.themoviedb.fragments.home.HomeViewModel
import com.example.themoviedb.fragments.home.SharedHomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal interface TheMovieDbModule

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun homeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SharedHomeViewModel::class)
    fun sharedHomeViewModel(sharedHomeViewModel: SharedHomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SharedDetailViewModel::class)
    fun sharedDetailViewModel(sharedDetailViewModel: SharedDetailViewModel): ViewModel

    @Binds
    fun theMovieDbViewModelFactory(theMovieDbViewModelFactory: TheMovieDbViewModelFactory): ViewModelProvider.Factory
}

@Module
internal interface ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainFragmentBuilder::class])
    @ActivityScope
    fun mainActivity(): MainActivity
}

@Module
internal interface ServiceBuilder