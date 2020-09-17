package com.example.themoviedb.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedb.activities.OnBackPressListener
import com.example.themoviedb.util.AutoDispose
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

open class BaseFragment : Fragment(), HasAndroidInjector, OnBackPressListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var autoDispose: AutoDispose

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        autoDispose.bindTo(lifecycle)
    }

    override fun onBackPressed(): Boolean {
        val childFragment = childFragmentManager.primaryNavigationFragment
        return if (childFragment is OnBackPressListener) childFragment.onBackPressed() else false
    }

    override fun androidInjector() = dispatchingAndroidInjector
}