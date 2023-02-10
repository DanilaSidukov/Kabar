package com.sidukov.kabar.di

import com.sidukov.kabar.ui.news.ActivityGeneral
import com.sidukov.kabar.ui.news.FragmentBookmark
import com.sidukov.kabar.ui.news.FragmentExplore
import com.sidukov.kabar.ui.news.newscategory.*
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
    fun inject(activityGeneral: ActivityGeneral)
    fun inject(activityArticleNews: ActivityArticleNews)

    fun inject(fragmentBookmark: FragmentBookmark)
    fun inject(fragmentExplore: FragmentExplore)

    fun inject(fragment: FragmentAllNews)
    fun inject(fragment: FragmentBusinessNews)
    fun inject(fragment: FragmentEntertainmentNews)
    fun inject(fragment: FragmentHealthAndFoodNews)
    fun inject(fragment: FragmentPoliticsNews)
    fun inject(fragment: FragmentScienceNews)
    fun inject(fragment: FragmentSportsNews)
    fun inject(fragment: FragmentTechnologyNews)
}