package com.sidukov.kabar.ui.news.newscategory

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.sidukov.kabar.R
import com.sidukov.kabar.domain.NewsItem
import com.sidukov.kabar.ui.news.newscategory.NewsAdapter.Companion.difference
import com.squareup.picasso.Picasso

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


    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_news)

        val bundle = intent.extras
        val newsItem =  bundle?.getSerializable("item_news") as NewsItem?

        println("$newsItem")

        imageAuthorOneNews = findViewById(R.id.image_author_one_news)
        textAuthorOneNews = findViewById(R.id.text_author_one_news)
        dateOneNews = findViewById(R.id.date_one_news)
        imageOneNews = findViewById(R.id.image_news_one)
        textCategoryOneNews = findViewById(R.id.text_category_news_one)
        titleOneNews = findViewById(R.id.text_title_news_one)
        textDescriptionOneNews = findViewById(R.id.text_description_news_one)

        Picasso.get().load(newsItem?.authorImage ?: R.drawable.ic_pencil_news)
            .into(imageAuthorOneNews)
        textAuthorOneNews.text = newsItem?.author
        dateOneNews.text = newsItem?.date.difference()
        Picasso.get().load(newsItem?.newsImage).into(imageOneNews)
        textCategoryOneNews.text = newsItem?.textCategory
        titleOneNews.text = newsItem?.titleText
        textDescriptionOneNews.text = newsItem?.description

        likeButton = findViewById(R.id.animated_like_news_one)
        var booleanLike = true
        likeButton.setOnClickListener {
            if (booleanLike) {
                likeButton.setMaxProgress(0.5f)
                likeButton.speed = 0.5f
                likeButton.playAnimation()
                booleanLike = false
            } else {
                likeButton.setMinProgress(0.5f)
                likeButton.setMaxProgress(1f)
                likeButton.speed = 0.5f
                likeButton.playAnimation()
                booleanLike = true
            }
        }

        bookmarkButton = findViewById(R.id.animated_bookmark_news_one)
        bookmarkButton.progress = 0.5f
        var booleanBookmark = true
        bookmarkButton.setOnClickListener {
            if (booleanBookmark) {
                bookmarkButton.setMinProgress(0.5f)
                bookmarkButton.setMaxProgress(1f)
                bookmarkButton.speed = 0.5f
                bookmarkButton.playAnimation()
                booleanBookmark = false
            } else {
                bookmarkButton.setMinProgress(0.5f)
                bookmarkButton.speed = -0.5f
                bookmarkButton.playAnimation()
                booleanBookmark = true
            }
        }
    }

}
