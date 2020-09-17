package com.example.themoviedb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.di.AppScope
import javax.inject.Inject
import javax.inject.Provider

@AppScope
class TheMovieDbViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
//            modelClass.isAssignableFrom(it.key)
//        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        val creator = creators[modelClass]
            ?: throw IllegalArgumentException("unknown model class $modelClass")
        return modelClass.cast(creator.get())!!
    }
}