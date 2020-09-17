package com.example.themoviedb

import android.content.Context
import com.example.data.di.AppScope
import com.example.data.di.DataComponent
import com.example.themoviedb.di.ApplicationContext
import com.example.themoviedb.di.modules.ActivityBuilder
import com.example.themoviedb.di.modules.ServiceBuilder
import com.example.themoviedb.di.modules.TheMovieDbModule
import com.example.themoviedb.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@AppScope
@Component(
    dependencies = [DataComponent::class],
    modules = [
        AndroidInjectionModule::class, TheMovieDbModule::class,
        ViewModelModule::class, ActivityBuilder::class, ServiceBuilder::class
    ]
)
interface TheMovieDbComponent {
    fun inject(application: TheMovieDbApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(@ApplicationContext context: Context): Builder
        fun dataComponent(dataComponent: DataComponent): Builder
        fun build(): TheMovieDbComponent
    }
}