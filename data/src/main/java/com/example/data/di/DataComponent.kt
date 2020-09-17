package com.example.data.di

import android.content.Context
import com.example.data.TheMovieDbPaging
import dagger.BindsInstance
import dagger.Component
import java.text.SimpleDateFormat
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class, NetworkModule::class,
        DataModule::class, InteractorModule::class
    ]
)
interface DataComponent {

    fun theMovieDbPaging(): TheMovieDbPaging
    fun dateFormat(): SimpleDateFormat

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder
        fun build(): DataComponent
    }
}