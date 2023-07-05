package com.sidukov.kabar.ui.news.newscategory

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.sidukov.kabar.R
import com.sidukov.kabar.data.database.EntityNews
import com.sidukov.kabar.di.injectViewModel
import com.sidukov.kabar.ui.NewsApplication
import com.sidukov.kabar.ui.news.NewsViewModel
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
    private lateinit var shareButton: CardView
    private lateinit var backButton: androidx.appcompat.widget.Toolbar

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var newsViewModel: NewsViewModel

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_news)
        NewsApplication.appComponent.inject(this)

        newsViewModel = injectViewModel(viewModelFactory)

        likeButton = findViewById(R.id.animated_like_news_one)
        bookmarkButton = findViewById(R.id.animated_bookmark_news_one)

        val bundle = intent.extras
        val newsItem = bundle?.getSerializable("item_news") as EntityNews?

        imageAuthorOneNews = findViewById(R.id.image_author_one_news)
        textAuthorOneNews = findViewById(R.id.text_author_one_news)
        dateOneNews = findViewById(R.id.date_one_news)
        imageOneNews = findViewById(R.id.image_news_one)
        textCategoryOneNews = findViewById(R.id.text_category_news_one)
        titleOneNews = findViewById(R.id.text_title_news_one)
        textDescriptionOneNews = findViewById(R.id.text_description_news_one)
        shareButton = findViewById(R.id.share_button)
        backButton = findViewById(R.id.one_news_tool_bar)

        if (newsItem?.authorImage == null) imageAuthorOneNews.setImageResource(R.drawable.ic_pencil_news)
        else Picasso.get().load(newsItem.authorImage).into(imageAuthorOneNews)
        textAuthorOneNews.text = newsItem?.author
        dateOneNews.text = newsItem?.date.difference()
        if (newsItem?.newsImage.isNullOrBlank()) imageOneNews.setImageResource(R.drawable.ic_news)
        else Picasso.get().load(newsItem?.newsImage).into(imageOneNews)
        textCategoryOneNews.text = newsItem?.category
        titleOneNews.text = newsItem?.title
        textDescriptionOneNews.text = newsItem?.description
        val linkNews = newsItem?.link

        lifecycleScope.launchWhenStarted {
            newsViewModel.bookmarkData.collect { list ->
                val descriptionList = list.map { it.description }
                if (textDescriptionOneNews.text in descriptionList) {
                    bookmarkButton.progress = 0f
                } else {
                    bookmarkButton.progress = 0.5f
                }
                if (newsItem?.likeBoolean!!) likeButton.progress = 0.5f
                else likeButton.progress = 0f
            }
        }

        likeButton.setOnClickListener {
            if (!newsItem?.likeBoolean!!) {
                likeButton.setMinProgress(0f)
                likeButton.setMaxProgress(0.5f)
                newsItem.likeBoolean = true
            } else {
                likeButton.setMinProgress(0.5f)
                likeButton.setMaxProgress(1f)
                newsItem.likeBoolean = false
            }
            likeButton.speed = 0.5f
            likeButton.playAnimation()
            newsViewModel.updateBookmarkData(newsItem)
        }

        bookmarkButton.setOnClickListener {
            if (!newsItem?.bookmarkBoolean!!) {
                bookmarkButton.setMaxProgress(0.5f)
                bookmarkButton.setMinProgress(0f)
                bookmarkButton.speed = -0.5f
                bookmarkButton.playAnimation()
                newsItem.bookmarkBoolean = true
                newsViewModel.addBookmarkData(newsItem)
            } else {
                bookmarkButton.setMinProgress(0f)
                bookmarkButton.setMaxProgress(0.5f)
                bookmarkButton.speed = 0.5f
                bookmarkButton.playAnimation()
                newsItem.bookmarkBoolean = false
                newsViewModel.deleteBookmarkData(newsItem)
            }
        }

        shareButton.setOnClickListener {
            val sharingIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                val sharedString = if (!linkNews?.isNullOrEmpty()!!) linkNews
                else textDescriptionOneNews.text.toString()
                putExtra(Intent.EXTRA_TEXT, "I share the news:\n$sharedString")
            }
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        setSupportActionBar(backButton)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        if (this.resources.configuration.uiMode == Configuration.UI_MODE_NIGHT_YES) backButton.navigationIcon?.setColorFilter(
            getColor(R.color.dark_color_back),
            PorterDuff.Mode.SRC_ATOP
        )
        else backButton.navigationIcon?.setColorFilter(
            getColor(R.color.anthracite),
            PorterDuff.Mode.SRC_ATOP
        )

        backButton.setNavigationOnClickListener {
            onBackPressed()
        }

    }

}
