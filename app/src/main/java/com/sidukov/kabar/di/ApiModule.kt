package com.sidukov.kabar.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.sidukov.kabar.data.remote.api.ApiClient
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.domain.news_body.NewsBody
import dagger.Module
import dagger.Provides
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    suspend fun getRequest() = suspendCoroutine<NewsBody> { continuation ->
        val request = Request.Builder()
            .url("https://newsdata.io/api/1/news?apikey=${Settings.API_KEY}&language=en")
            .cacheControl(CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build())
            .build()

        provideApiModule().newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }
                override fun onResponse(call: Call, response: Response) {
                    val a = JsonParser().parse(response.body()?.string())
                    val fd = GsonBuilder().create().fromJson(a, NewsBody::class.java)
                    continuation.resume(fd)
                }
            }
        )
    }
}

