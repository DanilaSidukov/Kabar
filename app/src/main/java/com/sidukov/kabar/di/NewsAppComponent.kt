package com.sidukov.kabar.di

import com.sidukov.kabar.ui.forgotpassword.fragmentpager.BaseViewPagerFragment
import com.sidukov.kabar.ui.news.ActivityGeneral
import com.sidukov.kabar.ui.news.FragmentBookmark
import com.sidukov.kabar.ui.news.FragmentExplore
import com.sidukov.kabar.ui.news.FragmentHome
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
    fun inject(fragmentHome: FragmentHome)
}