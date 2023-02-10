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
    repository: NewsRepository
): ViewModel() {

    private val _newsData = MutableSharedFlow<List<EntityNews>>()
    var newsData = _newsData.asSharedFlow()

    init {
        println("viewModel created")
        viewModelScope.launch {
            val value = repository.getNews()
            if (value.isEmpty()) return@launch
            value.forEach {
                repository.addNewsToDatabase(it)
            }
            _newsData.emit(value)
        }
    }
}