@file:Suppress("RemoveEmptyClassBody")

package com.example.data.di


import com.example.core.api.Paging
import com.example.data.TheMovieDbPaging
import com.example.data.network.TMDbRestApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
internal interface DataModule {

    @Binds
    fun paging(theMovieDbPaging: TheMovieDbPaging): Paging
}

@Module
internal class InteractorModule

@Module
internal class DatabaseModule

@Module
internal class NetworkModule {

    @Provides
    fun dateFormat(): SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient {//context: Context
        return OkHttpClient.Builder()//.addInterceptor(ChuckInterceptor(context))
            .build()
    }

    @Provides
    fun gson(): Gson {
        return GsonBuilder()
            .setLenient()
//            .setDateFormat(DATE_FORMAT)
//            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(TMDbRestApi.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun tMDbRestApi(retrofit: Retrofit): TMDbRestApi {
        return retrofit.create(TMDbRestApi::class.java)
    }
}