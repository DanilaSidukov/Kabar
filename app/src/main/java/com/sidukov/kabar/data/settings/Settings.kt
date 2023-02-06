package com.sidukov.kabar.data.settings

import android.content.Context
import com.sidukov.kabar.domain.NewsItem
import javax.inject.Inject

class Settings @Inject constructor(context: Context) {

    companion object {
        const val API_KEY = "pub_16526c5b9ee62502bac4aaee39680d3370436"

        var newsAllList: List<NewsItem> = emptyList()
    }

}