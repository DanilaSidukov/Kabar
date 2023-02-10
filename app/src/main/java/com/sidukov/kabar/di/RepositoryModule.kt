package com.sidukov.kabar.di

import androidx.lifecycle.ViewModel
import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.data.database.NewsBookmarkDao
import com.sidukov.kabar.data.database.NewsDao
import com.sidukov.kabar.data.remote.api.ApiClient
import com.sidukov.kabar.ui.news.newscategory.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        apiClient: ApiClient,
        newsDao: NewsDao,
        newsBookmarkDao: NewsBookmarkDao
    ): NewsRepository = NewsRepository(apiClient, newsDao, newsBookmarkDao)

}