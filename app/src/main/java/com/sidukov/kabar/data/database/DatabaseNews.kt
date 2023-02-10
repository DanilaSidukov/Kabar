package com.sidukov.kabar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EntityNews::class], version = 1)
abstract class DatabaseNews : RoomDatabase() {

    abstract fun daoNews(): NewsDao
}

@Database(entities = [EntityNews::class], version = 1)
abstract class DatabaseNewsBookmark : RoomDatabase(){

    abstract fun daoNewsBookmark(): NewsBookmarkDao
}