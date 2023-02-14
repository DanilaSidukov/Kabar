package com.sidukov.kabar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EntityNews::class], version = 1)
abstract class DatabaseNewsBookmark : RoomDatabase(){

    abstract fun daoNewsBookmark(): NewsBookmarkDao
}