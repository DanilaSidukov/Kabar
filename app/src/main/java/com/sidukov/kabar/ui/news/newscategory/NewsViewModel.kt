package com.sidukov.kabar.ui.news.newscategory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidukov.kabar.R
import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.di.StorageModule
import com.sidukov.kabar.domain.NewsItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

open class NewsViewModel @Inject constructor(
    repository: NewsRepository
): ViewModel() {

    private val _newsData = MutableSharedFlow<List<NewsItem>>()
    var newsData = _newsData.asSharedFlow()

    var newsList: List<NewsItem> = (emptyList())

    init {
        println("viewModel created")
        viewModelScope.launch {
            val value = repository.getNews()
            if (value.isEmpty()) return@launch
            _newsData.emit(value)
            newsList = value
            Settings.newsAllList = value
        }
    }

}