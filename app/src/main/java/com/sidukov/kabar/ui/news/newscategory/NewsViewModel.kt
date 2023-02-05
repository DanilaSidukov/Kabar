package com.sidukov.kabar.ui.news.newscategory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidukov.kabar.R
import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.domain.NewsItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class NewsViewModel(
    repository: NewsRepository
): ViewModel() {

    private val _newsData = MutableSharedFlow<List<NewsItem>>()
    var newsData = _newsData.asSharedFlow()

    init {
        viewModelScope.launch {
            val value = repository.getNews()
            if (value.isEmpty()) return@launch
            _newsData.emit(value)
        }
    }

}