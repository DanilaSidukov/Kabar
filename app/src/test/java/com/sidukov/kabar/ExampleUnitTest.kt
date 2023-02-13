package com.sidukov.kabar

import android.util.Log
import com.google.gson.GsonBuilder
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.domain.news_body.NewsBody
import okhttp3.*
import org.junit.Test

import org.junit.Assert.*
import java.io.IOException
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test

    fun getRequest(){

        val client: OkHttpClient = OkHttpClient.Builder()
//            .cache(Cache(
//                context.cacheDir,
//                10L * 1024L * 1024L
//            ))
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()

        var request = Request.Builder()
            .url("https://newsdata.io/api/1/news?apikey=${Settings.API_KEY}&language=en")
            .cacheControl(CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build())
            .build()

        val a = client.newCall(request).execute().use {
            println("response = ${it?.body?.string()}")
        }



//            .use {
//            if (!it.isSuccessful) throw IOException("Unexpected code $it")
//            println("string = ${it.body()}")
////            val responseJson = GsonBuilder().create().fromJson(it.body().jso, NewsBody::class.java)
//            println("response = ${it?.body?.string()}")
//        }

    }

//    fun addition_isCorrect() {
//
//        val tempDate = LocalDateTime.parse("2023-02-02 16:58:12",
//            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).withNano(0)
//        val tempDateMinutes = tempDate.toLocalTime()
//        val currentDate = LocalDateTime.parse(LocalDateTime.now().withNano(0).toString().replace('T', ' '),
//            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).withNano(0)
//        val returnTime: Duration = Duration.between(tempDate, currentDate)
//        println("returnTime = $returnTime")
//        println("returnTime to Days - ${returnTime.toDays()}")
//        println("returnTime to Hours - ${returnTime.toHours()}")
//        println("tempDate = $tempDate.")
//        println("currentDate = $currentDate")
//        println("now = ${LocalDateTime.now()}")
//        //        assertEquals(4, 2 + 2)
//    }
}