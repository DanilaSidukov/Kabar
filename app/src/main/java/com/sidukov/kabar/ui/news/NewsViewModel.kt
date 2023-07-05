package com.sidukov.kabar.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.data.database.EntityNews
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

open class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
) : ViewModel() {

    private val _newsData = MutableSharedFlow<List<EntityNews>>()
    var newsData = _newsData.asSharedFlow()

    private val _isDataLoaded = MutableStateFlow(false)
    var isDataLoaded = _isDataLoaded.asStateFlow()

    private val _bookmarkData =
        repository.getBookmarkNews().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    var bookmarkData = _bookmarkData

    fun addBookmarkData(data: EntityNews) {
        viewModelScope.launch {
            repository.addBookmark(data)
        }
    }

    fun deleteBookmarkData(data: EntityNews) {
        viewModelScope.launch {
            repository.deleteFromBookmark(data)
        }
    }

    fun updateBookmarkData(data: EntityNews) {
        viewModelScope.launch {
            repository.updateBookmarkItem(data)
        }
    }

    suspend fun requestNews() {
        viewModelScope.launch {
            val value = repository.getNews()
            if (value.isEmpty()) return@launch
            _isDataLoaded.emit(true)
            _newsData.emit(value)
        }
    }
}
