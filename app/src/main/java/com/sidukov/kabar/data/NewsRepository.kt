package com.sidukov.kabar.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.sidukov.kabar.data.database.EntityBookmarkNews
import com.sidukov.kabar.data.database.NewsBookmarkDao
import com.sidukov.kabar.data.database.NewsDao
import com.sidukov.kabar.data.remote.api.ApiClient
import com.sidukov.kabar.domain.NewsItem
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val newsDao: NewsDao,
    private val newsBookmarkDao: NewsBookmarkDao
) {

    companion object {
        const val DATE_PATTERN = "yyyy-dd-MM HH:mm:ss"
    }

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

    suspend fun addBookmark(bookmarkData: NewsItem){
        val bookmarkItem = EntityBookmarkNews(
            bookmarkData.titleText!!,
            bookmarkData.textCategory!!,
            bookmarkData.description!!,
            bookmarkData.newsImage,
            bookmarkData.author!!,
            bookmarkData.date
        )
        newsBookmarkDao.addToBookmarkNews(bookmarkItem)
    }

    suspend fun deleteFromBookmark(bookmarkData: NewsItem){
        val bookmarkItem = EntityBookmarkNews(
            bookmarkData.titleText!!,
            bookmarkData.textCategory!!,
            bookmarkData.description!!,
            bookmarkData.newsImage,
            bookmarkData.author!!,
            bookmarkData.date
        )
        newsBookmarkDao.deleteBookmarkNews(bookmarkItem)
    }

    private fun String.convertToDate(): Date? =
        SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(this)

}