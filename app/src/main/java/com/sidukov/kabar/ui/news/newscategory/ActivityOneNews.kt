package com.sidukov.kabar.ui.news.newscategory

import android.annotation.SuppressLint
import android.icu.number.NumberRangeFormatter.with
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.sidukov.kabar.R
import com.squareup.picasso.Picasso

class ActivityOneNews : AppCompatActivity() {

    private lateinit var likeButton: LottieAnimationView
    private lateinit var bookmarkButton: LottieAnimationView

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_news)

        likeButton = findViewById(R.id.animated_like_news_one)
        var booleanLike = true
        likeButton.setOnClickListener {
            if (booleanLike){
                likeButton.setMaxProgress(0.5f)
                likeButton.speed = 0.5f
                likeButton.playAnimation()
                booleanLike = false
            }
            else {
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
            if (booleanBookmark){
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
