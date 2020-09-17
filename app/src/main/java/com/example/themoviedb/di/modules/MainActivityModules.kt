package com.example.themoviedb.di.modules

import com.example.themoviedb.di.FragmentScope
import com.example.themoviedb.dialogs.date.DateDialogFragment
import com.example.themoviedb.fragments.detail.DetailFragment
import com.example.themoviedb.fragments.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal class MainActivityModule

@Module
internal interface MainFragmentBuilder {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class, HomeChildFragmentBuilder::class])
    @FragmentScope
    fun homeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [DetailFragmentModule::class, DetailChildFragmentBuilder::class])
    @FragmentScope
    fun detailFragment(): DetailFragment

    @ContributesAndroidInjector(modules = [DateDialogFragmentModule::class])
    @FragmentScope
    fun dateDialogFragment(): DateDialogFragment
}