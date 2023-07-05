package com.sidukov.kabar.data

import com.sidukov.kabar.R
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.data.database.NewsBookmarkDao
import com.sidukov.kabar.data.remote.api.ApiClient
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

open class NewsRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val newsBookmarkDao: NewsBookmarkDao
) {

    companion object {
        const val DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"
    }


    suspend fun getNews(): List<EntityNews> {

        val requestNews = apiClient.getRequest()

        val responseNewsList = mutableListOf<EntityNews>().apply {
            requestNews.results.forEach { newsBody ->
                this.add(
                    EntityNews(
                        newsImage = if (newsBody.image_url.isNullOrBlank()) (R.drawable.ic_news).toString()
                        else newsBody.image_url,
                        category = newsBody.category?.first() ?: "No category",
                        title = newsBody.description ?: "No title",
                        description = newsBody.content ?: "No description",
                        authorImage = null,
                        author = newsBody.creator?.first() ?: "No author",
                        date = newsBody.pubDate?.convertToDate()!!,
                        link = newsBody.link
                    )
                )
            }
        }

        return responseNewsList
    }

    suspend fun addBookmark(bookmarkData: EntityNews) = newsBookmarkDao.addToBookmarkNews(bookmarkData)

    suspend fun deleteFromBookmark(bookmarkData: EntityNews) = newsBookmarkDao.deleteBookmarkNews(bookmarkData)

    fun getBookmarkNews() = newsBookmarkDao.getBookmarkNews()

    suspend fun updateBookmarkItem(bookmarkData: EntityNews) = newsBookmarkDao.updateBookmarkNews(bookmarkData)

    private fun String.convertToDate(): Long? {

        val date = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(this)
        return date.time ?: null
    }

}