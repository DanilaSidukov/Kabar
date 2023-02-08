package com.sidukov.kabar.di

import com.sidukov.kabar.ui.news.newscategory.FragmentAllNews
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        RepositoryModule::class,
        StorageModule::class,
        ViewModelModule::class
    ]
)

interface NewsAppComponent {
    fun inject(fragment: FragmentAllNews)
}