package com.sidukov.kabar.ui.news.newscategory

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.sidukov.kabar.R
import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.domain.NewsItem
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.news.newscategory.NewsAdapter.Companion.difference
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ActivityArticleNews() : AppCompatActivity() {

    private lateinit var likeButton: LottieAnimationView
    private lateinit var bookmarkButton: LottieAnimationView

    private lateinit var imageAuthorOneNews: ImageView
    private lateinit var textAuthorOneNews: TextView
    private lateinit var dateOneNews: TextView
    private lateinit var imageOneNews: ImageView
    private lateinit var textCategoryOneNews: TextView
    private lateinit var titleOneNews: TextView
    private lateinit var textDescriptionOneNews: TextView

    @Inject
    lateinit var newsRepository: NewsRepository

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_news)
        NewsApplication.appComponent.inject(this)

        val bundle = intent.extras
        val newsItem =  bundle?.getSerializable("item_news") as EntityNews?

        println("$newsItem")

        imageAuthorOneNews = findViewById(R.id.image_author_one_news)
        textAuthorOneNews = findViewById(R.id.text_author_one_news)
        dateOneNews = findViewById(R.id.date_one_news)
        imageOneNews = findViewById(R.id.image_news_one)
        textCategoryOneNews = findViewById(R.id.text_category_news_one)
        titleOneNews = findViewById(R.id.text_title_news_one)
        textDescriptionOneNews = findViewById(R.id.text_description_news_one)

        if (newsItem?.authorImage == null) imageAuthorOneNews.setImageResource(R.drawable.ic_pencil_news)
        else Picasso.get().load(newsItem.authorImage).into(imageAuthorOneNews)
        textAuthorOneNews.text = newsItem?.author
        dateOneNews.text = newsItem?.date.difference()
        if (newsItem?.newsImage.isNullOrBlank()) imageOneNews.setImageResource(R.drawable.ic_news)
        else Picasso.get().load(newsItem?.newsImage).into(imageOneNews)
        textCategoryOneNews.text = newsItem?.category
        titleOneNews.text = newsItem?.title
        textDescriptionOneNews.text = newsItem?.description

        lifecycleScope.launchWhenStarted {
            val bookmarkList = newsRepository.getBookmarkNews().map { it.title }
            if (titleOneNews.text in bookmarkList ){
                bookmarkButton.progress = 0f
            } else bookmarkButton.progress = 0.5f
            if (newsItem?.likeBoolean!!) likeButton.progress = 0.5f
            else likeButton.progress = 0f
        }

        likeButton = findViewById(R.id.animated_like_news_one)
        var booleanLike = false
        likeButton.setOnClickListener {
            if (!newsItem?.likeBoolean!!) {
                likeButton.setMinProgress(0f)
                likeButton.setMaxProgress(0.5f)
                likeButton.speed = 0.5f
                likeButton.playAnimation()
                newsItem.likeBoolean = true
                booleanLike = true
                lifecycleScope.launchWhenStarted {
                    newsRepository.updateBookmarkItem(newsItem!!)
                }
            } else {
                likeButton.setMinProgress(0.5f)
                likeButton.setMaxProgress(1f)
                likeButton.speed = 0.5f
                likeButton.playAnimation()
                newsItem.likeBoolean = false
                booleanLike = false
                lifecycleScope.launchWhenStarted {
                    newsRepository.updateBookmarkItem(newsItem!!)
                }
            }
        }

        bookmarkButton = findViewById(R.id.animated_bookmark_news_one)
        var booleanBookmark = false
        bookmarkButton.setOnClickListener {
            if (!newsItem?.bookmarkBoolean!!) {
                bookmarkButton.setMinProgress(0.5f)
                bookmarkButton.setMaxProgress(1f)
                bookmarkButton.speed = 0.5f
                bookmarkButton.playAnimation()
                booleanBookmark = true
                newsItem.bookmarkBoolean = true
                lifecycleScope.launchWhenStarted {
                    newsRepository.addBookmark(newsItem!!)
                }
            } else {
                bookmarkButton.setMinProgress(0.5f)
                bookmarkButton.speed = -0.5f
                bookmarkButton.playAnimation()
                booleanBookmark = true
                newsItem.bookmarkBoolean = false
                lifecycleScope.launchWhenStarted {
                    newsRepository.deleteFromBookmark(newsItem!!)
                }
            }
        }
    }

}
