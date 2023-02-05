package com.sidukov.kabar.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.sidukov.kabar.data.remote.api.ApiClient
import com.sidukov.kabar.domain.NewsItem
import java.text.SimpleDateFormat
import java.util.*

class NewsRepository(
    private val apiClient: ApiClient,
) {

    companion object {
        const val DATE_PATTERN = "yyyy-dd-MM HH:mm:ss"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getNews(): List<NewsItem> {

        val requestNews = apiClient.getRequest()

        val responseNewsList = mutableListOf<NewsItem>().apply {
            requestNews.results.forEach { newsBody ->
                this.add(
                    NewsItem(
                        newsImage = newsBody.image_url,
                        textCategory = newsBody.category?.first(),
                        titleText = newsBody.description,
                        description = newsBody.content,
                        authorImage = null,
                        author = newsBody.creator?.first() ?: "No author",
                        date = newsBody.pubDate?.convertToDate()
                    )
                )
            }
        }
        return responseNewsList
    }

    private fun String.convertToDate(): Date? =
        SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(this)

}