package com.sidukov.kabar.di

import com.sidukov.kabar.ui.ActivityLogin
import com.sidukov.kabar.ui.ActivitySignUp
import com.sidukov.kabar.ui.createprofile.ActivityCreateProfile
import com.sidukov.kabar.ui.news.ActivityGeneral
import com.sidukov.kabar.ui.news.FragmentBookmark
import com.sidukov.kabar.ui.news.FragmentExplore
import com.sidukov.kabar.ui.news.FragmentHome
import com.sidukov.kabar.ui.news.newscategory.ActivityArticleNews
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
    fun inject(activitySignUp: ActivitySignUp)
    fun inject(activityLogin: ActivityLogin)
    fun inject(activityCreateProfile: ActivityCreateProfile)

    fun inject(fragmentBookmark: FragmentBookmark)
    fun inject(fragmentExplore: FragmentExplore)
    fun inject(fragmentHome: FragmentHome)
}