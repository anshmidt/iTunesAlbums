package com.anshmidt.itunesalbums.di.module

import android.app.Application
import android.content.Context
import com.anshmidt.itunesalbums.network.ApiConstants
import com.anshmidt.itunesalbums.network.ItunesApi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val context: Application) {

    @Singleton
    @Provides
    fun provideItunesApi(retrofit: Retrofit): ItunesApi {
        return retrofit.create(ItunesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient.Builder()
            .cache(cache)
            .build()
    }

}