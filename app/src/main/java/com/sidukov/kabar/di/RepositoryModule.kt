package com.sidukov.kabar.di

import androidx.compose.ui.text.font.FontVariation
import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.data.database.NewsBookmarkDao
import com.sidukov.kabar.data.remote.api.ApiClient
import com.sidukov.kabar.data.settings.AccountRepository
import com.sidukov.kabar.data.settings.Settings
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        apiClient: ApiClient,
        newsBookmarkDao: NewsBookmarkDao
    ): NewsRepository = NewsRepository(apiClient, newsBookmarkDao)

    @Singleton
    @Provides
    fun provideAccountRepository(
        settings: Settings
    ): AccountRepository = AccountRepository(settings)

}