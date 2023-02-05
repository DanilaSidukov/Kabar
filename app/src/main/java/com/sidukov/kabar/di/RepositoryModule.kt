package com.sidukov.kabar.di

import com.sidukov.kabar.data.NewsRepository
import com.sidukov.kabar.data.remote.api.ApiClient
import org.koin.dsl.module

val provideNewsRepositoryModule = module {
    factory {
        NewsRepository(apiClient = ApiClient(context = get()))
    }
}