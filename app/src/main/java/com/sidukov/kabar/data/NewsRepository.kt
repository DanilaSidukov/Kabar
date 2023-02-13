package com.sidukov.kabar.data

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.data.database.NewsBookmarkDao
import com.sidukov.kabar.data.database.NewsDao
import com.sidukov.kabar.data.remote.api.ApiClient
import com.sidukov.kabar.ui.news.ActivityGeneral
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

open class NewsRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val newsDao: NewsDao,
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
                        newsImage = newsBody.image_url,
                        category = newsBody.category?.first()!!,
                        title = newsBody.description ?: "No description",
                        description = newsBody.content!!,
                        authorImage = null,
                        author = newsBody.creator?.first() ?: "No author",
                        date = newsBody.pubDate?.convertToDate()!!
                    )
                )
            }
        }

        return responseNewsList
    }

    fun getAllNews() = newsDao.getAll()

    fun deleteNews(newsData: EntityNews) = newsDao.deleteNews(newsData)

    suspend fun addNewsToDatabase(newsData: List<EntityNews>) = newsDao.addNews(newsData)

    fun addBookmark(bookmarkData: EntityNews) = newsBookmarkDao.addToBookmarkNews(bookmarkData)

    fun deleteFromBookmark(bookmarkData: EntityNews) = newsBookmarkDao.deleteBookmarkNews(bookmarkData)

    fun getBookmarkNews() = newsBookmarkDao.getBookmarkNews()

    fun updateBookmarkItem(bookmarkData: EntityNews) = newsBookmarkDao.updateBookmarkNews(bookmarkData)

    private fun String.convertToDate(): Long? {

        val date = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(this)
        return date.time ?: null
    }

}