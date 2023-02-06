package com.sidukov.kabar.di

import android.content.Context
import androidx.annotation.NonNull
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.ui.news.newscategory.NewsViewModel
import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.NonNls
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Singleton
    @Provides
    @NonNull
    fun provideContext(): Context = context
}
