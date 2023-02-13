package com.sidukov.kabar.ui.news.newscategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.data.database.EntityNews
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

open class NewsViewModel @Inject constructor(
    repository: NewsRepository,
) : ViewModel() {

    private val _newsData = MutableSharedFlow<List<EntityNews>>()
    var newsData = _newsData.asSharedFlow()

    init {
        viewModelScope.launch {
            println("vm created")
            val value = repository.getNews()
            if (value.isEmpty()) return@launch

            println("value list = $value")
            launch {
                repository.addNewsToDatabase(value)
            }

            _newsData.emit(value)
        }
    }
}
