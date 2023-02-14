package com.sidukov.kabar.di

import android.content.Context
import androidx.room.Room
import com.sidukov.kabar.data.database.DatabaseNewsBookmark
import com.sidukov.kabar.data.database.NewsBookmarkDao
import com.sidukov.kabar.data.settings.Settings
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Singleton
    @Provides
    fun provideSettingsContext(context: Context): Settings = Settings(context)


    @Singleton
    @Provides
    fun provideDatabaseNewsBookmark(context: Context): DatabaseNewsBookmark = Room.databaseBuilder(
        context,
        DatabaseNewsBookmark::class.java,
        "bookmark-list"
    ).build()
    @Singleton
    @Provides
    fun provideNewsBookmarkDao(database: DatabaseNewsBookmark): NewsBookmarkDao =
        database.daoNewsBookmark()
}