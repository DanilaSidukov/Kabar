package com.sidukov.kabar.di

import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.data.remote.api.ApiClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        apiClient: ApiClient,
    ): NewsRepository = NewsRepository(apiClient)

}