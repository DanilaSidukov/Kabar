package com.sidukov.kabar.data.remote.api

import android.content.Context
import androidx.room.util.StringUtil
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.sidukov.kabar.data.settings.Settings.Companion.API_KEY
import com.sidukov.kabar.domain.news_body.NewsBody
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import java.net.URLEncoder
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ApiClient @Inject constructor(val context: Context) {

    private val client: OkHttpClient = OkHttpClient.Builder()
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
            .url("https://newsdata.io/api/1/news?apikey=${API_KEY}&language=en")
            .cacheControl(CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build())
            .build()

        client.newCall(request).enqueue(
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

//    private const val BASE_URL = "https://saurav.tech/NewsAPI/"
//    private var retrofitNews: Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//        .build()
//
//    var newsApiClient:NewsApi = retrofitNews.create(NewsApi::class.java)

}