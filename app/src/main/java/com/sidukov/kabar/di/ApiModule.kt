package com.sidukov.kabar.di

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule(private val context: Context){
    @Singleton
    @Provides
    fun provideApiModule(): OkHttpClient = OkHttpClient.Builder()
        .cache(Cache(
            context.cacheDir,
            10L * 1024L * 1024L
        ))
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()
}

